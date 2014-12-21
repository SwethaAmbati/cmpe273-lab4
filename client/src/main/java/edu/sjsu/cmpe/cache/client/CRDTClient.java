package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
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
		for (DistributedCacheService inputServer : allServers) {

			inputServer.get(key);
		}

		if (successServers.size() < 2) {

		}

		return null;

	}

}