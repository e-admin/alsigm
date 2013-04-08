package ieci.tecdoc.sgm.base.ftp;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.net.ftp.FTPClient;

public class FtpUtils {

	public static boolean comprobarConexionFTP(String direccion, String puerto, String usuario, String password) {
		boolean conexionFtp = false;
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(direccion, new Integer(puerto).intValue()) , 5000);
			FTPClient cliente = new FTPClient();
			cliente.connect(direccion, new Integer(puerto).intValue());
			conexionFtp = cliente.login(usuario, password);
			cliente.disconnect();
			socket.close();
		} catch(Exception e) {
			conexionFtp = false;
		}
		return conexionFtp;
	}
	
}
