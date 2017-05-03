package com.dataservice;

import java.util.List;

import com.utils.SpringUtil;
import com.controller.LoanController;
import com.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Data Interface for mock db (Cache)
 * @author anupama
 *
 */
public class UserDataServiceImpl implements UserDataService{
	@Autowired
	private UserCache usercache;
	private static final Logger logger = LoggerFactory.getLogger(UserDataServiceImpl.class);
	public List<User> getUsers() throws Exception {
		return usercache.getUsers();
	}
	public User getUser(int userId) throws Exception {
		if(usercache != null) {
			return usercache.getUser(userId);
		}
		return null;
	}
}
