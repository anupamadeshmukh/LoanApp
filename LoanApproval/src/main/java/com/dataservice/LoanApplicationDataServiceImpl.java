package com.dataservice;

import java.util.ArrayList;
import java.util.List;

import com.utils.AppConstants;
import com.utils.SpringUtil;
import com.model.LoanApplication;
import com.model.User;


/**
 * @author anupama
 *
 */
public class LoanApplicationDataServiceImpl implements
		LoanApplicationDataService {
	
	LoanApplicationCache loanCache;
	
	public LoanApplicationDataServiceImpl(LoanApplicationCache loanCache) {
		this.loanCache = loanCache;
	}

	public void updateLoan(LoanApplication application) throws Exception {
		loanCache.updateLoanApplication(application);
	}
	
	/**
	 * Approve the loan
	 */
	public void approveLoan(LoanApplication application, User user) throws Exception {
		application.setCurrentUser(user);
		application.setCurrentStateTypeID(AppConstants.StateType.APPROVED);
		//Available for reassignment
		application.setAssignedUser(null);
		application.setAssignedDate(null);
		application.setAssignedExpiryDate(null);
		loanCache.updateLoanApplication(application);
	}
	
	/**
	 * Reject Loan
	 */
	public void rejectLoan(LoanApplication application, User user) throws Exception {
		application.setCurrentUser(user);
		application.setCurrentStateTypeID(AppConstants.StateType.REJECTED);
		//Available for reassignment
		application.setAssignedUser(null);
		application.setAssignedDate(null);
		application.setAssignedExpiryDate(null);
		loanCache.updateLoanApplication(application);
	}
	
	public void allocateUser(LoanApplication application) throws Exception {
		//update the loanApplication
		loanCache.updateLoanApplication(application);
	}

	public LoanApplication getLoanApplication(int loanId) throws Exception {
		LoanApplication loan = loanCache.getLoan(loanId);
		return loan;
	}
	
	public List<LoanApplication> getUserLoanApplications(User user) throws Exception {
		List<LoanApplication> list = loanCache.getAssingedLoanApplications(user);
		return list;
	}
	
	/**
	 * Get expired loans
	 */
	public List<LoanApplication> getExpiredLoanApps() throws Exception {
		List<LoanApplication> list = loanCache.getExpiredLoanApplications();
		return list;
	}
	
	public List<LoanApplication> getAlLoanApplications() throws Exception {
		List<LoanApplication> list = loanCache.getAllLoanApplications();
		return list;
	}
}
