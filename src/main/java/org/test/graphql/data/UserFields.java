package org.test.graphql.data;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;
import static org.test.graphql.base.GraphQlFieldsHelper.FILTER;
import static org.test.graphql.base.GraphQlFieldsHelper.INPUT;
import static org.test.graphql.base.GraphQlFieldsHelper.getFilterMap;
import static org.test.graphql.base.GraphQlFieldsHelper.getInputMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.test.graphql.properties.GraphQlFields;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;

@Component
public class UserFields implements GraphQlFields {

	@Autowired
	private UserDataFetcher userDataFetcher;

	private GraphQLObjectType userType;

	private GraphQLInputObjectType addUserInputType;
	private GraphQLInputObjectType updateUserInputType;
	private GraphQLInputObjectType deleteUserInputType;
	private GraphQLInputObjectType filterUserInputType;

	private GraphQLFieldDefinition usersField;
	private GraphQLFieldDefinition addUserField;
	private GraphQLFieldDefinition updateUserField;
	private GraphQLFieldDefinition deleteUserField;

	private List<GraphQLFieldDefinition> queryFields;
	private List<GraphQLFieldDefinition> mutationFields;

	@PostConstruct
	public void postConstruct() {
		createTypes();
		createFields();
		queryFields = Collections.singletonList(usersField);
		mutationFields = Arrays.asList(addUserField, updateUserField, deleteUserField);
	}

	public UserDataFetcher getUserDataFetcher() {
		return userDataFetcher;
	}

	public void setUserDataFetcher(UserDataFetcher userDataFetcher) {
		this.userDataFetcher = userDataFetcher;
	}

	public GraphQLObjectType getUserType() {
		return userType;
	}

	public void setUserType(GraphQLObjectType userType) {
		this.userType = userType;
	}

	public GraphQLInputObjectType getAddUserInputType() {
		return addUserInputType;
	}

	public void setAddUserInputType(GraphQLInputObjectType addUserInputType) {
		this.addUserInputType = addUserInputType;
	}

	public GraphQLInputObjectType getUpdateUserInputType() {
		return updateUserInputType;
	}

	public void setUpdateUserInputType(GraphQLInputObjectType updateUserInputType) {
		this.updateUserInputType = updateUserInputType;
	}

	public GraphQLInputObjectType getDeleteUserInputType() {
		return deleteUserInputType;
	}

	public void setDeleteUserInputType(GraphQLInputObjectType deleteUserInputType) {
		this.deleteUserInputType = deleteUserInputType;
	}

	public GraphQLInputObjectType getFilterUserInputType() {
		return filterUserInputType;
	}

	public void setFilterUserInputType(GraphQLInputObjectType filterUserInputType) {
		this.filterUserInputType = filterUserInputType;
	}

	public GraphQLFieldDefinition getUsersField() {
		return usersField;
	}

	public void setUsersField(GraphQLFieldDefinition usersField) {
		this.usersField = usersField;
	}

	public GraphQLFieldDefinition getAddUserField() {
		return addUserField;
	}

	public void setAddUserField(GraphQLFieldDefinition addUserField) {
		this.addUserField = addUserField;
	}

	public GraphQLFieldDefinition getUpdateUserField() {
		return updateUserField;
	}

	public void setUpdateUserField(GraphQLFieldDefinition updateUserField) {
		this.updateUserField = updateUserField;
	}

	public GraphQLFieldDefinition getDeleteUserField() {
		return deleteUserField;
	}

	public void setDeleteUserField(GraphQLFieldDefinition deleteUserField) {
		this.deleteUserField = deleteUserField;
	}

	public void setQueryFields(List<GraphQLFieldDefinition> queryFields) {
		this.queryFields = queryFields;
	}

	public void setMutationFields(List<GraphQLFieldDefinition> mutationFields) {
		this.mutationFields = mutationFields;
	}

	@Override
	public List<GraphQLFieldDefinition> getQueryFields() {
		return queryFields;
	}

	@Override
	public List<GraphQLFieldDefinition> getMutationFields() {
		return mutationFields;
	}

	private void createTypes() {
		userType = newObject().name("user").description("A user").field(newFieldDefinition().name("id").description("The id").type(GraphQLInt).build())
				.field(newFieldDefinition().name("name").description("The network name").type(GraphQLString).build()).build();

		addUserInputType = newInputObject().name("addUserInput").field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLInt)).build())
				.field(newInputObjectField().name("name").type(new GraphQLNonNull(Scalars.GraphQLString)).build()).build();

		updateUserInputType = newInputObject().name("updateUserInput").field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLInt)).build()).field(newInputObjectField().name("name").type(GraphQLString).build()).build();

		deleteUserInputType = newInputObject().name("deleteUserInput").field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLInt)).build()).build();

		filterUserInputType = newInputObject().name("filterUserInput").field(newInputObjectField().name("id").type(GraphQLString).build()).build();
	}

	private void createFields() {
		usersField = newFieldDefinition().name("users").description("Provide an overview of all users").type(new GraphQLList(userType)).argument(newArgument().name(FILTER).type(filterUserInputType).build())
				.dataFetcher(environment -> userDataFetcher.getUsersByFilter(getFilterMap(environment))).build();

		addUserField = newFieldDefinition().name("addUser").description("Add new user").type(userType).argument(newArgument().name(INPUT).type(new GraphQLNonNull(addUserInputType)).build())
				.dataFetcher(environment -> userDataFetcher.addUser(getInputMap(environment))).build();

		updateUserField = newFieldDefinition().name("updateUser").description("Update existing user").type(userType).argument(newArgument().name(INPUT).type(new GraphQLNonNull(updateUserInputType)).build())
				.dataFetcher(environment -> userDataFetcher.updateUser(getInputMap(environment))).build();

		deleteUserField = newFieldDefinition().name("deleteUser").description("Delete existing user").type(userType).argument(newArgument().name(INPUT).type(new GraphQLNonNull(deleteUserInputType)).build())
				.dataFetcher(environment -> userDataFetcher.deleteUser(getInputMap(environment))).build();
	}
}
