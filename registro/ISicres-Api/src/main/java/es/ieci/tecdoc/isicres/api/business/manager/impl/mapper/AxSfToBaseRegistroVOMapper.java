package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;

import es.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager;
import es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.TransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoRegistroEnum;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;

/**
 * Clase que se encarga de mapear las propiedades de una instancia de
 * <code>AxSf</code> en una de tipo <code>BaseRegistroVO</code>.
 *
 * @author IECISA
 *
 */
public class AxSfToBaseRegistroVOMapper {

	public AxSfToBaseRegistroVOMapper(UsuarioVO anUsuario,
			TipoAsuntoManager anTipoAsuntoManager,
			UnidadAdministrativaManager anUnidadAdministrativaManager,
			String anIdLibro, String anIdRegistro) {
		setUnidadAdministrativaManager(anUnidadAdministrativaManager);
		setTipoAsuntoManager(anTipoAsuntoManager);
		setIdLibro(anIdLibro);
		setIdRegistro(anIdRegistro);
		setUsuario(anUsuario);
	}

	public AxSfToBaseRegistroVOMapper(UsuarioVO anUsuario,
			TipoAsuntoManager anTipoAsuntoManager,
			UnidadAdministrativaManager anUnidadAdministrativaManager,
			String anIdLibro) {
		setUnidadAdministrativaManager(anUnidadAdministrativaManager);
		setTipoAsuntoManager(anTipoAsuntoManager);
		setIdLibro(anIdLibro);
		setUsuario(anUsuario);
	}
	
	
	
	/**
	 * Metodo que a partir del objeto {@link AxSf} compone un
	 * {@link BaseRegistroVO} con opcion a que el mapeo sea completado consultando a bd o solo con la informacion que tenemos
	 *
	 * @param bookFolder
	 * @return {@link BaseRegistroVO}
	 */
	protected BaseRegistroVO map(AxSf bookFolder,boolean completeMap) {
		
		BaseRegistroVO registro = new BaseRegistroVO();
		
		
		if (bookFolder instanceof AxSfIn){
			registro = new RegistroEntradaVO();
		}
		
		if (bookFolder instanceof AxSfOut){
			registro = new RegistroSalidaVO();
		}
		

		// Numero de registro
		registro.setNumeroRegistro(bookFolder
				.getAttributeValueAsString(AxSf.FLD1_FIELD));

		// Fecha de registro
		registro.setFechaRegistro((Date) bookFolder
				.getAttributeValue(AxSf.FLD2_FIELD));

		// Fecha de alta
		registro.setFechaAlta((Date) bookFolder
				.getAttributeValue(AxSf.FLD4_FIELD));

		// Id oficina de registro
		OficinaVO oficinaRegistro = new OficinaVO();
		oficinaRegistro.setId(String.valueOf(bookFolder
				.getAttributeValue(AxSf.FLD5_FIELD)));
		registro.setOficinaRegistro(oficinaRegistro);

		// Estado
		String estado = bookFolder.getAttributeValueAsString(AxSf.FLD6_FIELD);
		if (StringUtils.isNotEmpty(estado)) {
			registro.setEstado(EstadoRegistroEnum.getEnum((new Integer (estado)).intValue()));
		}


		// Id Unidad adminitrativa origen
		UnidadAdministrativaVO unidadAdministrativaOrigen = new UnidadAdministrativaVO();
		if(bookFolder.getAttributeValue(AxSf.FLD7_FIELD)!=null){
			unidadAdministrativaOrigen.setId(String.valueOf(bookFolder
					.getAttributeValue(AxSf.FLD7_FIELD)));

			//si queremos completo mapeo se va a buscar la unidad administrativa a la base de datos
			if (completeMap){
				unidadAdministrativaOrigen = getUnidadAdministrativaManager()
						.findUnidadById(
								getUsuario().getConfiguracionUsuario().getLocale(),
								Integer.valueOf(unidadAdministrativaOrigen.getId()));
			}
		}
		registro.setUnidadAdministrativaOrigen(unidadAdministrativaOrigen);

		// Id Unidad administrativa destino
		UnidadAdministrativaVO unidadAdministrativaDestino = new UnidadAdministrativaVO();

		if(bookFolder.getAttributeValue(AxSf.FLD8_FIELD)!=null){
			unidadAdministrativaDestino.setId(String.valueOf(bookFolder
					.getAttributeValue(AxSf.FLD8_FIELD)));

			//si queremos completo mapeo se va a buscar la unidad administrativa a la base de datos
			if (completeMap){
				unidadAdministrativaDestino = getUnidadAdministrativaManager()
						.findUnidadById(
								getUsuario().getConfiguracionUsuario().getLocale(),
								Integer.valueOf(unidadAdministrativaDestino.getId()));
			}
		}
		registro.setUnidadAdministrativaDestino(unidadAdministrativaDestino);

		if (!StringUtils.isEmpty(getIdLibro())) {
			registro.setIdLibro(getIdLibro());
		}

		if (!StringUtils.isEmpty(getIdRegistro())) {
			registro.setIdRegistro(getIdRegistro());
		}


		//Tipo Asunto
		TipoAsuntoVO tipoAsunto = null;
		String idAsunto = null;
		if (bookFolder instanceof AxSfIn) {
			idAsunto = bookFolder.getAttributeValueAsString(AxSf.FLD16_FIELD);
		} else {
			idAsunto = bookFolder.getAttributeValueAsString(AxSf.FLD12_FIELD);
		}

		if (StringUtils.isNotBlank(idAsunto)) {
			tipoAsunto= new TipoAsuntoVO();
			tipoAsunto.setId(idAsunto);
			if (completeMap){
				tipoAsunto = getTipoAsuntoManager().getTipoAsuntoById(
						usuario,idAsunto);
			}
		}

		registro.setTipoAsunto(tipoAsunto);
		//

		return registro;
	}
	

	/**
	 * Metodo que a partir del objeto {@link AxSf} compone un
	 * {@link BaseRegistroVO}
	 *
	 * @param bookFolder
	 * @return {@link BaseRegistroVO}
	 */
	public BaseRegistroVO map(AxSf bookFolder) {
		boolean completeMap=true;
		BaseRegistroVO registro = map(bookFolder,completeMap);
		return registro;
	}
	
	/**
	 * Metodo que a partir del objeto {@link AxSf} compone un
	 * {@link BaseRegistroVO}
	 *
	 * @param bookFolder
	 * @return {@link BaseRegistroVO}
	 */
	public BaseRegistroVO mapBasic(AxSf bookFolder) {
		boolean completeMap=false;
		BaseRegistroVO registro = map(bookFolder,completeMap);
		return registro;
	}

	/**
	 * Metodo que a partir de los objetos que le llegan devuelve un objeto
	 * {@link BaseRegistroVO} completo
	 *
	 * @param bookFolder
	 * @param oficinaRegistro
	 * @param usuarioRegistro
	 * @param estado
	 * @param unidadAdministrativaOrigen
	 * @param unidadAdministrativaDestino
	 * @param tipoAsunto
	 * @param transporte
	 * @param terceros
	 * @param interesadoPrincipal
	 * @param documentos
	 * @param registrosRelacionados
	 * @param camposAdicionales
	 * @param registerToPopulate
	 * @return {@link BaseRegistroVO}
	 */
	public BaseRegistroVO map(AxSf bookFolder, OficinaVO oficinaRegistro,
			UsuarioVO usuarioRegistro, EstadoRegistroEnum estado,
			UnidadAdministrativaVO unidadAdministrativaOrigen,
			UnidadAdministrativaVO unidadAdministrativaDestino,
			TipoAsuntoVO tipoAsunto, TransporteVO transporte, List terceros,
			InteresadoVO interesadoPrincipal, List documentos,
			List registrosRelacionados, List camposAdicionales,
			BaseRegistroVO registerToPopulate) {

		BaseRegistroVO registro = null;
		if (registerToPopulate instanceof RegistroEntradaVO) {
			registro = new RegistroEntradaVO();
		} else if (registerToPopulate instanceof RegistroSalidaVO) {
			registro = new RegistroSalidaVO();
		}

		// Identificador del registro
		if (!StringUtils.isEmpty(getIdLibro())) {
			registro.setIdLibro(getIdLibro());
		}

		if (!StringUtils.isEmpty(getIdRegistro())) {
			registro.setIdRegistro(getIdRegistro());
		}

		// Numero de registro
		registro.setNumeroRegistro(bookFolder
				.getAttributeValueAsString(AxSf.FLD1_FIELD));

		// Oficina
		registro.setOficinaRegistro(oficinaRegistro);

		// Usuario del registro
		registro.setUsuarioRegistro(usuarioRegistro);

		// Fecha de registro
		registro.setFechaRegistro((Date) bookFolder
				.getAttributeValue(AxSf.FLD2_FIELD));

		// Fecha de alta FLD4
		registro.setFechaAlta((Date) bookFolder
				.getAttributeValue(AxSf.FLD4_FIELD));

		// Estado del Registro
		registro.setEstado(estado);

		// Unidad Origen
		registro.setUnidadAdministrativaOrigen(unidadAdministrativaOrigen);

		// Unidad Destino
		registro.setUnidadAdministrativaDestino(unidadAdministrativaDestino);

		// comprobamos de que tipo es el libro si entrada o salida para obtener
		// el resumen del registro y el comentario
		String resumen = null;

		if (bookFolder instanceof AxSfIn) {
			resumen = bookFolder.getAttributeValueAsString("fld17");
		} else {
			resumen = bookFolder.getAttributeValueAsString(AxSf.FLD13_FIELD);
		}

		// Resumen del registro
		registro.setResumen(resumen);

		// Tipo Asunto
		registro.setTipoAsunto(tipoAsunto);

		// Tipo Transporte
		registro.setTransporte(transporte);

		// Interesados
		registro.setInteresados(terceros);

		// Interesado principal
		registro.setInteresadoPrincipal(interesadoPrincipal);

		// Documentos
		registro.setDocumentos(documentos);

		// Registros Relacionados
		registro.setRegistrosRelacionados(registrosRelacionados);

		// Comentario
		registro.setComentario(registerToPopulate.getComentario());

		// Campos adicionales
		registro.setCamposAdicionales(camposAdicionales);

		return registro;
	}

	public String getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}

	public String getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	public UnidadAdministrativaManager getUnidadAdministrativaManager() {
		return unidadAdministrativaManager;
	}

	public void setUnidadAdministrativaManager(
			UnidadAdministrativaManager unidadAdministrativaManager) {
		this.unidadAdministrativaManager = unidadAdministrativaManager;
	}

	public TipoAsuntoManager getTipoAsuntoManager() {
		return tipoAsuntoManager;
	}

	public void setTipoAsuntoManager(TipoAsuntoManager tipoAsuntoManager) {
		this.tipoAsuntoManager = tipoAsuntoManager;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	// Members
	protected UsuarioVO usuario;

	protected String idLibro;

	protected String idRegistro;

	protected UnidadAdministrativaManager unidadAdministrativaManager;

	protected TipoAsuntoManager tipoAsuntoManager;
}
