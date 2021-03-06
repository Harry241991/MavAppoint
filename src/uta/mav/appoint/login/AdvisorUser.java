package uta.mav.appoint.login;

import java.util.ArrayList;

import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.visitor.Visitor;

public class AdvisorUser extends LoginUser{
	String pnam;
	
	public AdvisorUser(String em, String p){
		super(em);
		pnam = p;
	}
	
	@Override
	public String getHeader(){
		return "advisor_header";
	}

	/**
	 * @return the pname
	 */
	@Override
	public String getPname() {
		return pnam;
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
