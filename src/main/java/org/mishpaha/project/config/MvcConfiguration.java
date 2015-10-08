package org.mishpaha.project.config;

import org.mishpaha.project.data.dao.CategoryDaoImpl;
import org.mishpaha.project.data.dao.DistrictDaoImpl;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.PersonDaoImpl;
import org.mishpaha.project.data.dao.PhoneDaoImpl;
import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.District;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Phone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages="org.mishpaha.project")
public class MvcConfiguration {

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
//	@Profile("dev")
//	@Bean
//	public DataSource getDevelopmentDataSource() {
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");
//		dataSource.setUrl("jdbc:h2:mem:testDb");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//		dataSource.setInitialSize(5);
//		dataSource.setMaxActive(10);
//		return dataSource;
//	}

	@Bean
	public GenericDao<District> getDistrictDAO(DataSource dataSource) {
		return new DistrictDaoImpl(dataSource, "districts");
	}

	@Bean
	public GenericDao<Category> getCategoryDAO(DataSource dataSource) {
		return new CategoryDaoImpl(dataSource, "categories");
	}

	@Bean
	public GenericDao<Person> getPersonDAO(DataSource dataSource) {
		return new PersonDaoImpl(dataSource, "persons");
	}

	@Bean
	public GenericDao<Phone> getPhoneDAO(DataSource dataSource) {
		return new PhoneDaoImpl(dataSource, "phones");
	}

	@Bean
	public DataSource getTestingDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript("sql/h2/schema.sql")
			.build();
	}

}
