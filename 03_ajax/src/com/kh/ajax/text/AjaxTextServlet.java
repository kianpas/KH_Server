package com.kh.ajax.text;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AjaxTextServlet
 */
@WebServlet("/text")
public class AjaxTextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxTextServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 사용자 입력값 처리
		//2. 업무로직
		//3. 응답처리 : 출력스트림을 이용해서 응답메시지 직접 작성
		response.setContentType("text/plain; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.println("안녕");
		out.println("12345");
		out.println("<h1>Ajax</h1>");
		out.println(new Date());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//1. 사용자 입력값 처리
		String name = request.getParameter("name");
		System.out.println("name@servlet.doPost = " + name);
				//2. 업무로직
				//3. 응답처리 : 출력스트림을 이용해서 응답메시지 직접 작성
		response.setContentType("text/plain; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.println("POST로 요청하였습니다");
		out.println("이름은 [" + name + "] 입니다");
		out.println("요청 처리 시각은 " + new Date() +" 입니다.");
	}

}
