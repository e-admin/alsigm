package ieci.core.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public final class Base64Util {

	private Base64Util() {
	}

	public static String encode(byte decData[]) {
		BASE64Encoder encoder = new BASE64Encoder();
		String encData = encoder.encode(decData);
		return encData;
	}

	public static String encodeString(String decData) {
		return encode(decData.getBytes());
	}

	public static byte[] decode(String encData) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		byte decData[] = decoder.decodeBuffer(encData);
		return decData;
	}

	public static String decodeToString(String encData) throws Exception {
		return new String(decode(encData));
	}
}