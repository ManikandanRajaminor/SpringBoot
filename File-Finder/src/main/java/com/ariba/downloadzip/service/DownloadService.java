package com.ariba.downloadzip.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Shyam
 *
 */
public interface DownloadService {
	/**
	 * @param response
	 * @param firstName
	 * @param lastName
	 * @param city
	 * @throws IOException
	 */
	void downloadZipFile(HttpServletResponse response, String line_of_business, String requisition_id, String po_id,
			String invoice_id) throws Exception;

	/**
	 * HttpServletResponse response, String requisition_id, String po_id, String
	 * invoice_id
	 */
	void downloadFile(HttpServletResponse response, String line_of_business, String requisition_id, String po_id,
			String invoice_id) throws Exception;

}
