package com.ariba.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ariba.config.ConfigProperties;

/**
 * @author Shyam
 *
 */
@Service
public class FileFinderUtil {

	/**
	 * configProp
	 */
	@Autowired
	ConfigProperties configProp;

	/**
	 * @param line_of_business
	 * @param requisition_id
	 * @param po_id
	 * @param invoice_id
	 * @return
	 */
	public String getPath(String line_of_business, String requisition_id, String po_id, String invoice_id) {

		String path = configProp.getConfigValue("RESOURCE_PATH") + line_of_business + "\\";

		if (null != requisition_id) {
			path = path + "PR\\" + requisition_id;
		} else if (null != po_id) {
			path = path + "PO\\" + po_id;
		} else if (null != invoice_id) {
			path = path + "INV\\" + invoice_id;
		}

		return path;

	}
}
