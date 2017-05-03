package com.dataservice;
import java.util.List;

import com.model.LoanApplication;
import com.model.User;


/**
 * @author anupama
 *
 */
public interface LoanApplicationDataService {
	public void updateLoan(LoanApplication application) throws Exception;
	public void approveLoan(LoanApplication application, User user) throws Exception;
	public void rejectLoan(LoanApplication application, User user) throws Exception;
	public void allocateUser(LoanApplication application) throws Exception;
	public List<LoanApplication> getUserLoanApplications(User user) throws Exception;
	public List<LoanApplication> getAlLoanApplications() throws Exception;
	public List<LoanApplication> getExpiredLoanApps() throws Exception;
	public LoanApplication getLoanApplication(int loanId) throws Exception;
}
