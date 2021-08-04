package com.siemens.sl.apigateway.services;

import com.siemens.sl.apigateway.exception.customexceptions.GenericException;
import com.siemens.sl.apigateway.model.AuthenticationRequest;
import com.siemens.sl.apigateway.model.AuthenticationResponseData;
import com.siemens.sl.apigateway.model.LoginResponse;
import com.siemens.sl.apigateway.model.v1.User;
import com.siemens.sl.apigateway.utility.JwtTokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    private static Logger logger = LogManager.getLogger(LoginService.class);

    /**
     * This method is used to authenticate the user and return a login response.
     *
     * @param request    - Pass the request received
     * @return -  LoginResponse
     */
    public LoginResponse getLoginResponse(AuthenticationRequest request) {
        logger.info("Inside getLoginResponse LoginService " +request);
        //Authenticating the username and password using authenticationManager
        User user = userService.getUser(request.getUsername());
        //First checking if the user is present or not, if not throwing appropriate exception
        if (user == null) {
            logger.error("User with username: " + request.getUsername() + " does not exist, throwing exception");
            throw new GenericException(HttpStatus.UNAUTHORIZED, 401, 18, null);
        }

        //User found, now verifying the password sent
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
        } catch (AuthenticationException exception) {
            //Password match failed
            logger.error("AuthenticationException occurred");
            //update failed login
            userService.updateLoginFailure(user);
            throw new GenericException(HttpStatus.UNAUTHORIZED, 401, 20, null);
        }

        //Checking if the existing refresh token is still valid or not, if still valid then returning the
        // same else generating a new refresh token and returning the same
        //Generating access and refresh tokens
        String accessToken = jwtTokenUtil.generateAccessToken(user);

        AuthenticationResponseData data = new AuthenticationResponseData();
        data.setUserId(user.getId());
        data.setAccessToken(accessToken);

        logger.debug("Request successfully processed, returning LoginResponse object");
        return new LoginResponse(true, 200, 200, "success", data);
    }
}
