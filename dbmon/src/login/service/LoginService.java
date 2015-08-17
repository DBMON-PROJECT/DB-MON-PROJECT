package login.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import login.dto.LoginDto;
import login.dto.LoginLogDto;

/**
 * 
* 1. 패키지명 : login.service
* 2. 타입명 : LoginService.java
* 3. 작성일 : 2015. 8. 11. 오전 10:46:13
* 4. 작성자 : 길용현
* 5. 설명 : 로그인 관련 정보를 가져오는 기능을 수행하는 클래스
 */
public class LoginService {
	private static LoginService instance;
	
	private LoginService(){
		
	}
	
	public static LoginService getInstance(){
		if(instance == null){
			instance = new LoginService();
		}
		
		return instance;
	}
	
	/**
	 * 
	* 1. 메소드명 : readRegistry
	* 2. 작성일 : 2015. 8. 11. 오전 10:41:50
	* 3. 작성자 : 길용현
	* 4. 설명 : ORACLE_HOME Registry 값을 읽어오는 기능 (HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\ORACLE\KEY_OraDb11g_home1)
	* @return
	 */
	public String readRegistry(){
		/*try {
			Process process = Runtime.getRuntime().exec(
					"reg query HKLM\\Software\\oracle\\key_xe /v oracle_home");

			StreamReader reader = new StreamReader(process.getInputStream());
			reader.start();
			process.waitFor();
			reader.join();
			
			String output = reader.getResult();

			return output.substring(output.indexOf("REG_SZ") + 6).trim();
		} catch (Exception e) {
			return null;
		}*/
		return "C:\\app\\kyh\\product\\11.2.0\\dbhome_1";
	}
	
	/**
	 * 
	* 1. 메소드명 : getUserInfo
	* 2. 작성일 : 2015. 8. 11. 오전 10:52:29
	* 3. 작성자 : 길용현
	* 4. 설명 : tnsnames.ora 파일 파싱하는 기능
	* @param registryValue
	* @return
	 */
	public ArrayList<LoginDto> getUserInfo(String registryValue){
		//String oraHomeAdd = registryValue.replace("\\", "\\\\")+"\\\\network\\\\ADMIN\\\\";
		String oraHomeAdd = registryValue+"\\NETWORK\\ADMIN\\";
		StringBuffer str = new StringBuffer();
		
		try{
			FileInputStream fis = new FileInputStream(oraHomeAdd+"tnsnames.ora");

			int data = 0;
		
			while((data=fis.read()) != -1){
				char c = (char)data;
				str.append(c);
			}
		
			fis.close();
		}catch(Exception cnfe){
			cnfe.printStackTrace();
		}

		/*String pattern = 
		"([A-z,a-z, ]*)(=\r\n)(.*)([[ ]*[(][A-z,a-z]*=\r\n]*)(.*)(HOST[ ]?=)(.*)"
		+ "([)][(])(PORT[ ]?=)(.*)([)][)])([[ ]*[(][A-z,a-z]*=[.*,[)]]?\r\n]*)([(]SERVICE_NAME\\s*=)(\\w*)[)](.*)";
		*/
		
		String pattern = "(\\w+)(\\s*=\\s*\r\n)"+
                "(\\s*[(]\\s*DESCRIPTION\\s*=\\s*\r\n)"+
		         "(\\s*[(]\\s*ADDRESS\\s*=\\s*[(]\\s*PROTOCOL\\s*=\\s*\\w+\\s*[)])"+
                "(\\s*[(]\\w+\\s*=\\s*)([\\w|\\.]+)(\\s*[)]\\s*[(]\\s*PORT\\s*=\\s*)(\\w+)(\\s*[)]\\s*[)]\\s*\r\n)"+
				 "(\\s*[(]\\s*CONNECT_DATA\\s*=\\s*\r\n)"+
                "(\\s*[(]\\s*SERVER\\s*=\\s*DEDICATED\\s*[)]\\s*\r\n)"+
				 "(\\s*[(]\\s*SERVICE_NAME\\s*=\\s*)(\\w+)([)]\\s*\r\n)"+
                "(\\s*[)]\r\n)(\\s*[)]\r\n)";
		
		
		Pattern pt = Pattern.compile(pattern);
		Matcher mc = pt.matcher(str);
		
		ArrayList<LoginDto> loginInfo = new ArrayList<LoginDto>();
		
		while(mc.find()){
			loginInfo.add(new LoginDto(mc.group(1), mc.group(6), mc.group(8), mc.group(13),mc.group()));
		}
	
		if(mc.matches()){
			System.out.println("노매치컨텐츠");
		}
		
		return loginInfo;
	}
	
	/**
	 * 
	* 1. 메소드명 : getLoginLogFile
	* 2. 작성일 : 2015. 8. 11. 오후 3:12:26
	* 3. 작성자 : 길용현
	* 4. 설명 : Login Log 정보를 추출
	* @return
	 */
	public ArrayList<LoginLogDto> getLoginLogFile(){
		ArrayList<LoginLogDto> list = new ArrayList<LoginLogDto>();
		FileInputStream fis = null;
		BufferedReader br = null;
	
		try{   
            String filePath = LoginService.class.getResource("").getPath()+"/loginlog.txt";
            
            fis = new FileInputStream(new File(filePath));
            br = new BufferedReader(new InputStreamReader(fis));
            
            StringTokenizer st = null;
            String str = "";
            
            while((str=br.readLine()) != null){
            	st = new StringTokenizer(str, ",");
            	LoginLogDto logInfo = new LoginLogDto();
            	
            	String[] temp = new String[4];
            	int i=0;
            	
            	while(st.hasMoreTokens()){
            		temp[i] = st.nextToken();
            		i++;
            	}
            	
            	logInfo.setTnsName(temp[0]);
            	logInfo.setUserName(temp[1]);
            	logInfo.setUserPwd(temp[2]);
            	logInfo.setConnectTime(temp[3]);
            	
            	list.add(logInfo);
            }
            
            br.close();
    		fis.close();
        }catch(Exception e){
            e.printStackTrace();
        }
		
		return list;
	}
	
	/**
	 * 
	* 1. 메소드명 : addLoginLogFile
	* 2. 작성일 : 2015. 8. 11. 오후 3:19:36
	* 3. 작성자 : 길용현
	* 4. 설명 : 로그인 로그 정보 추가
	* @param logInfo
	 */
	public void addLoginLogFile(LoginLogDto logInfo){
		ArrayList<LoginLogDto> list = getLoginLogFile();
		boolean equalData = false;
		
		for(Object obj : list){
			LoginLogDto logTemp = (LoginLogDto)obj;
			if(logTemp.getTnsName().equals(logInfo.getTnsName()) &&
					logTemp.getUserName().equals(logInfo.getUserName())){
				Calendar cal = Calendar.getInstance();
		    	Date date = cal.getTime();    	
		    	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		    	
				logTemp.setConnectTime(sdf.format(date));
				equalData = true;
				break;
			}
		}
		
		if(!equalData){
			list.add(logInfo);
		}
		
		writeLoginLogFile(list);
	}
	
	/**
	 * 
	* 1. 메소드명 : writeLoginLogFile
	* 2. 작성일 : 2015. 8. 12. 오후 12:26:32
	* 3. 작성자 : 길용현
	* 4. 설명 : Login Log 정보 파일에 기술
	* @param list
	 */
	public void writeLoginLogFile(ArrayList<LoginLogDto> list){
		String filePath = LoginService.class.getResource("").getPath()+"/loginlog.txt";
		
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		
		try{   
			fos = new FileOutputStream(new File(filePath));
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            
            for(Object obj : list){
            	LoginLogDto logTemp = (LoginLogDto)obj;
            	bw.write(logTemp.getTnsName()+","+logTemp.getUserName()+","+logTemp.getUserPwd()+","+logTemp.getConnectTime()+"\n");
            }
            
            bw.close();
    		fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	/**
	 * 
	* 1. 메소드명 : getUserPassword
	* 2. 작성일 : 2015. 8. 12. 오후 3:48:06
	* 3. 작성자 : 길용현
	* 4. 설명 : 접속 비밀번호 리턴
	* @param tnsName
	* @param userName
	* @return
	 */
	public String getUserPassword(String tnsName, String userName){
		ArrayList<LoginLogDto> list = getLoginLogFile();
		
		for(Object obj : list){
			LoginLogDto logDto = (LoginLogDto)obj;
			
			if(tnsName.equals(logDto.getTnsName()) && userName.equals(logDto.getUserName())){
				return logDto.getUserPwd();
			}
		}
		
		return null;
	}
	
	/**
	 * 
	* 1. 패키지명 : login.service
	* 2. 타입명 : LoginService.java
	* 3. 작성일 : 2015. 8. 11. 오전 10:44:54
	* 4. 작성자 : 길용현
	* 5. 설명 : ORACLE_HOME Registry 값을 가져오기 위해 I/O를 수행하는 클래스
	 */
	class StreamReader extends Thread {
		private InputStream is;
		private StringWriter sw = new StringWriter();

		public StreamReader(InputStream is) {
			this.is = is;
		}

		public void run() {
			try {
				int c;
				while ((c = is.read()) != -1)
					sw.write(c);
			} catch (IOException e) {
			}
		}

		public String getResult() {
			return sw.toString();
		}
	}
}
