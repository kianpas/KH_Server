package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdate
 */
@WebServlet("/member/memberUpdate")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberUpdateServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();

		// 기존 로그인 세션
		Member loginMember = (Member) session.getAttribute("loginMember");
		String password = request.getParameter("password");
		String memberName = request.getParameter("memberName");
		String gender = request.getParameter("gender");
		String birthday_ = request.getParameter("birthday");

		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		// 취미를 배열로 가져오기
		String[] hobby_ = request.getParameterValues("hobby");

		System.out.println("password@servlet = " + password);

		Member member = new Member();
		member.setMemberId(loginMember.getMemberId());
		member.setPassword(password);
		member.setMemberName(memberName);
		member.setGender(gender);

		java.sql.Date birthday = java.sql.Date.valueOf(birthday_);

		member.setBirthday(birthday);

		member.setEmail(email);
		member.setPhone(phone);
		member.setAddress(address);

		String hobby = "";
		if (hobby != null) {
			for (int i = 0; i < hobby_.length; i++) {
				// ,을 기준으로 하나의 문자열로 만듬, 마지막은 , 생략
				hobby += hobby_[i] != hobby_[hobby_.length - 1] ? hobby_[i] + "," : hobby_[i];
				// join으로 처리 가능
				// hobby = String.join(",", hobbyArr);
			}
		}
		member.setHobby(hobby);

		//
		int result = memberService.updateMember(member);
		String msg = "";
		if (result > 0) {
			// 기존 세션을 가져와서 세팅
			msg = "성공적으로 회원정보를 수정했습니다.";
			
			//세션정보 수정
			session.setAttribute("loginMember", member);
			// 아래도 가능
			// session.setAttribute("loginMember", memberService.selectOne(memberId));
		} else {
			msg = "회원정보를 수정하는데 실패했습니다.";
		}
		session.setAttribute("msg", msg);
		// 인덱스페이지가 이닌 정보보기 창으로 리턴
		response.sendRedirect(request.getContextPath() + "/member/memberView");
	}

}
