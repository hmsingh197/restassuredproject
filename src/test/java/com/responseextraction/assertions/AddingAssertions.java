package com.responseextraction.assertions;




import java.util.HashMap;

import io.restassured.RestAssured;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;





//Static Imports
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;

import org.junit.BeforeClass;


public class AddingAssertions {
	static HashMap<String,Object> parameters = new HashMap<String,Object>();

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "https://query.yahooapis.com";
		RestAssured.basePath = "/v1/public";
		
		
		parameters.put("q","select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")");
		parameters.put("format","json");
		parameters.put("env","store://datatables.org/alltableswithkeys");

	}

	// 1) Assert on count value
	@Test
	public void test001() {
		given()
		.parameters(parameters)
		.when()
		.get("/yql")
		.then()
		.body("query.count", equalTo(6));


	}

	// 2 Assert on Single Name
	@Test
	public void test002() {
		given()
		.parameters(parameters)
		.when()
		.get("/yql")
		.then()
		.body("query.results.rate.Name", hasItem("USD/INR"));
	}

	// 3 Assert on Multiple Names
	@Test
	public void test003() {
		given()
		.parameters(parameters)
		.when()
		.get("/yql")
		.then()
		.body("query.results.rate.Name", hasItems("USD/INR","USD/THB","USD/BRL"));
	}

	// 4 Assert using logical function
	@Test
	public void test004() {
		given()
		.parameters(parameters)
		.when()
		.get("/yql")
		.then()
		.body("query.count",greaterThan(4));
		
		given()
		.parameters(parameters)
		.when()
		.get("/yql")
		.then()
		.body("query.count",lessThan(10));
	}

	// 5 Adding multiple assertions in single test
	
	@Test
	public void test005() {
		given()
		.parameters(parameters)
		.when()
		.get("/yql")
		.then()
		.body("query.results.rate.Name", hasItem("USD/INR"))
		.body("query.count",lessThan(10))
		.body("query.count",greaterThan(4))
		.body("query.results.rate.Name", hasItems("USD/INR","USD/THB","USD/BRL"));
		
	}
	
	@Test
	public void test006(){
int count=		given()
		.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
		.param("format", "json")
		.param("env","store://datatables.org/alltableswithkeys")
		.param("diagnostics","true")
		.when()
		.get("/yql")
		.then()
		.extract()
		.path("query.count");
	assertEquals(6, count);
	}

}
