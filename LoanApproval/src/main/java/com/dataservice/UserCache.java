package com.dataservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.model.User;

/**
 * Class containing mock user data
 * @author anupama
 *
 */
public class UserCache {
	HashMap<Integer,User> users = new HashMap<Integer,User>();
	private static final Logger logger = LoggerFactory.getLogger(UserCache.class);
	
	public UserCache()
	{
		initializeCache();
	}
	
	private void initializeCache() {
		int userId = 0;

		for(int i=0; i<10;i++) {
			userId++;
			users.put(userId, new User(userId, User.UserType.UW));
			userId++;
			users.put(userId, new User(userId, User.UserType.RO));
			userId++;
			users.put(userId, new User(userId, User.UserType.FT));
		}
		
		logger.info("User Cache Size : " + users.size());
	}
	
	public List<User> getUsers() {
		ArrayList<User> userList = Collections.list(Collections.enumeration(users.values()));
		return userList;
	}

	public User getUser(int userId) {
		return (User) users.get(userId);
	}
	
	
}
