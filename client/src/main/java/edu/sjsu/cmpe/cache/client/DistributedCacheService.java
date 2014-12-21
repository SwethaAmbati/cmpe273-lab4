package edu.sjsu.cmpe.cache.client;

import java.util.concurrent.Future;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Distributed cache service
 *
 */
public class DistributedCacheService implements CacheServiceInterface {
	private final String cacheServerUrl;

	public DistributedCacheService(String serverUrl) {
		this.cacheServerUrl = serverUrl;
	}

	public String getCacheServerURL() {
		return this.cacheServerUrl;
	}

	@Override
	public String get(long key) {
		Future<HttpResponse<JsonNode>> future = Unirest
				.get(this.cacheServerUrl + "/cache/{key}")
				.header("accept", "application/json")
				.routeParam("key", Long.toString(key))
				.asJsonAsync(new Callback<JsonNode>() {

					@Override
					public void failed(UnirestException e) {
						System.out.println("The request has failed");

					}

					@Override
					public void completed(HttpResponse<JsonNode> response) {
						if (response.getCode() != 200) {

						} else {
							String value = response.getBody().getObject()
									.getString("value");

						}
					}

					@Override
					public void cancelled() {
						System.out.println("The request has been cancelled");

					}

				});
		return cacheServerUrl;
	}

	@Override
	public boolean put(long key, String value) {

		Future<HttpResponse<JsonNode>> future = Unirest
				.put(this.cacheServerUrl + "/cache/{key}/{value}")
				.header("accept", "application/json")
				.routeParam("key", Long.toString(key))
				.routeParam("value", value)
				.asJsonAsync(new Callback<JsonNode>() {

					@Override
					public void failed(UnirestException e) {
						System.out.println("The request has failed");

					}

					@Override
					public void completed(HttpResponse<JsonNode> response) {
						if (response == null || response.getCode() != 200) {
							System.out.println("Failed to add to the cache.");

						} else {
							System.out.println("The request is successfull");

						}
					}

					@Override
					public void cancelled() {
						System.out.println("The request has been cancelled");

					}

				});
		try {
			return future != null && future.get().getCode() == 200;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public void delete(long key) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.delete(this.cacheServerUrl + "/cache/{key}")
					.header("accept", "application/json")
					.routeParam("key", Long.toString(key)).asJson();
		} catch (UnirestException e) {
			System.err.println(e);
		}
		System.out.println("response is " + response);
		if (response == null || response.getCode() != 204) {
			System.out.println("Failed to delete from the cache.");
		} else {
			System.out.println("Deleted " + key + " from "
					+ this.cacheServerUrl);
		}
	}
}
