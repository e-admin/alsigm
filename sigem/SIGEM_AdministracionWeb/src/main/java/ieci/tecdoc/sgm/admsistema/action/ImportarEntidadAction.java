package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.form.BaseDatosAndFtpForm;
import ieci.tecdoc.sgm.admsistema.proceso.importacion.Importar;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ImportarEntidadAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ImportarEntidadAction.class);

	public static final String FORWARD_IMPORTAR = "importar";
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

			try {
				BaseDatosAndFtpForm baseDatosAndFtpForm = (BaseDatosAndFtpForm)form;
				HttpSession session = request.getSession();

				String usuario = (String)session.getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
				if (Utilidades.esNuloOVacio(usuario))
					usuario = "";

				if (baseDatosAndFtpForm == null || Utilidades.esNuloOVacio(baseDatosAndFtpForm.getIdEntidad())) {
					if (form == null) form = new BaseDatosAndFtpForm();
					Entidad entidad = (Entidad)session.getAttribute(Defs.PARAMETRO_ENTIDAD);
					if (entidad == null) {
						request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.importar.error_inesperado");
						return mapping.findForward(FORWARD_ERROR);
					}

					((BaseDatosAndFtpForm)form).setIdEntidad(entidad.getIdentificador());
					((BaseDatosAndFtpForm)form).setDireccionBaseDatos("");
					((BaseDatosAndFtpForm)form).setPuertoBaseDatos("");
					((BaseDatosAndFtpForm)form).setUsuarioBaseDatos("");
					((BaseDatosAndFtpForm)form).setPasswordBaseDatos("");
					((BaseDatosAndFtpForm)form).setPasswordRepetidoBaseDatos("");
					((BaseDatosAndFtpForm)form).setDireccionFtp("");
					((BaseDatosAndFtpForm)form).setPuertoFtp("");
					((BaseDatosAndFtpForm)form).setRutaFtp("");
					((BaseDatosAndFtpForm)form).setUsuarioFtp("");
					((BaseDatosAndFtpForm)form).setPasswordFtp("");
					((BaseDatosAndFtpForm)form).setPasswordRepetidoFtp("");
					((BaseDatosAndFtpForm)form).setIdImportacion("");
					((BaseDatosAndFtpForm)form).setUsuarioBaseDatosTE("");
					((BaseDatosAndFtpForm)form).setPasswordBaseDatosTE("");
					((BaseDatosAndFtpForm)form).setUsuarioBaseDatosRP("");
					((BaseDatosAndFtpForm)form).setPasswordBaseDatosRP("");
					((BaseDatosAndFtpForm)form).setUsuarioBaseDatosAD("");
					((BaseDatosAndFtpForm)form).setPasswordBaseDatosAD("");
					((BaseDatosAndFtpForm)form).setUsuarioBaseDatosGE("");
					((BaseDatosAndFtpForm)form).setPasswordBaseDatosGE("");
					((BaseDatosAndFtpForm)form).setUsuarioBaseDatosAudit("");
					((BaseDatosAndFtpForm)form).setPasswordBaseDatosAudit("");
					((BaseDatosAndFtpForm)form).setUsuarioBaseDatosSIR("");
					((BaseDatosAndFtpForm)form).setPasswordBaseDatosSIR("");
					((BaseDatosAndFtpForm)form).setInstancia("");

					request.setAttribute(Defs.PARAMETRO_DIRECTORIO_IMPORTACION, System.getProperties().getProperty("user.home") + File.separator + Defs.EXPORTAR);
					request.setAttribute(Defs.PARAMETRO_IMPORTACIONES, Importar.obtenerProcesosImportaciones());
					session.setAttribute(Defs.PARAMETRO_ENTIDAD, entidad);

					return mapping.findForward(FORWARD_IMPORTAR);
				} else {
					if (form != null) {

//						String[][] parametros = new String[][]{
//								{Defs.BD_HOST_IMP, baseDatosAndFtpForm.getDireccionBaseDatos()},
//								{Defs.BD_PUERTO_IMP, baseDatosAndFtpForm.getPuertoBaseDatos()},
//								{Defs.BD_USUARIO_IMP, baseDatosAndFtpForm.getUsuarioBaseDatos()},
//								{Defs.BD_PASS_IMP, baseDatosAndFtpForm.getPasswordBaseDatos()},
//								{Defs.BD_INSTANCIA_IMP, baseDatosAndFtpForm.getInstancia()},
//								{Defs.BD_TIPO_BASE_DATOS_IMP, baseDatosAndFtpForm.getTipoBase().toLowerCase()},
//								{Defs.BD_USUARIO_AD_IMP, baseDatosAndFtpForm.getUsuarioBaseDatosAD()},
//								{Defs.BD_PASS_AD_IMP, baseDatosAndFtpForm.getPasswordBaseDatosAD()},
//								{Defs.BD_USUARIO_GE_IMP, baseDatosAndFtpForm.getUsuarioBaseDatosGE()},
//								{Defs.BD_PASS_GE_IMP, baseDatosAndFtpForm.getPasswordBaseDatosGE()},
//								{Defs.BD_USUARIO_RP_IMP, baseDatosAndFtpForm.getUsuarioBaseDatosRP()},
//								{Defs.BD_PASS_RP_IMP, baseDatosAndFtpForm.getPasswordBaseDatosRP()},
//								{Defs.BD_USUARIO_TE_IMP, baseDatosAndFtpForm.getUsuarioBaseDatosTE()},
//								{Defs.BD_PASS_TE_IMP, baseDatosAndFtpForm.getPasswordBaseDatosTE()},
//								{Defs.FTP_HOST_IMP, baseDatosAndFtpForm.getDireccionFtp()},
//								{Defs.FTP_PUERTO_IMP, baseDatosAndFtpForm.getPuertoFtp()},
//								//{Defs.FTP_PATH_IMP, baseDatosAndFtpForm.getRutaFtp()},
//								{Defs.FTP_PATH_IMP, ""},
//								{Defs.FTP_USUARIO_IMP, baseDatosAndFtpForm.getUsuarioFtp()},
//								{Defs.FTP_PASS_IMP, baseDatosAndFtpForm.getPasswordFtp()},
//								{Defs.ID_ENTIDAD_IMP, baseDatosAndFtpForm.getIdEntidad()},
//								{Defs.ID_IMPORTACION, baseDatosAndFtpForm.getIdImportacion()},
//								{Defs.LIMPIAR_BD, "1"} //No limpiar
//						};

						Map parametros = new HashMap();

						parametros.put(Defs.BD_HOST_IMP, baseDatosAndFtpForm.getDireccionBaseDatos());
						parametros.put(Defs.BD_PUERTO_IMP, baseDatosAndFtpForm.getPuertoBaseDatos());
						parametros.put(Defs.BD_USUARIO_IMP, baseDatosAndFtpForm.getUsuarioBaseDatos());
						parametros.put(Defs.BD_PASS_IMP, baseDatosAndFtpForm.getPasswordBaseDatos());
						parametros.put(Defs.BD_INSTANCIA_IMP, baseDatosAndFtpForm.getInstancia());
						parametros.put(Defs.BD_TIPO_BASE_DATOS_IMP, baseDatosAndFtpForm.getTipoBase().toLowerCase());
						parametros.put(Defs.BD_USUARIO_AD_IMP, baseDatosAndFtpForm.getUsuarioBaseDatosAD());
						parametros.put(Defs.BD_PASS_AD_IMP, baseDatosAndFtpForm.getPasswordBaseDatosAD());
						parametros.put(Defs.BD_USUARIO_GE_IMP, baseDatosAndFtpForm.getUsuarioBaseDatosGE());
						parametros.put(Defs.BD_PASS_GE_IMP, baseDatosAndFtpForm.getPasswordBaseDatosGE());
						parametros.put(Defs.BD_USUARIO_RP_IMP, baseDatosAndFtpForm.getUsuarioBaseDatosRP());
						parametros.put(Defs.BD_PASS_RP_IMP, baseDatosAndFtpForm.getPasswordBaseDatosRP());
						parametros.put(Defs.BD_USUARIO_TE_IMP, baseDatosAndFtpForm.getUsuarioBaseDatosTE());
						parametros.put(Defs.BD_PASS_TE_IMP, baseDatosAndFtpForm.getPasswordBaseDatosTE());
						parametros.put(Defs.BD_USUARIO_AUDIT_IMP, baseDatosAndFtpForm.getUsuarioBaseDatosAudit());
						parametros.put(Defs.BD_PASS_AUDIT_IMP, baseDatosAndFtpForm.getPasswordBaseDatosAudit());
						parametros.put(Defs.BD_USUARIO_SIR_IMP, baseDatosAndFtpForm.getUsuarioBaseDatosSIR());
						parametros.put(Defs.BD_PASS_SIR_IMP, baseDatosAndFtpForm.getPasswordBaseDatosSIR());
						parametros.put(Defs.FTP_HOST_IMP, baseDatosAndFtpForm.getDireccionFtp());
						parametros.put(Defs.FTP_PUERTO_IMP, baseDatosAndFtpForm.getPuertoFtp());
						//parametros.put(Defs.FTP_PATH_IMP, baseDatosAndFtpForm.getRutaFtp());
						parametros.put(Defs.FTP_PATH_IMP, "");
						parametros.put(Defs.FTP_USUARIO_IMP, baseDatosAndFtpForm.getUsuarioFtp());
						parametros.put(Defs.FTP_PASS_IMP, baseDatosAndFtpForm.getPasswordFtp());
						parametros.put(Defs.ID_ENTIDAD_IMP, baseDatosAndFtpForm.getIdEntidad());
						parametros.put(Defs.ID_IMPORTACION, baseDatosAndFtpForm.getIdImportacion());
						parametros.put(Defs.LIMPIAR_BD, "1"); //No limpiar

						ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
						ServicioAdministracion oServicioAdm = LocalizadorServicios.getServicioAdministracion();

						Entidad entidad = (Entidad)session.getAttribute(Defs.PARAMETRO_ENTIDAD);

						if (Importar.importar(parametros)) {
							oServicio.nuevaEntidad(entidad);
							oServicioAdm.actualizaPerfiles(Utilidades.obtenerIdentificadoresAplicacaciones(), usuario, entidad.getIdentificador());
							request.setAttribute(Defs.MENSAJE_INFORMATIVO, "mensaje.informativo.proceso.importar.correcto");
							return mapping.findForward(FORWARD_SUCCESS);
						}else {
							oServicioAdm.bajaPerfil(entidad.getIdentificador(), usuario);
							oServicio.eliminarEntidad(entidad);
						}
					}
					request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.importar.error_inesperado");
					return mapping.findForward(FORWARD_ERROR);
				}
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.importar.error_inesperado");
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
