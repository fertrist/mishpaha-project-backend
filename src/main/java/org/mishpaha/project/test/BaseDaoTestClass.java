package org.mishpaha.project.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mishpaha.project.data.dao.DataBaseDao;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.ChangeRecord;
import org.mishpaha.project.data.model.District;
import org.mishpaha.project.data.model.DoneTraining;
import org.mishpaha.project.data.model.Email;
import org.mishpaha.project.data.model.Graduation;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.GroupMember;
import org.mishpaha.project.data.model.Ministry;
import org.mishpaha.project.data.model.ModelUtil;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Phone;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.School;
import org.mishpaha.project.data.model.Training;
import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.data.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mishpaha.project.test.TestUtil.getDate;

/**
 * Created by fertrist on 09.10.15.
 */
public class BaseDaoTestClass {
    final String shouldSucceedMessage = "Request should succeed.";
    final String shouldFailMessage = "Request should fail.";

    @Autowired
    GenericDao<District> districtDao;
    @Autowired
    GenericDao<Person> personDao;
    @Autowired
    GenericDao<Phone> phoneDao;
    @Autowired
    GenericDao<Email> emailDao;
    @Autowired
    GenericDao<Category> categoryDao;
    @Autowired
    GenericDao<ChangeRecord> changeRecordDao;
    @Autowired
    GenericDao<DoneTraining> doneTrainingDao;
    @Autowired
    GenericDao<Graduation> graduationDao;
    @Autowired
    GenericDao<Group> groupDao;
    @Autowired
    GenericDao<GroupMember> groupMemberDao;
    @Autowired
    GenericDao<Ministry> ministryDao;
    @Autowired
    GenericDao<Region> regionDao;
    @Autowired
    GenericDao<School> schoolDao;
    @Autowired
    GenericDao<Training> trainingDao;
    @Autowired
    GenericDao<Tribe> tribeDao;
    @Autowired
    GenericDao<Volunteer> volunteerDao;
    @Autowired
    DataBaseDao dataBaseDao;

    private String[] districts = new String[]{"Оболонь", "Подол", "Дорогожичи", "Шулявка"};
    private String[] categories = new String[] {"Приход", "Зелёные", "Гости", "Еврейский список"};
    private Person[] persons = new Person[]{
        new Person(0, "Коленный-1", "Сидор", "Сидорович", true,
            getDate("1960-09-15"), 1, "дома", true, true),
        new Person(0, "Коленный-2", "Сидор", "Сидорович", true,
            getDate("1955-10-15"), 1, "дома", true, true),
        new Person(0, "Коленный-3", "Сидор", "Сидорович", true,
            getDate("1965-11-15"), 1, "дома", true, true),
        new Person(0, "Коленный-4", "Сидор", "Сидорович", true,
            getDate("1970-03-15"), 1, "дома", true, true),
        new Person(0, "Имя-1", "Фамилия-1", "Отчество-1", true,
            getDate("1986-12-03"), 1, "где-то в центре", true, true),
        new Person(0, "Имя-2", "Фамилия-2", "Отчество-2", true,
            getDate("1988-08-05"), 2, "Ильинская 43, 12", false, true),
        new Person(0, "Имя-3",  "Фамилия-3", "Отчество-3", false,
            getDate("1985-04-07"), 1, "Лайоша Гавро 67а, 83", false, false),
        new Person(0, "Имя-4",  "Фамилия-4", "Отчество-4", false,
            getDate("1990-03-01"), 4, "Олени Телиги 20, кв.4", true, true),
        new Person(0, "Имя-5",  "Фамилия-5", "Отчество-5", false,
            getDate("1990-03-01"), 4, "Олени Телиги 20, кв.4", true, false),
        new Person(0, "Имя-6",  "Фамилия-6", "Отчество-6", true,
            getDate("1990-03-01"), 4, "Олени Телиги 20, кв.4", true, true),
    };
    private Phone[] phones = new Phone[]{
        new Phone(1, "0634561237"), new Phone(1, "0446589631"),
        new Phone(2, "0677894561"),
        new Phone(3, "0984561239"), new Phone(3, "0443652356"),
        new Phone(4, "0731236548")
    };
    private Email[] emails = new Email[]{
        new Email(1, "ivanov.ivan@gmail.com"),
        new Email(2, "petrov.petr@gmail.com"), new Email(2, "petrov.petr@rambler.ru"),
        new Email(3, "fedorov.fedor@gmail.com"), new Email(3, "fedorov.fedor@rambler.ru"),
        new Email(4, "evgeniev.evgeniy@gmail.com")
    };
    private String[] tribes = new String[] {"Иванова", "Петрова", "Сидорова"};
    private String[] ministries = new String[]{"Административное", "Группа порядка", "Попечитель"};

    private Region[] regions = new Region[] {
        new Region(1, 1), new Region(2, 2), new Region(3, 3), new Region(4, 4)
    };
    private Group[] groups = new Group[] {
        new Group(5, 1), new Group(7, 1), new Group(10, 4)
    };
    private Volunteer[] volunteers = new Volunteer[] {
        new Volunteer(5, 3), new Volunteer(5, 1), new Volunteer(6, 1), new Volunteer(7, 3),
        new Volunteer(8, 2), new Volunteer(9, 2), new Volunteer(10, 3), new Volunteer(10, 2)
    };
    private GroupMember[] groupMembers = new GroupMember[] {
        new GroupMember(5, 1, 1),
        new GroupMember(6, 1, 2),
        new GroupMember(7, 2, 1),
        new GroupMember(8, 2, 3),
        new GroupMember(9, 3, 1),
        new GroupMember(10, 3, 1)
    };
    private School[] schools = new School[] {
        new School(0, School.Level.SECOND, getDate("2015-02-01"), getDate("2015-07-01"), "Русняк"),
        new School(0, School.Level.FIRST, getDate("2015-09-21"), getDate("2015-12-01"), "Фурса")
    };
    private Graduation[] graduations = new Graduation[] {
        new Graduation(1, 5), new Graduation(1, 6), new Graduation(1, 7), new Graduation(1, 8),
        new Graduation(1, 9), new Graduation(1, 10), new Graduation(2, 7), new Graduation(2, 5),
        new Graduation(2, 10)
    };
    private Training[] trainings = new Training[] {
        new Training(0, "Эффективный руководитель", getDate("2015-05-01")),
        new Training(0, "Для промоутеров", getDate("2015-08-19"))
    };
    private DoneTraining[] doneTrainings = new DoneTraining[] {
        new DoneTraining(5, 1), new DoneTraining(5, 2), new DoneTraining(7, 1),
        new DoneTraining(7, 2), new DoneTraining(10, 1), new DoneTraining(10, 2)
    };
    private ChangeRecord[] changeRecords = new ChangeRecord[] {
        new ChangeRecord(5, 1, true),
        new ChangeRecord(6, 1, true),
        new ChangeRecord(7, 2, true),
        new ChangeRecord(8, 2, true),
        new ChangeRecord(9, 3, true),
        new ChangeRecord(10, 3, true)
    };

    @Before
    public void fillTables() {
        prepareScenario();
    }

    @After
    public void cleanTables() {
        dataBaseDao.cleanTables(ModelUtil.getTableNames());
    }

    public void prepareScenario() {
        createDistricts();
        createPersons();
        createEmails();
        createPhones();
        createTrainings();
        createDoneTrainings();
        createMinistries();
        createVolunteers();
        createCategories();
        createTribes();
        createRegions();
        createGroups();
        createGroupMembers();
        createChangeRecords();
        createSchools();
        createGraduations();
    }

    void createDistricts() {
        for (String district : districts) {
            Assert.assertEquals(1, districtDao.save(new District(district)));
        }
    }

    void createPersons() {
        for (Person person : persons) {
            Assert.assertEquals(1, personDao.save(person));
        }
    }

    void createEmails() {
        for(Email email : emails) {
            Assert.assertEquals(1, emailDao.save(email));
        }
    }

    void createPhones() {
        for (Phone phone : phones) {
            Assert.assertEquals(1, phoneDao.save(phone));
        }
    }

    void createCategories() {
        for (String category : categories) {
            Assert.assertEquals(1, categoryDao.save(new Category(category)));
        }
    }

    void createChangeRecords() {
        for (ChangeRecord changeRecord : changeRecords) {
            Assert.assertEquals(1, changeRecordDao.save(changeRecord));
        }
    }

    void createDoneTrainings() {
        for (DoneTraining doneTraining : doneTrainings) {
            Assert.assertEquals(1, doneTrainingDao.save(doneTraining));
        }
    }

    void createGraduations() {
        for (Graduation graduation : graduations) {
            Assert.assertEquals(1, graduationDao.save(graduation));
        }
    }

    void createGroups() {
        for (Group group : groups) {
            Assert.assertEquals(1, groupDao.save(group));
        }
    }

    void createGroupMembers() {
        for (GroupMember groupMember : groupMembers) {
            Assert.assertEquals(1, groupMemberDao.save(groupMember));
        }
    }

    void createMinistries() {
        for (String ministry : ministries) {
            Assert.assertEquals(1, ministryDao.save(new Ministry(ministry)));
        }
    }

    void createRegions() {
        for (Region region : regions) {
            Assert.assertEquals(1, regionDao.save(region));
        }
    }

    void createSchools() {
        for (School school : schools) {
            Assert.assertEquals(1, schoolDao.save(school));
        }
    }

    void createTrainings() {
        for (Training training : trainings) {
            Assert.assertEquals(1, trainingDao.save(training));
        }
    }

    void createTribes() {
        for (String tribe : tribes) {
            Assert.assertEquals(1, tribeDao.save(new Tribe(tribe)));
        }
    }

    void createVolunteers() {
        for (Volunteer volunteer : volunteers) {
            Assert.assertEquals(1, volunteerDao.save(volunteer));
        }
    }

}
