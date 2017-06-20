package org.test.graphql.base;

import java.util.Map;

import graphql.schema.DataFetchingEnvironment;

public class GraphQlFieldsHelper {
    public static String INPUT = "input";
    public static String FILTER = "filter";

    public static TypeValueMap getInputMap(DataFetchingEnvironment environment) {
        return new TypeValueMap(environment.getArgument(INPUT));
    }

    public static TypeValueMap getFilterMap(DataFetchingEnvironment environment) {
        Map<String, Object> filterMap = environment.getArgument(FILTER);

        if (filterMap == null) {
            return new TypeValueMap();
        }

        return new TypeValueMap(filterMap);
    }
}

