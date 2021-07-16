/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.karaf.examples.rest.websvc;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.karaf.examples.rest.api.Booking;
import org.apache.karaf.examples.rest.websvc.authenticator.Authenticator;

@Path("/")
public class BookingServiceRest implements I_BookingServiceRest {

    @Context
	private HttpHeaders headers;
   
    private Authenticator authenticator;
	
    @Override
    @Path("/")
    @Produces("application/json")
    @GET
    public Response list() {
    	System.out.println("Calling BookingService.list()...");
    	String headerValue = headers.getHeaderString("Authorization");
    	authenticator = new Authenticator(headerValue);
    	if(authenticator.isAuthorized()) {
    		return Response.status(200).entity(ServiceCatcher.getBookingService().list()).build();	
    	} else {
    		return Response.status(401).build();
    	}
    }

    @Override
    @Path("/{id}")
    @Produces("application/json")
    @GET
    public Response get(@PathParam("id") Long id) {
    	System.out.println("Calling BookingService.get()...");
    	String headerValue = headers.getHeaderString("Authorization");
    	authenticator = new Authenticator(headerValue);
    	if(authenticator.isAuthorized()) {
    		return Response.status(200).entity(ServiceCatcher.getBookingService().get(id)).build();	
    	} else {
    		return Response.status(401).build();
    	}
    }
    
    @Override
    @Path("/")
    @Consumes("application/json")
    @POST
    public Response add(Booking booking) {
    	System.out.println("Calling BookingService.add()...");
    	String headerValue = headers.getHeaderString("Authorization");
    	authenticator = new Authenticator(headerValue);
    	if(authenticator.isAuthorized()) {
    		ServiceCatcher.getBookingService().add(booking);
    		return Response.status(200).build();	
    	} else {
        	return Response.status(401).build();
    	}        
    }

    @Override
    @Path("/")
    @Consumes("application/json")
    @PUT
    public Response update(Booking booking) {
    	System.out.println("Calling BookingService.update()...");
    	String headerValue = headers.getHeaderString("Authorization");
    	authenticator = new Authenticator(headerValue);
    	if(authenticator.isAuthorized()) {
            ServiceCatcher.getBookingService().update(booking);
            return Response.status(200).build();
    	} else {
        	return Response.status(401).build();
    	} 
    }

    @Override
    @Path("/{id}")
    @DELETE
    public Response remove(@PathParam("id") Long id) {
    	System.out.println("Calling BookingService.update()...");
    	String headerValue = headers.getHeaderString("Authorization");
    	authenticator = new Authenticator(headerValue);
    	if(authenticator.isAuthorized()) {
            ServiceCatcher.getBookingService().remove(id);
            return Response.status(200).build();
    	} else {
        	return Response.status(401).build();
    	} 
    }
}
