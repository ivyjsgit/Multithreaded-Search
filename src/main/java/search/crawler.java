package search;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
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

		try {
			if (cache.getCacheAge() > 86400) {
				generateSites();
			} else {
				Object[] asObjArr = (Object[]) cache.readCache();
				Iterator keyIt = ((HashSet<String>) asObjArr[0]).iterator();
				Iterator siteIt = ((HashSet<String[]>) asObjArr[1]).iterator();
				while (keyIt.hasNext()) {
					String[] siteArr = (String[]) siteIt.next();
					// String url, String description, String text
					Site newSite = new Site(siteArr[0], siteArr[1], siteArr[2]);
					visitedSites.put((String) keyIt.next(), newSite);
				}
			}
		} catch (IOException e) {
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

			}

			for (String siteString : getLinks.getLinks(docSite)) {
				Site visitingSite = new Site(siteString, "Placeholder", newSite.text());

				try {
					ArrayList<String> allLinks = new ArrayList<>();
					Site returnedSite = visitSite(visitedSites,siteString).get();
					try {
						 allLinks = getLinks.getLinks(returnedSite.asDoc);
					}catch (NullPointerException e){
						System.out.println("No links");
					}
					siteQueue.addAll(allLinks);
					visitedSites.put(siteString,returnedSite);

				} catch (IOException e) {
					e.getStackTrace();
				}
				visitedSites.put(siteString, visitingSite);

			}
			// Make into serializable format, so we can use SerializationUtilities

		}
		try {
			System.out.println("Writing file");
			// HashMaps aren't serializable, so we have to force it to be. HashSets and
			// Arrays are, so we have to make it into those
			HashSet<String> keys = new HashSet();
			HashSet<String[]> sites = new HashSet();

			for (String key : visitedSites.keySet()) {
				keys.add(key);
				sites.add(visitedSites.get(key).site);
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
	public static Optional<Site> visitSite(ConcurrentHashMap<String, Site> visitedSites, String siteString) throws IOException{
		Document newSite;
		Site visitingSite = new Site();
		if (!visitedSites.containsKey(siteString)) {
			newSite = Jsoup.connect(siteString).get();
			 visitingSite = new Site(siteString, "Placeholder", newSite.text()); // Only puts the parent
			visitingSite.setAsDoc(newSite);

//			siteQueue.add(siteString);

		}
		return Optional.of(visitingSite);
	}

}
