package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import db.mysql.*;
import entity.rv_Item;

/**
 * Servlet implementation class viewReservation
 */
@WebServlet("/viewReservation")
public class viewReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewReservation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String r_account = "";
		if (request.getParameter("account")!= null) {
			r_account = request.getParameter("account");
		}
		MySQLConnection conn = new MySQLConnection();
		String rItemID = conn.rNameTorID(r_account);
		
		List<rv_Item> rvList = conn.getRvList(rItemID, null);
		JSONArray array  = new JSONArray();
		for (rv_Item item : rvList) {
			JSONObject obj = item.toJSONObject();
			array.put(obj);
		}
		rpcHelper.writeJsonArray(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
