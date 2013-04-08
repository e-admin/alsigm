package ieci.tecdoc.sgm.usuarios_backoffice;

import ieci.tecdoc.idoc.admin.api.EstructuraOrganizativaLdapManager;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ResultadoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.EstructuraOrganizativaException;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativa;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativaLdap;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.CriterioBusquedaUsuarios;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.DatosUsuario;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.GestionUsuariosBackOfficeException;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuarioException;
import ieci.tecdoc.sgm.usuarios_backoffice.cripto.validacion.readers.IReaderCertInternalUser;
import ieci.tecdoc.sgm.usuarios_backoffice.cripto.validacion.readers.impl.InternalUserDNIEReader;
import ieci.tecdoc.sgm.usuarios_backoffice.cripto.validacion.readers.impl.InternalUserDefaultReader;
import ieci.tecdoc.sgm.usuarios_backoffice.cripto.validacion.readers.impl.InternalUserFnmtCertReader;
import ieci.tecdoc.sgm.usuarios_backoffice.datatype.DatosUsuarioImpl;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

/**
 * Adapter para la autenticación de usuarios contra invesDoc.
 *
 */
public class ServicioGestionUsuariosBackOfficeAdapter implements
		ServicioGestionUsuariosBackOffice {

	private static final Logger logger = Logger
			.getLogger(ServicioGestionUsuariosBackOfficeAdapter.class);

	/**
	 * Método que obtiene los datos de un determinado usuario. Comprueba que el
	 * nombre de usuario y contraseña sean correctos.
	 *
	 * @param usuario
	 *            Nombre de usuario
	 * @param password
	 *            Contraseña.
	 * @return Datos del usuario.
	 * @throws GestionUsuariosBackOfficeException
	 *             En caso de producirse alguna excepción.
	 */
	public DatosUsuario authenticateUser(DatosUsuario user, Entidad entidad)
			throws GestionUsuariosBackOfficeException {
		try {

			// Comprobamos si el usuario es de Ldap
			if (EstructuraOrganizativaLdapManager.esEntidadLdap(entidad
					.getIdentificador())) {
				// Si el usuario es de Ldap
				String userId = EstructuraOrganizativaLdapManager.authenticate(
						user.getUser(), user.getPassword(), entidad
								.getIdentificador());
				// LdapUserImpl userImpl =
				// EstructuraOrganizativaLdapManager.getUser(user.getUser(),
				// userId, entidad.getIdentificador());
				ServicioEstructuraOrganizativaLdap oServicio = LocalizadorServicios
						.getServicioEstructuraOrganizativaLdap();
				DatosUsuario datosUsuario = oServicio.getDatosUsuarioServicio(
						userId, user.getUser());
				datosUsuario
						.setAuthenticationType(DatosUsuario.AUTHENTICATION_TYPE_LDAP);

				ServicioEstructuraOrganizativa oServicioEst = LocalizadorServicios
						.getServicioEstructuraOrganizativa();
				String ldapGuid = oServicioEst.getUsuarioLdapBasicById(Integer
						.parseInt(userId), entidad.getIdentificador());
				datosUsuario.setLdapGuid(ldapGuid);

				return datosUsuario;
			} else {
				// Si el usuario es de Invesdoc
				String userId = GestionUsuariosBackOfficeManager.authenticate(
						user.getUser(), user.getPassword(), entidad
								.getIdentificador());
				DatosUsuario datosUsuario = getDatosUsuarioServicio(GestionUsuariosBackOfficeManager
						.getUser(userId, entidad.getIdentificador()));
				datosUsuario
						.setAuthenticationType(DatosUsuario.AUTHENTICATION_TYPE_INVESDOC);
				return datosUsuario;
			}

		} catch (ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException e) {
			throw getGestionUsuariosBackOfficeException(e);
		} catch (Throwable e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @throws SesionUsuarioException
	 *
	 * @see ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice#authenticateUser(java.security.cert.X509Certificate,
	 *      ieci.tecdoc.sgm.core.services.dto.Entidad)
	 */
	public DatosUsuario authenticateUser(X509Certificate certificate,
			Entidad entidad) throws GestionUsuariosBackOfficeException,
			SesionUsuarioException {

		/*
		 * Autenticar usuario por certificado digital
		 *
		 * 1.- Validar certificado haciendo uso del Servicio de Validación de
		 * certificados
		 *
		 * 2.- Invocar al Reader de certificados de usuarios internos para
		 * obtener el identificador del certificado digital
		 *
		 * 3.- A partir del id del certificado, obtener el usuario interno cuyo
		 * identificador de certificado sea el mismo que el asignado en la
		 * Administración de Estructura Organizativa
		 */

		String idCertificado = "";
		DatosUsuario datosUsuario = null;
		boolean valido = false;
		ResultadoValidacion resultado = null;
		try {

			ServicioCriptoValidacion oServicio;
			try {
				oServicio = LocalizadorServicios.getServicioCriptoValidacion();
				BASE64Encoder encoder = new BASE64Encoder();

				String psB64Certificate = encoder.encodeBuffer(certificate
						.getEncoded());

				resultado = oServicio.validateCertificate(psB64Certificate);
				if (ResultadoValidacion.VALIDACION_OK.equals(resultado
						.getResultadoValidacion())) {

					valido = true;

				}
			} catch (SigemException e2) {
				throw new GestionUsuariosBackOfficeException(
						GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION,
						e2);
			}

			// TODO: PROBAR ESTO
			if (!valido) {
				logger
						.error("ServicioGestionUsuariosBackOfficeAdapter->authenticateUser: El certificado no es válido");
				throw new SesionUsuarioException(
						SesionUsuarioException.INVALID_CREDENTIALS_ERROR_CODE);
			}

			InfoCertificado infoCertificado = resultado.getCertificado();

			IReaderCertInternalUser dnieReader = new InternalUserDNIEReader();
			if (dnieReader.isTypeOf(certificate)) {
				idCertificado = dnieReader.getIdCertInternalUser(certificate,
						infoCertificado);
			} else {
				IReaderCertInternalUser fnmtCertReader = new InternalUserFnmtCertReader();
				if (fnmtCertReader.isTypeOf(certificate)) {
					idCertificado = fnmtCertReader.getIdCertInternalUser(
							certificate, infoCertificado);
				} else {
					IReaderCertInternalUser defaultReader = new InternalUserDefaultReader();
					if (defaultReader.isTypeOf(certificate)) {
						idCertificado = defaultReader.getIdCertInternalUser(
								certificate, infoCertificado);
					}
				}
			}

			ServicioEstructuraOrganizativa oServicioEst;
			try {
				oServicioEst = LocalizadorServicios
						.getServicioEstructuraOrganizativa();

				// Comprobamos si el usuario es de Ldap
				if (EstructuraOrganizativaLdapManager.esEntidadLdap(entidad
						.getIdentificador())) {
					// Si el usuario es de Ldap
					UsuarioLdap usuarioLdap = oServicioEst
							.getUsuarioLdapByIdCert(idCertificado, entidad
									.getIdentificador());

					ServicioEstructuraOrganizativaLdap oServicioEstOrgLdap = LocalizadorServicios
							.getServicioEstructuraOrganizativaLdap();
					datosUsuario = oServicioEstOrgLdap.getDatosUsuarioServicio(
							String.valueOf(usuarioLdap.get_id()), usuarioLdap
									.get_ldapfullname());
					datosUsuario
							.setAuthenticationType(DatosUsuario.AUTHENTICATION_TYPE_LDAP);
					datosUsuario.setLdapGuid(usuarioLdap.get_ldapguid());

				} else {
					// Si el usuario es de Invesdoc
					Usuario usuario = oServicioEst.getUsuarioByIdCert(
							idCertificado, entidad.getIdentificador());

					DatosUsuarioImpl datos = GestionUsuariosBackOfficeManager
							.getUser(String.valueOf(usuario.get_id()), entidad
									.getIdentificador());
					datosUsuario = getDatosUsuarioServicio(datos);
					datosUsuario
							.setAuthenticationType(DatosUsuario.AUTHENTICATION_TYPE_INVESDOC);

				}
			} catch (EstructuraOrganizativaException e) {
				throw new GestionUsuariosBackOfficeException(
						GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION,
						e);
			} catch (SigemException e1) {
				throw new GestionUsuariosBackOfficeException(
						GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION,
						e1);
			}

		} catch (ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);

		} catch (CertificateEncodingException e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		} catch (Throwable e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		}

		return datosUsuario;
	}

	/**
	 * Método que recupera los datos de un usuario.
	 *
	 * @param user
	 *            Nombre de usuario.
	 * @return Datos del usuario
	 * @throws GestionUsuariosBackOfficeException
	 *             En caso de producirse algún error.
	 */
	public DatosUsuario getUser(DatosUsuario user, Entidad entidad)
			throws GestionUsuariosBackOfficeException {
		try {
			return getDatosUsuarioServicio(GestionUsuariosBackOfficeManager
					.getUser(user.getId(), entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException e) {
			throw getGestionUsuariosBackOfficeException(e);
		} catch (Throwable e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Devuelve una lista de usuarios que cumplen el criterio de búsqueda que
	 * llega como parámetro
	 *
	 * @param criteria
	 *            Criterio de búsqueda.
	 * @return Array de DatosUsuario.
	 * @throws GestionUsuariosBackOfficeException
	 *             En caso de producirse algún error.
	 */
	public DatosUsuario[] findUsers(CriterioBusquedaUsuarios criteria,
			Entidad entidad) throws GestionUsuariosBackOfficeException {
		try {
			return getDatosUsuariosServicio(GestionUsuariosBackOfficeManager
					.findUsersByName(criteria.getUser(), entidad
							.getIdentificador()));
		} catch (ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException e) {
			throw getGestionUsuariosBackOfficeException(e);
		} catch (Throwable e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Método que recupera los datos de un usuario.
	 *
	 * @param user
	 *            Nombre de usuario.
	 * @return Datos del usuario
	 * @throws GestionUsuariosBackOfficeException
	 *             En caso de producirse algún error.
	 */
	public void createUser(DatosUsuario user, Entidad entidad)
			throws GestionUsuariosBackOfficeException {
		try {
			GestionUsuariosBackOfficeManager.createUser(
					getDatosUsuarioImpl(user), entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException e) {
			throw getGestionUsuariosBackOfficeException(e);
		} catch (Throwable e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Método que da da alta en el sistema un nuevo usuario.
	 *
	 * @param user
	 *            Datos de usuario.
	 * @throws GestionUsuariosBackOfficeException
	 *             En caso de producirse algún error.
	 */
	public void updateUser(DatosUsuario user, Entidad entidad)
			throws GestionUsuariosBackOfficeException {
		try {
			GestionUsuariosBackOfficeManager.updateUser(
					getDatosUsuarioImpl(user), entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException e) {
			throw getGestionUsuariosBackOfficeException(e);
		} catch (Throwable e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Elimina la información de un usuario
	 *
	 * @param user
	 *            Usuario de acceso.
	 * @return User Datos de usuario.
	 * @throws GestionUsuariosBackOfficeException
	 *             Si se produce algún error.
	 */
	public void deleteUser(DatosUsuario user, Entidad entidad)
			throws GestionUsuariosBackOfficeException {
		try {
			GestionUsuariosBackOfficeManager.deleteUser(user.getId(), entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException e) {
			throw getGestionUsuariosBackOfficeException(e);
		} catch (Throwable e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Método que devuelve la URL de vuelta para una aplicacion de back office
	 *
	 * @param idAplicacion
	 *            Identificador de la aplicación de vuelta
	 * @return String URL de vuelta
	 * @throws GestionUsuariosBackOfficeException
	 *             Si se produce algún error.
	 */
	public String obtenerDireccionAplicacion(String idAplicacion)
			throws GestionUsuariosBackOfficeException {
		try {
			return GestionURLsBackOfficeManager
					.obtenerUrlAplicacion(idAplicacion);
		} catch (ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException e) {
			throw getGestionUsuariosBackOfficeException(e);
		} catch (Throwable e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Método que devuelve la URL de logado para back office
	 *
	 * @return String URL de login
	 * @throws GestionUsuariosBackOfficeException
	 *             Si se produce algún error.
	 */
	public String obtenerDireccionLogado()
			throws GestionUsuariosBackOfficeException {
		try {
			return GestionURLsBackOfficeManager.obtenerUrlLogin();
		} catch (ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException e) {
			throw getGestionUsuariosBackOfficeException(e);
		} catch (Throwable e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Método que devuelve la URL de logout para back office
	 *
	 * @return String URL de logout
	 * @throws GestionUsuariosBackOfficeException
	 *             Si se produce algún error.
	 */
	public String obtenerDireccionDeslogado()
			throws GestionUsuariosBackOfficeException {
		try {
			return GestionURLsBackOfficeManager.obtenerUrlLogout();
		} catch (ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException e) {
			throw getGestionUsuariosBackOfficeException(e);
		} catch (Throwable e) {
			throw new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	private GestionUsuariosBackOfficeException getGestionUsuariosBackOfficeException(
			ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException poException) {
		if (poException == null) {
			return new GestionUsuariosBackOfficeException(
					GestionUsuariosBackOfficeException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(
				ConstantesServicios.SERVICE_BACK_OFFICE_USER_MANAGER_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new GestionUsuariosBackOfficeException(Long.valueOf(
				cCodigo.toString()).longValue(), poException);
	}

	private DatosUsuario getDatosUsuarioServicio(
			ieci.tecdoc.sgm.usuarios_backoffice.datatype.DatosUsuarioImpl oUsuario) {
		if (oUsuario == null)
			return null;

		DatosUsuario poUsuario = new DatosUsuario();

		poUsuario.setEmail(oUsuario.getEmail());
		poUsuario.setId(oUsuario.getId());
		poUsuario.setLastname(oUsuario.getLastname());
		poUsuario.setName(oUsuario.getName());
		poUsuario.setPassword(oUsuario.getPassword());
		poUsuario.setUser(oUsuario.getUser());

		return poUsuario;
	}

	private DatosUsuario[] getDatosUsuariosServicio(
			ieci.tecdoc.sgm.usuarios_backoffice.datatype.DatosUsuarioImpl[] oUsuarios) {
		if (oUsuarios == null)
			return null;

		DatosUsuario[] poUsuarios = new DatosUsuario[oUsuarios.length];

		for (int i = 0; i < oUsuarios.length; i++) {
			poUsuarios[i] = getDatosUsuarioServicio(oUsuarios[i]);
		}

		return poUsuarios;
	}

	private ieci.tecdoc.sgm.usuarios_backoffice.datatype.DatosUsuarioImpl getDatosUsuarioImpl(
			DatosUsuario oUsuario) {
		if (oUsuario == null)
			return null;

		ieci.tecdoc.sgm.usuarios_backoffice.datatype.DatosUsuarioImpl poUsuario = new ieci.tecdoc.sgm.usuarios_backoffice.datatype.DatosUsuarioImpl();

		poUsuario.setEmail(oUsuario.getEmail());
		poUsuario.setId(oUsuario.getId());
		poUsuario.setLastname(oUsuario.getLastname());
		poUsuario.setName(oUsuario.getName());
		poUsuario.setPassword(oUsuario.getPassword());
		poUsuario.setUser(oUsuario.getUser());

		return poUsuario;
	}
}
