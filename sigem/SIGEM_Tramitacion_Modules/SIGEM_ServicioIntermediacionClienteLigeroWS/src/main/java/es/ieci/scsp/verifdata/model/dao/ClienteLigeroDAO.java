package es.ieci.scsp.verifdata.model.dao;

import java.util.List;

import org.apache.log4j.Logger;

import es.ieci.scsp.verifdata.model.dao.interfaces.ClienteLigeroGenericoDAO;
import es.ieci.scsp.verifdata.model.mapping.clienteligero.CertificadosAutorizaciones;



public class ClienteLigeroDAO extends ClienteLigeroGenericoDAO{
	
	private static final Logger log = Logger.getLogger(ClienteLigeroDAO.class);

	/**
	 * 
	 * Hasta aquí se implementan los métodos heredados, desde ahora vamos con los métodos específicos
	 * 
	 ***/
	
	public List<CertificadosAutorizaciones> getProcedimientoByDNI(String nifFuncionario, String codigoProcedimiento) {
		log.warn("Inicio getProcedimientoByDNI");		
			
		try{

			List<CertificadosAutorizaciones> instanceProcedimiento = null;
			
			List result = ejecutaHQLConsultaNativa("SELECT codigo_certificado FROM usuarios_autorizaciones where " +
					"id_usuario = '"+ nifFuncionario +"' AND codigo_procedimiento = '"+codigoProcedimiento+"'");
			StringBuffer proced = new StringBuffer("");
			if(result.size() == 0){
				log.warn("No existen datos para esa consulta SELECT codigo_certificado FROM usuarios_autorizaciones where " +
						"id_usuario = '"+ nifFuncionario +"' AND codigo_procedimiento = '"+codigoProcedimiento+"'");
			}
			else{
				if(result.size()>0){
					String codigoCertificado = (String) result.get(0);
					proced.append( " codigo_certificado = '"+ codigoCertificado +"'");
				}
				if(result.size()>1){
					for(int i=1; i < result.size(); i++){
						String codigoCertificado = (String) result.get(i);
						proced.append( " or codigo_certificado = '"+ codigoCertificado +"'");
					}
				}
					
				//Saco los representantes que hacen referencia a ese dni
				String strQuery = "from CertificadosAutorizaciones where"+proced.toString();		
				log.warn("strQueryParticipantes "+strQuery);
				instanceProcedimiento = ejecutaHQLConsulta(strQuery);
//				CertificadosAutorizaciones[] resPro = new CertificadosAutorizaciones [instanceProcedimiento.size()];
//				for(int i=0; i < instanceProcedimiento.size(); i ++){
//					resPro[i] = instanceProcedimiento.get(i);
//				}
			}
			return instanceProcedimiento;
		}
		catch(Exception e){
			log.error("TCG no hemos recuperado ningun procedimiento con nif: "+nifFuncionario+", error: "+e);
			return null;
		}
	}
	
	public CertificadosAutorizaciones getCertAutByCodCert(String codigoCertificado) {

		CertificadosAutorizaciones certificadosAutorizaciones = (CertificadosAutorizaciones) super.findById(CertificadosAutorizaciones.class, codigoCertificado);

		return certificadosAutorizaciones;
	}

}
