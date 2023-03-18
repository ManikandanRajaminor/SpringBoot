package com.ariba.model;

/**
 * @author Shyam
 *
 */
public class Finder {

	private String line_of_business;
	private String requisition_id;
	private String po_id;
	private String invoice_id;
	private String requisition_title;
	private String requisition_from_date;
	private String requisition_to_date;
	private String requester;
	private String supplier;

	public String getLine_of_business() {
		return line_of_business;
	}

	public void setLine_of_business(String line_of_business) {
		this.line_of_business = line_of_business;
	}

	public String getRequisition_id() {
		return requisition_id;
	}

	public void setRequisition_id(String requisition_id) {
		this.requisition_id = requisition_id;
	}

	public String getPo_id() {
		return po_id;
	}

	public void setPo_id(String po_id) {
		this.po_id = po_id;
	}

	public String getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(String invoice_id) {
		this.invoice_id = invoice_id;
	}

	public String getRequisition_title() {
		return requisition_title;
	}

	public void setRequisition_title(String requisition_title) {
		this.requisition_title = requisition_title;
	}

	public String getRequisition_from_date() {
		return requisition_from_date;
	}

	public void setRequisition_from_date(String requisition_from_date) {
		this.requisition_from_date = requisition_from_date;
	}

	public String getRequisition_to_date() {
		return requisition_to_date;
	}

	public void setRequisition_to_date(String requisition_to_date) {
		this.requisition_to_date = requisition_to_date;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	private String invoice_status;
	private String po_status;
	private String requisition_status;
	private String eform_cw_no_wo;

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}

	public String getPo_status() {
		return po_status;
	}

	public void setPo_status(String po_status) {
		this.po_status = po_status;
	}

	public String getRequisition_status() {
		return requisition_status;
	}

	public void setRequisition_status(String requisition_status) {
		this.requisition_status = requisition_status;
	}

	public String getEform_cw_no_wo() {
		return eform_cw_no_wo;
	}

	public void setEform_cw_no_wo(String eform_cw_no_wo) {
		this.eform_cw_no_wo = eform_cw_no_wo;
	}

	private String invoice_date;

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	@Override
	public String toString() {
		return "Finder [line_of_business=" + line_of_business + ", requisition_id=" + requisition_id + ", po_id="
				+ po_id + ", invoice_id=" + invoice_id + ", requisition_title=" + requisition_title
				+ ", requisition_from_date=" + requisition_from_date + ", requisition_to_date=" + requisition_to_date
				+ ", requester=" + requester + ", supplier=" + supplier + ", invoice_status=" + invoice_status
				+ ", po_status=" + po_status + ", requisition_status=" + requisition_status + ", eform_cw_no_wo="
				+ eform_cw_no_wo + ", invoice_date=" + invoice_date + "]";
	}

}
