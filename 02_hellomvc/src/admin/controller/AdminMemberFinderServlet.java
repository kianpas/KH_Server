package admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Servlet implementation class AdminiMemberFinderServlet
 */
@WebServlet("/admin/memberFinder")
public class AdminMemberFinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 사용자 입력값 처리
		String searchType = request.getParameter("searchType");
		String searchKeyword = request.getParameter("searchKeyword");

		HttpSession session = request.getSession();
		
		// 검색 정보를 맵으로 전달
		Map<String, String> param = new HashMap<>();
		param.put("searchType", searchType);
		param.put("searchKeyword", searchKeyword);

		System.out.println("param@servlet = " + param);

		// 1. 사용자 입력값 : 현재페이지 cPage, numberFormat예외 발생 가능성
		int numPerPage = 10;

		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));

		} catch (NumberFormatException e) {
			// 처리코드 없음, 기본값 1 유지
		}

		// 시작 페이지, 끝 페이지
		int end = cPage * numPerPage;
		// int start = end - (numPerPage -1);
		int start = (cPage - 1) * numPerPage + 1;

		// 2. 업무 로직
		// 여러개가 나와야하므로 list 리턴, param과 start, end를 매개변수로 사용
		List<Member> list = memberService.searchMember(param, start, end);
		
		// 전체 목록 수
		int totalContents = memberService.selectPagedMemberCount(param);
		System.out.println("totalcContents@servlet = " + totalContents);

		String url = request.getRequestURI(); // /mvc/admin/memberFinder
//		//+ "&searchType="+searchType +"&searchKeyword="+searchKeyword;
		
		String queryurl_ = request.getQueryString();
		String queryurl = queryurl_.replace("&cPage="+ cPage, "");
		
		session.setAttribute("searchType", searchType);
		session.setAttribute("searchKeyword", searchKeyword);
		
		
		String pageBar = MvcUtils.getPageBar(cPage, numPerPage, totalContents, url, queryurl);
		
	
		// 3. jsp에 html응답메세지 작성 위임
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp").forward(request, response);

	}

}
