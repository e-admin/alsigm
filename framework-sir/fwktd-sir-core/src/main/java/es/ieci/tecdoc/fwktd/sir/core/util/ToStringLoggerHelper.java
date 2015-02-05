package es.ieci.tecdoc.fwktd.sir.core.util;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;

/**
 * Clase auxiliar para generar cadenas de mensajes a utilizar en procesos de logger 
 * 
 * @author IECISA
 *
 *
 */
public class ToStringLoggerHelper {
	
	public static String toStringLogger(AsientoRegistralVO asiento){
		return toStringLogger((InfoBAsientoRegistralVO) asiento);
	}
	
	public static String toStringLogger(InfoBAsientoRegistralVO asiento){
		String[] excludeFields = {"timestampRegistro","timestampRegistroInicial"};
		return new ReflectionToStringBuilder(asiento, null, null, InfoBAsientoRegistralVO.class, false,false).setExcludeFieldNames(excludeFields).toString();
	}
	
	public static String toStringLogger(TrazabilidadVO traza){
		String[] excludeFields = null;;
		return new ReflectionToStringBuilder(traza, null, null, TrazabilidadVO.class, false,false).setExcludeFieldNames(excludeFields).toString();
	}
	
	public static String toStringLogger(InteresadoFormVO interesado){
		String[] excludeFields = null;;
		return new ReflectionToStringBuilder(interesado, null, null, InteresadoFormVO.class, false,false).setExcludeFieldNames(excludeFields).toString();
	}
	
	public static String toStringLogger(AnexoFormVO anexo){
		String[] excludeFields = {"certificado","firma","timestamp","validacionOCSPCertificado","contenido"};
		return new ReflectionToStringBuilder(anexo, null, null, AnexoFormVO.class, false,false).setExcludeFieldNames(excludeFields).toString();
	}
	
	public static String toStringLogger(AsientoRegistralFormVO asiento){
		String[] excludeFields = {"timestampRegistroInicial","anexos"};;
		return new ReflectionToStringBuilder(asiento, null, null, AsientoRegistralFormVO.class, false,false).setExcludeFieldNames(excludeFields).toString();
	}
	
	public static String toStringLogger(InteresadoVO interesado){
		String[] excludeFields = null;
		return new ReflectionToStringBuilder(interesado, null, null, InteresadoVO.class, false,false).setExcludeFieldNames(excludeFields).toString();
	}
	
	public static String toStringLogger(AnexoVO anexo){
		String[] excludeFields = {"certificado","firma","timestamp","validacionOCSPCertificado","hash"};
		return new ReflectionToStringBuilder(anexo, null, null, AnexoVO.class, false,false).setExcludeFieldNames(excludeFields).toString();
	}
	
}
