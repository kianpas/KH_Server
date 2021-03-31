package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/member/memberDelete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//request.setCharacterEncoding("utf-8");
		// 저장된 세션 가져오기
		HttpSession session = request.getSession();

		// 로그인된 정보 가져오기
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		//아래도 가능
		//String memberId = request.getParameter("memberId");
		
		System.out.println(loginMember.getMemberId());
		
		//아이디로 계정 삭제
		int result = memberService.deleteMember(loginMember.getMemberId());
		
		//탈퇴 결과에 따른 로직
		if(result > 0) {
			//세션해제하므로 메세지 안나옴
			//session.setAttribute("msg", "성공");
			//탈퇴성공하면 로그아웃
			//session.invalidate();
			////location으로 logout페이지 지정으로 로그아웃 기능, 이미 존재하므로 그대로 사용하면 된다
			response.sendRedirect(request.getContextPath() + "/member/logout");
		} else {
			
		}
		
		// 리다이렉트 : url 변경
		response.sendRedirect(request.getContextPath());
	}

}
