package com.carsoline.configs;

import org.springframework.context.annotation.Configuration;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

/**
 * Created by Dominik on 2017-05-09.
 */
//public class CorsResponseFilter
//        implements ContainerResponseFilter {
//
//    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
//            throws IOException {
//
//        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
//
//        headers.add("Access-Control-Allow-Origin", "*");
//        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
//        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
//    }
//}

