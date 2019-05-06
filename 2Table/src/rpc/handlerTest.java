package rpc;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.mockito.Mockito.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;


class handlerTest {
	
	@Test
	void testLoginHandler() {
		HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        String test = "{\"Id\":\"1111\",\"password\":\"12345\", \"clientType\":\"user\"}";
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);
        
        JSONObject obj = new JSONObject();
        
        try {
            when(request.getSession()).thenReturn(session);
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
        		when(request.getReader()).thenReturn(reader);
        		when(response.getWriter()).thenReturn(writer);
        		new login().doPost(request, response);
        		obj = new JSONObject();
        		obj.put("status", "OK");
        		obj.put("user_id","1111");
        		obj.put("name", "Joe");
        		obj.put("url", "https://i.loli.net/2019/04/25/5cc1189daf60d.png");
        		JSONAssert.assertEquals(stringWriter.toString(), obj.toString(), true);
        } catch (Exception e) {
			e.printStackTrace();
		}
        
	}

}
