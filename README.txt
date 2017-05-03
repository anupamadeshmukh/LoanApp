
The repository contains LoanApproval Project.

The project uses Spring Rest, Drool and Core Java. I have unit tested it using a 'Advanced Rest Client'. 

Use build.cmd to build the war locally and upload the war to any webapp server.

Testing is done using:
apache-tomcat-8.5.14-windows-x86


URLS:

Get user with user id:
http://localhost:8080/LoanApproval/Loan/user?id=1

Get all loan application in the system:
http://localhost:8080/LoanApproval/Loan/getAllLoans

Get Assigned Loans for the user:
http://localhost:8080/LoanApproval/Loan/getUserloans?userid=1

Approve Loan:
http://localhost:8080/LoanApproval/Loan/approveLoan?userid=1&loanid=1

Reject Loan:
http://localhost:8080/LoanApproval/Loan/rejectLoan?userid=2&loanid=1
