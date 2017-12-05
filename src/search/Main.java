package search;
public class Main {
	private static final long MEGABYTE = 1024L * 1024L;

	public static long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}

	public static void main(String[] args) throws Exception {

		crawler c = new crawler("myCrawler", false);
		c.start();
		searcher s = new searcher("mySearcher", c.visitedSites);
		s.start();

	}

}
