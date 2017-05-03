package com.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.controller.LoanController;
import com.dataservice.LoanApplicationDataService;
import com.engine.RuleEngine;
import com.exception.AllocationException;
import com.model.LoanApplication;
import com.model.User;
import com.scheduler.AllocationStrategyInterface;

public class AllocationTask implements Runnable, Task{

    private AllocationStrategyInterface strategy;
    private LoanApplicationDataService loanApplicationDataService;
	private LoanApplication loan;
    private User existingUser;
    private RuleEngine ruleEngine;

    private static final Logger logger = LoggerFactory.getLogger(AllocationTask.class);
    
    public AllocationTask(AllocationStrategyInterface strategy, 
    		LoanApplicationDataService loanApplicationDataService,
    		RuleEngine ruleEngine) {
    	this.strategy = strategy;
    	this.loanApplicationDataService = loanApplicationDataService;
    	this.ruleEngine = ruleEngine;
    }
    
    public LoanApplication getLoan() {
		return loan;
	}

	public void setLoan(LoanApplication loan) {
		this.loan = loan;
	}

	public User getExistingUser() {
		return existingUser;
	}

	public void setExistingUser(User existingUser) {
		this.existingUser = existingUser;
	}    
    public void run() {
    	try {
    		logger.info("Before firing Rule: currentStateTypeID : " + loan.getCurrentStateTypeID() + "Current User Type : " + loan.getCurrentUser().getType());
    		//Apply Rules
    		this.ruleEngine.fireStatelessRule(loan);
    		logger.info("After firing Rule: Assigned User : Next User Type : " + loan.getNextUserTypeID());
    		
    		//Assign to the user
    		strategy.assignTask(loan, existingUser);
    		//Persists the reassignment
    		loanApplicationDataService.updateLoan(loan);
    	} catch(Exception e) {
    		//throw the runtime exception
    		throw new RuntimeException(e);
    	}
    }
}