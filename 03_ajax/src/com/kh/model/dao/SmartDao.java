package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.phone.model.vo.Phone;
import static common.JDBCTemplate.*;

public class SmartDao {

	public List<Phone> selectList(Connection conn) {
		
		PreparedStatement pstmt = null;
		String sql = "select * from smartphone";
		
		ResultSet rset = null;
		
		List<Phone> list = null;
		try {
			pstmt = conn.prepareStatement(sql);
			
		    rset = pstmt.executeQuery();
		    
		    
		    list = new ArrayList<Phone>();
		    while(rset.next()) {
		    
		    	
		    	Phone phone = new Phone();
		    	
		    	phone.setName(rset.getString("pname"));
		    	phone.setAmount(rset.getInt("amount"));
		    	phone.setDate(rset.getDate("pdate"));
		    	
		    	list.add(phone);
		    	
		    }
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

}
