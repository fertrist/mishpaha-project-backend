package org.mishpaha.project.controller;

import org.mishpaha.project.data.model.Report;
import org.mishpaha.project.exception.DaoMistakeException;
import org.mishpaha.project.service.ReportService;
import org.mishpaha.project.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/group/{id}", method = RequestMethod.GET)
    public Report getGroupReport(@PathVariable int id,
                              @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate start,
                              @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate end)
        throws DaoMistakeException {
        end = DateUtil.setDefaultEnd(end, this);
        start = DateUtil.setDefaultStart(start, end, this);
        return reportService.getGroupReport(id, start, end);
    }

    @RequestMapping(value = "/region/{id}", method = RequestMethod.GET)
    public List<Report> getRegionReport(@PathVariable int id,
                                        @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate start,
                                        @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate end)
        throws DaoMistakeException {
        end = DateUtil.setDefaultEnd(end, this);
        start = DateUtil.setDefaultStart(start, end, this);
        return reportService.getRegionReport(id, start, end);
    }

}
