package com.kh.test.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static common.JDBCTemplate.*;

public class TestDao {

	public List<Test> selectList(Connection conn){
		
		PreparedStatement pstmt = null;
		
		String sql = "select * from test";
		
		ResultSet rset = null;
		
		List<Test> list = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			list= new ArrayList<Test>();
			
			while(rset.next()) {
				
				Test test = new Test();
				
				test.setSeq(rset.getInt("seq"));
				test.setWriter(rset.getString("writer"));
				test.setTitle(rset.getString("title"));
				test.setContent(rset.getString("content"));
				test.setRegDate(rset.getDate("regdate"));

				list.add(test);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return list;
	}
}
