package search;

import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class searcher implements Runnable {
	Thread t;
	String threadName;
	ConcurrentHashMap<String, Site> visitedSites;

	/**
	 * @param threadName
	 * @param visitedSites
	 */
	public searcher(String threadName, ConcurrentHashMap<String, Site> visitedSites) {
		this.threadName = threadName;
		this.visitedSites = visitedSites;
	}

	public void run() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("?");
			String query = sc.nextLine();
			if (query.toLowerCase().equals("quit")) {
				break;
			} else {
				System.out.println(visitedSites.size());
			}
		}

	}
	/***
	 * Start a new searcher thread
	 */
	public void start() {
		t = new Thread(this, threadName);
		t.start();
	}
	/***
	 * 
	 * @param query the string to search for within the sites already visited
	 * @return HashSet of all sites containing the query (inside a word, URL, text, etc.)
	 */
	public HashSet search(String query) {
		HashSet<String> h = new HashSet();
		for (Site siteClass : visitedSites.values()) {

			if (siteClass.getText() != null && (siteClass.getText().toLowerCase().contains(query.toLowerCase())
					|| siteClass.getUrl().contains(query.toLowerCase()))) {
				h.add(siteClass.getUrl());
				System.out.println(siteClass.getUrl());
			}
		}
		return h;
	}
	/***
	 * Get all visited sites
	 * @return HashSet of visited sites
	 */
	public HashSet getAll() {
		HashSet<String> h = new HashSet();
		for (Site siteClass : visitedSites.values()) {

			h.add(siteClass.getUrl());
		}

		return h;
	}

}
