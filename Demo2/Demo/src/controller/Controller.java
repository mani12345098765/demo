package controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

@Path("/Controller")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Controller {
	private static final Logger logger = LogManager.getLogger(Controller.class);

	@GET
	@Path("/get")
	public Response get() {
		
		JSONObject response = new JSONObject();
		response.put("Status", "Connected");
				
		return successResponse(response);
		
	}
	private static Response successResponse(JSONObject response) {
		logger.info("Response Form API - "+response.toString());
		return Response.status(200).entity(response.toString()).build();
	}
	private static Response fieldMisssing(JSONObject response) {
		response.put("status", "Invalid");
		response.put("Reason", "userMessage Field missing");
		logger.info("Response Form API - "+response.toString());
		return Response.status(204).entity(response.toString()).build();
	}
	private static Response improperJsonResponse(JSONObject response, String error) {
		logger.error("JSON format Exceoption - "+error);
		response.put("status", "Invalid");
		response.put("reason", "Improper JSON format");
		logger.info("response sent is - "+response.toString());
		return Response.status(500).entity(response.toString()).build();
	}
}