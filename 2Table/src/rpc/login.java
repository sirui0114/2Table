package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import db.mysql.MySQLConnection;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MySQLConnection conn = new MySQLConnection();
		try {
			JSONObject obj = new JSONObject();
			HttpSession session = request.getSession(false);
			if (session == null) {
				response.setStatus(403);
				obj.put("status", "Session Invalid");
			} else {
				String clientType = (String) session.getAttribute("clientType");
				String Id = (String) session.getAttribute("Id");
				if (clientType == "user") {
					String name = conn.getName(Id, true);
					obj.put("status", "OK");
					obj.put("user_id", Id);
					obj.put("name", name);
				}else {
					String name = conn.getName(Id, false);
					obj.put("status", "OK");
					obj.put("res_id", Id);
					obj.put("name", name);
				}
			}
			rpcHelper.writeJsonObject(response, obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MySQLConnection conn = new MySQLConnection();
			try {
				JSONObject input = rpcHelper.readJsonObject(request);
				String clientType = input.getString("clientType");
				String Id = input.getString("Id");
				String pwd = input.getString("password");
				boolean isUser;
				if (clientType.equals("user")) {
					isUser = true;
				}else {
					isUser = false;
				}
				JSONObject obj = new JSONObject();

				if (conn.verifyLogin(Id, pwd, isUser)) {
					HttpSession session = request.getSession();
					session.setAttribute("Id", Id);
					// setting session to expire in 10 minutes
					session.setMaxInactiveInterval(10 * 60);
					// Get user name
					String name = conn.getName(Id, isUser);
					String url = conn.getUrl(Id, isUser);
					obj.put("status", "OK");
					obj.put("user_id", Id);
					obj.put("name", name);
					obj.put("url", url);
				} else {
					response.setStatus(401);
				}
				rpcHelper.writeJsonObject(response, obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}

}
