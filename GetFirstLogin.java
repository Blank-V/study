package cookietest;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetFirstLogin
 */
public class GetFirstLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");		
		/*
		 * 处理用户登录页面
		 * 在客户端创建Cookie
         *直接输出用户的登录表单信息
         **/
		    Cookie usernameCookie = new Cookie("c-username", request.getParameter("username"));
		    /*
		     * 设置usernameCookie的其他属性
		     */
		    setCookieProperty(usernameCookie,request.getParameter("username"));
			response.addCookie(usernameCookie);
			Cookie pwdCookie = new Cookie("c-pwd", request.getParameter("pwd"));
			response.addCookie(pwdCookie);
			
			String formHead="<HTML>\n" +
			"<HEAD><TITLE>"+ "把Cookie写入客户端" + "</TITLE></HEAD>\n" +
	        "<BODY BGCOLOR=\"#FDF5E6\">\n" +
	        "<H1 ALIGN=CENTER>" + "--欢迎您首次光临--" + "</H1>\n" +       
	        "<TR BGCOLOR=\"#FFAD00\">\n" ;      			
			String formEnd="</body></html>";
			PrintWriter out = response.getWriter();
			
			out.println(formHead);
			out.println("<p>您的用户名是："+ request.getParameter("username"));
			out.println("<p>您的密码是："+request.getParameter("pwd"));
			out.println(formEnd);		
	}
	
	protected void setCookieProperty(Cookie c, String cvalue){
		c.setValue(cvalue);		
		c.setPath("/");

		}




}
