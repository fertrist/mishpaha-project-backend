package org.mishpaha.project.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ListControllerTest.class,
    ListServiceTest.class,
    EmailPhoneDaoTest.class,
    PersonDaoTest.class,
})
public class TestSuite {

}
