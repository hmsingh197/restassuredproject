package com.fileupload.example;


//Static imports
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.junit.Test;

public class FileUploadLoadExample {

	/**
	 * Upload a gif file to zamzar.com and convert it into a png file
	 */
	@Test
	public void uploadFileExample(){

	//PUT YOU API KEY HERE
	String apiKey="";
	
		File inputFile = new File(System.getProperty("user.dir")+File.separator+"dancing_banana.gif");
		System.out.println(inputFile.length());
		String endpoint = "https://sandbox.zamzar.com/v1/jobs";
		
		given()
		.auth()
		.basic(apiKey,"")
		.multiPart("source_file",inputFile)
		.multiPart("target_format","png")
		.when()
		.post(endpoint)
		.then()
		.log()
		.all();
	}
}
