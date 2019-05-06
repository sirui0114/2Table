package rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import entity.r_Item;
import entity.r_Item.rItemBuilder;

public class rpcHelperTest {

	@Test
	public void testGetJSONArray() throws JSONException {
		rItemBuilder build = new rItemBuilder();
		build.setCapacity(50);
		build.setPassword("12345");
		build.setRemail("yang12@cooper.edu");
		build.setAddress("30 cooper square");
		build.setrName("Rest001");
		build.setrItemID("one");
		r_Item one = new r_Item(build);
		rItemBuilder build2 = new rItemBuilder();
		build2.setCapacity(50);
		build2.setPassword("12345");
		build2.setRemail("yang12@cooper.edu");
		build2.setAddress("30 cooper square");
		build2.setrName("Rest001");
		build2.setrItemID("one");
		r_Item two = new r_Item(build);
		List<r_Item> listItem = new ArrayList<r_Item>();
		listItem.add(one);
		listItem.add(two);
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(one.toJSONObject());
		jsonArray.put(two.toJSONObject());
		
		JSONAssert.assertEquals(jsonArray, rpcHelper.getJSONArray(listItem), true);
	}
}

