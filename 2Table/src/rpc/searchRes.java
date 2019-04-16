package rpc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.mysql.MySQLConnection;
import entity.r_Item;
import entity.rv_Item;

/**
 * Servlet implementation class searchRes
 */
@WebServlet("/searchRes")
public class searchRes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchRes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MySQLConnection conn = new MySQLConnection();
		try {
			if (request.getParameter("Id")!= null) {
				String Id = request.getParameter("Id");
				r_Item resInfo = conn.getResInfoByID(Id);
				rpcHelper.writeJsonObject(response, resInfo.toJSONObject());
			}else {
			
				List<r_Item> rsList = conn.getRandRsList();
				JSONArray array  = new JSONArray();
				for (r_Item item : rsList) {
					JSONObject obj = item.toJSONObject();
					array.put(obj);
				}
				rpcHelper.writeJsonArray(response, array);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
