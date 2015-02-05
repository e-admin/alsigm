package se.repositoriosECM.vo;

import java.util.Properties;

import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.util.StringUtils;

import docelectronicos.vos.IRepositorioEcmVO;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

public class RepositorioEcmInternoVO extends ContentType implements
		IRepositorioEcmVO {

	private String nombre;
	private String id;
	private String tipo = IRepositorioEcmVO.REPOSITORIO_ECM_INTERNO;
	private String idLista;
	private String nombreDataSource;
	private MotorDocumental motorDocumental;

	private Properties parametros;


	public RepositorioEcmInternoVO(ContentType contentType) {
		super();
		setId("" + contentType.getId());
		setNombre(contentType.getName());
		setParametros(contentType.getProperties());
	}

	public RepositorioEcmInternoVO(String idLista, String nombreLista,
			String nombreDataSource, MotorDocumental motorDocumental) {
		this.id = idLista;
		this.idLista = idLista;
		this.nombreDataSource = nombreDataSource;
		this.motorDocumental = motorDocumental;
		this.nombre = nombreLista;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isInterno() {
		return REPOSITORIO_ECM_INTERNO.equals(tipo);
	}

	public String getIdReal() {
		return getIdLista();
	}

	public void setParametros(Properties parametros) {
		this.parametros = parametros;
	}

	public Properties getParametros() {
		return parametros;
	}

	public String getIdLista() {

		if (idLista == null) {
			idLista = getParametro(Constants.PROPERTY_ID_LISTA_VOLUMENES);

			if (StringUtils.isEmpty(idLista)) {
				return id;
			}
		}

		return idLista;
	}

	public String getNombreDataSource() {
		if (nombreDataSource == null) {
			nombreDataSource = getParametro(Constants.PROPERTY_JDBC_DATASOURCE);
			if (StringUtils.isBlank(nombreDataSource)) {
				nombreDataSource = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionAlmacenamiento()
						.getDataSourceNameAlmacenamiento();
			}
		}
		return nombreDataSource;
	}

	public MotorDocumental getMotorDocumental() {
		if (motorDocumental == null) {
			String plataformaMotorDocumental = getParametro(Constants.PROPERTY_MOTOR_DOCUMENTAL_PLATAFORMA);
			String rutaPlataformaMotorDocumental = getParametro(Constants.PROPERTY_MOTOR_DOCUMENTAL_PLATAFORMA);
			String extensionesMotorDocumental = getParametro(Constants.PROPERTY_MOTOR_DOCUMENTAL_EXTENSIONES);
			motorDocumental = new MotorDocumental(plataformaMotorDocumental,
					rutaPlataformaMotorDocumental, extensionesMotorDocumental);
		}

		return motorDocumental;
	}


	public String getParametro(String nombreParametro){
		if(parametros != null){
			return parametros.getProperty(nombreParametro);
		}

		return null;
	}

}
