package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.HibernateException;

import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.VFldVldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.ValidationFormat;

import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ConfiguracionLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroFieldVO;
import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.EstadoLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoLibroEnum;

/**
 * @author Iecisa
 * 
 *         Clase auxiliar adapter a los VOs del api
 * 
 */
public class LibroAdapter {
	
	

	/**
	 * Metodo adapta Vos del sistema legado a {@link EsquemaLibroVO}
	 * 
	 * @param fieldFormat
	 *            formato de los campos del libro
	 * @param camposValidados
	 *            lista de identificadores de campos validados
	 * @return {@link EsquemaLibroVO}
	 */
	public EsquemaLibroVO esquemaLibroAdapter(FieldFormat fieldFormat,
			List camposValidados) {

		EsquemaLibroVO result = new EsquemaLibroVO();
		EsquemaLibroFieldVO campo = null;
		// obtenemos la definicion de los campos
		Collection definicionesCampos = fieldFormat.getFlddefs().values();

		for (Iterator it = definicionesCampos.iterator(); it.hasNext();) {
			FFldDef definicionCampo = (FFldDef) it.next();

			// parseamos el objeto ffldDef a formato EsquemaFieldVO
			campo = fromFflDefToEsquemaLibroFieldVO(definicionCampo,
					camposValidados);

			result.getCamposLibro().add(campo);
		}

		return result;
	}

	/**
	 * Metodo que parsea un objeto {@link FFldDef} a un objeto
	 * {@link EsquemaLibroFieldVO}
	 * 
	 * @param definicionCampo
	 *            datos del campo a parsear
	 * @param camposValidados
	 *            listado de los campos que son validados
	 * @return {@link EsquemaLibroFieldVO}
	 */
	protected EsquemaLibroFieldVO fromFflDefToEsquemaLibroFieldVO(
			FFldDef definicionCampo, List camposValidados) {
		EsquemaLibroFieldVO result = new EsquemaLibroFieldVO();

		result.setId(Integer.toString(definicionCampo.getId()));
		result.setLabel(definicionCampo.getName());
		result.setName(definicionCampo.getColname());
		result.setLength(definicionCampo.getLen());
		result.setTipo(Integer.toString(definicionCampo.getType()));

		boolean campoValidado = false;
		// comprobamos si el campo es uno de los validados
		if (camposValidados.contains(new Integer(definicionCampo.getId()))) {
			campoValidado = true;
		}
		result.setHasValidation(campoValidado);

		return result;
	}

	/**
	 * Adapta el listado de objetos a objetos de tipo BaseLibroVO
	 * 
	 * @param listado
	 *            de objetos de tipo {@link ScrRegStateByLanguage} to
	 * @return lista de objetos de tipo {@link BaseLibroVO}
	 */
	public List fromScrRegStatesToBaseLibrosVO(List listado) {
		List result = new ArrayList();
		BaseLibroVO libro = null;
		for (Iterator it = listado.iterator(); it.hasNext();) {
			ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
					.next();

			switch (scrRegStateByLanguage.getType()) {
			case 1:
				libro = new LibroEntradaVO();
				break;
			case 2:
				libro = new LibroSalidaVO();
				break;
			}

			// generamos un LibroEntradaVO con id y nombre
			libro.setId(scrRegStateByLanguage.getIdocarchhdrId().toString());
			libro.setName(scrRegStateByLanguage.getIdocarchhdrName());

			result.add(libro);
		}
		return result;
	}
	
	/**
	 * Adapta el listado de objetos a objetos de tipo BaseLibroVO
	 * 
	 * @param listado
	 *            de objetos de tipo {@link ScrRegState} to
	 * @return lista de objetos de tipo {@link BaseLibroVO}
	 */
	public List fromScrRegStateToBaseLibrosVO(List listado) {
		List result = new ArrayList();
		BaseLibroVO libro = null;
		for (Iterator it = listado.iterator(); it.hasNext();) {
			ScrRegstate scrRegState = (ScrRegstate) it
					.next();

			switch (scrRegState.getIdocarchhdr().getType()) {
			case 1:
				libro = new LibroEntradaVO();
				break;
			case 2:
				libro = new LibroSalidaVO();
				break;
			}

			// generamos un LibroEntradaVO con id y nombre
			libro.setId(scrRegState.getIdocarchhdr().getId().toString());
			libro.setName(scrRegState.getIdocarchhdr().getName());

			result.add(libro);
		}
		return result;
	}


	/**
	 * Metodo que adapta VOs del sistema legado {@link ScrRegstate},
	 * {@link Idocarchhdr} a una lista de BaseLibroVO
	 * 
	 * @param session
	 * @param scrRegstates
	 *            listado de tipo ScrRegstate
	 * @param librosName
	 *            mapa con id de libro y el nombre del libro
	 * @param esquemasLibros
	 *            mapa de objetos de tipo {@link EsquemaLibroVO}
	 * @return lista de BaseLibroVO
	 * @throws HibernateException
	 */
	protected List librosAdapter(List scrRegstates, Map esquemasLibros)
			throws HibernateException {
		List result = new ArrayList();

		for (Iterator it = scrRegstates.iterator(); it.hasNext();) {
			ScrRegstate scrregstate = (ScrRegstate) it.next();
			Idocarchhdr idoccarchhdr = scrregstate.getIdocarchhdr();

			// obtenemos el esquema del libro
			EsquemaLibroVO esquemaLibro=((EsquemaLibroVO) esquemasLibros.get(idoccarchhdr.getId().toString()));
			BaseLibroVO libro=libroAdapter(scrregstate,esquemaLibro);
			result.add(libro);
		}
		return result;
	}
	
	
	
	
	
	/**
	 * Metodo que a partir de las definiciones del archivador y de las definiciones de los campos validados del archivador 
	 * adapta a un {@link EsquemaLibroVO}
	 * @param idocarchdetFldDef
	 * @param idocarchdetValidDef
	 * @return
	 */
	protected EsquemaLibroVO getEsquemaLibro(Idocarchdet idocarchdetFldDef,Idocarchdet idocarchdetValidDef){
		EsquemaLibroVO result=new EsquemaLibroVO();
		
		FieldFormat fieldFormat = new FieldFormat(idocarchdetFldDef.getDetval());
		
		//Formateamos la informacion recuperada a formato ValidationFormat
		ValidationFormat validationFormat = new ValidationFormat(idocarchdetValidDef.getDetval());
		
		//Generamos un array con el ID de los campos validados
		List camposValidados = new ArrayList();
		for (Iterator it = validationFormat.getFldvlddefs().values().iterator(); it.hasNext();) {
			VFldVldDef vldDef = (VFldVldDef) it.next();
			camposValidados.add(new Integer(vldDef.getFldid()));
		}
		
		result =esquemaLibroAdapter(fieldFormat,camposValidados);
		
		return result;
	}
	
	/**
	 * Metodo que adapta los datos pasados a un {@link BaseLibroVO}
	 * @param scrregstate estado del archivador
	 * @param idocarchdetFldDef definicion de los fld del archivador
	 * @param idocarchdetValidDef definicion de los campos q son validados
	 * @return
	 * @throws HibernateException
	 */
	public BaseLibroVO libroAdapter(ScrRegstate scrregstate, Idocarchdet idocarchdetFldDef,Idocarchdet idocarchdetValidDef){
		return libroAdapter(scrregstate,getEsquemaLibro(idocarchdetFldDef, idocarchdetValidDef));
	}
	
	
	protected BaseLibroVO libroAdapter(ScrRegstate scrregstate, EsquemaLibroVO esquemaLibro){

		BaseLibroVO result= null;
		
		Idocarchhdr idoccarchhdr = scrregstate.getIdocarchhdr();
		
		switch (idoccarchhdr.getType()) {
		case 1:
			result = new LibroEntradaVO();
			break;
		case 2:
			result = new LibroSalidaVO();
			break;
		}
	
		
	
		result.setId(idoccarchhdr.getId().toString());
		result.setIdArchivador(idoccarchhdr.getId().toString());
	
		// Obtenemos el nombre del libro
		result.setName(idoccarchhdr.getName());
	
		// obtenemos el esquema del libro
		result.setEsquemaLibro(esquemaLibro);
	
		// Estado Libro
		EstadoLibroVO estadoLibro = getEstadoLibroAdapter(scrregstate);
		result.setEstadoLibro(estadoLibro);
	
		// Configuracion del Libro
		ConfiguracionLibroVO configuracionLibro = getConfiguracionAdapter(scrregstate);
		result.setConfiguracion(configuracionLibro);
	
		result.setFechaCierre(scrregstate.getClosedate());
		result.setUsuarioCierre(scrregstate.getCloseuser());
	
		result.setComentario(idoccarchhdr.getRemarks());
	
		return result;
	}
	
	

	/**
	 * Metodo adapta Vos del sistema legado a {@link EstadoLibroVO}
	 * 
	 * @param scrregstate
	 *            informacion del estado del libro
	 * @return {@link EstadoLibroVO}
	 */
	protected EstadoLibroVO getEstadoLibroAdapter(ScrRegstate scrregstate) {
		EstadoLibroVO estadoLibroVO = new EstadoLibroVO();
		estadoLibroVO.setId(scrregstate.getId().toString());
		String idEstado = String.valueOf(EstadoLibroEnum.getEnum(
				scrregstate.getState()).getValue());
		estadoLibroVO.setIdEstado(idEstado);
		estadoLibroVO.setComentario(scrregstate.getIdocarchhdr().getRemarks());

		return estadoLibroVO;
	}

	/**
	 * Metodo adapta Vos del sistema legado a {@link ConfiguracionLibroVO}
	 * 
	 * @param scrregstate
	 *            informacion del estado del libro
	 * @return {@link ConfiguracionLibroVO}
	 */
	protected ConfiguracionLibroVO getConfiguracionAdapter(
			ScrRegstate scrregstate) {
		ConfiguracionLibroVO configuracionLibroVO = new ConfiguracionLibroVO();
		configuracionLibroVO.setTipoNumeracion(new Integer(scrregstate
				.getNumerationType()));
		configuracionLibroVO.setTipoAutenticacionImagenes(new Integer(
				scrregstate.getImageAuth()));
		return configuracionLibroVO;
	}

}
