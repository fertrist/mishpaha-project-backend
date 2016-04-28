package org.project.people;

import org.apache.commons.dbcp.BasicDataSource;
import org.project.people.data.dao.CategoryDaoImpl;
import org.project.people.data.dao.DataBaseDao;
import org.project.people.data.dao.GenericDao;
import org.project.people.data.dao.PersonDaoImpl;
import org.project.people.data.entity.Category;
import org.project.people.data.entity.Person;
import org.project.people.service.PersonService;
import org.project.people.util.EntityUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class Application {

    private static final String peopleDBName = "peopledb";
    public static final String production = "production";
    public static final String development = "development";
    public static final String profile = development;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setAdditionalProfiles(profile);
        springApplication.run(args);
    }

    /**
     * Development database.
     */
    @Profile(development)
    @Bean
    public DataSource getDevelopmentDataSource(){
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .setName(peopleDBName)
            .addScript("sql\\h2\\schema.sql")
            .addScript("sql\\h2\\insert-values.sql").setScriptEncoding("UTF-8")
            .build();
    }

    /**
     * Production database.
     */
    @Profile(production)
    @Bean DataSource getMySqlDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/" + peopleDBName); //?user=fertrist&password=fertrist
        dataSource.setUsername("fertrist");
        dataSource.setPassword("fertrist");
        dataSource.setInitialSize(3);
        return dataSource;
    }

    @Bean GenericDao<Person> getPersonDao(DataSource dataSource) {
        return new PersonDaoImpl(dataSource, EntityUtil.getTable(Person.class));
    }

    @Bean GenericDao<Category> getCategoryDao(DataSource dataSource) {
        return new CategoryDaoImpl(dataSource, EntityUtil.getTable(Category.class));
    }

    @Bean public DataBaseDao getDataBaseDao(DataSource dataSource) {
        return new DataBaseDao(dataSource);
    }

    @Bean public PersonService getPersonService() {
        return new PersonService();
    }

    /**
     * Will be discovered by Spring and used to register DelegatingFilterProxy with the web container.
     */
    //public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {}

}

