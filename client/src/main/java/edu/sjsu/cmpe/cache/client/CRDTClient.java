package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CRDTClient {

	private List<DistributedCacheService> allServers = new ArrayList<DistributedCacheService>();
	private List<DistributedCacheService> failureServers = new ArrayList<DistributedCacheService>();
	private List<DistributedCacheService> successServers = new ArrayList<DistributedCacheService>();

	public void addServerURL(String serverURL) {

		allServers.add(new DistributedCacheService(serverURL));
	}

	public boolean put(long key, String value) {
		System.out.println("Putting key value");
		for (DistributedCacheService inputServer : allServers) {
			if (!inputServer.put(key, value))
				failureServers.add(inputServer);
			successServers.add(inputServer);

		}

		if (failureServers.size() > 2) {
			// Delete logic
			for (DistributedCacheService servers : successServers) {
				servers.delete(key);
			}
			return false;
		}

		return true;

	}

	public String get(long key) {
		System.out.println("Getting key value");
		String correctValue = null;
		HashMap<String, String> values = new HashMap<String, String>();
		for (DistributedCacheService inputServer : allServers) {
			values.put(inputServer.getCacheServerURL(), inputServer.get(key));
		}

		if (values.size() != 0) {
			ArrayList<String> values1 = new ArrayList<String>(values.values());
			;
			for (String value : values1) {
				if (Collections.frequency(values1, value) >= 2) {
					correctValue = value;
					break;
				}
			}

			for (DistributedCacheService inputServer : allServers) {
				if (!inputServer.get(key).equalsIgnoreCase(correctValue))
					inputServer.put(key, correctValue);
			}
		}

		return correctValue;

	}
}