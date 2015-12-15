package uta.mav.appoint;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;

import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.visitor.AppointmentVisitor;
import uta.mav.appoint.visitor.CancelAppointmentVisitor;
import uta.mav.appoint.visitor.GetNextAppointmentVisitor;
import uta.mav.appoint.visitor.Visitor;

/**
 * Servlet implementation class ViewAppointmentServlet
 */
@WebServlet("/WaitServlet")
public class WaitServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    HttpSession session;   
    private String header;
    /**
     * @see HttpServlet#HttpServlet()
     */

    public WaitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
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
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/waitlist.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		final String dburl="jdbc:mysql://localhost:3306/s";
        //final String dbName="root";
        final String driver="com.mysql.jdbc.Driver";
        try{
        	header = "templates/" + user.getHeader() + ".jsp";
		String advisor_name = request.getParameter("advisor_name");
		String appointment_date = request.getParameter("appointment_date");
		String appointment_type = request.getParameter("type");
		String description = request.getParameter("description");
		String student_id = request.getParameter("student_id");
		String email = request.getParameter("email");
		
		Class.forName(driver).newInstance();
	    
	    String userid = "root";
	    String password = "1234";
	    Connection conn = DriverManager.getConnection(dburl,userid,password);
	    @SuppressWarnings("deprecation")
		java.util.Date myDate = new java.util.Date(appointment_date);
	    java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        PreparedStatement p =(PreparedStatement) conn.prepareStatement("insert into waitlist(appointment_date,appointment_type,description,student_id,email,advisor_name) values(?,?,?,?,?,?)");
        p.setDate(1, sqlDate);
        p.setString(2, appointment_type);
        p.setString(3,description);
        p.setString(4, student_id);
        p.setString(5, email);
        p.setString(6,advisor_name);
         int j = p.executeUpdate();
         if (j > 0){
				response.setHeader("Refresh","2; URL=advising");
				request.getRequestDispatcher("/WEB-INF/jsp/views/success.jsp").forward(request,response);
			}
			else{
				response.setHeader("Refresh","2; URL=advising");
				request.getRequestDispatcher("/WEB-INF/jsp/views/failure.jsp").forward(request,response);
			}

         p.close();
        }catch(Exception e){
        	
        	response.sendRedirect("waitlist");
        }
	}
}

