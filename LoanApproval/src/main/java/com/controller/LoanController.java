package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.dataservice.LoanApplicationDataService;
import com.dataservice.UserDataService;
import com.model.LoanApplication;
import com.model.User;
import com.utils.AppConstants.UserType;

/**
 * REST controller for handling rest calls
 * @author anupama
 *
 */
@RestController
@RequestMapping(value = "/Loan")
public class LoanController {
	public static final String APPROVE_LOAN = "/approveLoan";
	public static final String REJECT_LOAN = "/rejectLoan";
	public static final String GET_ASSIGNED_LOAN = "/getUserloans";
	public static final String GET_ALL_LOAN = "/getAllLoans";

	private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
	
	@Autowired
	private LoanApplicationDataService loanApplicationDataService;
	@Autowired
	private UserDataService userDataService;
	@Autowired
	private LoanAssignmentService loanAssignmentService;

	@RequestMapping("/user")
	public User getPersonDetail(@RequestParam(value = "id",required = false,
	                                                    defaultValue = "0") Integer id) {
		logger.info("userDataService = " + userDataService + " id " + id);
		//Initialize with dummy user
		User user = new User(-1, UserType.NONE);
		try{
			user = userDataService.getUser(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("user = " + user.getId());
		return user;
	}

	@RequestMapping(value = GET_ALL_LOAN, method = RequestMethod.GET)
	public @ResponseBody List<LoanApplication> getAllLoans() {
		logger.info("loanApplicationDataService " + loanApplicationDataService );
		List<LoanApplication> loans = new ArrayList<LoanApplication>();
		try {
			if(loanApplicationDataService != null) {
				loans.addAll(loanApplicationDataService.getAlLoanApplications());
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return loans;
	}	
	
	@RequestMapping(value = GET_ASSIGNED_LOAN, method = RequestMethod.GET)
	public @ResponseBody List<LoanApplication> getUserLoans(@RequestParam(value = "userid",required = false,
            defaultValue = "0") Integer userId) {
		logger.info("loanApplicationDataService " + loanApplicationDataService + " userId " + userId);
		List<LoanApplication> loans = new ArrayList<LoanApplication>();
		try {
			User user = userDataService.getUser(userId);
			if(loanApplicationDataService != null) {
				loans.addAll(loanApplicationDataService.getUserLoanApplications(user));
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return loans;
	}	

	@RequestMapping(value = APPROVE_LOAN, method = RequestMethod.GET)
	public @ResponseBody LoanApplication approveLoanApplication(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "loanid") Integer loadId) {
		logger.info("Update loan application");
		//Update the LoanApplication
		LoanApplication loan = null;
		try {
			if(loanApplicationDataService != null) {
				loan = loanApplicationDataService.getLoanApplication(loadId);
				User user = userDataService.getUser(userId);
				loanApplicationDataService.approveLoan(loan,user);
				//Assign the loan application asynchronously
				loanAssignmentService.createAssignmentTask(loan, user);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return loan;
	}	

	@RequestMapping(value = REJECT_LOAN, method = RequestMethod.GET)
	public @ResponseBody LoanApplication rejectLoanApplication(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "loanid") Integer loadId) {
		logger.info("rejectLoanApplication");
		//Update the LoanApplication
		LoanApplication loan = null;
		try {
			if(loanApplicationDataService != null) {
				loan = loanApplicationDataService.getLoanApplication(loadId);
				User user = userDataService.getUser(userId);
				loanApplicationDataService.rejectLoan(loan, user);
				//Assign the loan application asynchronously
				loanAssignmentService.createAssignmentTask(loan, user);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return loan;
	}	
	

}
