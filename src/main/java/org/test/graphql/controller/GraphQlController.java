package org.test.graphql.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.test.graphql.executor.GraphQlExecutor;

@Controller
@RequestMapping("/graphql")
public class GraphQlController {
	
	@Autowired
	private GraphQlExecutor executor;
	
	@PostMapping
	public Object postGraphQl(@RequestBody Map<String, Object> requestBody) {
		return executor.executeRequest(requestBody);
	}

}
