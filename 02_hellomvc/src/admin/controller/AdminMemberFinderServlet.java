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

		// 1. 사용자 입력값 : 현재페이지 cPage, numberFormat예외 발생 가능성
		int numPerPage = 10;

		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));

		} catch (NumberFormatException e) {
			// 처리코드 없음, 기본값 1 유지
		}
		// 검색 정보를 맵으로 전달
		//밑에 시작페이지와 마지막페이지를 지정하여 pram값으로 전달 하는 것도 가능
		Map<String, String> param = new HashMap<>();
		param.put("searchType", searchType);
		param.put("searchKeyword", searchKeyword);

		System.out.println("param@servlet = " + param);

//		param.put("start", String.valueOf((cPage -1) * numPerPage + 1));
//		param.put("end", String.valueOf(cPage * numPerPage));

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

		// 과제 String url = request.getRequestURI() + "?searchType=" + searchType +
		// "&searchKeyword=" + searchKeyword ;

		// 쿼리url을 문자열로 전달
		String queryurl_ = request.getQueryString();
		// 이전의 쿼리문이 그대로 넘어가지 않도록 처리
		String queryurl = queryurl_.replace("&cPage=" + cPage, "");

		// MvcUtils에 쿼리 url을 넘겨주기
		String pageBar = MvcUtils.getPageBar(cPage, numPerPage, totalContents, url, queryurl);

		// 3. jsp에 html응답메세지 작성 위임
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp").forward(request, response);

	}

}
