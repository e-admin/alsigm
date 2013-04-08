package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.PlantillaInformeVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.WSReport;

/**
 * Instancia de <code>Mapper</code> que transforma una instancia de
 * <code>PlantillaInformeVO</code> en una de tipo <code>WSReport</code>.
 * 
 * @see PlantillaInformeVO
 * @see WSReport
 * 
 * @author IECISA
 * 
 */
public class PlantillaInformeVOToWSReportMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(PlantillaInformeVO.class, obj);

		PlantillaInformeVO plantilla = (PlantillaInformeVO) obj;

		WSReport result = new WSReport();

		result.setId(Integer.valueOf(plantilla.getId()));
		result.setName(plantilla.getNombre());
		result.setType(plantilla.getTipo().getValue());

		return result;
	}

}
