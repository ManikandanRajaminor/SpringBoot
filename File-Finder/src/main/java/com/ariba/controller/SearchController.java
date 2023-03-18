package com.ariba.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ariba.config.ConfigProperties;
import com.ariba.model.Finder;
import com.ariba.repository.FinderRepository;
import com.ariba.util.ValidationService;

/**
 * @author Shyam
 *
 */
@Controller
public class SearchController {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	/**
	 * finderDao
	 */
	@Autowired
	private FinderRepository finderDao;

	/**
	 * userValidationService
	 */
	@Autowired
	private ValidationService userValidationService;

	/**
	 * configProp
	 */
	@Autowired
	ConfigProperties configProp;

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(Model model) {
		Finder finder = new Finder();
		model.addAttribute("finder", finder);
		return "search";
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cvsart", method = RequestMethod.GET)
	public String main1(Model model) {
		Finder finder = new Finder();
		model.addAttribute("finder", finder);
		return "cvsat";
	}
	/**
	 * @param model
	 * @param finder
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(Model model, @ModelAttribute("finder") @Valid Finder finder, BindingResult result) {
		String err = userValidationService.validateUser(finder);
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
			logger.error("Exception Occurred: " + err);
		}
		if (result.hasErrors()) {
			return "search";
		}

		List<Finder> listFinder = null;
		try {
			listFinder = finderDao.search(finder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception Occurred: " + e.getMessage());
			ObjectError error = null;
			if (e.getMessage() != null && e.getMessage().contains("please refine search")) {
				error = new ObjectError("globalError", configProp.getConfigValue("LIMIT_EXCEED"));
			} else {
				error = new ObjectError("globalError", configProp.getConfigValue("GLOBAL_ERROR") + e.getCause());
			}
			result.addError(error);
			return "search";
		}

		if (listFinder == null || listFinder.isEmpty()) {
			ObjectError error = new ObjectError("globalError", configProp.getConfigValue("NO_DATA_FOUND"));
			result.addError(error);
			logger.error("Exception Occurred: " + error.getDefaultMessage());
		} else if (listFinder.size() > Integer.parseInt(configProp.getConfigValue("MAX_COUNT"))) {
			ObjectError error = new ObjectError("globalError", configProp.getConfigValue("LIMIT_EXCEED"));
			result.addError(error);
			logger.error("Exception Occurred: " + error.getDefaultMessage());
		}

		if (result.hasErrors()) {
			return "search";
		}

		model.addAttribute("listFinder", listFinder);

		return "report";
	}

}
