package com.dataservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.controller.LoanController;
import com.model.LoanApplication;
import com.model.User;


/**
 * Mock DB (Cache) to maintain all the loan applications.
 * 
 * @author anupama
 *
 */
public class LoanApplicationCache {
	//Map for cache
	HashMap<Integer,LoanApplication> loans = new HashMap<Integer,LoanApplication>();
	private static final Logger logger = LoggerFactory.getLogger(LoanApplicationCache.class);
	
	public LoanApplicationCache()
	{
		initializeCache();
	}
	
	private void initializeCache() {
		for(int i=1; i<=4;i=i+3) {
			LoanApplication loan = new LoanApplication(i, 10000 * i, 10
					, LocalDate.now(), LocalDate.now().plusDays(5));
			loan.setAssignedUser(new User(i, User.UserType.UW));
			loans.put(loan.getLoadID(), loan);
		}
	}
	
	//Add the loan application to the cache
	public void addLoanApplication(LoanApplication loanApplication) {
		loans.put(loanApplication.getLoadID(), loanApplication);
	}

	public List<LoanApplication> getAllLoanApplications() {
		ArrayList<LoanApplication> loanList = Collections.list(Collections.enumeration(loans.values()));
		logger.info("All Loans " + loanList.size());
		return loanList;
	}
	
	/***
	 * Get assigned loan applications to the user
	 * @param user
	 * @return
	 */
	public List<LoanApplication> getAssingedLoanApplications(User user) {
		ArrayList<LoanApplication> loanList = Collections.list(Collections.enumeration(loans.values()));
		
		//filter list and fetch only assigned loans to the user
		ArrayList<LoanApplication> filteredLoans = new ArrayList<LoanApplication>();
		for (Integer key : loans.keySet()) {
			LoanApplication obj = loans.get(key);
			if(obj.getAssignedUser().getId() == user.getId()) {
				filteredLoans.add(obj);
			}
		}
		logger.info("filteredLoans " + filteredLoans.size());
		return filteredLoans;
	}
	
	/**
	 * Update the loan as per user action
	 * @param loanApplication
	 */
	public void updateLoanApplication(LoanApplication loanApplication) {
		loans.put(loanApplication.getLoadID(), loanApplication);
	}
	
	public LoanApplication getLoan(int loanId) {
		return loans.get(loanId);
	}
	
	/**
	 * Get assigned loan applications to the user
	 * @param user
	 * @return
	 */
	public List<LoanApplication> getExpiredLoanApplications() {
		ArrayList<LoanApplication> loanList = Collections.list(Collections.enumeration(loans.values()));
		
		//filter list and fetch only assigned loans to the user
		ArrayList<LoanApplication> filteredLoans = new ArrayList<LoanApplication>();
		for (Integer key : loans.keySet()) {
			LoanApplication obj = (LoanApplication) loans.get(key);
			if(obj.getAssignedExpiryDate() != null && obj.getAssignedExpiryDate().isBefore(LocalDate.now())) {
				filteredLoans.add(obj);
			}
		}
		return filteredLoans;
	}

}
