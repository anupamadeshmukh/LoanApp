package com.scheduler;

import com.exception.AllocationException;
import com.model.LoanApplication;
import com.model.User;

public interface AllocationStrategyInterface {
	public void assignTask(LoanApplication loan, User existingUser) throws AllocationException;
}
