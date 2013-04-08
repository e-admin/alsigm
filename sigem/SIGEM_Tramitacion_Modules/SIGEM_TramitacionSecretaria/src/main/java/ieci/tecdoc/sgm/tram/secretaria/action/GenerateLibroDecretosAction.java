package ieci.tecdoc.sgm.tram.secretaria.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.log4j.Logger;


public class GenerateLibroDecretosAction extends GenerateLibroAction {

	private static final Logger logger = Logger.getLogger(GenerateLibroDecretosAction.class);

	public String getIdTpDocContenido() throws ISPACException {
		try {
			return ctx.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_MODELO_DECRETO);
		} catch (ISPACException e) {
			logger.warn("Error al obtener la variable de sistema"+SecretariaConstants.COD_TPDOC_MODELO_DECRETO+e);
			throw(e);
		}
	}

	public String getTableNameToAddQuery(){
		return SecretariaConstants.ENTITY_DECRETO;
	}

	public String [] getIdTpDocContenidoAdicional() throws ISPACException {


		String [] idTpDocCotenidoAdicional= new String[1];
		idTpDocCotenidoAdicional[0]=	ctx.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_C_ERROR_DEC);
		return idTpDocCotenidoAdicional;

	}


	public String getQueryContenido(String initDay,
								String endDay,
								String idTpDocContenido,
								String numexp) {


		return " WHERE SPAC_DT_DOCUMENTOS.NUMEXP="+SecretariaConstants.ENTITY_DECRETO+".NUMEXP AND "+
		SecretariaConstants.ENTITY_DECRETO+"."+SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA +">= " +initDay+" AND " +
		SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA +"<= " +endDay+" AND"+
		" ID_TPDOC="+idTpDocContenido +" ORDER BY "+ 	SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA  ;
	}

	public String getQueryContenidoAdicional(String initDay,
			 String endDay,
			 String [] idTpDocContenido,
			 String numexp) {
         //En el libro de decretos como contenido adicional solo están las diligencia de corrección de error
		if(idTpDocContenido!=null && idTpDocContenido.length>0){
		return " WHERE  ID_TPDOC="+idTpDocContenido[0]+" AND"+
		" ESTADOFIRMA='"+DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)+"' AND SPAC_DT_DOCUMENTOS.NUMEXP='"+numexp+
		"' AND SPAC_EXPEDIENTES.NUMEXP=SPAC_DT_DOCUMENTOS.NUMEXP";
		}
		return "";

	}

	protected String getFieldFecha(){
		return SecretariaConstants.ENTITY_DECRETO+":"+SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA;
	}
	protected String getFieldName(){
		return SecretariaConstants.ENTITY_DECRETO+":"+SecretariaConstants.FIELD_DECRETO_NOMBRE;
	}
	protected String getFieldNum(){
		return SecretariaConstants.ENTITY_DECRETO+":"+SecretariaConstants.FIELD_DECRETO_NUM_DECRETO;
	}



}
