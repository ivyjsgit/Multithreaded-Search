package search;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class getLinks {
	public static ArrayList<String> getLinks(Document site) {
		ArrayList<String> linksOnSite = new ArrayList<String>();
		for (Element link : site.getElementsByTag("a")) {
			String foundLink = link.attr("href");
			if (foundLink.startsWith("http://") || foundLink.startsWith("https://"))
				linksOnSite.add(foundLink);
		}
		return linksOnSite;

	}
}
