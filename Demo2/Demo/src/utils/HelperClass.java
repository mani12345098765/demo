package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import properties.MessagesProperty;

public class HelperClass {
	public static final String GetHinlish = "getHinglish";
	public static final String GetDate = "getDataFromString";
	public static final String GetEmail = "getEmailFromString";
	public static final String GetSimilarity = "getSimilar";
	public static final String GetSearch = "search_regex";

	private static final Logger logger = LogManager.getLogger(HelperClass.class);
	/**
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 *             To Create Connections
	 */
	public static Connection getWriterConnection(String server, String DBName, String userName, String Password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = null;

		if(server==null)
			server = "localhost";

		String DB_WRITER_URL = "jdbc:mysql://" +server+":3306/+" + DBName+"?useSSL=false&characterEncoding=UTF-8&useUnicode=true";
		connection = DriverManager.getConnection(DB_WRITER_URL, userName, Password);
		return connection;
	}

	/**
	 * @param con
	 * @param stmt
	 * @param rs
	 *            To close statements, result sets and Connection
	 */
	public static void close(Connection con, Statement stmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();

			if (stmt != null)
				stmt.close();

			if (con != null)
				con.close();

		} catch (Exception e) {
			logger.error( "Error in closing Connection "+e.getMessage());
		}
	}
	public static String Similarity_Dictinary(String id,String message) {

		try {
			JSONObject Request = new JSONObject();
			Request.put("text", message);
			Request.put("id", id);

			JSONObject Response = new JSONObject();
			Request.put("password", MessagesProperty.PASSWORD);
			Response =   new JSONObject(HttpMethod.post(MessagesProperty.URL+GetSearch, Request));
			Request.put("model_name", MessagesProperty.BOT_NAME);


			if (Response!= null && Response.length() > 0) {
				Set<String> key =  Response.keySet();
				return Response.getString(key.toArray()[0].toString());
			}
			else
				return null;	
		}catch(Exception e) {
			logger.info("Error in Similarity "+e.getMessage());
		}
		return null;
	}

	public static Date GetDate(String message,String region) {

		message = message.replaceAll("[.,?!।\\/#!$%\\^&\\*;{}=\\-_`~()]", "");

		return get(message, region);
	}

	public static Date get(String message,String region) {
		Date date = null;
		JSONObject request = new JSONObject();
		request.put("text", message);
		request.put("region", region);

		JSONArray response_api = new JSONArray();
		request.put("password", MessagesProperty.PASSWORD);
		response_api =   new JSONArray(HttpMethod.post(MessagesProperty.URL+GetDate, request));
		request.put("model_name", MessagesProperty.BOT_NAME);

		if(response_api!=null&&!response_api.isNull(0)) {
			JSONObject resp = response_api.getJSONObject(0);
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resp.getString("date"));
			}catch (Exception e) {
				logger.error("Parse Exception in date - "+e);
			}
		}
		return date;
	}
}