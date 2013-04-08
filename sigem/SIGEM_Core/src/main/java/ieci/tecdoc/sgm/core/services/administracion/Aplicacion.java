package ieci.tecdoc.sgm.core.services.administracion;

/**
 * $Id: Aplicacion.java,v 1.1.2.2 2008/04/14 09:41:46 afernandez Exp $
 */

public class Aplicacion {

		private String identificador;
		private String definicion;
		private String contextoApp;
		private String servidorApp;
		private String puertoApp;
		private String protocolo;
		
		public String getContextoApp() {
			return contextoApp;
		}
		public void setContextoApp(String contextoApp) {
			this.contextoApp = contextoApp;
		}
		public String getIdentificador() {
			return identificador;
		}
		public void setIdentificador(String identificador) {
			this.identificador = identificador;
		}
		
		public String getServidorApp() {
			return servidorApp;
		}
		public void setServidorApp(String servidorApp) {
			this.servidorApp = servidorApp;
		}
		
		public String getPuertoApp() {
			return puertoApp;
		}
		public void setPuertoApp(String puertoApp) {
			this.puertoApp = puertoApp;
		}
		public String getDefinicion() {
			return definicion;
		}
		public void setDefinicion(String definicion) {
			this.definicion = definicion;
		}
		public String getProtocolo() {
			return protocolo;
		}
		public void setProtocolo(String protocolo) {
			this.protocolo = protocolo;
		}
		
		
		
		
}
