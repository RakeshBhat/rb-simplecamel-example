package com.simplecamel.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class NewSubRouteProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);
		String tst = body + "newProcessor";
		System.out.println("NewSubRouteProcessor Body: "+ tst);
		exchange.getOut().setBody(body);
	}

}
