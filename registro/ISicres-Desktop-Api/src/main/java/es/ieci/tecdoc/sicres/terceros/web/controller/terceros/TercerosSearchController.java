package es.ieci.tecdoc.sicres.terceros.web.controller.terceros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import es.ieci.tecdoc.fwktd.web.controller.BaseFormController;
import es.ieci.tecdoc.fwktd.web.propertyeditor.ValuedEnumPropertyEditor;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.FilterVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.OperatorEnum;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;
import es.ieci.tecdoc.sicres.terceros.web.controller.interesados.InteresadosCRUDController;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosFacade;

/**
 *
 * @author IECISA
 *
 */
public class TercerosSearchController extends BaseFormController {

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);

		binder.registerCustomEditor(SearchType.class,
				new ValuedEnumPropertyEditor<SearchType>() {
				});
		binder.registerCustomEditor(OperatorEnum.class,
				new ValuedEnumPropertyEditor<OperatorEnum>() {
				});
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		CriteriaVO criteria = (CriteriaVO) super.formBackingObject(request);
		criteria.setType(SearchType.FISICO);

		if (!isFormSubmission(request)) {
			// Inicializamos los filtros de busqueda
			criteria.setFilters(populateDefaultFilters(request,
					new ArrayList<FilterVO>()));
		}

		return criteria;
	}

	private List<FilterVO> populateDefaultFilters(HttpServletRequest request,
			List<FilterVO> filters) {

		if (!CollectionUtils.exists(filters,
				new BeanPropertyValueEqualsPredicate("field", "nif"))) {
			FilterVO terceroFisicoNIF = new FilterVO();
			terceroFisicoNIF.setField("nif");
			terceroFisicoNIF.setOperator(OperatorEnum.COMIENZA_POR);
			filters.add(terceroFisicoNIF);
		}

		if (!CollectionUtils.exists(filters,
				new BeanPropertyValueEqualsPredicate("field", "surname"))) {
			FilterVO terceroFisicoName = new FilterVO();
			terceroFisicoName.setField("surname");
			terceroFisicoName.setOperator(OperatorEnum.COMIENZA_POR);
			filters.add(terceroFisicoName);
		}

		if (!CollectionUtils.exists(filters,
				new BeanPropertyValueEqualsPredicate("field", "first_name"))) {
			FilterVO terceroFisicoSurname1 = new FilterVO();
			terceroFisicoSurname1.setField("first_name");
			terceroFisicoSurname1.setOperator(OperatorEnum.COMIENZA_POR);
			filters.add(terceroFisicoSurname1);
		}

		if (!CollectionUtils.exists(filters,
				new BeanPropertyValueEqualsPredicate("field", "second_name"))) {
			FilterVO terceroFisicoSurname2 = new FilterVO();
			terceroFisicoSurname2.setField("second_name");
			terceroFisicoSurname2.setOperator(OperatorEnum.COMIENZA_POR);
			filters.add(terceroFisicoSurname2);
		}

		if (!CollectionUtils.exists(filters,
				new BeanPropertyValueEqualsPredicate("field", "cif"))) {
			FilterVO terceroJuridicoCIF = new FilterVO();
			terceroJuridicoCIF.setField("cif");
			terceroJuridicoCIF.setOperator(OperatorEnum.COMIENZA_POR);
			filters.add(terceroJuridicoCIF);
		}

		if (!CollectionUtils.exists(filters,
				new BeanPropertyValueEqualsPredicate("field", "name"))) {
			FilterVO terceroJuridicoName = new FilterVO();
			terceroJuridicoName.setField("name");
			terceroJuridicoName.setOperator(OperatorEnum.COMIENZA_POR);
			filters.add(terceroJuridicoName);
		}

		return filters;
	}

	@Override
	protected void doReferenceData(HttpServletRequest request, Object command,
			Errors errors, Map<String, Object> referenceData) {
		super.doReferenceData(request, command, errors, referenceData);

		referenceData.put("operators", OperatorEnum.getEnumList());
		referenceData.put("searchTypes", SearchType.getEnumList());
	}

	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors, Map controlModel)
			throws Exception {

		String interesados = ServletRequestUtils.getStringParameter(request,
				"cadena");

		return super.showForm(request, response, errors, controlModel);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		List<TerceroValidadoVO> terceros = getTercerosFacade().findByCriteria(
				(CriteriaVO) command);

		Map<String, Object> referenceData = new HashMap<String, Object>();
		doReferenceData(request, command, errors, referenceData);

		ModelAndView mav = super.processFormSubmission(request, response,
				command, errors);
		mav.getModel().putAll(referenceData);
		mav.getModel().put("list", terceros);

		return mav;
	}

	public TercerosFacade getTercerosFacade() {
		return tercerosFacade;
	}

	public void setTercerosFacade(TercerosFacade tercerosFacade) {
		this.tercerosFacade = tercerosFacade;
	}

	public InteresadosCRUDController getInteresadosCRUDController() {
		return interesadosCRUDController;
	}

	public void setInteresadosCRUDController(
			InteresadosCRUDController interesadosCRUDController) {
		this.interesadosCRUDController = interesadosCRUDController;
	}

	protected TercerosFacade tercerosFacade;

	protected InteresadosCRUDController interesadosCRUDController;

}
