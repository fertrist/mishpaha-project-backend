/*
package hello.org.mishpaha.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import hello.org.mishpaha.project.dao.DistrictDAO;
import hello.org.mishpaha.project.model.District;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

*/
/**
 * This controller routes accesses to the application to the appropriate
 * hanlder methods. 
 * @author www.codejava.net
 *
 *//*

@Controller
public class HomeController {

	@Autowired
	private DistrictDAO districtDAO;
	
	@RequestMapping(value="/")
	public ModelAndView listDistricts(ModelAndView model) throws IOException{
		List<District> districtsList = districtDAO.list();
		model.addObject("listDistricts", districtsList);
		model.setViewName("districts");
		
		return model;
	}
	
	@RequestMapping(value = "/newDistrict", method = RequestMethod.GET)
	public ModelAndView newDistrict(ModelAndView model) {
		District newDistrict = new District();
		model.addObject("district", newDistrict);
		model.setViewName("DistrictForm");
		return model;
	}
	
	@RequestMapping(value = "/saveDistrict", method = RequestMethod.POST)
	public ModelAndView saveDistrict(@ModelAttribute District district) {
		districtDAO.saveOrUpdate(district);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/deleteDistrict", method = RequestMethod.GET)
	public ModelAndView deleteContact(HttpServletRequest request) {
		int districtId = Integer.parseInt(request.getParameter("id"));
		districtDAO.delete(districtId);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/editDistrict", method = RequestMethod.GET)
	public ModelAndView editContact(HttpServletRequest request) {
		int districtId = Integer.parseInt(request.getParameter("id"));
		District district = districtDAO.get(districtId);
		ModelAndView model = new ModelAndView("DistrictForm");
		model.addObject("district", district);
		
		return model;
	}
}
*/
