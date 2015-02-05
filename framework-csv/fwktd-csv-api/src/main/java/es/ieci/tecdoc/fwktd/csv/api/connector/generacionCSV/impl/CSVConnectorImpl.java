package es.ieci.tecdoc.fwktd.csv.api.connector.generacionCSV.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.csv.api.connector.BaseConnector;
import es.ieci.tecdoc.fwktd.csv.api.connector.generacionCSV.CSVConnector;
import es.ieci.tecdoc.fwktd.csv.api.manager.TiemposManager;
import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;
import es.ieci.tecdoc.fwktd.util.hash.HashUtils;

/**
 * Implementación del conector de generación de Códigos Seguros de Verificación
 * (CSV).
 *
 * Esta implementación está basada en la Orden JUS de 10 de enero de 2011 por la
 * que se establece el sistema de códigos seguros de verificación de documentos
 * en el desarrollo de actuaciones automatizadas del ministerio de justicia.
 *
 * El código seguro de verificación tendrá una longitud total de 20 caracteres y
 * se generará del modo siguiente:
 *
 * a) en primer lugar, se generará una cadena de caracteres uniendo la dirección
 * MAC del servidor, el tiempo actual en milisegundos, un número aleatorio y la
 * petición recibida como cadena de caracteres.
 *
 * b) Sobre esta cadena de caracteres resultante, se aplica un algoritmo SHA1
 * para generar un hash, el cual será truncado a 15 bytes.
 *
 * c) Una vez obtenido este código, se codificará en base64 con el fin de
 * obtener 20 caracteres alfanuméricos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CSVConnectorImpl extends BaseConnector implements CSVConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CSVConnectorImpl.class);

	/**
	 * Manager de tiempos.
	 */
	private TiemposManager tiemposManager = null;

	/**
	 * Constructor.
	 */
	public CSVConnectorImpl() {
		super();
	}

	public TiemposManager getTiemposManager() {
		return tiemposManager;
	}

	public void setTiemposManager(TiemposManager tiemposManager) {
		this.tiemposManager = tiemposManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.api.connector.generacionCSV.CSVConnector#generarCSV(es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm)
	 */
	public String generarCSV(InfoDocumentoCSVForm infoDocumentoForm) {

		logger.info("Llamada a generarCSV: {}", infoDocumentoForm);

		/*
		 * Paso 1: Se generará una cadena de caracteres uniendo la dirección MAC
		 * del servidor, el tiempo actual en milisegundos, un número aleatorio y
		 * la petición recibida como cadena de caracteres.
		 */
		String cadena = new StringBuffer()
			.append(getMACAddress())
			.append(getTiempoActual())
			.append(getNumeroAleatorio())
			.append(getPeticion(infoDocumentoForm))
			.toString();
		logger.info("Paso 1 - cadena de caracteres: {}", cadena);

		/*
		 * Paso 2: Sobre esta cadena de caracteres resultante, se aplica un
		 * algoritmo SHA1 para generar un hash, el cual será truncado a 15 bytes.
		 */
		byte[] hash = generarHash(cadena.getBytes());
		hash = ArrayUtils.subarray(hash, 0, 15);
		logger.info("Paso 2 - código hash truncado a 15 bytes: {}", hash);

		/*
		 * Paso 3: Una vez obtenido este código, se codificará en base64 con el
		 * fin de obtener 20 caracteres alfanuméricos.
		 */
		Base64 base64 = new Base64();
		String hashBase64 = base64.encodeToString(hash);
		logger.info("Paso 3 - código hash truncado a 15 bytes en base64: {}", hashBase64);

		logger.info("CSV: {}", hashBase64);

		return hashBase64;
	}

	protected String getMACAddress() {

		String macAddress = null;

		// try {
		//
		// InetAddress inetAddress = InetAddress.getLocalHost();
		// log("InetAddress: " + inetAddress);
		//
		// NetworkInterface networkInterface =
		// NetworkInterface.getByInetAddress(inetAddress);
		// if (networkInterface != null) {
		// byte[] mac = networkInterface.getHardwareAddress();
		// if (mac != null) {
		//
		// /*
		// * Extract each array of mac address and convert it to heca with the
		// * following format 08-00-27-DC-4A-9E.
		// */
		// for (int i=0; i < mac.length; i++) {
		// System.out.format("%02X%s", mac[i], (i<mac.length-1 ? "-" : ""));
		// }
		//
		// } else {
		// log("Address doesn't exist or is not accesible.");
		// }
		// } else {
		// log("Network Interface for the specified address is not found.");
		// }
		//
		// } catch (UnknownHostException e) {
		// e.printStackTrace();
		// } catch (SocketException e) {
		// e.printStackTrace();
		// }

		Process p = null;
		BufferedReader in = null;

		try {
			String osname = System.getProperty("os.name", "");

			if (osname.startsWith("Windows")) {
				p = Runtime.getRuntime().exec(
						new String[] { "ipconfig", "/all" }, null);
			}
			// Solaris code must appear before the generic code
			else if (osname.startsWith("Solaris") || osname.startsWith("SunOS")) {
				String hostName = getFirstLineOfCommand("uname", "-n");
				if (hostName != null) {
					p = Runtime.getRuntime().exec(
							new String[] { "/usr/sbin/arp", hostName }, null);
				}
			} else if (new File("/usr/sbin/lanscan").exists()) {
				p = Runtime.getRuntime().exec(
						new String[] { "/usr/sbin/lanscan" }, null);
			} else if (new File("/sbin/ifconfig").exists()) {
				p = Runtime.getRuntime().exec(
						new String[] { "/sbin/ifconfig", "-a" }, null);
			}

			if (p != null) {
				in = new BufferedReader(new InputStreamReader(
						p.getInputStream()), 128);
				String l = null;
				while ((l = in.readLine()) != null) {
					macAddress = parse(l);
					if (macAddress != null
					// && Hex.parseShort(macAddress) != 0xff) {
					) {
						break;
					}
				}
			}

		} catch (SecurityException ex) {
			// Ignore it.
		} catch (IOException ex) {
			// Ignore it.
		} finally {
			if (p != null) {
				if (in != null) {
					try {
						in.close();
					} catch (IOException ex) {
						// Ignore it.
					}
				}
				try {
					p.getErrorStream().close();
				} catch (IOException ex) {
					// Ignore it.
				}
				try {
					p.getOutputStream().close();
				} catch (IOException ex) {
					// Ignore it.
				}
				p.destroy();
			}
		}

		logger.info("Dirección MAC: {}", macAddress);

		return macAddress;
	}

	protected long getTiempoActual() {

		long tiempo = getTiemposManager().getFechaActual().getTime();
		logger.info("Tiempo actual en milisegundos: {}", tiempo);

		return tiempo;
	}

	protected long getNumeroAleatorio() {

		long numeroAleatorio = RandomUtils.nextLong();
		logger.info("Número aleatorio: {}", numeroAleatorio);

		return numeroAleatorio;
	}

	protected String getPeticion(InfoDocumentoCSVForm infoDocumentoForm) {

		String peticion = infoDocumentoForm.toXML();
		logger.info("Petición: {}", peticion);

		return peticion;
	}

	protected byte[] generarHash(byte[] contenido) {

		byte[] hash = null;

		if (contenido != null) {
			try {
				hash = HashUtils.generateHash(contenido, HashUtils.SHA1_ALGORITHM);
			} catch (Throwable t) {
				logger.error("Error al generar el hash de la cadena de caracteres", t);
				throw new CSVException(
						"error.csv.connector.generacionCSV.generarHash", null,
						"Error al generar el hash de la cadena de caracteres: " + t.toString());
			}
		}

		if (logger.isInfoEnabled()) {
			Base64 base64 = new Base64();
			logger.info("Hash generado: {}", base64.encode(hash));
		}

		return hash;
	}

	/**
	 * Returns the first line of the shell command.
	 *
	 * @param commands
	 *            the commands to run
	 * @return the first line of the command
	 * @throws IOException
	 */
	static String getFirstLineOfCommand(String... commands) throws IOException {

		Process p = null;
		BufferedReader reader = null;

		try {
			p = Runtime.getRuntime().exec(commands);
			reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()), 128);

			return reader.readLine();
		} finally {
			if (p != null) {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ex) {
						// Ignore it.
					}
				}
				try {
					p.getErrorStream().close();
				} catch (IOException ex) {
					// Ignore it.
				}
				try {
					p.getOutputStream().close();
				} catch (IOException ex) {
					// Ignore it.
				}
				p.destroy();
			}
		}

	}

	/**
	 * Attempts to find a pattern in the given String.
	 *
	 * @param in
	 *            the String, may not be <code>null</code>
	 * @return the substring that matches this pattern or <code>null</code>
	 */
	static String parse(String in) {

		String out = in;

		// lanscan

		int hexStart = out.indexOf("0x");
		if (hexStart != -1 && out.indexOf("ETHER") != -1) {
			int hexEnd = out.indexOf(' ', hexStart);
			if (hexEnd > hexStart + 2) {
				out = out.substring(hexStart, hexEnd);
			}
		}

		else {

			int octets = 0;
			int lastIndex, old, end;

			if (out.indexOf('-') > -1) {
				out = out.replace('-', ':');
			}

			lastIndex = out.lastIndexOf(':');

			if (lastIndex > out.length() - 2) {
				out = null;
			} else {

				end = Math.min(out.length(), lastIndex + 3);

				++octets;
				old = lastIndex;
				while (octets != 5 && lastIndex != -1 && lastIndex > 1) {
					lastIndex = out.lastIndexOf(':', --lastIndex);
					if (old - lastIndex == 3 || old - lastIndex == 2) {
						++octets;
						old = lastIndex;
					}
				}

				if (octets == 5 && lastIndex > 1) {
					out = out.substring(lastIndex - 2, end).trim();
				} else {
					out = null;
				}

			}

		}

		if (out != null && out.startsWith("0x")) {
			out = out.substring(2);
		}

		return out;
	}

}
