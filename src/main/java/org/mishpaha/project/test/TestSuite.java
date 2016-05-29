package org.mishpaha.project.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    PeopleControllerTest.class,
    EventControllerTest.class,
    DaoTest.class,
})
public class TestSuite {

}
