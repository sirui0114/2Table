package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.mysql.MySQLConnection;
import entity.u_Item;
import entity.u_Item.uItemBuilder;
import entity.r_Item;
import entity.r_Item.rItemBuilder;;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
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
			String client =  input.getString("client");
			
			MySQLConnection conn = new MySQLConnection();
			
			boolean result;
			if (client.equals("User")) {
				String Id = input.getString("ID");
				String name =  input.getString("name");
				String phone = input.getString("phone");
				String email = input.getString("email");
				String pwd = input.getString("password");
				uItemBuilder builder = new uItemBuilder();
				builder.setUserID(Id);
				builder.setEmail(email);
				builder.setName(name);
				builder.setPassword(pwd);
				builder.setPhone(phone);
				u_Item item = new u_Item(builder);
				result = conn.registerU(item);
			}else {
				String Id = input.getString("ID");
				String name =  input.getString("name");
				String phone = input.getString("phone");
				String email = input.getString("email");
				String pwd = input.getString("password");
				int capacity = input.getInt("capacity");
				String address = input.getString("address");
				rItemBuilder builder = new rItemBuilder();
				builder.setAddress(address);
				builder.setCapacity(capacity);
				builder.setRemail(email);
				builder.setrItemID(Id);
				builder.setrName(name);
				builder.setRphone(phone);
				builder.setPassword(pwd);
				r_Item item = new r_Item(builder);
				result = conn.registerR(item);
			}
			
			if(result) 
				rpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
			else 
				response.setStatus(401);
				rpcHelper.writeJsonObject(response, new JSONObject().put("result", "FAILURE"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
