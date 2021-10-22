package com.kollu.customer.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerIdGenerator implements IdentifierGenerator {

	private Logger logger = LoggerFactory.getLogger(CustomerIdGenerator.class);
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		System.out.println("Console:: CustomerIdGenerator - generate method");
		logger.info("CustomerIdGenerator - generate method");
		long prefix = 11;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		 try {
			 connection = session.connection();
	            statement=connection.createStatement();
	            rs=statement.executeQuery("select count(CUSTOMER_ID) as CUSTOMER_ID from customer_details");
	            logger.debug("CustomerIdGenerator - generate - rs :: "+rs.getFetchSize()); 
	            
	            if(rs.next()) {
	                int id=rs.getInt(1)+1;
	                long generatedId = prefix + id;
	                System.out.println("Console:: CustomerIdGenerator - generate - Generated Id :: " +generatedId);
	                logger.debug("CustomerIdGenerator - generate - Generated Id :: "+generatedId); 
	                return generatedId;
	            }
	        } catch (SQLException e) {
	        	 System.out.println("Console:: CustomerIdGenerator - generate - Error :: " +e.getMessage());
		        	logger.error("CustomerIdGenerator - generate - Error ::" +e.getMessage()); 
	        } finally {
	        	
	        	if(statement != null) {
	        		try {
	        			 System.out.println("Console:: CustomerIdGenerator - generate - statement conn closed");
	 	                logger.debug("CustomerIdGenerator - generate - statement conn closed"); 
						statement.close();
					} catch (SQLException e) {
						System.out.println("Console:: CustomerIdGenerator - generate - statement conn Error :: " +e.getMessage());
			        	logger.error("CustomerIdGenerator - generate - statement conn Error ::" +e.getMessage()); 
						//e.printStackTrace();
					}
	        	}//stmt 
	        	
	        	if(rs !=null) {
	        		try {
	        			 System.out.println("Console:: CustomerIdGenerator - generate - rs conn closed");
		 	                logger.debug("CustomerIdGenerator - generate - rs conn closed"); 
						rs.close();
					} catch (SQLException e) {
						System.out.println("Console:: CustomerIdGenerator - generate - rs conn Error :: " +e.getMessage());
			        	logger.error("CustomerIdGenerator - generate - rs conn Error ::" +e.getMessage());
						//e.printStackTrace();
					}
	        	}//rs
	        	
			}//finally
		
		return null;
	}

}