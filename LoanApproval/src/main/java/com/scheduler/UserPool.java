package com.scheduler;

import java.util.ArrayList;
import java.util.Random;

import com.dataservice.UserCache;
import com.dataservice.UserDataService;
import com.model.User;

public class UserPool {
	
	private UserDataService userDataService;
	private ArrayList<User> userPool;
	private int currentUserIndex=-1;
	
	public UserPool(UserDataService userDataService) {
		userPool = new ArrayList<User>();
		try {
		if(userDataService != null) {
			userPool.addAll(userDataService.getUsers());
		}
		}catch(Exception e){e.printStackTrace();}
	}
	
	public int getSize() {
		return userPool.size();
	}
	
	public User getUserAtIndex(int index) {
		return userPool.get(index);		
	}
	
	public User getNextUser() {
		if(currentUserIndex == userPool.size() - 1)
			currentUserIndex = -1;
		return (User) userPool.get(++currentUserIndex);
	}
	
	/**
	 * Get Random user for next assignment
	 * @param existingUser
	 * @return
	 */
	public User getRandomUser(User existingUser) {
		Random random = new Random();
		int index = random.nextInt(userPool.size());
		User randomUser = ((User)userPool.get(index));
		while( existingUser != null && existingUser.getId() == randomUser.getId()) {
			index = random.nextInt(userPool.size());
			randomUser = ((User)userPool.get(index));
		}
		return randomUser;
	}
	
}
