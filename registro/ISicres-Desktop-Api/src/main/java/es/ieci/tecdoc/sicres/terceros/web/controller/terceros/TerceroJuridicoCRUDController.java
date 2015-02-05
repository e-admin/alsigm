package es.ieci.tecdoc.sicres.terceros.web.controller.terceros;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import es.ieci.tecdoc.fwktd.web.controller.crud.CRUDController;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;
import es.ieci.tecdoc.sicres.terceros.web.delegate.MasterValuesDelegate;

/**
 *
 * @author IECISA
 *
 */
public class TerceroJuridicoCRUDController extends
		CRUDController<TerceroValidadoJuridicoVO> {

	@Override
	protected void onCreateSuccess(TerceroValidadoJuridicoVO create,
			ModelAndView mav) {
		super.onCreateSuccess(create, mav);

		// Exponemos el identificador del tercero para asociarlo como interesado
		mav.addObject("tercero.id", create.getId());
	}

	@Override
	protected void doReferenceData(HttpServletRequest request,
			Map<String, Object> referenceData) {
		super.doReferenceData(request, referenceData);

		referenceData.put("tiposDocumentos", getMasterValuesDelegate()
				.getTiposDocumentoIdentificativo(SearchType.JURIDICO));
	}

	public MasterValuesDelegate getMasterValuesDelegate() {
		return masterValuesDelegate;
	}

	public void setMasterValuesDelegate(
			MasterValuesDelegate masterValuesDelegate) {
		this.masterValuesDelegate = masterValuesDelegate;
	}

	protected MasterValuesDelegate masterValuesDelegate;

}
