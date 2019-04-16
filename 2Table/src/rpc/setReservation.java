package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.mysql.MySQLConnection;

/**
 * Servlet implementation class setReservation
 */
@WebServlet("/setReservation")
public class setReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public setReservation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject input = rpcHelper.readJsonObject(request);
			String resId =  input.getString("res_id");
			String userId = input.getString("user_id");
			int psize =  input.getInt("size");
			String pdatetime = input.getString("pdatetime");
			
			MySQLConnection conn = new MySQLConnection();
			if(conn.setReservation(userId, resId, psize, pdatetime)) 
				rpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
			else 
				rpcHelper.writeJsonObject(response, new JSONObject().put("result", "FAILURE"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
