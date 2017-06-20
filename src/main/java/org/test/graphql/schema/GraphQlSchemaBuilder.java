package org.test.graphql.schema;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.test.graphql.properties.GraphQlFields;
import org.test.graphql.properties.GraphQlProperties;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

@Component
public class GraphQlSchemaBuilder {

	@Autowired
	private GraphQlProperties properties;

	@Autowired
	private List<GraphQlFields> graphQlFields;

	private GraphQLSchema schema;

	@PostConstruct
	public void postConstruct() {
		GraphQLObjectType.Builder queryBuilder = newObject().name(properties.getRootQueryName());
		GraphQLObjectType.Builder mutationBuilder = newObject().name(properties.getRootMutationName());

		if (StringUtils.hasText(properties.getRootQueryDescription())) {
			queryBuilder = queryBuilder.description(properties.getRootQueryDescription());
		}

		if (StringUtils.hasText(properties.getRootMutationDescription())) {
			mutationBuilder = mutationBuilder.description(properties.getRootMutationDescription());
		}

		buildSchemaFromDefinitions(queryBuilder, mutationBuilder);
	}

	public GraphQLSchema getSchema() {
		return schema;
	}

	private void buildSchemaFromDefinitions(GraphQLObjectType.Builder queryBuilder, GraphQLObjectType.Builder mutationBuilder) {
		boolean foundQueryDefinitions = false;
		boolean foundMutationDefinitions = false;

		for (GraphQlFields graphQlField : graphQlFields) {
			List<GraphQLFieldDefinition> queryFields = graphQlField.getQueryFields();

			if (queryFields != null && queryFields.size() > 0) {
				queryBuilder = queryBuilder.fields(queryFields);
				foundQueryDefinitions = true;
			}

			List<GraphQLFieldDefinition> mutationFields = graphQlField.getMutationFields();

			if (mutationFields != null && mutationFields.size() > 0) {
				mutationBuilder = mutationBuilder.fields(mutationFields);
				foundMutationDefinitions = true;
			}
		}

		buildSchema(queryBuilder, mutationBuilder, foundQueryDefinitions, foundMutationDefinitions);
	}

	private void buildSchema(GraphQLObjectType.Builder queryBuilder, GraphQLObjectType.Builder mutationBuilder, boolean foundQueryDefinitions, boolean foundMutationDefinitions) {
		GraphQLSchema.Builder schemaBuilder = GraphQLSchema.newSchema();

		if (foundQueryDefinitions) {
			schemaBuilder = schemaBuilder.query(queryBuilder.build());
		}

		if (foundMutationDefinitions) {
			schemaBuilder = schemaBuilder.mutation(mutationBuilder.build());
		}

		if (foundQueryDefinitions || foundMutationDefinitions) {
			schema = schemaBuilder.build();
		} else {
			schema = generateGettingStartedGraphQlSchema();
		}
	}

	@SuppressWarnings("unchecked")
	private GraphQLSchema generateGettingStartedGraphQlSchema() {
		List<GraphQLFieldDefinition> staticValue = (List<GraphQLFieldDefinition>) newFieldDefinition().type(GraphQLString).name("gettingStarted").staticValue("Create a component and implement GraphQlFields interface.");
		GraphQLObjectType gettingStartedType = newObject().name("gettingStartedQuery").fields(staticValue).build();

		return GraphQLSchema.newSchema().query(gettingStartedType).build();
	}

}
