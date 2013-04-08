package salas.model;

import gcontrol.db.IUsuarioDBEntity;
import gcontrol.exceptions.UsuariosNotAllowedException;
import gcontrol.vos.UsuarioVO;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.Logger;

import salas.EstadoMesa;
import salas.db.IEdificioDBEntity;
import salas.db.IMesaDBEntity;
import salas.db.IRegistroConsultaSalaDBEntity;
import salas.db.ISalaDBEntity;
import salas.db.IUsuarioArchivoSalasConsultaDBEntity;
import salas.db.IUsuarioSalaConsultaDBEntity;
import salas.exceptions.SalasConsultaException;
import salas.vos.BusquedaRegistroConsultaSalaVO;
import salas.vos.BusquedaUsuarioSalaConsultaVO;
import salas.vos.EdificioVO;
import salas.vos.MesaVO;
import salas.vos.RegistroConsultaSalaVO;
import salas.vos.SalaVO;
import salas.vos.UsuarioArchivoSalasConsultaVO;
import salas.vos.UsuarioSalasConsultaVO;
import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorFactory;
import se.usuarios.exceptions.AppUserException;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.db.IConsultaDBEntity;
import solicitudes.db.IDetalleDBEntity;
import auditoria.logger.LoggingEvent;

import common.Constants;
import common.TiposUsuario;
import common.bi.ServiceBase;
import common.definitions.ArchivoActions;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.SecurityException;
import common.model.UserInfo;
import common.security.SalasSecurityManager;
import common.util.ArrayUtils;
import common.util.StringUtils;

/**
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class GestionSalasConsultaBIImpl extends ServiceBase implements
		GestionSalasConsultaBI {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GestionSalasConsultaBIImpl.class);

	IEdificioDBEntity _edificioDBEntity = null;
	ISalaDBEntity _salaDbEntity = null;
	IMesaDBEntity _mesaDbEntity = null;
	IUsuarioSalaConsultaDBEntity _usuarioSalaConsultaDBEntity = null;
	IUsuarioArchivoSalasConsultaDBEntity _usuarioArchivoSalasConsultaDBEntity = null;
	IRegistroConsultaSalaDBEntity _registroConsultaSalaDBEntity = null;
	IUsuarioDBEntity _usuarioDEntity = null;
	IConsultaDBEntity _consultaDBEntity = null;
	IDetalleDBEntity _detalleDBEntity = null;

	public GestionSalasConsultaBIImpl() {
	}

	public GestionSalasConsultaBIImpl(
			IEdificioDBEntity edificioDBEntity,
			ISalaDBEntity salasDBEntity,
			IMesaDBEntity mesasDBEntity,
			IUsuarioSalaConsultaDBEntity usuarioSalaConsultaDBEntity,
			IUsuarioArchivoSalasConsultaDBEntity usuarioArchivoSalasConsultaDBEntity,
			IRegistroConsultaSalaDBEntity registroConsultaSalaDBEntity,
			IUsuarioDBEntity usuarioDBEntity,
			IConsultaDBEntity consultaDBEntity, IDetalleDBEntity detalleDBEntity) {

		this._edificioDBEntity = edificioDBEntity;
		this._salaDbEntity = salasDBEntity;
		this._mesaDbEntity = mesasDBEntity;
		this._usuarioSalaConsultaDBEntity = usuarioSalaConsultaDBEntity;
		this._usuarioArchivoSalasConsultaDBEntity = usuarioArchivoSalasConsultaDBEntity;
		this._registroConsultaSalaDBEntity = registroConsultaSalaDBEntity;
		this._usuarioDEntity = usuarioDBEntity;
		this._consultaDBEntity = consultaDBEntity;
		this._detalleDBEntity = detalleDBEntity;
	}

	/************************ EDIFICIOS ********************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getEstructuraSalasConsulta()
	 */
	public EstructuraSalasConsulta getEstructuraSalasConsulta() {
		return new EstructuraSalasConsulta(this, getServiceClient());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getEstructuraSalasConsulta(java.lang.String)
	 */
	public EstructuraSalasConsulta getEstructuraSalasConsulta(String subtreeNode) {
		return new EstructuraSalasConsulta(this, getServiceClient(),
				subtreeNode);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#actualizarEdificio(salas.vos.EdificioVO)
	 */
	public void actualizarEdificio(final EdificioVO edificioVO)
			throws SalasConsultaException, SecurityException {

		checkPermission(SalasSecurityManager.MODIFICAR_EDIFICIO_ACTION);

		// Comprobar que el nombre no está duplicado
		EdificioVO edificioBDVO = _edificioDBEntity
				.getEdificioByNombre(edificioVO.getNombre());

		if (edificioBDVO != null
				&& !edificioBDVO.getId().equals(edificioVO.getId())) {
			throw new SalasConsultaException(
					SalasConsultaException.NOMBRE_EDIFICIO_DUPLICADO);
		}

		edificioBDVO = _edificioDBEntity.getEdificioById(edificioVO.getId());
		if (!edificioBDVO.getIdArchivo().equals(edificioVO.getIdArchivo())
				&& _mesaDbEntity.countMesasByEdificiosYEstados(
						edificioBDVO.getId(),
						new String[] { EstadoMesa.OCUPADA }) > 0) {
			throw new SalasConsultaException(
					SalasConsultaException.EDIFICIO_CON_MESAS_OCUPADAS);
		}

		_edificioDBEntity.updateEdificio(edificioVO);

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_EDICION_EDIFICIO);
		AuditSalasConsulta.addDataLogInfoEdificio(locale, event, edificioVO);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#eliminarEdificio(java.lang.String)
	 */
	public void eliminarEdificio(final EdificioVO edificioVO)
			throws SalasConsultaException, SecurityException {
		checkPermission(SalasSecurityManager.ELIMINAR_EDIFICIO_ACTION);

		if (isEdificioConSalas(edificioVO.getId())) {
			throw new SalasConsultaException(
					SalasConsultaException.EDIFICIO_CON_SALAS);
		}

		_edificioDBEntity.deleteEdificio(edificioVO.getId());

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_ELIMINAR_EDIFICIO);
		AuditSalasConsulta.addDataLogInfoEdificio(locale, event, edificioVO);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getEdificioById(java.lang.String)
	 */
	public EdificioVO getEdificioById(final String idEdificio)
			throws SecurityException {
		checkPermission(SalasSecurityManager.VER_EDIFICIO_ACTION);

		return _edificioDBEntity.getEdificioById(idEdificio);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getEdificiosByIdsArchivo(java.lang.String[])
	 */
	public List getEdificiosByIdsArchivo(final String[] idsArchivo)
			throws SecurityException {
		checkPermission(SalasSecurityManager.VER_EDIFICIOS_ACTION);
		return _edificioDBEntity.getEdificiosByIdsArchivo(idsArchivo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#hasEdificiosByIdArchivo(java.lang.String)
	 */
	public boolean isArchivoConEdificios(final String idArchivo) {
		if (_edificioDBEntity.getCountEdificiosByIdArchivo(idArchivo) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws Exception
	 * @see salas.model.GestionSalasConsultaBI#insertarEdificio(salas.vos.EdificioVO)
	 */
	public void insertarEdificio(final EdificioVO edificioVO)
			throws SalasConsultaException, SecurityException {
		checkPermission(SalasSecurityManager.ALTA_EDIFICIO_ACTION);

		// Comprobar que el nombre no está duplicado
		EdificioVO edificioBDVO = _edificioDBEntity
				.getEdificioByNombre(edificioVO.getNombre());

		if (edificioBDVO != null) {
			throw new SalasConsultaException(
					SalasConsultaException.NOMBRE_EDIFICIO_DUPLICADO);
		}

		_edificioDBEntity.insertEdificio(edificioVO);

		// AUDITORIA
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_ALTA_EDIFICIO);
		AuditSalasConsulta.addDataLogInfoEdificio(locale, event, edificioVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getEdificiosNumHijosByArchivo(java.lang.String,
	 *      java.lang.String)
	 */
	public List getEdificiosNumHijosByArchivo(String idArchivo,
			String salasConEquipoInformatico) {
		return _edificioDBEntity.getMesasLibresEdificio(idArchivo,
				salasConEquipoInformatico);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getMesasLibresByEdificios(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List getMesasLibresByEdificio(String idEdificio, String idArchivo,
			String salasConEquipoInformatico) {
		return _edificioDBEntity.getMesasLibresByEdificio(idArchivo,
				idEdificio, salasConEquipoInformatico);
	}

	/************************ FIN EDIFICIOS ********************************/

	/************************ SALAS ********************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#insertarSala(salas.vos.SalaVO)
	 */
	public void insertarSala(final SalaVO salaVO, String[] codigosMesa)
			throws SalasConsultaException, SecurityException {

		checkPermission(SalasSecurityManager.ALTA_SALA_ACTION);

		iniciarTransaccion();

		if (salaVO.getId() == null) {
			// Comprobar que el nombre no está duplicado
			SalaVO sala = _salaDbEntity.getSalaByNombreAndEdificio(
					salaVO.getNombre(), salaVO.getIdEdificio());

			if (sala != null) {
				throw new SalasConsultaException(
						SalasConsultaException.NOMBRE_SALA_DUPLICADO);
			}

			_salaDbEntity.insertSala(salaVO);
		}

		int maxNumOrden = 1;
		if (salaVO.getId() != null)
			maxNumOrden = _mesaDbEntity.getMaxNumOrden(salaVO.getId()) + 1;

		if (ArrayUtils.isNotEmpty(codigosMesa)) {
			for (int i = 0; i < codigosMesa.length; i++) {
				MesaVO mesaVO = new MesaVO();
				mesaVO.setCodigo(codigosMesa[i]);
				mesaVO.setNumorden(new Integer(maxNumOrden + i));
				mesaVO.setIdSala(salaVO.getId());
				mesaVO.setEstado(EstadoMesa.LIBRE);
				mesaVO.setFechaEstado(new Date());

				_mesaDbEntity.insertMesa(mesaVO);
			}
		}
		commit();

		// AUDITORIA
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_ALTA_SALA);
		AuditSalasConsulta.addDataLogInfoSala(locale, event, salaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#eliminarSala(java.lang.String)
	 */
	public void eliminarSala(final SalaVO salaVO)
			throws SalasConsultaException, SecurityException {

		checkPermission(SalasSecurityManager.ELIMINAR_SALA_ACTION);

		if (existenMesasOcupadas(salaVO.getId())) {
			throw new SalasConsultaException(
					SalasConsultaException.NO_ELIMINAR_SALA_CON_MESAS);
		}

		_mesaDbEntity.deleteMesasByIdSala(salaVO.getId());

		_salaDbEntity.deleteSala(salaVO.getId());

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_ELIMINAR_SALA);
		AuditSalasConsulta.addDataLogInfoSala(locale, event, salaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#actualizarSala(salas.vos.SalaVO)
	 */
	public void actualizarSala(final SalaVO salaVO)
			throws SalasConsultaException, SecurityException {

		checkPermission(SalasSecurityManager.MODIFICAR_SALA_ACTION);

		// Comprobar que el nombre no está duplicado
		SalaVO sala = _salaDbEntity.getSalaByNombreAndEdificio(
				salaVO.getNombre(), salaVO.getIdEdificio());

		if (sala != null && !sala.getId().equals(salaVO.getId())) {
			throw new SalasConsultaException(
					SalasConsultaException.NOMBRE_SALA_DUPLICADO);
		}

		_salaDbEntity.updateSala(salaVO);

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_EDICION_SALA);
		AuditSalasConsulta.addDataLogInfoSala(locale, event, salaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getSalaById(java.lang.String)
	 */
	public SalaVO getSalaById(final String idSala) throws SecurityException {

		checkPermission(SalasSecurityManager.VER_SALA_ACTION);

		return _salaDbEntity.getSalaById(idSala);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getSalasByIdsEdificio(java.lang.String[])
	 */
	public List getSalas(final String idEdificio) throws SecurityException {

		checkPermission(SalasSecurityManager.VER_SALAS_ACTION);
		return _salaDbEntity.getSalas(idEdificio);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#isEdificioConSalas(java.lang.String)
	 */
	public boolean isEdificioConSalas(final String idEdificio) {
		if (_salaDbEntity.countSalasPorEdificio(idEdificio) > 0)
			return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#existeSala(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean existeSala(final String nombre, final String idEdificio) {
		if (_salaDbEntity.getSalaByNombreAndEdificio(nombre, idEdificio) != null)
			return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getSalaByNombreAndEdificio(java.lang.String,
	 *      java.lang.String)
	 */
	public SalaVO getSalaByNombreAndEdificio(final String nombre,
			final String idEdificio) throws SecurityException {

		checkPermission(SalasSecurityManager.VER_SALA_ACTION);
		SalaVO salaVO = _salaDbEntity.getSalaByNombreAndEdificio(nombre,
				idEdificio);

		return salaVO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getSalasNumHijos(java.lang.String,
	 *      java.lang.String)
	 */
	public List getSalasNumHijos(final String idEdificio,
			final String salasConEquipoInformatico) {
		return _salaDbEntity.getMesasLibresSalaByEdificio(idEdificio,
				salasConEquipoInformatico);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getMesasLibresBySala(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List getMesasLibresBySala(final String idSala,
			final String idArchivo, final String salasConEquipoInformatico) {
		return _salaDbEntity.getMesasLibresBySala(idArchivo, idSala,
				salasConEquipoInformatico);
	}

	/************************ FIN SALAS ********************************/

	/************************ MESAS ********************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#insertarMesa(salas.vos.MesaVO)
	 */
	public void insertarMesa(final MesaVO mesaVO) throws Exception {
		checkPermission(SalasSecurityManager.ALTA_MESA_ACTION);

		_mesaDbEntity.insertMesa(mesaVO);

		// AUDITORIA
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_ALTA_MESA);
		AuditSalasConsulta.addDataLogInfoMesa(locale, event, mesaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#eliminarMesa(salas.vos.MesaVO)
	 */
	public void eliminarMesa(final MesaVO mesaVO) {
		checkPermission(SalasSecurityManager.ELIMINAR_SALA_ACTION);

		_mesaDbEntity.deleteMesa(mesaVO.getId());

		// AUDITORIA
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_ELIMINAR_MESA);
		AuditSalasConsulta.addDataLogInfoMesa(locale, event, mesaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#actualizarMesa(salas.vos.MesaVO)
	 */
	public void actualizarMesa(final MesaVO mesaVO) {
		checkPermission(SalasSecurityManager.MODIFICAR_SALA_ACTION);

		_mesaDbEntity.updateMesa(mesaVO);

		// AUDITORIA
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_EDICION_MESA);
		AuditSalasConsulta.addDataLogInfoMesa(locale, event, mesaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getMesaById(java.lang.String)
	 */
	public MesaVO getMesaById(final String idMesa) {
		checkPermission(SalasSecurityManager.VER_SALA_ACTION);

		MesaVO mesaVO = _mesaDbEntity.getMesaById(idMesa);

		return mesaVO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getMesas(java.lang.String)
	 */
	public List getMesas(final String idSala) {
		checkPermission(SalasSecurityManager.VER_MESAS_ACTION);
		return _mesaDbEntity.getMesas(idSala);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#isSalaConMesas(java.lang.String)
	 */
	public boolean isSalaConMesas(final String idSala) {
		if (_mesaDbEntity.countMesasPorSala(idSala) > 0)
			return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getNumeroMesas(java.lang.String)
	 */
	public int getNumeroMesas(final String idSala) {
		return _mesaDbEntity.countMesasPorSala(idSala);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#existenMesasOcupadas(java.lang.String)
	 */
	public boolean existenMesasOcupadas(final String idSala) {
		String[] estados = new String[] { EstadoMesa.OCUPADA };
		if (_mesaDbEntity.counMesasBySalaYEstados(idSala, estados) > 0)
			return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getMaxNumOrden(java.lang.String)
	 */
	public int getMaxNumOrden(final String idSala) {
		return _mesaDbEntity.getMaxNumOrden(idSala);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getMesaBySalaAndCodigo(java.lang.String,
	 *      java.lang.String)
	 */
	public MesaVO getMesaBySalaAndCodigo(final String idSala,
			final String codigo) {
		return _mesaDbEntity.getMesaBySalaAndCodigo(idSala, codigo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#cambiarEstado(java.lang.String[],
	 *      java.lang.String)
	 */
	public void cambiarEstado(final String[] idsMesa,
			final String estadoAEstablecer) {
		checkPermission(SalasSecurityManager.MODIFICAR_MESA_ACTION);
		_mesaDbEntity.cambiarEstado(idsMesa, estadoAEstablecer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getCountMesasByIdEdificio(java.lang.String)
	 */
	public int getCountMesasByIdEdificio(String idEdificio) {
		return _mesaDbEntity.countMesasByEdificiosYEstados(idEdificio, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getCountMesasLibresByIdEdificio(java.lang.String)
	 */
	public int getCountMesasLibresByIdEdificio(String idEdificio) {
		String[] estados = new String[] { EstadoMesa.LIBRE };
		return _mesaDbEntity.countMesasByEdificiosYEstados(idEdificio, estados);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getCountMesasOcupadasByIdEdificio(java.lang.String)
	 */
	public int getCountMesasOcupadasByIdEdificio(String idEdificio) {
		String[] estados = new String[] { EstadoMesa.OCUPADA };
		return _mesaDbEntity.countMesasByEdificiosYEstados(idEdificio, estados);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#eliminarMesas(java.lang.String,
	 *      java.lang.String[])
	 */
	public void eliminarMesas(final String idSala, final String[] idsMesa) {
		_mesaDbEntity.deleteMesas(idSala, idsMesa);
	}

	public int getCountMesasLibresByIdSala(final String idSala) {
		String[] estados = new String[] { EstadoMesa.LIBRE };
		return _mesaDbEntity.counMesasBySalaYEstados(idSala, estados);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getCountMesasOcupadasByIdSala(java.lang.String)
	 */
	public int getCountMesasOcupadasByIdSala(final String idSala) {
		String[] estados = new String[] { EstadoMesa.OCUPADA };
		return _mesaDbEntity.counMesasBySalaYEstados(idSala, estados);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getMesasById(java.lang.String[])
	 */
	public List getMesasById(final String[] idsMesa) {
		checkPermission(SalasSecurityManager.VER_MESAS_ACTION);
		return _mesaDbEntity.getMesasById(idsMesa);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#actualizarCodigosMesa(java.lang.String[],
	 *      java.lang.String[])
	 */
	public void actualizarCodigosMesa(String[] idsMesa, String[] codigosMesa) {
		checkPermission(SalasSecurityManager.MODIFICAR_MESA_ACTION);
		iniciarTransaccion();

		for (int i = 0; i < idsMesa.length; i++) {
			_mesaDbEntity.cambiarCodigo(idsMesa[i], codigosMesa[i]);
		}

		commit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getMesasNavegacionPorSala(java.lang.String)
	 */
	public List getMesasNavegacionPorSala(final String idSala) {
		checkPermission(SalasSecurityManager.VER_MESAS_ACTION);
		return _mesaDbEntity.getMesasNavegacion(idSala);
	}

	/************************ FIN MESAS ********************************/

	/************************ REGISTROS DE CONSULTA ********************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#usuarioRegistrado(java.lang.String)
	 */
	public boolean isUsuarioRegistrado(String idUsuarioConsulta) {
		if (_registroConsultaSalaDBEntity
				.getRegistroConsultaSalaByIdUsuario(idUsuarioConsulta) != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#insertarRegistroConsultaSala(salas.vos.RegistroConsultaSalaVO,
	 *      salas.vos.MesaVO)
	 */
	public void insertarRegistroConsultaSala(
			final RegistroConsultaSalaVO registroConsultaSalaVO,
			final MesaVO mesaVO) throws SalasConsultaException,
			SecurityException {
		checkPermission(SalasSecurityManager.ALTA_REGISTRO_ACTION);

		iniciarTransaccion();

		_mesaDbEntity.updateBloqueo(mesaVO.getId());

		_registroConsultaSalaDBEntity
				.insertRegistroConsultaSala(registroConsultaSalaVO);

		int mesasActualizadas = _mesaDbEntity.registrarEntrada(mesaVO.getId(),
				registroConsultaSalaVO.getIdUsrCSala());

		if (mesasActualizadas == 0)
			throw new SalasConsultaException(
					SalasConsultaException.REGISTRO_MESA_OCUPADA);

		commit();

		// AUDITORIA
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_ALTA_REGISTRO);
		AuditSalasConsulta.addDataLogInfoRegistro(locale, event,
				registroConsultaSalaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getRegistroConsultaSalaByUsuario(java.lang.String)
	 */
	public RegistroConsultaSalaVO getRegistroConsultaSalaByUsuario(
			final String idUsuarioConsulta) {
		checkPermission(SalasSecurityManager.VER_REGISTRO_ACTION);

		RegistroConsultaSalaVO registroConsultaVO = _registroConsultaSalaDBEntity
				.getRegistroConsultaSalaByIdUsuario(idUsuarioConsulta);

		return registroConsultaVO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getUsuariosRegistrados()
	 */
	public List getUsuariosRegistrados() {
		checkPermission(SalasSecurityManager.VER_REGISTROS_ACTION);
		return _registroConsultaSalaDBEntity.getUsuariosRegistrados();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.bi.GestionConsultasBI#getConsultasConsultaUsuarioSala(java.lang.String)
	 */
	public List getConsultasUsuarioSala(String idUsrCSala) {
		checkPermission(SalasSecurityManager.VER_REGISTROS_ACTION);
		int[] estados = { ConsultasConstants.ESTADO_CONSULTA_ABIERTA,
				ConsultasConstants.ESTADO_CONSULTA_SOLICITADA,
				ConsultasConstants.ESTADO_CONSULTA_RESERVADA,
				ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA,
				ConsultasConstants.ESTADO_CONSULTA_ENTREGADA,
				ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA };
		return _consultaDBEntity.getConsultasByIdUsrCSala(idUsrCSala, estados);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getRegistroConsultaSala(java.lang.String)
	 */
	public RegistroConsultaSalaVO getRegistroConsultaSala(String idRegistro) {
		checkPermission(SalasSecurityManager.VER_REGISTROS_ACTION);
		return _registroConsultaSalaDBEntity
				.getRegistroConsultaSalaById(idRegistro);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getRegistrosConsultaSala(java.lang.String[])
	 */
	public List getRegistrosConsultaSala(String[] idsRegistro) {
		checkPermission(SalasSecurityManager.VER_REGISTROS_ACTION);
		return _registroConsultaSalaDBEntity
				.getRegistrosConsultaSalaByIds(idsRegistro);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#isUsuarioConUnidadesPendientes(java.lang.String)
	 */
	public boolean isUsuarioConUnidadesPendientes(String idUsrCSala) {
		checkPermission(SalasSecurityManager.ELIMINAR_REGISTRO_ACTION);
		List consultas = _consultaDBEntity
				.getConsultasPendientesByIdUsrCSala(idUsrCSala);
		if (consultas != null && !consultas.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#registrarSalidaSala(java.lang.String,
	 *      java.lang.String)
	 */
	public void registrarSalidaSala(String idRegistro, String idUsuario) {
		checkPermission(SalasSecurityManager.MODIFICAR_REGISTRO_ACTION);
		iniciarTransaccion();

		_registroConsultaSalaDBEntity.updateRegistrosConsultaSala(idRegistro);

		_mesaDbEntity.registrarSalida(idUsuario);

		commit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#buscarRegistros(salas.vos.BusquedaRegistroConsultaSalaVO)
	 */
	public List buscarRegistros(
			BusquedaRegistroConsultaSalaVO busquedaRegistroConsultaSalaVO)
			throws SecurityException {
		checkPermission(SalasSecurityManager.VER_REGISTROS_ACTION);
		return _registroConsultaSalaDBEntity
				.findRegistros(busquedaRegistroConsultaSalaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getRegistrosAbiertos()
	 */
	public List getRegistrosAbiertos() {
		checkPermission(SalasSecurityManager.VER_REGISTROS_ACTION);
		return _registroConsultaSalaDBEntity.getRegistrosAbiertos();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getCountRegistrosAbiertos()
	 */
	public int getCountRegistrosAbiertos() {
		checkPermission(SalasSecurityManager.VER_REGISTROS_ACTION);
		return _registroConsultaSalaDBEntity.countRegistrosAbiertos();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getRegistroConsultaSala(salas.vos.RegistroConsultaSalaVO)
	 */
	public RegistroConsultaSalaVO getRegistroConsultaSala(
			RegistroConsultaSalaVO registroConsultaSala) {
		return _registroConsultaSalaDBEntity.getRegistro(
				registroConsultaSala.getIdArchivo(),
				registroConsultaSala.getIdUsrCSala(),
				registroConsultaSala.getIdScaUsr(),
				registroConsultaSala.getFechaEntrada());
	}

	/************************ FIN DE REGISTROS DE CONSULTA ********************************/

	/************************ USUARIOS ********************************/
	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#actualizarUsuario(salas.vos.UsuarioSalasConsultaVO)
	 */
	public void actualizarUsuario(UsuarioSalasConsultaVO usuarioSalaConsultaVO)
			throws SecurityException, SalasConsultaException {
		checkPermission(SalasSecurityManager.MODIFICAR_USUARIO_ACTION);

		if (usuarioSalaConsultaVO.getIdscausr() != null
				&& isUsuarioAsociadoAUsuarioSalasConsultaDistintoUsuario(
						usuarioSalaConsultaVO.getIdscausr(),
						usuarioSalaConsultaVO.getId())) {
			throw new SalasConsultaException(
					SalasConsultaException.USUARIO_CONSULTA_SALAS_USUARIO_APLICACION_YA_CREADO);
		}

		_usuarioSalaConsultaDBEntity.updateUsuario(usuarioSalaConsultaVO);

		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_EDICION_USUARIO);
		AuditSalasConsulta.addDataLogInfoUsuario(locale, event,
				usuarioSalaConsultaVO);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#buscarUsuarios(salas.vos.BusquedaUsuarioSalaConsultaVO)
	 */
	public List buscarUsuarios(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO)
			throws SecurityException {

		checkPermission(SalasSecurityManager.VER_USUARIOS_ACTION);
		return _usuarioSalaConsultaDBEntity
				.findUsuarios(busquedaUsuarioSalaConsultaVO);
	}

	public List buscarUsuariosPorArchivo(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO)
			throws SecurityException {
		checkPermission(SalasSecurityManager.VER_USUARIOS_ACTION);
		return _usuarioSalaConsultaDBEntity
				.findUsuariosByArchivo(busquedaUsuarioSalaConsultaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#eliminarUsuario(java.lang.String)
	 */
	public void eliminarUsuario(String idUsuario) throws SecurityException,
			ActionNotAllowedException {
		checkPermission(SalasSecurityManager.ELIMINAR_USUARIO_ACTION);

		UsuarioSalasConsultaVO usuarioSalaConsultaVO = _usuarioSalaConsultaDBEntity
				.getUsuarioById(idUsuario);

		if (usuarioSalaConsultaVO != null) {

			// Comprobar que el usuario no esté conectado
			List mesas = _mesaDbEntity.getMesasByIdUsuarioSala(idUsuario);
			if (mesas != null && mesas.size() > 0) {
				throw new SalasConsultaException(
						SalasConsultaException.NO_ELIMINAR_USUARIO_EN_SALA);
			}

			// Comprobar que el usuario no tiene consultas
			if (isUsuarioSalaConConsultas(idUsuario)) {
				throw new SalasConsultaException(
						SalasConsultaException.NO_ELIMINAR_USUARIO_CON_CONSULTAS);
			}
			iniciarTransaccion();

			// Eliminar los archivos relacionados
			_usuarioArchivoSalasConsultaDBEntity.deleteByIdUsuario(idUsuario);

			// Eliminar el usuario
			_usuarioSalaConsultaDBEntity.deleteUsuario(idUsuario);

			commit();

			Locale locale = getServiceClient().getLocale();
			LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
					ArchivoActions.SALAS_MODULE_ELIMINAR_USUARIO);
			AuditSalasConsulta.addDataLogInfoUsuario(locale, event,
					usuarioSalaConsultaVO);

		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getUsuarioById(java.lang.String)
	 */
	public UsuarioSalasConsultaVO getUsuarioById(String id,
			boolean cargarDatosRelacionados) throws SecurityException {
		checkPermission(SalasSecurityManager.VER_USUARIO_ACTION);

		UsuarioSalasConsultaVO usuarioSalaConsultaVO = _usuarioSalaConsultaDBEntity
				.getUsuarioById(id);

		// Obtener los Archivos
		if (cargarDatosRelacionados && usuarioSalaConsultaVO != null) {
			List listaArchivos = _usuarioArchivoSalasConsultaDBEntity
					.getArchivosByIdUsuarioSalaConsulta(id);
			usuarioSalaConsultaVO.setListaArchivos(listaArchivos);

			// Cargar nombre del Usuario de la Aplicación
			if (StringUtils.isNotEmpty(usuarioSalaConsultaVO.getIdscausr())) {
				UsuarioVO usuarioVO = _usuarioDEntity
						.getUsuario(usuarioSalaConsultaVO.getIdscausr());

				if (usuarioVO != null) {
					usuarioSalaConsultaVO
							.setNombreCompletoUsuarioAplicacion(usuarioVO
									.getNombreYApellidos());
				}
			}
		}

		return usuarioSalaConsultaVO;
	}

	public UsuarioSalasConsultaVO getUsuarioSalaById(String id) {
		return _usuarioSalaConsultaDBEntity.getUsuarioById(id);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getUsuarioExternoById(java.lang.String)
	 */
	public UsuarioSalasConsultaVO getUsuarioExternoById(String idScaUsr,
			boolean cargaArchivos) {

		UsuarioSalasConsultaVO usuarioSalaConsultaVO = _usuarioSalaConsultaDBEntity
				.getUsuarioExterno(idScaUsr, Constants.TRUE_STRING);

		// Obtener los Archivos
		if (cargaArchivos && usuarioSalaConsultaVO != null) {
			List listaArchivos = _usuarioArchivoSalasConsultaDBEntity
					.getArchivosByIdUsuarioSalaConsulta(usuarioSalaConsultaVO
							.getId());
			usuarioSalaConsultaVO.setListaArchivos(listaArchivos);

			// Cargar nombre del Usuario de la Aplicación
			if (StringUtils.isNotEmpty(usuarioSalaConsultaVO.getIdscausr())) {
				UsuarioVO usuarioVO = _usuarioDEntity
						.getUsuario(usuarioSalaConsultaVO.getIdscausr());

				if (usuarioVO != null) {
					usuarioSalaConsultaVO
							.setNombreCompletoUsuarioAplicacion(usuarioVO
									.getNombreYApellidos());
				}
			}
		}

		return usuarioSalaConsultaVO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getUsuariosVigentes()
	 */
	public List getUsuariosVigentes() {
		return _usuarioSalaConsultaDBEntity
				.getUsuariosByVigencia(Constants.TRUE_STRING);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#insertarUsuario(salas.vos.UsuarioSalasConsultaVO)
	 */
	public void insertarUsuario(UsuarioSalasConsultaVO usuarioSalaConsultaVO)
			throws SalasConsultaException, SecurityException {
		checkPermission(SalasSecurityManager.ALTA_USUARIO_ACTION);

		iniciarTransaccion();

		if (usuarioSalaConsultaVO.getIdscausr() != null
				&& isUsuarioAsociadoAUsuarioSalasConsulta(usuarioSalaConsultaVO
						.getIdscausr())) {
			throw new SalasConsultaException(
					SalasConsultaException.USUARIO_CONSULTA_SALAS_USUARIO_APLICACION_YA_CREADO);
		}

		_usuarioSalaConsultaDBEntity.insertUsuario(usuarioSalaConsultaVO);

		if (ArrayUtils.isNotEmpty(usuarioSalaConsultaVO.getIdsArchivos())) {
			for (int i = 0; i < usuarioSalaConsultaVO.getIdsArchivos().length; i++) {
				String idArchivo = usuarioSalaConsultaVO.getIdsArchivos()[i];
				UsuarioArchivoSalasConsultaVO usuarioArchivoSalasConsultaVO = new UsuarioArchivoSalasConsultaVO(
						usuarioSalaConsultaVO.getId(), idArchivo);
				_usuarioArchivoSalasConsultaDBEntity
						.insertar(usuarioArchivoSalasConsultaVO);
			}
		}

		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditSalasConsulta.getLogginEvent(this,
				ArchivoActions.SALAS_MODULE_ALTA_USUARIO);
		AuditSalasConsulta.addDataLogInfoUsuario(locale, event,
				usuarioSalaConsultaVO);

		commit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getUsuarios()
	 */
	public List getUsuarios() {
		checkPermission(SalasSecurityManager.VER_USUARIOS_ACTION);
		return _usuarioSalaConsultaDBEntity.getUsuariosByVigencia(null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getUsuariosNoVigentes()
	 */
	public List getUsuariosNoVigentes() {
		return _usuarioSalaConsultaDBEntity
				.getUsuariosByVigencia(Constants.FALSE_STRING);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getArchivosByIdUsuarioSalaConsultaInArchivos(java.lang.String,
	 *      java.lang.String[])
	 */
	public List getArchivosByIdUsuarioSalaConsultaInArchivos(String idUsuario,
			String[] idsArchivo) {

		return _usuarioArchivoSalasConsultaDBEntity
				.getArchivosByIdUsuarioSalaConsultaInArchivos(idUsuario,
						idsArchivo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#asociarArchivos(java.lang.String,
	 *      java.lang.String[])
	 */
	public void desasociarArchivos(String idUsuario, String[] idsArchivo)
			throws SecurityException, SalasConsultaException {
		checkPermission(SalasSecurityManager.MODIFICAR_USUARIO_ACTION);

		List mesas = _mesaDbEntity.getMesasByIdUsuarioSalaArchivos(idUsuario,
				idsArchivo);
		if (mesas != null && mesas.size() > 0) {
			throw new SalasConsultaException(
					SalasConsultaException.ARCHIVO_USUARIO_CONSULTA_SALA_CON_MESA_OCUPADA);
		}

		if (StringUtils.isNotEmpty(idUsuario)
				&& ArrayUtils.isNotEmpty(idsArchivo)) {
			_usuarioArchivoSalasConsultaDBEntity.deleteByIdUsuarioYArchivos(
					idUsuario, idsArchivo);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#desasociarArchivos(java.lang.String,
	 *      java.lang.String[])
	 */
	public void asociarArchivos(String idUsuario, String[] idsArchivo)
			throws SecurityException {
		checkPermission(SalasSecurityManager.MODIFICAR_USUARIO_ACTION);

		iniciarTransaccion();

		if (ArrayUtils.isNotEmpty(idsArchivo)) {
			for (int i = 0; i < idsArchivo.length; i++) {
				String idArchivo = idsArchivo[i];
				UsuarioArchivoSalasConsultaVO usuarioArchivoSalasConsultaVO = new UsuarioArchivoSalasConsultaVO(
						idUsuario, idArchivo);

				if (!_usuarioArchivoSalasConsultaDBEntity.existe(idUsuario,
						idArchivo)) {
					_usuarioArchivoSalasConsultaDBEntity
							.insertar(usuarioArchivoSalasConsultaVO);
				}
			}
		}

		commit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getUsuarioExternos()
	 */
	public List getUsuariosExternos(String filtro) {
		String tipoUsuario = TiposUsuario.ORGANO_EXTERNO_VALUE;
		return _usuarioDEntity.getUsuariosByTipo(tipoUsuario, filtro);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getDatosUsuario(java.lang.String)
	 */
	public UsuarioVO getDatosUsuario(String idUsuario) {
		return _usuarioDEntity.getUsuario(idUsuario);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws AppUserException
	 * @see salas.model.GestionSalasConsultaBI#crearUsuarioExterno(java.lang.String)
	 */
	public UsuarioVO crearUsuarioExterno(String idUsrSisExtGestor)
			throws AppUserException, UsuariosNotAllowedException {

		UsuarioVO usuario;

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		AuthenticationConnector authClient = AuthenticationConnectorFactory
				.getConnector(TiposUsuario.ORGANO_EXTERNO_VALUE, params);

		// Autenticación del usuario
		UserInfo userInfo = authClient.getUserInfo(idUsrSisExtGestor);
		if (userInfo == null)
			throw new AppUserException(AppUserException.USER_NOT_FOUND);

		// Crear el usuario
		usuario = new UsuarioVO();
		usuario.setNombre(userInfo.getName());
		usuario.setApellidos(userInfo.getSurname());
		usuario.setEmail(userInfo.getEmail());
		usuario.setDireccion(userInfo.getAddress());
		usuario.setTipo(TiposUsuario.ORGANO_EXTERNO_VALUE);
		usuario.setHabilitado(Constants.TRUE_STRING);
		usuario.setFMaxVigencia(null);
		usuario.setIdUsrsExtGestor(userInfo.getExternalUserId());
		usuario.setDescripcion(userInfo.getDescription());

		// Obtener la información del usuario en Archivo
		usuario = getGestionControlUsusarios().crearUsuarioExterno(usuario);
		if (logger.isDebugEnabled())
			logger.debug("UsuarioVO creado:" + Constants.NEWLINE + usuario);

		return usuario;
	}

	public boolean isUsuarioAsociadoAUsuarioSalasConsulta(
			String idUsuarioExterno) {
		int numUsuarios = _usuarioSalaConsultaDBEntity
				.getCountUsuariosByIdExterno(idUsuarioExterno,
						Constants.TRUE_STRING);

		if (numUsuarios > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isUsuarioAsociadoAUsuarioSalasConsultaDistintoUsuario(
			String idUsuarioExterno, String idDistintoUsuario) {
		int numUsuarios = _usuarioSalaConsultaDBEntity
				.getCountUsuariosByIdExternoDistintosUsuario(idUsuarioExterno,
						idDistintoUsuario, Constants.TRUE_STRING);

		if (numUsuarios > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getUsuariosConPermisoConsultaSala(java.lang.String)
	 */
	public List getUsuariosConPermisoConsultaSala(String idArchivo) {
		return getUsuariosConPermisoConsultaSala(idArchivo, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getUsuariosConPermisoConsultaSala(java.lang.String,
	 *      java.lang.String)
	 */
	public List getUsuariosConPermisoConsultaSala(String idArchivo,
			String filtro) {

		return _usuarioSalaConsultaDBEntity.getUsuariosConPermisoConsultaSala(
				idArchivo, filtro);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getExpedientesUsuarioConsulta(salas.vos.BusquedaUsuarioSalaConsultaVO)
	 */
	public List getExpedientesUsuarioConsulta(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioConsulta) {
		return _detalleDBEntity
				.getExpedientesUsuarioConsulta(busquedaUsuarioConsulta);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getCountUsuariosConsulta(salas.vos.BusquedaUsuarioSalaConsultaVO)
	 */
	public int getCountUsuariosConsulta(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala) {
		return _usuarioSalaConsultaDBEntity.getCountUsuariosConsultaSala(
				busquedaUsuarioSala.getFechaIniExp(),
				busquedaUsuarioSala.getFechaFinExp());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getCountUnidadesConsultadas(salas.vos.BusquedaUsuarioSalaConsultaVO)
	 */
	public int getCountUnidadesConsultadas(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala) {
		return _detalleDBEntity.getCountUnidadesConsultadas(
				busquedaUsuarioSala.getFechaIniExp(),
				busquedaUsuarioSala.getFechaFinExp());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getCountRegistros(salas.vos.BusquedaUsuarioSalaConsultaVO)
	 */
	public int getCountRegistros(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala) {
		return _registroConsultaSalaDBEntity.getCountRegistros(
				busquedaUsuarioSala.getFechaIniExp(),
				busquedaUsuarioSala.getFechaFinExp());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getUnidadesConsultadas(salas.vos.BusquedaUsuarioSalaConsultaVO)
	 */
	public List getUnidadesConsultadas(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala) {
		return _detalleDBEntity.getUnidadesConsultadas(busquedaUsuarioSala);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.model.GestionSalasConsultaBI#getTemasUsuarioConsulta(salas.vos.BusquedaUsuarioSalaConsultaVO)
	 */
	public List getTemasUsuarioConsulta(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala) {
		String idUsrCSala = null;
		if (busquedaUsuarioSala != null) {
			idUsrCSala = busquedaUsuarioSala.getId();
		}
		return _usuarioSalaConsultaDBEntity.getTemasUsuarioConsulta(idUsrCSala);
	}

	/************************ FIN USUARIOS ********************************/

	/** CONSULTAS **/

	public boolean isUsuarioSalaConConsultas(String idcsusrsala) {
		_consultaDBEntity.getCountConsultasByIdUsrSalaNotInEstados(idcsusrsala,
				null);

		int consultas = _consultaDBEntity
				.getCountConsultasByIdUsrSalaNotInEstados(idcsusrsala, null);

		if (consultas > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isUsuarioConConsultasSinDevolver(String idcsusrsala) {
		int[] estadosNotIn = new int[] {
				ConsultasConstants.ESTADO_CONSULTA_DENEGADA,
				ConsultasConstants.ESTADO_CONSULTA_DEVUELTA };

		int consultas = _consultaDBEntity
				.getCountConsultasByIdUsrSalaNotInEstados(idcsusrsala,
						estadosNotIn);

		if (consultas > 0) {
			return true;
		} else {
			return false;
		}
	}
	/** **/
}