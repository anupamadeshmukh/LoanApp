package com.dataservice;

import java.util.List;

import com.model.User;

public interface UserDataService {
	public List<User> getUsers() throws Exception;
	public User getUser(int userId) throws Exception;
}
