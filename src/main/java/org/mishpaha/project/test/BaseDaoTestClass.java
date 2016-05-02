package org.mishpaha.project.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mishpaha.project.data.dao.DataBaseDao;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.Email;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.GroupMember;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Phone;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.mishpaha.project.util.TestUtil.getDate;

class BaseDaoTestClass {

    @Autowired
    GenericDao<Person> personDao;
    @Autowired
    GenericDao<Phone> phoneDao;
    @Autowired
    GenericDao<Email> emailDao;
    @Autowired
    GenericDao<Category> categoryDao;
    @Autowired
    GenericDao<Group> groupDao;
    @Autowired
    GenericDao<GroupMember> groupMemberDao;
    @Autowired
    GenericDao<Region> regionDao;
    @Autowired
    GenericDao<Tribe> tribeDao;
    @Autowired
    DataBaseDao dataBaseDao;

    final String shouldSucceedMessage = "Request should succeed.";
    final String shouldFailMessage = "Request should fail.";

    //each 5th is group leader, each 11th is region leader, each 23d is tribe leader
    protected static final int personsPerGroup = 5;
    private static final int groupsPerRegion = 2;
    private static final int regionsPerTribe = 2;
    private static final int tribeCount = 1;
    private static final int personsCount = personsPerGroup * groupsPerRegion * regionsPerTribe * tribeCount +
        tribeCount + regionsPerTribe * tribeCount; //extra points for region and tribe leaders

    private String[] categories = new String[]{"Белый", "Синий", "Зеленый", "Еврейский"};
    private List<Tribe> tribes = new ArrayList<>();
    private List<Region> regions = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
    private List<Phone> phones = new ArrayList<>();
    private List<Email> emails = new ArrayList<>();
    private List<GroupMember> groupMembers = new ArrayList<>();


    @Before
    public void setUp() {
        Assert.assertEquals("Tables are not created", 1, dataBaseDao.createTables());
        fillTables();
    }

    @After
    public void cleanTables() {
        dataBaseDao.dropTables(ModelUtil.getTableNames());
    }

    private void fillTables() {
        fillCategories();
        provideTestData();
        fillTestData();
        fillEmails();
        fillPhones();
    }

    private void fillCategories() {
        for (String category : categories) {
            Assert.assertEquals(1, categoryDao.save(new Category(category)));
        }
    }

    private void provideTestData() {
        tribes = new ArrayList<>();
        int reg = 1, gr = 1, per = 1;
        for (int tr = 1; tr <= tribeCount; tr++, per++) {
            Tribe tribe = new Tribe(tr, "Колено-" + tr);
            List<Region> tribeRegions = new ArrayList<>();
            for (int i = 1; i <= regionsPerTribe; i++, reg++, per++) {
                Region region = new Region(reg, reg*(personsPerGroup*groupsPerRegion+1), tr);
                List<Group> regionGroups = new ArrayList<>();
                for (int j = 1; j <= groupsPerRegion ; j++, gr++) {
                    Group group = new Group(gr, gr*personsPerGroup, reg);
                    List<Person> groupPersons = new ArrayList<>();
                    for (int k = 1; k <= personsPerGroup; k++, per++) {
                        groupPersons.add(new Person(per, "Имя_"+per, "Фамилия_"+per, "Отчество_"+per, true,
                            getDate("1990-09-01"), per%2==0, per%2==1, per % categories.length, "Киев_"+per, "None"));
                        groupMembers.add(new GroupMember(per, gr));
                    }
                    group.setPersons(groupPersons);
                    persons.addAll(groupPersons);
                    regionGroups.add(group);
                }
                region.setGroups(regionGroups);
                groups.addAll(regionGroups);
                tribeRegions.add(region);
                //regional leader
                persons.add(new Person(per, "Региональный_"+per, "Фамилия_"+per, "Отчество_"+per, true,
                    getDate("1990-09-01"), per%2==0, true, 1, "Киев_"+per, "None"));
            }
            //tribe leader
            persons.add(new Person(per, "Коленный_"+per, "Фамилия_"+per, "Отчество_"+per, true,
                getDate("1990-09-01"), per%2==0, true, 1, "Киев_"+per, "None"));
            tribe.setRegions(tribeRegions);
            regions.addAll(tribeRegions);
            tribes.add(tribe);
        }
    }

    private void fillTestData() {
        for (Person person : persons) {
            Assert.assertEquals(1, personDao.save(person));
        }
        for (Tribe tribe : tribes) {
            Assert.assertEquals(1, tribeDao.save(new Tribe(tribe.getName())));
        }
        for (Region region : regions) {
            Assert.assertEquals(1, regionDao.save(region));
        }
        for (Group group : groups) {
            Assert.assertEquals(1, groupDao.save(group));
        }
        for (GroupMember groupMember : groupMembers) {
            Assert.assertEquals(1, groupMemberDao.save(groupMember));
        }
    }

    private void fillEmails() {
        for (int i = 1; i <= persons.size(); i++) {
            emails.add(new Email(i, persons.get(i-1).getFirstName() + ".a@gmail.com"));
            emails.add(new Email(i, persons.get(i-1).getFirstName() + ".b@gmail.com"));
        }
        for (Email email : emails) {
            Assert.assertEquals(1, emailDao.save(email));
        }
    }

    private void fillPhones() {
        for (Person person : persons) {
            phones.add(new Phone(person.getId(), "06300000" + person.getId()));
            phones.add(new Phone(person.getId(), "06700000" + person.getId()));
        }
        for (Phone phone : phones) {
            Assert.assertEquals(1, phoneDao.save(phone));
        }
    }

    List<Person> getPersonsByGroup(int groupId) {
        for (Group group : groups) {
            if(group.getId() == groupId) {
                return group.getPersons();
            }
        }
        return null;
    }

    public List<Phone> getPhones(int personId) {
        List<Phone> phoneList = new ArrayList<>();
        for(Phone phone : phones) {
            if (phone.getPersonId() == personId) {
                phoneList.add(phone);
            }
        }
        return phoneList;
    }

    public List<Email> getEmails(int personId) {
        List<Email> emailList = new ArrayList<>();
        for(Email email : emails) {
            if (email.getPersonId() == personId) {
                emailList.add(email);
            }
        }
        return emailList;
    }

    public Person getPersonById(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public List<Tribe> getTribes() {
        return tribes;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Person> getPersons() {
        return persons;
    }

    /*void fillPersons() {
        for (int i = 1; i <= persons.length; i++) {
            persons[i-1] = new Person("Имя_"+i, "Фамилия_"+i, "Отчество_"+i, true, getDate
                ("1990-09-01"), i%2==0, i%2==1, i % categories.length, "Киев_"+i, "None");
        }
        for (Person person : persons) {
            Assert.assertEquals(1, personDao.save(person));
        }
    }

    void fillTribes() {
        for (Tribe tribe : tribes) {
            Assert.assertEquals(1, tribeDao.save(tribe));
        }
    }

    void fillRegions() {
        for (Region region : regions) {
            Assert.assertEquals(1, regionDao.save(region));
        }
    }

    void fillGroups() {
        int groupsPerRegion = regions.length / groups.length;
        for (int reg = 1; reg <= regions.length; reg++) {
            for (int i = 1; i < groups.length; i++) {
                //groups[i-1] = new Group(i, i*5, ); //each 5th person is group leader
            }
        }
        for (Group group : groups) {
            Assert.assertEquals(1, groupDao.save(group));
        }
    }

    void fillGroupMembers() {
        for (GroupMember groupMember : groupMembers) {
            Assert.assertEquals(1, groupMemberDao.save(groupMember));
        }
    }

    @Autowired
    GenericDao<ChangeRecord> changeRecordDao;
    @Autowired
    GenericDao<DoneTraining> doneTrainingDao;
    @Autowired
    GenericDao<Graduation> graduationDao;
    @Autowired
    GenericDao<Ministry> ministryDao;
    @Autowired
    GenericDao<School> schoolDao;
    @Autowired
    GenericDao<Training> trainingDao;
    @Autowired
    GenericDao<Volunteer> volunteerDao;

    String[] ministries = new String[]{"Административное", "Группа порядка", "Попечитель"};

    Volunteer[] volunteers = new Volunteer[]{
        new Volunteer(5, 3), new Volunteer(5, 1), new Volunteer(6, 1), new Volunteer(7, 3),
        new Volunteer(8, 2), new Volunteer(9, 2), new Volunteer(10, 3), new Volunteer(10, 2)
    };

    School[] schools = new School[]{
        new School(0, School.Level.SECOND, getDate("2015-02-01"), getDate("2015-07-01"), "Русняк"),
        new School(0, School.Level.FIRST, getDate("2015-09-21"), getDate("2015-12-01"), "Фурса")
    };
    Graduation[] graduations = new Graduation[]{
        new Graduation(1, 5), new Graduation(1, 6), new Graduation(1, 7), new Graduation(1, 8),
        new Graduation(1, 9), new Graduation(1, 10), new Graduation(2, 7), new Graduation(2, 5),
        new Graduation(2, 10)
    };
    Training[] trainings = new Training[]{
        new Training(0, "Эффективный руководитель", getDate("2015-05-01")),
        new Training(0, "Для промоутеров", getDate("2015-08-19"))
    };
    DoneTraining[] doneTrainings = new DoneTraining[]{
        new DoneTraining(5, 1), new DoneTraining(5, 2), new DoneTraining(7, 1),
        new DoneTraining(7, 2), new DoneTraining(10, 1), new DoneTraining(10, 2)
    };
    ChangeRecord[] changeRecords = new ChangeRecord[]{
        new ChangeRecord(5, 1, true),
        new ChangeRecord(6, 1, true),
        new ChangeRecord(7, 2, true),
        new ChangeRecord(8, 2, true),
        new ChangeRecord(9, 3, true),
        new ChangeRecord(10, 3, true)
    };

    void fillChangeRecords() {
        for (ChangeRecord changeRecord : changeRecords) {
            Assert.assertEquals(1, changeRecordDao.save(changeRecord));
        }
    }

    void fillDoneTrainings() {
        for (DoneTraining doneTraining : doneTrainings) {
            Assert.assertEquals(1, doneTrainingDao.save(doneTraining));
        }
    }

    void fillGraduations() {
        for (Graduation graduation : graduations) {
            Assert.assertEquals(1, graduationDao.save(graduation));
        }
    }

    void fillMinistries() {
        for (String ministry : ministries) {
            Assert.assertEquals(1, ministryDao.save(new Ministry(ministry)));
        }
    }

    void fillSchools() {
        for (School school : schools) {
            Assert.assertEquals(1, schoolDao.save(school));
        }
    }

    void fillTrainings() {
        for (Training training : trainings) {
            Assert.assertEquals(1, trainingDao.save(training));
        }
    }

    void fillVolunteers() {
        for (Volunteer volunteer : volunteers) {
            Assert.assertEquals(1, volunteerDao.save(volunteer));
        }
    }*/
}
