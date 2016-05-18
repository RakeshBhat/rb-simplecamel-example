package com.simplecamel.route;

import org.apache.camel.builder.RouteBuilder;

public class ReDeliverSubRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		errorHandler(deadLetterChannel("mock:error")
				.logExhaustedMessageBody(true)
				.logExhausted(true)
				.logRetryStackTrace(true)
				.maximumRedeliveries(3)
				.redeliveryDelay(1000)
				.backOffMultiplier(2)
				.useOriginalMessage()
				.useExponentialBackOff());
		
		from("direct:start")
		.log("Dump incoming" + body())
		.to("direct:sub")
		.end();
		
		from("direct:sub")
		.errorHandler(noErrorHandler())
		.process(new SubRouteProcessor())
		.log("Dump incoming body "+ body())
		.process(new NewSubRouteProcessor())
		.transform(body().append("Modified Data !"))
		.to("mock:result");
	}

}
