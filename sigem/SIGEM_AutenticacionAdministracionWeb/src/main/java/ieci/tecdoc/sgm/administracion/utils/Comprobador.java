package ieci.tecdoc.sgm.administracion.utils;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.Aplicacion;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.administracion.Usuario;
import ieci.tecdoc.sgm.core.services.admsesion.administracion.ServicioAdministracionSesionesAdministrador;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativa;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativaLdap;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.CriterioBusquedaUsuarios;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.DatosUsuario;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Comprobador {
	private final static String FORWARD_LOGIN = "login";
	private final static String FORWARD_ENTIDADES = "entidades";
	private final static String FORWARD_APLICACIONES = "aplicaciones";
	private final static String FORWARD_REDIRIGIR = "redirigir";

	public static String comprobarInformacionCertificado(
			HttpServletRequest request, String key, String idEntidad,
			String idAplicacion, DatosUsuario user, boolean validado)
			throws Exception {

		ServicioAdministracion oServicioAdm = LocalizadorServicios
				.getServicioAdministracion();

		// Key de sesion válido
		if (!Utilidades.isNuloOVacio(key)
				&& AutenticacionAdministracion.autenticar(request)) {
			ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
					.getServicioAdministracionSesionesAdministrador();
			ieci.tecdoc.sgm.core.services.admsesion.administracion.Sesion datosSesion = oCliente
					.obtenerSesion(key);
			// Datos de sesion válidos
			if (datosSesion != null) {
				int idUsuario = Utilidades.obtenerIdUsuario(datosSesion
						.getDatosEspecificos());
				datosSesion = oCliente.obtenerSesionEntidad(key + "_"
						+ idEntidad);
				String keyEntidad = null;
				// Sesion de entidad ya creada
				if (datosSesion == null) {
					keyEntidad = oCliente.nuevaSesionEntidad(key, idEntidad);
					datosSesion = oCliente.obtenerSesionEntidad(keyEntidad);
				} else {
					keyEntidad = datosSesion.getUsuario() + "_"
							+ datosSesion.getIdSesion() + "_"
							+ datosSesion.getIdEntidad();
				}
				// Id de aplicacion válido
				if (!Utilidades.isNuloOVacio(idAplicacion)) {
					// Usuario con acceso a la aplicacion de la entidad
					if (Utilidades.permisosAplicacionInternos(idUsuario,
							idEntidad, idAplicacion)
							|| ConstantesGestionUsuariosAdministracion.APLICACION_ARCHIVO
									.equals(idAplicacion)
							|| ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_PROCEDIMIENTOS
									.equals(idAplicacion)) {
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
										key);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
										keyEntidad);
						Aplicacion aplicacion = oServicioAdm
								.getAplicacion(idAplicacion);
						String Url = Utilidades.obtenerUrlAplicacion(request,
								aplicacion);
						request.setAttribute(Defs.PARAMETRO_URL, Url);
						return FORWARD_REDIRIGIR;
					}
					// Usuario sin acceso a la aplicacion
					else {
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
										key);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
										keyEntidad);
						request.setAttribute(Defs.MENSAJE_ERROR,
								"mensaje.error.sin_permisos_aplicacion");
						Aplicacion[] aplicaciones = Utilidades
								.obtenerAplicacionesInterno(idUsuario,
										idEntidad);
						if (aplicaciones == null)
							aplicaciones = new Aplicacion[0];
						request.setAttribute(Defs.PARAMETRO_APLICACIONES,
								aplicaciones);
						return FORWARD_APLICACIONES;
					}
				}
				// Id de aplicación no válido
				else {
					request.getSession()
							.setAttribute(
									ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
									key);
					request.getSession()
							.setAttribute(
									ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
									keyEntidad);
					Aplicacion[] aplicaciones = Utilidades
							.obtenerAplicacionesInterno(idUsuario, idEntidad);
					if (aplicaciones == null)
						aplicaciones = new Aplicacion[0];
					request.setAttribute(Defs.PARAMETRO_APLICACIONES,
							aplicaciones);
					return FORWARD_APLICACIONES;
				}
			}
			// Datos de sesion no validos
			else {
				request.getSession().setAttribute(
						Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(false));
				return FORWARD_LOGIN;
			}
		}
		// Key de sesión no válido
		else {

			// datosUsuario.setPassword(password);
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador(idEntidad);
			// Usuario válido
			if (validado) {
				request.getSession().setAttribute(
						Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(true));
				// Id de aplicacion válido
				if (!Utilidades.isNuloOVacio(idAplicacion)) {
					// Usuario con acceso a la aplicacion de la entidad
					if (Utilidades.permisosAplicacionInternos(new Integer(
							user.getId()), idEntidad, idAplicacion)
							|| ConstantesGestionUsuariosAdministracion.APLICACION_ARCHIVO
									.equals(idAplicacion)
							|| ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_PROCEDIMIENTOS
									.equals(idAplicacion)) {
						// Crear nueva sesion
						ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
								.getServicioAdministracionSesionesAdministrador();
						key = oCliente.nuevaSesion(user.getUser(),
								Sesion.TIPO_USUARIO_INTERNO);
						oCliente.modificarDatosSesion(key,
								"<IdUsuario>" + user.getId()
										+ "</IdUsuario><NombreUsuario>"
										+ user.getName() + " "
										+ user.getLastname()
										+ "</NombreUsuario>");
						String keyEntidad = oCliente.nuevaSesionEntidad(key,
								idEntidad);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
										key);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
										keyEntidad);
						Aplicacion aplicacion = oServicioAdm
								.getAplicacion(idAplicacion);
						String Url = Utilidades.obtenerUrlAplicacion(request,
								aplicacion);
						request.setAttribute(Defs.PARAMETRO_URL, Url);

						return FORWARD_REDIRIGIR;
					}
					// Usuario sin acceso a la aplicacion
					else {
						ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
								.getServicioAdministracionSesionesAdministrador();
						key = oCliente.nuevaSesion(user.getUser(),
								Sesion.TIPO_USUARIO_INTERNO);
						oCliente.modificarDatosSesion(key,
								"<IdUsuario>" + user.getId()
										+ "</IdUsuario><NombreUsuario>"
										+ user.getName() + " "
										+ user.getLastname()
										+ "</NombreUsuario>");
						String keyEntidad = oCliente.nuevaSesionEntidad(key,
								idEntidad);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
										key);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
										keyEntidad);
						request.setAttribute(Defs.MENSAJE_ERROR,
								"mensaje.error.sin_permisos_aplicacion");
						Aplicacion[] aplicaciones = Utilidades
								.obtenerAplicacionesInterno(new Integer(
										user.getId()).intValue(),
										idEntidad);
						if (aplicaciones == null)
							aplicaciones = new Aplicacion[0];
						request.setAttribute(Defs.PARAMETRO_APLICACIONES,
								aplicaciones);
						return FORWARD_APLICACIONES;
					}
				}
				// Id de aplicación no válido
				else {
					ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
							.getServicioAdministracionSesionesAdministrador();
					key = oCliente.nuevaSesion(user.getUser(),
							Sesion.TIPO_USUARIO_INTERNO);
					oCliente.modificarDatosSesion(key,
							"<IdUsuario>" + user.getId()
									+ "</IdUsuario><NombreUsuario>"
									+ user.getName() + " "
									+ user.getLastname()
									+ "</NombreUsuario>");
					String keyEntidad = oCliente.nuevaSesionEntidad(key,
							idEntidad);
					request.getSession()
							.setAttribute(
									ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
									key);
					request.getSession()
							.setAttribute(
									ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
									keyEntidad);
					Aplicacion[] aplicaciones = Utilidades
							.obtenerAplicacionesInterno(new Integer(
									user.getId()).intValue(), idEntidad);
					if (aplicaciones == null)
						aplicaciones = new Aplicacion[0];
					request.setAttribute(Defs.PARAMETRO_APLICACIONES,
							aplicaciones);
					return FORWARD_APLICACIONES;
				}
			}
			// Usuario no válido
			else {
				if (!validado && !Utilidades.isNuloOVacio(user.getUser()))
					request.setAttribute(Defs.MENSAJE_ERROR,
							"mensaje.error.usuario_no_valido");
				request.getSession().setAttribute(
						Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(false));
				return FORWARD_LOGIN;
			}
		}
	}

	public static String comprobarInformacion(HttpServletRequest request,
			String key, String idEntidad, String idAplicacion, String usuario,
			String password, boolean validado) throws Exception {
		ServicioAdministracion oServicio = LocalizadorServicios
				.getServicioAdministracion();

		// Key de sesion válido
		if (!Utilidades.isNuloOVacio(key)
				&& AutenticacionAdministracion.autenticar(request)) {
			ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
					.getServicioAdministracionSesionesAdministrador();
			ieci.tecdoc.sgm.core.services.admsesion.administracion.Sesion datosSesion = oCliente
					.obtenerSesion(key);
			// Datos de sesion válidos
			if (datosSesion != null) {
				// Id de entidad válido
				if (!Utilidades.isNuloOVacio(idEntidad)) {
					// Usuario con acceso a la entidad
					if (Utilidades.permisosEntidad(datosSesion.getUsuario(),
							idEntidad)) {
						datosSesion = oCliente.obtenerSesionEntidad(key + "_"
								+ idEntidad);
						String keyEntidad = null;
						// Sesion de entidad ya creada
						if (datosSesion == null) {
							keyEntidad = oCliente.nuevaSesionEntidad(key,
									idEntidad);
							datosSesion = oCliente
									.obtenerSesionEntidad(keyEntidad);
						} else {
							keyEntidad = datosSesion.getUsuario() + "_"
									+ datosSesion.getIdSesion() + "_"
									+ datosSesion.getIdEntidad();
						}
						// Id de aplicacion válido
						if (!Utilidades.isNuloOVacio(idAplicacion)) {
							// Usuario con acceso a la aplicacion de la entidad
							if (Utilidades.permisosAplicacion(
									datosSesion.getUsuario(), idEntidad,
									idAplicacion)) {
								request.getSession()
										.setAttribute(
												ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
												key);
								request.getSession()
										.setAttribute(
												ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
												keyEntidad);
								Aplicacion aplicacion = oServicio
										.getAplicacion(idAplicacion);
								String Url = Utilidades.obtenerUrlAplicacion(
										request, aplicacion);
								request.setAttribute(Defs.PARAMETRO_URL, Url);
								return FORWARD_REDIRIGIR;
							}
							// Usuario sin acceso a la aplicacion
							else {
								request.getSession()
										.setAttribute(
												ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
												key);
								request.getSession()
										.setAttribute(
												ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
												keyEntidad);
								request.setAttribute(Defs.MENSAJE_ERROR,
										"mensaje.error.sin_permisos_aplicacion");
								Aplicacion[] aplicaciones = oServicio
										.getAplicaciones(
												datosSesion.getUsuario(),
												idEntidad);
								if (aplicaciones == null)
									aplicaciones = new Aplicacion[0];
								request.setAttribute(
										Defs.PARAMETRO_APLICACIONES,
										aplicaciones);
								return FORWARD_APLICACIONES;
							}
						}
						// Id de aplicación no válido
						else {
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
											key);
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
											keyEntidad);
							Aplicacion[] aplicaciones = oServicio
									.getAplicaciones(datosSesion.getUsuario(),
											idEntidad);
							if (aplicaciones == null)
								aplicaciones = new Aplicacion[0];
							request.setAttribute(Defs.PARAMETRO_APLICACIONES,
									aplicaciones);
							return FORWARD_APLICACIONES;
						}
					}
					// Usuario sin aceso a la entidad
					else {
						request.setAttribute(Defs.MENSAJE_ERROR,
								"mensaje.error.sin_permisos_entidad");
						Entidad[] entidades = oServicio
								.getEntidades(datosSesion.getUsuario());
						if (entidades.length != 1) {
							request.setAttribute(Defs.PARAMETRO_ENTIDADES,
									entidades);
							return FORWARD_ENTIDADES;
						} else {
							idEntidad = entidades[0].getIdentificador();
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD,
											idEntidad);
							key = oCliente.nuevaSesion(usuario,
									Sesion.TIPO_USUARIO_ADMINISTRADOR);
							Usuario oUsuario = oServicio
									.obtenerUsuario(usuario);
							oCliente.modificarDatosSesion(
									key,
									oUsuario.getNombre() + " "
											+ oUsuario.getApellidos());
							String keyEntidad = oCliente.nuevaSesionEntidad(
									key, idEntidad);
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
											key);
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
											keyEntidad);

							if (!Utilidades.isNuloOVacio(idAplicacion)) {
								if (Utilidades.permisosAplicacion(usuario,
										idEntidad, idAplicacion)) {
									Aplicacion aplicacion = oServicio
											.getAplicacion(idAplicacion);
									String Url = Utilidades
											.obtenerUrlAplicacion(request,
													aplicacion);
									request.setAttribute(Defs.PARAMETRO_URL,
											Url);
									return FORWARD_REDIRIGIR;
								}
							}
							Aplicacion[] aplicaciones = oServicio
									.getAplicaciones(usuario, idEntidad);
							if (aplicaciones == null)
								aplicaciones = new Aplicacion[0];
							request.setAttribute(Defs.PARAMETRO_APLICACIONES,
									aplicaciones);
							return FORWARD_APLICACIONES;
						}
					}
				}
				// Id de entidad no válido
				else {
					// Id de aplicacion válido y es la aplicacion de
					// administracion de entidades
					if (!Utilidades.isNuloOVacio(idAplicacion)
							&& ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION
									.equals(idAplicacion)) {
						// Si tiene permiso para administrar entidades
						if (Utilidades.accesoAdministracion(datosSesion
								.getUsuario())) {
							Aplicacion aplicacion = oServicio
									.getAplicacion(idAplicacion);
							String Url = Utilidades.obtenerUrlAplicacion(
									request, aplicacion);
							request.setAttribute(Defs.PARAMETRO_URL, Url);
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
											key);
							return FORWARD_REDIRIGIR;
						}
						// Si no tiene permiso para administrar entidades
						else {
							request.setAttribute(Defs.MENSAJE_ERROR,
									"mensaje.error.sin_permisos_administracion");
						}
					}
					// Id de aplicación vacio o no es aplicación de
					// administracion de entidades
					Entidad[] entidades = oServicio.getEntidades(datosSesion
							.getUsuario());
					if (entidades.length != 1) {
						request.setAttribute(Defs.PARAMETRO_ENTIDADES,
								entidades);
						return FORWARD_ENTIDADES;
					} else {
						idEntidad = entidades[0].getIdentificador();
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD,
										idEntidad);
						key = oCliente.nuevaSesion(usuario,
								Sesion.TIPO_USUARIO_ADMINISTRADOR);
						Usuario oUsuario = oServicio.obtenerUsuario(usuario);
						oCliente.modificarDatosSesion(key, oUsuario.getNombre()
								+ " " + oUsuario.getApellidos());
						String keyEntidad = oCliente.nuevaSesionEntidad(key,
								idEntidad);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
										key);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
										keyEntidad);

						if (!Utilidades.isNuloOVacio(idAplicacion)) {
							if (Utilidades.permisosAplicacion(usuario,
									idEntidad, idAplicacion)) {
								Aplicacion aplicacion = oServicio
										.getAplicacion(idAplicacion);
								String Url = Utilidades.obtenerUrlAplicacion(
										request, aplicacion);
								request.setAttribute(Defs.PARAMETRO_URL, Url);
								return FORWARD_REDIRIGIR;
							}
						}
						Aplicacion[] aplicaciones = oServicio.getAplicaciones(
								usuario, idEntidad);
						if (aplicaciones == null)
							aplicaciones = new Aplicacion[0];
						request.setAttribute(Defs.PARAMETRO_APLICACIONES,
								aplicaciones);
						return FORWARD_APLICACIONES;
					}
				}
			}
			// Datos de sesion no validos
			else {
				request.getSession().setAttribute(
						Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(false));
				return FORWARD_LOGIN;
			}
		}
		// Key de sesion no válido
		else {
			// Usuario ya validado o nuevo usuario introducido válido
			if (validado
					|| (!Utilidades.isNuloOVacio(usuario)
							&& !Utilidades.isNuloOVacio(password) && oServicio
								.autenticaUsuario(usuario, password))) {
				request.getSession().setAttribute(
						Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(true));
				// Id de entidad válido
				if (!Utilidades.isNuloOVacio(idEntidad)) {
					// Usuario con acceso a la entidad
					if (Utilidades.permisosEntidad(usuario, idEntidad)) {
						// Id de aplicacion válido
						if (!Utilidades.isNuloOVacio(idAplicacion)) {
							// Usuario con acceso a la aplicacion de la entidad
							if (Utilidades.permisosAplicacion(usuario,
									idEntidad, idAplicacion)) {
								// Crear nueva sesion
								ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
										.getServicioAdministracionSesionesAdministrador();
								key = oCliente.nuevaSesion(usuario,
										Sesion.TIPO_USUARIO_ADMINISTRADOR);
								Usuario oUsuario = oServicio
										.obtenerUsuario(usuario);
								oCliente.modificarDatosSesion(
										key,
										oUsuario.getNombre() + " "
												+ oUsuario.getApellidos());
								String keyEntidad = oCliente
										.nuevaSesionEntidad(key, idEntidad);
								request.getSession()
										.setAttribute(
												ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
												key);
								request.getSession()
										.setAttribute(
												ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
												keyEntidad);
								Aplicacion aplicacion = oServicio
										.getAplicacion(idAplicacion);
								String Url = Utilidades.obtenerUrlAplicacion(
										request, aplicacion);
								request.setAttribute(Defs.PARAMETRO_URL, Url);

								return FORWARD_REDIRIGIR;
							}
							// Usuario sin acceso a la aplicacion de la entidad
							else {
								ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
										.getServicioAdministracionSesionesAdministrador();
								key = oCliente.nuevaSesion(usuario,
										Sesion.TIPO_USUARIO_ADMINISTRADOR);
								Usuario oUsuario = oServicio
										.obtenerUsuario(usuario);
								oCliente.modificarDatosSesion(
										key,
										oUsuario.getNombre() + " "
												+ oUsuario.getApellidos());
								String keyEntidad = oCliente
										.nuevaSesionEntidad(key, idEntidad);
								request.getSession()
										.setAttribute(
												ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
												key);
								request.getSession()
										.setAttribute(
												ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
												keyEntidad);
								request.setAttribute(Defs.MENSAJE_ERROR,
										"mensaje.error.sin_permisos_aplicacion");
								Aplicacion[] aplicaciones = oServicio
										.getAplicaciones(usuario, idEntidad);
								if (aplicaciones == null)
									aplicaciones = new Aplicacion[0];
								request.setAttribute(
										Defs.PARAMETRO_APLICACIONES,
										aplicaciones);
								return FORWARD_APLICACIONES;
							}
						}
						// Id de aplicación no válido
						else {
							ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
									.getServicioAdministracionSesionesAdministrador();
							key = oCliente.nuevaSesion(usuario,
									Sesion.TIPO_USUARIO_ADMINISTRADOR);
							Usuario oUsuario = oServicio
									.obtenerUsuario(usuario);
							oCliente.modificarDatosSesion(
									key,
									oUsuario.getNombre() + " "
											+ oUsuario.getApellidos());
							String keyEntidad = oCliente.nuevaSesionEntidad(
									key, idEntidad);
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
											key);
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
											keyEntidad);
							Aplicacion[] aplicaciones = oServicio
									.getAplicaciones(usuario, idEntidad);
							if (aplicaciones == null)
								aplicaciones = new Aplicacion[0];
							request.setAttribute(Defs.PARAMETRO_APLICACIONES,
									aplicaciones);
							return FORWARD_APLICACIONES;
						}
					}
					// Usuario sin acceso a la entidad
					else {
						request.setAttribute(Defs.MENSAJE_ERROR,
								"mensaje.error.sin_permisos_entidad");
						Entidad[] entidades = oServicio.getEntidades(usuario);
						if (entidades.length != 1) {
							request.setAttribute(Defs.PARAMETRO_ENTIDADES,
									entidades);
							return FORWARD_ENTIDADES;
						} else {
							idEntidad = entidades[0].getIdentificador();
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD,
											idEntidad);
							ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
									.getServicioAdministracionSesionesAdministrador();
							key = oCliente.nuevaSesion(usuario,
									Sesion.TIPO_USUARIO_ADMINISTRADOR);
							Usuario oUsuario = oServicio
									.obtenerUsuario(usuario);
							oCliente.modificarDatosSesion(
									key,
									oUsuario.getNombre() + " "
											+ oUsuario.getApellidos());
							String keyEntidad = oCliente.nuevaSesionEntidad(
									key, idEntidad);
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
											key);
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
											keyEntidad);

							if (!Utilidades.isNuloOVacio(idAplicacion)) {
								if (Utilidades.permisosAplicacion(usuario,
										idEntidad, idAplicacion)) {
									Aplicacion aplicacion = oServicio
											.getAplicacion(idAplicacion);
									String Url = Utilidades
											.obtenerUrlAplicacion(request,
													aplicacion);
									request.setAttribute(Defs.PARAMETRO_URL,
											Url);
									return FORWARD_REDIRIGIR;
								}
							}
							Aplicacion[] aplicaciones = oServicio
									.getAplicaciones(usuario, idEntidad);
							if (aplicaciones == null)
								aplicaciones = new Aplicacion[0];
							request.setAttribute(Defs.PARAMETRO_APLICACIONES,
									aplicaciones);
							return FORWARD_APLICACIONES;
						}
					}
				}
				// Id de entidad no válido
				else {
					// Id de aplicacion válido y es la aplicacion de
					// administracion de entidades
					if (!Utilidades.isNuloOVacio(idAplicacion)
							&& ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION
									.equals(idAplicacion)) {
						// Si tiene permiso para administrar entidades
						if (Utilidades.accesoAdministracion(usuario)) {
							// Crear nueva sesion
							ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
									.getServicioAdministracionSesionesAdministrador();
							key = oCliente.nuevaSesion(usuario,
									Sesion.TIPO_USUARIO_ADMINISTRADOR);
							Usuario oUsuario = oServicio
									.obtenerUsuario(usuario);
							oCliente.modificarDatosSesion(
									key,
									oUsuario.getNombre() + " "
											+ oUsuario.getApellidos());
							Aplicacion aplicacion = oServicio
									.getAplicacion(idAplicacion);
							String Url = Utilidades.obtenerUrlAplicacion(
									request, aplicacion);
							request.setAttribute(Defs.PARAMETRO_URL, Url);
							request.getSession()
									.setAttribute(
											ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
											key);
							return FORWARD_REDIRIGIR;
						}
						// Si no tiene permiso para administrar entidades
						else {
							request.setAttribute(Defs.MENSAJE_ERROR,
									"mensaje.error.sin_permisos_administracion");
						}
					}
					Entidad[] entidades = oServicio.getEntidades(usuario);
					if (entidades.length != 1) {
						request.setAttribute(Defs.PARAMETRO_ENTIDADES,
								entidades);
						return FORWARD_ENTIDADES;
					} else {
						idEntidad = entidades[0].getIdentificador();
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD,
										idEntidad);
						ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
								.getServicioAdministracionSesionesAdministrador();
						key = oCliente.nuevaSesion(usuario,
								Sesion.TIPO_USUARIO_ADMINISTRADOR);
						Usuario oUsuario = oServicio.obtenerUsuario(usuario);
						oCliente.modificarDatosSesion(key, oUsuario.getNombre()
								+ " " + oUsuario.getApellidos());
						String keyEntidad = oCliente.nuevaSesionEntidad(key,
								idEntidad);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
										key);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
										keyEntidad);
						if (!Utilidades.isNuloOVacio(idAplicacion)) {
							if (Utilidades.permisosAplicacion(usuario,
									idEntidad, idAplicacion)) {
								Aplicacion aplicacion = oServicio
										.getAplicacion(idAplicacion);
								String Url = Utilidades.obtenerUrlAplicacion(
										request, aplicacion);
								request.setAttribute(Defs.PARAMETRO_URL, Url);
								return FORWARD_REDIRIGIR;
							}
						}
						Aplicacion[] aplicaciones = oServicio.getAplicaciones(
								usuario, idEntidad);
						if (aplicaciones == null)
							aplicaciones = new Aplicacion[0];
						request.setAttribute(Defs.PARAMETRO_APLICACIONES,
								aplicaciones);
						return FORWARD_APLICACIONES;
					}
				}
			}
			// Usuario no válido
			else {
				if (!validado && !Utilidades.isNuloOVacio(usuario)
						&& !Utilidades.isNuloOVacio(password))
					request.setAttribute(Defs.MENSAJE_ERROR,
							"mensaje.error.usuario_no_valido");
				request.getSession().setAttribute(
						Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(false));
				return FORWARD_LOGIN;
			}
		}
	}

	public static String comprobarInformacionInterno(
			HttpServletRequest request, String key, String idEntidad,
			String idAplicacion, String usuario, String password,
			boolean validado) throws Exception {

		ServicioGestionUsuariosBackOffice oServicioGestUsuariosBackOffice = LocalizadorServicios
				.getServicioAutenticacionUsuariosBackOffice();
		ServicioAdministracion oServicioAdm = LocalizadorServicios
				.getServicioAdministracion();
		ServicioAdministracionSesionesAdministrador oServicioAdmSesionesAdm = LocalizadorServicios
		.getServicioAdministracionSesionesAdministrador();

		// Key de sesion válido
		if (!Utilidades.isNuloOVacio(key)
				&& AutenticacionAdministracion.autenticar(request)) {

			ieci.tecdoc.sgm.core.services.admsesion.administracion.Sesion datosSesion = oServicioAdmSesionesAdm
					.obtenerSesion(key);

			// Datos de sesion válidos
			if (datosSesion != null) {
				int idUsuario = Utilidades.obtenerIdUsuario(datosSesion
						.getDatosEspecificos());
				datosSesion = oServicioAdmSesionesAdm.obtenerSesionEntidad(key + "_"
						+ idEntidad);
				String keyEntidad = null;
				// Sesion de entidad ya creada
				if (datosSesion == null) {
					keyEntidad = oServicioAdmSesionesAdm.nuevaSesionEntidad(key, idEntidad);
					datosSesion = oServicioAdmSesionesAdm.obtenerSesionEntidad(keyEntidad);
				} else {
					keyEntidad = datosSesion.getUsuario() + "_"
							+ datosSesion.getIdSesion() + "_"
							+ datosSesion.getIdEntidad();
				}
				// Id de aplicacion válido
				if (!Utilidades.isNuloOVacio(idAplicacion)) {
					// Usuario con acceso a la aplicacion de la entidad
					if (Utilidades.permisosAplicacionInternos(idUsuario,
							idEntidad, idAplicacion)
							|| ConstantesGestionUsuariosAdministracion.APLICACION_ARCHIVO
									.equals(idAplicacion)
							|| ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_PROCEDIMIENTOS
									.equals(idAplicacion)) {
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
										key);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
										keyEntidad);
						Aplicacion aplicacion = oServicioAdm
								.getAplicacion(idAplicacion);
						String Url = Utilidades.obtenerUrlAplicacion(request,
								aplicacion);
						request.setAttribute(Defs.PARAMETRO_URL, Url);
						return FORWARD_REDIRIGIR;
					}
					// Usuario sin acceso a la aplicacion
					else {
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
										key);
						request.getSession()
								.setAttribute(
										ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
										keyEntidad);
						request.setAttribute(Defs.MENSAJE_ERROR,
								"mensaje.error.sin_permisos_aplicacion");
						Aplicacion[] aplicaciones = Utilidades
								.obtenerAplicacionesInterno(idUsuario,
										idEntidad);
						if (aplicaciones == null)
							aplicaciones = new Aplicacion[0];
						request.setAttribute(Defs.PARAMETRO_APLICACIONES,
								aplicaciones);
						return FORWARD_APLICACIONES;
					}
				}
				// Id de aplicación no válido
				else {
					request.getSession()
							.setAttribute(
									ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
									key);
					request.getSession()
							.setAttribute(
									ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
									keyEntidad);
					Aplicacion[] aplicaciones = Utilidades
							.obtenerAplicacionesInterno(idUsuario, idEntidad);
					if (aplicaciones == null)
						aplicaciones = new Aplicacion[0];
					request.setAttribute(Defs.PARAMETRO_APLICACIONES,
							aplicaciones);
					return FORWARD_APLICACIONES;
				}
			}
			// Datos de sesion no validos
			else {
				request.getSession().setAttribute(
						Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(false));
				return FORWARD_LOGIN;
			}
		}
		// Key de sesión no válido
		else {
			HttpSession session = request.getSession();

			DatosUsuario datosUsuario = new DatosUsuario();
			ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			entidad.setIdentificador(idEntidad);

			String singleSignOn = (String) session.getServletContext().getAttribute(Defs.PLUGIN_SINGLE_SIGN_ON);
			if ((singleSignOn != null) && (singleSignOn.equalsIgnoreCase("true"))) {

				// Autenticación Single-Sign On
				if (!Utilidades.isNuloOVacio(usuario)) {

					ServicioEstructuraOrganizativaLdap oServicioEstOrgLdap = LocalizadorServicios.getServicioEstructuraOrganizativaLdap();
					DatosUsuario[] users = null;
					if (oServicioEstOrgLdap.esEntidadLdap(entidad.getIdentificador())) {

						ServicioEstructuraOrganizativa oServicioEstOrg = LocalizadorServicios.getServicioEstructuraOrganizativa();
						UsuarioLdap usuarioLdap = oServicioEstOrg.getUsuarioLdapByFullName(usuario, entidad.getIdentificador());
						DatosUsuario datoUser = oServicioEstOrgLdap.getDatosUsuarioServicio(String.valueOf(usuarioLdap.get_id()), usuarioLdap.get_ldapfullname());
						users = new DatosUsuario[]{datoUser};
					}else{
						CriterioBusquedaUsuarios criteriobusquedausuarios = new CriterioBusquedaUsuarios();
						criteriobusquedausuarios.setUser(usuario);
						users = oServicioGestUsuariosBackOffice.findUsers(criteriobusquedausuarios, entidad);
					}

					if ((users != null) && (users.length > 0)) {
						datosUsuario = users[0];
						if(oServicioEstOrgLdap.esEntidadLdap(entidad.getIdentificador())) {
							// Usuario de LDAP
							datosUsuario.setAuthenticationType(DatosUsuario.AUTHENTICATION_TYPE_LDAP);
							ServicioEstructuraOrganizativa oServicioEstOrg = LocalizadorServicios.getServicioEstructuraOrganizativa();
							String ldapGuid = oServicioEstOrg.getUsuarioLdapBasicById(Integer.parseInt(datosUsuario.getId()), entidad.getIdentificador());
							datosUsuario.setLdapGuid(ldapGuid);
						} else {
			                // Si el usuario es de Invesdoc
							datosUsuario.setAuthenticationType(DatosUsuario.AUTHENTICATION_TYPE_INVESDOC);
						}

						return createSession(request, oServicioAdm, oServicioAdmSesionesAdm, datosUsuario, key, idEntidad, idAplicacion);
					} else {

						request.setAttribute(Defs.MENSAJE_ERROR, "mensaje_error.sso.validar_usuario");
						request.getSession().setAttribute(Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(false));
						return FORWARD_LOGIN;
					}
				} else {
					request.setAttribute(Defs.MENSAJE_ERROR, "mensaje_error.sso.no_usuario");
					request.getSession().setAttribute(Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(false));
					return FORWARD_LOGIN;
				}
			} else {

				// Autenticación mediante Usuario / Password
				datosUsuario.setUser(usuario);
				datosUsuario.setPassword(password);

				// Usuario válido
				if (validado
						|| (!Utilidades.isNuloOVacio(usuario)
								&& !Utilidades.isNuloOVacio(password) && ((datosUsuario = oServicioGestUsuariosBackOffice
								.authenticateUser(datosUsuario, entidad)) != null))) {

					return createSession(request, oServicioAdm, oServicioAdmSesionesAdm, datosUsuario, key, idEntidad, idAplicacion);
				}
				// Usuario no válido
				else {
					if (!validado && !Utilidades.isNuloOVacio(usuario)
							&& !Utilidades.isNuloOVacio(password))
						request.setAttribute(Defs.MENSAJE_ERROR,
								"mensaje.error.usuario_no_valido");
					request.getSession().setAttribute(
							Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(false));
					return FORWARD_LOGIN;
				}
			}
		}
	}

	protected static String createSession(HttpServletRequest request,
			ServicioAdministracion oServicioAdm,
			ServicioAdministracionSesionesAdministrador oServicioAdmSesionesAdm,
			DatosUsuario datosUsuario, String key, String idEntidad,
			String idAplicacion) throws Exception {

		request.getSession().setAttribute(
				Defs.PARAMETRO_USUARIO_VALIDADO, new Boolean(true));

		// Id de aplicacion válido
		if (!Utilidades.isNuloOVacio(idAplicacion)) {
			// Usuario con acceso a la aplicacion de la entidad
			if (Utilidades.permisosAplicacionInternos(new Integer(
					datosUsuario.getId()).intValue(), idEntidad,
					idAplicacion)
					|| ConstantesGestionUsuariosAdministracion.APLICACION_ARCHIVO
							.equals(idAplicacion)
					|| ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_PROCEDIMIENTOS
							.equals(idAplicacion)) {
				// Crear nueva sesion
				ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios
						.getServicioAdministracionSesionesAdministrador();
				key = oCliente.nuevaSesion(datosUsuario.getUser(),
						Sesion.TIPO_USUARIO_INTERNO);
				oCliente.modificarDatosSesion(key,
						"<IdUsuario>" + datosUsuario.getId()
								+ "</IdUsuario><NombreUsuario>"
								+ datosUsuario.getName() + " "
								+ datosUsuario.getLastname()
								+ "</NombreUsuario>");
				String keyEntidad = oCliente.nuevaSesionEntidad(key,
						idEntidad);
				request.getSession()
						.setAttribute(
								ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
								key);
				request.getSession()
						.setAttribute(
								ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
								keyEntidad);
				Aplicacion aplicacion = oServicioAdm
						.getAplicacion(idAplicacion);
				String Url = Utilidades.obtenerUrlAplicacion(request,
						aplicacion);
				request.setAttribute(Defs.PARAMETRO_URL, Url);

				return FORWARD_REDIRIGIR;
			}
			// Usuario sin acceso a la aplicacion
			else {
				key = oServicioAdmSesionesAdm.nuevaSesion(datosUsuario.getUser(),
						Sesion.TIPO_USUARIO_INTERNO);
				oServicioAdmSesionesAdm.modificarDatosSesion(key,
						"<IdUsuario>" + datosUsuario.getId()
								+ "</IdUsuario><NombreUsuario>"
								+ datosUsuario.getName() + " "
								+ datosUsuario.getLastname()
								+ "</NombreUsuario>");
				String keyEntidad = oServicioAdmSesionesAdm.nuevaSesionEntidad(key,
						idEntidad);
				request.getSession()
						.setAttribute(
								ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
								key);
				request.getSession()
						.setAttribute(
								ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
								keyEntidad);
				request.setAttribute(Defs.MENSAJE_ERROR,
						"mensaje.error.sin_permisos_aplicacion");
				Aplicacion[] aplicaciones = Utilidades
						.obtenerAplicacionesInterno(new Integer(
								datosUsuario.getId()).intValue(),
								idEntidad);
				if (aplicaciones == null)
					aplicaciones = new Aplicacion[0];
				request.setAttribute(Defs.PARAMETRO_APLICACIONES,
						aplicaciones);
				return FORWARD_APLICACIONES;
			}
		}
		// Id de aplicación no válido
		else {
			key = oServicioAdmSesionesAdm.nuevaSesion(datosUsuario.getUser(),
					Sesion.TIPO_USUARIO_INTERNO);
			oServicioAdmSesionesAdm.modificarDatosSesion(key,
					"<IdUsuario>" + datosUsuario.getId()
							+ "</IdUsuario><NombreUsuario>"
							+ datosUsuario.getName() + " "
							+ datosUsuario.getLastname()
							+ "</NombreUsuario>");
			String keyEntidad = oServicioAdmSesionesAdm.nuevaSesionEntidad(key,
					idEntidad);
			request.getSession()
					.setAttribute(
							ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
							key);
			request.getSession()
					.setAttribute(
							ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD,
							keyEntidad);
			Aplicacion[] aplicaciones = Utilidades
					.obtenerAplicacionesInterno(new Integer(
							datosUsuario.getId()).intValue(), idEntidad);
			if (aplicaciones == null)
				aplicaciones = new Aplicacion[0];
			request.setAttribute(Defs.PARAMETRO_APLICACIONES,
					aplicaciones);
			return FORWARD_APLICACIONES;
		}
	}
}
