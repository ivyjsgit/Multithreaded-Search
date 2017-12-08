package search;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class crawler implements Runnable {
	private String threadName;
	private Thread t;
	public ConcurrentLinkedQueue<String> siteQueue;
	public ConcurrentHashMap<String, Site> visitedSites;
	private boolean debug;

	public crawler(String threadName, boolean debug) {
		this.threadName = threadName;
		this.siteQueue = new ConcurrentLinkedQueue<String>();
		this.visitedSites = new ConcurrentHashMap<String, Site>();
		this.debug = debug;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Can also use stack for different type of
		// search

		try {
			if (cache.getCacheAge() > 86400) {
				//If older than 1 day, refresh the cache again
				generateSites();
			} else {
				Object[] asObjArr = (Object[]) cache.readCache();
				Iterator<String> keyIt = ((HashSet<String>) asObjArr[0]).iterator(); //This should always be the case if cache.txt hasn't been tampered with
				Iterator<String[]> siteIt = ((HashSet<String[]>) asObjArr[1]).iterator();
				while (keyIt.hasNext()) {
					String[] siteArr = siteIt.next();
					// String url, String description, String text
					Site newSite = new Site(siteArr[0], siteArr[1], siteArr[2]);
					visitedSites.put(keyIt.next(), newSite);
				}
			}
		} catch (IOException e) { //Site doesn't exist
			// TODO Auto-generated catch block
			generateSites();
		}
		System.out.println("Done loading");

	}

	private void generateSites() {
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
			// Make into serializable format, so we can use SerializationUtilities

		}
		try {
			System.out.println("Writing file");
			// HashMaps aren't serializable, so we have to force it to be. HashSets and
			// Arrays are, so we have to make it into those
			HashSet<String> keys = new HashSet<String>();
			HashSet<String[]> sites = new HashSet<String[]>();

			for (String key : visitedSites.keySet()) {
				keys.add(key);
				sites.add(visitedSites.get(key).site); //Because .site is just a String[], we can get away with this easily
			}

			Object[] keyValues = new Object[2];
			keyValues[0] = keys;
			keyValues[1] = sites;

			cache.writeCache(keyValues);
			System.out.println("Wrote cache");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void start() {
		t = new Thread(this, threadName);
		t.start();
	}

}
