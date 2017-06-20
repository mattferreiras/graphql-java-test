package org.test.graphql.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.test.graphql.base.TypeValueMap;

@Component
public class UserDataFetcher {
	
	public Map<Long, User> users = new HashMap<>();

    public List<User> getUsersByFilter(TypeValueMap arguments) {
        Integer id = arguments.get("id");

        if (id != null) {
            return Collections.singletonList(users.get(id));
        } else {
            return new ArrayList<>(users.values());
        }
    }

    public User addUser(TypeValueMap arguments) {
        User user = new User();

        user.setId(arguments.get("id"));
        user.setName(arguments.get("name"));

        users.put(user.getId(), user);

        return user;
    }

    public User updateUser(TypeValueMap arguments) {
        User user = users.get(arguments.get("id"));

        if (arguments.containsKey("name")) {
            user.setName(arguments.get("name"));
        }

        return user;
    }

    public User deleteUser(TypeValueMap arguments) {
        User user = users.get(arguments.get("id"));

        users.remove(user.getId());

        return user;
    }

}
