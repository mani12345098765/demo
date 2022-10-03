package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class HttpMethod{

	private static final Logger logger = LogManager.getLogger(HttpMethod.class);
	
	public static String post(String url, JSONObject data) {
		String response = null;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-type", "application/json");
			StringEntity entity = new StringEntity(data.toString(), "UTF-8");
			post.setEntity(entity);

			HttpResponse responseHttp = httpClient.execute(post);
			HttpEntity entity2 = responseHttp.getEntity();
			if (responseHttp.getStatusLine().getStatusCode() == 200) {
				if (entity2 != null) {
					String content = EntityUtils.toString(entity2);
					response = content;
				}
			}
			JSONObject request = new JSONObject();
			request.put("request_type", "post");
			request.put("url", url);
			request.put("request_data", data);
		} catch (Exception ex) {
			logger.error("Error in Http Post method for BODY - "+data +"\nURL - "+url);
		}
		return response;
	}
	
	
	public static JSONObject get(String url) {
		JSONObject response= null;
		logger.info("URL :::::: " + url );
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet(url);
			get.setHeader("Content-type", "application/json");
			HttpResponse responseHttp = httpClient.execute(get);
			HttpEntity entity2 = responseHttp.getEntity();

			logger.info(entity2.getContentType().toString());

			if (entity2!= null) {
				String content = EntityUtils.toString(entity2);
				logger.info("Response from GET API ---------->>>>> " + content);
				response = new JSONObject(content);

				if(responseHttp.getStatusLine().getStatusCode()!=200) {
					logger.error("Error via API with status " + responseHttp.getStatusLine().getStatusCode());
				}
			}

		} catch (Exception ex) {
			logger.error("Post",ex);
			ex.printStackTrace();
		}
		logger.info("Response of POST request : \n" + response.toString());
		return response;
	}
	
}
