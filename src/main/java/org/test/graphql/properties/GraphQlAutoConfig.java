package org.test.graphql.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.test.graphql.controller.GraphQlController;
import org.test.graphql.executor.GraphQlExecutor;
import org.test.graphql.executor.GraphQlExecutorProperties;
import org.test.graphql.schema.GraphQlSchemaBuilder;

@Configuration
@EnableConfigurationProperties({
        GraphQlProperties.class,
        GraphQlExecutorProperties.class
})
@Import({
        GraphQlExecutor.class,
        GraphQlSchemaBuilder.class,
        GraphQlController.class
})
public class GraphQlAutoConfig {

}
