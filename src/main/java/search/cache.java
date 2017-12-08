package search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

public class cache {
	/***
	 * 
	 * @param o Data to write to cache
	 * @throws IOException Cannot write data
	 */
	public static void writeCache(Serializable o ) throws IOException {
		byte[] asByteArray = SerializationUtils.serialize(o);

		FileOutputStream output = new FileOutputStream("cache.txt");
		output.write(asByteArray);
		output.close();

	}
/***
 * 
 * @return Object represented by cache.txt
 * @throws IOException
 */
	public static Object readCache() throws IOException {
		File f = new File("cache.txt");
		InputStream i = new FileInputStream(f);
		return SerializationUtils.deserialize(i);

	}
/***
 * 
 * @return age of cache.txt
 * @throws IOException file not found
 */
	public static long getCacheAge() throws IOException {
		File f = new File("cache.txt");
		long d = f.lastModified();
		long curTime = (System.currentTimeMillis() / 1000L);
		return curTime - d;
	}
}
