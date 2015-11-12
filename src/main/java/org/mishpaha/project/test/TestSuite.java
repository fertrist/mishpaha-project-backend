package org.mishpaha.project.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by fertrist on 29.09.15.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    SimpleDaoTest.class,
    ComplexDaoTest.class,
    TestServices.class,
    TestPersonController.class,
})
public class TestSuite {

}
