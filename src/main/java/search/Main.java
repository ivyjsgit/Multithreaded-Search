package search;

public class Main {

	public static void main(String[] args) throws Exception {

		crawler c = new crawler("myCrawler", false);
		c.start();
		searcher s = new searcher("mySearcher", c.visitedSites);
		s.start();

	}

}
