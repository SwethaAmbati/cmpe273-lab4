package edu.sjsu.cmpe.cache.client;

public class Client {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting Cache Client...");

		CRDTClient crdtclient = new CRDTClient();
		crdtclient.addServerURL("http://localhost:3000");
		crdtclient.addServerURL("http://localhost:3001");
		crdtclient.addServerURL("http://localhost:3002");

		// First HTTP PUT call to store “a” to key 1.
		boolean putvalue1 = crdtclient.put(1, "a");
		if (putvalue1 == true) {
			System.out.println("1 => a");
		}
		Thread.sleep(30 * 1000);

		// Second HTTP PUT call to update key 1 value to “b”.

		boolean putvalue2 = crdtclient.put(1, "b");
		if (putvalue2 == true) {
			System.out.println("1 => b");
		}

		Thread.sleep(30 * 1000);

		// Final HTTP GET call to retrieve key “1” value.
		String value = crdtclient.get(1);
		System.out.println(value);

		System.out.println("Existing Cache Client...");
	}

}
