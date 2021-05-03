package com.kh.ajax.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.model.service.SmartService;
import com.kh.phone.model.vo.Phone;

/**
 * Servlet implementation class AjaxSmartServlet
 */
@WebServlet("/smart")
public class AjaxSmartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SmartService smartService = new SmartService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxSmartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List <Phone> list = smartService.selectList();
		
		for(Phone p : list) {
			System.out.println(p);
		}
		
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(list);
		
		System.out.println(jsonStr);
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		
	}

}
