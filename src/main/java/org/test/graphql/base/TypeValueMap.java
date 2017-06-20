package org.test.graphql.base;

import java.util.HashMap;
import java.util.Map;

public class TypeValueMap {

	private Map<String, Object> map;

	public TypeValueMap() {
		this(null);
	}

	public TypeValueMap(Map<String, Object> map) {
		this.map = map == null ? new HashMap<>() : map;
	}

	public boolean containsKey(String name) {
		return map.containsKey(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T) this.map.get(name);
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeValueMap other = (TypeValueMap) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}
}
