package member.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberEnrollServlet
 */
@WebServlet("/member/memberEnroll")
public class MemberEnrollServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 가입페이지 호출을 위한 기본적인 리퀘스트
		request.getRequestDispatcher("/WEB-INF/views/member/memberEnroll.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 대소문자 상관없음. 요청한 view단의 charset값과 동일해야 한다.
		// request.setCharacterEncoding("utf-8");

		// 입력값 가져오기
		String memberId = request.getParameter("memberId");
		// System.out.println(memberId);
		String password = MvcUtils.getSha512(request.getParameter("password"));

		String memberName = request.getParameter("memberName");
		String gender = request.getParameter("gender");
		String birthday_ = request.getParameter("birthday");

		// 포맷팅 불필요
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
//				SimpleDateFormat sqlsdf = new SimpleDateFormat("yyyy-mm-dd");
		// System.out.println(birthday_);

		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		// 취미를 배열로 가져오기
		String[] hobby_ = request.getParameterValues("hobby");

		Member member = new Member();

		// 아래와 같이 가능
		// Member member = new Member(memberId, password, memberName,
		// MemberService.MEMBER_ROLE, gender, birthday, email,
		// phone, address, hobby, null);

		// 페이지 시작후 null값 전달 방지 및 조건 검사
		// 조건 검사는 jsp에서 하자
		if (memberId != null) {
			member.setMemberId(memberId);
			member.setPassword(password);
			member.setMemberName(memberName);
			member.setGender(gender);

			java.sql.Date birthday = java.sql.Date.valueOf(birthday_);
			System.out.println(birthday);
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

			int result = memberService.insertMember(member);

			System.out.println(member);
			if (result > 0) {
				// 가입성공
				request.setAttribute("signUpMsg", "회원가입 성공!");
				// 가능은 하나 리다이렉트 처리할 것 밑에서
				// request.setAttribute("loc2", request.getContextPath());
			} else {
				// 가입실패
				request.setAttribute("signUpMsg", "회원가입 실패!");
			}
			// 인덱스페이지로 무조건 리다이렉트, 페이지에 남아있을 수 있으므로
			response.sendRedirect(request.getContextPath());
		}

	}

}
