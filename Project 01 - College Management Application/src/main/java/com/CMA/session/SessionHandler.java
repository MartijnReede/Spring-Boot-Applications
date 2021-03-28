package com.CMA.session;

public class SessionHandler{
	
	private static Session session = new Session(false, "", -1);
	
	public static void setSession(Session session) {
		SessionHandler.session=session;
	}
	
	public static Session getSession() {
		return session;
	}
	
	public static Boolean accessNotAllowed(String userType) {
		if (session.loggedIn==false) return true;
		else if (!(userType.equals(session.getUserType()))) return true;
		else return false;
	}
	
	public static String redirect() {
		if (session.getUserType().equals("ADMIN")) return "redirect:/admin";
		else if (session.getUserType().equals("STUDENT")) return "redirect:/students";
		else if (session.getUserType().equals("TEACHER")) return "redirect:/teachers";
		else return "redirect:/"; 
	}
	
}
