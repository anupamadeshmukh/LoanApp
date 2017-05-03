package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.model.LoanApplication;
import com.model.User;
import com.task.AllocationTask;
import com.task.AsynchTaskExecutorHandler;
import com.utils.SpringUtil;

/**
 * Service to create the task and execute it asynchronously
 * @author anupama
 *
 */
@Service
public class LoanAssignmentService {
	private AsynchTaskExecutorHandler asynchTaskExecutorHandler;
	private static final Logger logger = LoggerFactory.getLogger(LoanAssignmentService.class);
	
	public LoanAssignmentService(AsynchTaskExecutorHandler asynchTaskExecutorHandler) { 
		this.asynchTaskExecutorHandler = asynchTaskExecutorHandler;
	}
	/**
	 * Create the asynchronous task and submit it to the executor for later execution
	 * @param loan
	 * @param user
	 */
	public void createAssignmentTask(LoanApplication loan, User user) {
		
		//Create Task per request
		AllocationTask allocationTask = (AllocationTask) SpringUtil.getApplicationContext()
						.getBean("allocationTask");
		allocationTask.setLoan(loan);
		allocationTask.setExistingUser(user);
		//Submit the task to executor
		this.asynchTaskExecutorHandler.submit(allocationTask);
	}
}
