package com.carsoline.configs;

import com.carsoline.rest.daos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.sql.DataSource;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * Created by Dominik on 2017-05-12.
 */
/*@Provider
//@EnableWebSecurity//NEW!!!!
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter implements javax.ws.rs.container.ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
            .entity("Access blocked!").build();

    @Autowired
    DataSource dataSource;

    @Override
    public void filter(ContainerRequestContext requestContext)
    {

        Method method = resourceInfo.getResourceMethod();
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
        {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class))
            {
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //if (headers.containsKey("dnt")) {
            //    return;
            //}

            if(authorization == null || authorization.isEmpty())
            {
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }

            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            //String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));
            byte[] usernameAndPasswordBytes = Base64.getDecoder().decode(encodedUserPassword);
            String usernameAndPassword = new String(usernameAndPasswordBytes);

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            if(method.isAnnotationPresent(RolesAllowed.class))
            {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                //Is user valid?
                User user = authorizeUser(username, password);
                if(!(user == null)) {
                    if(!isUserAllowed(user, rolesSet))
                    {
                        requestContext.abortWith(ACCESS_FORBIDDEN);
                        return;
                    }
                } else {
                    requestContext.abortWith(ACCESS_DENIED);
                    return;
                }

            }
        }
    }

    private User authorizeUser(final String username, final String password) {
        User authorizedUser;

        List<User> users = new JdbcTemplate(dataSource).query(
                "select name, password, role from user",
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));
                        user.setRole(rs.getString("role"));
                        return user;
                    }
                });
        for(User user : users) {
            if(user.getName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    private boolean isUserAllowed(User user, final Set<String> rolesSet)
    {
        boolean isAllowed = false;

        if(rolesSet.contains(user.getRole()))
        {
            isAllowed = true;
        }

        return isAllowed;
    }
}*/
