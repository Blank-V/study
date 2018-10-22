package cookietest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;

import javax.servlet.http.Cookie;
/**
 * Servlet implementation class GetLogin
 */
public class DoLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 private int number;
@Override
public void init() throws ServletException {


String path = this.getServletConfig().getServletContext().getRealPath("num.txt");
File file = new File(path);
if(!file.exists())
{
	try {
		file.createNewFile();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	
}
@Override
public void destroy() {
	
	String path = this.getServletConfig().getServletContext().getRealPath("num.txt");
	File writenum =new File(path);
	BufferedWriter out;
	try {
		out = new BufferedWriter(new FileWriter(writenum));
		out.write((int)number);
		out.flush();
		out.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	number=1;
	super.destroy();
}	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		number++;
		
		String username=null;//存放从Cookie中获取的用户名
		String pwd=null;//存放从Cookie中获取的密码
		boolean hasCookies=false;//检查客户端是否已有用户名和密码的Cookies

		
		String logincount="1";
		String logintime="";
		
		Cookie[] cookies = request.getCookies();
		/*
		 * 判断客户端是否已经有用户名和密码的Cookies
		 * 若有则把Cookie读取出来
		 * 否则下面在客户端创建Cookie
		 **/
		if (cookies != null) {
			/*
			 * 添加登陆次数cookie
			 */
			String countString =getCookieValue(request,"logincount","1");
			int count=1;
			count=Integer.parseInt(countString);
			Cookie a=new Cookie("logincount", String.valueOf(count+1));
			a.setMaxAge(60*60*24);
			response.addCookie(a);
			
			
			
			/*
			 * 添加时间cookie
			 */
			Cookie systime=new Cookie("logintime", getSystemTime());
			systime.setMaxAge(60*60*24);
			response.addCookie(systime);
			
			for(int i=0; i<cookies.length; i++) {
					Cookie c = cookies[i];
					if (c.getName().equals("c-username"))
						username=c.getValue();
						
					if (c.getName().equals("c-pwd"))
						pwd=c.getValue();
					
					if(c.getName().equals("logintime"))
						logintime=c.getValue();
					
					if(c.getName().equals("logincount"))
						logincount=c.getValue();
					/*
					 * 输出Cookie的各个属性
					 */
					
					
					if (pwd!=null && username!=null)
						{hasCookies=true;
						break;
						}
					}
		}				
		/*if 存在本网站需求的Cookie
		 * then 则无需登录
		 * else 转到登录网页
		 * */
		if(hasCookies){
			String formHead="<HTML>\n" +
			"<HEAD><TITLE>"+ "读取Cookies" + "</TITLE></HEAD>\n" +
	        "<BODY BGCOLOR=\"#FDF5E6\">\n" +
	        "<H1 ALIGN=CENTER ><font color=\"red\">" + "读取Cookies，您不用登陆，欢迎您再次光临!!" + "</font></H1>\n" +       
	        "<TR BGCOLOR=\"#FFAD00\">\n" ;      			
			String formEnd="</body></html>";
			PrintWriter out = response.getWriter();
			
			out.println(formHead);
			out.println("<p>您的用户名是："+username);
			out.println("<p>您的密码是："+pwd);
			
			out.println("<p>登陆次数："+logincount);
			out.println("<p>登陆时间："+logintime);
			
			out.println("<p>File中登录次数："+number);

			out.println(formEnd);
		}
		else{
			
			response.sendRedirect("firstlogin.html");
			}	
	}
	public  String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName())) {
					return(cookie.getValue());
				}
				}
			}
		return(defaultValue);
	}
	public String getSystemTime(){
		String t=null;

		  Calendar   calendar=Calendar.getInstance();   
	      int   year=calendar.get(Calendar.YEAR);   
	      int   month=calendar.get(Calendar.MONTH)+1;   
	      int   day=calendar.get(Calendar.DATE);   
	      int   hour=calendar.get(Calendar.HOUR_OF_DAY);   
	      int   minute=calendar.get(Calendar.MINUTE);   
	      int   second=calendar.get(Calendar.SECOND);   
	      t=year+"-"+month+"-"+day+"___"+hour+":"+minute+":"+second;     
		
		return t;
	}


}
