package es.ieci.tecdoc.fwktd.core.util.hash;

import java.io.ByteArrayInputStream;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import es.ieci.tecdoc.fwktd.util.hash.HashUtils;

@RunWith(BlockJUnit4ClassRunner.class)
public class HashUtilsTest {

    private static final String CONTENT = "Prueba de HashUtils";
    private static final String SHA1_HASH_BASE64 = "NBdIE4CiUwAGFc6WLoGNnk0k3W4=";
    //private static final String SHA1_HASH_HEX = "3417481380a253000615ce962e818d9e4d24dd6e";
    private static final String MD5_HASH_BASE64 = "qV/OdeicmGt4RYr7P569nQ==";
    //private static final String MD5_HASH_HEX = "a95fce75e89c986b78458afb3f9ebd9d";


    @Test
    public void testGenerateHashWithAlgProv() {

    	Base64 base64 = new Base64();

    	try {

	        // Algoritmo SHA-1
        	byte[] hash = HashUtils.generateHash(CONTENT.getBytes(), HashUtils.SHA1_ALGORITHM, null);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, new String(base64.encode(hash)));

	        // Algoritmo MD5
        	hash = HashUtils.generateHash(CONTENT.getBytes(), HashUtils.MD5_ALGORITHM, null);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(MD5_HASH_BASE64, new String(base64.encode(hash)));

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void testGenerateHashWithAlg() {

    	Base64 base64 = new Base64();

    	try {

	        // Algoritmo SHA-1
        	byte[] hash = HashUtils.generateHash(CONTENT.getBytes(), HashUtils.SHA1_ALGORITHM);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, new String(base64.encode(hash)));

	        // Algoritmo MD5
        	hash = HashUtils.generateHash(CONTENT.getBytes(), HashUtils.MD5_ALGORITHM);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(MD5_HASH_BASE64, new String(base64.encode(hash)));

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

    @Test
    public void testGenerateHash() {

    	Base64 base64 = new Base64();

    	try {

    		// Algoritmo por defecto (SHA-1)
        	byte[] hash = HashUtils.generateHash(CONTENT.getBytes());

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, new String(base64.encode(hash)));

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

    @Test
    public void testGenerateHashWithAlgProvInputStream() {

    	Base64 base64 = new Base64();

    	try {

	        // Algoritmo SHA-1
        	byte[] hash = HashUtils.generateHash(new ByteArrayInputStream(CONTENT.getBytes()), HashUtils.SHA1_ALGORITHM, null);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, new String(base64.encode(hash)));

	        // Algoritmo MD5
        	hash = HashUtils.generateHash(new ByteArrayInputStream(CONTENT.getBytes()), HashUtils.MD5_ALGORITHM, null);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(MD5_HASH_BASE64, new String(base64.encode(hash)));

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void testGenerateHashWithAlgInputStream() {

    	Base64 base64 = new Base64();

    	try {

	        // Algoritmo SHA-1
        	byte[] hash = HashUtils.generateHash(new ByteArrayInputStream(CONTENT.getBytes()), HashUtils.SHA1_ALGORITHM);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, new String(base64.encode(hash)));

	        // Algoritmo MD5
        	hash = HashUtils.generateHash(new ByteArrayInputStream(CONTENT.getBytes()), HashUtils.MD5_ALGORITHM);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(MD5_HASH_BASE64, new String(base64.encode(hash)));

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

    @Test
    public void testGenerateHashInputStream() {

    	Base64 base64 = new Base64();

    	try {

    		// Algoritmo por defecto (SHA-1)
        	byte[] hash = HashUtils.generateHash(new ByteArrayInputStream(CONTENT.getBytes()));

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, new String(base64.encode(hash)));

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

    @Test
    public void testGenerateHashBase64WithAlgProv() {

    	try {

	        // Algoritmo SHA-1
        	String hash = HashUtils.generateHashBase64(CONTENT.getBytes(), HashUtils.SHA1_ALGORITHM, null);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, hash);

	        // Algoritmo MD5
        	hash = HashUtils.generateHashBase64(CONTENT.getBytes(), HashUtils.MD5_ALGORITHM, null);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(MD5_HASH_BASE64, hash);

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void testGenerateHashBase64WithAlg() {
    	try {

	        // Algoritmo SHA-1
        	String hash = HashUtils.generateHashBase64(CONTENT.getBytes(), HashUtils.SHA1_ALGORITHM);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, hash);

	        // Algoritmo MD5
        	hash = HashUtils.generateHashBase64(CONTENT.getBytes(), HashUtils.MD5_ALGORITHM);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(MD5_HASH_BASE64, hash);

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

    @Test
    public void testGenerateHashBase64() {
    	try {

    		// Algoritmo por defecto (SHA-1)
        	String hash = HashUtils.generateHashBase64(CONTENT.getBytes());

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, hash);

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

    @Test
    public void testGenerateHashBase64WithAlgProvInputStream() {

    	try {

	        // Algoritmo SHA-1
        	String hash = HashUtils.generateHashBase64(new ByteArrayInputStream(CONTENT.getBytes()), HashUtils.SHA1_ALGORITHM, null);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, hash);

	        // Algoritmo MD5
        	hash = HashUtils.generateHashBase64(new ByteArrayInputStream(CONTENT.getBytes()), HashUtils.MD5_ALGORITHM, null);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(MD5_HASH_BASE64, hash);

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void testGenerateHashBase64WithAlgInputStream() {
    	try {

	        // Algoritmo SHA-1
        	String hash = HashUtils.generateHashBase64(new ByteArrayInputStream(CONTENT.getBytes()), HashUtils.SHA1_ALGORITHM);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, hash);

	        // Algoritmo MD5
        	hash = HashUtils.generateHashBase64(new ByteArrayInputStream(CONTENT.getBytes()), HashUtils.MD5_ALGORITHM);

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(MD5_HASH_BASE64, hash);

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

    @Test
    public void testGenerateHashBase64InputStream() {
    	try {

    		// Algoritmo por defecto (SHA-1)
        	String hash = HashUtils.generateHashBase64(new ByteArrayInputStream(CONTENT.getBytes()));

	        Assert.assertNotNull(hash);
	        Assert.assertEquals(SHA1_HASH_BASE64, hash);

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

    @Test
    public void testValidateHash() {
    	try {

    		// Algoritmo por defecto (SHA-1)
        	boolean valid = HashUtils.validateHash(CONTENT.getBytes(), SHA1_HASH_BASE64);
        	Assert.assertTrue(valid);

        	valid = HashUtils.validateHash(CONTENT.getBytes(), MD5_HASH_BASE64);
        	Assert.assertFalse(valid);

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

    @Test
    public void testValidateHashWithAlg() {
    	try {

    		// Algoritmo SHA-1
        	boolean valid = HashUtils.validateHash(CONTENT.getBytes(), SHA1_HASH_BASE64, HashUtils.SHA1_ALGORITHM);
        	Assert.assertTrue(valid);

        	valid = HashUtils.validateHash(CONTENT.getBytes(), MD5_HASH_BASE64, HashUtils.SHA1_ALGORITHM);
        	Assert.assertFalse(valid);

    		// Algoritmo MD5
        	valid = HashUtils.validateHash(CONTENT.getBytes(), MD5_HASH_BASE64, HashUtils.MD5_ALGORITHM);
        	Assert.assertTrue(valid);

        	valid = HashUtils.validateHash(CONTENT.getBytes(), SHA1_HASH_BASE64, HashUtils.MD5_ALGORITHM);
        	Assert.assertFalse(valid);

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

    @Test
    public void testValidateHashWithAlgProv() {
    	try {

    		// Algoritmo SHA-1
        	boolean valid = HashUtils.validateHash(CONTENT.getBytes(), SHA1_HASH_BASE64, HashUtils.SHA1_ALGORITHM, null);
        	Assert.assertTrue(valid);

        	valid = HashUtils.validateHash(CONTENT.getBytes(), MD5_HASH_BASE64, HashUtils.SHA1_ALGORITHM, null);
        	Assert.assertFalse(valid);

    		// Algoritmo MD5
        	valid = HashUtils.validateHash(CONTENT.getBytes(), MD5_HASH_BASE64, HashUtils.MD5_ALGORITHM, null);
        	Assert.assertTrue(valid);

        	valid = HashUtils.validateHash(CONTENT.getBytes(), SHA1_HASH_BASE64, HashUtils.MD5_ALGORITHM, null);
        	Assert.assertFalse(valid);

        } catch (Exception e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
	}

//	@Test
//	public void test() {
//
//		try {
//
//			System.out.println("> SHA-1");
//
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			byte[] hash = md.digest(CONTENT.getBytes());
//			String hashBase64 = new String(Base64.encode(hash));
//			System.out.println("> [MD5] hashBase64: " + hashBase64);
//			System.out.println("> [MD5] hex: " + getHex(hash));
//
//			md = MessageDigest.getInstance("SHA1");
//			md.update(CONTENT.getBytes());
//			hash = md.digest();
//			hashBase64 = new String(Base64.encode(hash));
//			System.out.println("> [SHA1] hashBase64: " + hashBase64);
//			System.out.println("> [SHA1] hex: " + getHex(hash));
//
//		} catch (Exception e) {
//			Assert.fail(e.toString());
//			e.printStackTrace();
//		}
//
//	}
//
//	private static final String getHex(byte[] hash) {
//		StringBuffer hexString = new StringBuffer();
//		for (int i = 0; i < hash.length; i++) {
//			String hex = Integer.toHexString(0xFF & hash[i]);
//			if (hex.length() == 1)
//				hexString.append('0');
//
//			hexString.append(hex);
//		}
//		return hexString.toString();
//	}

}
