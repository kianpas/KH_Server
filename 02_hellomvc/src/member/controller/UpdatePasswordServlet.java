package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class UpdatePasswordServlet
 */
@WebServlet("/member/updatePassword")
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberService memberService = new MemberService();
	/**
	 * 비밀번호 변경페이지 제공
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/member/updatePassword.jsp").forward(request, response);
	}

	/**
	 * 비밀번호 변경처리
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 사용자 입력값 처리 : 기존비밀번호/신규비밀번호 암호화처리 필수
		String password = MvcUtils.getSha512(request.getParameter("password"));
		String newPassword = MvcUtils.getSha512(request.getParameter("newPassword"));

		System.out.println("기본 비밀번호 :" + password);
		System.out.println("새로운 비밀번호 :" + newPassword);

		// 2. 기존비밀번호 비교 : session의 loginMember객체 이용할 것

		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		System.out.println("세션 기본 비밀번호 :" + loginMember.getPassword());
		// 3. 업무로직 : 기존비밀번호가 일치한 경우만 신규비밀번호로 업데이트한다.

		String msg = "";
		
		if (password.equals(loginMember.getPassword())) {
			loginMember.setPassword(newPassword);
			// 기존비밀번호가 일치하고, 신규비밀번호 변경에 성공했다면, "비밀번호를 성공적으로 변경했습니다." 안내 &
			// /mvc/member/memberView 리다이렉트
			System.out.println(loginMember);
			int result = memberService.updatePassword(loginMember);
			System.out.println(result);
			if (result > 0) {
				msg = "비밀번호를 성공적으로 변경했습니다.";
				response.sendRedirect(request.getContextPath() + "/member/memberView");
			} 

		} else {
			// 4. 사용자경고창 및 리다이렉트 처리

			// 기존비밀번호가 일치하지 않았다면, "비밀번호가 일치하지 않습니다." 안내 & /mvc/member/updatePassword 리다이렉트
			msg = "비밀번호가 일치하지 않습니다.";
			response.sendRedirect(request.getContextPath() + "/member/updatePassword");

		}
		session.setAttribute("msg", msg);

	}

}
