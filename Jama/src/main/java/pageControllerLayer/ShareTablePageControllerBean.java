package pageControllerLayer;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.resource.spi.IllegalStateException;

import util.MathUtil;
import util.Messages;
import businessLayer.AbstractShareTable;
import businessLayer.ChiefScientist;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;

@Named("shareTablePCB")
@ConversationScoped
public class ShareTablePageControllerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractShareTable shareTable;

	public ShareTablePageControllerBean() {
	}

	public AbstractShareTable getShareTable() {
		return shareTable;
	}

	public void setShareTable(AbstractShareTable shareTable) {
		this.shareTable = shareTable;
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) {
		float atheneumCapitalBalance = (Float) ((UIInput) component
				.findComponent("atheneumCapitalBalance")).getValue();
		float atheneumCommonBalance = (Float) ((UIInput) component
				.findComponent("atheneumCommonBalance")).getValue();
		float structures = (Float) ((UIInput) component
				.findComponent("structures")).getValue();
		float personnel = (Float) ((UIInput) component
				.findComponent("personnel")).getValue();

		float goodsAndServices = (Float) ((UIInput) component
				.findComponent("goodsAndServices")).getValue();
		float businessTrip = (Float) ((UIInput) component
				.findComponent("businessTrip")).getValue();
		float consumerMaterials = (Float) ((UIInput) component
				.findComponent("consumerMaterials")).getValue();
		float inventoryMaterials = (Float) ((UIInput) component
				.findComponent("inventoryMaterials")).getValue();
		float rentals = (Float) ((UIInput) component.findComponent("rentals"))
				.getValue();
		float personnelOnContract = (Float) ((UIInput) component
				.findComponent("personnelOnContract")).getValue();
		float otherCost = (Float) ((UIInput) component
				.findComponent("otherCost")).getValue();

		float[] mainValues = { atheneumCapitalBalance, atheneumCommonBalance,
				structures, personnel, goodsAndServices };
		float[] goodsAndServicesValues = { businessTrip, consumerMaterials,
				inventoryMaterials, rentals, personnelOnContract, otherCost };
		float[] personnelValues = {};

		shareTable.validate(mainValues, goodsAndServicesValues,
				personnelValues, goodsAndServices, personnel);
	}
}