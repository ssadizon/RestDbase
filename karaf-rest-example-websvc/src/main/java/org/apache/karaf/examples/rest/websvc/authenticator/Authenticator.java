package org.apache.karaf.examples.rest.websvc.authenticator;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

public class Authenticator {
	
	private String headers;
	
	public Authenticator(String headers) {
		this.headers = headers;
	}
	
	public boolean isAuthorized() {
		boolean isAuthorized = false;
		ArrayList headersValues = new ArrayList(Arrays.asList(this.headers.split(" ")));
		if(headersValues.size() != 2 || !headersValues.get(0).equals("Basic")) {
			return isAuthorized;	
		} else {
			String decoded = "";
			try {
				decoded = new String(Base64.getDecoder().decode(headersValues.get(1).toString()));
				String[] namePassword = decoded.split(":");
				System.out.println("Name: " +namePassword[0]+ " Password: " +namePassword[1]);
				Properties user = new Properties();
				String userProp = System.getProperty("karaf.etc");
				System.out.println("User.properties: " +userProp);
				File file = new File(userProp + "/users.properties");
				if(!file.exists()) {
					System.out.println("No file");
					return isAuthorized;
				} else {
					FileReader reader = new FileReader(file);
					user.load(reader);
					String userInfo = user.get(namePassword[0]).toString();
					String[] userCreds = userInfo.split(",");
					String configPassword = userCreds[0];
					System.out.println(configPassword);
					if(namePassword[1].equals(configPassword)) {
						isAuthorized = true;						
					}
					return isAuthorized;	 
				}	
			} catch(Exception exception) {
				System.out.println("Exception list()");
				return isAuthorized;
			}
		}		
	}

}
