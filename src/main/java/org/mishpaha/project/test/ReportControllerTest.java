package org.mishpaha.project.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.Application;
import org.mishpaha.project.config.Constants;
import org.mishpaha.project.controller.ReportController;
import org.mishpaha.project.util.DateUtil;
import org.mishpaha.project.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(Constants.PROFILE_TEST)
public class ReportControllerTest extends BaseTestClass{

    @Autowired
    private ReportController reportController;

    @Test
    public void testGroupReportDefaultDates() throws Exception {
        LocalDate end = DateUtil.setDefaultEnd(null, reportController.getClass());
        LocalDate start = DateUtil.setDefaultStart(null, end, reportController.getClass());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
            .get("/reports/group/1?start=" + start.toString() + "&end=" + end.toString()));
        Util.assertSuccess(resultActions);

        resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/reports/group/1"));
        Util.assertSuccess(resultActions);
    }

    @Test
    public void testRegionReport() {

    }

}
