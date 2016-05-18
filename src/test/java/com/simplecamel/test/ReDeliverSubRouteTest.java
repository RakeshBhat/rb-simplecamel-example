package com.simplecamel.test;

import static org.junit.Assert.*;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import com.simplecamel.route.ReDeliverSubRoute;

public class ReDeliverSubRouteTest extends CamelTestSupport{

 
	protected RouteBuilder createRouteBuilder() throws Exception{
		return new ReDeliverSubRoute();
	}
	
	@Produce(uri = "direct:start")
	protected ProducerTemplate pTemplate;
	
	@EndpointInject(uri = "mock:error")
	protected MockEndpoint mockError;
	
	@EndpointInject(uri = "mock:result")
	protected MockEndpoint mockResult;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		
		mockResult.whenAnyExchangeReceived(new Processor(){

			@Override
			public void process(Exchange exchange) throws Exception {
				String body = exchange.getIn().getBody(String.class);
				
				if(body.startsWith("FAIL ALWAYS")){
					throw new Exception("Error publishing message");
				}	
			}
			
		});
	}
	
	@Test
	public void failingMessage() throws Exception{
		mockResult.expectedMessageCount(4);
		mockError.expectedMessageCount(1);
		mockError.expectedBodiesReceived("FAIL ALWAYS");
		
		long startTime = System.currentTimeMillis();
		pTemplate.sendBody("FAIL ALWAYS");
		assertMockEndpointsSatisfied();
		
		long endTime = System.currentTimeMillis();
		
		assertTrue(endTime - startTime > 7000);
	}
}
