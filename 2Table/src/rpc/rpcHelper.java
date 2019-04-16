package rpc;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.r_Item;

public class rpcHelper {
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj) {
		try {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
			response.setContentType("application/JSON");
			
			PrintWriter out = response.getWriter();
			out.print(obj);
			out.flush();
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void writeJsonArray(HttpServletResponse response, JSONArray array) {
		try {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
			response.setContentType("application/JSON");
			
			PrintWriter out = response.getWriter();
			out.print(array);
			out.flush();
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Parses a JSONObject from http request.
	public static JSONObject readJsonObject(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			return new JSONObject(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 public static JSONArray getJSONArray(List<r_Item> items) {
		    JSONArray result = new JSONArray();
		    try {
		      for (r_Item item : items) {
		        result.put(item.toJSONObject());
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return result;
		  }


}
