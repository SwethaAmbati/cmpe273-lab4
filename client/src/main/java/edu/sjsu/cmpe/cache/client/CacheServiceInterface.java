package edu.sjsu.cmpe.cache.client;

/**
 * Cache Service Interface
 *
 */
public interface CacheServiceInterface {
	public String get(long key);

	public boolean put(long key, String value);

	public void delete(long key);
}
