package com.ariba.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ariba.model.Finder;

/**
 * @author Shyam
 *
 */
public class DataMapper implements RowMapper<Finder> {

	/**
	 * ResultSet rs, int map
	 */
	@Override
	public Finder mapRow(ResultSet rs, int map) throws SQLException {

		Finder finder = new Finder();
		finder.setLine_of_business(rs.getString("LINE_OF_BUSINESS"));
		finder.setRequisition_id(rs.getString("REQUISITION_ID"));
		finder.setRequisition_title(rs.getString("REQUISITION_TITLE"));
		finder.setRequester(rs.getString("REQUESTER"));
		finder.setRequisition_from_date(rs.getString("REQUISITION_DATE"));
		finder.setRequisition_status(rs.getString("REQUISITION_STATUS"));		
		finder.setPo_id(rs.getString("PO_ID"));
		finder.setPo_status(rs.getString("PO_STATUS"));
		finder.setInvoice_id(rs.getString("INVOICE_ID"));
		finder.setInvoice_date(rs.getString("INVOICE_DATE"));
		finder.setInvoice_status(rs.getString("INVOICE_STATUS"));
		finder.setEform_cw_no_wo(rs.getString("EFORM_CW_NO_WO"));
		finder.setSupplier(rs.getString("SUPPLIER"));
		return finder;
	}

}
