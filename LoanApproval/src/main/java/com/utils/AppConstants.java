package com.utils;


/**
 * The Interface MessageConstants.
 *
 * @author anupama
 */
public interface AppConstants {
	
	/**
	 * The Enum MSG_KEY.
	 */
	/*
	 * Keys of the incoming message
	 */
	public enum MSG_KEY {
		LOANID, 
		LOANAMOUNT, 
		TENURE, 
		CUSTOMERID, 
		USERTYPEID}; 

	public enum StateType{APPROVED, REJECTED, PENDING, UNDEFINED};
	
	public enum UserType{UW, RO, FT, NONE};	
}
