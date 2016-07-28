package org.mishpaha.project.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.mishpaha.project.data.dao.jdbc.CategoryDaoImpl;
import org.mishpaha.project.data.dao.jdbc.ChangeRecordDaoImpl;
import org.mishpaha.project.data.dao.jdbc.DataBaseDao;
import org.mishpaha.project.data.dao.jdbc.DoneTrainingDaoImpl;
import org.mishpaha.project.data.dao.jdbc.EmailDaoImpl;
import org.mishpaha.project.data.dao.jdbc.EventDaoImpl;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.jdbc.GraduationDaoImpl;
import org.mishpaha.project.data.dao.jdbc.GroupDaoImpl;
import org.mishpaha.project.data.dao.jdbc.GroupMemberDaoImpl;
import org.mishpaha.project.data.dao.jdbc.MinistryDaoImpl;
import org.mishpaha.project.data.dao.jdbc.PersonDaoImpl;
import org.mishpaha.project.data.dao.jdbc.PhoneDaoImpl;
import org.mishpaha.project.data.dao.jdbc.RegionDaoImpl;
import org.mishpaha.project.data.dao.jdbc.SchoolDaoImpl;
import org.mishpaha.project.data.dao.jdbc.SecurityDaoImpl;
import org.mishpaha.project.data.dao.jdbc.TrainingDaoImpl;
import org.mishpaha.project.data.dao.jdbc.TribeDaoImpl;
import org.mishpaha.project.data.dao.jdbc.VolunteerDaoImpl;
import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.ChangeRecord;
import org.mishpaha.project.data.model.DoneTraining;
import org.mishpaha.project.data.model.Email;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.Graduation;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.GroupMember;
import org.mishpaha.project.data.model.Ministry;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Phone;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.School;
import org.mishpaha.project.data.model.Training;
import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.data.model.Volunteer;
import org.mishpaha.project.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages="org.mishpaha.project")
@EnableAutoConfiguration
@PropertySource(value = { "classpath:application.properties" })
public class Application {

    @Autowired
    private Environment environment;

    @Bean CommandLineRunner init() {
        return (evt) -> {};
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setAdditionalProfiles(Constants.PROFILE_DEV);
        springApplication.run(args);
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new StandardPasswordEncoder(SecurityConfig.SECRET);
    }

    @Bean
    public SecurityDaoImpl getSecurityDAO(DataSource dataSource) {
        return new SecurityDaoImpl(dataSource);
    }

    @Bean
    public EventDaoImpl getEventDAO(DataSource dataSource) {
        return new EventDaoImpl(dataSource, ModelUtil.getTable(Event.class));
    }

	@Bean
	public GenericDao<Category> getCategoryDAO(DataSource dataSource) {
		return new CategoryDaoImpl(dataSource, ModelUtil.getTable(Category.class));
	}

	@Bean
	public GenericDao<Person> getPersonDAO(DataSource dataSource) {
		return new PersonDaoImpl(dataSource, ModelUtil.getTable(Person.class));
	}

	@Bean
	public GenericDao<Phone> getPhoneDAO(DataSource dataSource) {
		return new PhoneDaoImpl(dataSource, ModelUtil.getTable(Phone.class));
	}

	@Bean
    public GenericDao<Email> getEmailDao(DataSource dataSource) {
        return new EmailDaoImpl(dataSource, ModelUtil.getTable(Email.class));
    }

    @Bean
    public GenericDao<Ministry> getMinistryDao(DataSource dataSource) {
        return new MinistryDaoImpl(dataSource, ModelUtil.getTable(Ministry.class));
    }

    @Bean
    public GenericDao<Tribe> getTribeDao(DataSource dataSource) {
        return new TribeDaoImpl(dataSource, ModelUtil.getTable(Tribe.class));
    }

    @Bean
    public GenericDao<Region> getRegionDao(DataSource dataSource) {
        return new RegionDaoImpl(dataSource, ModelUtil.getTable(Region.class));
    }

    @Bean
    public GenericDao<Group> getGroupDao(DataSource dataSource) {
        return new GroupDaoImpl(dataSource, ModelUtil.getTable(Group.class));
    }

    @Bean
    public GenericDao<GroupMember> getGroupMemberDao(DataSource dataSource) {
        return new GroupMemberDaoImpl(dataSource, ModelUtil.getTable(GroupMember.class));
    }

    @Bean
    public GenericDao<School> getSchoolDao(DataSource dataSource) {
        return new SchoolDaoImpl(dataSource, ModelUtil.getTable(School.class));
    }

    @Bean
    public GenericDao<Training> getTrainingDao(DataSource dataSource) {
        return new TrainingDaoImpl(dataSource, ModelUtil.getTable(Training.class));
    }

    @Bean
    public GenericDao<DoneTraining> getDoneTrainingDao(DataSource dataSource) {
        return new DoneTrainingDaoImpl(dataSource, ModelUtil.getTable(DoneTraining.class));
    }

    @Bean
    public GenericDao<Volunteer> getVolunteerDao(DataSource dataSource) {
        return new VolunteerDaoImpl(dataSource, ModelUtil.getTable(Volunteer.class));
    }

    @Bean
    public GenericDao<Graduation> getGraduationDao(DataSource dataSource) {
        return new GraduationDaoImpl(dataSource, ModelUtil.getTable(Graduation.class));
    }

    @Bean
    public GenericDao<ChangeRecord> getChangeRecordDao(DataSource dataSource) {
        return new ChangeRecordDaoImpl(dataSource, ModelUtil.getTable(ChangeRecord.class));
    }

    @Bean
    public DataBaseDao getDataBaseDao(DataSource dataSource) {
        return new DataBaseDao(dataSource);
    }

    @Profile(Constants.PROFILE_DEV)
    @Bean @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(new String[]{"org.mishpaha.project.data"});
        factoryBean.setHibernateProperties(hibernateProperties());
        return factoryBean;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
//        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
//        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
//        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;
    }

    @Profile(Constants.PROFILE_DEV)
    @Bean
    public DataSource getDevelopmentDataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:mem:people");
//        dataSource.setUsername("user");
//        dataSource.setPassword("password");
//        dataSource.setInitialSize(5);
//        dataSource.setMaxActive(10);
//        return dataSource;

        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql\\h2\\schema.sql").setScriptEncoding("UTF-8")
                .addScript("classpath:sql\\h2\\init.sql").setScriptEncoding("UTF-8")
                .build();
    }

    @Profile(Constants.PROFILE_TEST)
    @Bean
    public DataSource getTestingDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:people");
        dataSource.setUsername("user");
        dataSource.setPassword("password");
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(10);
        return dataSource;
    }
    //	@Profile("prod")
//	@Bean
//	public DataSource getProductionDataSource() {
//		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//		dataSource.setDriverClass(org.postgresql.Driver.class);
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/mishpaha");
//		dataSource.setUsername("fertrist");
//		dataSource.setPassword("password");
//		return dataSource;
//	}
//

//	@Bean
//	public DataSource getTestingDataSource() {
//		return new EmbeddedDatabaseBuilder()
//			.setType(EmbeddedDatabaseType.H2)
//			.addScript("sql/h2/schema.sql")
//			.build();
//	}

}
