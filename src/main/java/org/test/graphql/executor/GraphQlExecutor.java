package org.test.graphql.executor;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.test.graphql.schema.GraphQlSchemaBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLException;
import graphql.execution.ExecutionStrategy;
import graphql.execution.ExecutorServiceExecutionStrategy;

@Component
public class GraphQlExecutor {

	@Autowired
	private GraphQlExecutorProperties properties;

	@Autowired
	private ObjectMapper jackson;

	@Autowired
	private GraphQlSchemaBuilder schema;

	private TypeReference<HashMap<String, Object>> typeRefReadJsonString = new TypeReference<HashMap<String, Object>>() {
	};

	private GraphQL graphQL;

	@PostConstruct
	private void postConstruct() {
		graphQL = new GraphQL(schema.getSchema(), createExecutionStrategy());
	}

	protected ExecutionStrategy createExecutionStrategy() {
		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>() {
			private static final long serialVersionUID = 5970583268783000817L;

			@Override
			public boolean offer(Runnable e) {
				/* queue that always rejects tasks */
				return false;
			}
		};

		return new ExecutorServiceExecutionStrategy(new ThreadPoolExecutor(
				properties.getMinimumThreadPoolSize(), /* core pool size 2 thread */
				properties.getMaximumThreadPoolSize(), /* max pool size 2 thread */
				properties.getKeepAliveTimeInSeconds(), TimeUnit.SECONDS,
				/*
				 * Do not use the queue to prevent threads waiting on enqueued
				 * tasks.
				 */
				queue,
				/*
				 * If all the threads are working, then the caller thread should
				 * execute the code in its own thread. (serially)
				 */
				new CustomizableThreadFactory("graphql-thread-"), new ThreadPoolExecutor.CallerRunsPolicy()));
	}
	
	public Object executeRequest(Map<String, Object> requestBody) {
        String query = (String) requestBody.get("query");
        String operationName = (String) requestBody.get("operationName");
        Map<String, Object> variables = getVariablesFromRequest(requestBody);
        Map<String, Object> context = new HashMap<String, Object>();

        ExecutionResult executionResult = graphQL.execute(query, operationName, context, variables);

        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();

        if (executionResult.getErrors().size() > 0) {
            result.put("errors", executionResult.getErrors());
        }
        result.put("data", executionResult.getData());

        return result;
    }

    @SuppressWarnings("unchecked")
	private Map<String, Object> getVariablesFromRequest(Map<String, Object> requestBody) {
        Object variablesFromRequest = requestBody.get("variables");

        if (variablesFromRequest == null) {
            return Collections.emptyMap();
        }

        if (variablesFromRequest instanceof String) {
            if (StringUtils.hasText((String) variablesFromRequest)) {
                return getVariablesMapFromString((String) variablesFromRequest);
            }
        } else if (variablesFromRequest instanceof Map) {
            return (Map<String, Object>) variablesFromRequest;
        } else {
            throw new GraphQLException("Incorrect variables");
        }

        return Collections.emptyMap();
    }

    private Map<String, Object> getVariablesMapFromString(String variablesFromRequest) {
        try {
            return jackson.readValue(variablesFromRequest, typeRefReadJsonString);
        } catch (IOException exception) {
            throw new GraphQLException("Cannot parse variables", exception);
        }
    }

}
