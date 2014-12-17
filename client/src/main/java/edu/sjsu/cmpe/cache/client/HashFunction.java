package edu.sjsu.cmpe.cache.client;

/**
 * Hash Function
 *
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction {

	MessageDigest instance;

	public HashFunction() {

		try {
			instance = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
	}

	int hash(Object key) {
		instance.reset();
		instance.update(key.toString().getBytes());
		byte[] digest = instance.digest();

		int h = 0;
		for (int i = 0; i < 4; i++) {
			h <<= 8;
			h |= (digest[i]) & 0xFF;
		}
		return Math.abs(h);
	}

}
