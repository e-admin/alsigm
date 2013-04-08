package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroOriginalVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoTransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.TransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.XMLGregorianCalendarHelper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamInputRegisterEx;

public class WSParamInputRegisterExToRegistroEntradaVOMapper implements Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(WSParamInputRegisterEx.class, obj);

		WSParamInputRegisterEx wsParamInputRegisterEx = (WSParamInputRegisterEx) obj;

		RegistroEntradaVO result = new RegistroEntradaVO();

		// Datos básicos

		// Unidad administrativa origen
		UnidadAdministrativaVO unidadAdministrativaOrigen = new UnidadAdministrativaVO();
		unidadAdministrativaOrigen.setCodigoUnidad(wsParamInputRegisterEx
				.getSender());
		result.setUnidadAdministrativaOrigen(unidadAdministrativaOrigen);

		// Unidad administrativa destino
		UnidadAdministrativaVO unidadAdministrativaDestino = new UnidadAdministrativaVO();
		unidadAdministrativaDestino.setCodigoUnidad(wsParamInputRegisterEx
				.getDestination());
		result.setUnidadAdministrativaDestino(unidadAdministrativaDestino);

		// Transporte
		TransporteVO transporte = new TransporteVO();
		transporte.setNumeroTransporte(wsParamInputRegisterEx
				.getTransportNumber());
		TipoTransporteVO tipoTransporte = new TipoTransporteVO();
		tipoTransporte.setId(wsParamInputRegisterEx.getTransportType());
		transporte.setTipoTransporte(tipoTransporte);
		result.setTransporte(transporte);

		// Asunto
		TipoAsuntoVO tipoAsunto = new TipoAsuntoVO();
		tipoAsunto.setCodigo(wsParamInputRegisterEx.getMatterType());
		
		//tipoAsunto.setDescripcion(wsParamInputRegisterEx.getMatter());
		result.setResumen(wsParamInputRegisterEx.getMatter());
		result.setTipoAsunto(tipoAsunto);

		// Terceros
		if (null != wsParamInputRegisterEx.getPersons()) {
			result.setInteresados((List<InteresadoVO>) new ArrayOfWSParamPersonToListOfInteresadoVOMapper()
					.map(wsParamInputRegisterEx.getPersons()));
		}

		// Campos Adicionales
		if (null != wsParamInputRegisterEx.getAddFields()) {
			List<CampoAdicionalRegistroVO> camposAdicionales = (List<CampoAdicionalRegistroVO>) new ArrayOfWSAddFieldToListOfCampoAdicionalRegistroVOMapper()
					.map(wsParamInputRegisterEx.getAddFields());

			List<CampoAdicionalRegistroVO> camposAdicionalesRegistroSinCampoComentario = new ArrayList<CampoAdicionalRegistroVO>();
			// recorremos los campos adicionales
			for (Iterator<CampoAdicionalRegistroVO> it = camposAdicionales
					.iterator(); it.hasNext();) {
				CampoAdicionalRegistroVO campoAdicional = it.next();
				
				/*
				if(Integer.parseInt(campoAdicional.getName()) == ConstantKeys.ID_FLD_EREG_RESUMEN){
				// si el campo es el 17 corresponde al RESUMEN
					result.setResumen(campoAdicional.getValue());
				}
				*/
				
					if (Integer.parseInt(campoAdicional.getName()) == ConstantKeys.ID_FLD_EREG_COMENTARIOS) {
						// si el campo es el 18 corresponde al COMENTARIO
						result.setComentario(campoAdicional.getValue());
					} else {
						// sino se considera un campo adicional
						camposAdicionalesRegistroSinCampoComentario.add(campoAdicional);
					}
				}

			result.setCamposAdicionales(camposAdicionalesRegistroSinCampoComentario);
		}

		// Documentos
		if (null != wsParamInputRegisterEx.getDocuments()) {
			result.setDocumentos((List<DocumentoRegistroVO>) new ArrayOfWSParamDocumentToListOfDocumentoRegistroVOMapper()
					.map(wsParamInputRegisterEx.getDocuments()));
		}

		// Registro original
		RegistroOriginalVO registroOriginal = new RegistroOriginalVO();
		registroOriginal.setNumeroRegistroOriginal(wsParamInputRegisterEx
				.getOriginalNumber());
		if (null != wsParamInputRegisterEx.getOriginalDate()) {
			registroOriginal
					.setFechaRegistroOriginal(XMLGregorianCalendarHelper
							.toDate(wsParamInputRegisterEx.getOriginalDate()));
		}

		String tipoRegistroOriginal = null;
		if(wsParamInputRegisterEx.getOriginalType()!=null){
			if(wsParamInputRegisterEx.getOriginalType()!= 0){
				tipoRegistroOriginal = String.valueOf(wsParamInputRegisterEx.getOriginalType());
			}
		}
		registroOriginal.setTipoRegistroOriginal(tipoRegistroOriginal);

		UnidadAdministrativaVO entidadRegistral = new UnidadAdministrativaVO();
		entidadRegistral.setCodigoUnidad(wsParamInputRegisterEx
				.getOriginalEntity());
		registroOriginal.setEntidadRegistral(entidadRegistral);

		result.setRegistroOriginal(registroOriginal);

		return result;
	}
}
