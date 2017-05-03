package com.model;

import java.io.Serializable;
import java.util.Date;

import com.model.User;
import com.utils.AppConstants;
import java.time.LocalDate;
/** 
 * Pojo to containing the state of the Loan Approval
 * @author anupama
 *
 */
public class LoanApplication implements Serializable, AppConstants {
	
	//User who has just done the operation
	private User currentUser;
	private StateType currentStateTypeID;

	private User.UserType nextUserTypeID;
	private User assignedUser;

	private int loadID;
	private double loanAmount;
	private int tenure; //years
	private int customerID;
	private LocalDate assignedDate;
	private LocalDate assignedExpiryDate;
	
	private static final long serialVersionUID = -8243145429438016231L;
	public static final String UNASSIGNED = "UNASSIGNED"; 
	
	public LoanApplication(int loadID, double loanAmount, int tenure
			, LocalDate assignedDate, LocalDate assignedExpiryDate)
	{
		this.loadID = loadID;
		this.loanAmount = loanAmount;
		this.tenure = tenure;
		this.assignedDate = assignedDate;
		this.setAssignedExpiryDate(assignedExpiryDate);
	}
	
	public int getLoadID() {
		return loadID;
	}
	public void setLoadID(int loadID) {
		this.loadID = loadID;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public int getTenure() {
		return tenure;
	}
	public void setTenure(int tenure) {
		this.tenure = tenure;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public StateType getCurrentStateTypeID() {
		return currentStateTypeID;
	}
	public void setCurrentStateTypeID(StateType currentStateTypeID) {
		this.currentStateTypeID = currentStateTypeID;
	}
	
	public User.UserType getNextUserTypeID() {
		return nextUserTypeID;
	}

	public void setNextUserTypeID(User.UserType nextUserTypeID) {
		this.nextUserTypeID = nextUserTypeID;
	}

	@Override
	public String toString() {
		return "[ loadID = " + loadID + " loanAmount = " + loanAmount + " currentUID=" + currentUser.getId() + " currentUserTypeID = " + currentUser.getType() +  " currentStateTypeID=" + currentStateTypeID + " nextUserTypeID=" + nextUserTypeID + "]";
	}

	public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	public User getAssignedUser() {
		return assignedUser;
	}
	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	public LocalDate getAssignedExpiryDate() {
		return assignedExpiryDate;
	}

	public void setAssignedExpiryDate(LocalDate assignedExpiryDate) {
		this.assignedExpiryDate = assignedExpiryDate;
	}


}
