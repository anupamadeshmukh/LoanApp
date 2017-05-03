package com.scheduler;

import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataservice.LoanApplicationDataService;
import com.model.LoanApplication;
import com.task.AllocationTask;
import com.task.AsynchTaskExecutorHandler;
import com.utils.SpringUtil;

/**
 * Timer to manage the loan applications which are expired
 * @author anupama
 *
 */
//public class DeadlineManagerTimer extends TimerTask{
public class DeadlineManagerTimer {
	private AsynchTaskExecutorHandler executor;
	private LoanApplicationDataService loanApplicationDataService;
	
	private static final Logger logger = LoggerFactory.getLogger(DeadlineManagerTimer.class);
	
	public DeadlineManagerTimer(AsynchTaskExecutorHandler executor, LoanApplicationDataService loanApplicationDataService) {
		this.executor = executor;
		this.loanApplicationDataService = loanApplicationDataService;
	}
	
	/**
	 * Run and find expired allocations
	 */
	public void run() {
		//Reassign the task
		logger.info("Running the Expired Loan ReAssignment Service");
		try {
			//Find all the expired Task loop through them and reassign them
			if(loanApplicationDataService != null) {
				List<LoanApplication> expiredLoans = loanApplicationDataService.getExpiredLoanApps();
				for(int i=0; i<expiredLoans.size() ;i++) {
					LoanApplication loan = (LoanApplication) expiredLoans.get(i);
					logger.info("Loan Expired submitted to reassignment: LoanId" + loan.getLoadID());
					createAndSubmit(loan);
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Create the reassignment Task and submit it for execution into Asynchronous executor
	 * @param loan
	 */
	private void createAndSubmit(LoanApplication loan) {
		//Assign using the Random assigner
		//Create Task
		AllocationTask randomAllocationTask = (AllocationTask) SpringUtil.getApplicationContext()
						.getBean("randomAllocationTask");
		randomAllocationTask.setLoan(loan);
		randomAllocationTask.setExistingUser(loan.getAssignedUser());
		
		//Submit the task to executor
		this.executor.submit(randomAllocationTask);		
	}
}
