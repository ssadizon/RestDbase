package org.apache.karaf.examples.rest.websvc;

import org.apache.karaf.examples.rest.api.BookingService;

public class ServiceCatcher {
	private static BookingService _BookingService;
	
	public static void setBookingService(BookingService svc) {
		_BookingService = svc;
	}
	public static BookingService getBookingService() {
		return _BookingService;
	}
}
