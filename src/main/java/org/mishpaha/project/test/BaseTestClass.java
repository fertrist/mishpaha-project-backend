package org.mishpaha.project.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mishpaha.project.data.dao.CategoryDaoImpl;
import org.mishpaha.project.data.dao.CategoryDaoImpl.Categories;
import org.mishpaha.project.data.dao.DataBaseDao;
import org.mishpaha.project.data.dao.EventDaoImpl;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.Email;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.EventType;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.GroupMember;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Phone;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mishpaha.project.data.dao.CategoryDaoImpl.Categories.green;
import static org.mishpaha.project.data.dao.CategoryDaoImpl.Categories.white;
import static org.mishpaha.project.data.dao.EventDaoImpl.EventTypes.call;
import static org.mishpaha.project.data.dao.EventDaoImpl.EventTypes.group;
import static org.mishpaha.project.data.dao.EventDaoImpl.EventTypes.meeting;
import static org.mishpaha.project.data.dao.EventDaoImpl.EventTypes.visit;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

abstract class BaseTestClass {

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
    @Autowired
    GenericDao<Event> eventDao;
    @Autowired
    private WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;

    final String shouldSucceedMessage = "Request should succeed.";
    final String shouldFailMessage = "Request should fail.";

    //each 15th is group leader, each 31th is region leader, each 23d is tribe leader
    protected static final int personsPerGroup = 15;
    private static final int groupsPerRegion = 2;
    private static final int regionsPerTribe = 2;
    private static final int tribeCount = 1;
    private static final int personsCount = personsPerGroup * groupsPerRegion * regionsPerTribe * tribeCount +
        tribeCount + regionsPerTribe * tribeCount; //extra points for region and tribe leaders
    private List<EventType> eventTypes = new ArrayList<>();

    private List<Event> events = new ArrayList<>();

    private List<Category> categories = new ArrayList<>();
    private List<Tribe> tribes = new ArrayList<>();
    private List<Region> regions = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
    private List<Phone> phones = new ArrayList<>();
    private List<Email> emails = new ArrayList<>();
    private List<GroupMember> groupMembers = new ArrayList<>();

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
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
        fillEvents();
    }

    private void fillCategories() {
        int i = 1;
        for (Categories category : Categories.values()) {
            Assert.assertEquals(1, categoryDao.save(new Category(category.name())));
            categories.add(new Category(i++, category.name()));
        }
    }

    private int getCategory(String category) {
        return categories.indexOf(category) + 1;
    }

    /**
     * Common scenario is following:
     * Each region contains 2 groups + regional leader.
     * Each group contains 15 persons. 10 are white. 9 new. 2 guests.
     */
    private void provideTestData() {
        tribes = new ArrayList<>();
        int regionId = 1, groupId = 1, personId = 1;
        LocalDate birthday = LocalDate.of(1990, 9, 1);
        for (int tribeId = 1; tribeId <= tribeCount; tribeId++, personId++) {
            Tribe tribe = new Tribe(tribeId, "tribe-" + tribeId);
            List<Region> tribeRegions = new ArrayList<>();
            for (int i = 1; i <= regionsPerTribe; i++, regionId++, personId++) {
                Region region = new Region(regionId, regionId*(personsPerGroup*groupsPerRegion+1), tribeId);
                List<Group> regionGroups = new ArrayList<>();
                for (int j = 1; j <= groupsPerRegion ; j++, groupId++, personId++) {
                    Group group = new Group(groupId, groupId*personsPerGroup, regionId);
                    List<Person> groupPersons = new ArrayList<>();
                    for (int k = 1; k < personsPerGroup; k++, personId++) {
                        String name = "Рег." + regionId + "_" + "Гр." + groupId + "_Чел." + personId;
                        int categoryId;
                        //2/3 of listed people are members of a group
                        if (k < (personsPerGroup / 3) * 2 - 1) {
                            categoryId = white.ordinal() + 1;
                        } else {
                            categoryId = personId % categories.size();
                            categoryId = categoryId == 0 ? categories.size() : categoryId;
                        }
                        String surname = categories.get(categoryId - 1).getName();
                        boolean sex = personId % 2 == 0;
                        boolean participates = personId % 2 == 0;
                        groupPersons.add(new Person(personId, name, surname, "Отчество", sex,
                            birthday, participates, !participates, categoryId, "Адрес", "Комментарий"));
                        groupMembers.add(new GroupMember(personId, groupId));
                    }
                    //add group leader
                    groupPersons.add(
                        new Person(personId, "Лид.Рег."+regionId+"Гр."+groupId, categories.get(white.ordinal()).getName(),
                            "Отчество", true, birthday, true, true, white.ordinal()+1, "Адрес", "Комментарий"));
                    groupMembers.add(new GroupMember(personId, groupId));
                    group.setPersons(groupPersons);
                    persons.addAll(groupPersons);
                    regionGroups.add(group);
                }
                region.setGroups(regionGroups);
                groups.addAll(regionGroups);
                tribeRegions.add(region);
                //add regional leader
                persons.add(new Person(personId, "Лид.Рег."+regionId, categories.get(white.ordinal()).getName(),
                    "Отчество", true, birthday, true, true, white.ordinal()+1, "Адрес", "Комментарий"));
            }
            //tribe leader
            persons.add(new Person(personId, "Лид.Кол."+tribeId, categories.get(white.ordinal()).getName(),
                "Отчество", true, birthday, true, true, white.ordinal()+1, "Адрес", "Комментарий"));
            tribe.setRegions(tribeRegions);
            regions.addAll(tribeRegions);
            tribes.add(tribe);
        }
    }

    /**
     * Add eventTypes. For last 6 months fill each day with each group events depending on day of a week.
     */
    private void fillEvents() {
        //fill in event types
        int i = 1;
        for (EventDaoImpl.EventTypes type : EventDaoImpl.EventTypes.values()) {
            assertEquals(1, ((EventDaoImpl) eventDao).saveEventType(type.name()));
            getEventTypes().add(new EventType(i++, type.name()));
        }
        //prepare time range
        LocalDate date = LocalDate.now().minusMonths(6);
        LocalDate now = LocalDate.now();
        /* Fill in past events.*/
        for (; date.isBefore(now); date = date.plusDays(1)) {
            populateGroupsEvents(date);
        }
    }

    /**
     * For each group creates following scenario:
     * Each person in category has 2 calls on a week.
     * Each 1 meeting and 1 visit.
     * Each week has a group. There is 1 absent, 1 quest, few new people.
     *
     * Meetings, visits, calls:
     * meetings white = 1
     * visits white = 1
     * meetings new = 3
     * visits new = 3
     * calls new = 8
     */
    private void populateGroupsEvents(LocalDate happened) {
        for (Group groupEntity : getGroups()) {
            switch (happened.getDayOfWeek().ordinal()) {
                case 1: // 1 meet listed, 1 visit listed, call all new
                    createSingleEvent(Categories.white, meeting, happened, groupEntity.getId());
                    createSingleEvent(Categories.white, visit, happened, groupEntity.getId());
                    createEventForCategory(Categories.green, call, happened, groupEntity.getId());
                    createEventForCategory(Categories.brown, call, happened, groupEntity.getId());
                    createEventForCategory(Categories.blue, call, happened, groupEntity.getId());
                    break;
                case 2:
                    break;
                case 3: //3 visits for each new, call all new
                    createSingleEvent(Categories.blue, visit, happened, groupEntity.getId());
                    createSingleEvent(Categories.brown, visit, happened, groupEntity.getId());
                    createSingleEvent(Categories.green, visit, happened, groupEntity.getId());
                    createEventForCategory(Categories.green, call, happened, groupEntity.getId());
                    createEventForCategory(Categories.brown, call, happened, groupEntity.getId());
                    createEventForCategory(Categories.blue, call, happened, groupEntity.getId());
                    break;
                case 4: // group
                    createGroupEvent(groupEntity.getId(), happened);
                    break;
                case 5: // 3 meet for each new
                    createSingleEvent(Categories.blue, meeting, happened, groupEntity.getId());
                    createSingleEvent(Categories.brown, meeting, happened, groupEntity.getId());
                    createSingleEvent(Categories.green, meeting, happened, groupEntity.getId());
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
        }
    }

    /**
     * Create specific event for all persons in category for certain group at specific date.
     */
    private void createEventForCategory(Categories category, EventDaoImpl.EventTypes eventType, LocalDate happened, int groupId) {
        getGroups().get(groupId-1).getPersons().stream()
            .filter(person -> person.getCategoryId() == category.ordinal() + 1)
            .forEach(person -> createAndSaveEvent(eventType, groupId, person.getId(), happened));
    }

    /**
     * All were present except 1 and 2 person.
     */
    private void createGroupEvent(int groupId, LocalDate happened) {
        List<Person> persons = getGroups().get(groupId-1).getPersons();
        for (int i = 2; i < persons.size(); i++) {
            createAndSaveEvent(group, groupId, persons.get(i).getId(), happened);
        }
    }

    private void createSingleEvent(Categories category, EventDaoImpl.EventTypes eventType, LocalDate happened, int groupId) {
        List<Person> persons = getGroups().get(groupId-1).getPersons();
        for (Person person : persons) {
            if (person.getCategoryId() == category.ordinal() + 1) {
                createAndSaveEvent(eventType, groupId, person.getId(), happened);
                return;
            }
        }
    }

    private void createAndSaveEvent(EventDaoImpl.EventTypes eventType, int groupId, int personId, LocalDate happened) {
        Event event = new Event(personId, groupId,
            eventType.ordinal()+1, happened, "Good " + eventType.name());
        event.setPersonId(personId);
        assertEquals(1, eventDao.save(event));
        getEvents().add(event);
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
        for (Person person : persons) {
            Email email1 = new Email(person.getId(), person.getFirstName() + ".a@gmail.com");
            Email email2 = new Email(person.getId(), person.getFirstName() + ".b@gmail.com");
            emails.add(email1);
            emails.add(email2);
            person.setEmails(new ArrayList<>());
            person.getEmails().add(email1.getEmail());
            person.getEmails().add(email2.getEmail());
        }
        for (Email email : emails) {
            Assert.assertEquals(1, emailDao.save(email));
        }
    }

    private void fillPhones() {
        for (Person person : persons) {
            Phone phone1 = new Phone(person.getId(), "06300000" + person.getId());
            Phone phone2 = new Phone(person.getId(), "06700000" + person.getId());
            phones.add(phone1);
            phones.add(phone2);
            person.setPhones(new ArrayList<>());
            person.getPhones().add(phone1.getPhone());
            person.getPhones().add(phone2.getPhone());
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

    public List<EventType> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }
    public List<Event> getEvents() {
        return events;
    }

    /*void fillPersons() {
        for (int i = 1; i <= persons.length; i++) {
            persons[i-1] = new Person("Имя_"+i, "Фамилия_"+i, "Отчество_"+i, true, getDate
                ("1990-09-01"), i%2==0, i%2==1, i % rawCategories.length, "Киев_"+i, "None");
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
