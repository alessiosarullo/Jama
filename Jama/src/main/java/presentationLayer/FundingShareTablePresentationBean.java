package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import businessLayer.AbstractShareTable;

@Named("fundShareTablePB")
@ConversationScoped
public class FundingShareTablePresentationBean extends ShareTablePresentationObj implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//TODO scommentare quando si può 
	//@Inject @TransferObj Funding funding

	public FundingShareTablePresentationBean() {}

	@Override
	protected AbstractShareTable getTransferObjShareTable() {
		// return funding.getShareTable();
		return null;
	}

	@Override
	protected float getTransfetObjWholeAmount() {
		// return funding.getWholeAmount();
		return 0;
	}

}