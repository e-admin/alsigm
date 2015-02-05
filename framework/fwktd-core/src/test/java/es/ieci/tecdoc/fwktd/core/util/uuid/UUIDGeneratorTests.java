/*
 * 
 */
package es.ieci.tecdoc.fwktd.core.util.uuid;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.safehaus.uuid.Jug;

import es.ieci.tecdoc.fwktd.util.uuid.UUIDGenerator;

@RunWith(JUnit4.class)
public class UUIDGeneratorTests {

	@Test
	public void testRandomUUIDGeneration() {
		UUID uuid = UUIDGenerator.generateRandomBasedUUID();

		Assert.assertNotNull(uuid);
		Assert.assertEquals(4, uuid.version());
	}

	@Test
	public void testTimeBasedUUIDGeneration() {
		UUID uuid = UUIDGenerator.generateTimeBasedUUID();

		Assert.assertNotNull(uuid);
		Assert.assertEquals(1, uuid.version());
	}

	@Test
	public void testNameBasedUUIDGeneration() {
		Jug jug = new Jug();
//		EthernetAddress address = NativeInterfaces.getPrimaryInterface();
//		System.out.println(address.toString());
		com.ccg.net.ethernet.EthernetAddress ea = com.ccg.net.ethernet.EthernetAddress
				.getPrimaryAdapter();

		// UUID uuid = UUIDGenerator.generateNameBasedUUID();
		//
		// Assert.assertNotNull(uuid);
		// Assert.assertEquals(3, uuid.version());
	}

}
