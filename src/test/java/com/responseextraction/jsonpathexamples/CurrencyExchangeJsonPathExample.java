package com.responseextraction.jsonpathexamples;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;



public class CurrencyExchangeJsonPathExample {

	
	@BeforeClass
	public static void init(){
		RestAssured.baseURI="https://query.yahooapis.com";
		RestAssured.basePath="/v1/public";
	}
	//1) Extract count value from the response
		@Test
		public void test001(){
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
	System.out.println("The value of count is:"+count);
		}
		
	//2) Extract lang value from the response
		@Test
		public void test002(){
			
			String lang=		given()
					.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
					.param("format", "json")
					.param("env","store://datatables.org/alltableswithkeys")
					.param("diagnostics","true")
					.when()
					.get("/yql")
					.then()
					.extract()
					.path("query.lang");
			System.out.println("The value of lang is:"+lang);
		}
			
	//3)Extract "Name": "USD/THB" from the first rate array
		@Test
		public void test003(){
			
			String name=		given()
					.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
					.param("format", "json")
					.param("env","store://datatables.org/alltableswithkeys")
					.param("diagnostics","true")
					.when()
					.get("/yql")
					.then()
					.extract()
					.path("query.results.rate[0].Name");
			System.out.println("The value of name is:"+name);
		}
			
	//4) Get the values under rate
		@Test
		public void test004(){
			

			List<String> rateValues=		given()
					.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
					.param("format", "json")
					.param("env","store://datatables.org/alltableswithkeys")
					.param("diagnostics","true")
					.when()
					.get("/yql")
					.then()
					.extract()
					.path("query.results.rate");
			System.out.println("The rate values are : "+rateValues);
			System.out.println("The size of rate is : "+rateValues.size());
		
		}
			
	//5)Get the size of rate
		@Test
		public void test005(){
			int sizeOfRate=		given()
					.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
					.param("format", "json")
					.param("env","store://datatables.org/alltableswithkeys")
					.param("diagnostics","true")
					.when()
					.get("/yql")
					.then()
					.extract()
					.path("query.results.rate.size()");
			System.out.println("The size of rate is : "+sizeOfRate);
		}
		
	//6) Get All the Names from the Response
		@Test
		public void test006(){
			
			List<String> names=		given()
					.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
					.param("format", "json")
					.param("env","store://datatables.org/alltableswithkeys")
					.param("diagnostics","true")
					.when()
					.get("/yql")
					.then()
					.extract()
					.path("query.results.rate.Rate");
			System.out.println("The values of Names are : "+names);
			
		}
		
	//7) Get the all the values for Name==USD/BRL
		@Test
		public void test007(){	
			
			List<String> values=		given()
					.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")")
					.param("format", "json")
					.param("env","store://datatables.org/alltableswithkeys")
					.param("diagnostics","true")
					.when()
					.get("/yql")
					.then()
					.extract()
					.path("query.results.rate.findAll{it.Name=='USD/BRL'}");
			System.out.println("The values  are : "+values);
		}
		
	//8) Get the rate value for Name==USD/EUR
		@Test
		public void test008(){
			
			List<String> values=		given()
					.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\",\"USDEUR\")")
					.param("format", "json")
					.param("env","store://datatables.org/alltableswithkeys")
					.param("diagnostics","true")
					.when()
					.get("/yql")
					.then()
					.extract()
					.path("query.results.rate.findAll{it.Name=='USD/EUR'}");
			System.out.println("The values  for Name USD/EUR are : "+values);
		}
			
	//9) Get the Names which have Rate greater than 10
		@Test
		public void test009(){
			
		Response response=	given()
			.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\",\"USDEUR\")")
			.param("format", "json")
			.param("env","store://datatables.org/alltableswithkeys")
			.param("diagnostics","true")
			.when()
			.get("/yql");
		
	List<String> names=	response
		.then()
		.extract()
		.path("query.results.rate.findAll{it.Rate>'30'}.Name");
			
		}
	//10) Get values that Start with id =USDB
		@Test
		public void test010(){
			List<String> values=		given()
					.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\",\"USDEUR\")")
					.param("format", "json")
					.param("env","store://datatables.org/alltableswithkeys")
					.param("diagnostics","true")
					.when()
					.get("/yql")
					.then()
					.extract()
					.path("query.results.rate.findAll{it.id==~/USDB.*/}");
			System.out.println("The values  that start with USDB are: "+values);
			
		}
	//11) Get values that End with id UD
		@Test
		public void test011(){
			
			List<String> values=		given()
					.param("q", "select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\",\"USDEUR\")")
					.param("format", "json")
					.param("env","store://datatables.org/alltableswithkeys")
					.param("diagnostics","true")
					.when()
					.get("/yql")
					.then()
					.extract()
					.path("query.results.rate.findAll{it.id==~/.*UD/}");
			System.out.println("The values  that end with id: UD are: "+values);
		}
	
}
