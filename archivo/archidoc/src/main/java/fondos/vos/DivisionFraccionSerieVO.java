package fondos.vos;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import common.Constants;
import common.Globals;
import common.exceptions.UncheckedArchivoException;
import common.util.DateUtils;

import fondos.model.EstadoDivisionFS;
import fondos.model.UnidadDocumental;

/**
 * Métodos de obtención de los valores de los campos de una división de fracción
 * de serie en otras unidades documentales o fracciones de serie
 */
public class DivisionFraccionSerieVO {

	protected String idFS = null;
	protected String idFicha = null;
	protected String idNivelDocumental = null;
	protected int estado = 0;
	protected String idUsrGestor = null;
	protected Date fechaEstado = null;
	/**
	 * Objeto que representa a la fracción de serie que se está dividiendo hasta
	 * el momento de la finalización de la división o validación
	 **/
	protected UnidadDocumentalVO fraccionSerie = null;
	/**
	 * Objeto que representa a la fracción de serie que se ha dividido una vez
	 * se ha finalizado la división
	 */
	protected DivisionFSXInfo info = new DivisionFSXInfo();

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	public String getIdFS() {
		return idFS;
	}

	public void setIdFS(String idFS) {
		this.idFS = idFS;
	}

	public String getIdNivelDocumental() {
		return idNivelDocumental;
	}

	public void setIdNivelDocumental(String idNivelDocumental) {
		this.idNivelDocumental = idNivelDocumental;
	}

	public String getIdUsrGestor() {
		return idUsrGestor;
	}

	public void setIdUsrGestor(String idUsrGestor) {
		this.idUsrGestor = idUsrGestor;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getInfo() {
		return info.asXML();
	}

	public void setInfo(String xmlInfo) {
		if (StringUtils.isNotEmpty(xmlInfo))
			try {
				URL digesterRulesFile = getClass().getResource(
						Globals.RULES_INFO_FSDIVIDIDA);
				Digester digester = DigesterLoader
						.createDigester(digesterRulesFile);

				// xinfo.reset();
				digester.push(info);
				StringReader strReader = new StringReader(xmlInfo.trim());
				digester.parse(strReader);
			} catch (MalformedURLException mue) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de división de fracción de serie",
						mue);
			} catch (IOException ioe) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de división de fracción de serie",
						ioe);
			} catch (SAXException saxe) {
				throw new UncheckedArchivoException(
						"Error leyendo xml de división de fracción de serie",
						saxe);
			}
	}

	public void setInfo(UnidadDocumental udoc, String idFSEnREntrega) {
		info.setAsunto(udoc.getTitulo());
		info.setFechaExtIni(DateUtils.formatDate(udoc.getFechaInicial()));
		info.setFechaExtFin(DateUtils.formatDate(udoc.getFechaFinal()));
		info.setCodReferencia(udoc.getCodReferencia());
		info.setIdFSEnREntrega(idFSEnREntrega);
	}

	protected DivisionFSXInfo getXinfo() {
		return info;
	}

	public UnidadDocumentalVO getFraccionSerie() {
		return fraccionSerie;
	}

	public void setFraccionSerie(UnidadDocumentalVO fraccionSerie) {
		this.fraccionSerie = fraccionSerie;
	}

	// Datos del campo XInfo
	public String getCodReferencia() {
		String codRef = Constants.STRING_EMPTY;
		if (this.estado == EstadoDivisionFS.VALIDADA) {
			if (info != null)
				codRef = info.getCodReferencia();
		} else {
			if (this.fraccionSerie != null)
				codRef = this.fraccionSerie.getCodReferencia();
		}
		return codRef;
	}

	// El idFSEnREntrega está relleno desde antes de la validación puesto que no
	// va a ser un dato que se pueda modificar
	public String getIdFSEnREntrega() {
		String idFSREntrega = Constants.STRING_EMPTY;
		if (info != null)
			idFSREntrega = this.info.getIdFSEnREntrega();
		return idFSREntrega;
	}

	public void setIdFSEnREntrega(String idFSEnREntrega) {
		this.info.setIdFSEnREntrega(idFSEnREntrega);
	}

	// El idUIDeposito está relleno desde antes de la validación puesto que no
	// va a ser un dato que se pueda modificar ya que la caja va a estar
	// bloqueada mientras se hace la división
	public String getIdUIDeposito() {
		String idUIDeposito = Constants.STRING_EMPTY;
		if (info != null)
			idUIDeposito = this.info.getIdUIDeposito();
		return idUIDeposito;
	}

	public void setIdUIDeposito(String idUIDeposito) {
		this.info.setIdUIDeposito(idUIDeposito);
	}

	public String getNombreArchivo() {
		String nombreArchivo = Constants.STRING_EMPTY;
		if (info != null)
			nombreArchivo = this.info.getNombreArchivo();
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.info.setNombreArchivo(nombreArchivo);
	}

	public String getTituloSerie() {
		String tituloSerie = Constants.STRING_EMPTY;
		if (info != null)
			tituloSerie = this.info.getTituloSerie();
		return tituloSerie;
	}

	public void setTituloSerie(String tituloSerie) {
		this.info.setTituloSerie(tituloSerie);
	}

	public String getAsunto() {

		String asunto = Constants.STRING_EMPTY;
		if (this.estado == EstadoDivisionFS.VALIDADA) {
			if (info != null)
				asunto = this.info.getAsunto();
		} else {
			if (this.fraccionSerie != null)
				asunto = this.fraccionSerie.getTitulo();
		}

		return asunto;
	}

	public void setProductor(String id, String nombre) {
		OrganoProductorVO productor = new OrganoProductorVO();
		productor.setId(id);
		productor.setNombre(nombre);

		this.getXinfo().setProductor(productor);
	}

	public void setProductor(OrganoProductorVO productor) {
		this.getXinfo().setProductor(productor);
	}

	public OrganoProductorVO getProductor() {
		if (this.info != null)
			return this.info.getProductor();
		else
			return null;
	}
}
