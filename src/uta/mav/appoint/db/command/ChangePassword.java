package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

public class ChangePassword extends SQLCmd{

	String email;
	String studentId;
	String password;
	
	public ChangePassword(String emailAddress, String studentId, String password){
		this.email = emailAddress;
		this.password = password;
		this.studentId = studentId;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "UPDATE user SET password=? WHERE email=? and userid=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1,password);
			statement.setString(2,email);
			statement.setString(3,studentId);
			updateRes = statement.executeUpdate();
			}
			catch(Exception e){
				System.out.println(e);
			}
	}

	@Override
	public void processResult() {
		result.add(updateRes);
	}
}
