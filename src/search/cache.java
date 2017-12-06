package search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang3.SerializationUtils;

public class cache {
	public static void writeCache(Object o) throws IOException {
		byte[] asByteArray = SerializationUtils.serialize((Serializable) o);

		FileOutputStream output = new FileOutputStream("src/search/cache.txt");
		output.write(asByteArray);
		output.close();

	}

	public static Object readCache() throws IOException {
		File f = new File("src/search/cache.txt");
		InputStream i = new FileInputStream(f);
		return SerializationUtils.deserialize(i);

	}

	public static long getCacheAge() throws IOException {
		File f = new File("src/search/cache.txt");
		long d = f.lastModified();
		long curTime = (System.currentTimeMillis() / 1000L);
		return curTime - d;
	}
}
