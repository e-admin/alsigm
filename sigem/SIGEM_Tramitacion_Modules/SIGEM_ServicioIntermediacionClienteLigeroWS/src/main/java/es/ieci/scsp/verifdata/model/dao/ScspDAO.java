package es.ieci.scsp.verifdata.model.dao;

import java.util.List;

import org.apache.log4j.Logger;

import es.ieci.scsp.verifdata.model.dao.interfaces.ScspGenericoDAO;
import es.ieci.scsp.verifdata.model.mapping.clienteligero.CertificadosAutorizaciones;
import es.ieci.scsp.verifdata.model.mapping.scsp.CoreServicio;

public class ScspDAO extends ScspGenericoDAO {
	private static final Logger log = Logger.getLogger(ScspDAO.class);

	/**
	 * 
	 * Hasta aquí se implementan los métodos heredados, desde ahora vamos con los métodos específicos
	 * 
	 ***/
	
	public List<CoreServicio> getDescripcionProcedimiento(List<CertificadosAutorizaciones> procedimientos) {
		log.warn("Inicio getDescripcionProcedimiento");		
			
		try{

			List<CoreServicio> coreServicio = null;
			
			StringBuffer proced = new StringBuffer("");
			if(procedimientos.size()>0){
				CertificadosAutorizaciones cAut = procedimientos.get(0);
				String codCertificado = cAut.getCodigoCertificado();
				proced.append( " codcertificado = '"+ codCertificado +"'");
			}
			if(procedimientos.size()>1){
				for(int i=1; i < procedimientos.size(); i++){
					CertificadosAutorizaciones cAut = procedimientos.get(i);
					String codCertificado = cAut.getCodigoCertificado();
					proced.append( " or codcertificado = '"+ codCertificado +"'");
				}
			}
				
			//Saco los representantes que hacen referencia a ese dni
			String strQuery = "from CoreServicio where"+proced.toString();		
			log.warn("strQueryCoreServicios "+strQuery);
			coreServicio = ejecutaHQLConsulta(strQuery);

			return coreServicio;
		}
		catch(Exception e){
			log.error("TCG no hemos recuperado ningun procedimiento, error: "+e);
			return null;
		}
	}

	public CoreServicio getCoreServByCodCertificado(String codCertificado) {
		CoreServicio coreServicio = new CoreServicio();
		coreServicio = (CoreServicio) super.findById(CoreServicio.class, codCertificado);
		return coreServicio;
	}

	public List<String> getEmisorCertificadoByNombre(String emisorServicio) {
		return ejecutaHQLConsultaNativa("SELECT cif FROM core_emisor_certificado where nombre = '"+emisorServicio+"'");
	}

}
