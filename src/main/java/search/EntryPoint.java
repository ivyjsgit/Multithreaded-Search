/***
 * Entry point used to connect to py4j and flask.
 */

package search;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import py4j.GatewayServer;

public class EntryPoint {

	private Stack stack;
	searcher s;

	public EntryPoint() {
		crawler c = new crawler("myCrawler", false);
		c.start();
		s = new searcher("mySearcher", c.visitedSites);
		s.start();
	}

public  String search(String query) {
	String output="";
	Iterator i =  s.search(query).iterator();
	while(i.hasNext()) {
		String url = (String) i.next();
		output+="<a href=" +url+">" + url+"</a>" + "<br>";
	}
	return output;
	
}

	public static void main(String[] args) {
		GatewayServer gatewayServer = new GatewayServer(new EntryPoint());
		gatewayServer.start();
		System.out.println("Gateway Server Started");
	}

}