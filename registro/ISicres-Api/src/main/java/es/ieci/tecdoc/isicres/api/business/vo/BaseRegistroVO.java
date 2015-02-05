package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoRegistroEnum;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * un registro siempre necesita tener el idLibro para saber a que libro
 * pertenece
 */
public class BaseRegistroVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = 8228740534287297696L;

	/**
	 * Todos lso registros pertenecen a algún libro, este campo es el
	 * identificador del libro al que pertenecen y el identificaro del registro
	 * dentro del libro
	 */
	protected IdentificadorRegistroVO id;

	/**
	 * Fld 1
	 */
	protected String numeroRegistro;

	/**
	 * Fld 5
	 */
	protected OficinaVO oficinaRegistro;

	/**
	 * Fld 3
	 */
	protected UsuarioVO usuarioRegistro;

	/**
	 * Fld 2
	 */
	protected Date fechaRegistro;

	/**
	 * Fld 4
	 */
	protected Date fechaTrabajo;

	/**
	 * No necesaria
	 */
	protected Date fechaAlta;

	/**
	 * Fld 7
	 */
	protected UnidadAdministrativaVO unidadAdministrativaOrigen;

	/**
	 * Fld 8
	 */
	protected UnidadAdministrativaVO unidadAdministrativaDestino;

	/**
	 * Fld 17 para Entrada
	 *
	 * Fld 13 para Salida
	 */
	protected String resumen;

	/**
	 * Fld 16 para Entrada
	 *
	 * Fld 12 para Salida
	 */
	protected TipoAsuntoVO tipoAsunto;

	/**
	 * Fld 14 y 15 para Entrada
	 *
	 * Fld 10 y 11 para Salida
	 */
	protected TransporteVO transporte;

	/**
	 * los intererados del registro de tipo <code>InteresadoVO</code>
	 */
	protected List<InteresadoVO> interesados;

	/**
	 * interesado principal o remitente , debe ser uno de la lista de terceros
	 *
	 * Fld 9
	 */
	protected InteresadoVO interesadoPrincipal;

	/**
	 * los documentos del registro <code>DocumentoRegistroVO</code>
	 */
	protected List<DocumentoRegistroVO> documentos;

	/**
	 * Estado del registro
	 *
	 * Fld 6
	 */
	protected EstadoRegistroEnum estado;

	/**
	 * lista de registros de tipo
	 * <code>BaseRegistroVO<code> ya que pueden estar relacionados con registros de libros de entrada, salida,..
	 */
	protected List<BaseRegistroVO> registrosRelacionados;

	/**
	 * Comentario
	 *
	 * Fld 18 para Entrada
	 *
	 * Fld 14 para Salida
	 */
	protected String comentario;

	/**
	 * lista de campos adicionales a los estandar que pueden tener los
	 * registros. de tipo <code>CampoAdicionalRegistroVO</code>
	 */
	protected List<CampoAdicionalRegistroVO> camposAdicionales;

	public IdentificadorRegistroVO getId() {
		return id;
	}

	public void setId(IdentificadorRegistroVO id) {
		this.id = id;
	}

	public String getIdRegistro() {
		String result = null;
		if (this.id != null) {
			result = id.getIdRegistro();
		}
		return result;
	}

	public void setIdRegistro(String idRegistro) {
		if (this.id == null) {
			this.id = new IdentificadorRegistroVO();
		}
		this.id.setIdRegistro(idRegistro);
	}

	public String getIdLibro() {
		String result = null;
		if (this.id != null) {
			result = id.getIdLibro();
		}
		return result;
	}

	public void setIdLibro(String idLibro) {
		if (this.id == null) {
			this.id = new IdentificadorRegistroVO();
		}
		this.id.setIdLibro(idLibro);
	}

	public List<InteresadoVO> getInteresados() {
		return interesados;
	}

	public void setInteresados(List<InteresadoVO> interesados) {
		this.interesados = interesados;
	}

	public InteresadoVO getInteresadoPrincipal() {
		if (null == interesadoPrincipal) {
			interesadoPrincipal = new InteresadoVO();
		}
		return interesadoPrincipal;
	}

	public void setInteresadoPrincipal(InteresadoVO interesadoPrincipal) {
		this.interesadoPrincipal = interesadoPrincipal;
	}

	public List<DocumentoRegistroVO> getDocumentos() {
		if (null == documentos) {
			documentos = new ArrayList<DocumentoRegistroVO>();
		}
		return documentos;
	}

	public void setDocumentos(List<DocumentoRegistroVO> documentos) {
		this.documentos = documentos;
	}

	public EstadoRegistroEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoRegistroEnum estado) {
		this.estado = estado;
	}

	public List<BaseRegistroVO> getRegistrosRelacionados() {
		if (null == registrosRelacionados) {
			registrosRelacionados = new ArrayList<BaseRegistroVO>();
		}
		return registrosRelacionados;
	}

	public void setRegistrosRelacionados(
			List<BaseRegistroVO> registrosRelacionados) {
		this.registrosRelacionados = registrosRelacionados;
	}

	public List<CampoAdicionalRegistroVO> getCamposAdicionales() {
		if (null == camposAdicionales) {
			camposAdicionales = new ArrayList<CampoAdicionalRegistroVO>();
		}
		return camposAdicionales;
	}

	public void setCamposAdicionales(
			List<CampoAdicionalRegistroVO> camposAdicionales) {
		this.camposAdicionales = camposAdicionales;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public OficinaVO getOficinaRegistro() {
		if (null == oficinaRegistro) {
			oficinaRegistro = new OficinaVO();
		}
		return oficinaRegistro;
	}

	public void setOficinaRegistro(OficinaVO oficinaRegistro) {
		this.oficinaRegistro = oficinaRegistro;
	}

	public UsuarioVO getUsuarioRegistro() {
		if (null == usuarioRegistro) {
			usuarioRegistro = new UsuarioVO();
		}
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(UsuarioVO usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public TipoAsuntoVO getTipoAsunto() {
		if (null == tipoAsunto) {
			tipoAsunto = new TipoAsuntoVO();
		}
		return tipoAsunto;
	}

	public void setTipoAsunto(TipoAsuntoVO tipoAsunto) {
		this.tipoAsunto = tipoAsunto;
	}

	public TransporteVO getTransporte() {
		if (null == transporte) {
			transporte = new TransporteVO();
		}
		return transporte;
	}

	public void setTransporte(TransporteVO transporte) {
		this.transporte = transporte;
	}

	public UnidadAdministrativaVO getUnidadAdministrativaOrigen() {
		if (null == unidadAdministrativaOrigen) {
			unidadAdministrativaOrigen = new UnidadAdministrativaVO();
		}
		return unidadAdministrativaOrigen;
	}

	public void setUnidadAdministrativaOrigen(
			UnidadAdministrativaVO unidadAdministrativaOrigen) {
		this.unidadAdministrativaOrigen = unidadAdministrativaOrigen;
	}

	public UnidadAdministrativaVO getUnidadAdministrativaDestino() {
		if (null == unidadAdministrativaDestino) {
			unidadAdministrativaDestino = new UnidadAdministrativaVO();
		}
		return unidadAdministrativaDestino;
	}

	public void setUnidadAdministrativaDestino(
			UnidadAdministrativaVO unidadAdministrativaDestino) {
		this.unidadAdministrativaDestino = unidadAdministrativaDestino;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
