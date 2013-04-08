package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.PlantillaInformeVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.ArrayOfWSReport;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.WSReport;

/**
 * Instancia de <code>Mapper</code> que transforma una
 * <code>java.util.List</code> de <code>PlantillaInformeVO</code> en un objeto
 * de tipo <code>ArrayOfWSReport</code>.
 * 
 * @see PlantillaInformeVO
 * @see ArrayOfWSReport
 * 
 * @author IECISA
 * 
 */
public class ListOfPlantillaInformeVOToArrayOfWSReportMapper implements Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<PlantillaInformeVO> reports = (List<PlantillaInformeVO>) obj;

		ArrayOfWSReport result = new ArrayOfWSReport();
		for (PlantillaInformeVO plantilla : reports) {
			result.getWSReport().add(
					(WSReport) new PlantillaInformeVOToWSReportMapper()
							.map(plantilla));
		}

		return result;
	}

}
