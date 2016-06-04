package org.mishpaha.project.controller;
import org.mishpaha.project.data.model.User;
import org.mishpaha.project.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mishpaha.project.config.Constants.*;

@RestController
@RequestMapping(SECURITY_BASE)
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = USER, method = RequestMethod.GET)
    public Principal getUser(Principal user) {
        return user;
    }

    @RequestMapping(value = USER, method = RequestMethod.POST)
    public User saveUser(User user) {
        return securityService.save(user);
    }

    @RequestMapping(value = USER + "/{username}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String username) {
        securityService.delete(username);
    }

    @RequestMapping("/resource")
    public Map<String,Object> home(@AuthenticationPrincipal UserDetails principal) {
        String name = principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ? "Admin" : "User";
        Map<String,Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello " + name);
        return model;
    }

}

