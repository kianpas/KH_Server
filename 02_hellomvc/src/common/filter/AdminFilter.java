package common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet Filter implementation class AdminFilter
 */
//@WebFilter(urlPatterns = { "/admin/memberList", "/admin/memberRoleUpdate", "/admin/memberFinder" })
//아래와 같이 admin의 모든 주소를 처리가능
@WebFilter("/admin/*")
public class AdminFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 로그인 여부 확인하기
		// 형변환 필요
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		HttpSession session = httpReq.getSession();

		// 세션이 유효하지않다면 새로만들기 때문에 null이 될 수 없으므로 판단 안함
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		//로그인멤버가 null이거나 관리자계정이 아닌 경우
		if (loginMember == null || !MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())) {
			session.setAttribute("msg", "관리자 로그인 후 사용할 수 있습니다.");
			httpRes.sendRedirect(httpReq.getContextPath());

			// 서블릿 이전에 리턴 처리됨
			return;
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
