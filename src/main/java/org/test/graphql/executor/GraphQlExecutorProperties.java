package org.test.graphql.executor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "org.test.graphql.executor")
public class GraphQlExecutorProperties {

	private Integer minimumThreadPoolSize = 10;
	private Integer maximumThreadPoolSize = 20;
	private Integer keepAliveTimeInSeconds = 30;

	public Integer getMinimumThreadPoolSize() {
		return minimumThreadPoolSize;
	}

	public void setMinimumThreadPoolSize(Integer minimumThreadPoolSize) {
		this.minimumThreadPoolSize = minimumThreadPoolSize;
	}

	public Integer getMaximumThreadPoolSize() {
		return maximumThreadPoolSize;
	}

	public void setMaximumThreadPoolSize(Integer maximumThreadPoolSize) {
		this.maximumThreadPoolSize = maximumThreadPoolSize;
	}

	public Integer getKeepAliveTimeInSeconds() {
		return keepAliveTimeInSeconds;
	}

	public void setKeepAliveTimeInSeconds(Integer keepAliveTimeInSeconds) {
		this.keepAliveTimeInSeconds = keepAliveTimeInSeconds;
	}

}
