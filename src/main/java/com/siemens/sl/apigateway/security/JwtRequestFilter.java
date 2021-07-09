package com.siemens.sl.apigateway.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siemens.sl.apigateway.constants.endpoints.Endpoints;
import com.siemens.sl.apigateway.model.ErrorResponse;
import com.siemens.sl.apigateway.services.UserService;
import com.siemens.sl.apigateway.utility.ErrorJsonParser;
import com.siemens.sl.apigateway.utility.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;


/**
 * The JwtRequestFilter extends the Spring Web Filter OncePerRequestFilter class. For any incoming request this Filter
 * class gets executed. It checks if the request has a valid JWT token. If it has a valid JWT Token then it sets the
 * Authentication in the context, to specify that the current user is authenticated.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    private static Logger logger = LogManager.getLogger(JwtRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String username;
        String jwtToken = null;
        UserDetails userDetails = null;
        int code = -1;
        int subCode = -1;

        logger.debug("New request received with endpoint: " + request.getRequestURI());
        //If the request is an authentication request then do check for authorization just continue
        for (String endPoint : Endpoints.allowedEndpoints) {
            if (endPoint.equalsIgnoreCase(request.getServletPath())) {
                logger.debug("Request endpoint present in list of allowed endpoints, skipping JWT Token validation");
                //Continue with the request
                chain.doFilter(request, response);
                return;
            }
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        final String requestTokenHeader = request.getHeader("Authorization");
        logger.error("requestTokenHeader  "  + requestTokenHeader);
        if (requestTokenHeader == null) {
            //The request does not contain any token
            code = 400;
            subCode = 2;
            logger.error("Bearer token not found 2");
        } else if (!requestTokenHeader.startsWith("Bearer ")) {
            //The request contains token but the token does not start with 'Bearer'
            code = 401;
            subCode = 12;
            logger.error("Bearer token does not start with 'Bearer'");
        }

        //The request contains the bearer token, validating the token
        if (code == -1) {
            //The request contains an authorization token starting with 'Bearer', removing the same
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException exception) {
                code = 401;
                subCode = 3;
                logger.error("Username Not Found, returning error response");
            } catch (MalformedJwtException exception) {
                code = 401;
                subCode = 3;
                logger.error("MalformedJwtException occurred, returning error response");
            } catch (SignatureException exception) {
                code = 401;
                subCode = 7;
                logger.error("SignatureException occurred, returning error response");
            } catch (IllegalArgumentException exception) {
                code = 401;
                subCode = 3;
                logger.error("IllegalArgumentException occurred, returning error response");
            } catch (ExpiredJwtException exception) {
                code = 401;
                subCode = 10;
                logger.error("ExpiredJwtException occurred, returning error response");
            }
        }

        if (code == -1 && userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.


                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                logger.debug("JWT Token validated, continuing with the request");
                chain.doFilter(request, response);
            } else {
                code = 401;
                subCode = 18;
                logger.error("JWT Token invalid");
            }
        }


        if (code != -1) {
            //Note - Not throwing generic exception from here as it is causing a null pointer exception
            response.setStatus(code);
            //If some error occurred while authorizing, send a custom error response back
            ErrorResponse errorResponse = getErrorResponse(code, subCode);
            PrintWriter writer = response.getWriter();
            writer.write(convertObjectToJson(errorResponse));
            writer.flush();
        }
        //Else continue with the request
    }


    private ErrorResponse getErrorResponse(int code, int subCode) {
        ErrorResponse responseFromJson = ErrorJsonParser.getErrorResponse("en", code, subCode);

        if (responseFromJson == null) {
            //INFO - Ensure the code and sub code should not return null!!
            responseFromJson = ErrorJsonParser.getErrorResponse("en", 400, 1);
        }

        responseFromJson.setTimestamp(new Date().toString());
        return responseFromJson;
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}