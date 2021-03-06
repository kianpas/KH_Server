package com.kh.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MenuOrderServlet extends HttpServlet{

	public MenuOrderServlet() {
		super();
		
	}
	 @Override
	public void init()  {
		
	}
	@Override
	public void destroy() {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//0. encoding 선언
		request.setCharacterEncoding("utf-8");
		
		//1. 사용자 입력값 처리
		String main = request.getParameter("main_menu");
		String side = request.getParameter("side_menu");
		String drink = request.getParameter("drink_menu");
		
		
		//2. 합계계산 switch문
		int price = 0;
		switch (main) {
		case "한우버거" : price += 5000;
		break;
		case "밥버거" : price += 4500;
		break;
		case "치즈버거" : price += 400;
		break;
		}
		
		switch (side) {
		case "감자튀김" : price += 1500;
		break;
		case "어니언링" : price += 1700;
		break;
		
		}
		
		switch (drink) {
		case "콜라" : price += 1000;
		break;
		case "사이다" : price += 1000;
		break;
		case "커피" : price += 1500;
		break;
		case "밀크쉐이크" : price += 2500;
		break;
		}
		
		request.setAttribute("price", price);
		
		RequestDispatcher reqDispatcher = request.getRequestDispatcher("/menu/menuOrder.jsp");
		
		reqDispatcher.forward(request, response);
		
		
		
		
	}
	
	
}
