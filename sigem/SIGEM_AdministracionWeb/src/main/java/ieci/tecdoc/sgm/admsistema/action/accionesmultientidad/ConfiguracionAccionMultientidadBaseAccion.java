package ieci.tecdoc.sgm.admsistema.action.accionesmultientidad;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.admsistema.vo.AccionMultientidadVO;
import ieci.tecdoc.sgm.admsistema.vo.OpcionConfiguracionVO;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Clase para para las acciones de multientidad
 * @author IECISA
 *
 */
public class ConfiguracionAccionMultientidadBaseAccion {

	/**
	 * Genera un resumen para una configuracion
	 * @param accionMultientidadVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected String generateResumenConfiguracion(AccionMultientidadVO accionMultientidadVO, HttpServletRequest request) throws Exception {
		ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
		AccionMultientidad accionMultientidad = oServicio.getAccionMultientidad(accionMultientidadVO.getIdAccion());

		ResourceBundle rb = ResourceBundle.getBundle("ieci/tecdoc/sgm/admsistema/resources/AdministracionMessage", request.getLocale());
		StringBuffer resumen = new StringBuffer();
		resumen.append(ABRIR_NEGRITA).append(rb.getString("acciones.multientidad.nombre")).append(SEPARADOR_CLAVE_VALOR).append(CERRAR_NEGRITA)
				.append(accionMultientidad.getNombre()).append(SEPARADOR_LINEA);

		if (!ArrayUtils.isEmpty(accionMultientidadVO.getEntidades())) {
				resumen.append(ABRIR_NEGRITA).append(rb.getString("acciones.multientidad.entidades")).append(SEPARADOR_CLAVE_VALOR).append(CERRAR_NEGRITA)
				.append(getEntidadesString(accionMultientidadVO.getEntidades(), request)).append(SEPARADOR_LINEA);
		}

		if (!ArrayUtils.isEmpty(accionMultientidadVO.getEntidadesOrigen())) {
			resumen.append(ABRIR_NEGRITA).append(rb.getString("acciones.multientidad.entidades.origen")).append(SEPARADOR_CLAVE_VALOR).append(CERRAR_NEGRITA)
			.append(getEntidadesString(accionMultientidadVO.getEntidadesOrigen(), request)).append(SEPARADOR_LINEA);
		}

		if (!ArrayUtils.isEmpty(accionMultientidadVO.getEntidadesDestino())) {
			resumen.append(ABRIR_NEGRITA).append(rb.getString("acciones.multientidad.entidades.destino")).append(SEPARADOR_CLAVE_VALOR).append(CERRAR_NEGRITA)
			.append(getEntidadesString(accionMultientidadVO.getEntidadesDestino(), request)).append(SEPARADOR_LINEA);
		}

		if (StringUtils.isNotEmpty(accionMultientidadVO.getFicheroTemporal())) {
			resumen.append(ABRIR_NEGRITA).append(rb.getString("acciones.multientidad.fichero")).append(SEPARADOR_CLAVE_VALOR).append(CERRAR_NEGRITA)
			.append(accionMultientidadVO.getNombreFicheroTemporal()).append(SEPARADOR_LINEA);
		}

		if (StringUtils.isNotEmpty(accionMultientidadVO.getOpcion())) {
			resumen.append(ABRIR_NEGRITA).append(rb.getString("acciones.multientidad.opcion")).append(SEPARADOR_CLAVE_VALOR).append(CERRAR_NEGRITA)
			.append(getOpcionString(accionMultientidadVO.getOpcion(),accionMultientidadVO.getOpcionesConfiguracion())).append(SEPARADOR_LINEA);
		}

		return resumen.toString();

	}

	/**
	 * Genera el nombre de la opcion de configuracion
	 * @param idsEntidad
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String getOpcionString(String opcion, OpcionConfiguracionVO [] opciones) throws Exception {

		if (opciones!=null&&(opciones.length>0)){
			for (int i=0;i<opciones.length;i++){
				if (opciones[i].getId().equals(opcion)){
					return opciones[i].getLabel();
				}
			}
		}
		return "";
	}

	/**
	 * Genera los nombres de las entidades separados por ,
	 * @param idsEntidad
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private String getEntidadesString(String [] idsEntidad, HttpServletRequest request) throws Exception {
		String usuario = (String)request.getSession().getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);

		ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
		List entidades = Utilidades.entidadesAdministrar(usuario, oServicio.getEntidades(usuario));

		StringBuffer buffer = new StringBuffer();

		if (!ArrayUtils.isEmpty(idsEntidad)&&(entidades!=null)){
			Entidad entidad = null;
			for (int i=0;i<idsEntidad.length;i++){
				Iterator it = entidades.iterator();
				while(it.hasNext()){
					entidad = (Entidad) it.next();
					if (idsEntidad[i].equals(entidad.getIdentificador())){
						buffer.append(entidad.getNombreLargo());
						if (it.hasNext()){
							buffer.append(SEPARADOR_VALORES);
						}
					}
				}
			}
		}

		return buffer.toString();
	}

	private final String SEPARADOR_CLAVE_VALOR = ": ";
	private final String SEPARADOR_VALORES = ", ";
	private final String SEPARADOR_LINEA = "<br/>";
	private final String ABRIR_NEGRITA = "<b>";
	private final String CERRAR_NEGRITA = "</b>";
}
