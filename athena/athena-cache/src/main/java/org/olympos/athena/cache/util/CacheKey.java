package org.olympos.athena.cache.util;

public class CacheKey {

	private String key;

	public CacheKey(String bizPoint, String... key) {
		if ( null == bizPoint)
			throw new RuntimeException("bizPoint is null!");
		if ( null == key || key.length == 0)
			throw new RuntimeException("key is null! or the key is empty");

		StringBuilder k = new StringBuilder();
		k.append( bizPoint);

		for (String s : key)
			k.append(":").append( s);

		this.key = k.toString();
	}

	public String getKey() {
		return this.key;
	}
}
