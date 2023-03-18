package com.ariba.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import oracle.jdbc.pool.OracleDataSource;

/**
 * @author Shyam
 *
 */
@Profile("oracle")
public class DBConfiguration {

	/**
	 * @return
	 * @throws SQLException
	 */
	@Bean
	public DataSource dataSource() throws SQLException {
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setUser("tester");
		dataSource.setPassword("tester123");
		dataSource.setURL("jdbc:oracle:thin:@//localhost:1521/orcl");
		//dataSource.setFastConnectionFailoverEnabled(true);
		//dataSource.setImplicitCachingEnabled(true);
		//dataSource.setConnectionCachingEnabled(true);
		return dataSource;

	}
}
