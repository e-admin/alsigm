package fondos.model;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import common.Globals;
import common.exceptions.UncheckedArchivoException;
import common.util.StringUtils;

import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.NivelFichaUDocRepEcmVO;
import fondos.vos.SerieVO;

public class Serie extends ElementoCuadroClasificacion implements SerieVO {
	/** Logger de la clase */
	protected static Logger logger = Logger.getLogger(Serie.class);

	public static final DynaProperty CODIGO_BEAN_PROPERTY = new DynaProperty(
			"CODIGO");
	public static final DynaProperty DENOMINACION_BEAN_PROPERTY = new DynaProperty(
			"DENOMINACION");
	public static final DynaProperty ID_PADRE_BEAN_PROPERTY = new DynaProperty(
			"ID_PADRE");
	public static final DynaProperty ID_FONDO_BEAN_PROPERTY = new DynaProperty(
			"ID_FONDO");
	public static final DynaProperty GESTOR_BEAN_PROPERTY = new DynaProperty(
			"GESTOR");
	public static final DynaProperty OBSERVACIONES_BEAN_PROPERTY = new DynaProperty(
			"OBSERVACIONES");
	public static final DynaProperty ID_NIVEL_BEAN_PROPERTY = new DynaProperty(
			"ID_NIVEL");
	public static final DynaProperty VOLUMEN_BEAN_PROPERTY = new DynaProperty(
			"VOLUMEN");
	public static final DynaProperty INFO_FICHA_UDOC_REP_ECM_BEAN_PROPERTY = new DynaProperty(
			"INFOFICHAUDOCREPECM");

	public static final String INFO_FICHA_UDOC_REP_ECM = "INFO_FICHA_UDOC_REP_ECM";
	public static final String NIVELES_DOCUMENTALES = "NIVELES_DOCUMENTALES";
	public static final String NIVEL = "NIVEL";
	public static final String ID_NIVEL = "ID_NIVEL";
	public static final String ID_FICHADESCRPREFUDOC = "ID_FICHADESCRPREFUDOC";
	public static final String ID_FICHACLFDOCPREFUDOC = "ID_FICHACLFDOCPREFUDOC";
	public static final String ID_REPECMPREFUDOC = "ID_REPECMPREFUDOC";
	public static final String ACTUALIZAR_UDOCS = "ACTUALIZAR_UDOCS";

	public String idelementocf;
	public Date fechaestado;
	public int ultimoestadoautoriz;
	public String idusrgestor;
	public Date fextremainicial;
	public Date fextremafinal;
	public int numunidaddoc;
	public int numuinstalacion;
	public String identificacion;
	public String observaciones;
	public int estadoserie;
	String idProcedimiento;
	int tipoProcedimiento;
	double volumen;
	List nivelesFichaUDocRepEcm = null;

	// flag para saber si ya se ha identificado
	protected boolean tieneIdentificacion = false;

	public Serie() {
	}

	public Serie(String id) {
		this.idelementocf = id;
	}

	Serie(ElementoCuadroClasificacionVO elementoCF) {
		this.idelementocf = elementoCF.getId();
		this.id = elementoCF.getId();
		this.estado = elementoCF.getEstado();
		this.idNivel = elementoCF.getIdNivel();
		this.titulo = elementoCF.getTitulo();
		this.codigo = elementoCF.getCodigo();
		this.idFondo = elementoCF.getIdFondo();
		this.codRefFondo = elementoCF.getCodRefFondo();
		this.setCodReferencia(elementoCF.getCodReferencia());
		this.idPadre = elementoCF.getIdPadre();
		this.tipo = TipoNivelCF.SERIE.getIdentificador();
		this.idFichaDescr = elementoCF.getIdFichaDescr();
		this.editClfDocs = elementoCF.getEditClfDocs();
		this.idRepEcm = elementoCF.getIdRepEcm();
	}

	public int getEstadoserie() {
		return this.estadoserie;
	}

	public void setEstadoserie(int estadoserie) {
		this.estadoserie = estadoserie;
	}

	public Date getFextremafinal() {
		return this.fextremafinal;
	}

	public void setFextremafinal(Date fextremafinal) {
		this.fextremafinal = fextremafinal;
	}

	public Date getFextremainicial() {
		return this.fextremainicial;
	}

	public void setFextremainicial(Date fextremainicial) {
		this.fextremainicial = fextremainicial;
	}

	public Date getFechaestado() {
		return this.fechaestado;
	}

	public void setFechaestado(Date fechaestado) {
		this.fechaestado = fechaestado;
	}

	public String getIdelementocf() {
		return this.idelementocf;
	}

	public void setIdelementocf(String idelementocf) {
		this.idelementocf = idelementocf;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getIdusrgestor() {
		return this.idusrgestor;
	}

	public void setIdusrgestor(String idusrgestor) {
		this.idusrgestor = idusrgestor;
	}

	public int getNumuinstalacion() {
		return this.numuinstalacion;
	}

	public void setNumuinstalacion(int numuinstalacion) {
		this.numuinstalacion = numuinstalacion;
	}

	public int getNumunidaddoc() {
		return this.numunidaddoc;
	}

	public void setNumunidaddoc(int numunidaddoc) {
		this.numunidaddoc = numunidaddoc;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getUltimoestadoautoriz() {
		return this.ultimoestadoautoriz;
	}

	public void setUltimoestadoautoriz(int ultimoestadoautoriz) {
		this.ultimoestadoautoriz = ultimoestadoautoriz;
	}

	public String getIdProcedimiento() {
		return idProcedimiento;
	}

	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}

	public String getDenominacion() {
		return getTitulo();
	}

	public void setDenominacion(String denominacion) {
		setTitulo(denominacion);
	}

	public int getTipoProcedimiento() {
		return tipoProcedimiento;
	}

	public void setTipoProcedimiento(int tipoProcedimiento) {
		this.tipoProcedimiento = tipoProcedimiento;
	}

	/**
	 * Obtiene el Volumen en cm
	 */
	public double getVolumen() {
		return volumen;
	}

	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}

	/**
	 * Obtiene el Volumen en metros
	 */
	public String getVolumenEnMetros() {
		Float volumenFloat = new Float(volumen);
		float volumenMetros = volumenFloat.floatValue() / 100;
		volumenFloat = new Float(volumenMetros);
		return volumenFloat.toString();
	}

	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}

	public boolean equals(Object o) {
		boolean igual = false;
		Serie serie = (Serie) o;
		if (serie != null)
			igual = this.getId().equals(serie.getId());
		return igual;
	}

	/**
	 * Indica si el estado en que se encuentra la serie permite que se inicie
	 * una valoracion sobre ella o no
	 * 
	 * @return <b>true</b> caso de que el inicio de valoracion sea posible y
	 *         <b>false</b> cuando no lo es
	 */
	public boolean getPermitidoIniciarValoracion() {
		boolean permitidoIniciarValoracion = false;
		if (estadoserie == EstadoSerie.VIGENTE
				|| estadoserie == EstadoSerie.HISTORICA)
			permitidoIniciarValoracion = true;
		return permitidoIniciarValoracion;
	}

	public String getInfoFichaUDocRepEcm() {

		StringBuffer xmlInfo = new StringBuffer();
		if (nivelesFichaUDocRepEcm != null && nivelesFichaUDocRepEcm.size() > 0) {
			xmlInfo.append(getTag(INFO_FICHA_UDOC_REP_ECM + " version=\"1.2\""));
			xmlInfo.append(getTag(NIVELES_DOCUMENTALES));
			Iterator it = nivelesFichaUDocRepEcm.iterator();
			while (it.hasNext()) {
				NivelFichaUDocRepEcmVO nivelFichaUDocRepEcmVO = (NivelFichaUDocRepEcmVO) it
						.next();
				xmlInfo.append(getTag(NIVEL));
				xmlInfo.append(getTag(ID_NIVEL))
						.append(nivelFichaUDocRepEcmVO.getIdNivel())
						.append(getEndTag(ID_NIVEL));
				if (common.util.StringUtils.isNotEmpty(nivelFichaUDocRepEcmVO
						.getIdFichaDescrPrefUdoc()))
					xmlInfo.append(getTag(ID_FICHADESCRPREFUDOC))
							.append(nivelFichaUDocRepEcmVO
									.getIdFichaDescrPrefUdoc())
							.append(getEndTag(ID_FICHADESCRPREFUDOC));
				if (common.util.StringUtils.isNotEmpty(nivelFichaUDocRepEcmVO
						.getIdFichaClfDocPrefUdoc()))
					xmlInfo.append(getTag(ID_FICHACLFDOCPREFUDOC))
							.append(nivelFichaUDocRepEcmVO
									.getIdFichaClfDocPrefUdoc())
							.append(getEndTag(ID_FICHACLFDOCPREFUDOC));
				if (common.util.StringUtils.isNotEmpty(nivelFichaUDocRepEcmVO
						.getIdRepEcmPrefUdoc()))
					xmlInfo.append(getTag(ID_REPECMPREFUDOC))
							.append(nivelFichaUDocRepEcmVO
									.getIdRepEcmPrefUdoc())
							.append(getEndTag(ID_REPECMPREFUDOC));
				if (nivelFichaUDocRepEcmVO.isUpdateUDocs())
					xmlInfo.append(getTag(ACTUALIZAR_UDOCS))
							.append(nivelFichaUDocRepEcmVO.isUpdateUDocs())
							.append(getEndTag(ACTUALIZAR_UDOCS));
				xmlInfo.append(getEndTag(NIVEL));
			}
			xmlInfo.append(getEndTag(NIVELES_DOCUMENTALES));
			xmlInfo.append(getEndTag(INFO_FICHA_UDOC_REP_ECM));
		}

		return xmlInfo.toString();
	}

	private String getTag(String tag) {
		return "<" + tag + ">";
	}

	private String getEndTag(String tag) {
		return "</" + tag + ">";
	}

	public void setInfoFichaUDocRepEcm(String infoFichaUDocRepEcm) {
		if (StringUtils.isNotEmpty(infoFichaUDocRepEcm)) {
			try {
				URL digesterRulesFile = getClass().getResource(
						Globals.RULES_INFO_FICHA_UDOC_LVOL);
				Digester digester = DigesterLoader
						.createDigester(digesterRulesFile);

				resetInfoFichaUDocRepEcm();
				digester.push(this);
				StringReader strReader = new StringReader(
						infoFichaUDocRepEcm.trim());
				digester.parse(strReader);

			} catch (MalformedURLException mue) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de unidad documental", mue);
			} catch (IOException ioe) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de unidad documental", ioe);
			} catch (SAXException saxe) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de unidad documental", saxe);
			}
		}
	}

	public void addNivelFichaUDocRepEcm(
			NivelFichaUDocRepEcmVO nivelFichaUDocRepEcm) {
		if (this.nivelesFichaUDocRepEcm == null)
			this.nivelesFichaUDocRepEcm = new ArrayList();
		this.nivelesFichaUDocRepEcm.add(nivelFichaUDocRepEcm);
	}

	public void resetInfoFichaUDocRepEcm() {
		this.nivelesFichaUDocRepEcm = null;
	}

	public List getNivelesFichaUDocRepEcm() {
		return nivelesFichaUDocRepEcm;
	}

	public void setNivelesFichaUDocRepEcm(List nivelesFichaUDocRepEcm) {
		this.nivelesFichaUDocRepEcm = nivelesFichaUDocRepEcm;
	}

	public String getIdRepEcmPrefUdoc(String idNivel) {
		String ret = null;

		if (this.nivelesFichaUDocRepEcm != null) {
			Iterator it = this.nivelesFichaUDocRepEcm.iterator();
			while (it.hasNext()) {
				NivelFichaUDocRepEcmVO nivelFichaUDocRepEcm = (NivelFichaUDocRepEcmVO) it
						.next();

				if (nivelFichaUDocRepEcm.getIdNivel().equals(idNivel))
					ret = nivelFichaUDocRepEcm.getIdRepEcmPrefUdoc();
			}
		}

		return ret;
	}

	public String getIdFichaDescrPrefUdoc(String idNivel) {
		String ret = null;

		if (this.nivelesFichaUDocRepEcm != null) {
			Iterator it = this.nivelesFichaUDocRepEcm.iterator();
			while (it.hasNext()) {
				NivelFichaUDocRepEcmVO nivelFichaUDocRepEcm = (NivelFichaUDocRepEcmVO) it
						.next();

				if (nivelFichaUDocRepEcm.getIdNivel() != null
						&& nivelFichaUDocRepEcm.getIdNivel().equals(idNivel)) {
					ret = nivelFichaUDocRepEcm.getIdFichaDescrPrefUdoc();
					break;
				}
			}
		}

		return ret;
	}

	public String getIdFichaClfDocPrefUdoc(String idNivel) {
		String ret = null;

		if (this.nivelesFichaUDocRepEcm != null) {
			Iterator it = this.nivelesFichaUDocRepEcm.iterator();
			while (it.hasNext()) {
				NivelFichaUDocRepEcmVO nivelFichaUDocRepEcm = (NivelFichaUDocRepEcmVO) it
						.next();

				if (nivelFichaUDocRepEcm.getIdNivel() != null
						&& nivelFichaUDocRepEcm.getIdNivel().equals(idNivel)) {
					ret = nivelFichaUDocRepEcm.getIdFichaClfDocPrefUdoc();
					break;
				}
			}
		}

		return ret;
	}

	public boolean isEnEstudio() {
		if (EstadoSerie.EN_ESTUDIO == estadoserie) {
			return true;
		}
		return false;
	}

}