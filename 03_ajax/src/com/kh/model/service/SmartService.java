package com.kh.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.model.dao.SmartDao;
import com.kh.phone.model.vo.Phone;
import static common.JDBCTemplate.*;

public class SmartService {
	
	private SmartDao smartDao = new SmartDao();

	public List<Phone> selectList() {
		
		Connection conn = getConnection();
		
		List<Phone> list = smartDao.selectList(conn);
		
		close(conn);
		
		
		return list;
	}

}
