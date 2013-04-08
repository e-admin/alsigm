package ieci.tecdoc.sgm.admsistema.action;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import ieci.tecdoc.sgm.admsistema.form.BaseDatosForm;
import ieci.tecdoc.sgm.admsistema.proceso.AccesoBBDD;
import ieci.tecdoc.sgm.admsistema.proceso.borrar.Borrar;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.administracion.Usuario;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BorrarEntidadAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(BorrarEntidadAction.class);

	public static final String FORWARD_BORRAR = "borrar";
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

			try {
				BaseDatosForm baseDatosForm = (BaseDatosForm)form;

				String usuario = (String)request.getSession().getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);

				if (baseDatosForm == null || Utilidades.esNuloOVacio(baseDatosForm.getIdEntidad())) {
					if (form == null) form = new BaseDatosForm();
					Entidad entidad = (Entidad)request.getAttribute(Defs.PARAMETRO_ENTIDAD);
					if (entidad == null) {
						request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.borrar.error_inesperado");
						return mapping.findForward(FORWARD_ERROR);
					}

					if (Utilidades.permisoAdministrarEntidad(usuario, entidad.getIdentificador())) {

						((BaseDatosForm)form).setIdEntidad(entidad.getIdentificador());
						((BaseDatosForm)form).setDireccionBaseDatos("");
						((BaseDatosForm)form).setPuertoBaseDatos("");
						((BaseDatosForm)form).setUsuarioBaseDatos("");
						((BaseDatosForm)form).setPasswordBaseDatos("");
						((BaseDatosForm)form).setPasswordRepetidoBaseDatos("");
						((BaseDatosForm)form).setUsuarioBaseDatosTE("");
						((BaseDatosForm)form).setPasswordBaseDatosTE("");
						((BaseDatosForm)form).setUsuarioBaseDatosRP("");
						((BaseDatosForm)form).setPasswordBaseDatosRP("");
						((BaseDatosForm)form).setUsuarioBaseDatosAD("");
						((BaseDatosForm)form).setPasswordBaseDatosAD("");
						((BaseDatosForm)form).setUsuarioBaseDatosGE("");
						((BaseDatosForm)form).setPasswordBaseDatosGE("");
						((BaseDatosForm)form).setUsuarioBaseDatosAudit("");
						((BaseDatosForm)form).setPasswordBaseDatosAudit("");
						((BaseDatosForm)form).setUsuarioBaseDatosSIR("");
						((BaseDatosForm)form).setPasswordBaseDatosSIR("");
						((BaseDatosForm)form).setInstancia("");

						return mapping.findForward(FORWARD_BORRAR);
					} else {
						logger.debug("El usuario " + usuario + " ha intentado borrar la entidad " + entidad.getIdentificador() + " sin tener permisos");
						request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.borrar.error_sin_permiso");
						return mapping.findForward(FORWARD_ERROR);
					}
				} else {
					if (form != null) {
						Hashtable bdData=new Hashtable();
						bdData.put(AccesoBBDD.BD_HOST, baseDatosForm.getDireccionBaseDatos());
						bdData.put(AccesoBBDD.BD_PUERTO, baseDatosForm.getPuertoBaseDatos());

						if (Defs.PLUGIN_BASE_DATOS_POSTGRES.equals(baseDatosForm.getTipoBase()) ||
							Defs.PLUGIN_BASE_DATOS_SQLSERVER.equals(baseDatosForm.getTipoBase()) ) {
							bdData.put(AccesoBBDD.BD_INSTANCE, "");
							bdData.put(AccesoBBDD.BD, "registro");
							bdData.put(AccesoBBDD.BD_USUARIO, baseDatosForm.getUsuarioBaseDatos());
							bdData.put(AccesoBBDD.BD_PASS, baseDatosForm.getPasswordBaseDatos());
						} else {
							bdData.put(AccesoBBDD.BD_INSTANCE, baseDatosForm.getInstancia());
							bdData.put(AccesoBBDD.BD_USUARIO, baseDatosForm.getUsuarioBaseDatosRP());
							bdData.put(AccesoBBDD.BD_PASS, baseDatosForm.getPasswordBaseDatosRP());
						}

						bdData.put(AccesoBBDD.BD_TIPO, baseDatosForm.getTipoBase().toLowerCase());
						bdData.put(AccesoBBDD.ID_ENTIDAD, baseDatosForm.getIdEntidad());

						Hashtable hash=AccesoBBDD.recoger(bdData);
						String paths[]=(String[])hash.get(AccesoBBDD.FTP_PATH);
						StringBuffer sb=new StringBuffer();
						sb.append("\"");
						for(int i=0;i<paths.length;i++) {
							sb.append(paths[i]);
							sb.append(" ");
						}
						sb.deleteCharAt(sb.length()-1);
						sb.append("\"");
						String ftpPath=sb.toString();

//						String[][] parametros = new String[][]{
//								{Defs.BD_HOST_EXP, baseDatosForm.getDireccionBaseDatos()},
//								{Defs.BD_PUERTO_EXP, baseDatosForm.getPuertoBaseDatos()},
//								{Defs.BD_USUARIO_EXP, baseDatosForm.getUsuarioBaseDatos()},
//								{Defs.BD_PASS_EXP, baseDatosForm.getPasswordBaseDatos()},
//								{Defs.BD_INSTANCIA_EXP, baseDatosForm.getInstancia()},
//								{Defs.BD_TIPO_BASE_DATOS_EXP, baseDatosForm.getTipoBase()},
//								{Defs.BD_USUARIO_AD_EXP, baseDatosForm.getUsuarioBaseDatosAD()},
//								{Defs.BD_PASS_AD_EXP, baseDatosForm.getPasswordBaseDatosAD()},
//								{Defs.BD_USUARIO_GE_EXP, baseDatosForm.getUsuarioBaseDatosGE()},
//								{Defs.BD_PASS_GE_EXP, baseDatosForm.getPasswordBaseDatosGE()},
//								{Defs.BD_USUARIO_RP_EXP, baseDatosForm.getUsuarioBaseDatosRP()},
//								{Defs.BD_PASS_RP_EXP, baseDatosForm.getPasswordBaseDatosRP()},
//								{Defs.BD_USUARIO_TE_EXP, baseDatosForm.getUsuarioBaseDatosTE()},
//								{Defs.BD_PASS_TE_EXP, baseDatosForm.getPasswordBaseDatosTE()},
//								{Defs.FTP_HOST_EXP, (String)hash.get(AccesoBBDD.FTP_HOST)},
//								{Defs.FTP_PUERTO_EXP, (String)hash.get(AccesoBBDD.FTP_PUERTO)},
//								{Defs.FTP_PATH_EXP, ftpPath},
//								{Defs.FTP_USUARIO_EXP, (String)hash.get(AccesoBBDD.FTP_USUARIO)},
//								{Defs.FTP_PASS_EXP, (String)hash.get(AccesoBBDD.FTP_PASS)},
//								{Defs.ID_ENTIDAD_EXP, baseDatosForm.getIdEntidad()}
//						};

						Map parametros = new HashMap();
						parametros.put(Defs.BD_HOST_EXP, baseDatosForm.getDireccionBaseDatos());
						parametros.put(Defs.BD_PUERTO_EXP, baseDatosForm.getPuertoBaseDatos());
						parametros.put(Defs.BD_USUARIO_EXP, baseDatosForm.getUsuarioBaseDatos());
						parametros.put(Defs.BD_PASS_EXP, baseDatosForm.getPasswordBaseDatos());
						parametros.put(Defs.BD_INSTANCIA_EXP, baseDatosForm.getInstancia());
						parametros.put(Defs.BD_TIPO_BASE_DATOS_EXP, baseDatosForm.getTipoBase());
						parametros.put(Defs.BD_USUARIO_AD_EXP, baseDatosForm.getUsuarioBaseDatosAD());
						parametros.put(Defs.BD_PASS_AD_EXP, baseDatosForm.getPasswordBaseDatosAD());
						parametros.put(Defs.BD_USUARIO_GE_EXP, baseDatosForm.getUsuarioBaseDatosGE());
						parametros.put(Defs.BD_PASS_GE_EXP, baseDatosForm.getPasswordBaseDatosGE());
						parametros.put(Defs.BD_USUARIO_RP_EXP, baseDatosForm.getUsuarioBaseDatosRP());
						parametros.put(Defs.BD_PASS_RP_EXP, baseDatosForm.getPasswordBaseDatosRP());
						parametros.put(Defs.BD_USUARIO_TE_EXP, baseDatosForm.getUsuarioBaseDatosTE());
						parametros.put(Defs.BD_PASS_TE_EXP, baseDatosForm.getPasswordBaseDatosTE());
						parametros.put(Defs.BD_USUARIO_AUDIT_EXP, baseDatosForm.getUsuarioBaseDatosAudit());
						parametros.put(Defs.BD_PASS_AUDIT_EXP, baseDatosForm.getPasswordBaseDatosAudit());
						parametros.put(Defs.BD_USUARIO_SIR_EXP, baseDatosForm.getUsuarioBaseDatosSIR());
						parametros.put(Defs.BD_PASS_SIR_EXP, baseDatosForm.getPasswordBaseDatosSIR());
						parametros.put(Defs.FTP_HOST_EXP, (String)hash.get(AccesoBBDD.FTP_HOST));
						parametros.put(Defs.FTP_PUERTO_EXP, (String)hash.get(AccesoBBDD.FTP_PUERTO));
						parametros.put(Defs.FTP_PATH_EXP, paths);
						parametros.put(Defs.FTP_USUARIO_EXP, (String)hash.get(AccesoBBDD.FTP_USUARIO));
						parametros.put(Defs.FTP_PASS_EXP, (String)hash.get(AccesoBBDD.FTP_PASS));
						parametros.put(Defs.ID_ENTIDAD_EXP, baseDatosForm.getIdEntidad());

						if (Borrar.borrar(parametros)) {
							ServicioAdministracion oServicioAdm = LocalizadorServicios.getServicioAdministracion();
							ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();

							Usuario[] usuarios = oServicioAdm.getUsuariosEntidad(baseDatosForm.getIdEntidad());
							if (usuarios != null) {
								for(int k=0; k<usuarios.length; k++) {
									try {
										oServicioAdm.bajaPerfil(baseDatosForm.getIdEntidad(), usuarios[k].getUsuario());
									} catch(Exception e) {
										logger.error("No se han podido eliminar los permisos del usuario en la entidad borrada", e.getCause());
									}
									Entidad[] entidades = oServicioAdm.getEntidades(usuarios[k].getUsuario());
									if (entidades == null || entidades.length == 0)
										oServicioAdm.bajaUsuario(usuarios[k]);
								}
							}
							Entidad entidad = new Entidad();
							entidad.setIdentificador(baseDatosForm.getIdEntidad());
							oServicio.eliminarEntidad(entidad);
							request.setAttribute(Defs.MENSAJE_INFORMATIVO, "mensaje.informativo.proceso.borrar.correcto");
							return mapping.findForward(FORWARD_SUCCESS);
						} else {
							request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.borrar.incorrecto");
							return mapping.findForward(FORWARD_ERROR);
						}
					}
				}
				request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.borrar.error_inesperado");
				return mapping.findForward(FORWARD_ERROR);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.borrar.error_inesperado");
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
