package APIHandlers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.friendscentral.steamnet.IndexGrid;
import org.friendscentral.steamnet.JawnAdapter;
import org.friendscentral.steamnet.BaseClasses.Spark;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.json.parsers.JSONParser;
import com.squareup.okhttp.OkHttpClient;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

public class RetrieveDataTaskPostSpark {
	char spark_type;
	char content_type;
	String content;
	String user;
	String[] tags;
	String tagsString;
	GridView gridView;
	IndexGrid indexGrid;
	JawnAdapter adapter;
	
	public RetrieveDataTaskPostSpark(char st, char ct, String c, GridView g, IndexGrid i) {
		spark_type = st;
		content_type = ct;
		content = c;
		gridView = g;
		indexGrid = i;
		adapter = indexGrid.getAdapter();
		
		tagsString = "";
		/*if(tags != null){
			for (int i = 0; i < tags.length; i++) {
				tagsString += tags[i];
				if (i != tags.length - 1) {
					tagsString += ",";
				}
			}
		}*/
		
		OkHTTPTask task = new OkHTTPTask();
		task.execute("http://steamnet.herokuapp.com/api/v1/sparks.json");
	}
	
	@SuppressWarnings("unused")
	class OkHTTPTask extends AsyncTask<String, Void, String> {
	
		String TAG = "RetreiveDataTask";
		
	    OkHttpClient client = new OkHttpClient();
		
	    private Exception exception;
	    
	    @Override
	    protected String doInBackground(String... urls) {
	        try {
	        	
	        	/*
	        	 * BELOW ME IS THE STRING TO EDIT, READ THE README ON GITHUB
	        	 * RIGHT HERE!
	        	 * LOOK! ITS RIGHT THERE!
	        	 */
	        	
	        	String postData = "&spark[spark_type]="+spark_type+"&spark[content_type]="+content_type+"&spark[content]="+content+"&username="+user+"&tags="+tagsString+"&username=max";
	        	Log.v(TAG, postData);
	        	
	        	//return get(new URL(urls[0]));
	        	
	        	return post(new URL(urls[0]), postData.getBytes());
	        	
	        } catch (Exception e) {
	            this.exception = e;
	            Log.e(TAG, "Exception: "+e);
	            return null;
	        }
	    }
	
	    protected void onPostExecute(String data) {
	    	Log.d(TAG, "=> "+data);
	    	try {
				Spark newSpark = parseData(data);
				indexGrid.addJawn(newSpark);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    Spark parseData(String data) throws JSONException {
			Log.v("TEST", "BEGGINING TO PARSE DATA");
        	final String ID = "id";
        	final String SPARK_TYPE = "spark_type";
        	final String CONTENT_TYPE = "content_type";
        	final String CONTENT = "content";
        	final String CREATED_AT = "created_at";
        	final String UPDATED_AT = "updated_at";
        	final String USERS = "users";
        	final String USER = "user";
        	final String USERNAME = "name";
        	// Creating JSON Parser instance
        	JSONParser jParser = new JSONParser();
        	 
        	// getting JSON string from URL
        	JSONObject json = new JSONObject(data);
        	 
        	try {
				// Storing each json item in variable
				String id = json.getString(ID);
				String sparkType = json.getString(SPARK_TYPE);
				String contentType = json.getString(CONTENT_TYPE);
				String content = json.getString(CONTENT);
				String createdAt = json.getString(CREATED_AT);
				String firstUser = "";
				
				//Getting Array of Users
        	    JSONArray usersJSON = json.getJSONArray(USERS);
        	     
        	    // looping through All Users
        	    ArrayList<Integer> usersArrayList = new ArrayList<Integer>();
        	    int count = 0;
        	    for(int i = 0; i < usersJSON.length(); i++){
        	        JSONObject u = usersJSON.getJSONObject(i);
        	        // Storing each json item in variable
        	        if(count == 0){
        	        	count++;
        	        	firstUser = u.getString(USERNAME);
        	        }
        	        int userID = u.getInt(ID);
        	        usersArrayList.add(userID);
        	    }
        	    int[] usersArray = new int[usersArrayList.size()];
        	    for(int q = 0; q < usersArrayList.size(); q++){
        	    	usersArray[q] = usersArrayList.get(q);
        	    }
        	    
        	    String[] createdAts = new String[1];
        	    createdAts[0] = createdAt;
        	    
				Spark newSpark = new Spark(Integer.parseInt(id), sparkType.charAt(0), contentType.charAt(0), content, createdAts, usersArray, firstUser);
				Log.v("TEST", newSpark.toString());
				return newSpark;
        	} catch (JSONException e) {
        	    e.printStackTrace();
        	}
        	return null;
        }
	    
	    //I think this method can be deleted
	    String get(URL url) throws IOException {
	      HttpURLConnection connection = client.open(url);
	      InputStream in = null;
	      try {
	        // Read the response.
	        in = connection.getInputStream();
	        byte[] response = readFully(in);
	        return new String(response, "UTF-8");
	      } finally {
	        if (in != null) in.close();
	      }
	    }
	    
	    /*
	     * TRYING TO MAKE POST FROM HERE DOWN
	     */
	    
	    String post(URL url, byte[] body) throws IOException {
	        HttpURLConnection connection = client.open(url);
	        OutputStream out = null;
	        InputStream in = null;
	        try {
	          // Write the request.
	          Log.v("TEST", "Writing the Request");
	          connection.setRequestMethod("POST");
	          out = connection.getOutputStream();
	          out.write(body);
	          Log.v("TEST", "Closing the Request");
	          out.close();
	
	          // Read the response.
	          if (connection.getResponseCode() != HttpURLConnection.HTTP_OK && connection.getResponseCode() != 201) {
	        	Log.v("TEST", "GOT AN ERROR!!!!!!");
	            throw new IOException("Unexpected HTTP response: "
	                + connection.getResponseCode() + " " + connection.getResponseMessage());
	          }
	          in = connection.getInputStream();
	          Log.v("TEST", "Reading the first line...");
	          return readFirstLine(in);
	        } finally {
	          // Clean up.
	          if (out != null) out.close();
	          if (in != null) in.close();
	        }
	      }
	    
	    String readFirstLine(InputStream in) throws IOException {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	        return reader.readLine();
	      }
	    /*
	     * END ATTEMPT TO MAKE POST
	     */
	    
	    //I think this method can be deleted
	    byte[] readFully(InputStream in) throws IOException {
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        for (int count; (count = in.read(buffer)) != -1; ) {
	          out.write(buffer, 0, count);
	        }
	        return out.toByteArray();
	      }
	    
	 }

}
