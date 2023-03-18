package com.ariba.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ariba.downloadzip.service.DownloadService;
import com.ariba.repository.FinderRepository;

/**
 * @author Shyam
 *
 */
@Controller
public class DownloadController {

	/**
	 * downloadService
	 */
	@Autowired
	private DownloadService downloadService;

	/**
	 * @param model
	 * @param finder
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/downloadFile")
	public void downloadFile(HttpServletResponse response, @RequestParam String line_of_business,
			@RequestParam(required = false) String requisition_id, @RequestParam(required = false) String po_id,
			@RequestParam(required = false) String invoice_id) throws Exception {
		downloadService.downloadFile(response, line_of_business, requisition_id, po_id, invoice_id);
	}

	/**
	 * @param model
	 * @param finder
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/downloadCopyPDFExcelCSV")
	public void downloadCopyPDFExcelCSV(HttpServletResponse response, @RequestParam String line_of_business,
			@RequestParam(required = false) String requisition_id, @RequestParam(required = false) String po_id,
			@RequestParam(required = false) String invoice_id) throws Exception {
		// downloadService.downloadZipFile(response, line_of_business, requisition_id,
		// po_id, invoice_id);
	}
}
