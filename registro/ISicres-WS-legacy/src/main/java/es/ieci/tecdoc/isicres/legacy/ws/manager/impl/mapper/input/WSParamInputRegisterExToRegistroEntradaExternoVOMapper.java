package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroOriginalVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoTransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.TransporteVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.XMLGregorianCalendarHelper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamInputRegisterEx;

/**
 *
 * @author IECISA
 *
 */
public class WSParamInputRegisterExToRegistroEntradaExternoVOMapper implements
		Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(WSParamInputRegisterEx.class, obj);

		WSParamInputRegisterEx wsRegister = (WSParamInputRegisterEx) obj;

		RegistroEntradaExternoVO registro = new RegistroEntradaExternoVO();

		// Unidad administratica origen
		UnidadAdministrativaVO unidadAdministrativaOrigen = new UnidadAdministrativaVO();
		unidadAdministrativaOrigen.setCodigoUnidad(wsRegister.getSender());
		registro.setUnidadAdministrativaOrigen(unidadAdministrativaOrigen);

		// Unidad administrativa destino
		UnidadAdministrativaVO unidadAdministrativaDestino = new UnidadAdministrativaVO();
		unidadAdministrativaDestino
				.setCodigoUnidad(wsRegister.getDestination());
		registro.setUnidadAdministrativaDestino(unidadAdministrativaDestino);

		// Tipo de asunto
		TipoAsuntoVO tipoAsunto = new TipoAsuntoVO();
		tipoAsunto.setCodigo(wsRegister.getMatterType());
		//tipoAsunto.setDescripcion(wsRegister.getMatter());
		registro.setTipoAsunto(tipoAsunto);

		registro.setResumen(wsRegister.getMatter());
		
		if (null != wsRegister.getOriginalDate()) {
			registro.setFechaAlta(XMLGregorianCalendarHelper.toDate(wsRegister
					.getOriginalDate()));
		}

		// Transporte
		TransporteVO transporte = new TransporteVO();
		transporte.setNumeroTransporte(wsRegister.getTransportNumber());
		TipoTransporteVO tipoTransporte = new TipoTransporteVO();
		tipoTransporte.setId(wsRegister.getTransportType());
		transporte.setTipoTransporte(tipoTransporte);
		registro.setTransporte(transporte);

		// Interesados
		if (null != wsRegister.getPersons()) {
			registro.setInteresados((List<InteresadoVO>) new WSParamPersonToListOfInteresadoVOMapper()
					.map(wsRegister.getPersons()));
		}

		// Campos Adicionales
		if (null != wsRegister.getAddFields()) {
			// se obtienen todos los campos adicionales del registro
			List<CampoAdicionalRegistroVO> camposAdicionales = (List<CampoAdicionalRegistroVO>) new ArrayOfWSAddFieldToListOfCampoAdicionalRegistroVOMapper()
					.map(wsRegister.getAddFields());

			List<CampoAdicionalRegistroVO> camposAdicionalesRegistroSinCampoComentario = new ArrayList<CampoAdicionalRegistroVO>();
			// recorremos los campos adicionales
			for (Iterator<CampoAdicionalRegistroVO> it = camposAdicionales
					.iterator(); it.hasNext();) {
				CampoAdicionalRegistroVO campoAdicional = it.next();
				/*
				if(Integer.parseInt(campoAdicional.getName()) == ConstantKeys.ID_FLD_EREG_RESUMEN){
				// si el campo es el 17 corresponde al RESUMEN
					registro.setResumen(campoAdicional.getValue());
				}*/
				
					if(Integer.parseInt(campoAdicional.getName()) == ConstantKeys.ID_FLD_EREG_COMENTARIOS){
					// si el campo es el 18 corresponde al COMENTARIO
						registro.setComentario(campoAdicional.getValue());
					} else {
						// sino se considera un campo adicional
						camposAdicionalesRegistroSinCampoComentario
								.add(campoAdicional);
					}
				}

			registro.setCamposAdicionales(camposAdicionalesRegistroSinCampoComentario);
		}

		// Documentos
		if (null != wsRegister.getDocuments()) {
			registro.setDocumentos((List<DocumentoRegistroVO>) new ArrayOfWSParamDocumentToListOfDocumentoRegistroVOMapper()
					.map(wsRegister.getDocuments()));
		}

		// Registro original
		RegistroOriginalVO registroOriginal = new RegistroOriginalVO();
		registroOriginal.setNumeroRegistroOriginal(wsRegister
				.getOriginalNumber());
		if (null != wsRegister.getOriginalDate()) {
			registroOriginal
					.setFechaRegistroOriginal(XMLGregorianCalendarHelper
							.toDate(wsRegister.getOriginalDate()));
		}

		String tipoRegistroOriginal = null;

		if(wsRegister.getOriginalType()!=null){
			if(wsRegister.getOriginalType()!= 0){
				tipoRegistroOriginal = String.valueOf(wsRegister.getOriginalType());
			}
		}

		registroOriginal.setTipoRegistroOriginal(tipoRegistroOriginal);

		UnidadAdministrativaVO entidadRegistral = new UnidadAdministrativaVO();
		entidadRegistral.setCodigoUnidad(wsRegister.getOriginalEntity());
		registroOriginal.setEntidadRegistral(entidadRegistral);

		registro.setRegistroOriginal(registroOriginal);

		return registro;
	}
}
