package com.model;

import com.utils.AppConstants;

public class User implements AppConstants {

  private int id;
  public UserType type;
  
  public User(int id, UserType type) {
	  this.id = id;
	  this.setType(type);
  }
	
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

	public UserType getType() {
		return type;
	}
	
	public void setType(UserType type) {
		this.type = type;
	}
}