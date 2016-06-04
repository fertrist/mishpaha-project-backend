package org.mishpaha.project.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mishpaha.project.util.DateUtil;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    PeopleControllerTest.class,
    EventControllerTest.class,
    ReportControllerTest.class,
    ServiceTest.class,
    DaoTest.class,
    DateUtil.class
})
public class TestSuite {

}
