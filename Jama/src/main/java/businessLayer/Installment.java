package businessLayer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.validator.ValidatorException;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import util.Messages;

/**
 * Entity implementation class for Entity: Installment
 * 
 */
@Entity
public class Installment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date date;

	private float wholeAmount;
	private float IVA_amount;
	private float wholeTaxableAmount;

	@Min(0)
	private int voucherNumber;

	@Temporal(TemporalType.DATE)
	private Date voucherDate;

	@Min(0)
	private int ivaVoucherNumber;
	@Min(0)
	private int pendingNumber;
	@Min(0)
	private int invoiceNumber;

	@Temporal(TemporalType.DATE)
	private Date invoiceDate;

	private boolean paidInvoice;
	private boolean reportRequired;
	private String note;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Agreement agreement;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn
	private InstallmentShareTable shareTable;

	public Installment() {
		this.shareTable = new InstallmentShareTable(this);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getWholeAmount() {
		return wholeAmount;
	}

	private void setWholeAmount() {
		this.wholeAmount = this.wholeTaxableAmount * (100 + this.IVA_amount)
				/ 100;
	}

	public void setWholeTaxableAmount(float wholeTaxableAmount) {
		this.wholeTaxableAmount = wholeTaxableAmount;
		setWholeAmount();
	}

	public float getWholeTaxableAmount() {
		return wholeTaxableAmount;
	}

	public float getIVA_amount() {
		return IVA_amount;
	}

	public void setIVA_amount(float IVA_amount) {
		this.IVA_amount = IVA_amount;
		setWholeAmount();
	}

	public int getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(int voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public int getIvaVoucherNumber() {
		return ivaVoucherNumber;
	}

	public void setIvaVoucherNumber(int ivaVoucherNumber) {
		this.ivaVoucherNumber = ivaVoucherNumber;
	}

	public int getPendingNumber() {
		return pendingNumber;
	}

	public void setPendingNumber(int pendingNumber) {
		this.pendingNumber = pendingNumber;
	}

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public boolean isPaidInvoice() {
		return paidInvoice;
	}

	public void setPaidInvoice(boolean paidInvoice) {
		this.paidInvoice = paidInvoice;
	}

	public boolean isReportRequired() {
		return reportRequired;
	}

	public void setReportRequired(boolean reportRequired) {
		this.reportRequired = reportRequired;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public InstallmentShareTable getShareTable() {
		return shareTable;
	}

	public void setShareTable(InstallmentShareTable shareTable) {
		this.shareTable = shareTable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public void validateAmount() {
		List<Installment> installments = agreement.getInstallments();
		float sum = 0;
		for (Installment installment : installments) {
			sum += installment.getWholeAmount();
		}
		sum += this.wholeAmount;
		if (sum > agreement.getWholeAmount()) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_installmentAmount"));
		}
	}

	float[] getMainValuesAmounts() {
		float[] mainValuesAmount = {
				shareTable.getAtheneumCapitalBalance() * wholeAmount / 100,
				shareTable.getAtheneumCommonBalance() * wholeAmount / 100,
				shareTable.getPersonnel() * wholeAmount / 100,
				shareTable.getStructures() * wholeAmount / 100,
				shareTable.getGoodsAndServices() * wholeAmount / 100 };
		return mainValuesAmount;
	}
}
