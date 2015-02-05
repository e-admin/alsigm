package common;

import xml.config.ConfiguracionArchivoManager;

public class OrganizationMessages {

	private static final String COPYRIGHT = "organizacion.copyright";
	private static final String PORTAL = "organizacion.aviso_legal.portal";
	/*
	 * private static final String ENTITLEMENT =
	 * "organizacion.aviso_legal.titularidad"; private static final String
	 * ENTITLEMENT_ARTICLE = "organizacion.aviso_legal.titularidadArticulo";
	 * private static final String OFFICIAL_CONTENT =
	 * "organizacion.aviso_legal.contenidoOficial";
	 */
	private static final String ORGANIZATION = "organizacion.archivo.informe.cartelas.archivo.organizacion";

	private static final String LEGAL_NOTICE_P1 = "organizacion.aviso_legal.parrafo1";
	private static final String LEGAL_NOTICE_P2 = "organizacion.aviso_legal.parrafo2";
	private static final String LEGAL_NOTICE_P3 = "organizacion.aviso_legal.parrafo3";
	private static final String LEGAL_NOTICE_P4 = "organizacion.aviso_legal.parrafo4";
	private static final String LEGAL_NOTICE_P5 = "organizacion.aviso_legal.parrafo5";
	private static final String LEGAL_NOTICE_P6 = "organizacion.aviso_legal.parrafo6";

	public static final String TITLE = "organizacion.archivo.informe.titulo";
	public static final String SUBTITLE1 = "organizacion.archivo.informe.subtitulo1";
	public static final String SUBTITLE2 = "organizacion.archivo.informe.subtitulo2";
	public static final String SUBTITLE3 = "organizacion.archivo.informe.subtitulo3";
	public static final String POSTAL_ADDRESS = "organizacion.archivo.informe.direccion";
	public static final String TITLE_CARTELAS_ARCHIVO = "organizacion.archivo.informe.cartelas.archivo.titulo";
	public static final String TITLE_CARTELAS_DEPOSITO = "organization.archivo.informe.cartelas.deposito.titulo";
	public static final String RECIBI = "organization.archivo.informe.informeSolicitudPrestamo.firmaYSelloArchivo";

	protected static final String DEFAULT_COUNTRY = "organizacion.archivo.transferencias.paisDefecto";
	protected static final String DEFAULT_PROVINCE = "organizacion.archivo.transferencias.comunidadDefecto";

	// private static final String BUNDLE_NAME =
	// "resources.OrganizationResources";

	private static OrganizationMessages organizationMessages = null;

	private OrganizationMessages() {
	}

	public static String getString(String key) {

		try {
			return ConfiguracionArchivoManager.getInstance()
					.getOrganizationProperties().getProperty(key);
		} catch (Exception e) {
			return key;
		}
	}

	public static String getCopyright() {
		return getString(COPYRIGHT);
	}

	public static String getPortal() {
		return getString(PORTAL);
	}

	public static String getLegalNoticeP1() {
		return getString(LEGAL_NOTICE_P1);
	}

	public static String getLegalNoticeP2() {
		return getString(LEGAL_NOTICE_P2);
	}

	public static String getLegalNoticeP3() {
		return getString(LEGAL_NOTICE_P3);
	}

	public static String getLegalNoticeP4() {
		return getString(LEGAL_NOTICE_P4);
	}

	public static String getLegalNoticeP5() {
		return getString(LEGAL_NOTICE_P5);
	}

	public static String getLegalNoticeP6() {
		return getString(LEGAL_NOTICE_P6);
	}

	public static String getOrganization() {
		return getString(ORGANIZATION);
	}

	/*
	 * public static String getDefaultCountry() { return
	 * getString(DEFAULT_COUNTRY); } public static String getDefaultProvinces()
	 * { return getString(DEFAULT_PROVINCE); }
	 */
	public static String getTitleCartelasArchivo() {
		return getString(TITLE_CARTELAS_ARCHIVO);
	}

	public static String getTitleCartelasDeposito() {
		return getString(TITLE_CARTELAS_DEPOSITO);
	}

	public static OrganizationMessages getInstance() {
		if (organizationMessages == null)
			organizationMessages = new OrganizationMessages();
		return organizationMessages;
	}
}
