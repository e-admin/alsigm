package es.ieci.plusvalias.util;

import ieci.tecdoc.sgm.pe.ws.client.Liquidacion;
import ieci.tecdoc.sgm.pe.ws.client.Tasa;
import ieci.tecdoc.sgm.rde.database.ContenedorDocumentoDatos;
import ieci.tecdoc.sgm.registro.util.RegistroDocumento;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentos;
import ieci.tecdoc.sgm.tram.ws.client.dto.DocumentoExpediente;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.ResultadoUnitario;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.sigem.RegistroTelematico;

public class SigmHelper {
	public static Logger logger = Logger.getLogger(SigmHelper.class);

	public String getDetalleLiquidacion(Plusvalia plusvalia, String model){
		if (model.indexOf("#REF_CATASTRAL#") != -1)
		{
			model = model.replaceAll("#REF_CATASTRAL#", plusvalia.getInmueble().getRefCatastral());
		}

		if (model.indexOf("#DIRECCION#") != -1)
		{
			String direccionInmueble = plusvalia.getInmueble().getTipovia() + " " + plusvalia.getInmueble().getNombrevia() + " "
			+ plusvalia.getInmueble().getNumvia() + " " + plusvalia.getInmueble().getNumero() + " " + plusvalia.getInmueble().getPlanta()
					+ " " + plusvalia.getInmueble().getPuerta() + " " + plusvalia.getInmueble().getEscalera();

			model = model.replaceAll("#DIRECCION#", direccionInmueble);
		}

		if (model.indexOf("#NUM_LIQUIDACION#") != -1)
		{
			model = model.replaceAll("#NUM_LIQUIDACION#", plusvalia.getFolderIdRegistro());
		}

		if (model.indexOf("#NUM_PROTOCOLO#") != -1)
		{
			model = model.replaceAll("#NUM_PROTOCOLO#", String.valueOf(plusvalia.getNumProtocolo()));
		}

		if (model.indexOf("#SUJETOPASIVO#") != -1)
		{
			model = model.replaceAll("#SUJETOPASIVO#", String.valueOf(plusvalia.getSujetoPasivo().getNombreCompleto()));
		}

		return model.replaceAll("null", "");
	}

	public String getDatosEspecificos(Plusvalia plusvalia, Transmitente transmitiente, String xml) throws DocumentException
	{
		org.dom4j.Document doc = DocumentHelper.parseText(xml);

		ResultadoUnitario plusvaliaParcial = plusvalia.getResultado();

		W3cXmlUtils.setNode(doc, "/Datos_Especificos/REF_CATASTRAL", plusvalia.getInmueble().getRefCatastral());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/NOM_VIA", plusvalia.getInmueble().getNombrevia());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/NUM_VIA", plusvalia.getInmueble().getNumero());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/TRANS_ANTERIOR", plusvalia.getFechatanterior());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/TRANS_ACTUAL", plusvalia.getFechatactual());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/SUP_SOLAR", plusvalia.getInmueble().getSupsolar());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/VALOR", plusvaliaParcial.getValorTerreno());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/VALOR_UNITARIO", plusvaliaParcial.getValorUnitario());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/VALOR_FINAL", plusvaliaParcial.getValorTerrenoFin());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/PORC_DEDUCCION", plusvaliaParcial.getPorcentajeDeduccion());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/TIPO_IMPOSITIVO", plusvaliaParcial.getTipoImpositivo());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/PORC_RECARGO", plusvaliaParcial.getPorcentajeRecargo());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/TOTAL", plusvalia.getResultado().getTotalLiquidacion());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/NIF_TRANSMITIENTE", plusvalia.getNifTrans());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/NOM_TRANSMITIENTE", plusvalia.getNombreTrans());
		String edadTransmitiente = "";
		if (transmitiente.getEdad() > 0) {
			edadTransmitiente = String.valueOf(transmitiente.getEdad());
		}
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/EDAD_TRANSMITIENTE", edadTransmitiente);
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/PORC_TRANSMITIDO", plusvalia.getPorcTransmitido());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/PORC_BONIFICACION", plusvalia.getPorcBonificacion());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/NIF_ADQUIRIENTE", plusvalia.getNifAdq());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/NOM_ADQUIRIENTE", plusvalia.getNombreAdqui());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/CLASE_DERECHO", plusvalia.getClaseDerecho());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/PORC_ADQUIRIDO", plusvalia.getPorcAdquirido());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/NUM_NOTARIO", plusvalia.getNumNotario());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/NOM_NOTARIO", plusvalia.getNotario());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/NUM_PROTOCOLO", plusvalia.getNumProtocolo());
		W3cXmlUtils.setNode(doc, "/Datos_Especificos/REF_PAGO", plusvalia.getReferenciaPago());

		if (logger.isDebugEnabled())
		{
			logger.debug(doc.asXML());
		}

		return doc.asXML();
	}

	public ArrayList<DocumentoExpediente> getDocumentosTramitacion(RegistroTelematico rt, String numRegistro) throws Exception
	{
		ArrayList<DocumentoExpediente> documentos = new ArrayList<DocumentoExpediente>();

		RegistroDocumentos documentosRegistro = rt.getDocumentosRegistro(numRegistro);

		for (int i = 0; i < documentosRegistro.count(); i++)
		{
			RegistroDocumento rd = documentosRegistro.get(i);

			DocumentoExpediente documento = new DocumentoExpediente();
			ContenedorDocumentoDatos cdd = rt.getDocument(rd.getGuid());

			documento.setName(rd.getCode());
			documento.setExtension(cdd.getExtension());

			if (rd.getCode().equals(RegistroTelematico.CODIGO_SOLICITUD))
			{
				documento.setCode(RegistroTelematico.TRAMITADOR_SOLICITUD);
			}
			else if (rd.getCode().equals(RegistroTelematico.CODIGO_JUSTIFICANTE))
			{
				documento.setCode(RegistroTelematico.TRAMITADOR_JUSTIFICANTE);
			}

			documento.setLenght(cdd.getContentSize());
			documento.setContent(cdd.getContent());

			documentos.add(documento);
		}

		return documentos;
	}

	public ieci.tecdoc.sgm.core.services.pago.Liquidacion getLiquidacion(Liquidacion liquidacion){
		 ieci.tecdoc.sgm.core.services.pago.Liquidacion liq=new ieci.tecdoc.sgm.core.services.pago.Liquidacion();
		 liq.setDatosEspecificos(liquidacion.getDatosEspecificos());
		 liq.setDiscriminante(liquidacion.getDiscriminante());
		 liq.setEjercicio(liquidacion.getEjercicio());
		 liq.setEstado(liquidacion.getEstado());
		 liq.setFechaPago(liquidacion.getFechaPago()!=null?liquidacion.getFechaPago().getTime():null);
		 liq.setFinPeriodo(liquidacion.getFinPeriodo()!=null?liquidacion.getFinPeriodo().getTime():null);
		 liq.setIdEntidadEmisora(liquidacion.getIdEntidadEmisora());
		 liq.setIdTasa(liquidacion.getIdTasa());
		 liq.setImporte(liquidacion.getImporte());
		 liq.setInicioPeriodo(liquidacion.getInicioPeriodo()!=null?liquidacion.getInicioPeriodo().getTime():null);
		 liq.setNif(liquidacion.getNif());
		 liq.setNombre(liquidacion.getNombre());
		 liq.setNrc(liquidacion.getNrc());
		 liq.setReferencia(liquidacion.getReferencia());
		 liq.setSolicitud(liquidacion.getSolicitud());
		 liq.setTasa(liquidacion.getTasa()!=null?getTasa(liquidacion.getTasa()):null);
		 liq.setVencimiento(liquidacion.getVencimiento());
		return liq;
	}

	public ieci.tecdoc.sgm.core.services.pago.Tasa getTasa(Tasa tasa){
		ieci.tecdoc.sgm.core.services.pago.Tasa t=new ieci.tecdoc.sgm.core.services.pago.Tasa();
		t.setCaptura(tasa.getCaptura());
		t.setCodigo(tasa.getCodigo());
		t.setDatosEspecificos(tasa.getDatosEspecificos());
		t.setIdEntidadEmisora(tasa.getIdEntidadEmisora());
		t.setModelo(tasa.getModelo());
		t.setNombre(tasa.getNombre());
		t.setTipo(tasa.getTipo());
		return t;
	}
}
