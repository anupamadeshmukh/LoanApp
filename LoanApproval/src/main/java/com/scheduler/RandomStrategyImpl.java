package com.scheduler;

import com.model.LoanApplication;
import com.model.User;
import com.exception.AllocationException;

public class RandomStrategyImpl implements AllocationStrategyInterface{
	private UserPool userPool;
	
	public RandomStrategyImpl(UserPool userPool) {
		this.userPool = userPool;
	}

	/**
	 * Get Random user for next assignment
	 * @param existingUser
	 * @return
	 */
	public User getRandomUser(User existingUser) {
		User randomUser = userPool.getRandomUser(existingUser);
		return randomUser;
	}
	
	public void assignTask(LoanApplication loan, User existingUser) throws AllocationException{
		loan.setAssignedUser(getRandomUser(existingUser));
	}
	
}
