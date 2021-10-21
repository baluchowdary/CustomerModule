package com.kollu.customer.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomerIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		System.out.println("i am from CustomerIdGenerator.generate method");
		long prefix = 11;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		 try {
			 connection = session.connection();
	            statement=connection.createStatement();
	            rs=statement.executeQuery("select count(CUSTOMER_ID) as CUSTOMER_ID from customer_details");

	            if(rs.next()) {
	                int id=rs.getInt(1)+1;
	                long generatedId = prefix + id;
	                System.out.println("Generated Id: " + generatedId);
	                return generatedId;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	        	
	        	if(statement != null) {
	        		try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
	        	}//stmt 
	        	
	        	if(rs !=null) {
	        		try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
	        	}//rs
	        	
			}//finally
		
		return null;
	}

}
