package org.test.graphql.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "org.test.graphql.properties")
public class GraphQlProperties {

	private String rootQueryName = "queries";
	private String rootQueryDescription = "";
	private String rootMutationName = "mutations";
	private String rootMutationDescription = "";

	public String getRootQueryName() {
		return rootQueryName;
	}

	public void setRootQueryName(String rootQueryName) {
		this.rootQueryName = rootQueryName;
	}

	public String getRootQueryDescription() {
		return rootQueryDescription;
	}

	public void setRootQueryDescription(String rootQueryDescription) {
		this.rootQueryDescription = rootQueryDescription;
	}

	public String getRootMutationName() {
		return rootMutationName;
	}

	public void setRootMutationName(String rootMutationName) {
		this.rootMutationName = rootMutationName;
	}

	public String getRootMutationDescription() {
		return rootMutationDescription;
	}

	public void setRootMutationDescription(String rootMutationDescription) {
		this.rootMutationDescription = rootMutationDescription;
	}
}
