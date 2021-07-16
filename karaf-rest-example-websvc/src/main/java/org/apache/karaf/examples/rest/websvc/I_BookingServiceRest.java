package org.apache.karaf.examples.rest.websvc;

import javax.ws.rs.core.Response;

import org.apache.karaf.examples.rest.api.Booking;

public interface I_BookingServiceRest {
	
	Response list();
	
	Response get(Long id);

	Response add(Booking booking);
    
	Response update(Booking booking);
    
	Response remove(Long id);	

}
