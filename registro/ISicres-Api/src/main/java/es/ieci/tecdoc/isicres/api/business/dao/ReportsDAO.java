package es.ieci.tecdoc.isicres.api.business.dao;

import es.ieci.tecdoc.isicres.api.business.vo.ReportVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * 
 * @author IECISA
 * 
 */
public interface ReportsDAO {

	/**
	 * 
	 * @param reportId
	 * @return
	 */
	public ReportVO getReportInfo(UsuarioVO usuario, String reportId);
}
