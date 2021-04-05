package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class checkIdDuplicateServlet
 */
@WebServlet("/member/checkIdDuplicate")
public class CheckIdDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckIdDuplicateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값 처리
		String memberId = request.getParameter("memberId");
		System.out.println("memberId@servlet = " + memberId);
		
		//2. 업무로직 : 해당 id를 db에서 조회
		
		Member member = new MemberService().selectOne(memberId);
		//null이 나올 경우 false
		boolean available = member == null;
		request.setAttribute("available", available);
		
		//3. 응답처리 : 사용가능여부를 jsp에서 html로 작성
		//여기서는 리다이렉션 처리 안한 것
		RequestDispatcher reqDispatcher = request.getRequestDispatcher("/WEB-INF/views/member/checkIdDuplicate.jsp");
		
		reqDispatcher.forward(request, response);
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
