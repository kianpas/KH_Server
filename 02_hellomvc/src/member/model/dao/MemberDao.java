package member.model.dao;

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
import java.util.Map;
import java.util.Properties;


import static common.JDBCTemplate.*;
import member.model.vo.Member;

public class MemberDao {

	private Properties prop = new Properties();

	public MemberDao() {
		String fileName = MemberDao.class.getResource("/sql/member/member-query.properties").getPath();

		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Member selectOne(Connection conn, String str) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOne");

		Member m = null;

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, str);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_Date");

				m = new Member(memberId, password, memberName, memberRole, gender, birthday, email, phone, address,
						hobby, enrollDate);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			close(rset);
			close(pstmt);

		}

		return m;
	}

	public int insertMember(Connection conn, Member member) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMember");
		int result = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender());
			pstmt.setDate(5, member.getBirthday());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int updateMember(Connection conn, Member member) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateMember");
		int result = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMemberName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getPhone());
			pstmt.setString(7, member.getAddress());
			pstmt.setString(8, member.getHobby());

			pstmt.setString(9, member.getMemberId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int deleteMember(Connection conn, String memberId) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteMember");

		int result = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberId);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			close(pstmt);
		}

		return result;
	}

	public int updatePassword(Connection conn, Member member) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updatePassword");
		int result = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMemberId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public List<Member> selectList(Connection conn) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectList");
		ResultSet rset = null;

		List<Member> list = null;
		Member m = null;

		try {

			pstmt = conn.prepareStatement(sql);

			rset = pstmt.executeQuery();
			list = new ArrayList<Member>();
			while (rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_Date");

				m = new Member(memberId, password, memberName, memberRole, gender, birthday, email, phone, address,
						hobby, enrollDate);

				list.add(m);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int updateMemberRole(Connection conn, Member member) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateMemberRole");
		int result = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getMemberRole());
			pstmt.setString(2, member.getMemberId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(conn);
		}

		return result;
	}

	public List<Member> searchMember(Connection conn, Map<String, String> param) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("searchMember");
		// select * from member where member_id like %a%
		// select * from member where member_name like %a%
		// select * from member where gender = "M"
		// 스위치문에는 객체는 불가능
		switch (param.get("searchType")) {
		case "memberId":
			sql += " member_id like '%" + param.get("searchKeyword") + "%'";
			break;
		case "memberName":
			sql += " member_name like '%" + param.get("searchKeyword") + "%'";
			break;
		case "gender":
			sql += " gender = '" + param.get("searchKeyword") + "'";
			break;
		}
		System.out.println("query@dao = " + sql);
		ResultSet rset = null;

		List<Member> list = null;
		Member m = null;

		try {

			pstmt = conn.prepareStatement(sql);

			rset = pstmt.executeQuery();

			list = new ArrayList<Member>();
			while (rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_Date");

				m = new Member(memberId, password, memberName, memberRole, gender, birthday, email, phone, address,
						hobby, enrollDate);

				list.add(m);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			close(rset);
			close(pstmt);
		}

		return list;
	}

	public List<Member> selectList(Connection conn, int start, int end) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectPagedList");
		ResultSet rset = null;

		List<Member> list = null;
		Member m = null;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rset = pstmt.executeQuery();
			list = new ArrayList<Member>();
			while (rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_Date");

				m = new Member(memberId, password, memberName, memberRole, gender, birthday, email, phone, address,
						hobby, enrollDate);

				list.add(m);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int selectMemberCount(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectMemberCount");

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

			close(pstmt);
		}

		return totalContents;
	}

	public List<Member> searchMember(Connection conn, Map<String, String> param, int start, int end) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("searchPagedMember");
		// select * from member where member_id like %a%
		// select * from member where member_name like %a%
		// select * from member where gender = "M"
		// 스위치문에는 객체는 불가능

		switch (param.get("searchType")) {
		case "memberId":
			sql += " where member_id like '%" + param.get("searchKeyword") + "%') M where rnum between ? and ?";
			break;
		case "memberName":
			sql += " where member_name like '%" + param.get("searchKeyword") + "%') M where rnum between ? and ?";
			break;
		case "gender":
			sql += " where gender = '" + param.get("searchKeyword") + "') M where rnum between ? and ?";
			break;
		}

		ResultSet rset = null;

		List<Member> list = null;
		Member m = null;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rset = pstmt.executeQuery();

			// System.out.println(sql);

			list = new ArrayList<Member>();
			while (rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String memberRole = rset.getString("member_role");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_Date");

				m = new Member(memberId, password, memberName, memberRole, gender, birthday, email, phone, address,
						hobby, enrollDate);

				list.add(m);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int selectPagedMemberCount(Connection conn, Map<String, String> param) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectPagedMemberCount");

		switch (param.get("searchType")) {
		case "memberId":
			sql += " member_id like '%" + param.get("searchKeyword") + "%'";
			break;
		case "memberName":
			sql += " member_name like '%" + param.get("searchKeyword") + "%'";
			break;
		case "gender":
			sql += " gender = '" + param.get("searchKeyword") + "'";
			break;
		}

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

			close(pstmt);
		}

		return totalContents;
	}

	// 중복제거를 위한 메소드 생성
	public String setQuery(String query, String searchType, String searchKeyword) {
		switch (searchType) {
		case "memberId":
			query = query.replace("#", " member_id like '%" + searchKeyword + "%'");
			break;
		case "memberName":
			query = query.replace("#", " member_name like '%" + searchKeyword + "%'");
			break;
		case "gender":
			query = query.replace("#", " gender = '" + searchKeyword + "'");
			break;

		}
		return query;
	}
}
