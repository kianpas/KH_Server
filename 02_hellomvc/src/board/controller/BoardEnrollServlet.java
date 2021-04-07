package board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MvcFileRenamePolicy;

/**
 * Servlet implementation class BoardEnorollServlet
 */
@WebServlet("/board/boardEnroll")
public class BoardEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardEnrollServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 0. form의 속성 enctype="multipart/form-data" 추가
	 * 1. MultipartRequest 객체 생성 : 서버컴퓨터 파일 저장
	 * 		- request
	 *    	- 저장경로
	 *    	- encoding
	 *    	- 최대허용크기
	 *    	- 파일명 변경정책 객체
	 * 2. db에 파일정보를 저장 
	 * 		- 사용자가 저장한 파일명 original_filename
	 * 		- 실제 저장된 파일명 renamed_filename
	 * 
	 * MultipartRequest객체를 사용하면, 
	 * 기존 HttpServletRequest에서는 사용자입력값에 접근할 수 없다.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//1. MultipartRequest 객체 생성
		// /WebContent/upload/board/업로드파일명.jpg
		//was에 접근할 수 있는 것 
		//web root dir를 절대경로로 반환
		String saveDirectory = getServletContext().getRealPath("/upload/board");
		System.out.println("saveDirectory@servlet = " + saveDirectory);
		
		//최대 파일 허용크기 10mb = 10 * 1kb * 1kb
		int maxPostSize = 10* 1024*1024; //바이트 단위
		
		//인코딩
		String encoding = "utf-8";
	
		//파일명 변경정책 객체
		//중복파일인 경우, numbering처리, 보안적으로 안좋으므로 다른 방식 사용
		//FileRenamePolicy policy = new DefaultFileRenamePolicy();
		//파일 안전성, 사용자 보안성을 위해 아래와 같이 저장해야함
		//filerename : 202210406191919_123.jpg
		FileRenamePolicy policy = new MvcFileRenamePolicy();
		
		MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);

		//2. db에 게시글/첨부파일 정보 저장
		
		
		//2-1. 사용자 입력값처리
		//이제 request 사용 안함
//		String title = request.getParameter("title");
//		String writer = request.getParameter("writer");
//		String content = request.getParameter("content");
		
		String title = multipartRequest.getParameter("title");
		String writer = multipartRequest.getParameter("writer");
		String content = multipartRequest.getParameter("content");
		
		//업로드한 파일명
		String originalFileName = multipartRequest.getOriginalFileName("upFile");
		String renamedFileName = multipartRequest.getFilesystemName("upFile");
		

		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		System.out.println(board);
		
		//	//같은 파일 이름이 있는 경우
		//multipartRequest.getFile("upFile"):File != null 이것도 가능
		if(originalFileName != null) {
			Attachment attach = new Attachment();
			attach.setOriginalFileName(originalFileName);
			attach.setRenameFileName(renamedFileName);
			board.setAttach(attach);
		}
		
		//2-2. 업무로직 : db에 insert
		int result = boardService.insertBoard(board);

		// 3. dml요청 : 리다이렉트 % 사용자 피드백
		HttpSession session = request.getSession();
		String msg = "";
		if (result > 0) {
			msg = "게시글 등록 성공";
			//response.sendRedirect(location);
			
		} else {
			msg = "게시글 등록 실패";
		}
		session.setAttribute("msg", msg);
		response.sendRedirect(request.getContextPath() + "/board/boardList");

		// /mvc/board/boardList

	}

}
