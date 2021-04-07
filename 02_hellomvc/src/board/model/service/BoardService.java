package board.model.service;

import board.model.dao.BoardDao;
import board.model.vo.Attachment;
import board.model.vo.Board;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

public class BoardService {

	private BoardDao boardDao = new BoardDao();

	public List<Board> selectList(int start, int end) {
		
		Connection conn = getConnection();

		List<Board> list = boardDao.selectList(conn, start, end);

		close(conn);

		return list;
	}

	public int selectBoardCount() {
		
		Connection conn = getConnection();

		int totalContents = boardDao.selectBoardCount(conn);

		close(conn);
		
		return totalContents;
	}

	/**
	 * 첨부파일이 있는 경우, attach객체를 attachment테이블에 등록한다. - board등록, attachment등록은 하나의
	 * 트랜젝션으로 처리되어야 한다. - 하나의 Connection에 의해 처리되어야 한다.
	 * 
	 * @param board
	 * @return
	 */
	public int insertBoard(Board board) {

		Connection conn = getConnection();
		
		int result = 0;

		try {
			result = boardDao.insertBoard(conn, board);
			// 생성된 board_no를 가져오기
			int boardNo = boardDao.selectLastBoardNo(conn);
			System.out.println("boardNo@service = " + boardNo);

			if (board.getAttach() != null) {

				// 참조할 boardNo세팅
				//첨부파일용 dao
				board.getAttach().setBoardNo(boardNo);
				result = boardDao.insertAttachment(conn, board.getAttach());
			}
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			
			e.printStackTrace();
			result = 0;
		} finally {
			close(conn);
		}
//		
//		if(result > 0) {
//			commit(conn);
//		} else {
//			rollback(conn);
//		}

		return result;
	}

	public Board selectOne(int no) {
		
		Connection conn = getConnection();

		Board board = boardDao.selectOne(conn, no);

		close(conn);

		return board;
		
	}

	public Attachment findAttach(int no) {
		Connection conn = getConnection();

		Attachment attach = boardDao.findAttach(conn, no);

		close(conn);

		return attach;
	}

}
