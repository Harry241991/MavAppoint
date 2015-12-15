package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.visitor.AppointmentVisitor;
import uta.mav.appoint.visitor.CancelAppointmentVisitor;
import uta.mav.appoint.visitor.Visitor;

/**
 * Servlet implementation class ViewAppointmentServlet
 */
@WebServlet("/DefaultersServlet")
public class DefaultersServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    HttpSession session;   
    String header;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DefaultersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user != null){
			try{
				header = "templates/" + user.getHeader() + ".jsp";
				DatabaseManager db = new DatabaseManager();
				ArrayList<Object> gt = db.getDefaulters();
				if (gt.size() != 0&& gt != null){
					session.setAttribute("defaulters", gt);
				}
			}
			catch(Exception e){
				System.out.printf(e.toString());
			}
		}
		else{
			header = "templates/header.jsp";
		}
		
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/defaulters.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		try{
		if (user != null){
			
				header = "templates/" + user.getHeader() + ".jsp";
				Visitor v = new CancelAppointmentVisitor();
				int id = Integer.parseInt(request.getParameter("cancel_button"));
				user.accept(v,(Object)id);
				v = new AppointmentVisitor();
				ArrayList<Object> appointments = user.accept(v,null);
				if (appointments.size() != 0&& appointments != null){
					session.removeAttribute("defaulters");
					session.setAttribute("defaulters", appointments);
					response.setHeader("Refresh","2; URL=defaulters");
					request.getRequestDispatcher("/WEB-INF/jsp/views/success.jsp").forward(request,response);
					
				}
		
		}
		else{
			header = "templates/header.jsp";
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/defaulters.jsp").forward(request,response);
		}
		catch(Exception e){
			System.out.printf("Error in Servlet: " + e.toString()+"\n");
		}
	}
}
