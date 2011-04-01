package net.ildn;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.ildn.fedorait.R;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.CookieSyncManager;

public class Authentication {
	
	private String portalelogin;
	private String username;
	private Context cx;
	
	public static final int ERROR = -1;
	public static final int ACCESS = 0;
	public static final int NOT_ACCESS = 1;
	
	private static final String LOG_ID = "ildn - Authentication";
	
	public Authentication(Context cx) {
		this.cx=cx;
	}

	public int login() {
		SharedPreferences settings = cx.getSharedPreferences(
				cx.getString(R.string.ildnPreference), 0);
		this.portalelogin = settings.getString("portalelogin",
				"nessuno");
		
		final UserCredential uc = new UserCredential(cx);
        String ildnuser=uc.getPrefs("ildnuser");
        String ildnpasswd=uc.getPrefs("ildnpasswd");
        
        if (ildnpasswd.equalsIgnoreCase("") || ildnuser.equalsIgnoreCase("")) return Authentication.NOT_ACCESS;
		
		HttpClient con = new DefaultHttpClient();
		HttpContext httpcontext = new BasicHttpContext();
		CookieStore cookieStore = new BasicCookieStore();
		CookieSyncManager.createInstance(cx);
		CookieSyncManager.getInstance().startSync();
		httpcontext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			    
		try {
			String loginurl = "http://www." + portalelogin + "/user/login";
			Log.i(LOG_ID, "login url " + loginurl);
			HttpGet httpget = new HttpGet(loginurl);

			//get per ottenere form_build_id
			HttpResponse response = con.execute(httpget,httpcontext);
			StatusLine sl = response.getStatusLine();
			
			if (sl.getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				String result = convertToString(is);
				
				String value = (String) result.subSequence(result.indexOf("value=\"form-")+7 ,result.indexOf("value=\"form-") + 7 + 37);
				Log.i(LOG_ID, "value of form_build_id is " + value);
				
				//post per il login
				HttpPost httppost = new HttpPost(loginurl);
				HttpParams params = con.getParams(); 
				HttpClientParams.setRedirecting(params, false);

				
	            
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("name", ildnuser));
		        nameValuePairs.add(new BasicNameValuePair("pass", ildnpasswd));
		        nameValuePairs.add(new BasicNameValuePair("form_build_id", value));
		        nameValuePairs.add(new BasicNameValuePair("form_id", "user_login"));
		        nameValuePairs.add(new BasicNameValuePair("op", "Accedi"));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        
				
				response = con.execute(httppost,httpcontext);				
				sl = response.getStatusLine();
				entity = response.getEntity();
				is = entity.getContent();
				
				result = convertToString(is);
				
				CookieSyncManager.getInstance().sync();
				
				if (sl.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
					Header[] headers = response.getHeaders("Location");
					String location = headers[0].toString();
					this.username=location.substring(location.lastIndexOf("/")+1, location.length());
					Log.i(LOG_ID, "Username grabbed to " + this.username + "from: "+ portalelogin);
				}
				else {
					return Authentication.NOT_ACCESS;
				}
					
			}
			else 
				return Authentication.NOT_ACCESS;			
		}
		catch (Exception e) {
			Log.i(LOG_ID, "Errore in Authentication" + e.getMessage());
			return Authentication.ERROR;
		}
		return Authentication.ACCESS;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	private String convertToString(InputStream is) throws IOException {
		/*
		 if (is != null) {
	            Writer writer = new StringWriter();

	            char[] buffer = new char[1024];
	            try {
	                Reader reader = new BufferedReader(
	                        new InputStreamReader(is, "UTF-8"));
	                int n;
	                while ((n = reader.read(buffer)) != -1) {
	                    writer.write(buffer, 0, n);
	                }
	            }
	            finally {
	                is.close();
	            }
	            return writer.toString();
	        } else {        
	            return "";
	        }
	   */

        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = is.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
	}
}
