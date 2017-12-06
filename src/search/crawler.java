package search;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class crawler implements Runnable {
	String threadName;
	Thread t;
	public ConcurrentLinkedQueue<String> siteQueue;
	public ConcurrentHashMap<String, Site> visitedSites;
	boolean debug;

	public crawler(String threadName, boolean debug) {
		this.threadName = threadName;
		this.siteQueue = new ConcurrentLinkedQueue<String>();
		this.visitedSites = new ConcurrentHashMap<String, Site>();
		this.debug = debug;

	}

	public void run() {
		// TODO Auto-generated method stub
		// Can also use stack for different type of
		// search
		siteQueue.add("https://en.wikipedia.org/wiki/Hat");

		int count = 0;
		// TODO Auto-generated method stub
		System.out.println("Searching sites. This may take a while, but you can search while they load.");
		while (!siteQueue.isEmpty()) {
			String newSiteString = siteQueue.remove();
			if (debug)
				System.out.println("Searching " + newSiteString);

			// Fetch a site
			Document docSite = new Document("");
			Document newSite = new Document("");
			try {
				docSite = Jsoup.connect(newSiteString).get();
			} catch (Exception e) {
				if (debug) {
					if (e instanceof HttpStatusException) {
						System.out.println("Site not accessible: " + newSiteString + ". Error code: "
								+ ((HttpStatusException) e).getStatusCode());
					} else if (e instanceof UnknownHostException) {
						System.out.println("No connection.");
						return;
					} else if (e instanceof IOException) {
						System.out.println("Too many redirections " + newSiteString);
					}
				}
			}
	
			for (String siteString : getLinks.getLinks(docSite)) {
				Site visitingSite = new Site(siteString, "Placeholder", newSite.text());

				try {

					if (!visitedSites.containsKey(newSiteString)) {
						newSite = Jsoup.connect(siteString).get();
						count++;
						visitingSite = new Site(siteString, "Placeholder", newSite.text()); // Only puts the parent
						siteQueue.add(siteString);

					} else {
						if (debug)
							System.out.println("Already visited");
					}
				} catch (Exception e) {
					if (debug) {
						if (e instanceof HttpStatusException) {
							System.out.println("Site not accessible: " + siteString + ". Error code: "
									+ ((HttpStatusException) e).getStatusCode());

						} else {
							System.out.println("Too many redirections " + siteString);
						}
					}
				}
				visitedSites.put(siteString, visitingSite);

			}

		}
		System.out.println("Done loading");

	}

	public void start() {
		t = new Thread(this, threadName);
		t.start();
	}

}
