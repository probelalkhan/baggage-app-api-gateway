package com.siemens.sl.apigateway.services;

import com.siemens.sl.apigateway.model.AddUserRequest;
import com.siemens.sl.apigateway.model.GetUserResponse;
<<<<<<< HEAD
import com.siemens.sl.apigateway.model.User;
import com.siemens.sl.apigateway.model.v1.Group;
=======
import com.siemens.sl.apigateway.model.v1.User;
>>>>>>> 27d324f9974c4609e2528124156991f5a4051d02
import com.siemens.sl.apigateway.retrofit.RemoteServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.util.List;

@Service
public class UserService {

    @Autowired
    RemoteServices remoteServices;

    private static Logger logger = LogManager.getLogger(UserService.class);

    public User getUser(String username) {
        logger.info("Inside getUser in UserService class of API gateway " + username);
        try {
            Response<GetUserResponse> response = remoteServices.getUser(username).execute();
            if (response.isSuccessful() &&
                    response.body() != null &&
                    response.body().getData() != null) {
                return response.body().getData();
            } else {
                logger.error("Get user service failed");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Get user service failed (Exception thrown)");
        }
        return null;
    }

    public AddUserRequest addUser(AddUserRequest request) {
        try {
            BCryptPasswordEncoder bs = new BCryptPasswordEncoder();
            request.setPassword(bs.encode(request.getPassword()));
            Response<AddUserRequest> response = remoteServices.addUser(request).execute();
            if (response.isSuccessful() &&
                    response.body() != null) {
                return response.body();
            } else {
                logger.error("Get user service failed");
            }

        } catch (Exception e) {
            logger.error("Get user service failed (Exception thrown)");
        }
        return null;
    }

    public Group addGroup(Group request) {
        try {
            Response<Group> response = remoteServices.addGroup(request).execute();
            if (response.isSuccessful() &&
                    response.body() != null) {
                return response.body();
            } else {
                logger.error("Get user service failed");
            }

        } catch (Exception e) {
            logger.error("Get user service failed (Exception thrown)");
        }
        return null;
    }

    public List<com.siemens.sl.apigateway.model.v1.User> getAllUsers() {
        try {
            Response<List<com.siemens.sl.apigateway.model.v1.User>> response = remoteServices.getAllUsers().execute();
            if (response.isSuccessful() &&
                    response.body() != null) {
                return response.body();
            } else {
                logger.error(response.body().toString());
                logger.error(response.errorBody().string());
                logger.error("Get user service failed");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Get user service failed (Exception thrown)");
        }
        return null;
    }
}
