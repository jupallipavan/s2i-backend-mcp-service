package com.snap2insight.s2i_mcp_service.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

@Service
public class S2IBackendMcpServer{

	private static final String BASE_URL = "https://dev.snap2insight.com/api/Snap2Buy-3.0/service/S2B/db";
	private static final String USER_AGENT = "com.snap2insight.app - 5.3.3.dev(12)";

	private final RestClient restClient;

	public S2IBackendMcpServer() {
		this.restClient = RestClient.builder()
			.baseUrl(BASE_URL)
			.defaultHeader("accept", "application/json, text/plain, */*")
			.defaultHeader("user-agent", USER_AGENT)
			.defaultHeader("platform", "ios")
			.defaultHeader("platformflavour", "react-native")
			.defaultHeader("userlocale", "en")
			.defaultHeader("SkipAuthorization", "InternalCall")
			.defaultHeader("Content-Type", "text/plain")
			.build();
	}


	/**
	 * Execute a SELECT query against the database
	 * @param query The SQL SELECT query to execute
	 * @return The query results in a formatted string
	 * @throws RestClientException if the request fails
	 */
	@Tool(description = "Executes Mysql Read only queries.Convert output to CSV file while showing")
	public String executeQuery(String query) {
		try {
			return	restClient.post()
				.uri("/queryresult").body(query).retrieve().body(String.class);
		} catch (RestClientException e) {
			return String.format("Database query failed: %s", e.getMessage());
		}
	}

	/**
	 * Get all table names from the database
	 * @return List of all tables in the database
	 * @throws RestClientException if the request fails
	 */
	@Tool(description = "Get all table names available in the database. returns comma seperated table names.")
	public String getAllTables() {
		try {
			return	restClient.get()
				.uri("/tables").retrieve().body(String.class);
		} catch (RestClientException e) {
			return String.format("Database query failed: %s", e.getMessage());
		}
	}

	/**
	 * Get the schema information for a specific table
	 * @param tableName The name of the table to get schema for
	 * @return Schema information including column names, data types, etc.
	 * @throws RestClientException if the request fails
	 */
	@Tool(description = "Get schema information for a specific table including column names, data types, and constraints by passing the table name to this tool")
	public String getTableSchema(String tableName) {
		try {
			return	restClient.get()
				.uri("/schema?tableName=projectstoreresult").retrieve().body(String.class);
		} catch (RestClientException e) {
			return String.format("Database query failed: %s", e.getMessage());
		}
	}
	

	@Tool(description = "Provides Our Company (Snap2Insight) Backend Infra and db schema. It gives the context to solve the user requests")
	public String getS2IContext() {
		File f = new File("/Users/pavan/Downloads/claude/S2I_BACKEND_AND_DATABASE_TRAINING.txt");
		FileInputStream fis = null;
		String result = "File Not Found:error";
		try {
			fis = new FileInputStream(f);
			result= new String(fis.readAllBytes());
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Tool(description = "allows claude to read the java/resource file from the snap2insight code base.it's a maven project.the param should starts with src/main.. and ends with file name you want to read, except the pom.xml file. ")
	public String getS2IJavaCodeFromFile(String fileName) {
		String basepath="/Users/pavan/eclipse-workspace/s2i-services/Snap2Pay/";
		File f = new File(basepath+fileName);
		FileInputStream fis = null;
		String result = "File Not Found:error";
		try {
			fis = new FileInputStream(f);
			result= new String(fis.readAllBytes());
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Tool(description = "allows claude to read the files heirarchy from the snap2insight code base.it's a maven project.It helps the claude to know the exact path to the file to read the contents of that file. ")
	public String getS2IServicesFilesHeirarchy() {
		File f = new File("/Users/pavan/Downloads/claude/S2I_SERVICES_FILES_HIERARCHY.txt");
		FileInputStream fis = null;
		String result = "File Not Found:error";
		try {
			fis = new FileInputStream(f);
			result= new String(fis.readAllBytes());
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
