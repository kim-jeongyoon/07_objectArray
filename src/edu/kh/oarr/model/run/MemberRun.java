package edu.kh.oarr.model.run;

import edu.kh.oarr.model.service.MemberService;

public class MemberRun {

	public static void main(String[] args) {
	
		MemberService service = new MemberService;
		
		service.displayMenu(); 

	}

}
