package ieci.tecdoc.sgm.rpadmin.manager;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Listas;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativa;
import ieci.tecdoc.sgm.rpadmin.beans.FiltroImpl;
import ieci.tecdoc.sgm.rpadmin.beans.FiltrosImpl;
import ieci.tecdoc.sgm.rpadmin.beans.IDocArchHDRImpl;
import ieci.tecdoc.sgm.rpadmin.beans.IDocArchsHDRImpl;
import ieci.tecdoc.sgm.rpadmin.beans.IUserObjsPermsImpl;
import ieci.tecdoc.sgm.rpadmin.beans.IVolArchListImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresContadorCentralImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresContadoresOficinasImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresLibroEstadoImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresLibrosOficinasImpl;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSicres;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSigem;
import ieci.tecdoc.sgm.rpadmin.exception.RPAdminDAOException;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterCombo;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterFecha;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterField;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterOficina;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterString;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterTipoAsunto;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterUnidadAdm;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionLibroEntrada;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionLibroRegistro;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionLibroSalida;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminLibroManager;

/*$Id*/

public class RPAdminLibroManager {

	private static final Logger logger = Logger
			.getLogger(RPAdminLibroManager.class);

	public static IDocArchsHDRImpl obtenerLibros(int tipoLibro, String entidad)
			throws RPAdminDAOException {

		IDocArchsHDRImpl archivadores = null;

		try {
			archivadores = AdapterVOSigem
					.adapterSIGEMIDocArchsHDRImpl(ISicresRPAdminLibroManager
							.obtenerLibros(tipoLibro, entidad));
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo libros");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return archivadores;
	}

	public static IDocArchHDRImpl getArchivador(int bookId, String entidad)
			throws RPAdminDAOException {

		IDocArchHDRImpl libro = null;

		try {
			libro = AdapterVOSigem
					.adapterSIGEMIDocArchHDRImpl(ISicresRPAdminLibroManager
							.getArchivador(bookId, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo libro");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return libro;
	}

	public static SicresLibroEstadoImpl getLibro(int bookId, String entidad)
			throws RPAdminDAOException {

		SicresLibroEstadoImpl libro = null;

		try {
			libro = AdapterVOSigem
					.adapterSIGEMSicresLibroEstadoImpl(ISicresRPAdminLibroManager
							.getLibro(bookId, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo libro");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return libro;
	}

	public static int crearLibro(IDocArchHDRImpl archivador, SicresLibroEstadoImpl libro,
			String entidad) throws RPAdminDAOException {

		int idLibro;

		try {
			idLibro = ISicresRPAdminLibroManager.crearLibro(
					AdapterVOSicres.adapterISicresIDocArchHDRImpl(archivador),
					AdapterVOSicres.adapterISicresSicresLibroEstadoImpl(libro), entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error creando el libro");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return idLibro;

	}

	public static void editarLibro(SicresLibroEstadoImpl libro,
			IDocArchHDRImpl archivador, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminLibroManager.editarLibro(
					AdapterVOSicres.adapterISicresSicresLibroEstadoImpl(libro),
					AdapterVOSicres.adapterISicresIDocArchHDRImpl(archivador),
					entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando el libro");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void eliminarLibro(int idLibro, String entidad)
			throws RPAdminDAOException {

		try {

			ISicresRPAdminLibroManager.eliminarLibro(idLibro, entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando el libro");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static IVolArchListImpl getLista(int bookId, String entidad)
			throws RPAdminDAOException {

		IVolArchListImpl lista = null;
		try {
			lista = AdapterVOSigem.adapterSIGEMIVolArchListImpl(ISicresRPAdminLibroManager.getLista(bookId, entidad));
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo lista");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return lista;

	}

	public static void asociarListaALibro(IVolArchListImpl lista, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminLibroManager.asociarListaALibro(
					AdapterVOSicres.adapterISicresIVolArchListImpl(lista), entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo lista");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static IUserObjsPermsImpl obtenerPermisos(int bookId, int tipo,
			String entidad) throws RPAdminDAOException {

		IUserObjsPermsImpl permisos = null;
		try {
			permisos = AdapterVOSigem
					.adapterSIGEMIUserObjsPermsImpl(ISicresRPAdminLibroManager
							.obtenerPermisos(bookId, tipo, entidad));
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo permisos");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return permisos;
	}

	public static SicresContadoresOficinasImpl obtenerContadoresOficinas(
			int anyo, int tipo, String entidad) throws RPAdminDAOException {

		SicresContadoresOficinasImpl contadores = null;

		try {
			contadores = AdapterVOSigem
					.adapterSIGEMSicresContadoresOficinasImpl(ISicresRPAdminLibroManager
							.obtenerContadoresOficinas(anyo, tipo, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo contadores de oficinas");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return contadores;
	}

	public static void editarContadoresOficinas(
			SicresContadoresOficinasImpl contadores, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminLibroManager.editarContadoresOficinas(AdapterVOSicres
					.adapterISicresSicresContadoresOficinasImpl(contadores),
					entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando los contadores");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static SicresContadorCentralImpl obtenerContadorCentral(int anyo,
			int tipo, String entidad) throws RPAdminDAOException {

		SicresContadorCentralImpl contador = null;
		try {
			contador = AdapterVOSigem
					.adapterSIGEMSicresContadorCentralImpl(ISicresRPAdminLibroManager
							.obtenerContadorCentral(anyo, tipo, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo contador central");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return contador;

	}

	public static void editarContadorCentral(
			SicresContadorCentralImpl contador, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminLibroManager
					.editarContadorCentral(AdapterVOSicres
							.adapterISicresSicresContadorCentralImpl(contador),
							entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando el contadorCentral");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static SicresLibrosOficinasImpl obtenerOficinas(int bookId,
			String entidad) throws RPAdminDAOException {

		SicresLibrosOficinasImpl oficinas = null;

		try {
			oficinas = AdapterVOSigem
					.adapterSIGEMSicresLibrosOficinasImpl(ISicresRPAdminLibroManager
							.obtenerOficinas(bookId, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo oficinas");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return oficinas;
	}

	public static void asociarOficinasALibro(
			SicresLibrosOficinasImpl libroOficinas, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminLibroManager.asociarOficinasALibro(AdapterVOSicres
					.adapterISicresSicresLibrosOficinasImpl(libroOficinas),
					entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error asociando oficina");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void desasociarOficinasALibro(
			SicresLibrosOficinasImpl libroOficinas, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminLibroManager.desasociarOficinasALibro(AdapterVOSicres
					.adapterISicresSicresLibrosOficinasImpl(libroOficinas),
					entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error desasociando oficina");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void actualizarNumeracionOficinaAsociadaALibro(int idBook,
				int idOfic, int numeration, String entidad) throws RPAdminDAOException {

		try {
			ISicresRPAdminLibroManager
					.actualizarNumeracionOficinaAsociadaALibro(idBook, idOfic,
							numeration, entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error actualizando numeracion oficinas asociadas a libro");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void modificarPermisos(IUserObjsPermsImpl permisosDelete,
			IUserObjsPermsImpl permisosAdd, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminLibroManager.modificarPermisos(
					AdapterVOSicres.adapterISicresIUserObjsPermsImpl(permisosDelete),
					AdapterVOSicres.adapterISicresIUserObjsPermsImpl(permisosAdd),
					entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando el usuario");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void configureFiltros(int tipoFiltro, int tipoLibro,
			int idLibro, int idUserOfic, FiltrosImpl filtros, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminLibroManager.configureFiltros(tipoFiltro, tipoLibro,
					idLibro, idUserOfic,
					AdapterVOSicres.adapterISicresFiltrosImpl(filtros), entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error configurando filtros");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static FiltrosImpl obtenerFiltros(int tipoFiltro, int tipoLibro,
			int idLibro, int idUserOfic, String entidad)
			throws RPAdminDAOException {

		FiltrosImpl filtros;
		try {
			filtros = AdapterVOSigem
					.adapterSIGEMFiltrosImpl(ISicresRPAdminLibroManager
							.obtenerFiltros(tipoFiltro, tipoLibro, idLibro,
									idUserOfic, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo filtros");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return filtros;

	}

	public static Listas obtenerListas(String entidad)
			throws RPAdminDAOException {
		Listas listas = null;
		try {
			ServicioEstructuraOrganizativa oServicio = LocalizadorServicios.getServicioEstructuraOrganizativa();
			listas = oServicio.getListas(entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo listas de volúmenes");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return listas;
	}

	public static int[] getOperadores(String tipo, String entidad) {

		return ISicresRPAdminLibroManager.getOperadores(tipo, entidad);

	}

	public static DefinicionLibroRegistro getDefinicionLibroRegistro(int tipo) {
		if (tipo == DefinicionLibroRegistro.LIBRO_ENTRADA)
			return new DefinicionLibroEntrada();
		else
			return new DefinicionLibroSalida();
	}

	public static DefinicionFilterField getDefinicionFiltro(String tipo,
			int tipoLibro, String entidad) {

		if (FiltroImpl.TIPO_STRING.equals(tipo))
			return new DefinicionFilterString(tipoLibro);
		else if (FiltroImpl.TIPO_DATE.equals(tipo))
			return new DefinicionFilterFecha(tipoLibro);
		else if (FiltroImpl.TIPO_COMBO.equals(tipo))
			return new DefinicionFilterCombo(tipoLibro);
		else if (FiltroImpl.TIPO_OFICINAS.equals(tipo))
			return new DefinicionFilterOficina(tipoLibro, entidad);
		else if (FiltroImpl.TIPO_UNIDADES_ADMIN.equals(tipo))
			return new DefinicionFilterUnidadAdm(tipoLibro, entidad);
		else if (FiltroImpl.TIPO_ASUNTO.equals(tipo)) {
			return new DefinicionFilterTipoAsunto(tipoLibro, entidad);
		} else {
			return null;
		}

	}

	public static ArchiveFld getCampo(int tipoLibro, int id) {
		return AdapterVOSigem.adapterSIGEMArchiveFld(ISicresRPAdminLibroManager.getCampo(tipoLibro, id));
	}

	public static ArchiveFld getCampo(int tipoLibro, String nombre) {
		return AdapterVOSigem.adapterSIGEMArchiveFld(ISicresRPAdminLibroManager.getCampo(tipoLibro, nombre));
	}

}
