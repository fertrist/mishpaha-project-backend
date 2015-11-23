package org.mishpaha.project.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.mishpaha.project.data.dao.AddressDaoImpl;
import org.mishpaha.project.data.dao.CategoryDaoImpl;
import org.mishpaha.project.data.dao.ChangeRecordDaoImpl;
import org.mishpaha.project.data.dao.DataBaseDao;
import org.mishpaha.project.data.dao.DoneTrainingDaoImpl;
import org.mishpaha.project.data.dao.EmailDaoImpl;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.GraduationDaoImpl;
import org.mishpaha.project.data.dao.GroupDaoImpl;
import org.mishpaha.project.data.dao.GroupMemberDaoImpl;
import org.mishpaha.project.data.dao.MinistryDaoImpl;
import org.mishpaha.project.data.dao.PersonDaoImpl;
import org.mishpaha.project.data.dao.PhoneDaoImpl;
import org.mishpaha.project.data.dao.RegionDaoImpl;
import org.mishpaha.project.data.dao.SchoolDaoImpl;
import org.mishpaha.project.data.dao.TrainingDaoImpl;
import org.mishpaha.project.data.dao.TribeDaoImpl;
import org.mishpaha.project.data.dao.VolunteerDaoImpl;
import org.mishpaha.project.data.model.Address;
import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.ChangeRecord;
import org.mishpaha.project.data.model.DoneTraining;
import org.mishpaha.project.data.model.Email;
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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages="org.mishpaha.project")
@EnableAutoConfiguration
public class MvcConfiguration {

    @Bean CommandLineRunner init() {
        return (evt) -> {};
    }

    public static void main(String[] args) {
        SpringApplication.run(MvcConfiguration.class, args);
    }

    @Bean
    public GenericDao<Address> getAddressDAO(DataSource dataSource) {
        return new AddressDaoImpl(dataSource, ModelUtil.getTable(Address.class));
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

    //@Profile("dev")
    @Bean
    public DataSource getDevelopmentDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testDb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
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
