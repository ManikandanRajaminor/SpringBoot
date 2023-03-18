package com.ariba.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.validation.ObjectError;

import com.ariba.config.ConfigProperties;
import com.ariba.mapper.DataMapper;
import com.ariba.model.Finder;

/**
 * @author Shyam
 *
 */
@Repository
@Transactional
public class FinderRepository {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(FinderRepository.class);

	/**
	 * configProp
	 */
	@Autowired
	ConfigProperties configProp;

	/**
	 * jdbcTemplate
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @return
	 */
	public List<Finder> list() {

		String sql = "SELECT * FROM file_finder_pbm";

		List<Finder> listFinder = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Finder.class));

		System.out.print("test****************" + listFinder.size());

		return listFinder;
	}

	/**
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	public List<Finder> search(Finder finder) throws Exception {

		logger.info("Line_of_business: " + finder.getLine_of_business());

		String countSQL = "select count(1) ";

		String dataSQL = "select LINE_OF_BUSINESS, REQUISITION_ID, REQUISITION_TITLE, REQUESTER, "
				+ "NVL(TO_CHAR(REQUISITION_DATE,'MM/DD/YYYY'),'NA') as REQUISITION_DATE, "
				+ "REQUISITION_STATUS, PO_ID, PO_STATUS, INVOICE_ID, "
				+ "NVL(TO_CHAR(INVOICE_DATE,'MM/DD/YYYY'),'NA') as INVOICE_DATE, INVOICE_STATUS, "
				+ "EFORM_CW_NO_WO, SUPPLIER ";

		String SQL = "from "
				+ (null != finder.getLine_of_business() && finder.getLine_of_business().equalsIgnoreCase("PBM")
						? "file_finder_pbm "
						: "file_finder_retail ")
				+ "where line_of_business = ? ";

		if (finder.getRequisition_id() != null && finder.getRequisition_id().trim() != null
				&& finder.getRequisition_id().trim().length() > 0) {
			SQL = SQL + "and REQUISITION_ID like '%" + finder.getRequisition_id().trim() + "%'";
		}

		if (finder.getPo_id() != null && finder.getPo_id().trim() != null && finder.getPo_id().trim().length() > 0) {
			SQL = SQL + "and PO_ID like '%" + finder.getPo_id().trim() + "%'";
		}

		if (finder.getInvoice_id() != null && finder.getInvoice_id().trim() != null
				&& finder.getInvoice_id().trim().length() > 0) {
			SQL = SQL + "and INVOICE_ID like '%" + finder.getInvoice_id().trim() + "%'";
		}

		if (finder.getRequester() != null && finder.getRequester().trim() != null
				&& finder.getRequester().trim().length() > 0) {
			SQL = SQL + "and REQUESTER like '%" + finder.getRequester().trim() + "%'";
		}

		if (finder.getRequisition_from_date() != null && finder.getRequisition_from_date().trim() != null
				&& finder.getRequisition_from_date().trim().length() > 0) {
			SQL = SQL + "and trunc(REQUISITION_DATE) >= to_date('" + finder.getRequisition_from_date()
					+ "','YYYY-MM-DD') and trunc(REQUISITION_DATE) <= to_date('" + finder.getRequisition_to_date()
					+ "','YYYY-MM-DD')";
		}

		if (finder.getSupplier() != null && finder.getSupplier().trim() != null
				&& finder.getSupplier().trim().length() > 0) {
			SQL = SQL + "and SUPPLIER like '%" + finder.getSupplier().trim() + "%'";
		}

		logger.info("Count Query: " + countSQL + SQL);

		Integer count = jdbcTemplate.queryForObject(countSQL + SQL, Integer.class,
				new Object[] { finder.getLine_of_business() });

		if (count > Integer.parseInt(configProp.getConfigValue("MAX_COUNT"))) {
			logger.error("Exception Occurred: " + configProp.getConfigValue("LIMIT_EXCEED"));
			throw new Exception(configProp.getConfigValue("LIMIT_EXCEED") + "testin");

		} else {
			SQL = dataSQL + SQL;
		}
		logger.info("Query: " + SQL);

		List<Finder> listFinder = jdbcTemplate.query(SQL, new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, finder.getLine_of_business());
			}
		}, new DataMapper());

		logger.info("List: " + (listFinder != null ? listFinder.size() : null));

		return listFinder;
	}
}
