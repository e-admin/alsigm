package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoTransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.TransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamOutputRegisterEx;

/**
 * Instancia de <code>Mapper</code> que transforma un objeto de tipo
 * <code>WSParamOutputRegisterEx</code> en uno de tipo
 * <code>RegistroSalidaVO</code>.
 *
 * @see WSParamOutputRegisterEx
 * @see RegistroSalidaVO
 *
 * @author IECISA
 *
 */
public class WSParamOutputRegisterExToRegistroSalidaVOMapper implements Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(WSParamOutputRegisterEx.class, obj);

		WSParamOutputRegisterEx register = (WSParamOutputRegisterEx) obj;

		RegistroSalidaVO result = new RegistroSalidaVO();

		// Campos adicionales
		if (null != register.getAddFields()) {
			List<CampoAdicionalRegistroVO> camposAdicionales = (List<CampoAdicionalRegistroVO>) new ArrayOfWSAddFieldToListOfCampoAdicionalRegistroVOMapper()
					.map(register.getAddFields());

			List<CampoAdicionalRegistroVO> camposAdicionalesRegistroSinCampoComentario = new ArrayList<CampoAdicionalRegistroVO>();
			// recorremos los campos adicionales
			for (Iterator<CampoAdicionalRegistroVO> it = camposAdicionales
					.iterator(); it.hasNext();) {
				CampoAdicionalRegistroVO campoAdicional = it.next();
				/*
				if(Integer.parseInt(campoAdicional.getName()) == ConstantKeys.ID_FLD_SREG_RESUMEN){
				// si el campo es el 13 corresponde al RESUMEN
					result.setResumen(campoAdicional.getValue());
				}
				*/
					if (Integer.parseInt(campoAdicional.getName()) == ConstantKeys.ID_FLD_SREG_COMENTARIOS) {
						// si el campo es el 14 corresponde al COMENTARIO
						result.setComentario(campoAdicional.getValue());
					} else {
						// sino se considera un campo adicional
						camposAdicionalesRegistroSinCampoComentario.add(campoAdicional);
					}
				}

			result.setCamposAdicionales(camposAdicionalesRegistroSinCampoComentario);
		}

		// Unidad administrativa destino
		UnidadAdministrativaVO unidadAdministrativaDestino = new UnidadAdministrativaVO();
		unidadAdministrativaDestino.setCodigoUnidad(register.getDestination());
		result.setUnidadAdministrativaDestino(unidadAdministrativaDestino);

		// Unidad administrativa origen
		UnidadAdministrativaVO unidadAdministrativaOrigen = new UnidadAdministrativaVO();
		unidadAdministrativaOrigen.setCodigoUnidad(register.getSender());
		result.setUnidadAdministrativaOrigen(unidadAdministrativaOrigen);

		// Documentos
		if (null != register.getDocuments()) {
			result.setDocumentos((List<DocumentoRegistroVO>) new ArrayOfWSParamDocumentToListOfDocumentoRegistroVOMapper()
					.map(register.getDocuments()));
		}

		// Tipo de asunto
		TipoAsuntoVO tipoAsunto = new TipoAsuntoVO();
		tipoAsunto.setCodigo(register.getMatterType());
		//tipoAsunto.setDescripcion(register.getMatter());
		result.setTipoAsunto(tipoAsunto);

		result.setResumen(register.getMatter());

		// Interesados
		if (null != register.getPersons()) {
			result.setInteresados((List<InteresadoVO>) new ArrayOfWSParamPersonToListOfInteresadoVOMapper()
					.map(register.getPersons()));
		}

		// Transporte
		TransporteVO transporte = new TransporteVO();
		TipoTransporteVO tipoTransporte = new TipoTransporteVO();
		tipoTransporte.setId(register.getTransportType());
		transporte.setTipoTransporte(tipoTransporte);
		transporte.setNumeroTransporte(register.getTransportNumber());
		result.setTransporte(transporte);

		return result;
	}
}
