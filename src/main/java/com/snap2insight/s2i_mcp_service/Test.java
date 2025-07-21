package com.snap2insight.s2i_mcp_service;

public class Test {
	
	public static void main(String[] args) {
		String s = """
				 create_time &gt; "2012-04-21T11:30:00-04:00"
	    
	     create_time &gt; "2012-04-21T11:30:00-04:00" AND
	       thread.name = spaces/AAAAAAAAAAA/threads/123
	    
	     create_time &gt; "2012-04-21T11:30:00+00:00" AND
	    
	     create_time &lt; "2013-01-01T00:00:00+00:00" AND
	       thread.name = spaces/AAAAAAAAAAA/threads/123
	    
	     thread.name = spaces/AAAAAAAAAAA/threads/123
				""";
		System.out.println(s);
	}

}
