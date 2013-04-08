package transferencias.actions;

import fondos.model.ElementoCuadroClasificacion;
import fondos.model.TipoNivelCF;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.FondoVO;
import fondos.vos.INivelCFVO;
import fondos.vos.SerieVO;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.UsuarioVO;

import java.util.List;
import java.util.Locale;

import se.NotAvailableException;
import se.procedimientos.GestorCatalogo;
import se.procedimientos.InfoBProcedimiento;
import se.procedimientos.exceptions.GestorCatalogoException;
import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.model.InvalidTipoTransferenciaException;
import transferencias.model.TipoSignaturacion;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.PrevisionVO;
import transferencias.vos.RelacionEntregaVO;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.Messages;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.UncheckedArchivoException;
import common.util.StringUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoVO;
import deposito.vos.FormatoHuecoVO;
import descripcion.model.xml.definition.DefCampoDato;
import descripcion.model.xml.definition.DefCampoEspecial;
import descripcion.model.xml.definition.DefFicha;
import descripcion.model.xml.definition.DefFichaFactory;
import descripcion.model.xml.definition.DefTipos;
import descripcion.vos.FichaVO;

/**
 * Clase que proporciona la informacion de presentacion requerida para una
 * relacion de entrega
 * 
 */
public class RelacionEntregaPO extends RelacionEntregaVO {

	UsuarioVO gestorEnOrganoRemitente = null, gestorEnArchivo = null;
	InfoBProcedimiento procedimiento = null;
	ElementoCuadroClasificacionVO funcion = null;
	FondoVO fondo = null;
	SerieVO serieOrigen = null;
	SerieVO serie = null;
	FormatoHuecoVO formato = null;
	PrevisionVO prevision = null;
	DetallePrevisionVO detallePrevision = null;
	CAOrganoVO organoRemitente = null;
	DepositoVO ubicacion = null;
	ElementoVO deposito = null;
	ArchivoVO archivoRemitente = null;
	ArchivoVO archivoReceptor = null;
	Integer nUnidadesInstalacion = null;
	Integer nUnidadesInstalacionAsignadas = null;
	INivelCFVO nivelDocumental = null;
	boolean masDeUnNivelUDocLeido = false;
	boolean masDeUnNivelUDoc = false;
	boolean camposEnFichaChequeados = false;
	boolean expedienteEnFicha = false;
	boolean productorEnFicha = false;
	FichaVO ficha = null;
	Integer numUIsReencajado = null;
	FormatoHuecoVO formatoRE = null;

	GestionControlUsuariosBI controlAccesoBI = null;
	GestorCatalogo catalogoProcedimientos = null;
	GestorEstructuraDepositoBI depositoBI = null;
	GestionPrevisionesBI previsionBI = null;
	GestionFondosBI fondoBI = null;
	GestionSeriesBI serieBI = null;
	GestionCuadroClasificacionBI cclfBI = null;
	GestionRelacionesEntregaBI relacionBI = null;
	GestionSistemaBI sistemaBI = null;
	GestionDescripcionBI descripcionBI = null;
	ServiceRepository services = null;
	GestionRelacionesEACRBI relacionEABI = null;

	RelacionEntregaPO(GestionRelacionesEntregaBI relacionBI,
			GestionControlUsuariosBI controlAccesoBI,
			GestorCatalogo catalogoProcedimientos,
			GestorEstructuraDepositoBI depositoBI,
			GestionPrevisionesBI previsionBI, GestionFondosBI fondoBI,
			GestionSeriesBI serieBI, GestionCuadroClasificacionBI cclfBI,
			GestionSistemaBI sistemaBI, GestionDescripcionBI descripcionBI,
			ServiceRepository services, GestionRelacionesEACRBI relacionEABI) {
		this.relacionBI = relacionBI;
		this.controlAccesoBI = controlAccesoBI;
		this.catalogoProcedimientos = catalogoProcedimientos;
		this.depositoBI = depositoBI;
		this.previsionBI = previsionBI;
		this.fondoBI = fondoBI;
		this.serieBI = serieBI;
		this.cclfBI = cclfBI;
		this.sistemaBI = sistemaBI;
		this.descripcionBI = descripcionBI;
		this.services = services;
		this.relacionEABI = relacionEABI;
	}

	public InfoBProcedimiento getProcedimiento()
			throws GestorCatalogoException, NotAvailableException {
		// if (getIdprocedimiento() == null)
		// return null;
		// if (procedimiento == null)
		// procedimiento =
		// catalogoProcedimientos.recuperarProcedimiento(getIdprocedimiento());
		// return procedimiento.getInformacionBasica();

		if (getIdprocedimiento() != null) {
			if (procedimiento == null) {
				List procedimientos = catalogoProcedimientos
						.recuperarInfoBProcedimientos(new String[] { getIdprocedimiento() });
				if (procedimientos != null && procedimientos.size() > 0)
					procedimiento = (InfoBProcedimiento) procedimientos.get(0);
			}
		}
		return procedimiento;
	}

	public SerieVO getSerieOrigen() {
		if (serieOrigen == null)
			serieOrigen = serieBI.getSerie(getIdserieorigen());
		return serieOrigen;
	}

	public SerieVO getSerie() {
		if (serie == null)
			serie = serieBI.getSerie(getIdseriedestino());
		return serie;
	}

	public UsuarioVO getGestorEnOrganoRemitente() {
		if (gestorEnOrganoRemitente == null)
			gestorEnOrganoRemitente = controlAccesoBI
					.getUsuario(getIdusrgestorrem());
		return gestorEnOrganoRemitente;
	}

	public UsuarioVO getGestorEnArchivo() {
		if (gestorEnArchivo == null)
			gestorEnArchivo = controlAccesoBI
					.getUsuario(getIdusrgestorarchivorec());
		return gestorEnArchivo;
	}

	public CAOrganoVO getOrganoRemitente() {
		if (organoRemitente == null)
			organoRemitente = controlAccesoBI
					.getCAOrgProductorVOXId(getIdorganoremitente());
		return organoRemitente;
	}

	public PrevisionVO getPrevision() {
		if ((prevision == null) && (StringUtils.isNotEmpty(getIdprevision()))
				&& (!RelacionEntregaVO.SIN_PREVISION.equals(getIdprevision()))) {
			prevision = previsionBI.getPrevision(getIdprevision());
			prevision = (PrevisionPO) (new PrevisionToPO(previsionBI,
					relacionBI, controlAccesoBI, fondoBI, sistemaBI, services))
					.transform(prevision);
		}
		return prevision;
	}

	public void setPrevision(PrevisionVO previsionVO) {
		this.prevision = previsionVO;
	}

	public DetallePrevisionVO getDetallePrevision() {
		if (getIddetprevision() == null)
			return null;
		if (detallePrevision == null)
			detallePrevision = previsionBI
					.getDetallePrevision(getIddetprevision());
		return detallePrevision;
	}

	public String getNombreArchivoRemitente() {
		if (archivoRemitente == null)
			archivoRemitente = sistemaBI.getArchivo(getIdarchivoremitente());
		return (archivoRemitente != null ? archivoRemitente.getNombre() : null);
	}

	public String getCodigoArchivoRemitente() {
		if (archivoRemitente == null)
			archivoRemitente = sistemaBI.getArchivo(getIdarchivoremitente());
		return (archivoRemitente != null ? archivoRemitente.getCodigo() : null);
	}

	public ArchivoVO getArchivoReceptor() {
		if (archivoReceptor == null)
			archivoReceptor = sistemaBI.getArchivo(getIdarchivoreceptor());
		return (archivoReceptor != null ? archivoReceptor : null);
	}

	public String getNombreArchivoReceptor() {
		if (archivoReceptor == null)
			archivoReceptor = sistemaBI.getArchivo(getIdarchivoreceptor());
		return (archivoReceptor != null ? archivoReceptor.getNombre() : null);
	}

	public String getCodigoArchivoReceptor() {
		if (archivoReceptor == null)
			archivoReceptor = sistemaBI.getArchivo(getIdarchivoreceptor());
		return (archivoReceptor != null ? archivoReceptor.getCodigo() : null);
	}

	public FondoVO getFondo() {
		if (fondo == null)
			fondo = fondoBI.getFondoXId(getIdfondodestino());
		return fondo;
	}

	public ElementoCuadroClasificacionVO getFuncion() {
		if (funcion == null) {
			SerieVO serie = getSerie();
			if (serie != null)
				funcion = cclfBI.getElementoCuadroClasificacion(serie
						.getIdPadre());
		}
		return funcion;
	}

	public FormatoHuecoVO getFormato() {
		if (formato == null) {
			formato = depositoBI.getFormatoHueco(getIdformatoui());
		}
		return formato;
	}

	public DepositoVO getUbicacion() throws Exception {
		if (ubicacion == null && getIddeposito() != null)
			ubicacion = depositoBI.getUbicacion(getIddeposito());
		return ubicacion;
	}

	public ElementoVO getDeposito() throws Exception {
		if (deposito == null)
			deposito = depositoBI.getNoAsignable(getIdelmtodreserva());
		return deposito;
	}

	public String getTextoNumeroUIs() {
		StringBuffer numeroUIS = new StringBuffer()
				.append(getNumeroUnidadesInstalacionOrigen());

		if (isRelacionConReencajado()) {
			numeroUIS.append(Constants.SEPARADOR_FORMATOS_REENCAJADO).append(
					getNumUIsReencajado());
		}

		return numeroUIS.toString();
	}

	public int getNumeroUnidadesInstalacionOrigen() {
		if (nUnidadesInstalacion == null) {
			nUnidadesInstalacion = new Integer(
					relacionBI.getCountUnidadesInstalacion(getId(),
							getTipotransferencia()));
		}
		return nUnidadesInstalacion.intValue();
	}

	public int getNumeroUnidadesInstalacion() {
		if (nUnidadesInstalacion == null) {
			nUnidadesInstalacion = new Integer(
					relacionBI.getNUnidadesInstalacion(getId(),
							getTipotransferencia()));
		}
		return nUnidadesInstalacion.intValue();
	}

	public int getNumeroUnidadesInstalacionAsignadas() {
		if (nUnidadesInstalacionAsignadas == null) {
			nUnidadesInstalacionAsignadas = new Integer(
					relacionBI.getNUnidadesInstalacionAsignadas(getId(),
							getTipotransferencia()));
		}
		return nUnidadesInstalacionAsignadas.intValue();
	}

	public int getTipooperacion() {
		int tipoPrevision;
		if (getIddetprevision() != null) {
			tipoPrevision = PrevisionVO.PREVISION_DETALLADA;
		} else
			tipoPrevision = PrevisionVO.PREVISION_NODETALLADA;
		try {
			return PrevisionVO.getTipoOperacion(this.getTipotransferencia(),
					tipoPrevision);
		} catch (InvalidTipoTransferenciaException ite) {
			throw new UncheckedArchivoException(ite);
		}
	}

	public String getNombreestado() {
		Locale locale = null;

		ServiceClient client = services.getServiceClient();
		if (client != null) {
			locale = client.getLocale();
		}

		return Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + "."
						+ this.getEstado(), locale);
	}

	public boolean isDetallada() {
		return this.getIddetprevision() != null;
	}

	public boolean isSignaturaSolictableEnUDoc() {
		// return isConSignatura() && !getFormato().isMultidoc();
		/*
		 * return isConSignatura() && (!getFormato().isMultidoc() ||
		 * getNivelDocumental().getId()
		 * .equals(ConfiguracionSistemaArchivoFactory
		 * .getConfiguracionSistemaArchivo() .getConfiguracionGeneral()
		 * .getIdNivelFraccionSerie()));
		 */

		return isConSignatura()
				&& (!getFormato().isMultidoc() || getNivelDocumental()
						.getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA);
	}

	public boolean isSignaturaSolictableEnUI() {
		/*
		 * return isConSignatura() && (getFormato().isMultidoc() ||
		 * !getNivelDocumental().getId()
		 * .equals(ConfiguracionSistemaArchivoFactory
		 * .getConfiguracionSistemaArchivo() .getConfiguracionGeneral()
		 * .getIdNivelFraccionSerie()));
		 */
		return isConSignatura()
				&& getFormato().isMultidoc()
				&& getNivelDocumental().getSubtipo() != ElementoCuadroClasificacion.SUBTIPO_CAJA;
		// || TipoTransferencia.INGRESO_DIRECTO.getIdentificador() ==
		// getTipotransferencia();
	}

	public String getCodigoTransferencia() {
		return CodigoTransferenciaUtils.getCodigoTransferencia(this);
	}

	public String getCodigoPrevision() {
		return CodigoTransferenciaUtils.getCodigoTransferencia(prevision
				.getAno(), getCodigoArchivoReceptor(),
				new Integer(prevision.getOrden()),
				TransferenciasConstants.FORMAT_ORDEN);

	}

	public INivelCFVO getNivelDocumental() {

		if (nivelDocumental == null)
			nivelDocumental = cclfBI.getNivelCF(getIdNivelDocumental());

		return nivelDocumental;
	}

	public void setNivelDocumental(INivelCFVO nivelDocumental) {
		this.nivelDocumental = nivelDocumental;
	}

	public boolean isNivelDocumentalFraccionSerie() {
		/*
		 * boolean ret = false; if (this.nivelDocumental != null &&
		 * this.nivelDocumental.getId()!=null)
		 * 
		 * ret =
		 * this.nivelDocumental.getId().equals(ConfiguracionSistemaArchivoFactory
		 * .getConfiguracionSistemaArchivo()
		 * .getConfiguracionGeneral().getIdNivelFraccionSerie());
		 * 
		 * return ret;
		 */

		return (this.nivelDocumental != null && this.nivelDocumental
				.getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA);
	}

	public boolean isMasDeUnNivelUDoc() {

		if (!masDeUnNivelUDocLeido) {
			List niveles = cclfBI.getNivelesByTipo(
					TipoNivelCF.UNIDAD_DOCUMENTAL, null);
			if (niveles != null && niveles.size() > 1)
				masDeUnNivelUDoc = true;
			masDeUnNivelUDocLeido = true;
		}

		return masDeUnNivelUDoc;
	}

	public boolean contieneFichaExpediente(DefFicha defFicha, String idNivel) {
		boolean ret = false;

		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();

		/*
		 * DefFicha defFicha = DefFichaFactory .getInstance(getServiceClient())
		 * .getDefFichaById(idFicha);
		 */
		if (defFicha != null) {
			if (StringUtils.isNotEmpty(idNivel)) {
				INivelCFVO nivel = cuadroBI.getNivelCF(idNivel);
				if (nivel != null) {
					if (nivel.getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
						// Si es un subtipo CAJA, comprobamos si existen en la
						// ficha el rango inicial y el final
						DefCampoDato campoDatoRI = defFicha
								.getDato(ConfiguracionSistemaArchivoFactory
										.getConfiguracionSistemaArchivo()
										.getConfiguracionDescripcion()
										.getRangoInicial());

						DefCampoDato campoDatoRF = defFicha
								.getDato(ConfiguracionSistemaArchivoFactory
										.getConfiguracionSistemaArchivo()
										.getConfiguracionDescripcion()
										.getRangoFinal());

						if (campoDatoRI != null && campoDatoRF != null)
							ret = true;

					} else {
						// Si es un subtipo unidad documental simple,
						// comprobamos si tiene el número de expediente
						DefCampoEspecial campoEspecial = defFicha
								.getDatoEspecial(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE);

						if (campoEspecial != null)
							ret = true;
					}
				}
			}
			// }
		} else {
			ret = true;
		}

		return ret;
	}

	public boolean contieneFichaProductor(DefFicha defFicha) {
		boolean ret = false;

		if (defFicha != null) {
			DefCampoDato campoDato = defFicha
					.getDato(ConfiguracionSistemaArchivoFactory
							.getConfiguracionSistemaArchivo()
							.getConfiguracionDescripcion().getProductor());
			if (campoDato != null)
				ret = true;
		} else {
			ret = true;
		}

		return ret;
	}

	public void checkCamposEnFicha() {
		if (StringUtils.isNotEmpty(this.getIdFicha())) {
			if (!this.isCamposEnFichaChequeados()) {

				DefFicha defFicha = DefFichaFactory.getInstance(
						services.getServiceClient()).getDefFichaById(
						this.getIdFicha());

				this.setExpedienteEnFicha(contieneFichaExpediente(defFicha,
						this.getIdNivelDocumental()));
				this.setProductorEnFicha(contieneFichaProductor(defFicha));
			}
		} else {
			// Si estamos en una configuración en la que no se permite
			// seleccionar la ficha, estos campos aparecerán siempre
			// if
			// (!ConfigConstants.getInstance().getPermitirFichaAltaRelacion()){
			this.setExpedienteEnFicha(true);
			this.setProductorEnFicha(true);
			// }
		}

		this.setCamposEnFichaChequeados(true);
	}

	public boolean isExpedienteEnFicha() {
		checkCamposEnFicha();
		return this.expedienteEnFicha;
	}

	public boolean isCamposEnFichaChequeados() {
		return camposEnFichaChequeados;
	}

	public void setCamposEnFichaChequeados(boolean camposEnFichaChequeados) {
		this.camposEnFichaChequeados = camposEnFichaChequeados;
	}

	public boolean isProductorEnFicha() {
		checkCamposEnFicha();
		return productorEnFicha;
	}

	public void setProductorEnFicha(boolean productorEnFicha) {

		this.productorEnFicha = productorEnFicha;
	}

	public void setExpedienteEnFicha(boolean expedienteEnFicha) {
		this.expedienteEnFicha = expedienteEnFicha;
	}

	public FichaVO getFicha() {
		if (this.ficha == null) {
			this.ficha = descripcionBI.getFicha(this.getIdFicha());
		}

		return this.ficha;
	}

	public void setFicha(FichaVO ficha) {
		this.ficha = ficha;
	}

	public String getNombreFicha() {
		String ret = Constants.STRING_EMPTY;

		if (StringUtils.isNotEmpty(this.getIdFicha())) {
			this.ficha = descripcionBI.getFicha(this.getIdFicha());
			if (this.ficha != null)
				ret = this.ficha.getNombre();
		}

		return ret;
	}

	public boolean isSignaturacionAsociadaAHueco() {

		if (archivoReceptor == null)
			archivoReceptor = sistemaBI.getArchivo(getIdarchivoreceptor());
		return (archivoReceptor != null ? TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
				.getIdentificador() == archivoReceptor.getTiposignaturacion()
				: false);

	}

	public boolean isSignaturacionAsociadaHuecoYSignaturaSolicitable() {
		return isSignaturacionAsociadaAHueco()
				&& (isSignaturaSolictableEnUDoc() || isSignaturaSolictableEnUI());
	}

	public FormatoHuecoVO getFormatoRE() {
		if (formatoRE == null)
			formatoRE = depositoBI.getFormatoHueco(getIdFormatoRe());
		return formatoRE;
	}

	public int getNumUIsReencajado() {
		if (numUIsReencajado == null) {
			numUIsReencajado = new Integer(
					relacionEABI.getNumUIsReeaCr(getId()));
		}
		return numUIsReencajado.intValue();
	}

	public int getNumUIs() {
		int numUIs = 0;
		if (isRelacionConReencajado()) {
			numUIs = getNumUIsReencajado();
		} else {
			numUIs = getNumeroUnidadesInstalacion();
		}
		return numUIs;
	}

	public FormatoHuecoVO getFormatoDestino() {
		FormatoHuecoVO formato = new FormatoHuecoVO();
		if (isRelacionConReencajado()) {
			formato = getFormatoRE();
		} else {
			formato = getFormato();
		}
		return formato;
	}

	public String getNombreFormato() {
		StringBuffer nombreFormato = new StringBuffer();

		if (getFormato() != null) {
			nombreFormato.append(getFormato().getNombre());
		}

		if (getFormatoRE() != null) {
			nombreFormato.append(Constants.SEPARADOR_FORMATOS_REENCAJADO)
					.append(getFormatoRE().getNombre());
		}

		return nombreFormato.toString();
	}
}
