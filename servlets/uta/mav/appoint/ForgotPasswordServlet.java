package uta.mav.appoint;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;

import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;

/**
 * Servlet implementation class ForgotPasswordServlet
 */
@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	private String header;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		try{
		if (user != null){
			
				header = "templates/" + user.getHeader() + ".jsp";
				
		}
		else{
			if (user == null){
				user = new LoginUser();
				session.setAttribute("user", user);
			}
			header = "templates/header.jsp";
		}
		}catch(Exception e){}
	
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/forgotpassword.jsp").forward(request,response);
	}
	public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
    throws IOException, ServletException {
    	
    	
    }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String emailAddress = request.getParameter("emailAddress");
		String userId = request.getParameter("userid");
		GetSet sets = new GetSet();
		
		try{
			sets.setEmailAddress(emailAddress);
			sets.setStudentId(userId);
			sets.setPassword(RandomStringUtils.randomAlphanumeric(8));
			DatabaseManager dbm = new DatabaseManager();
			int status = dbm.changePassword(sets);
			if(status ==  0){
				request.setAttribute("status", "Invalid userId or emailAddress");
				response.sendRedirect("forgotpassword");
			}
			else{
				sendEmail(emailAddress, sets.getPassword());
				session.setAttribute("status", "Password is reset and emailed to you.");
				response.sendRedirect("login");
			}
		}
		catch(Exception e){
			
			response.sendRedirect("forgotpassword");
		}
	}

	private void sendEmail(String emailAddress, String password) {
		try{
		String to = emailAddress;
		String subject = "Password Reset";
		String body = "Please find the new generated password below. In case you haven't generated a new password please report it to the university. \n\n"+password;
		String from = "mudassir.m3@gmail.com";
		String pw = "threemistakesofmylife";
		String host = "smtp.gmail.com";
		String port = "587";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.user",from);
		properties.put("mail.smtp.password",pw);
		properties.put("mail.smtp.port",port);
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.socketFactory.port","465");
		properties.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
		
		
		
		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator(){
					protected PasswordAuthentication getPasswordAuthentication(){
						return new PasswordAuthentication("mudassir.m3@gmail.com","threemistakesofmylife");
					}
		});
		javax.mail.Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		message.setText(body);
		message.setSubject(subject);
		Transport.send(message);
		}
		catch(Exception mex){
			mex.printStackTrace();
		}		
	}
}
