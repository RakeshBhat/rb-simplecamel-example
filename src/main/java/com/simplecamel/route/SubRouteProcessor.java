package com.simplecamel.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SubRouteProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);
		String tst = body + "newProcessor";
		System.out.println("SubRouteProcessor Body: "+ tst);
		exchange.getOut().setBody(body);	}

}
