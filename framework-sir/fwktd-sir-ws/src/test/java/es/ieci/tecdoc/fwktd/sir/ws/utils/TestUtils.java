package es.ieci.tecdoc.fwktd.sir.ws.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.sir.api.manager.impl.ws.WSSIRHelper;
import es.ieci.tecdoc.fwktd.sir.api.schema.De_Mensaje;
import es.ieci.tecdoc.fwktd.sir.api.schema.types.Indicador_PruebaType;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoBAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoRechazoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoFormDTO;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

public class TestUtils {

    public static AsientoRegistralFormVO createDefaultAsientoRegistralFormVO() {

        AsientoRegistralFormVO asiento = new AsientoRegistralFormVO();

        asiento.setCodigoEntidadRegistral("ER0000000000000000001");
        asiento.setCodigoEntidadRegistralOrigen("ER0000000000000000001");
        asiento.setDescripcionEntidadRegistralOrigen("Entidad Registral ER0000000000000000001");
        asiento.setCodigoEntidadRegistralInicio(asiento.getCodigoEntidadRegistralOrigen());
        asiento.setDescripcionEntidadRegistralInicio(asiento.getDescripcionEntidadRegistralOrigen());
        asiento.setCodigoUnidadTramitacionOrigen("UT0000000000000000001");
        asiento.setDescripcionUnidadTramitacionOrigen("Unidad de Tramitación UT0000000000000000001");
        asiento.setCodigoEntidadRegistralDestino("ER0000000000000000002");
        asiento.setDescripcionEntidadRegistralDestino("Entidad Registral ER0000000000000000002");
        asiento.setCodigoUnidadTramitacionDestino("UT0000000000000000002");
        asiento.setDescripcionUnidadTramitacionDestino("Unidad de Tramitación UT0000000000000000002");
        asiento.setNumeroRegistro("201000100000001");
        asiento.setFechaRegistro(new Date());
        asiento.setTimestampRegistro("***timestamp***".getBytes());
        asiento.setResumen("Resumen");
        asiento.setCodigoAsunto("ASUNTO0000000001");
        asiento.setReferenciaExterna("REF0000000000001");
        asiento.setNumeroExpediente("EXP2010/00001");
        asiento.setTipoTransporte(TipoTransporteEnum.FAX);
        asiento.setNumeroTransporte("99999");
        asiento.setNombreUsuario("usuario");
        asiento.setContactoUsuario("usuario@contacto.es");
        asiento.setTipoRegistro(TipoRegistroEnum.ENTRADA);
        asiento.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA);
        asiento.setObservacionesApunte("observaciones");
        asiento.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA);
        asiento.setExpone("expone");
        asiento.setSolicita("solicita");

        InteresadoFormVO interesado = new InteresadoFormVO();

        interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF);
        interesado.setDocumentoIdentificacionInteresado("A28855260");
        interesado.setRazonSocialInteresado("razonSocialInteresado");
        interesado.setNombreInteresado("");
        interesado.setPrimerApellidoInteresado("");
        interesado.setSegundoApellidoInteresado("");
        interesado.setCodigoPaisInteresado("0001");
        interesado.setCodigoProvinciaInteresado("05");
        interesado.setCodigoMunicipioInteresado("01544");
        interesado.setDireccionInteresado("direccionInteresado");
        interesado.setCodigoPostalInteresado("33004");
        interesado.setCorreoElectronicoInteresado("correoElectronico@interesado.es");
        interesado.setTelefonoInteresado("999999999");
        interesado.setDireccionElectronicaHabilitadaInteresado("deu");
        interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);
        interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF);
        interesado.setDocumentoIdentificacionRepresentante("00000000T");
        interesado.setRazonSocialRepresentante("");
        interesado.setNombreRepresentante("nombreRepresentante");
        interesado.setPrimerApellidoRepresentante("primerApellidoRepresentante");
        interesado.setSegundoApellidoRepresentante("segundoApellidoRepresentante");
        interesado.setCodigoPaisRepresentante("0001");
        interesado.setCodigoProvinciaRepresentante("05");
        interesado.setCodigoMunicipioRepresentante("01544");
        interesado.setDireccionRepresentante("direccionRepresentante");
        interesado.setCodigoPostalRepresentante("33004");
        interesado.setCorreoElectronicoRepresentante("correoElectronico@representante.es");
        interesado.setTelefonoRepresentante("666666666");
        interesado.setDireccionElectronicaHabilitadaRepresentante("deu_repr");
        interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA);
        interesado.setObservaciones("observaciones");

        asiento.getInteresados().add(interesado);


        AnexoFormVO anexo = new AnexoFormVO();
        anexo.setNombreFichero("fichero1.txt");
        anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA_COMPULSADA);
        anexo.setTipoDocumento(TipoDocumentoEnum.FICHERO_TECNICO_INTERNO);
        anexo.setCertificado("***certificado***".getBytes());
        anexo.setFirma("***firma***".getBytes());
        anexo.setTimestamp("***timestmap***".getBytes());
        anexo.setValidacionOCSPCertificado("***ocsp***".getBytes());
        anexo.setTipoMIME("text/plain");
        anexo.setObservaciones("Observaciones al fichero 1");
        anexo.setContenido("contenido fichero 1".getBytes());
        anexo.setCodigoFichero("COD_001");
        anexo.setCodigoFicheroFirmado("COD_001"); //Autofirmado

        asiento.getAnexos().add(anexo);

        anexo = new AnexoFormVO();
        anexo.setNombreFichero("fichero2.txt");
        anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA_ORIGINAL);
        anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO);
        anexo.setCertificado(null);
        anexo.setFirma(null);
        anexo.setTimestamp("***timestmap***".getBytes());
        anexo.setValidacionOCSPCertificado(null);
        anexo.setTipoMIME("text/plain");
        anexo.setObservaciones("Observaciones al fichero 2");
        anexo.setContenido("contenido fichero 2".getBytes());
        anexo.setCodigoFichero("COD_002");

        asiento.getAnexos().add(anexo);

        anexo = new AnexoFormVO();
        anexo.setNombreFichero("fichero2_firma.txt");
        anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA_ORIGINAL);
        anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO);
        anexo.setCertificado("***certificado***".getBytes());
        anexo.setFirma(null);
        anexo.setTimestamp("***timestmap***".getBytes());
        anexo.setValidacionOCSPCertificado("***ocsp***".getBytes());
        anexo.setTipoMIME("text/plain");
        anexo.setCodigoFicheroFirmado("COD_002");
        anexo.setObservaciones("Firma del fichero 2");
        anexo.setContenido("firma fichero 2".getBytes());

        asiento.getAnexos().add(anexo);

        return asiento;
    }

    public static String createXMLFicheroIntercambio(String identificadorIntercambio, TipoAnotacionEnum tipoAnotacion, boolean attached) {
    	return new StringBuffer()
    		.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    		.append("<Fichero_Intercambio_SICRES_3>")
    		.append("<De_Origen_o_Remitente>")
		        .append("<Codigo_Entidad_Registral_Origen>ER0000000000000000001</Codigo_Entidad_Registral_Origen>")
		        .append("<Decodificacion_Entidad_Registral_Origen><![CDATA[Entidad Registral ER0000000000000000001]]></Decodificacion_Entidad_Registral_Origen>")
		        .append("<Numero_Registro_Entrada>201000100000001</Numero_Registro_Entrada>")
		        .append("<Fecha_Hora_Entrada>20110308131106</Fecha_Hora_Entrada>")
		        .append("<Timestamp_Entrada>KioqdGltZXN0YW1wKioq</Timestamp_Entrada>")
		        .append("<Codigo_Unidad_Tramitacion_Origen>UT0000000000000000001</Codigo_Unidad_Tramitacion_Origen>")
		        .append("<Decodificacion_Unidad_Tramitacion_Origen><![CDATA[Unidad de Tramitación UT0000000000000000001]]></Decodificacion_Unidad_Tramitacion_Origen>")
	        .append("</De_Origen_o_Remitente>")
	        .append("<De_Destino>")
		        .append("<Codigo_Entidad_Registral_Destino>ER0000000000000000002</Codigo_Entidad_Registral_Destino>")
		        .append("<Decodificacion_Entidad_Registral_Destino>Entidad Registral ER0000000000000000002</Decodificacion_Entidad_Registral_Destino>")
		        .append("<Codigo_Unidad_Tramitacion_Destino>UT0000000000000000002</Codigo_Unidad_Tramitacion_Destino>")
		        .append("<Decodificacion_Unidad_Tramitacion_Destino>Unidad de Tramitación UT0000000000000000002</Decodificacion_Unidad_Tramitacion_Destino>")
	        .append("</De_Destino>")
	        .append("<De_Interesado>")
		        .append("<Tipo_Documento_Identificacion_Interesado>C</Tipo_Documento_Identificacion_Interesado>")
		        .append("<Documento_Identificacion_Interesado>A28855260</Documento_Identificacion_Interesado>")
		        .append("<Razon_Social_Interesado>razonSocialInteresado</Razon_Social_Interesado>")
		        .append("<Tipo_Documento_Identificacion_Representante>N</Tipo_Documento_Identificacion_Representante>")
		        .append("<Documento_Identificacion_Representante>00000000T</Documento_Identificacion_Representante>")
		        .append("<Nombre_Representante>nombreRepresentante</Nombre_Representante>")
		        .append("<Primer_Apellido_Representante>primerApellidoRepresentante</Primer_Apellido_Representante>")
		        .append("<Segundo_Apellido_Representante>segundoApellidoRepresentante</Segundo_Apellido_Representante>")
		        .append("<Pais_Interesado>0001</Pais_Interesado>")
		        .append("<Provincia_Interesado>05</Provincia_Interesado>")
		        .append("<Municipio_Interesado>01544</Municipio_Interesado>")
		        .append("<Direccion_Interesado>direccionInteresado</Direccion_Interesado>")
		        .append("<Codigo_Postal_Interesado>33004</Codigo_Postal_Interesado>")
		        .append("<Correo_Electronico_Interesado>correoElectronico@interesado.es</Correo_Electronico_Interesado>")
		        .append("<Telefono_Contacto_Interesado>999999999</Telefono_Contacto_Interesado>")
		        .append("<Direccion_Electronica_Habilitada_Interesado>deu</Direccion_Electronica_Habilitada_Interesado>")
		        .append("<Canal_Preferente_Comunicacion_Interesado>01</Canal_Preferente_Comunicacion_Interesado>")
		        .append("<Pais_Representante>0001</Pais_Representante>")
		        .append("<Provincia_Representante>05</Provincia_Representante>")
		        .append("<Municipio_Representante>01544</Municipio_Representante>")
		        .append("<Direccion_Representante>direccionRepresentante</Direccion_Representante>")
		        .append("<Codigo_Postal_Representante>33004</Codigo_Postal_Representante>")
		        .append("<Correo_Electronico_Representante>correoElectronico@representante.es</Correo_Electronico_Representante>")
		        .append("<Telefono_Contacto_Representante>666666666</Telefono_Contacto_Representante>")
		        .append("<Direccion_Electronica_Habilitada_Representante>deu_repr</Direccion_Electronica_Habilitada_Representante>")
		        .append("<Canal_Preferente_Comunicacion_Representante>02</Canal_Preferente_Comunicacion_Representante>")
		        .append("<Observaciones><![CDATA[observaciones]]>")
		        .append("</Observaciones>")
	        .append("</De_Interesado>")
	        .append("<De_Asunto>")
		        .append("<Resumen><![CDATA[Resumen]]></Resumen>")
		        .append("<Codigo_Asunto_Segun_Destino>ASUNTO0000000001</Codigo_Asunto_Segun_Destino>")
		        .append("<Referencia_Externa><![CDATA[REF0000000000001]]></Referencia_Externa>")
		        .append("<Numero_Expediente><![CDATA[EXP2010/00001]]></Numero_Expediente>")
	        .append("</De_Asunto>")
	        .append("<De_Anexo>")
		        .append("<Nombre_Fichero_Anexado>fichero1.txt</Nombre_Fichero_Anexado>")
		        .append("<Identificador_Fichero>").append(identificadorIntercambio).append("_01_0001.txt</Identificador_Fichero>")
		        .append("<Validez_Documento>02</Validez_Documento>")
		        .append("<Tipo_Documento>03</Tipo_Documento>")
		        .append("<Certificado>KioqY2VydGlmaWNhZG8qKio=</Certificado>")
		        .append("<Firma_Documento>KioqZmlybWEqKio=</Firma_Documento>")
		        .append("<TimeStamp>KioqdGltZXN0bWFwKioq</TimeStamp>")
		        .append("<Validacion_OCSP_Certificado>Kioqb2NzcCoqKg==</Validacion_OCSP_Certificado>")
		        .append("<Tipo_MIME>text/plain</Tipo_MIME>")
		        .append(attached ? "<Anexo>Y29udGVuaWRvIGZpY2hlcm8gMQ==</Anexo>" : "")
		        .append("<Hash>oLO7sg9G8wj9vvD3adxdLnPXrcY=</Hash>")
		        .append("<Identificador_Documento_Firmado>").append(identificadorIntercambio).append("_01_0001</Identificador_Documento_Firmado>")
		        .append("<Observaciones><![CDATA[Observaciones al fichero 1]]>")
		        .append("</Observaciones>")
	        .append("</De_Anexo>")
	        .append("<De_Anexo>")
		        .append("<Nombre_Fichero_Anexado>fichero2.txt</Nombre_Fichero_Anexado>")
		        .append("<Identificador_Fichero>").append(identificadorIntercambio).append("_01_0002.txt</Identificador_Fichero>")
		        .append("<Validez_Documento>03</Validez_Documento>")
		        .append("<Tipo_Documento>02</Tipo_Documento>")
		        .append("<TimeStamp>KioqdGltZXN0bWFwKioq</TimeStamp>")
		        .append("<Tipo_MIME>text/plain</Tipo_MIME>")
		        .append(attached ? "<Anexo>Y29udGVuaWRvIGZpY2hlcm8gMQ==</Anexo>" : "")
		        .append("<Hash>oLO7sg9G8wj9vvD3adxdLnPXrcY=</Hash>")
		        .append("<Observaciones><![CDATA[Observaciones al fichero 2]]>")
		        .append("</Observaciones>")
	        .append("</De_Anexo>")
	        .append("<De_Anexo>")
		        .append("<Nombre_Fichero_Anexado>fichero2_firma.txt</Nombre_Fichero_Anexado>")
		        .append("<Identificador_Fichero>").append(identificadorIntercambio).append("_01_0003.txt</Identificador_Fichero>")
		        .append("<Validez_Documento>03</Validez_Documento>")
		        .append("<Tipo_Documento>02</Tipo_Documento>")
		        .append("<Certificado>KioqY2VydGlmaWNhZG8qKio=</Certificado>")
		        .append("<TimeStamp>KioqdGltZXN0bWFwKioq</TimeStamp>")
		        .append("<Validacion_OCSP_Certificado>Kioqb2NzcCoqKg==</Validacion_OCSP_Certificado>")
		        .append("<Tipo_MIME>text/plain</Tipo_MIME>")
		        .append(attached ? "<Anexo>Y29udGVuaWRvIGZpY2hlcm8gMQ==</Anexo>" : "")
		        .append("<Hash>oLO7sg9G8wj9vvD3adxdLnPXrcY=</Hash>")
		        .append("<Identificador_Documento_Firmado>").append(identificadorIntercambio).append("_01_0001.txt</Identificador_Documento_Firmado>")
		        .append("<Observaciones><![CDATA[Firma del fichero 2]]>")
		        .append("</Observaciones>")
	        .append("</De_Anexo>")
	        .append("<De_Internos_Control>")
		        .append("<Tipo_Transporte_Entrada>06</Tipo_Transporte_Entrada>")
		        .append("<Numero_Transporte_Entrada>99999</Numero_Transporte_Entrada>")
		        .append("<Nombre_Usuario>usuario</Nombre_Usuario>")
		        .append("<Contacto_Usuario><![CDATA[usuario@contacto.es]]></Contacto_Usuario>")
		        .append("<Identificador_Intercambio>").append(identificadorIntercambio).append("</Identificador_Intercambio>")
		        .append("<Tipo_Anotacion>").append(tipoAnotacion.getValue()).append("</Tipo_Anotacion>")
		        .append("<Descripcion_Tipo_Anotacion><![CDATA[").append(tipoAnotacion.getName()).append("]]></Descripcion_Tipo_Anotacion>")
		        .append("<Tipo_Registro>0</Tipo_Registro>")
		        .append("<Documentacion_Fisica>2</Documentacion_Fisica>")
		        .append("<Observaciones_Apunte><![CDATA[observaciones]]></Observaciones_Apunte>")
		        .append("<Indicador_Prueba>1</Indicador_Prueba>")
		        .append("<Codigo_Entidad_Registral_Inicio>ER0000000000000000001</Codigo_Entidad_Registral_Inicio>")
		        .append("<Decodificacion_Entidad_Registral_Inicio><![CDATA[Entidad Registral ER0000000000000000001]]></Decodificacion_Entidad_Registral_Inicio>")
	        .append("</De_Internos_Control>")
	        .append("<De_Formulario_Generico>")
		        .append("<Expone><![CDATA[expone]]></Expone>")
		        .append("<Solicita><![CDATA[solicita]]></Solicita>")
	        .append("</De_Formulario_Generico>")
	        .append("</Fichero_Intercambio_SICRES_3>")
    		.toString();
    }

    public static String createXMLMensaje(String identificadorIntercambio, TipoMensajeEnum tipoMensaje) {

        StringWriter stringWriter = new StringWriter();

    	De_Mensaje msg = new De_Mensaje();
    	msg.setCodigo_Entidad_Registral_Origen("ER0000000000000000002");
    	msg.setCodigo_Entidad_Registral_Destino("ER0000000000000000001");
    	msg.setIdentificador_Intercambio(identificadorIntercambio);
    	msg.setTipo_Mensaje(tipoMensaje.getValue());
    	msg.setDescripcion_Mensaje(tipoMensaje.getName());
    	msg.setNumero_Registro_Entrada_Destino("201000100000001");
    	msg.setFecha_Hora_Entrada_Destino(WSSIRHelper.formatDate(new Date()));
    	msg.setCodigo_Error(null);
    	msg.setIdentificador_Fichero(new String[0]);
    	msg.setIndicador_Prueba(Indicador_PruebaType.fromValue(IndicadorPruebaEnum.PRUEBA.getValue()));

        try {
			msg.marshal(stringWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	    return stringWriter.toString();
	}

	public static AsientoRegistralFormDTO createAsientoRegistralFormDTO() {

		AsientoRegistralFormDTO asientoForm = new AsientoRegistralFormDTO();

		asientoForm.setCodigoEntidadRegistral("TESTER000000000000001");
		
		asientoForm.setCodigoEntidadRegistralOrigen("TESTER000000000000001");
		asientoForm.setDescripcionEntidadRegistralOrigen("Entidad Registral TESTER000000000000001");
		asientoForm.setCodigoUnidadTramitacionOrigen("UT0000000000000000001");
		asientoForm.setDescripcionUnidadTramitacionOrigen("Unidad de Tramitación UT0000000000000000001");

		asientoForm.setCodigoEntidadRegistralDestino("TESTER000000000000002");
		asientoForm.setDescripcionEntidadRegistralDestino("Entidad Registral TESTER000000000000002");
		asientoForm.setCodigoUnidadTramitacionDestino("UT0000000000000000002");
		asientoForm.setDescripcionUnidadTramitacionDestino("Unidad de Tramitación UT0000000000000000002");

		asientoForm.setCodigoEntidadRegistralInicio(asientoForm.getCodigoEntidadRegistralOrigen());
		asientoForm.setDescripcionEntidadRegistralInicio(asientoForm.getDescripcionEntidadRegistralOrigen());

		asientoForm.setTipoRegistro(TipoRegistroEnum.ENTRADA.getValue());
		asientoForm.setNumeroRegistro("201100100000001");
		asientoForm.setFechaRegistro(DateUtils.toXMLGregorianCalendar(new Date()));
		asientoForm.setTimestampRegistro("***timestamp***".getBytes());

		asientoForm.setNumeroRegistroInicial(asientoForm.getNumeroRegistro());
		asientoForm.setFechaRegistroInicial(asientoForm.getFechaRegistro());
		asientoForm.setTimestampRegistroInicial(asientoForm.getTimestampRegistro());

		asientoForm.setResumen("Resumen");
		asientoForm.setCodigoAsunto("ASUNTO0000000001");
		asientoForm.setReferenciaExterna("REF0000000000001");
		asientoForm.setNumeroExpediente("EXP2011/00001");
		asientoForm.setTipoTransporte(TipoTransporteEnum.SERVICIO_MENSAJEROS.getValue());
		asientoForm.setNumeroTransporte("99999");
		asientoForm.setNombreUsuario("usuario");
		asientoForm.setContactoUsuario("usuario@contacto.es");
		asientoForm.setDocumentacionFisica(DocumentacionFisicaEnum.DOCUMENTACION_FISICA_COMPLEMENTARIA.getValue());
		asientoForm.setObservacionesApunte("observaciones");
		asientoForm.setIndicadorPrueba(IndicadorPruebaEnum.PRUEBA.getValue());
		asientoForm.setExpone("expone");
		asientoForm.setSolicita("solicita");

		asientoForm.getInteresados().add(createInteresadoFormDTO("1"));

		asientoForm.getAnexos().add(createAnexoFormDTO("1"));
		asientoForm.getAnexos().add(createAnexoFormDTO("2"));
		asientoForm.getAnexos().add(createAnexoFormDTO("3"));

		return asientoForm;
	}

	public static AnexoFormDTO createAnexoFormDTO(String id) {

		AnexoFormDTO anexo = new AnexoFormDTO();

		anexo.setCertificado(Base64.decodeBase64("LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlGMnpDQ0JNT2dBd0lCQWdJRVJKbmxrekFOQmdrcWhraUc5dzBCQVFVRkFEQmNNUXN3Q1FZRFZRUUdFd0pGDQpVekVvTUNZR0ExVUVDZ3dmUkVsU1JVTkRTVTlPSUVkRlRrVlNRVXdnUkVVZ1RFRWdVRTlNU1VOSlFURU5NQXNHDQpBMVVFQ3d3RVJFNUpSVEVVTUJJR0ExVUVBd3dMUVVNZ1JFNUpSU0F3TURJd0hoY05NRGt3T1RJNU1Ea3lNakEzDQpXaGNOTVRJd016STVNRGsxTWpBMVdqQjVNUXN3Q1FZRFZRUUdFd0pGVXpFU01CQUdBMVVFQlJNSk1EazBNamN4DQpPVE5GTVJJd0VBWURWUVFFREFsR1JWSk9RVTVFUlZveERqQU1CZ05WQkNvTUJVUkJWa2xFTVRJd01BWURWUVFEDQpEQ2xHUlZKT1FVNUVSVm9nUVV4V1FWSkZXaXdnUkVGV1NVUWdLRUZWVkVWT1ZFbERRVU5KdzVOT0tUQ0NBU0l3DQpEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBTDV6Z2J6ZlRDWmh1V3dia3RobFJsSlg3eFlNDQpvanBLNFJFVkhOMHpuV01QQTdxNzhqaGoyYlRsU1NaQXdGSFJqc0RPUUdDTlJmQzc4WWRhQStNTm92Rk1TK1crDQpKRW41dGxjRUZ0OTdUb0RZenhQbThTVG81dEFJODNndTZuNXRUY2pWbyt1Q2dTRFFBcnUxanVpRFpVMGNRdm9ZDQpUMmF6WDlIbXVPMkcwUHcvNmVDMEdkSDFHZEtSZFJoelpJVDFCaEFMNytQU2swN1IvWGhXb0FDUFBYMGYzbHcxDQpHVmNCZTZOOGlVWUU1S3NKOHdUZUpxZjBHNEFndTVGSW8zV1RKZjFhWE1YZzJTRC8zTjhlZ2JnWXB6a1RVZU4wDQoxRmdmMjRVblo3M1hVL1BPRHZseExNQWlnT29ha2gyN0QxUkY3ZFkxanZSZ1A3STNsQkxMa3k3WUxFVUNBd0VBDQpBYU9DQW9Zd2dnS0NNQTRHQTFVZER3RUIvd1FFQXdJSGdEQW9CZ05WSFFrRUlUQWZNQjBHQ0NzR0FRVUZCd2tCDQpNUkVZRHpFNU56Y3dNVEl6TVRJd01EQXdXakJDQmdoZ2hWUUJBZ0lFQVFRMk1EUXdNZ0lCQWpBTEJnbGdoa2dCDQpaUU1FQWdFRUlDSUI1cm85UjdWSm1XeXF5S1Q5bFFlMmlNMDA3VmVVYzFaWE1GaE1LZWswTUlId0JnZ3JCZ0VGDQpCUWNCQWdTQjR6Q0I0REF5QWdFQk1Bc0dDV0NHU0FGbEF3UUNBUVFnR0RrNHkzQTY2U1oxelJpbi90NjNzMjVnDQowZkVVOWI4UE9hT2dYdFRPRE5Fd01nSUJBREFMQmdsZ2hrZ0JaUU1FQWdFRUlJbDJqSVY4UnFRYmdjS0JaNW54DQpPSzhETVJqNWN4d0NEOGo1dk9DZnR5aUZNRG9HQ1dDRlZBRUNBZ1FDQVRBTEJnbGdoa2dCWlFNRUFnRUVJRUVyDQpVazFJcFBTeDM3RE5KRUNSRFRuQmw4VnFKUGJKS0liNjFUZVVjUXU3TURvR0NXQ0ZWQUVDQWdRQ0JqQUxCZ2xnDQpoa2dCWlFNRUFnRUVJRHVSbXc4RkVOdDNpNTNPb2tDdmcxSVJKaWI1VS9tY3JNVHFmL2VvZXcvNE1Bd0dBMVVkDQpFd0VCL3dRQ01BQXdJZ1lJS3dZQkJRVUhBUU1FRmpBVU1BZ0dCZ1FBamtZQkFUQUlCZ1lFQUk1R0FRUXdZQVlJDQpLd1lCQlFVSEFRRUVWREJTTUI4R0NDc0dBUVVGQnpBQmhoTm9kSFJ3T2k4dmIyTnpjQzVrYm1sbExtVnpNQzhHDQpDQ3NHQVFVRkJ6QUNoaU5vZEhSd09pOHZkM2QzTG1SdWFXVXVaWE12WTJWeWRITXZRVU5TWVdsNkxtTnlkREE3DQpCZ05WSFNBRU5EQXlNREFHQ0dDRlZBRUNBZ0lFTUNRd0lnWUlLd1lCQlFVSEFnRVdGbWgwZEhBNkx5OTNkM2N1DQpaRzVwWlM1bGN5OWtjR013SHdZRFZSMGpCQmd3Rm9BVU9xYUo3QlhvSkdSeDRDVit5YkZpTVFmcEJxSXdIUVlEDQpWUjBPQkJZRUZDQll5cnN5S2hIbzN5dzBYazJVUHJicWtDdEJNQTBHQ1NxR1NJYjNEUUVCQlFVQUE0SUJBUUJ6DQpBaXYrSS9IVnFzVk00TXpyRlJtTHBQMU9JLzVYMDB1YWhjNUFPbGUwaVkwWm5PcjV6TnpNbExBRFVUcGJSQ0VvDQpnZWV1c2ZPR1dkTHQvdjY1N0pucHBNb083cEs2OVo2c2hVT2R1Q0MvaEdKc0tHL1JBUXoyakNOV05IamRCYUNEDQp2TlFNcGFKSkc3MW8wSWRQY0hPYUlEbmNEQUMxQXQzNHRGZTdRMFlqU2JNTkpCTkFRRzY2eC9nWW1iRVhncWlqDQp2eDVUZktmQ05UbG9ZclBnSk5PSUNjV1pmbmRlemRZblIvMTc2NTB1SE02UndJeld2TjdMU1UzSWVkWFVQUGo1DQpaZEdmZjdWT294cW5MSklQcFNUOFBoOCtNUjExYi9MTTJXU3JseXUybUNhMTFlSUdranl1ZXJoNTRzWWxPK3VJDQpuaHdzRkJxKzBxZk9zRkg2T1JnMA0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQ0K"));
	    anexo.setCodigoFichero("CODIGO_FICHERO_" + id);
	    anexo.setCodigoFicheroFirmado(null);
	    anexo.setContenido("Contenido del anexo".getBytes());
	    anexo.setFirma(Base64.decodeBase64("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"));
	    anexo.setIdentificadorFicheroFirmado(null);
	    anexo.setNombreFichero("NOMBRE_FICHERO_" + id + ".txt");
	    anexo.setObservaciones("OBSERVACIONES " + id);
	    anexo.setTimestamp("***timestamp***".getBytes());
	    anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO.getValue());
	    anexo.setTipoMIME("text/plain");
	    anexo.setValidacionOCSPCertificado(Base64.decodeBase64("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"));
	    anexo.setValidezDocumento(ValidezDocumentoEnum.COPIA.getValue());

		return anexo;
	}
	
	public static AnexoFormDTO createFirmaAnexoFormDTO(String idFicheroFirmado) {

		AnexoFormDTO anexo = new AnexoFormDTO();

		anexo.setCertificado(Base64.decodeBase64("LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlGMnpDQ0JNT2dBd0lCQWdJRVJKbmxrekFOQmdrcWhraUc5dzBCQVFVRkFEQmNNUXN3Q1FZRFZRUUdFd0pGDQpVekVvTUNZR0ExVUVDZ3dmUkVsU1JVTkRTVTlPSUVkRlRrVlNRVXdnUkVVZ1RFRWdVRTlNU1VOSlFURU5NQXNHDQpBMVVFQ3d3RVJFNUpSVEVVTUJJR0ExVUVBd3dMUVVNZ1JFNUpSU0F3TURJd0hoY05NRGt3T1RJNU1Ea3lNakEzDQpXaGNOTVRJd016STVNRGsxTWpBMVdqQjVNUXN3Q1FZRFZRUUdFd0pGVXpFU01CQUdBMVVFQlJNSk1EazBNamN4DQpPVE5GTVJJd0VBWURWUVFFREFsR1JWSk9RVTVFUlZveERqQU1CZ05WQkNvTUJVUkJWa2xFTVRJd01BWURWUVFEDQpEQ2xHUlZKT1FVNUVSVm9nUVV4V1FWSkZXaXdnUkVGV1NVUWdLRUZWVkVWT1ZFbERRVU5KdzVOT0tUQ0NBU0l3DQpEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBTDV6Z2J6ZlRDWmh1V3dia3RobFJsSlg3eFlNDQpvanBLNFJFVkhOMHpuV01QQTdxNzhqaGoyYlRsU1NaQXdGSFJqc0RPUUdDTlJmQzc4WWRhQStNTm92Rk1TK1crDQpKRW41dGxjRUZ0OTdUb0RZenhQbThTVG81dEFJODNndTZuNXRUY2pWbyt1Q2dTRFFBcnUxanVpRFpVMGNRdm9ZDQpUMmF6WDlIbXVPMkcwUHcvNmVDMEdkSDFHZEtSZFJoelpJVDFCaEFMNytQU2swN1IvWGhXb0FDUFBYMGYzbHcxDQpHVmNCZTZOOGlVWUU1S3NKOHdUZUpxZjBHNEFndTVGSW8zV1RKZjFhWE1YZzJTRC8zTjhlZ2JnWXB6a1RVZU4wDQoxRmdmMjRVblo3M1hVL1BPRHZseExNQWlnT29ha2gyN0QxUkY3ZFkxanZSZ1A3STNsQkxMa3k3WUxFVUNBd0VBDQpBYU9DQW9Zd2dnS0NNQTRHQTFVZER3RUIvd1FFQXdJSGdEQW9CZ05WSFFrRUlUQWZNQjBHQ0NzR0FRVUZCd2tCDQpNUkVZRHpFNU56Y3dNVEl6TVRJd01EQXdXakJDQmdoZ2hWUUJBZ0lFQVFRMk1EUXdNZ0lCQWpBTEJnbGdoa2dCDQpaUU1FQWdFRUlDSUI1cm85UjdWSm1XeXF5S1Q5bFFlMmlNMDA3VmVVYzFaWE1GaE1LZWswTUlId0JnZ3JCZ0VGDQpCUWNCQWdTQjR6Q0I0REF5QWdFQk1Bc0dDV0NHU0FGbEF3UUNBUVFnR0RrNHkzQTY2U1oxelJpbi90NjNzMjVnDQowZkVVOWI4UE9hT2dYdFRPRE5Fd01nSUJBREFMQmdsZ2hrZ0JaUU1FQWdFRUlJbDJqSVY4UnFRYmdjS0JaNW54DQpPSzhETVJqNWN4d0NEOGo1dk9DZnR5aUZNRG9HQ1dDRlZBRUNBZ1FDQVRBTEJnbGdoa2dCWlFNRUFnRUVJRUVyDQpVazFJcFBTeDM3RE5KRUNSRFRuQmw4VnFKUGJKS0liNjFUZVVjUXU3TURvR0NXQ0ZWQUVDQWdRQ0JqQUxCZ2xnDQpoa2dCWlFNRUFnRUVJRHVSbXc4RkVOdDNpNTNPb2tDdmcxSVJKaWI1VS9tY3JNVHFmL2VvZXcvNE1Bd0dBMVVkDQpFd0VCL3dRQ01BQXdJZ1lJS3dZQkJRVUhBUU1FRmpBVU1BZ0dCZ1FBamtZQkFUQUlCZ1lFQUk1R0FRUXdZQVlJDQpLd1lCQlFVSEFRRUVWREJTTUI4R0NDc0dBUVVGQnpBQmhoTm9kSFJ3T2k4dmIyTnpjQzVrYm1sbExtVnpNQzhHDQpDQ3NHQVFVRkJ6QUNoaU5vZEhSd09pOHZkM2QzTG1SdWFXVXVaWE12WTJWeWRITXZRVU5TWVdsNkxtTnlkREE3DQpCZ05WSFNBRU5EQXlNREFHQ0dDRlZBRUNBZ0lFTUNRd0lnWUlLd1lCQlFVSEFnRVdGbWgwZEhBNkx5OTNkM2N1DQpaRzVwWlM1bGN5OWtjR013SHdZRFZSMGpCQmd3Rm9BVU9xYUo3QlhvSkdSeDRDVit5YkZpTVFmcEJxSXdIUVlEDQpWUjBPQkJZRUZDQll5cnN5S2hIbzN5dzBYazJVUHJicWtDdEJNQTBHQ1NxR1NJYjNEUUVCQlFVQUE0SUJBUUJ6DQpBaXYrSS9IVnFzVk00TXpyRlJtTHBQMU9JLzVYMDB1YWhjNUFPbGUwaVkwWm5PcjV6TnpNbExBRFVUcGJSQ0VvDQpnZWV1c2ZPR1dkTHQvdjY1N0pucHBNb083cEs2OVo2c2hVT2R1Q0MvaEdKc0tHL1JBUXoyakNOV05IamRCYUNEDQp2TlFNcGFKSkc3MW8wSWRQY0hPYUlEbmNEQUMxQXQzNHRGZTdRMFlqU2JNTkpCTkFRRzY2eC9nWW1iRVhncWlqDQp2eDVUZktmQ05UbG9ZclBnSk5PSUNjV1pmbmRlemRZblIvMTc2NTB1SE02UndJeld2TjdMU1UzSWVkWFVQUGo1DQpaZEdmZjdWT294cW5MSklQcFNUOFBoOCtNUjExYi9MTTJXU3JseXUybUNhMTFlSUdranl1ZXJoNTRzWWxPK3VJDQpuaHdzRkJxKzBxZk9zRkg2T1JnMA0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQ0K"));
	    anexo.setCodigoFichero("FIRMA_CODIGO_FICHERO_" + idFicheroFirmado);
	    anexo.setContenido("Firma Contenido del anexo".getBytes());
	    anexo.setCodigoFicheroFirmado("CODIGO_FICHERO_"+ idFicheroFirmado);
	    //anexo.setIdentificadorFicheroFirmado(idFicheroFirmado);
	    anexo.setNombreFichero("FIRMA_NOMBRE_FICHERO_" + idFicheroFirmado + ".txt");
	    anexo.setObservaciones("FIRMA_OBSERVACIONES " + idFicheroFirmado);
	    //anexo.setTimestamp("***timestamp***".getBytes());
	    anexo.setTipoDocumento(TipoDocumentoEnum.DOCUMENTO_ADJUNTO.getValue());
	    anexo.setTipoMIME("text/plain");
	    //anexo.setValidacionOCSPCertificado(Base64.decodeBase64("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"));
	    anexo.setValidezDocumento(ValidezDocumentoEnum.ORIGINAL.getValue());

		return anexo;
	}


	public static InteresadoFormDTO createInteresadoFormDTO(String id) {

		InteresadoFormDTO interesado = new InteresadoFormDTO();

		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF.getValue());
		interesado.setDocumentoIdentificacionInteresado("A28855260");
		interesado.setRazonSocialInteresado("razonSocialInteresado " + id);
		interesado.setNombreInteresado(null);
		interesado.setPrimerApellidoInteresado(null);
		interesado.setSegundoApellidoInteresado(null);
		interesado.setCodigoPaisInteresado("0001");
		interesado.setCodigoProvinciaInteresado("05");
		interesado.setCodigoMunicipioInteresado("01544");
		interesado.setDireccionInteresado("direccionInteresado " + id);
		interesado.setCodigoPostalInteresado("33004");
		interesado.setCorreoElectronicoInteresado("correoElectronico" + id + "@interesado.es");
		interesado.setTelefonoInteresado("999999999");
		interesado.setDireccionElectronicaHabilitadaInteresado("deu");
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL.getValue());
		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF.getValue());
		interesado.setDocumentoIdentificacionRepresentante("00000000T");
		interesado.setRazonSocialRepresentante(null);
		interesado.setNombreRepresentante("nombreRepresentante " + id);
		interesado.setPrimerApellidoRepresentante("primerApellidoRepresentante " + id);
		interesado.setSegundoApellidoRepresentante("segundoApellidoRepresentante " + id);
		interesado.setCodigoPaisRepresentante("0001");
		interesado.setCodigoProvinciaRepresentante("05");
		interesado.setCodigoMunicipioRepresentante("01544");
		interesado.setDireccionRepresentante("direccionRepresentante " + id);
		interesado.setCodigoPostalRepresentante("33004");
		interesado.setCorreoElectronicoRepresentante("correoElectronico" + id + "@representante.es");
		interesado.setTelefonoRepresentante("666666666");
		interesado.setDireccionElectronicaHabilitadaRepresentante("deu_repr");
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA.getValue());
		interesado.setObservaciones("observaciones " + id);

		return interesado;
	}

	public static void assertEquals(InfoBAsientoRegistralDTO infoBAsientoDTO, InfoBAsientoRegistralVO infoBAsientoVO) {

		if (infoBAsientoDTO == null) {
			Assert.assertNull("infoBAsientoDTO es nulo y infoBAsientoVO no", infoBAsientoVO);
		} else {
			Assert.assertEquals(infoBAsientoDTO.getAplicacion(), infoBAsientoVO.getAplicacion());
			Assert.assertEquals(infoBAsientoDTO.getCodigoAsunto(), infoBAsientoVO.getCodigoAsunto());
			Assert.assertEquals(infoBAsientoDTO.getCodigoEntidadRegistral(), infoBAsientoVO.getCodigoEntidadRegistral());
			Assert.assertEquals(infoBAsientoDTO.getCodigoEntidadRegistralDestino(), infoBAsientoVO.getCodigoEntidadRegistralDestino());
			Assert.assertEquals(infoBAsientoDTO.getCodigoEntidadRegistralInicio(), infoBAsientoVO.getCodigoEntidadRegistralInicio());
			Assert.assertEquals(infoBAsientoDTO.getCodigoEntidadRegistralOrigen(), infoBAsientoVO.getCodigoEntidadRegistralOrigen());
			Assert.assertEquals(infoBAsientoDTO.getCodigoError(), infoBAsientoVO.getCodigoError());
			Assert.assertEquals(infoBAsientoDTO.getCodigoUnidadTramitacionDestino(), infoBAsientoVO.getCodigoUnidadTramitacionDestino());
			Assert.assertEquals(infoBAsientoDTO.getCodigoUnidadTramitacionOrigen(), infoBAsientoVO.getCodigoUnidadTramitacionOrigen());
			Assert.assertEquals(infoBAsientoDTO.getContactoUsuario(), infoBAsientoVO.getContactoUsuario());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionEntidadRegistralDestino(), infoBAsientoVO.getDescripcionEntidadRegistralDestino());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionEntidadRegistralInicio(), infoBAsientoVO.getDescripcionEntidadRegistralInicio());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionEntidadRegistralOrigen(), infoBAsientoVO.getDescripcionEntidadRegistralOrigen());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionError(), infoBAsientoVO.getDescripcionError());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionUnidadTramitacionDestino(), infoBAsientoVO.getDescripcionUnidadTramitacionDestino());
			Assert.assertEquals(infoBAsientoDTO.getDescripcionUnidadTramitacionOrigen(), infoBAsientoVO.getDescripcionUnidadTramitacionOrigen());
			Assert.assertEquals(infoBAsientoDTO.getDocumentacionFisica(), infoBAsientoVO.getDocumentacionFisica().getValue());
			Assert.assertEquals(infoBAsientoDTO.getEstado(), infoBAsientoVO.getEstado().getValue());
			Assert.assertEquals(infoBAsientoDTO.getExpone(), infoBAsientoVO.getExpone());
			Assert.assertEquals(DateUtils.toDate(infoBAsientoDTO.getFechaEstado()).toString(), infoBAsientoVO.getFechaEstado().toString());
			if ((infoBAsientoDTO.getFechaEnvio() != null) || (infoBAsientoVO.getFechaEnvio() != null)) {
				Assert.assertEquals(DateUtils.toDate(infoBAsientoDTO.getFechaEnvio()).toString(), infoBAsientoVO.getFechaEnvio().toString());
			}
			if ((infoBAsientoDTO.getFechaRecepcion() != null) || (infoBAsientoVO.getFechaRecepcion() != null)) {
				Assert.assertEquals(DateUtils.toDate(infoBAsientoDTO.getFechaRecepcion()).toString(), infoBAsientoVO.getFechaRecepcion().toString());
			}
			Assert.assertEquals(DateUtils.toDate(infoBAsientoDTO.getFechaRegistro()).toString(), infoBAsientoVO.getFechaRegistro().toString());
			Assert.assertEquals(DateUtils.toDate(infoBAsientoDTO.getFechaRegistroInicial()).toString(), infoBAsientoVO.getFechaRegistroInicial().toString());
			Assert.assertEquals(infoBAsientoDTO.getId(), infoBAsientoVO.getId());
			Assert.assertEquals(infoBAsientoDTO.getIdentificadorIntercambio(), infoBAsientoVO.getIdentificadorIntercambio());
			Assert.assertEquals(infoBAsientoDTO.getIndicadorPrueba(), infoBAsientoVO.getIndicadorPrueba().getValue());
			Assert.assertEquals(infoBAsientoDTO.getNombreUsuario(), infoBAsientoVO.getNombreUsuario());
			Assert.assertEquals(infoBAsientoDTO.getNumeroExpediente(), infoBAsientoVO.getNumeroExpediente());
			Assert.assertEquals(infoBAsientoDTO.getNumeroRegistro(), infoBAsientoVO.getNumeroRegistro());
			Assert.assertEquals(infoBAsientoDTO.getNumeroRegistroInicial(), infoBAsientoVO.getNumeroRegistroInicial());
			Assert.assertEquals(infoBAsientoDTO.getNumeroReintentos().intValue(), infoBAsientoVO.getNumeroReintentos());
			Assert.assertEquals(infoBAsientoDTO.getNumeroTransporte(), infoBAsientoVO.getNumeroTransporte());
			Assert.assertEquals(infoBAsientoDTO.getObservacionesApunte(), infoBAsientoVO.getObservacionesApunte());
			Assert.assertEquals(infoBAsientoDTO.getReferenciaExterna(), infoBAsientoVO.getReferenciaExterna());
			Assert.assertEquals(infoBAsientoDTO.getResumen(), infoBAsientoVO.getResumen());
			Assert.assertEquals(infoBAsientoDTO.getSolicita(), infoBAsientoVO.getSolicita());
			Assert.assertEquals(Base64.encodeBase64String(infoBAsientoDTO.getTimestampRegistro()), Base64.encodeBase64String(infoBAsientoVO.getTimestampRegistro()));
			Assert.assertEquals(Base64.encodeBase64String(infoBAsientoDTO.getTimestampRegistroInicial()), Base64.encodeBase64String(infoBAsientoVO.getTimestampRegistroInicial()));
			Assert.assertEquals(infoBAsientoDTO.getTipoRegistro(), infoBAsientoVO.getTipoRegistro().getValue());
			Assert.assertEquals(infoBAsientoDTO.getTipoTransporte(), infoBAsientoVO.getTipoTransporte().getValue());
		}
	}

	public static void assertEquals(AsientoRegistralDTO asientoDTO, AsientoRegistralVO asientoVO) {

		if (asientoDTO == null) {
			Assert.assertNull("asientoDTO es nulo y asientoVO no", asientoVO);
		} else {

			assertEquals((InfoBAsientoRegistralDTO)asientoDTO, (InfoBAsientoRegistralVO)asientoVO);

			if (asientoDTO.getAnexos() == null) {
				Assert.assertNull("asientoDTO.getAnexos() es nulo y asientoVO.getAnexos() no", asientoVO.getAnexos());
			} else {
				Assert.assertEquals(asientoDTO.getAnexos().size(), asientoVO.getAnexos().size());
				for (int i = 0; i < asientoDTO.getAnexos().size(); i++) {
					assertEquals(asientoDTO.getAnexos().get(i), asientoVO.getAnexos().get(i));
				}
			}

			if (asientoDTO.getInteresados() == null) {
				Assert.assertNull("asientoDTO.getInteresados() es nulo y asientoVO.getInteresados() no", asientoVO.getInteresados());
			} else {
				Assert.assertEquals(asientoDTO.getInteresados().size(), asientoVO.getInteresados().size());
				for (int i = 0; i < asientoDTO.getInteresados().size(); i++) {
					assertEquals(asientoDTO.getInteresados().get(i), asientoVO.getInteresados().get(i));
				}
			}
		}
	}

	public static void assertEquals(AnexoDTO anexoDTO, AnexoVO anexoVO) {

		if (anexoDTO == null) {
			Assert.assertNull("anexoDTO es nulo y anexoVO no", anexoVO);
		} else {
			if ((anexoDTO.getCertificado() != null) || (anexoDTO.getCertificado() != null)) {
				Assert.assertEquals(Base64.encodeBase64String(anexoDTO.getCertificado()), Base64.encodeBase64String(anexoVO.getCertificado()));
			}
			if ((anexoDTO.getFirma() != null) || (anexoDTO.getFirma() != null)) {
				Assert.assertEquals(Base64.encodeBase64String(anexoDTO.getFirma()), Base64.encodeBase64String(anexoVO.getFirma()));
			}
			if ((anexoDTO.getHash() != null) || (anexoDTO.getHash() != null)) {
				Assert.assertEquals(Base64.encodeBase64String(anexoDTO.getHash()), Base64.encodeBase64String(anexoVO.getHash()));
			}
			Assert.assertEquals(anexoDTO.getId(), anexoVO.getId());
			Assert.assertEquals(anexoDTO.getIdAsientoRegistral(), anexoVO.getIdAsientoRegistral());
			Assert.assertEquals(anexoDTO.getIdentificadorFicheroFirmado(), anexoVO.getIdentificadorFicheroFirmado());
			Assert.assertEquals(anexoDTO.getNombreFichero(), anexoVO.getNombreFichero());
			Assert.assertEquals(anexoDTO.getObservaciones(), anexoVO.getObservaciones());
			if ((anexoDTO.getTimestamp() != null) || (anexoDTO.getTimestamp() != null)) {
				Assert.assertEquals(Base64.encodeBase64String(anexoDTO.getTimestamp()), Base64.encodeBase64String(anexoVO.getTimestamp()));
			}
			Assert.assertEquals(anexoDTO.getTipoDocumento(), anexoVO.getTipoDocumento().getValue());
			Assert.assertEquals(anexoDTO.getTipoMIME(), anexoVO.getTipoMIME());
			if ((anexoDTO.getValidacionOCSPCertificado() != null) || (anexoDTO.getValidacionOCSPCertificado() != null)) {
				Assert.assertEquals(Base64.encodeBase64String(anexoDTO.getValidacionOCSPCertificado()), Base64.encodeBase64String(anexoVO.getValidacionOCSPCertificado()));
			}
			Assert.assertEquals(anexoDTO.getValidezDocumento(), anexoVO.getValidezDocumento().getValue());
		}
	}

	public static void assertEquals(InteresadoDTO interesadoDTO, InteresadoVO interesadoVO) {

		if (interesadoDTO == null) {
			Assert.assertNull("interesadoDTO es nulo y interesadoVO no", interesadoVO);
		} else {
			Assert.assertEquals(interesadoDTO.getId(), interesadoVO.getId());
			Assert.assertEquals(interesadoDTO.getIdAsientoRegistral(), interesadoVO.getIdAsientoRegistral());
			Assert.assertEquals(interesadoDTO.getTipoDocumentoIdentificacionInteresado(), interesadoVO.getTipoDocumentoIdentificacionInteresado().getValue());
			Assert.assertEquals(interesadoDTO.getDocumentoIdentificacionInteresado(), interesadoVO.getDocumentoIdentificacionInteresado());
			Assert.assertEquals(interesadoDTO.getRazonSocialInteresado(), interesadoVO.getRazonSocialInteresado());
			Assert.assertEquals(interesadoDTO.getNombreInteresado(), interesadoVO.getNombreInteresado());
			Assert.assertEquals(interesadoDTO.getPrimerApellidoInteresado(), interesadoVO.getPrimerApellidoInteresado());
			Assert.assertEquals(interesadoDTO.getSegundoApellidoInteresado(), interesadoVO.getSegundoApellidoInteresado());
			Assert.assertEquals(interesadoDTO.getCodigoPaisInteresado(), interesadoVO.getCodigoPaisInteresado());
			Assert.assertEquals(interesadoDTO.getCodigoProvinciaInteresado(), interesadoVO.getCodigoProvinciaInteresado());
			Assert.assertEquals(interesadoDTO.getCodigoMunicipioInteresado(), interesadoVO.getCodigoMunicipioInteresado());
			Assert.assertEquals(interesadoDTO.getDireccionInteresado(), interesadoVO.getDireccionInteresado());
			Assert.assertEquals(interesadoDTO.getCodigoPostalInteresado(), interesadoVO.getCodigoPostalInteresado());
			Assert.assertEquals(interesadoDTO.getCorreoElectronicoInteresado(), interesadoVO.getCorreoElectronicoInteresado());
			Assert.assertEquals(interesadoDTO.getTelefonoInteresado(), interesadoVO.getTelefonoInteresado());
			Assert.assertEquals(interesadoDTO.getDireccionElectronicaHabilitadaInteresado(), interesadoVO.getDireccionElectronicaHabilitadaInteresado());
			Assert.assertEquals(interesadoDTO.getCanalPreferenteComunicacionInteresado(), interesadoVO.getCanalPreferenteComunicacionInteresado().getValue());
			Assert.assertEquals(interesadoDTO.getTipoDocumentoIdentificacionRepresentante(), interesadoVO.getTipoDocumentoIdentificacionRepresentante().getValue());
			Assert.assertEquals(interesadoDTO.getDocumentoIdentificacionRepresentante(), interesadoVO.getDocumentoIdentificacionRepresentante());
			Assert.assertEquals(interesadoDTO.getRazonSocialRepresentante(), interesadoVO.getRazonSocialRepresentante());
			Assert.assertEquals(interesadoDTO.getNombreRepresentante(), interesadoVO.getNombreRepresentante());
			Assert.assertEquals(interesadoDTO.getPrimerApellidoRepresentante(), interesadoVO.getPrimerApellidoRepresentante());
			Assert.assertEquals(interesadoDTO.getSegundoApellidoRepresentante(), interesadoVO.getSegundoApellidoRepresentante());
			Assert.assertEquals(interesadoDTO.getCodigoPaisRepresentante(), interesadoVO.getCodigoPaisRepresentante());
			Assert.assertEquals(interesadoDTO.getCodigoProvinciaRepresentante(), interesadoVO.getCodigoProvinciaRepresentante());
			Assert.assertEquals(interesadoDTO.getCodigoMunicipioRepresentante(), interesadoVO.getCodigoMunicipioRepresentante());
			Assert.assertEquals(interesadoDTO.getDireccionRepresentante(), interesadoVO.getDireccionRepresentante());
			Assert.assertEquals(interesadoDTO.getCodigoPostalRepresentante(), interesadoVO.getCodigoPostalRepresentante());
			Assert.assertEquals(interesadoDTO.getCorreoElectronicoRepresentante(), interesadoVO.getCorreoElectronicoRepresentante());
			Assert.assertEquals(interesadoDTO.getTelefonoRepresentante(), interesadoVO.getTelefonoRepresentante());
			Assert.assertEquals(interesadoDTO.getDireccionElectronicaHabilitadaRepresentante(), interesadoVO.getDireccionElectronicaHabilitadaRepresentante());
			Assert.assertEquals(interesadoDTO.getCanalPreferenteComunicacionRepresentante(), interesadoVO.getCanalPreferenteComunicacionRepresentante().getValue());
			Assert.assertEquals(interesadoDTO.getObservaciones(), interesadoVO.getObservaciones());
		}
	}

	public static void assertEquals(AsientoRegistralFormDTO asientoFormDTO, AsientoRegistralDTO asientoDTO) {

		if (asientoFormDTO == null) {
			Assert.assertNull("asientoFormDTO es nulo y asientoDTO no", asientoDTO);
		} else {
			Assert.assertEquals(asientoFormDTO.getCodigoAsunto(), asientoDTO.getCodigoAsunto());
			Assert.assertEquals(asientoFormDTO.getCodigoEntidadRegistral(), asientoDTO.getCodigoEntidadRegistral());
			Assert.assertEquals(asientoFormDTO.getCodigoEntidadRegistralDestino(), asientoDTO.getCodigoEntidadRegistralDestino());
			Assert.assertEquals(asientoFormDTO.getCodigoEntidadRegistralInicio(), asientoDTO.getCodigoEntidadRegistralInicio());
			Assert.assertEquals(asientoFormDTO.getCodigoEntidadRegistralOrigen(), asientoDTO.getCodigoEntidadRegistralOrigen());
			Assert.assertEquals(asientoFormDTO.getCodigoUnidadTramitacionDestino(), asientoDTO.getCodigoUnidadTramitacionDestino());
			Assert.assertEquals(asientoFormDTO.getCodigoUnidadTramitacionOrigen(), asientoDTO.getCodigoUnidadTramitacionOrigen());
			Assert.assertEquals(asientoFormDTO.getContactoUsuario(), asientoDTO.getContactoUsuario());
			Assert.assertEquals(asientoFormDTO.getDescripcionEntidadRegistralDestino(), asientoDTO.getDescripcionEntidadRegistralDestino());
			Assert.assertEquals(asientoFormDTO.getDescripcionEntidadRegistralInicio(), asientoDTO.getDescripcionEntidadRegistralInicio());
			Assert.assertEquals(asientoFormDTO.getDescripcionEntidadRegistralOrigen(), asientoDTO.getDescripcionEntidadRegistralOrigen());
			Assert.assertEquals(asientoFormDTO.getDescripcionUnidadTramitacionDestino(), asientoDTO.getDescripcionUnidadTramitacionDestino());
			Assert.assertEquals(asientoFormDTO.getDescripcionUnidadTramitacionOrigen(), asientoDTO.getDescripcionUnidadTramitacionOrigen());
			Assert.assertEquals(asientoFormDTO.getDocumentacionFisica(), asientoDTO.getDocumentacionFisica());
			Assert.assertEquals(asientoFormDTO.getExpone(), asientoDTO.getExpone());
			Assert.assertEquals(DateUtils.toDate(asientoFormDTO.getFechaRegistro()).toString(), DateUtils.toDate(asientoDTO.getFechaRegistro()).toString());
			if ((asientoFormDTO.getFechaRegistroInicial() != null) || (asientoDTO.getFechaRegistroInicial() != null)) {
				Assert.assertEquals(DateUtils.toDate(asientoFormDTO.getFechaRegistroInicial()).toString(), DateUtils.toDate(asientoDTO.getFechaRegistroInicial()).toString());
			}
			Assert.assertEquals(asientoFormDTO.getIndicadorPrueba(), asientoDTO.getIndicadorPrueba());
			Assert.assertEquals(asientoFormDTO.getNombreUsuario(), asientoDTO.getNombreUsuario());
			Assert.assertEquals(asientoFormDTO.getNumeroExpediente(), asientoDTO.getNumeroExpediente());
			Assert.assertEquals(asientoFormDTO.getNumeroRegistro(), asientoDTO.getNumeroRegistro());
			Assert.assertEquals(asientoFormDTO.getNumeroRegistroInicial(), asientoDTO.getNumeroRegistroInicial());
			Assert.assertEquals(asientoFormDTO.getNumeroTransporte(), asientoDTO.getNumeroTransporte());
			Assert.assertEquals(asientoFormDTO.getObservacionesApunte(), asientoDTO.getObservacionesApunte());
			Assert.assertEquals(asientoFormDTO.getReferenciaExterna(), asientoDTO.getReferenciaExterna());
			Assert.assertEquals(asientoFormDTO.getResumen(), asientoDTO.getResumen());
			Assert.assertEquals(asientoFormDTO.getSolicita(), asientoDTO.getSolicita());
			if ((asientoFormDTO.getTimestampRegistro() != null) && (asientoDTO.getTimestampRegistro() != null)) {
				Assert.assertEquals(Base64.encodeBase64String(asientoFormDTO.getTimestampRegistro()), Base64.encodeBase64String(asientoDTO.getTimestampRegistro()));
			}
			if ((asientoFormDTO.getTimestampRegistroInicial() != null) && (asientoDTO.getTimestampRegistroInicial() != null)) {
				Assert.assertEquals(Base64.encodeBase64String(asientoFormDTO.getTimestampRegistroInicial()), Base64.encodeBase64String(asientoDTO.getTimestampRegistroInicial()));
			}
			Assert.assertEquals(asientoFormDTO.getTipoRegistro(), asientoDTO.getTipoRegistro());
			Assert.assertEquals(asientoFormDTO.getTipoTransporte(), asientoDTO.getTipoTransporte());

			if (asientoFormDTO.getAnexos() == null) {
				Assert.assertNull("asientoFormDTO.getAnexos() es nulo y asientoDTO.getAnexos() no", asientoDTO.getAnexos());
			} else {
				Assert.assertEquals(asientoFormDTO.getAnexos().size(), asientoDTO.getAnexos().size());
				for (int i = 0; i < asientoFormDTO.getAnexos().size(); i++) {
					assertEquals(asientoFormDTO.getAnexos().get(i), asientoDTO.getAnexos().get(i));
				}
			}

			if (asientoFormDTO.getInteresados() == null) {
				Assert.assertNull("asientoFormDTO.getInteresados() es nulo y asientoDTO.getInteresados() no", asientoDTO.getInteresados());
			} else {
				Assert.assertEquals(asientoFormDTO.getInteresados().size(), asientoDTO.getInteresados().size());
				for (int i = 0; i < asientoFormDTO.getInteresados().size(); i++) {
					assertEquals(asientoFormDTO.getInteresados().get(i), asientoDTO.getInteresados().get(i));
				}
			}
		}
	}

	public static void assertEquals(AnexoFormDTO anexoFormDTO, AnexoDTO anexoDTO) {

		if (anexoFormDTO == null) {
			Assert.assertNull("anexoFormDTO es nulo y anexoDTO no", anexoDTO);
		} else {
			if (anexoFormDTO.getCertificado() != null) {
				Assert.assertEquals(Base64.encodeBase64String(anexoFormDTO.getCertificado()), Base64.encodeBase64String(anexoDTO.getCertificado()));
			}
			if (anexoFormDTO.getFirma() != null) {
				Assert.assertEquals(Base64.encodeBase64String(anexoFormDTO.getFirma()), Base64.encodeBase64String(anexoDTO.getFirma()));
			}
			Assert.assertEquals(anexoFormDTO.getNombreFichero(), anexoDTO.getNombreFichero());
			Assert.assertEquals(anexoFormDTO.getObservaciones(), anexoDTO.getObservaciones());
			if ((anexoFormDTO.getTimestamp() != null) && (anexoDTO.getTimestamp() != null)) {
				Assert.assertEquals(Base64.encodeBase64String(anexoFormDTO.getTimestamp()), Base64.encodeBase64String(anexoDTO.getTimestamp()));
			}
			Assert.assertEquals(anexoFormDTO.getTipoDocumento(), anexoDTO.getTipoDocumento());
			Assert.assertEquals(anexoFormDTO.getTipoMIME(), anexoDTO.getTipoMIME());
			if (anexoFormDTO.getValidacionOCSPCertificado() != null) {
				Assert.assertEquals(Base64.encodeBase64String(anexoFormDTO.getValidacionOCSPCertificado()), Base64.encodeBase64String(anexoDTO.getValidacionOCSPCertificado()));
			}
			Assert.assertEquals(anexoFormDTO.getValidezDocumento(), anexoDTO.getValidezDocumento());
		}
	}

	public static void assertEquals(InteresadoFormDTO interesadoFormDTO, InteresadoDTO interesadoDTO) {

		if (interesadoFormDTO == null) {
			Assert.assertNull("interesadoFormDTO es nulo y interesadoDTO no", interesadoDTO);
		} else {
			Assert.assertEquals(interesadoFormDTO.getTipoDocumentoIdentificacionInteresado(), interesadoDTO.getTipoDocumentoIdentificacionInteresado());
			Assert.assertEquals(interesadoFormDTO.getDocumentoIdentificacionInteresado(), interesadoDTO.getDocumentoIdentificacionInteresado());
			Assert.assertEquals(interesadoFormDTO.getRazonSocialInteresado(), interesadoDTO.getRazonSocialInteresado());
			Assert.assertEquals(interesadoFormDTO.getNombreInteresado(), interesadoDTO.getNombreInteresado());
			Assert.assertEquals(interesadoFormDTO.getPrimerApellidoInteresado(), interesadoDTO.getPrimerApellidoInteresado());
			Assert.assertEquals(interesadoFormDTO.getSegundoApellidoInteresado(), interesadoDTO.getSegundoApellidoInteresado());
			Assert.assertEquals(interesadoFormDTO.getCodigoPaisInteresado(), interesadoDTO.getCodigoPaisInteresado());
			Assert.assertEquals(interesadoFormDTO.getCodigoProvinciaInteresado(), interesadoDTO.getCodigoProvinciaInteresado());
			Assert.assertEquals(interesadoFormDTO.getCodigoMunicipioInteresado(), interesadoDTO.getCodigoMunicipioInteresado());
			Assert.assertEquals(interesadoFormDTO.getDireccionInteresado(), interesadoDTO.getDireccionInteresado());
			Assert.assertEquals(interesadoFormDTO.getCodigoPostalInteresado(), interesadoDTO.getCodigoPostalInteresado());
			Assert.assertEquals(interesadoFormDTO.getCorreoElectronicoInteresado(), interesadoDTO.getCorreoElectronicoInteresado());
			Assert.assertEquals(interesadoFormDTO.getTelefonoInteresado(), interesadoDTO.getTelefonoInteresado());
			Assert.assertEquals(interesadoFormDTO.getDireccionElectronicaHabilitadaInteresado(), interesadoDTO.getDireccionElectronicaHabilitadaInteresado());
			Assert.assertEquals(interesadoFormDTO.getCanalPreferenteComunicacionInteresado(), interesadoDTO.getCanalPreferenteComunicacionInteresado());
			Assert.assertEquals(interesadoFormDTO.getTipoDocumentoIdentificacionRepresentante(), interesadoDTO.getTipoDocumentoIdentificacionRepresentante());
			Assert.assertEquals(interesadoFormDTO.getDocumentoIdentificacionRepresentante(), interesadoDTO.getDocumentoIdentificacionRepresentante());
			Assert.assertEquals(interesadoFormDTO.getRazonSocialRepresentante(), interesadoDTO.getRazonSocialRepresentante());
			Assert.assertEquals(interesadoFormDTO.getNombreRepresentante(), interesadoDTO.getNombreRepresentante());
			Assert.assertEquals(interesadoFormDTO.getPrimerApellidoRepresentante(), interesadoDTO.getPrimerApellidoRepresentante());
			Assert.assertEquals(interesadoFormDTO.getSegundoApellidoRepresentante(), interesadoDTO.getSegundoApellidoRepresentante());
			Assert.assertEquals(interesadoFormDTO.getCodigoPaisRepresentante(), interesadoDTO.getCodigoPaisRepresentante());
			Assert.assertEquals(interesadoFormDTO.getCodigoProvinciaRepresentante(), interesadoDTO.getCodigoProvinciaRepresentante());
			Assert.assertEquals(interesadoFormDTO.getCodigoMunicipioRepresentante(), interesadoDTO.getCodigoMunicipioRepresentante());
			Assert.assertEquals(interesadoFormDTO.getDireccionRepresentante(), interesadoDTO.getDireccionRepresentante());
			Assert.assertEquals(interesadoFormDTO.getCodigoPostalRepresentante(), interesadoDTO.getCodigoPostalRepresentante());
			Assert.assertEquals(interesadoFormDTO.getCorreoElectronicoRepresentante(), interesadoDTO.getCorreoElectronicoRepresentante());
			Assert.assertEquals(interesadoFormDTO.getTelefonoRepresentante(), interesadoDTO.getTelefonoRepresentante());
			Assert.assertEquals(interesadoFormDTO.getDireccionElectronicaHabilitadaRepresentante(), interesadoDTO.getDireccionElectronicaHabilitadaRepresentante());
			Assert.assertEquals(interesadoFormDTO.getCanalPreferenteComunicacionRepresentante(), interesadoDTO.getCanalPreferenteComunicacionRepresentante());
			Assert.assertEquals(interesadoFormDTO.getObservaciones(), interesadoDTO.getObservaciones());
		}
	}

	public static AsientoRegistralVO getAsientoRegistralVO(
			AsientoRegistralDTO asientoDTO) {

		AsientoRegistralVO asientoVO = null;

		if (asientoDTO != null) {
			asientoVO = new AsientoRegistralVO();
			asientoVO.setId(asientoDTO.getId());
			asientoVO.setCodigoEntidadRegistral(asientoDTO.getCodigoEntidadRegistral());
			asientoVO.setCodigoEntidadRegistralOrigen(asientoDTO.getCodigoEntidadRegistralOrigen());
			asientoVO.setDescripcionEntidadRegistralOrigen(asientoDTO.getDescripcionEntidadRegistralOrigen());
			asientoVO.setNumeroRegistro(asientoDTO.getNumeroRegistro());
			asientoVO.setFechaRegistro(DateUtils.toDate(asientoDTO.getFechaRegistro()));
			asientoVO.setTimestampRegistro(asientoDTO.getTimestampRegistro());
			asientoVO.setNumeroRegistroInicial(asientoDTO.getNumeroRegistroInicial());
			asientoVO.setFechaRegistroInicial(DateUtils.toDate(asientoDTO.getFechaRegistroInicial()));
			asientoVO.setTimestampRegistroInicial(asientoDTO.getTimestampRegistroInicial());
			asientoVO.setCodigoUnidadTramitacionOrigen(asientoDTO.getCodigoUnidadTramitacionOrigen());
			asientoVO.setDescripcionUnidadTramitacionOrigen(asientoDTO.getDescripcionUnidadTramitacionOrigen());
			asientoVO.setCodigoEntidadRegistralDestino(asientoDTO.getCodigoEntidadRegistralDestino());
			asientoVO.setDescripcionEntidadRegistralDestino(asientoDTO.getDescripcionEntidadRegistralDestino());
			asientoVO.setCodigoUnidadTramitacionDestino(asientoDTO.getCodigoUnidadTramitacionDestino());
			asientoVO.setDescripcionUnidadTramitacionDestino(asientoDTO.getDescripcionUnidadTramitacionDestino());
			asientoVO.setResumen(asientoDTO.getResumen());
			asientoVO.setCodigoAsunto(asientoDTO.getCodigoAsunto());
			asientoVO.setReferenciaExterna(asientoDTO.getReferenciaExterna());
			asientoVO.setNumeroExpediente(asientoDTO.getNumeroExpediente());
			asientoVO.setTipoTransporte(TipoTransporteEnum.getTipoTransporte(asientoDTO.getTipoTransporte()));
			asientoVO.setNumeroTransporte(asientoDTO.getNumeroTransporte());
			asientoVO.setNombreUsuario(asientoDTO.getNombreUsuario());
			asientoVO.setContactoUsuario(asientoDTO.getContactoUsuario());
			asientoVO.setIdentificadorIntercambio(asientoDTO.getIdentificadorIntercambio());
			asientoVO.setEstado(EstadoAsientoRegistralEnum.getEstadoAsientoRegistral(asientoDTO.getEstado()));
			asientoVO.setFechaEstado(DateUtils.toDate(asientoDTO.getFechaEstado()));
			asientoVO.setFechaEnvio(DateUtils.toDate(asientoDTO.getFechaEnvio()));
			asientoVO.setFechaRecepcion(DateUtils.toDate(asientoDTO.getFechaRecepcion()));
			asientoVO.setNumeroReintentos(asientoDTO.getNumeroReintentos() != null ? asientoDTO.getNumeroReintentos() : 0);
			asientoVO.setAplicacion(asientoDTO.getAplicacion());
			asientoVO.setTipoRegistro(TipoRegistroEnum.getTipoRegistro(asientoDTO.getTipoRegistro()));
			asientoVO.setDocumentacionFisica(DocumentacionFisicaEnum.getDocumentacionFisica(asientoDTO.getDocumentacionFisica()));
			asientoVO.setObservacionesApunte(asientoDTO.getObservacionesApunte());
			asientoVO.setIndicadorPrueba(IndicadorPruebaEnum.getIndicadorPrueba(asientoDTO.getIndicadorPrueba()));
			asientoVO.setCodigoEntidadRegistralInicio(asientoDTO.getCodigoEntidadRegistralInicio());
			asientoVO.setDescripcionEntidadRegistralInicio(asientoDTO.getDescripcionEntidadRegistralInicio());
			asientoVO.setExpone(asientoDTO.getExpone());
			asientoVO.setSolicita(asientoDTO.getSolicita());
			asientoVO.setCodigoError(asientoDTO.getCodigoError());
			asientoVO.setDescripcionError(asientoDTO.getDescripcionError());

			// Anexos
			asientoVO.getAnexos().addAll(getListaAnexoVO(asientoDTO.getAnexos()));

			// Interesados
			asientoVO.getInteresados().addAll(getListaInteresadoVO(asientoDTO.getInteresados()));
		}

		return asientoVO;
	}

	protected static List<AnexoVO> getListaAnexoVO(List<AnexoDTO> listaAnexoDTO) {

		List<AnexoVO> listaAnexoVO = new ArrayList<AnexoVO>();

		if (listaAnexoDTO != null) {
			for (AnexoDTO anexoDTO : listaAnexoDTO) {
				if (anexoDTO != null) {
					listaAnexoVO.add(getAnexoVO(anexoDTO));
				}
			}
		}

		return listaAnexoVO;
	}

	protected static List<InteresadoVO> getListaInteresadoVO(List<InteresadoDTO> listaInteresadoDTO) {

		List<InteresadoVO> listaInteresadoVO = new ArrayList<InteresadoVO>();

		if (listaInteresadoDTO != null) {
			for (InteresadoDTO interesadoDTO : listaInteresadoDTO) {
				if (interesadoDTO != null) {
					listaInteresadoVO.add(getInteresadoVO(interesadoDTO));
				}
			}
		}

		return listaInteresadoVO;
	}

	public static AnexoVO getAnexoVO(AnexoDTO anexoDTO) {

		AnexoVO anexoVO = null;

		if (anexoDTO != null) {
			anexoVO = new AnexoVO();
			anexoVO.setId(anexoDTO.getId());
			anexoVO.setIdAsientoRegistral(anexoDTO.getIdAsientoRegistral());
				anexoVO.setIdentificadorFichero(anexoDTO.getIdentificadorFichero());
			anexoVO.setNombreFichero(anexoDTO.getNombreFichero());
			anexoVO.setValidezDocumento(ValidezDocumentoEnum.getValidezDocumento(anexoDTO.getValidezDocumento()));
			anexoVO.setTipoDocumento(TipoDocumentoEnum.getTipoDocumento(anexoDTO.getTipoDocumento()));
			anexoVO.setCertificado(anexoDTO.getCertificado());
			anexoVO.setFirma(anexoDTO.getFirma());
			anexoVO.setTimestamp(anexoDTO.getTimestamp());
			anexoVO.setValidacionOCSPCertificado(anexoDTO.getValidacionOCSPCertificado());
			anexoVO.setHash(anexoDTO.getHash());
			anexoVO.setTipoMIME(anexoDTO.getTipoMIME());
			anexoVO.setIdentificadorFicheroFirmado(anexoDTO.getIdentificadorFicheroFirmado());
			anexoVO.setObservaciones(anexoDTO.getObservaciones());
		}

		return anexoVO;
	}

	public static InteresadoVO getInteresadoVO(InteresadoDTO interesadoDTO) {

		InteresadoVO interesadoVO = null;

		if (interesadoDTO != null) {
			interesadoVO = new InteresadoVO();
			interesadoVO.setId(interesadoDTO.getId());
			interesadoVO.setIdAsientoRegistral(interesadoDTO.getIdAsientoRegistral());
			interesadoVO.setTipoDocumentoIdentificacionInteresado(
					TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(interesadoDTO.getTipoDocumentoIdentificacionInteresado()));
			interesadoVO.setDocumentoIdentificacionInteresado(interesadoDTO.getDocumentoIdentificacionInteresado());
			interesadoVO.setRazonSocialInteresado(interesadoDTO.getRazonSocialInteresado());
			interesadoVO.setNombreInteresado(interesadoDTO.getNombreInteresado());
			interesadoVO.setPrimerApellidoInteresado(interesadoDTO.getPrimerApellidoInteresado());
			interesadoVO.setSegundoApellidoInteresado(interesadoDTO.getSegundoApellidoInteresado());
			interesadoVO.setCodigoPaisInteresado(interesadoDTO.getCodigoPaisInteresado());
			interesadoVO.setCodigoProvinciaInteresado(interesadoDTO.getCodigoProvinciaInteresado());
			interesadoVO.setCodigoMunicipioInteresado(interesadoDTO.getCodigoMunicipioInteresado());
			interesadoVO.setDireccionInteresado(interesadoDTO.getDireccionInteresado());
			interesadoVO.setCodigoPostalInteresado(interesadoDTO.getCodigoPostalInteresado());
			interesadoVO.setCorreoElectronicoInteresado(interesadoDTO.getCorreoElectronicoInteresado());
			interesadoVO.setTelefonoInteresado(interesadoDTO.getTelefonoInteresado());
			interesadoVO.setDireccionElectronicaHabilitadaInteresado(interesadoDTO.getDireccionElectronicaHabilitadaInteresado());
			interesadoVO.setCanalPreferenteComunicacionInteresado(
					CanalNotificacionEnum.getCanalNotificacion(interesadoDTO.getCanalPreferenteComunicacionInteresado()));
			interesadoVO.setTipoDocumentoIdentificacionRepresentante(
					TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion(interesadoDTO.getTipoDocumentoIdentificacionRepresentante()));
			interesadoVO.setDocumentoIdentificacionRepresentante(interesadoDTO.getDocumentoIdentificacionRepresentante());
			interesadoVO.setRazonSocialRepresentante(interesadoDTO.getRazonSocialRepresentante());
			interesadoVO.setNombreRepresentante(interesadoDTO.getNombreRepresentante());
			interesadoVO.setPrimerApellidoRepresentante(interesadoDTO.getPrimerApellidoRepresentante());
			interesadoVO.setSegundoApellidoRepresentante(interesadoDTO.getSegundoApellidoRepresentante());
			interesadoVO.setCodigoPaisRepresentante(interesadoDTO.getCodigoPaisRepresentante());
			interesadoVO.setCodigoProvinciaRepresentante(interesadoDTO.getCodigoProvinciaRepresentante());
			interesadoVO.setCodigoMunicipioRepresentante(interesadoDTO.getCodigoMunicipioRepresentante());
			interesadoVO.setDireccionRepresentante(interesadoDTO.getDireccionRepresentante());
			interesadoVO.setCodigoPostalRepresentante(interesadoDTO.getCodigoPostalRepresentante());
			interesadoVO.setCorreoElectronicoRepresentante(interesadoDTO.getCorreoElectronicoRepresentante());
			interesadoVO.setTelefonoRepresentante(interesadoDTO.getTelefonoRepresentante());
			interesadoVO.setDireccionElectronicaHabilitadaRepresentante(interesadoDTO.getDireccionElectronicaHabilitadaRepresentante());
			interesadoVO.setCanalPreferenteComunicacionRepresentante(
					CanalNotificacionEnum.getCanalNotificacion(interesadoDTO.getCanalPreferenteComunicacionRepresentante()));
			interesadoVO.setObservaciones(interesadoDTO.getObservaciones());
		}

		return interesadoVO;
	}
	
	public static String generaCadena(String contenido, int longitudTotal){
		String caracteres="0123456789";
		String result;
		if(contenido==null){
			contenido=""; 
		}
		result=StringUtils.rightPad(contenido, longitudTotal, caracteres);
		result=result.substring(0, longitudTotal-1);
		return  result;
	}
	
	public static String generaCadenaCaracteresEspeciales(String contenido,int longitudMaxima){
		String caracteresEspeciales="áéíóüÁÉÍÓÚÜñÑ";
		String result;
		//String caracteresEspeciales="!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~¡¢£¤¥¦§¨©ª«¬ ®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
				
		if(contenido==null){
			contenido=""; 
		}
		result=StringUtils.rightPad(contenido, longitudMaxima, caracteresEspeciales);
		result=result.substring(0, longitudMaxima-1);
		return  result;

	}
	
	public static byte[] getTimestamp(){
		return "***timestamp***".getBytes();
	}
	
	public static void vaciarRepresentante(InteresadoFormDTO interesado){
		interesado.setTipoDocumentoIdentificacionRepresentante(null);
		interesado.setDocumentoIdentificacionRepresentante(null);
		interesado.setRazonSocialRepresentante(null);
		interesado.setNombreRepresentante(null);
		interesado.setPrimerApellidoRepresentante(null);
		interesado.setSegundoApellidoRepresentante(null);
		interesado.setCodigoPaisRepresentante(null);
		interesado.setCodigoProvinciaRepresentante(null);
		interesado.setCodigoMunicipioRepresentante(null);
		interesado.setDireccionRepresentante(null);
		interesado.setCodigoPostalRepresentante(null);
		interesado.setCorreoElectronicoRepresentante(null);
		interesado.setTelefonoRepresentante(null);
		interesado.setDireccionElectronicaHabilitadaRepresentante(null);
		interesado.setCanalPreferenteComunicacionRepresentante(null);
	}

	public static InfoRechazoDTO createInfoRechazoDTO(TipoRechazoEnum tipoRechazo) {
		
		InfoRechazoDTO infoRechazo = new InfoRechazoDTO();
		
		infoRechazo.setTipoRechazo(tipoRechazo.getValue());
		infoRechazo.setDescripcion("Motivo del rechazo");
		infoRechazo.setUsuario("usuario");
		infoRechazo.setContacto("contacto");
		infoRechazo.setAplicacion("app1");
		
		return infoRechazo;
	}
}
