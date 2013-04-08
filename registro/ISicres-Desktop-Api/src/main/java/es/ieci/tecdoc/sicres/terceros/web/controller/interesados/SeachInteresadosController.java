package es.ieci.tecdoc.sicres.terceros.web.controller.interesados;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.FilterVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.OperatorEnum;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosFacade;

/**
 * Controller que busca los interesados que cumplan el criterio indicado desde
 * el formulario del registro
 *
 * @author 66194663
 *
 */
public class SeachInteresadosController extends AbstractController{


	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		getInteresadosCRUDController().setInteresados(request,
				request.getSession());

		String code = ServletRequestUtils.getStringParameter(request,"Code");

		//Obtenemos los datos para la paginación
		Integer maxResults = Integer.parseInt(Configurator
				.getInstance()
				.getProperty(
						ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES));
		Integer rangoDefault = Integer.parseInt(Configurator
				.getInstance()
				.getProperty(
						ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_VALIDATION_LIST_SIZE));


		//Se compone la consulta para persona fisica
		CriteriaVO criterioInterFisico = new CriteriaVO();
		criterioInterFisico.setType(SearchType.FISICO);

		List filtersFisico = new ArrayList();
		//Filtro por nif
		FilterVO terceroFisicoNIF = new FilterVO();
		terceroFisicoNIF.setField("nif");
		terceroFisicoNIF.setOperator(OperatorEnum.ES_IGUAL);
		terceroFisicoNIF.setValue(code);
		filtersFisico.add(terceroFisicoNIF);

		//Seteamos los filtros de persona fisica al criterio de busqueda
		criterioInterFisico.setFilters(filtersFisico);

		//Obtenemos los datos de la busqueda para persona fisica
		List tercerosFisicos = getTercerosFacade().findByCriteria(criterioInterFisico);


		//Se compone la consulta para persona juridica
		CriteriaVO criteriaInterJuridico = new CriteriaVO();
		criteriaInterJuridico.setType(SearchType.JURIDICO);


		List filtersJuridico = new ArrayList();
		//Filtro por cif
		FilterVO terceroJuridicoCIF = new FilterVO();
		terceroJuridicoCIF.setField("cif");
		terceroJuridicoCIF.setOperator(OperatorEnum.ES_IGUAL);
		terceroJuridicoCIF.setValue(code);
		filtersJuridico.add(terceroJuridicoCIF);

		//Seteamos los filtros de persona juridica al criterio de busqueda
		criteriaInterJuridico.setFilters(filtersJuridico);

		//Obtenemos los datos de la busqueda para persona juridica
		List tercerosJuridicos = getTercerosFacade().findByCriteria(criteriaInterJuridico);

		//Validamos los resultados de busqueda
		if(tercerosFisicos.size() + tercerosJuridicos.size() == 0){
			// En caso de no encontrar nada IGUAL a NIF o CIF se cambia el
			// criterio de busqueda a que el NIF o CIF COMIENCE POR...
			terceroFisicoNIF.setOperator(OperatorEnum.COMIENZA_POR);
			terceroJuridicoCIF.setOperator(OperatorEnum.COMIENZA_POR);

			// Se lanza la busqueda
			tercerosFisicos = getTercerosFacade().findByCriteria(criterioInterFisico);
			tercerosJuridicos = getTercerosFacade().findByCriteria(criteriaInterJuridico);

			//Se vuelve a validar la busqueda
			if(tercerosFisicos.size() + tercerosJuridicos.size() == 0){
				//Sino se encuentra nada por CIF o NIF que COMIENZA POR, se pasa a buscar por NOMBRE COMIENZA POR...
				terceroFisicoNIF.setField("first_name");
				terceroFisicoNIF.setOperator(OperatorEnum.COMIENZA_POR);
				terceroJuridicoCIF.setField("name");
				terceroJuridicoCIF.setOperator(OperatorEnum.COMIENZA_POR);

				// Se lanza de nuevo la busqueda
				tercerosFisicos = getTercerosFacade().findByCriteria(criterioInterFisico);
				tercerosJuridicos = getTercerosFacade().findByCriteria(criteriaInterJuridico);
			}
		}

		// Generamos el objeto que contiene el resultado de busqueda
		List resultInteresados = new ArrayList();

		if((tercerosFisicos.size()>0) && !(tercerosFisicos.isEmpty())){
			// Si la busqueda de interesados fisicos tiene datos, se asigna al
			// resultado de busqueda
			resultInteresados.addAll(tercerosFisicos);
		}
		if((tercerosJuridicos.size()>0) && !(tercerosJuridicos.isEmpty())){
			// Si la busqueda de interesados juridico tiene datos, se asigna al
			// resultado de busqueda
			resultInteresados.addAll(tercerosJuridicos);
		}

		// Si todo ha sido correcto se responde a la vista correspondiente
		// pasando el resultado de la busqueda
		return new ModelAndView(getSuccessView()).addObject("list", resultInteresados);
	}

	public String getSuccessView() {
		return successView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}


	public String getFormView() {
		return formView;
	}

	public void setFormView(String formView) {
		this.formView = formView;
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



	protected InteresadosCRUDController interesadosCRUDController;

	protected TercerosFacade tercerosFacade;

	protected String successView;

	protected String formView;

}
