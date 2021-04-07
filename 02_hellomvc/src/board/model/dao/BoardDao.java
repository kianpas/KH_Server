package board.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static common.JDBCTemplate.*;

import board.model.exception.BoardException;
import board.model.vo.Attachment;
import board.model.vo.Board;

public class BoardDao {

	private Properties prop = new Properties();

	public BoardDao() {
		// board-query.properties의 내용 읽어와서 prop에 저장
		// resources/sql/board-query.properties가 아니라
		// WEB-INF/classes/sql/board-query.properties에 접근해야함

		String fileName = BoardDao.class.getResource("/sql/board/board-query.properties").getPath();

		// 이것도 가능
		// String fileName = "/sql/board/board-query.properties";
		// String absPath = BoardDao.class.getResource(fileName).getPath();

		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Board> selectList(Connection conn, int start, int end) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectList");
		List<Board> list = null;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rset = pstmt.executeQuery();

			list = new ArrayList<>();

			while (rset.next()) {
				Board board = new Board();
				int no = rset.getInt("no");
				String title = rset.getString("title");
				String writer = rset.getString("writer");
				String content = rset.getString("content");
				Date regDate = rset.getDate("reg_date");
				int readCount = rset.getInt("read_count");
				board.setNo(no);
				board.setTitle(title);
				board.setWriter(writer);
				board.setContent(content);
				board.setRegDate(regDate);
				board.setReadCount(readCount);

				// 첨부파일이 있는 경우
				if (rset.getInt("attach_no") != 0) {
					Attachment attach = new Attachment();
					attach.setNo(rset.getInt("attach_no"));
					attach.setBoardNo(rset.getInt("no"));
					attach.setOriginalFileName(rset.getString("original_filename"));
					attach.setRenameFileName(rset.getString("renamed_filename"));
					attach.setStatus("Y".equals(rset.getString("status")) ? true : false);

					board.setAttach(attach);
				}

				list.add(board);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int selectBoardCount(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectBoardCount");

		ResultSet rset = null;
		int totalContents = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				// cnt는 별칭
				totalContents = rset.getInt("cnt");
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			close(rset);
			close(pstmt);
		}

		return totalContents;
	}

	public int insertBoard(Connection conn, Board board) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertBoard");
		int result = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			throw new BoardException("게시물 등록 오류", e);
		} finally {

			close(pstmt);
		}

		return result;
	}

	public int selectLastBoardNo(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectLastBoardNo");

		ResultSet rset = null;
		int boardNo = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				// cnt는 별칭
				boardNo = rset.getInt("board_no");
			}

		} catch (SQLException e) {

			throw new BoardException("게시물 번호 등록 오류", e);

		} finally {

			close(rset);
			close(pstmt);
		}

		return boardNo;

	}

	public int insertAttachment(Connection conn, Attachment attach) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachment");
		int result = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, attach.getBoardNo());
			pstmt.setString(2, attach.getOrginalFileName());
			pstmt.setString(3, attach.getRenameFileName());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// 런타입 익셉션을 상속한 커스텀 익셉션으로 던짐
			throw new BoardException("게시물 첨부파일 등록 오류", e);
		} finally {

			close(pstmt);
		}

		return result;
	}

	public Board selectOne(Connection conn, int no) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOne");
		Board board = null;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				board = new Board();
				// int no = rset.getInt("no");
				String title = rset.getString("title");
				String writer = rset.getString("writer");
				String content = rset.getString("content");
				Date regDate = rset.getDate("reg_date");
				int readCount = rset.getInt("read_count");
				board.setNo(no);
				board.setTitle(title);
				board.setWriter(writer);
				board.setContent(content);
				board.setRegDate(regDate);
				board.setReadCount(readCount);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return board;
	}

	public Attachment findAttach(Connection conn, int no) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("findAttach");
		Attachment attach = null;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				attach = new Attachment();
				int no_ = rset.getInt("no");
				int boardNo = rset.getInt("board_no");
				String originalFileName = rset.getString("original_filename");
				String renameFileName = rset.getString("renamed_filename");
				String status = rset.getString("status");

				attach.setNo(no_);
				attach.setBoardNo(boardNo);
				attach.setOriginalFileName(originalFileName);
				attach.setRenameFileName(renameFileName);
				attach.setStatus("Y".equals(status) ? true : false);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return attach;

		
	}

}
