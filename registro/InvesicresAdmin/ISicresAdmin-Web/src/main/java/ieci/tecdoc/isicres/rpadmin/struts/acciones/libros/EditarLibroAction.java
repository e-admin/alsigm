package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;


import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.LibroForm;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Libro;
import es.ieci.tecdoc.isicres.admin.beans.LibroBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.beans.PermisosSicres;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionLibroSicres3Utils;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminLibroManager;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class EditarLibroAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(EditarLibroAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {


		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		String idBook = (String)request.getParameter("idLibro");
//		String tipo = (String)request.getParameter("tipo");
//		String nombre = (String)request.getParameter("nombre");
		if(idBook == null || idBook.equals(""))
			idBook = (String)request.getSession(false).getAttribute("idLibro");

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		LibroForm libroForm = (LibroForm)form;
		if( idBook != null && !idBook.equals("")) {

            // Mostramos la página de editarLibro.jsp
			if(libroForm.getId() == null) {
				LibroBean libro = oServicio.obtenerLibroBean(Integer.parseInt(idBook), entidad);

				//Obtenemos el nombre del repositorio a partir de su id
				//if(libro.getIdLista() != Defs.NULL_ID){
				if(libro.getIdLista() != Integer.MAX_VALUE - 1){
					OptionsBean listas = oServicio.obtenerListas( entidad);
					for(int i=0; i < listas.count(); i++){
						if(listas.get(i).getCodigo().equals(String.valueOf(libro.getIdLista()))){
							libro.setNombreLista(listas.get(i).getDescripcion());
							break;
						}
					}
				}
				BeanUtils.copyProperties(libroForm, libro);
			}
			// Para cargar oficinas asociadas y sus permisos
			PermisosSicres permisosSicres = oServicio.obtenerPermisosOficinasLibro(Integer.parseInt(idBook), entidad);
			BeanUtils.copyProperties(libroForm, permisosSicres);

			request.setAttribute("permisosSicres", permisosSicres);

			//  Comprobamos si el libro ya tiene los campos de Sicres3
			boolean actualizableASicres3 = false;
			if (DefinicionLibroSicres3Utils.isSicres3Enabled()) {
				// Si está activado el intercambio registral y el libro NO es aún SICRES3, no tiene aún los campos de IR, se puede
				// actualizar para convertirlo a SICRES3
				if (!ISicresRPAdminLibroManager.isLibroSicres3(Integer.parseInt(idBook), entidad.getIdentificador())) {
					actualizableASicres3 = true;
				}
			}
			// Si ya es Sicres3 NO será actualizable nuevamente, ya los tiene y si NO es Sicres3, se podrá actualizar para que lo sea
			libroForm.setActualizableASicres3(actualizableASicres3);
		}else{
			// Esto se pone para que al guardar si hay errores no se pierdan los datos
			String autenticacion = libroForm.getAutenticacion();
			if(autenticacion.equals(Boolean.TRUE.toString()) || autenticacion.equals(String.valueOf(LibroBean.AUTENTICACION_IMAGENES_SI)))
				libroForm.setAutenticacion(String.valueOf(LibroBean.AUTENTICACION_IMAGENES_SI));
			else
				libroForm.setAutenticacion(String.valueOf(LibroBean.AUTENTICACION_IMAGENES_NO));
		}

		request.setAttribute("tipoLibro", loadOptionsTipo(request));
		request.setAttribute("numeracionLibro", loadOptionsNumeracion(request));

		return mapping.findForward("success");
	}

	private OptionsBean loadOptionsTipo(HttpServletRequest request) {

        //	Cargamos los combos Tipo de Libro
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource", LocaleFilterHelper.getCurrentLocale(request));

		OptionsBean optionsTipo = new OptionsBean();

		OptionBean optionTipoSeleccione = new OptionBean();
		optionTipoSeleccione.setCodigo("");
		optionTipoSeleccione.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.tipo.seleccione"));
		optionsTipo.add(optionTipoSeleccione);

		OptionBean optionTipoEntrada = new OptionBean();
		optionTipoEntrada.setCodigo(String.valueOf(Libro.LIBRO_ENTRADA));
		optionTipoEntrada.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.entrada"));
		optionsTipo.add(optionTipoEntrada);

		OptionBean optionTipoSalida = new OptionBean();
		optionTipoSalida.setCodigo(String.valueOf(Libro.LIBRO_SALIDA));
		optionTipoSalida.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.salida"));
		optionsTipo.add(optionTipoSalida);

		return optionsTipo;


	}

	private OptionsBean loadOptionsNumeracion(HttpServletRequest request) {

        // Cargamos los combos Numeración de Libro
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource", LocaleFilterHelper.getCurrentLocale(request));

		OptionsBean optionsNumeracion = new OptionsBean();

		OptionBean optionNumSeleccione = new OptionBean();
		optionNumSeleccione.setCodigo("");
		optionNumSeleccione.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.numeracion.seleccione"));
		optionsNumeracion.add(optionNumSeleccione);

		OptionBean optionNumCentral = new OptionBean();
		optionNumCentral.setCodigo(String.valueOf(LibroBean.NUMERACION_CENTRAL));
		optionNumCentral.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.numeracion.central"));
		optionsNumeracion.add(optionNumCentral);

		OptionBean optionNumOficina = new OptionBean();
		optionNumOficina.setCodigo(String.valueOf(LibroBean.NUMERACION_POR_OFICINA));
		optionNumOficina.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.numeracion.oficina"));
		optionsNumeracion.add(optionNumOficina);

		return optionsNumeracion;
	}
}