package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MemberLogoutServlet
 */
@WebServlet("/member/logout")
public class MemberLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//세션 무효화 : 세션에 저장된 속성값을 모두 폐기
		//만약 세션이 존재하지 않으면, 새로 만들지 않고, null 리턴
		HttpSession session = request.getSession(false);
		
		//완전 폐기가 아닌 이번 세션만 제거하고 나중에 재사용을 위해 쓴 것, 아이디도 재발급
		if(session != null)
			session.invalidate();
		
		response.sendRedirect(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//doGet, doPost 상관없는 것이면 이런 식으로 처리됨 dopost가 doget호출
		doGet(request, response);
	}

}
