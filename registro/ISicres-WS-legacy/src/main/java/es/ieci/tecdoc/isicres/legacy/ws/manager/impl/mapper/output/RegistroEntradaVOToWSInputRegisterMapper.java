package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.util.Assert;

import com.ieci.tecdoc.common.isicres.AxSf;

import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.XMLGregorianCalendarHelper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSAddField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSAddField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegister;

/**
 * Instancia de <code>Mapper</code> que transforma objetos de tipo
 * <code>RegistroEntradaVO</code> en objetos de tipo
 * <code>WSInputRegister</code>.
 *
 * @see RegistroEntradaVO
 * @see WSInputRegister
 *
 * @author IECISA
 *
 */
public class RegistroEntradaVOToWSInputRegisterMapper extends WSInputRegister
		implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(RegistroEntradaVO.class, obj);

		RegistroEntradaVO registro = (RegistroEntradaVO) obj;

		WSInputRegister result = new WSInputRegister();

		result.setAddFields(new ArrayOfWSAddField());
		/*
		 * El comentario es el campo: 
		 * 
		 * Fld 18 para Entrada Fld 14 para Salida
		 */

			WSAddField addFieldComentario = new WSAddField();
			addFieldComentario.setFieldId(AxSf.FLD18_FIELD_ID);
			addFieldComentario.setValue(registro.getComentario());
			result.getAddFields().getWSAddField().add(addFieldComentario);

		if (null != registro.getCamposAdicionales()) {
			ArrayOfWSAddField fields = (ArrayOfWSAddField) new ListOfCampoAdicionalRegistroVOToArrayOfWSAddFieldMapper()
					.map(registro.getCamposAdicionales());
			result.getAddFields().getWSAddField()
					.addAll(fields.getWSAddField());

		}
		
		result.setBookId(Integer.valueOf(registro.getIdLibro()).intValue());
		if (null != registro.getFechaRegistro()) {
			result.setDate(XMLGregorianCalendarHelper
					.toXMLGregorianCalendar(registro.getFechaRegistro()));
		}
		result.setDestination(registro.getUnidadAdministrativaDestino()
				.getCodigoUnidad());
		result.setDestinationName(registro.getUnidadAdministrativaDestino()
				.getName());

		if (null != registro.getDocumentos()) {
			result.setDocuments((ArrayOfWSDocument) new ListOfDocumentoRegistroVOToArrayOfWSDocumentMapper()
					.map(registro.getDocumentos()));
		}
		// se parsea como Long debido al formato del numero de registro
		result.setFolderId(Long.valueOf(registro.getIdRegistro()).intValue());
		
		result.setMatter(registro.getResumen());
		
		result.setMatterType(registro.getTipoAsunto().getCodigo());
		result.setMatterTypeName(registro.getTipoAsunto().getDescripcion());
		result.setNumber(registro.getNumeroRegistro());
		result.setOffice(registro.getOficinaRegistro().getCodigoOficina());
		result.setOfficeName(registro.getOficinaRegistro().getName());

		// Atributos del registro original
		if (null != registro.getRegistroOriginal().getFechaRegistroOriginal()) {
			result.setOriginalDate(XMLGregorianCalendarHelper
					.toXMLGregorianCalendar(registro.getRegistroOriginal()
							.getFechaRegistroOriginal()));
		}
		result.setOriginalEntity(registro.getRegistroOriginal()
				.getEntidadRegistral().getCodigoUnidad());
		result.setOriginalEntityName(registro.getRegistroOriginal()
				.getEntidadRegistral().getName());
		result.setOriginalNumber(registro.getRegistroOriginal()
				.getNumeroRegistroOriginal());
		if (null != registro.getRegistroOriginal().getTipoRegistroOriginal()) {
			result.setOriginalType(Integer.valueOf(
					registro.getRegistroOriginal().getTipoRegistroOriginal())
					.intValue());
		}

		if (null != registro.getInteresados()) {
			result.setPersons((ArrayOfWSPerson) new ListOfInteresadoVOToArrayOfWSPersonMapper()
					.map(registro.getInteresados()));
		}

		result.setSender(registro.getUnidadAdministrativaOrigen()
				.getCodigoUnidad());
		result.setSenderName(registro.getUnidadAdministrativaOrigen().getName());
		result.setState(registro.getEstado().getValue());

		if (null != registro.getFechaAlta()) {
			result.setSystemDate(XMLGregorianCalendarHelper
					.toXMLGregorianCalendar(registro.getFechaAlta()));
		}
		result.setTransportNumber(registro.getTransporte()
				.getNumeroTransporte());
		result.setTransportType(registro.getTransporte().getTipoTransporte()
				.getDescripcion());
		result.setUserName(registro.getUsuarioRegistro().getLoginName());

		return result;
	}

}
