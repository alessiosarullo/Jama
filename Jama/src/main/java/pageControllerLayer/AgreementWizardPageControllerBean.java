package pageControllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import businessLayer.Agreement;
import businessLayer.AgreementShareTable;
import daoLayer.AgreementDaoBean;



@Named("agreementWizardPCB")
@ConversationScoped
public class AgreementWizardPageControllerBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EJB private AgreementDaoBean agreementDao;
	@Inject private Conversation conversation;
	@Inject private ShareTablePageControllerBean shareTablePCB;
	
	private Agreement agreement;
	private AgreementShareTable agreementShareTable;

	public AgreementWizardPageControllerBean() {
		this.agreement = new Agreement();
		this.agreementShareTable = new AgreementShareTable();
		this.agreement.setShareTable(agreementShareTable);
	}
	
	@PostConstruct
	public void init(){
		conversation.begin();
		shareTablePCB.setShareTable(agreementShareTable);
	}
	
	public Agreement getAgreement() {
		return agreement;
	}
	
	public AgreementShareTable getAgreementShareTable() {
		return agreementShareTable;
	}
		
	public Conversation getConversation() {
		return conversation;
	}

	public void save(){
		agreementDao.create(agreement);
		close();
	}
	
	public void close(){
		conversation.end();
	}
	
	

}