package com.specification.examples;

//Static Imports
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
public class ResponseSpecificationExample {

	
	static RequestSpecBuilder builder;
	static RequestSpecification rspec;
	
	static ResponseSpecBuilder responsebuilder;
	static ResponseSpecification responseSpec;

	static Map<String,Object> responseHeaders = new HashMap<String,Object>();

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "https://query.yahooapis.com";
		RestAssured.basePath = "/v1/public";
		
		builder = new RequestSpecBuilder();
		
		builder.addParam("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")");
		builder.addParam("format","json");
		builder.addParam("env","store://datatables.org/alltableswithkeys");
		
		rspec= builder.build();
		
		//Building response
		responseHeaders.put("Content-Type","application/json;charset=utf-8");
		responseHeaders.put("Server","ATS");
		
		responsebuilder= new ResponseSpecBuilder();
		responsebuilder.expectBody("query.count",equalTo(6));
		responsebuilder.expectStatusCode(200);
		responsebuilder.expectHeaders(responseHeaders);
		
		responseSpec= responsebuilder.build();
		
	
	}

	// 1) Assert on count value
	@Test
	public void test001() {
		given()
		.spec(rspec)
		.log()
		.all()
		.when()
		.get("/yql")
		.then()
		.spec(responseSpec);
	}

}
