package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.mysql.MySQLConnection;

/**
 * Servlet implementation class getClientInfo
 */
@WebServlet("/getClientInfo")
public class getClientInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getClientInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String u_ID = "";
		if (request.getParameter("user_ID")!= null) {
			u_ID = request.getParameter("user_ID");
		}
		MySQLConnection conn = new MySQLConnection();
		List<String> cInfo = conn.getContactInfo(u_ID);
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.append("user_name", cInfo.get(0));
			obj.append("user_phone", cInfo.get(1));
			obj.append("user_email", cInfo.get(2));
		}catch(Exception e) {
				System.out.println(e.getMessage());
		}
		arr.put(obj);
		rpcHelper.writeJsonArray(response, arr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
