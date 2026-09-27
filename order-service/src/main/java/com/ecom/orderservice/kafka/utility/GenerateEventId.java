package com.ecom.orderservice.kafka.utility;

import java.util.Random;

public class GenerateEventId {

	public static String generateEventId() {
		
		Random random = new Random();
	    int nextInt = random.nextInt(900000) + 100000;
		String eventId = "EVT-" + String.valueOf(nextInt);
		return eventId;
	}
	
	

}
