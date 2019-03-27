package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.mysql.MySQLConnection;

/**
 * Servlet implementation class setCapacity
 */
@WebServlet("/setCapacity")
public class setCapacity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public setCapacity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject input = rpcHelper.readJsonObject(request);
			String rID =  input.getString("r_ID");
			int cap =  input.getInt("capacity");
			String time = input.getString("rTime");
			
			MySQLConnection conn = new MySQLConnection();
			if (conn.setCapacity(rID, cap, time))
				rpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
			else 
				rpcHelper.writeJsonObject(response, new JSONObject().put("result", "FAILURE"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
