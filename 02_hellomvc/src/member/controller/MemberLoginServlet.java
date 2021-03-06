package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;


/**
 * Servlet implementation class MemberLoginServlet
 * 서블릿 등록을 자동으로 처리함
 * 
 * -urlPatterns : String[]
 * -name : String 
 */
//@WebServlet("/member/login") urlPatterns 생략가능
@WebServlet(urlPatterns = {"/member/login"})
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. encoding처리, 필터로 사전에 처리
		//request.setCharacterEncoding("utf-8");
		
		//2. 사용자입력값 처리
		String memberId = request.getParameter("memberId");
		String password = MvcUtils.getSha512(request.getParameter("password"));
		String saveId = request.getParameter("saveId");
		System.out.println("memberId@servlet = " + memberId);
		System.out.println("password@servlet = " + password);
		
		//3. 업무로직 : memberId로 회원 객체를 조회
		Member member = memberService.selectOne(memberId);
		System.out.println("member@servlet = " + member);
		
		//로그인 성공/실패여부 판단
		//1. 로그인 성공 : member != null && password.equals(member.getPassword())
		//2. 로그인 실패 : 
		//아이디가 존재하지 않을 때 member == null
		//비번이 틀릴 때 member != null && !password.equals(member.getPassword())
		
		//else에도 사용하기 위해 조건문 위에서 선언
		//true는 새로운 세션을 생성을 의미
		HttpSession session = request.getSession(true);
		
		if(member != null && password.equals(member.getPassword())) {
			//로그인 성공
			//request.setAttribute("msg", "로그인에 성공했습니다");
			
			//로그인한 사용자 정보
			//request.getSession(create:boolean); : 새로 생성 여부 boolean형으로 출력 기본값 true 
			//HttpSession session = request.getSession(true);
			//System.out.println(session.getId());
			session.setAttribute("loginMember", member);
			
			//session timeout : web.xml보다 우선순위 높음
			//초
//			session.setMaxInactiveInterval(30);
			
			//saveId : cookie 처리
			Cookie c = new Cookie("saveId", memberId);
			c.setPath(request.getContextPath()); //path쿠키를 전송할 url
			if(saveId != null) {
				//saveId체크시
				c.setMaxAge(60 * 60 * 24 * 7); //원하는 기간 지정 초로 지정 현재는 7일짜리 영속쿠키로 지정
			} else {
				//saveId 체크해재ㅔ시
				c.setMaxAge(0); //0으로 지정해서 즉시 삭제, 음수로 지정하면 session종료시 제거
			}
			response.addCookie(c);
			//리다이렉트 : url 변경
			//response.sendRedirect(request.getContextPath());
			
		} else {
			//로그인 실패
			//request는 1회용
			//request.setAttribute("msg", "로그인에 실패했습니다");
			
			//세션에 저장하여 로그인 실패시 계속 사용할 수 있도록함
			session.setAttribute("msg", "로그인에 실패했습니다");
			
			//request.setAttribute("loc", request.getContextPath()); // /mvc 리턴
			//4. 응답메세지, jsp에 위임, 또는 리다이렉트
			//rqtds 사용안할 것
//			RequestDispatcher reqDispatcher = request.getRequestDispatcher("/index.jsp");
//			reqDispatcher.forward(request, response);
		}
		//리다이렉트 : url 변경
		response.sendRedirect(request.getContextPath());
	
	}

}
