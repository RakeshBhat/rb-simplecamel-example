package com.simplecamel.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SubRouteProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);
		body = body + "newProcessor";
		System.out.println("SubRouteProcessor Body: "+ body);
		exchange.getOut().setBody(body);	}

}
