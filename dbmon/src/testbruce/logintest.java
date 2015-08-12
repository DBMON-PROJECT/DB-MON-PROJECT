package testbruce;

import java.util.ArrayList;

import login.dto.LoginDto;
import login.service.LoginService;

public class logintest {
	public static void main(String[] args) {
		
		ArrayList<LoginDto> list = new ArrayList<LoginDto>();
		
		LoginService loginService = LoginService.getInstance();
		
		list = loginService.getUserInfo(loginService.readRegistry());
		
		 String path = logintest.class.getResource("").getPath();
		 System.out.println(path);
	}
}
