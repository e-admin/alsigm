/*
 * Created on 28-nov-2005
 *
 */
package common.navigation;

import org.apache.commons.lang.StringUtils;

public class TitlesToolBar {
	static String NAVIGATION_KEY = "NavigationTitle_";

	/**
	 * @param keyClientInvocation
	 * @return Retorna la key del message que ira en el resource.
	 * 
	 *         La key inicialmente sera la misma q la que se asigno a los
	 *         keysClients, pero sustituyendo el token comun
	 *         'KeysClientsInvocations.getKeyToken()' por otro NAVIGATION_KEY
	 */
	private static String getResourceKey(String keyClientInvocation) {
		String keyClientInvocationToken = KeysClientsInvocations.getKeyToken();
		if (keyClientInvocation.indexOf(keyClientInvocationToken) != -1)
			return StringUtils.replace(keyClientInvocation,
					keyClientInvocationToken, NAVIGATION_KEY);

		return new StringBuffer(NAVIGATION_KEY).append(keyClientInvocation)
				.toString();
	}

	public static String getATitleForClientInvocation(ClientInvocation cli) {
		return getResourceKey(cli.getKeyClient());
	}

	/**
	 * 
	 * @param keyClientInvocation
	 *            @link KeysClientsInvocations
	 * @return
	 */
	public static String getATitleKeyClientInvocation(String keyClientInvocation) {
		return getResourceKey(keyClientInvocation);
	}

	public static final String ORGANIZACION_VER_ELEMENTO = getResourceKey("ORGANIZACION_VER_ELEMENTO");
	public static final String ORGANIZACION_EDITAR_ELEMENTO = getResourceKey("ORGANIZACION_EDITAR_ELEMENTO");
	public static final String ORGANIZACION_CREAR_INSTITUCION = getResourceKey("ORGANIZACION_CREAR_INSTITUCION");

	public static final String CUADRO_VER_CLASIFICADOR_FONDO = getResourceKey("CUADRO_VER_CLASIFICADOR_FONDO");
	public static final String CUADRO_VER_FONDO = getResourceKey("CUADRO_VER_FONDO");
	public static final String CUADRO_VER_SERIE = getResourceKey("CUADRO_VER_SERIE");
	public static final String CUADRO_VER_CLASIFICADOR_SERIES = getResourceKey("CUADRO_VER_CLASIFICADOR_SERIES");

	public static final String DEPOSITO_VER_ELEMENTO = getResourceKey("DEPOSITO_VER_ELEMENTO_");
	public static final String DEPOSITO_EDITAR_ELEMENTO = getResourceKey("DEPOSITO_EDITAR_ELEMENTO_");
	public static final String DEPOSITO_CREAR_ELEMENTO = getResourceKey("DEPOSITO_CREAR_ELEMENTO_");
}
