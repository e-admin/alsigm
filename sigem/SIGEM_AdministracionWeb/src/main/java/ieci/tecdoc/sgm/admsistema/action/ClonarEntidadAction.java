package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.form.BaseDatosx2AndFtpForm;
import ieci.tecdoc.sgm.admsistema.proceso.AccesoBBDD;
import ieci.tecdoc.sgm.admsistema.proceso.clonar.Clonar;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ClonarEntidadAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ClonarEntidadAction.class);

	public static final String FORWARD_CLONAR = "clonar";
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

			try {
				BaseDatosx2AndFtpForm baseDatosx2AndFtpForm = (BaseDatosx2AndFtpForm)form;
				HttpSession session = request.getSession();

				String usuario = (String)request.getSession().getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);

				if (baseDatosx2AndFtpForm == null || Utilidades.esNuloOVacio(baseDatosx2AndFtpForm.getIdEntidadImp()) ||
						Utilidades.esNuloOVacio(baseDatosx2AndFtpForm.getIdEntidadExp())) {
					if (form == null) form = new BaseDatosx2AndFtpForm();
					Entidad entidadExp = (Entidad)session.getAttribute(Defs.PARAMETRO_ENTIDAD_EXPORTAR);
					Entidad entidadImp = (Entidad)session.getAttribute(Defs.PARAMETRO_ENTIDAD_IMPORTAR);
					if (entidadExp == null || entidadImp == null) {
						request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.clonar.error_inesperado");
						return mapping.findForward(FORWARD_ERROR);
					}

					if (Utilidades.permisoAdministrarEntidad(usuario, entidadExp.getIdentificador())) {

						((BaseDatosx2AndFtpForm)form).setIdEntidadExp(entidadExp.getIdentificador());
						((BaseDatosx2AndFtpForm)form).setIdEntidadImp(entidadImp.getIdentificador());
						((BaseDatosx2AndFtpForm)form).setDireccionBaseDatosExp("");
						((BaseDatosx2AndFtpForm)form).setPuertoBaseDatosExp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosExp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosExp("");
						((BaseDatosx2AndFtpForm)form).setPasswordRepetidoBaseDatosExp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosTEExp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosTEExp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosRPExp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosRPExp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosADExp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosADExp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosGEExp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosGEExp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosAuditExp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosAuditExp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosSIRExp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosSIRExp("");
						((BaseDatosx2AndFtpForm)form).setInstanciaExp("");
						((BaseDatosx2AndFtpForm)form).setDireccionBaseDatosImp("");
						((BaseDatosx2AndFtpForm)form).setPuertoBaseDatosImp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosImp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosImp("");
						((BaseDatosx2AndFtpForm)form).setPasswordRepetidoBaseDatosImp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosTEImp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosTEImp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosRPImp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosRPImp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosADImp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosADImp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosGEImp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosGEImp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosAuditImp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosAuditImp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioBaseDatosSIRImp("");
						((BaseDatosx2AndFtpForm)form).setPasswordBaseDatosSIRImp("");
						((BaseDatosx2AndFtpForm)form).setInstanciaImp("");
						((BaseDatosx2AndFtpForm)form).setDireccionFtp("");
						((BaseDatosx2AndFtpForm)form).setPuertoFtp("");
						((BaseDatosx2AndFtpForm)form).setRutaFtp("");
						((BaseDatosx2AndFtpForm)form).setUsuarioFtp("");
						((BaseDatosx2AndFtpForm)form).setPasswordFtp("");
						((BaseDatosx2AndFtpForm)form).setPasswordRepetidoFtp("");
						((BaseDatosx2AndFtpForm)form).setLimpiar(false);

						return mapping.findForward(FORWARD_CLONAR);
					} else {
						logger.debug("El usuario " + usuario + " ha intentado exportar la entidad " + entidadExp.getIdentificador() + " sin tener permisos");
						request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.clonar.error_sin_permiso");
						return mapping.findForward(FORWARD_ERROR);
					}
				} else {
					if (form != null) {
						Hashtable bdData=new Hashtable();
						bdData.put(AccesoBBDD.BD_HOST, baseDatosx2AndFtpForm.getDireccionBaseDatosExp());
						bdData.put(AccesoBBDD.BD_PUERTO, baseDatosx2AndFtpForm.getPuertoBaseDatosExp());

						if (Defs.PLUGIN_BASE_DATOS_POSTGRES.equals(baseDatosx2AndFtpForm.getTipoBaseExp()) ||
							Defs.PLUGIN_BASE_DATOS_SQLSERVER.equals(baseDatosx2AndFtpForm.getTipoBaseExp())) {
							bdData.put(AccesoBBDD.BD_INSTANCE, "");
							bdData.put(AccesoBBDD.BD, "registro");
							bdData.put(AccesoBBDD.BD_USUARIO, baseDatosx2AndFtpForm.getUsuarioBaseDatosExp());
							bdData.put(AccesoBBDD.BD_PASS, baseDatosx2AndFtpForm.getPasswordBaseDatosExp());
						} else {
							bdData.put(AccesoBBDD.BD_INSTANCE, baseDatosx2AndFtpForm.getInstanciaExp());
							bdData.put(AccesoBBDD.BD_USUARIO, baseDatosx2AndFtpForm.getUsuarioBaseDatosRPExp());
							bdData.put(AccesoBBDD.BD_PASS, baseDatosx2AndFtpForm.getPasswordBaseDatosRPExp());
						}

						bdData.put(AccesoBBDD.BD_TIPO, baseDatosx2AndFtpForm.getTipoBaseExp().toLowerCase());
						bdData.put(AccesoBBDD.ID_ENTIDAD, baseDatosx2AndFtpForm.getIdEntidadExp());

						Hashtable hash=AccesoBBDD.recoger(bdData);
						//Al no considerar la ruta esto no vale
						String paths[]=(String[])hash.get(AccesoBBDD.FTP_PATH);
						StringBuffer sb=new StringBuffer();
						sb.append("\"");
						for(int i=0;i<paths.length;i++) {
							sb.append(paths[i]);
							sb.append(" ");
						}
						sb.deleteCharAt(sb.length()-1);
						sb.append("\"");
						String ftpPathExp=sb.toString();

						String limpiar = "1"; //No limpiar
						if (baseDatosx2AndFtpForm.isLimpiar())
							limpiar = "0"; //Limpiar

//						String[][] parametros = new String[][]{
//								{Defs.BD_HOST_EXP, baseDatosx2AndFtpForm.getDireccionBaseDatosExp()},
//								{Defs.BD_PUERTO_EXP, baseDatosx2AndFtpForm.getPuertoBaseDatosExp()},
//								{Defs.BD_USUARIO_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosExp()},
//								{Defs.BD_PASS_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosExp()},
//								{Defs.BD_INSTANCIA_EXP, baseDatosx2AndFtpForm.getInstanciaExp()},
//								{Defs.BD_TIPO_BASE_DATOS_EXP, baseDatosx2AndFtpForm.getTipoBaseExp()},
//								{Defs.BD_USUARIO_AD_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosADExp()},
//								{Defs.BD_PASS_AD_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosADExp()},
//								{Defs.BD_USUARIO_GE_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosGEExp()},
//								{Defs.BD_PASS_GE_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosGEExp()},
//								{Defs.BD_USUARIO_RP_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosRPExp()},
//								{Defs.BD_PASS_RP_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosRPExp()},
//								{Defs.BD_USUARIO_TE_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosTEExp()},
//								{Defs.BD_PASS_TE_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosTEExp()},
//								{Defs.FTP_HOST_EXP, (String)hash.get(AccesoBBDD.FTP_HOST)},
//								{Defs.FTP_PUERTO_EXP, (String)hash.get(AccesoBBDD.FTP_PUERTO)},
//								{Defs.FTP_PATH_EXP, ftpPathExp},
//								{Defs.FTP_USUARIO_EXP, (String)hash.get(AccesoBBDD.FTP_USUARIO)},
//								{Defs.FTP_PASS_EXP, (String)hash.get(AccesoBBDD.FTP_PASS)},
//								{Defs.ID_ENTIDAD_EXP, baseDatosx2AndFtpForm.getIdEntidadExp()},
//								{Defs.BD_HOST_IMP, baseDatosx2AndFtpForm.getDireccionBaseDatosImp()},
//								{Defs.BD_PUERTO_IMP, baseDatosx2AndFtpForm.getPuertoBaseDatosImp()},
//								{Defs.BD_USUARIO_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosImp()},
//								{Defs.BD_PASS_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosImp()},
//								{Defs.BD_INSTANCIA_IMP, baseDatosx2AndFtpForm.getInstanciaImp()},
//								{Defs.BD_TIPO_BASE_DATOS_IMP, baseDatosx2AndFtpForm.getTipoBaseImp()},
//								{Defs.BD_USUARIO_AD_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosADImp()},
//								{Defs.BD_PASS_AD_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosADImp()},
//								{Defs.BD_USUARIO_GE_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosGEImp()},
//								{Defs.BD_PASS_GE_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosGEImp()},
//								{Defs.BD_USUARIO_RP_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosRPImp()},
//								{Defs.BD_PASS_RP_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosRPImp()},
//								{Defs.BD_USUARIO_TE_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosTEImp()},
//								{Defs.BD_PASS_TE_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosTEImp()},
//								{Defs.FTP_HOST_IMP, baseDatosx2AndFtpForm.getDireccionFtp()},
//								{Defs.FTP_PUERTO_IMP, baseDatosx2AndFtpForm.getPuertoFtp()},
//								{Defs.FTP_PATH_IMP, baseDatosx2AndFtpForm.getRutaFtp()},
//								{Defs.FTP_USUARIO_IMP, baseDatosx2AndFtpForm.getUsuarioFtp()},
//								{Defs.FTP_PASS_IMP, baseDatosx2AndFtpForm.getPasswordFtp()},
//								{Defs.ID_ENTIDAD_IMP, baseDatosx2AndFtpForm.getIdEntidadImp()},
//								{Defs.LIMPIAR_BD, limpiar},
//						};

						Map parametros = new HashMap();
						parametros.put(Defs.BD_HOST_EXP, baseDatosx2AndFtpForm.getDireccionBaseDatosExp());
						parametros.put(Defs.BD_PUERTO_EXP, baseDatosx2AndFtpForm.getPuertoBaseDatosExp());
						parametros.put(Defs.BD_USUARIO_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosExp());
						parametros.put(Defs.BD_PASS_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosExp());
						parametros.put(Defs.BD_INSTANCIA_EXP, baseDatosx2AndFtpForm.getInstanciaExp());
						parametros.put(Defs.BD_TIPO_BASE_DATOS_EXP, baseDatosx2AndFtpForm.getTipoBaseExp());
						parametros.put(Defs.BD_USUARIO_AD_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosADExp());
						parametros.put(Defs.BD_PASS_AD_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosADExp());
						parametros.put(Defs.BD_USUARIO_GE_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosGEExp());
						parametros.put(Defs.BD_PASS_GE_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosGEExp());
						parametros.put(Defs.BD_USUARIO_RP_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosRPExp());
						parametros.put(Defs.BD_PASS_RP_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosRPExp());
						parametros.put(Defs.BD_USUARIO_TE_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosTEExp());
						parametros.put(Defs.BD_PASS_TE_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosTEExp());
						parametros.put(Defs.BD_USUARIO_AUDIT_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosAuditExp());
						parametros.put(Defs.BD_PASS_AUDIT_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosAuditExp());
						parametros.put(Defs.BD_USUARIO_SIR_EXP, baseDatosx2AndFtpForm.getUsuarioBaseDatosSIRExp());
						parametros.put(Defs.BD_PASS_SIR_EXP, baseDatosx2AndFtpForm.getPasswordBaseDatosSIRExp());
						parametros.put(Defs.FTP_HOST_EXP, (String)hash.get(AccesoBBDD.FTP_HOST));
						parametros.put(Defs.FTP_PUERTO_EXP, (String)hash.get(AccesoBBDD.FTP_PUERTO));
						//parametros.put(Defs.FTP_PATH_EXP, ftpPathExp);
						parametros.put(Defs.FTP_PATH_EXP, paths);
						parametros.put(Defs.FTP_USUARIO_EXP, (String)hash.get(AccesoBBDD.FTP_USUARIO));
						parametros.put(Defs.FTP_PASS_EXP, (String)hash.get(AccesoBBDD.FTP_PASS));
						parametros.put(Defs.ID_ENTIDAD_EXP, baseDatosx2AndFtpForm.getIdEntidadExp());
						parametros.put(Defs.BD_HOST_IMP, baseDatosx2AndFtpForm.getDireccionBaseDatosImp());
						parametros.put(Defs.BD_PUERTO_IMP, baseDatosx2AndFtpForm.getPuertoBaseDatosImp());
						parametros.put(Defs.BD_USUARIO_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosImp());
						parametros.put(Defs.BD_PASS_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosImp());
						parametros.put(Defs.BD_INSTANCIA_IMP, baseDatosx2AndFtpForm.getInstanciaImp());
						parametros.put(Defs.BD_TIPO_BASE_DATOS_IMP, baseDatosx2AndFtpForm.getTipoBaseImp());
						parametros.put(Defs.BD_USUARIO_AD_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosADImp());
						parametros.put(Defs.BD_PASS_AD_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosADImp());
						parametros.put(Defs.BD_USUARIO_GE_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosGEImp());
						parametros.put(Defs.BD_PASS_GE_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosGEImp());
						parametros.put(Defs.BD_USUARIO_RP_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosRPImp());
						parametros.put(Defs.BD_PASS_RP_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosRPImp());
						parametros.put(Defs.BD_USUARIO_TE_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosTEImp());
						parametros.put(Defs.BD_PASS_TE_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosTEImp());
						parametros.put(Defs.BD_USUARIO_AUDIT_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosAuditImp());
						parametros.put(Defs.BD_PASS_AUDIT_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosAuditImp());
						parametros.put(Defs.BD_USUARIO_SIR_IMP, baseDatosx2AndFtpForm.getUsuarioBaseDatosSIRImp());
						parametros.put(Defs.BD_PASS_SIR_IMP, baseDatosx2AndFtpForm.getPasswordBaseDatosSIRImp());
						parametros.put(Defs.FTP_HOST_IMP, baseDatosx2AndFtpForm.getDireccionFtp());
						parametros.put(Defs.FTP_PUERTO_IMP, baseDatosx2AndFtpForm.getPuertoFtp());
						parametros.put(Defs.FTP_PATH_IMP, baseDatosx2AndFtpForm.getRutaFtp());
						parametros.put(Defs.FTP_USUARIO_IMP, baseDatosx2AndFtpForm.getUsuarioFtp());
						parametros.put(Defs.FTP_PASS_IMP, baseDatosx2AndFtpForm.getPasswordFtp());
						parametros.put(Defs.ID_ENTIDAD_IMP, baseDatosx2AndFtpForm.getIdEntidadImp());
						parametros.put(Defs.LIMPIAR_BD, limpiar);

						ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
						ServicioAdministracion oServicioAdm = LocalizadorServicios.getServicioAdministracion();

						Entidad entidadImp = (Entidad)session.getAttribute(Defs.PARAMETRO_ENTIDAD_IMPORTAR);

						if (Clonar.clonar(parametros)) {
							oServicio.nuevaEntidad(entidadImp);
							oServicioAdm.actualizaPerfiles(Utilidades.obtenerIdentificadoresAplicacaciones(), usuario, entidadImp.getIdentificador());
							request.setAttribute(Defs.MENSAJE_INFORMATIVO, "mensaje.informativo.proceso.clonar.correcto");
							return mapping.findForward(FORWARD_SUCCESS);
						} else {
							oServicioAdm.bajaPerfil(entidadImp.getIdentificador(), usuario);
							oServicio.eliminarEntidad(entidadImp);
						}
					}
					request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.clonar.incorrecto");
					return mapping.findForward(FORWARD_ERROR);
				}
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.clonar.incorrecto");
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
