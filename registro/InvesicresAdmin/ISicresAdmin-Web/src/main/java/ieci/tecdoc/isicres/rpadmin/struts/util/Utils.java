package ieci.tecdoc.isicres.rpadmin.struts.util;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Constantes;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.keys.InformesKeys;
import ieci.tecdoc.isicres.rpadmin.struts.forms.AsuntoForm;
import ieci.tecdoc.isicres.rpadmin.struts.forms.ConfiguracionLdapForm;
import ieci.tecdoc.isicres.rpadmin.struts.forms.InformeForm;
import ieci.tecdoc.isicres.rpadmin.struts.forms.TransporteForm;
import ieci.tecdoc.isicres.rpadmin.struts.forms.UsuarioForm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.DateFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Campo;
import es.ieci.tecdoc.isicres.admin.beans.Campos;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Filtro;
import es.ieci.tecdoc.isicres.admin.beans.Filtros;
import es.ieci.tecdoc.isicres.admin.beans.LibroBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.beans.OrganizacionBean;
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistrador;
import es.ieci.tecdoc.isicres.admin.beans.UsuariosRegistradores;
import es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgDefs;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;
import es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider;
import es.ieci.tecdoc.isicres.api.intercambio.registral.business.util.IntercambioRegistralConfiguration;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralException;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.exception.IntercambioRegistralExceptionCodes;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.ConfiguracionIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;

public class Utils {

	public static final String FORMATO_FECHA = "dd/MM/yyyy";

	public static final int FECHA_MAYOR = 1;
	public static final int FECHA_MENOR = -1;
	public static final int FECHA_IGUAL = 0;


	/**
	 * Expresión Regular para comprobar la fecha. DD/MM/AAAA
	 */
	public static final String REGEXP_FORMATO_FECHA = "(0[1-9]|[12][0-9]|3[01])([/])(0[1-9]|1[012])([/])[0-9]{4}$";


	private static DateFormatter dateFormatter = new DateFormatter(new SimpleDateFormat(FORMATO_FECHA));
	private static final Logger logger = Logger.getLogger(Utils.class);

	public static String formatDate(Date date) {
		try {
			return dateFormatter.valueToString(date);
		} catch (ParseException e) {
			logger.debug("Error al formatear la fecha: " + date);
			return "";
		}
	}

    public final static boolean isFecha(String date) {
    	//Comprobar si cumple el formato de Fecha DD/MM/YYYY
    	Pattern mask = Pattern.compile(REGEXP_FORMATO_FECHA);
		Matcher matcher = mask.matcher(date);

		if (matcher.matches()) {
	    	if (GenericValidator.isDate(date, FORMATO_FECHA, false))
	            return true;
	        return false;
		}
		return false;
    }

    public final static boolean isIP (String ip) {

    	try {

    		InetAddress.getByName(ip);
    		return true;
    	}
		// Excepción retornada si no se trata de una IP válida IPv4 o IPv6
    	catch (UnknownHostException e) {
    		return false;
    	}
    }

	/**
	 * Compara dos fechas de tipo Calendar
	 * @param fecha1 Fecha Base de la Comparación
	 * @param fecha2 Fecha de Comparación
	 * @return Devuelve -1 si fecha1 < fecha2, 0 si fecha1 = fecha2, 1 si fecha1 > fecha2
	 */
	private static int compararFechas(Date fechaBase, Date fechaComparacion)
	{
		GregorianCalendar fecha1 = new GregorianCalendar();
		fecha1.setTime(fechaBase);
		GregorianCalendar fecha2 = new GregorianCalendar();
		fecha2.setTime(fechaComparacion);

		int anio1 = fecha1.get(GregorianCalendar.YEAR);
		int anio2 = fecha2.get(GregorianCalendar.YEAR);
		int mes1 = fecha1.get(GregorianCalendar.MONTH);
		int mes2 = fecha2.get(GregorianCalendar.MONTH);
		int dia1 = fecha1.get(GregorianCalendar.DATE);
		int dia2 = fecha2.get(GregorianCalendar.DATE);

		if (anio1 > anio2)
			return FECHA_MAYOR;
		else if (anio1 < anio2)
			return FECHA_MENOR;

		if (mes1 > mes2)
			return FECHA_MAYOR;
		else if (mes1 < mes2)
			return FECHA_MENOR;

		if (dia1 > dia2)
			return FECHA_MAYOR;
		else if (dia1 < dia2)
			return FECHA_MENOR;

		return FECHA_IGUAL;
	}
	public static Date formatString(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			logger.debug("Error al formatear la fecha: " + date);
		}
		return null;
	}

	public static boolean existeDentroVector(ArrayList v, String strId) {
		boolean bExiste = false;
		for (int i = 0; i < v.size(); i++) {
			String strAux = (String) v.get(i);
			if (strAux.trim().equalsIgnoreCase(strId.trim())) {
				bExiste = true;
				break;
			}
		}
		return bExiste;
	}

	public static boolean isNuloOVacio(Object cadena) {
		if ((cadena == null) || StringUtils.isEmpty(cadena.toString())) {
			return true;
		}
		return false;
	}

	public static boolean isNotNuloOVacio(Object cadena) {
		if ((cadena != null) && StringUtils.isNotEmpty(cadena.toString())) {
			return true;
		}
		return false;
	}

	public static void traducePerfiles(OptionsBean perfiles, Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResource",
				locale);
		for (int i = 0; i < perfiles.count(); i++) {
			OptionBean option = perfiles.get(i);
			option.setDescripcion(bundle.getString(option.getDescripcion()));
		}
	}

	public static void traducePerfiles(UsuariosRegistradores usuarios,
			Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResource",
				locale);
		for (int i = 0; i < usuarios.count(); i++) {
			UsuarioRegistrador usuario = usuarios.get(i);
			if (usuario.getPerfil() != null && !"".equals(usuario.getPerfil())
					&& !"0".equals(usuario.getPerfil())) {
				usuario.setPerfil(bundle.getString(usuario.getPerfil()));
			}
		}
	}

	/***************************************************************************
	 * Método que obtiene los literales del combo de nexos del properties
	 *
	 * @return OptionsBean
	 */
	public static OptionsBean traduceNexos(Locale locale) {

		OptionsBean options = new OptionsBean();
		OptionBean option;

		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource",
				locale);

		option = new OptionBean();
		option.setCodigo("");
		option.setDescripcion("");
		options.add(option);

		option = new OptionBean();
		option.setCodigo(Filtro.NEXO_Y);
		option.setDescripcion(rb
				.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.nexo.y"));
		options.add(option);

		option = new OptionBean();
		option.setCodigo(Filtro.NEXO_O);
		option.setDescripcion(rb
				.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.nexo.o"));
		options.add(option);

		return options;
	}

	public static OptionsBean traduceEstados(Locale locale) {
		OptionsBean options = new OptionsBean();
		OptionBean option;

		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource",
				locale);

		// se mantiene el valor 3, por motivos de compatibilidad, debido a que
		// en versiones anteriores se ha utilizado de forma incorrecta este
		// valor
		option = new OptionBean();
		option.setCodigo(String.valueOf(Filtro.VALOR_ESTADO_NO_VALIDO));
		option.setDescripcion("");
		options.add(option);

		option = new OptionBean();
		option.setCodigo(String.valueOf(Filtro.ESTADO_COMPLETO));
		option
				.setDescripcion(rb
						.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.estados.completo"));
		options.add(option);

		option = new OptionBean();
		option.setCodigo(String.valueOf(Filtro.ESTADO_INCOMPLETO));
		option
				.setDescripcion(rb
						.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.estados.incompleto"));
		options.add(option);

		option = new OptionBean();
		option.setCodigo(String.valueOf(Filtro.ESTADO_RESERVADO));
		option
				.setDescripcion(rb
						.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.estados.reservado"));
		options.add(option);

		option = new OptionBean();
		option.setCodigo(String.valueOf(Filtro.ESTADO_ANULADO));
		option
				.setDescripcion(rb
						.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.estados.anulado"));
		options.add(option);

		option = new OptionBean();
		option.setCodigo(String.valueOf(Filtro.ESTADO_CERRADO));
		option
				.setDescripcion(rb
						.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.estados.cerrado"));
		options.add(option);

		return options;
	}

	/***************************************************************************
	 * Método que obtiene los literales del combo de campos del properties
	 *
	 * @param campos -
	 *            Listado de campos
	 * @param tipoLibro -
	 *            Tipo del libro (1) de entrada (2) de salida
	 * @return Campos
	 */
	public static Campos traduceCampos(int tipoLibro, Campos campos,
			Locale locale) {
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource",
				locale);

		for (int i = 0; i < campos.count(); i++) {
			((Campo) campos.get(i))
					.setDescripcion(rb
							.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.campos."
									+ tipoLibro
									+ "."
									+ ((Campo) campos.get(i)).getId()));
		}
		return campos;
	}

	/***************************************************************************
	 * Método que obtiene los literales para el combo de operadores del
	 * properties
	 *
	 * @param operadores -
	 *            Listado de operadores
	 * @param tipoLibro -
	 *            Tipo del libro (1) de entrada (2) de salida
	 * @return OptionsBean
	 */
	public static OptionsBean traduceOperadores(OptionsBean operadores,
			Locale locale) {
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource",
				locale);

		for (int i = 0; i < operadores.count(); i++) {
			((OptionBean) operadores.get(i))
					.setDescripcion(rb
							.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.operadores."
									+ ((OptionBean) operadores.get(i))
											.getCodigo()));
		}

		return operadores;
	}

	/***************************************************************************
	 * Método que obtiene los literales para el combo de tipo de registro
	 * original
	 *
	 * @param tipoLibro -
	 *            Tipo del libro (1) de entrada (2) de salida
	 * @return OptionsBean
	 */

	public static OptionsBean traduceTipoRegistro(Locale locale) {
		OptionsBean options = new OptionsBean();
		OptionBean option;

		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource",
				locale);

		option = new OptionBean();
		option.setCodigo(String.valueOf(Filtro.TIPO_REGISTRO_ORIGINAL_ENTRADA));
		option
				.setDescripcion(rb
						.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.tipoRegistro.entrada"));
		options.add(option);

		option = new OptionBean();
		option.setCodigo(String.valueOf(Filtro.TIPO_REGISTRO_ORIGINAL_SALIDA));
		option
				.setDescripcion(rb
						.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.tipoRegistro.salida"));
		options.add(option);

		return options;
	}

	/**
	 * Método que obtiene los literales de los diferentes Tipos de Servidor LDAP permitidos
	 * @param locale -
	 * 					Localización de la aplicación
	 * @return OptionsBean -
	 * 					Lista de opciones con los Tipos de Servidor LDAP
	 */
	public static OptionsBean obtenerTiposServidorCombo(Locale locale) {
		OptionsBean options = new OptionsBean();
		OptionBean option;

		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource",
				locale);

//        option = new OptionBean();
//        option.setCodigo(String.valueOf(CfgDefs.LDAP_ENGINE_NONE));
//        option.setDescripcion(rb
//				.getString("ieci.tecdoc.sgm.rpadmin.configuracionLdap.tipoServidor.ninguno"));
//		options.add(option);

	    option = new OptionBean();
	    option.setCodigo(String.valueOf(CfgDefs.LDAP_ENGINE_ACTIVE_DIRECTORY));
        option.setDescripcion(rb
				.getString("ieci.tecdoc.sgm.rpadmin.configuracionLdap.tipoServidor.activeDirectory"));
        options.add(option);

        option = new OptionBean();
        option.setCodigo(String.valueOf(CfgDefs.LDAP_ENGINE_IPLANET));
        option.setDescripcion(rb
				.getString("ieci.tecdoc.sgm.rpadmin.configuracionLdap.tipoServidor.iPlanet"));
		options.add(option);

        option = new OptionBean();
        option.setCodigo(String.valueOf(CfgDefs.LDAP_ENGINE_OPENLDAP));
        option.setDescripcion(rb
				.getString("ieci.tecdoc.sgm.rpadmin.configuracionLdap.tipoServidor.openLdap"));
		options.add(option);

		return options;
	}

	// //FUNCIONES COMUNES A LOS FILTROS
	private static final String CAMPOS_TIPO = "Campos_tipo_";
	private static final String OPERADORES_TIPO = "Operadores_tipo_";

	public static Campos obtenerCampos(ISicresServicioRPAdmin oServicio,
			int tipoLibro, int tipoFiltro, HttpServletRequest request,
			Locale locale) throws Exception {
		Campos campos;
		if (request.getSession(false).getAttribute(CAMPOS_TIPO + tipoLibro) != null) {
			campos = (Campos) request.getSession(false).getAttribute(
					CAMPOS_TIPO + tipoLibro);
		} else {
			//Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			campos = oServicio.obtenerCampos(tipoFiltro, tipoLibro, entidad);
			traduceCampos(tipoLibro, campos, locale);
			request.getSession(false).setAttribute(CAMPOS_TIPO + tipoLibro,
					campos);
		}
		return campos;
	}

	public static OptionsBean obtenerOperadores(int fila, String idCampo,
			int tipoLibro, int tipoFiltro, Filtro filtro,
			ISicresServicioRPAdmin oServicio, HttpServletRequest request, Locale locale)
			throws Exception {

		String tipoCampo = dameTipoCampo(idCampo, tipoLibro);
		OptionsBean operadores;
		if (request.getSession(false).getAttribute(OPERADORES_TIPO + tipoCampo) != null) {
			operadores = (OptionsBean) request.getSession(false).getAttribute(
					OPERADORES_TIPO + tipoCampo);
		} else {
			//Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			operadores = oServicio.obtenerOperadores(tipoCampo, entidad);
			traduceOperadores(operadores, locale);
			request.getSession(false).setAttribute(OPERADORES_TIPO + tipoCampo,
					operadores);
		}
		request.setAttribute("tipoCampo_" + fila, tipoCampo);
		request.setAttribute("idCampo_" + fila, idCampo);
		fillComboBox(tipoLibro, Integer.parseInt(idCampo), fila, filtro,
				request, false, locale);
		return operadores;
	}

	public static String dameTipoCampo(String idCampo, int tipoLibro) {
		if (idCampo == null || "".endsWith(idCampo))
			return "";

		int id = Integer.parseInt(idCampo);
		if (tipoLibro == LibroBean.LIBRO_ENTRADA) {
			switch (id) {
			case Filtro.CAMPO_FECHA_REGISTRO:
			case Filtro.CAMPO_FECHA_TRABAJO:
			case Filtro.CAMPO_FECHA_REGISTRO_ORIGINAL:
				return FiltroImpl.TIPO_DATE;
			case Filtro.CAMPO_ESTADO:
			case Filtro.CAMPO_TIPO_REGISTRO_ORIGINAL:
				return FiltroImpl.TIPO_COMBO;
			case Filtro.CAMPO_OFICINA_REGISTRO:
				return FiltroImpl.TIPO_OFICINAS;
			case Filtro.CAMPO_ORIGEN:
			case Filtro.CAMPO_DESTINO:
				return FiltroImpl.TIPO_UNIDADES_ADMIN;
			case Filtro.CAMPO_TIPO_ASUNTO_ENTRADA:
				return FiltroImpl.TIPO_ASUNTO;
			case Filtro.CAMPO_FECHA_DEL_DOCUMENTO_ENTRADA:
				return FiltroImpl.TIPO_DATE;
			default:
				return Filtro.TIPO_STRING;
			}
		} else {
			switch (id) {
			case Filtro.CAMPO_FECHA_REGISTRO:
			case Filtro.CAMPO_FECHA_TRABAJO:
				return FiltroImpl.TIPO_DATE;
			case Filtro.CAMPO_ESTADO:
				return FiltroImpl.TIPO_COMBO;
			case Filtro.CAMPO_OFICINA_REGISTRO:
				return FiltroImpl.TIPO_OFICINAS;
			case Filtro.CAMPO_ORIGEN:
			case Filtro.CAMPO_DESTINO:
				return FiltroImpl.TIPO_UNIDADES_ADMIN;
			case Filtro.CAMPO_TIPO_ASUNTO_SALIDA:
				return FiltroImpl.TIPO_ASUNTO;
			case Filtro.CAMPO_FECHA_DEL_DOCUMENTO_SALIDA:
				return FiltroImpl.TIPO_DATE;
			default:
				return Filtro.TIPO_STRING;
			}
		}
	}

	public static void fillComboBox(int tipoLibro, int idCampo, int fila,
			Filtro filtro, HttpServletRequest request, boolean cleanValue,
			Locale locale) {

		if (idCampo == 6) {
			OptionsBean options = Utils.traduceEstados(locale);
			if (cleanValue)
				filtro.setValor("");
			request.setAttribute("valores[" + fila + "]", options);
		} else if (idCampo == 11 && tipoLibro == LibroBean.LIBRO_ENTRADA) {
			OptionsBean options = Utils.traduceTipoRegistro(locale);
			if (cleanValue)
				filtro.setValor("");
			request.setAttribute("valores[" + fila + "]", options);
		}
	}

	public static void loadCampos(int tipoLibro, ISicresServicioRPAdmin oServicio,
			HttpServletRequest request, Locale locale) throws Exception {

		// Cargamos el combo de campos
		Campos campos;
		if (request.getSession(false).getAttribute(CAMPOS_TIPO + tipoLibro) != null) {
			campos = (Campos) request.getSession(false).getAttribute(
					CAMPOS_TIPO + tipoLibro);
		} else {
			//Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			campos = oServicio.obtenerCampos(tipoLibro, tipoLibro, entidad);
			campos = Utils.traduceCampos(tipoLibro, campos, locale);
			request.getSession(false).setAttribute(CAMPOS_TIPO + tipoLibro,
					campos);
		}
		request.setAttribute("campos", campos);
	}

	public static Filtros obtenerFiltros(String fila) {

		Filtros filtros = new Filtros();
		Filtro filtro;

		if (fila == null || Integer.parseInt(fila) == 0) {
			filtro = new Filtro();
			filtro.setCampo("");
			filtro.setOperador("");
			filtro.setValor("");
			filtro.setNexo("");
			filtros.add(filtro);

		} else {
			for (int i = 0; i < Integer.parseInt(fila) + 1; i++) {
				filtro = new Filtro();
				filtro.setCampo("");
				filtro.setOperador("");
				filtro.setValor("");
				filtro.setNexo("");
				filtros.add(filtro);
			}
		}

		return filtros;
	}


	public static ActionErrors validarDatosTransporte(TransporteForm transporteForm){
		ActionErrors errores = new ActionErrors();
		boolean datosValidos = true;
		int contador = 0;

		if(transporteForm != null){
			if (isNuloOVacio(transporteForm.getTransport())) {
				datosValidos = false;
				contador++;
				ActionError error = new ActionError(
						"ieci.tecdoc.sgm.rpadmin.transportes.tipo.transporte.required");
				errores.add("Error interno", error);
			}
		}

		if (datosValidos) {
			errores = null;
		}

		return errores;
	}

	public static ActionErrors validarDatosAsunto(AsuntoForm asuntoForm, MessageResources mr, Locale locale){
		ActionErrors errores = new ActionErrors();
		boolean datosValidos = true;
		int contador = 0;

		if(asuntoForm != null){

			if(asuntoForm.getDeshabilitado() != null && asuntoForm.getDeshabilitado().booleanValue()){
				String fechaCreacion = asuntoForm.getFechaCreacion();
				String fechaBaja = asuntoForm.getFechaBaja();

				String campoFechaBaja = mr.getMessage(locale,"ieci.tecdoc.sgm.rpadmin.asuntos.fecha.baja");
				String campoFechaCreacion = mr.getMessage(locale,"ieci.tecdoc.sgm.rpadmin.asuntos.fecha.creacion");


				if(isNuloOVacio(fechaBaja)){
					datosValidos = false;
					contador++;
					ActionError error = new ActionError(
						"errors.required", campoFechaBaja);
					errores.add("Error interno", error);
				}
				else {
					if(isFecha(fechaBaja)){
						//Comprobar que la fecha de baja no sea anterior a la fecha de creación
						if(!isNuloOVacio(fechaCreacion)){
							Date fechaCreacionDate = formatString(fechaCreacion);
							Date fechaBajaDate = formatString(fechaBaja);

							if(compararFechas(fechaBajaDate, fechaCreacionDate) == FECHA_MENOR){
								datosValidos = false;
								contador++;
								ActionError error = new ActionError(
										"errors.date.no.before", campoFechaBaja, campoFechaCreacion);
								errores.add("Error interno", error);
							}
						}
					}
					else{
						datosValidos = false;
						contador++;
						ActionError error = new ActionError(
							"errors.date", campoFechaBaja);
						errores.add("Error interno", error);
					}
				}
			}
		}
		if (datosValidos) {
			errores = null;
		}
		return errores;
	}

	/**
	 * Metodo que comprueba los campos que es obligatorio insertar en la base de
	 * datos para las tablas de usuario
	 *
	 * @param usuarioForm
	 * @return
	 */
	public static ActionErrors validarDatosUsuario(UsuarioForm usuarioForm) {
		ActionErrors errores = new ActionErrors();
		boolean datosValidos = true;
		int contador = 0;
		if (usuarioForm != null) {
			if (isNuloOVacio(usuarioForm.getNombreIdentificacion())) {
				datosValidos = false;
				contador++;
				ActionError error = new ActionError(
						"ieci.tecdoc.sgm.rpadmin.usuarios.identificacion.nombre.required");
				errores.add("Error interno", error);
			}
			if (isNuloOVacio(usuarioForm.getNombre())) {
				datosValidos = false;
				contador++;
				ActionError error = new ActionError(
						"ieci.tecdoc.sgm.rpadmin.usuarios.identificacion.nombre.required");
				errores.add("Error interno", error);
			}

			if (isNuloOVacio(usuarioForm.getPrimerApellido())) {
				datosValidos = false;
				contador++;
				ActionError error = new ActionError(
						"ieci.tecdoc.sgm.rpadmin.usuarios.identificacion.primer.apellido.required");
				errores.add("Error interno", error);
			}



			if((isNotNuloOVacio(usuarioForm.getCodigoPostal()))||(isNotNuloOVacio(usuarioForm.getProvincia()))||
					(isNotNuloOVacio(usuarioForm.getProvincia()))||(isNotNuloOVacio(usuarioForm.getCiudad())) ||
					(isNotNuloOVacio(usuarioForm.getTelefono()))||(isNotNuloOVacio(usuarioForm.getFax())) ||
					(isNotNuloOVacio(usuarioForm.getDireccion())))
			{
				if (isNuloOVacio(usuarioForm.getDireccion()) && (contador < 2)) {
					datosValidos = false;
					contador++;
					ActionError error = new ActionError(
							"ieci.tecdoc.sgm.rpadmin.usuarios.localizacion.direccion.required");
					errores.add("Error interno", error);
				}

				if (isNuloOVacio(usuarioForm.getCiudad()) && (contador < 2)) {
					datosValidos = false;
					contador++;
					ActionError error = new ActionError(
							"ieci.tecdoc.sgm.rpadmin.usuarios.localizacion.ciudad.required");
					errores.add("Error interno", error);
				}
			}

		}

		if (datosValidos) {
			errores = null;
		}

		return errores;
	}

	public static boolean getBooleanValue(int valor){
		if(valor == Constantes.CHECKED_VALUE) return true;
		return false;
	}

	public static int getIntValue(boolean valor){
		if(valor)return Constantes.CHECKED_VALUE;
		else return Constantes.UNCHECKED_VALUE;
	}

	public static boolean isDBCaseSensitive(HttpServletRequest request) {
		String caseSensitive = SesionHelper.obtenerCaseSensitive(request);

		if ((caseSensitive != null) && (caseSensitive.equalsIgnoreCase("CS"))) {
			return true;
		} else {
			return false;
		}


	}

	/**
	 * Metodo estatico que comprueba los datos del formulario para insertar o modificar un informe
	 * @param informeForm
	 * @param mr
	 * @param locale
	 * @param path
	 * @return
	 */
	public static ActionErrors validarDatosInforme(InformeForm informeForm, MessageResources mr, Locale locale, String path){
		ActionErrors errores = new ActionErrors();
		boolean datosValidos = true;
		int contador = 0;

		if(informeForm != null){
			if(isNuloOVacio(informeForm.getDescription())){
				datosValidos = false;
				contador++;
				ActionError error = new ActionError(
				"ieci.tecdoc.sgm.rpadmin.informes.descripcion.required");
				errores.add("Error interno", error);
			}

			if(isNuloOVacio(informeForm.getTypeReport())){
				datosValidos = false;
				contador++;
				ActionError error = new ActionError(
				"ieci.tecdoc.sgm.rpadmin.informes.tipo.required");
				errores.add("Error interno", error);
			}
			if(isNuloOVacio(informeForm.getTypeArch())){
				datosValidos = false;
				contador++;
				ActionError error = new ActionError(
				"ieci.tecdoc.sgm.rpadmin.informes.tipo.archivo.required");
				errores.add("Error interno", error);
			}
			if(!(isNuloOVacio(informeForm.getInformeFile()))){
				if(isNuloOVacio(informeForm.getInformeFile().getFileName())){
					datosValidos = false;
					contador++;
					ActionError error = new ActionError(
					"ieci.tecdoc.sgm.rpadmin.informes.archivo.required");
					errores.add("Error interno", error);
				}
				int size = informeForm.getInformeFile().getFileSize();
				String extension[] = informeForm.getInformeFile().getFileName().split("\\.");
				String exten = extension[extension.length-1].toLowerCase();
				if(size>4194304){
					datosValidos = false;
					contador++;
					ActionError error = new ActionError(
					"ieci.tecdoc.sgm.rpadmin.informes.zip.size");
					errores.add("Error interno", error);
				}else{
					if(exten.toLowerCase().equals(InformesKeys.ZIP)){
						if(isInvalidoContenidoZip(informeForm, path)){
							datosValidos = false;
							contador++;
							ActionError error = new ActionError(
							"ieci.tecdoc.sgm.rpadmin.informes.contenido.illegal");
							errores.add("Error interno", error);
						}
					}else{
						datosValidos = false;
						contador++;
						ActionError error = new ActionError(
						"ieci.tecdoc.sgm.rpadmin.informes.contenido.zip");
						errores.add("Error interno", error);
					}
				}
			}
		}

		if (datosValidos) {
			errores = null;
		}

		return errores;
	}

	/**
	 * Valida el contenido de un fichero zip
	 * @param informeForm
	 * @param path
	 * @return
	 */
	private static boolean isInvalidoContenidoZip(InformeForm informeForm, String path) {
		boolean result= true;
		try{
			String name = System.currentTimeMillis() + "." + InformesKeys.ZIP;
			String fileTxt = path+"//"+InformesKeys.TEMP+"//"+name;
			File f = new File(path+"//"+InformesKeys.TEMP);

			// Comprueba el directorio, si no existe lo crea
			if(!(f.exists())){
				f.mkdir();
			}

			// Escribe el zip en una carpeta temporal
			byte[] archivo = informeForm.getInformeFile().getFileData();
			OutputStream out = new FileOutputStream(fileTxt);
			out.write(archivo);
			out.close();

			// Recorre el zip y lo comprueba
			ZipFile zip;
			f = new File(fileTxt);
			zip = new ZipFile(f);
			Enumeration e = zip.entries();
			while( e.hasMoreElements() ) {
				ZipEntry zen = (ZipEntry) e.nextElement();
				if (zen.isDirectory()){
					continue;
				}
				InputStream zis = zip.getInputStream(zen);
				String extractfile = zen.getName();
				String el[] = extractfile.split("\\.");
				String extension = el[el.length-1].toLowerCase();
				if(extension.toLowerCase().equals(InformesKeys.JRXML)){
					result = false;
				}
				zis.close();
			}
			zip.close();

			// Borra el fichero
			f.delete();

			return result;
		}catch(Exception ex){
			result = true;
			return result;
		}

	}

	/**
	 * Metodo que comprueba los campos que es obligatorio insertar en la base de
	 * datos para configurar Ldap
	 *
	 * @param configuracionLdapForm
	 * @return
	 */
	public static ActionErrors validarDatosConfiguracionLdap(ConfiguracionLdapForm configuracionLdapForm) {
		ActionErrors errores = new ActionErrors();
		boolean datosValidos = true;
		if (configuracionLdapForm != null) {
			if (isNuloOVacio(configuracionLdapForm.getDireccion())) {
				datosValidos = false;
				ActionError error = new ActionError(
						"ieci.tecdoc.sgm.rpadmin.configuracionLdap.direccion.required");
				errores.add("Error interno", error);
//			} else {
//				if (!isIP(configuracionLdapForm.getDireccion())) {
//					datosValidos = false;
//					ActionError error = new ActionError(
//						"ieci.tecdoc.sgm.rpadmin.configuracionLdap.direccion.formatoIncorrecto");
//					errores.add("Error interno", error);
//				}
			}

			if (!isNuloOVacio(configuracionLdapForm.getPuerto())) {
				if (!GenericValidator.isInt(configuracionLdapForm.getPuerto())) {
					datosValidos = false;
					ActionError error = new ActionError(
							"ieci.tecdoc.sgm.rpadmin.configuracionLdap.puerto.formatoIncorrecto");
					errores.add("Error interno", error);
				}
			}

			if (isNuloOVacio(configuracionLdapForm.getNodoRaiz())) {
				datosValidos = false;
				ActionError error = new ActionError(
						"ieci.tecdoc.sgm.rpadmin.configuracionLdap.nodoRaiz.required");
				errores.add("Error interno", error);
			}

			int tipoServidor = Integer.valueOf(configuracionLdapForm.getTipoServidor());

			if (tipoServidor != CfgDefs.LDAP_ENGINE_ACTIVE_DIRECTORY && configuracionLdapForm.getUsarSOAuth()) {
				datosValidos = false;
				ActionError error = new ActionError(
						"ieci.tecdoc.sgm.rpadmin.configuracionLdap.tipoServidor.usarSOAuthIncorrecto");
				errores.add("Error interno", error);
			}
		}

		if (datosValidos) {
			errores = null;
		}

		return errores;
	}

	/**
	 * Método que comprueba si exite relación entre la unidad de tramitación y
	 * la entidad registral indicada en el IR siempre y cuando dicha validación
	 * este activa por configuración intercambioRegistral.properties
	 *
	 * @param unidad
	 */
	public static void validateDatosIR(OrganizacionBean unidad) {
		// comprobamos si esta activa la validación para verificar la relación
		// entre entidad y unidad
		if (IntercambioRegistralConfiguration.getInstance()
				.getActiveValidationRelationEntidadUnidad()) {

			activeValidateDatosIR(unidad);
		}
	}

	/**
	 * Método que comprueba si exite relación entre la unidad de tramitación y la entidad registral indicada en el IR
	 * @param unidad
	 */
	private static void activeValidateDatosIR(OrganizacionBean unidad) {
		// managers a usar
		ConfiguracionIntercambioRegistralManager intercambioManager = IsicresManagerProvider
				.getInstance()
				.getConfiguracionIntercambioRegistralManager();
		// comprobamos si esta rellenada la unid. orgánica y la entidad para
		// el
		// IR
		if (StringUtils.isNotBlank(unidad.getCodeUnidadTramit())
				&& StringUtils.isNotBlank(unidad.getCodEntidadReg())) {
			// si esta rellena, pasamos a comprobar si existe una relación
			// con
			// la entidad registral
			if (!intercambioManager
					.existRelacionUnidOrgaOficina(
							unidad.getCodEntidadReg(),
							unidad.getCodeUnidadTramit())) {
				// al no existir relación entre la unidad y la entidad
				// devolvemos excepción alertando del problema
				throw new IntercambioRegistralException(
						"La unid. de tramitación indicada no se corresponde con la entidad registral",
						IntercambioRegistralExceptionCodes.ERROR_CODE_VALIDACION_UNID_TRAMITA_ENTIDAD_REG);
			}
		}
	}
}