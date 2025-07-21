package com.snap2insight.s2i_mcp_service;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.snap2insight.s2i_mcp_service.service.S2IBackendMcpServer;

@SpringBootApplication
public class S2iMcpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(S2iMcpServiceApplication.class, args);
	}
	
	
	@Bean
	public ToolCallbackProvider weatherTools(S2IBackendMcpServer s2iBackendMcpServer) {
		return MethodToolCallbackProvider.builder().toolObjects(s2iBackendMcpServer).build();
	}

	public record TextInput(String input) {
	}

	@Bean
	public ToolCallback toUpperCase() {
		return FunctionToolCallback.builder("toUpperCase", (TextInput input) -> input.input().toUpperCase())
			.inputType(TextInput.class)
			.description("Put the text to upper case")
			.build();
	}

}
