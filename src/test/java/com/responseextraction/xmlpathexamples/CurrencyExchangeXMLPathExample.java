package com.responseextraction.xmlpathexamples;
import static org.junit.Assert.assertEquals;
import io.restassured.RestAssured;
import io.restassured.RestAssured;
import io.restassured.internal.path.xml.NodeChildrenImpl;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.*;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.path.xml.XmlPath.*;


public class CurrencyExchangeXMLPathExample {

	

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "https://query.yahooapis.com";
		RestAssured.basePath = "/v1/public";
	}

	// 1) Extract count value from the response
	@Test
	public void test001() {
	String count=	given()
		.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
		.param("format", "xml")
		.param("env","store://datatables.org/alltableswithkeys")
		.param("diagnostics","true")
		.when()
		.get("/yql")
		.then()
		.extract()
		.path("query.@yahoo:count");
	
	System.out.println("The count is: "+count);
		
	}

	// 2) Extract lang value from the response
	@Test
	public void test002() {
		
		String lang=	given()
				.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
				.param("format", "xml")
				.param("env","store://datatables.org/alltableswithkeys")
				.param("diagnostics","true")
				.when()
				.get("/yql")
				.then()
				.extract()
				.path("query.@yahoo:lang");
			
			System.out.println("The lang is: "+lang);
	}

	// 3)Extract "Name": "USD/THB" from the first rate element
	@Test
	public void test003() {
		
		String name=	given()
				.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
				.param("format", "xml")
				.param("env","store://datatables.org/alltableswithkeys")
				.param("diagnostics","true")
				.when()
				.get("/yql")
				.then()
				.extract()
				.path("query.results.rate[0].Name");
			
			System.out.println("The name is: "+name);
	}
	// 4) Get the values under rate as String
	@Test
	public void test004() {
	String xml=	given()
		.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
		.param("format", "xml")
		.param("env","store://datatables.org/alltableswithkeys")
		.param("diagnostics","true")
		.when()
		.get("/yql")
		.andReturn()
		.asString();
	
	String valuesUnderRate = with(xml).get("query.results.rate").toString();
	
	System.out.println("The values under rate are: "+valuesUnderRate);
	}
	

	// 5)Get the size of rate
	@Test
	public void test005() {
	NodeChildrenImpl sizeOfNode=	given()
		.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
		.param("format", "xml")
		.param("env","store://datatables.org/alltableswithkeys")
		.param("diagnostics","true")
		.when()
		.get("/yql")
		.then()
		.extract()
		.path("query.results.rate");
	System.out.println("The size of rate is: "+sizeOfNode.size());
	}

	// 6) Get All the Names from the Response as String
	@Test
	public void test006() {
		String xml=	given()
				.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
				.param("format", "xml")
				.param("env","store://datatables.org/alltableswithkeys")
				.param("diagnostics","true")
				.when()
				.get("/yql")
				.andReturn()
				.asString();
		String names= with(xml).get("query.results.rate.findAll{it.Name}.Name").toString();
		
		System.out.println("The names are: "+names);
			
	}

	// 7) Get all the info for name USD/AUD
	@Test
	public void test007() {
		
		String xml=	given()
				.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
				.param("format", "xml")
				.param("env","store://datatables.org/alltableswithkeys")
				.param("diagnostics","true")
				.when()
				.get("/yql")
				.andReturn()
				.asString();
		String values= with(xml).get("**.findAll{it.Name=='USD/AUD'}").toString();
		
		System.out.println("The values are: "+values);
	}

	// 8) Extracting using attr id="INR=X" and getting the Rate..using **
	@Test
	public void test008() {
		String xml=	given()
				.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
				.param("format", "xml")
				.param("env","store://datatables.org/alltableswithkeys")
				.param("diagnostics","true")
				.when()
				.get("/yql")
				.andReturn()
				.asString();
		String values= with(xml).get("**.findAll{it.@id=='USDINR'}.Rate").toString();
		System.out.println("The Rate is "+values);
	}

}
