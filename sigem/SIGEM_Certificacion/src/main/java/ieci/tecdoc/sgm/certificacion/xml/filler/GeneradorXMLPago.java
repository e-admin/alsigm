package ieci.tecdoc.sgm.certificacion.xml.filler;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.certificacion.bean.pago.Liquidacion;
import ieci.tecdoc.sgm.certificacion.bean.pago.Pago;
import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.exception.CodigosErrorCertificacionException;
import ieci.tecdoc.sgm.certificacion.util.Defs;
import ieci.tecdoc.sgm.certificacion.util.Utilidades;
import ieci.tecdoc.sgm.certificacion.xml.GeneradorXML;
import ieci.tecdoc.sgm.certificacion.xml.bean.Campo;
import ieci.tecdoc.sgm.certificacion.xml.bean.DatosComunes;
import ieci.tecdoc.sgm.certificacion.xml.bean.DatosEspecificos;

/**
 * Clase que genera la parte específica del XML de Pago
 * @author José Antonio Nogales
 */
public class GeneradorXMLPago extends GeneradorXML {
	private static final Logger logger = Logger.getLogger(GeneradorXMLPago.class);
			
	/**
	 * Método que genera un XML para pagos
	 * @param datosComunes Datos comunes del XML
	 * @param poPagos Listado de pagos a incluir en la parte específica del XML
	 * @param header true para incluir la cabecera del XML, false para no incluirla
	 * @return XML con los datos
	 * @throws CertificacionException En caso de producirse algún error
	 */
	public static String GenerarXML(DatosComunes datosComunes, Pago[] poPagos, boolean header) throws CertificacionException {
		try {
			DatosEspecificos datosEspecificos = new DatosEspecificos();
			
			if (poPagos != null && poPagos.length > 0){
				ArrayList pagos = new ArrayList();
				Campo campo = new Campo();
				campo.setEtiquetaXML(Defs.TAG_XML_PAGOS);
				ArrayList pagosArray = new ArrayList();
				for(int i=0; i<poPagos.length; i++){
					Pago oPago = poPagos[i];
					Campo aux = new Campo();
					aux.setEtiquetaXML(Defs.TAG_XML_PAGO);
					ArrayList valores = new ArrayList();
					valores.add(new Campo(Defs.TAG_XML_PAGO_ID_TASA, oPago.getIdTasa(), null));
					valores.add(new Campo(Defs.TAG_XML_PAGO_ID_ENTIDAD_EMISORA, oPago.getIdEntidadEmisora(), null));
					valores.add(new Campo(Defs.TAG_XML_PAGO_IMPORTE, oPago.getImporte() + " " + Defs.MONEDA, null));
					valores.add(new Campo(Defs.TAG_XML_PAGO_FECHA, oPago.getFecha(), null));
					valores.add(new Campo(Defs.TAG_XML_PAGO_HORA, oPago.getHora(), null));
					Liquidacion liquidacion = oPago.getLiquidacion();
					Campo cLiquidacion = new Campo();
					cLiquidacion.setEtiquetaXML("Liquidacion");
					ArrayList arrayLiquidacion = new ArrayList();
					arrayLiquidacion.add(new Campo("InicioPeriodo",liquidacion.getInicioPeriodo(), null));
					arrayLiquidacion.add(new Campo("FinPeriodo",liquidacion.getFinPeriodo(), null));
					cLiquidacion.setHijosCampo(arrayLiquidacion);
					valores.add(cLiquidacion);
					aux.setHijosCampo(valores);
					pagosArray.add(aux);
				}
				campo.setHijosCampo(pagosArray);
				pagos.add(campo);
				datosEspecificos.setDatosEspecificos(pagos);
			}
			
			return GenerarXML(datosComunes, datosEspecificos, header);
		}catch(CertificacionException ce){
			throw ce;
		}catch(Exception e) {
			logger.error("Se ha producido un error en la generación del XML", e);
			throw new CertificacionException(
				CodigosErrorCertificacionException.ERROR_GENERACION_XML,
				e.getMessage(),
				e.getCause());
		}
	}
	
}
