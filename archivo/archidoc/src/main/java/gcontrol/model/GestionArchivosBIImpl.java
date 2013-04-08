package gcontrol.model;

import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IFondoDbEntity;
import gcontrol.db.IArchivoDbEntity;
import gcontrol.db.INivelArchivoDbEntity;
import gcontrol.vos.ArchivoVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import salas.db.IEdificioDBEntity;
import salas.db.IUsuarioArchivoSalasConsultaDBEntity;
import solicitudes.consultas.db.IConsultaDBEntity;

import common.Constants;
import common.bi.GestionArchivosBI;
import common.bi.ServiceBase;
import common.util.ListUtils;

import deposito.db.IDepositoDbEntity;

public class GestionArchivosBIImpl extends ServiceBase implements
		GestionArchivosBI {

	IArchivoDbEntity archivoDbEntity;
	INivelArchivoDbEntity nivelArchivoDbEntity;
	IFondoDbEntity fondoDbEntity;
	IElementoCuadroClasificacionDbEntity elementoCuadroClasificacion;
	IDepositoDbEntity depositoDbEntity;
	IEdificioDBEntity edificioDBEntity;
	IConsultaDBEntity consultasDBEntity;
	IUsuarioArchivoSalasConsultaDBEntity usuarioArchivoConsultaSalaDBEntity;

	public GestionArchivosBIImpl(
			IArchivoDbEntity archivoDbEntity,
			IFondoDbEntity fondoDbEntity,
			IElementoCuadroClasificacionDbEntity elementoCuadroClasificacion,
			IDepositoDbEntity depositoDbEntity,
			IEdificioDBEntity edificioDBEntity,
			IConsultaDBEntity consultasDBEntity,
			IUsuarioArchivoSalasConsultaDBEntity usuarioArchivoConsultaSalaDBEntity) {
		super();
		this.archivoDbEntity = archivoDbEntity;
		this.fondoDbEntity = fondoDbEntity;
		this.elementoCuadroClasificacion = elementoCuadroClasificacion;
		this.depositoDbEntity = depositoDbEntity;
		this.edificioDBEntity = edificioDBEntity;
		this.consultasDBEntity = consultasDBEntity;
		this.usuarioArchivoConsultaSalaDBEntity = usuarioArchivoConsultaSalaDBEntity;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionArchivosBI#getArchivoXId(java.lang.String)
	 */
	public ArchivoVO getArchivoXId(String idArchivo) {
		return archivoDbEntity.getArchivoXId(idArchivo);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionArchivosBI#getArchivoXId(java.lang.String[])
	 */
	public List getArchivosXId(Object[] idsArchivo) {

		List ret = new ArrayList();
		if (idsArchivo != null && idsArchivo.length > 0)
			ret = archivoDbEntity.getArchivosXId(idsArchivo);

		return ret;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionArchivosBI#getArchivosXCodArchivo(java.lang.String)
	 */
	public ArchivoVO getArchivosXCodArchivo(String codArchivo) {
		return archivoDbEntity.getArchivoXCodArchivo(codArchivo);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionArchivosBI#getListaArchivos()
	 */
	public List getListaArchivos() {
		return archivoDbEntity.getAll();
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionArchivosBI#getPosiblesArchivosReceptores(java.lang.Integer
	 * )
	 */
	public List getPosiblesArchivosReceptores(String orden) {
		return archivoDbEntity.getArchivosConOrdenNivelMayor(orden);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.bi.GestionArchivosBI#getArchivoReceptores(java.util.List)
	 */
	public List getArchivosReceptores(List ltArchivosRemitentes) {
		List receptores = new ArrayList();
		if (ltArchivosRemitentes != null) {

			ListIterator it = ltArchivosRemitentes.listIterator();

			while (it.hasNext()) {

				ArchivoVO archivoBDVO = (ArchivoVO) it.next();

				if (archivoBDVO != null) {
					List receptoresArchivo = archivoDbEntity
							.getArchivosReceptores(archivoBDVO.getId());
					if ((receptoresArchivo != null)
							&& (!receptoresArchivo.isEmpty())) {
						receptores.addAll(receptoresArchivo);
					}
				}
			}
		}
		return receptores;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionArchivosBI#getArchivosReceptores(java.lang.String)
	 */
	public List getArchivosReceptores(String idArchivoRemitente) {
		List receptores = new ArrayList();
		if (idArchivoRemitente != null) {
			iniciarTransaccion();

			// Comprobar si el nivel ha cambiado
			ArchivoVO archivoBDVO = archivoDbEntity
					.getArchivoXId(idArchivoRemitente);
			if (archivoBDVO != null) {
				receptores = archivoDbEntity
						.getArchivosReceptores(idArchivoRemitente);
				if ((receptores != null)
						&& (!receptores.isEmpty())
						&& (archivoBDVO.getIdreceptordefecto() != null)
						&& (!archivoBDVO.getIdreceptordefecto().equals(
								Constants.STRING_EMPTY))) {
					ArchivoVO remitente = new ArchivoVO();
					remitente.setId(archivoBDVO.getIdreceptordefecto());
					int index = receptores.indexOf(remitente);
					if (index != -1) {
						// Meterlo en la primera posición por defecto
						remitente = (ArchivoVO) receptores.get(index);
						receptores.remove(index);
						receptores.add(0, remitente);
					}
				}
			}

			commit();
		}
		return receptores;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionArchivosBI#getArchivosReceptores(java.lang.String)
	 */
	public List getArchivosReceptoresExtraordinaria(
			String idArchivoReceptorDefecto) {
		List receptores = new ArrayList();
		iniciarTransaccion();

		receptores = archivoDbEntity.getAll();
		if ((receptores != null) && (!receptores.isEmpty())
				&& (idArchivoReceptorDefecto != null)
				&& (!idArchivoReceptorDefecto.equals(Constants.STRING_EMPTY))) {
			ArchivoVO remitente = new ArchivoVO();
			remitente.setId(idArchivoReceptorDefecto);
			int index = receptores.indexOf(remitente);
			if (index != -1) {
				// Meterlo en la primera posición por defecto
				remitente = (ArchivoVO) receptores.get(index);
				receptores.remove(index);
				receptores.add(0, remitente);
			}
		}

		commit();

		return receptores;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionArchivosBI#actualizarArchivo(gcontrol.vos.ArchivoVO)
	 */
	public void actualizarArchivo(ArchivoVO archivoVO) {
		if (archivoVO != null) {
			iniciarTransaccion();

			// Comprobar si el nivel ha cambiado
			ArchivoVO archivoBDVO = archivoDbEntity.getArchivoXId(archivoVO
					.getId());

			if (!archivoVO.getIdnivel().equals(archivoBDVO.getIdnivel())) {
				List listaArchivosReceptores = archivoDbEntity
						.getArchivosXIdReceptorDefecto(archivoVO.getId());

				if (!ListUtils.isEmpty(listaArchivosReceptores)) {
					Iterator it = listaArchivosReceptores.iterator();
					while (it.hasNext()) {
						Object obj = it.next();
						if (obj instanceof ArchivoVO) {
							ArchivoVO archivo = (ArchivoVO) obj;

							// Poner a Null el idReceptor por defecto
							archivo.setIdreceptordefecto(null);
							archivoDbEntity.update(archivo);
						}
					}
				}
			}
			archivoDbEntity.update(archivoVO);
			commit();
		}
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionArchivosBI#insertarArchivo(gcontrol.vos.ArchivoVO)
	 */
	public ArchivoVO insertarArchivo(ArchivoVO archivoVO) {
		if (archivoVO != null) {
			iniciarTransaccion();
			archivoDbEntity.insert(archivoVO);
			commit();
		}
		return archivoVO;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see common.bi.GestionArchivosBI#eliminarArchivo(gcontrol.vos.ArchivoVO)
	 */
	public void eliminarArchivo(ArchivoVO archivoVO) throws ArchivosException {
		if (archivoVO != null) {
			// Comprobar que el archivo no existe en el cuadro de clasificación.
			List listaElementos = this.elementoCuadroClasificacion
					.getDistinctIdsArchivo(archivoVO.getId());
			if (!ListUtils.isEmpty(listaElementos)) {
				throw new ArchivosException(
						ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO);
			} else {
				// Comprobar que el archivo no está en ninguna ubicación
				List listaUbicaciones = this.depositoDbEntity
						.getUbicacionesXIdsArchivo(new String[] { archivoVO
								.getId() });
				if (!ListUtils.isEmpty(listaUbicaciones)) {
					throw new ArchivosException(
							ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_EN_UBICACION);
				} else {
					// Comprobar que no se esté utilizando en niguna edificio de
					// salas de consulta
					if (edificioDBEntity.getCountEdificiosByIdArchivo(archivoVO
							.getId()) > 0) {
						throw new ArchivosException(
								ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_EN_EDIFICIOS_SC);
					}

					// Comprobar que el archivo no tenga consultas(No denegadas
					// o devueltas) asociadas.
					List consultas = consultasDBEntity
							.getConsultasByIdArchivo(archivoVO.getId());
					if (consultas != null && !consultas.isEmpty()) {
						throw new ArchivosException(
								ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_CONSULTAS);
					}

					iniciarTransaccion();

					// Actualizar los archivos que tengan tengan el archivo como
					// receptor por defecto.
					List listaArchivosReceptores = archivoDbEntity
							.getArchivosXIdReceptorDefecto(archivoVO.getId());

					if (!ListUtils.isEmpty(listaArchivosReceptores)) {
						Iterator it = listaArchivosReceptores.iterator();
						while (it.hasNext()) {
							Object obj = it.next();
							if (obj instanceof ArchivoVO) {
								ArchivoVO archivo = (ArchivoVO) obj;

								// Poner a Null el idReceptor por defecto
								archivo.setIdreceptordefecto(null);
								archivoDbEntity.update(archivo);
							}
						}
					}

					// Eliminar relaciones con usuarios de consulta en sala
					usuarioArchivoConsultaSalaDBEntity
							.deleteByIdArchivo(archivoVO.getId());

					this.archivoDbEntity.delete(archivoVO);

					commit();
				}
			}

		}
	}

	public List getArchivosXNivel(String idNivel) {
		return archivoDbEntity.getArchivosXNivel(idNivel);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionArchivosBI#getArchivosXIdReceptorDefecto(java.lang.String
	 * )
	 */
	public List getArchivosXIdReceptorDefecto(String idArchivo) {
		return archivoDbEntity.getArchivosXIdReceptorDefecto(idArchivo);
	}

	/**
	 * Devuelve todos los archivos que tengan el tipo de signaturacion pasada
	 * como parametro
	 * 
	 * @param tipoSignaturacion
	 * @param idArchivo
	 * @return {List ArchivoVO}
	 */
	public List getArchivosXTipoSignaturacion(String tipoSignaturacion,
			String idArchivo) {
		return archivoDbEntity.getArchivosXTipoSignaturacion(tipoSignaturacion,
				idArchivo);
	}

	/*
	 * 
	 * public void actualizarArchivo(ArchivoVO nivelArchivoVO) { if
	 * (nivelArchivoVO != null) { iniciarTransaccion(); // Modificación del tipo
	 * de documento vital archivoDbEntity.update(nivelArchivoVO); commit(); } }
	 * 
	 * public List getListaArchivos() { return archivoDbEntity.getAll(); }
	 * 
	 * public NivelArchivoVO getNivelArchivoXId(String idNivelArchivo) { return
	 * archivoDbEntity.getNivelArchivoXId(idNivelArchivo); }
	 * 
	 * public List getNivelesArchivoXId(String[] idNivelesArchivo) { return
	 * archivoDbEntity.getNivelesArchivoXId(idNivelesArchivo); }
	 * 
	 * public NivelArchivoVO insertaNivelArchivo(NivelArchivoVO nivelArchivoVO)
	 * { if (nivelArchivoVO != null) { iniciarTransaccion();
	 * archivoDbEntity.insert(nivelArchivoVO); commit(); } return
	 * nivelArchivoVO; }
	 */
}
