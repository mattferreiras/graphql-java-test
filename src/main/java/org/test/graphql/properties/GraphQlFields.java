package org.test.graphql.properties;

import java.util.List;

import graphql.schema.GraphQLFieldDefinition;

public interface GraphQlFields {
	
	List<GraphQLFieldDefinition> getQueryFields();
    List<GraphQLFieldDefinition> getMutationFields();

}
