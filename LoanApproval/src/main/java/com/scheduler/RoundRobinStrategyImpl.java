package com.scheduler;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataservice.LoanApplicationCache;
import com.exception.AllocationException;
import com.model.LoanApplication;
import com.model.User;

public class RoundRobinStrategyImpl implements AllocationStrategyInterface{
	
	private int currentIndex=-1;
	private UserPool userPool;
	
	private static final Logger logger = LoggerFactory.getLogger(RoundRobinStrategyImpl.class);
	
	public RoundRobinStrategyImpl(UserPool userPool) {
		this.userPool = userPool;
	}
	
	private User getNextUser(User.UserType userType) {
		if(currentIndex == userPool.getSize() - 1) {
			currentIndex=-1;
		}
		User user = userPool.getUserAtIndex(++currentIndex);
		
		while(userType != user.getType()) {
			user = userPool.getUserAtIndex(++currentIndex);
			if(currentIndex == userPool.getSize() - 1) {
				currentIndex=-1;
			}
		}
		return user;
	}
	
	
	public void assignTask(LoanApplication loan, User lastActionByUser) throws AllocationException{
		//Assign to a user
		User assignedUser = getNextUser(loan.getNextUserTypeID());
		//update the object and return
		loan.setCurrentStateTypeID(null);
		loan.setCurrentUser(null);
		loan.setAssignedUser(assignedUser);
		loan.setAssignedDate(LocalDate.now());
		loan.setAssignedExpiryDate(LocalDate.now().plusDays(5));
		loan.setNextUserTypeID(null);
	}
}
