package uta.mav.appoint.login;

import java.util.ArrayList;

import uta.mav.appoint.visitor.Visitor;

public class StudentUser extends LoginUser{
	
	String pname;

	public StudentUser(String em,String p){
		super(em);
		pname = p;
	}

	@Override
	public String getHeader(){
		return "student_header";
	}
	@Override
	public String getPname() {
		// TODO Auto-generated method stub
		return pname;
	}
	@Override
	public void accept(Visitor v){
		v.check(this);
	}
	
	@Override
	public ArrayList<Object> accept(Visitor v, Object o){//allow javabean to be passed in
		return v.check(this,o);
	}
}
