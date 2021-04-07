package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import common.MvcUtils;

/**
 * 페이징 - 한페이자당 게시글 수 5개 numPerPage 5개
 */
@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 0. 인코딩처리는 encodingFilter가 선처리
		// 1. 사용자 입력값
		final int numPerPage = 5;
		int cPage = 1;

		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));

		} catch (NumberFormatException e) {
			// 처리코드 없음, 기본값 1 유지
		}

		// 2.업무로직
		// a.contents 영역 : start ~ end
		//cPage 1, numPerPage 5 -> 1 ~ 5
		//Cpage 2, numPerPage 5 -> 6 ~ 10
		int end = cPage * numPerPage;

		int start = (cPage - 1) * numPerPage + 1;
		List<Board> list = boardService.selectList(start, end);

		// b. pageBar 영역
		int totalContents = boardService.selectBoardCount();
		String url = request.getRequestURI();
		String pageBar = MvcUtils.getPageBar(cPage, numPerPage, totalContents, url);

		// 3. 응답 html처리 jsp에 위임
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/board/boardList.jsp").forward(request, response);
	}

}
