package hello.org.mishpaha.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import hello.org.mishpaha.project.dao.DistrictDAO;
import hello.org.mishpaha.project.dao.DistrictDAOImpl;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages="org.mishpaha.project")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/mishpaha");
		dataSource.setUsername("admin");
		dataSource.setPassword("admin");
		
		return dataSource;
	}
	
	@Bean
	public DistrictDAO getDistrictDAO() {
		return new DistrictDAOImpl(getDataSource());
	}
}
