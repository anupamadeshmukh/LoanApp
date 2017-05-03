package com.engine;

import java.time.LocalDate;

import com.model.LoanApplication;
import com.model.User;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is main rule engine and decides the next state of the LoanApplication
 * drools configuration
 * @author anupama
 *
 */
public class RuleEngine {
	
	private static final Logger logger = LoggerFactory.getLogger(RuleEngine.class);
	private StatefulKnowledgeSession ksession;
	private StatelessKnowledgeSession statelessSession;
	
	/**
	 * Constructor
	 * @param ruleFile
	 */
	public RuleEngine (String ruleFile){
		try {
			// load up the knowledge base
			KnowledgeBase kbase = readKnowledgeBase(ruleFile);
			ksession = kbase.newStatefulKnowledgeSession();
			statelessSession = kbase.newStatelessKnowledgeSession();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	/**
	 * Read the knowledgebase and keep the session created.
	 * @param ruleFile
	 * @return
	 */
	private static KnowledgeBase readKnowledgeBase(String ruleFile) {

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	    kbuilder.add(ResourceFactory.newClassPathResource(ruleFile),ResourceType.DRL);
	    logger.info("Loading rules from file" + ruleFile);
	    KnowledgeBuilderErrors errors = kbuilder.getErrors();
	    if (errors.size() > 0) {
	    	for (KnowledgeBuilderError error: errors) {
	    		System.err.println(error);
	    	}
	    	logger.error("Could not parse knowledge.");
	    	throw new IllegalArgumentException("Could not parse knowledge.");
	    }
	    
	    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	    return kbase;
	}
	
	public void fireRules() {
		if (ksession != null) {
			ksession.fireAllRules();
		}
	}
	
	public void fireRule(LoanApplication loanApp) {
		if (ksession != null) {
			ksession.insert(loanApp);
			ksession.fireAllRules();
		}
	}
	
	public void fireStatelessRule(LoanApplication loanApp) {
		if (statelessSession != null) {
			statelessSession.execute(loanApp);
		}
	}
	
	public static void main (String args[]) {
		String fileName = "./ApprovalRule.drl";

		RuleEngine engine = new RuleEngine(fileName);

		LoanApplication loanApplicationObj= new LoanApplication(2, 2000, 5, LocalDate.now(), LocalDate.now().plusDays(5));
		loanApplicationObj.setCurrentUser(new User(2,User.UserType.RO));
		loanApplicationObj.setCurrentStateTypeID(LoanApplication.StateType.REJECTED);

		System.out.println("Before Firing the Rule :" + loanApplicationObj);
		engine.fireStatelessRule(loanApplicationObj);
		System.out.println("After firing the Rule : " + loanApplicationObj);
		
		loanApplicationObj.setCurrentUser(new User(2,User.UserType.UW));
		loanApplicationObj.setCurrentStateTypeID(LoanApplication.StateType.APPROVED);
		

		System.out.println("Before Firing the Rule :" + loanApplicationObj);
		engine.fireStatelessRule(loanApplicationObj);
		System.out.println("After firing the Rule : " + loanApplicationObj);
		
	}

}
