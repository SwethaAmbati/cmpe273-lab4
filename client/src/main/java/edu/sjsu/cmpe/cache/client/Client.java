package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Client {
	public static void main(String[] args) throws Exception {
		System.out.println("Starting Cache Client...");
		CacheServiceInterface cache = new DistributedCacheService(
				"http://localhost:3000");
		CacheServiceInterface cache1 = new DistributedCacheService(
				"http://localhost:3001");
		CacheServiceInterface cache2 = new DistributedCacheService(
				"http://localhost:3002");

		List<CacheServiceInterface> servers = new ArrayList<CacheServiceInterface>();
		servers.add(cache);
		servers.add(cache1);
		servers.add(cache2);

		ConsistentHash<CacheServiceInterface> consistenthash = new ConsistentHash<CacheServiceInterface>(
				new HashFunction(), 1, servers);

		Map<Integer, String> data = new HashMap<Integer, String>();
		data.put(1, "a");
		data.put(2, "b");
		data.put(3, "c");
		data.put(4, "d");
		data.put(5, "e");
		data.put(6, "f");
		data.put(7, "g");
		data.put(8, "h");
		data.put(9, "i");
		data.put(10, "j");

		CacheServiceInterface inputCache;
        //Putting all keys and their values across three cache servers
		System.out
				.println("Putting all keys and their values across three cache servers");
		for (Entry<Integer, String> entry : data.entrySet()) {
			Integer key = entry.getKey();
			String value = entry.getValue();
			inputCache = consistenthash.get(key);
			inputCache.put(key, value);

			System.out.println("Put (" + key + "=>" + value + ")  server: "
					+ inputCache.serverValue());
		}
        //Retrieving all keys and their values from the three cache servers
		System.out
				.println("Retrieving all keys and their values from the three cache servers");
		for (Entry<Integer, String> entry : data.entrySet()) {
			Integer key = entry.getKey();
			inputCache = consistenthash.get(key);

			System.out.println("Get (" + key + ") => " + inputCache.get(key)
					+ "  server: " + inputCache.serverValue());

		}
		System.out.println("Exiting Cache Client...");
	}
}