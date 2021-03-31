package member.model.service;

import java.sql.Connection;
import java.util.List;

import member.model.dao.MemberDao;
import member.model.vo.Member;
import static common.JDBCTemplate.*;

public class MemberService {

	private MemberDao memberDao = new MemberDao();

	public static final String MEMBER_ROLE = "U";
	public static final String ADMIN_ROLE = "A";

	public Member selectOne(String str) {
		Connection conn = getConnection();
		Member member = memberDao.selectOne(conn, str);

		close(conn);

		return member;
	}

	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn, member);
		if (result > 0) {
			close(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public int updateMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.updateMember(conn, member);
		if (result > 0) {
			close(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public int deleteMember(String memberId) {

		Connection conn = getConnection();
		int result = memberDao.deleteMember(conn, memberId);
		if (result > 0) {
			close(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public int updatePassword(Member member) {
		Connection conn = getConnection();
		int result = memberDao.updatePassword(conn, member);
		
		if (result > 0) {
			close(conn);
		} else {
			rollback(conn);
		}
		return result;
	}
}
