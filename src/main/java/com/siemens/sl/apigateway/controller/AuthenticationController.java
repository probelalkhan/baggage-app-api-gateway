package com.siemens.sl.apigateway.controller;

import com.siemens.sl.apigateway.constants.endpoints.Endpoints;
import com.siemens.sl.apigateway.model.*;
import com.siemens.sl.apigateway.model.v1.Group;
import com.siemens.sl.apigateway.model.v1.User;
import com.siemens.sl.apigateway.services.LoginService;
import com.siemens.sl.apigateway.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://172.18.170.30:7004", "http://localhost:7004"})
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:7004"})
    public class AuthenticationController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    private static Logger logger = LogManager.getLogger(AuthenticationController.class);

    @PostMapping(Endpoints.LOGIN)
    public LoginResponse login(@RequestBody AuthenticationRequest request) {
        logger.info("Login Req: "+request);
        LoginResponse loginResponse = loginService.getLoginResponse(request);
        logger.debug("LOGIN Request successfully processed, returning response");
        return loginResponse;
    }

    @PostMapping(Endpoints.LOGOUT)
    public LogoutResponse logout(String user) {
        //TODO - WILL BE USED FOR LOGGING ONLY (Most likely)
        logger.info("Logout Request successfully processed, returning Success Response");
        return new LogoutResponse(true, 200, 200, "success", user);
    }

    @PostMapping(Endpoints.ADD_USER)
    public AddUserRequest addUser(@RequestBody AddUserRequest request){
        logger.info("Add user Called " + request);
        return userService.addUser(request);
    }

    @GetMapping(Endpoints.USERS)
    public List<User> users(){
        logger.info("users Called ");
        return userService.getAllUsers();
    }

    @PostMapping(Endpoints.ADD_GROUP)
    public Group addGroup(@RequestBody Group group){
        return userService.addGroup(group);
    }

    @GetMapping(Endpoints.GROUPS)
    public List<Group> addGroup(){
        return userService.getAllGroups();
    }

    @PostMapping(Endpoints.UPDATE_PASSWORD)
    public AddUserRequest updatePassword(@RequestBody AddUserRequest request){
        return userService.updatePassword(request);
    }
}