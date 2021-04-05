package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class MvcUtils {

	/**
	 * 단방향 암호화 알고리즘 - md5 - sha1 160바이트 - sha256 256 부터 사용 추천 - sha512 512
	 * 
	 * 1. MessageDigest : 단방향암호화처리, 기록하기 어려움
	 * 
	 * 2. Base64 인코딩 처리 : 암호화된 byte[](이진데이터)를 64개의 문자로 변환 digest값을 변환처리
	 * 
	 * @return
	 */
	public static String getSha512(String password) {
		String encryptedPassword = null;

		// 1. 암호화
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}

		byte[] bytes = null;
		try {
			bytes = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		md.update(bytes);
		byte[] encryptedBytes = md.digest(); // 암호화 처리

		System.out.println("암호화 처리후 : " + new String(encryptedBytes));

		// 2. 문자 인코딩 처리
		encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
		System.out.println("인코딩 처리후 : " + encryptedPassword);
		return encryptedPassword;
	}

	/**
	 * 1. cPage 2. numPerPage 3. totalContents 총컨텐츠 수 4. url 이동할 주소
	 * /mvc/admin/memberList?cPage= ----------------------- 5. totalPage 전체 페이지 수 -
	 * pageNo 넘침방지 6. pageBarSize 페이지바에 표시할 페이지 개수 5로 지정 7. pageStart ~ pageEnd
	 * pageNo의 범위 8. pageNo 페이지 넘버를 출력할 증감변수
	 */
	public static String getPageBar(int cPage, int numPerPage, int totalContents, String url) {
		// 계속 변할 것 같으면 stringbuilder사용
		StringBuilder pageBar = new StringBuilder();

		// 총페이지를 페이지 수로 나누고 올림
		int totalPage = (int) Math.ceil((double) totalContents / numPerPage);
		int pageBarSize = 5;

		// cPage속성 추가전 키워드 작업
		// cPage이외의 다른 사용자 입력값 있는 경우 대비
		// /mvc/admin/memberFinder?type=id&kw=abc&cPage=
		// ?는 쿼리의 시작 &은 다른 쿼리와 함께 쓸 때 , 조건 확인 해주는 것

		url = (url.indexOf("?") > -1) ? url + "&" : url + "?";

		/*
		 * 
		 * > 1 2 3 4 5 ->1 6 7 8 9 10 ->6 11 12 13 14 15 ->11 ...
		 */

		int pageStart = (cPage - 1) / pageBarSize * pageBarSize + 1;
		int pageEnd = pageStart + pageBarSize - 1;

		// 증감변수는 pageStart부터 시작
		int pageNo = pageStart;

		// 1. 이전
		if (pageNo == 1) {

		} else {
			pageBar.append("<a href='" + url + "cPage=" + (pageNo - 1) + "'/>prev</a>\n");
		}

		// 2. pageNo 출력 , 11페이지를 넘어가면 탈출
		while (pageNo <= pageEnd && pageNo <= totalPage) {
			if (pageNo == cPage) {
				// 현재페이지와 페이지번호가 같다면 단순 문자
				pageBar.append("<span class='cPage'>" + pageNo + "</span>");
			} else {
				// 아니면 다른 페이지의 링크
				pageBar.append("<a href='" + url + "cPage=" + (pageNo) + "'/>" + pageNo + "</a>\n");

			}
			pageNo++;
		}

		// 3. 다음
		// 마지막 페이지가 포함된 페이지 바인 경우
		if (pageNo > totalPage) {

		} else {
			// 이미 위에서 더해졌으므로 +1 필요 없다
			pageBar.append("<a href='" + url + "cPage=" + (pageNo) + "'/>next</a>\n");

		}
//		아래와 같이 출력
//		<a href='/mvc/admin/memberList?cPage=5'/>prev</a>

//		<a href='/mvc/admin/memberList?cPage=6'/>6</a>
//		<span class='cPage'>7</span><a href='/mvc/admin/memberList?cPage=8'/>8</a>
//		<a href='/mvc/admin/memberList?cPage=9'/>9</a>
//		<a href='/mvc/admin/memberList?cPage=10'/>10</a>

//		<a href='/mvc/admin/memberList?cPage=11'/>next</a>

		return pageBar.toString();
	}

	public static String getPageBar(int cPage, int numPerPage, int totalContents, String url, String queryurl) {
		// 계속 변할 것 같으면 stringbuilder사용
		StringBuilder pageBar = new StringBuilder();
		
		// 총페이지를 페이지 수로 나누고 올림
		int totalPage = (int) Math.ceil((double) totalContents / numPerPage);
		int pageBarSize = 5;
		
		// cPage속성 추가전 키워드 작업
		// cPage이외의 다른 사용자 입력값 있는 경우 대비
		// /mvc/admin/memberFinder?type=id&kw=abc&cPage=
		// ?는 쿼리의 시작 &은 다른 쿼리와 함께 쓸 때 , 조건 확인 해주는 것
		
		url = (url.indexOf("?") > -1) ? url + "&" : url + "?";
		url += queryurl;
		//url += "searchType=" + session.getAttribute("searcType")+ "&searchKeyword=" + session.getAttribute("searcKeyword");
		System.out.println(url);
		/*
		 * 
		 * > 1 2 3 4 5 ->1 6 7 8 9 10 ->6 11 12 13 14 15 ->11 ...
		 */

		int pageStart = (cPage - 1) / pageBarSize * pageBarSize + 1;
		int pageEnd = pageStart + pageBarSize - 1;

		// 증감변수는 pageStart부터 시작
		int pageNo = pageStart;

		// 1. 이전
		if (pageNo == 1) {

		} else {
			pageBar.append("<a href='" + url +  "&cPage=" + (pageNo - 1) + "'/>prev</a>\n");
		}

		// 2. pageNo 출력 , 11페이지를 넘어가면 탈출
		while (pageNo <= pageEnd && pageNo <= totalPage) {
			if (pageNo == cPage) {
				// 현재페이지와 페이지번호가 같다면 단순 문자
				pageBar.append("<span class='cPage'>" + pageNo + "</span>");
			} else {
				// 아니면 다른 페이지의 링크
				pageBar.append("<a href='" + url + "&cPage=" + (pageNo) + "'/>" + pageNo + "</a>\n");

			}
			pageNo++;
		}

		// 3. 다음
		// 마지막 페이지가 포함된 페이지 바인 경우
		if (pageNo > totalPage) {

		} else {
			// 이미 위에서 더해졌으므로 +1 필요 없다
			pageBar.append("<a href='" + url + "&cPage=" + (pageNo) + "'/>next</a>\n");

		}
//		아래와 같이 출력
//		<a href='/mvc/admin/memberList?cPage=5'/>prev</a>

//		<a href='/mvc/admin/memberList?cPage=6'/>6</a>
//		<span class='cPage'>7</span><a href='/mvc/admin/memberList?cPage=8'/>8</a>
//		<a href='/mvc/admin/memberList?cPage=9'/>9</a>
//		<a href='/mvc/admin/memberList?cPage=10'/>10</a>

//		<a href='/mvc/admin/memberList?cPage=11'/>next</a>

		return pageBar.toString();
	}

}
