package gcontrol.model;

import gcontrol.db.IArchivoDbEntity;
import gcontrol.db.INivelArchivoDbEntity;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.NivelArchivoVO;

import java.util.Iterator;
import java.util.List;

import common.bi.GestionNivelesArchivoBI;
import common.bi.ServiceBase;
import common.util.ListUtils;

public class GestionNivelesArchivoBIImpl extends ServiceBase implements
		GestionNivelesArchivoBI {

	INivelArchivoDbEntity nivelArchivoDbEntity;
	IArchivoDbEntity archivoDbEntity;

	public GestionNivelesArchivoBIImpl(
			INivelArchivoDbEntity _nivelArchivoDbEntity,
			IArchivoDbEntity _archivoDbEntity) {
		super();
		this.nivelArchivoDbEntity = _nivelArchivoDbEntity;
		this.archivoDbEntity = _archivoDbEntity;
	}

	private void actualizaNivelArchivo(NivelArchivoVO nivelArchivoVO) {
		if (nivelArchivoVO != null) {
			// Modificación del tipo de documento vital
			nivelArchivoDbEntity.update(nivelArchivoVO);
		}
	}

	public List getListaNivelesArchivo() {
		return nivelArchivoDbEntity.getAll();
	}

	public NivelArchivoVO getNivelArchivoXId(String idNivelArchivo) {
		return nivelArchivoDbEntity.getNivelArchivoXId(idNivelArchivo);
	}

	public List getNivelesArchivoXId(String[] idNivelesArchivo) {
		return nivelArchivoDbEntity.getNivelesArchivoXId(idNivelesArchivo);
	}

	public NivelArchivoVO getNivelArchivoXNombre(String nombre) {
		return nivelArchivoDbEntity.getNivelArchivoXNombre(nombre);
	}

	private NivelArchivoVO insertaNivelArchivo(NivelArchivoVO nivelArchivoVO) {
		if (nivelArchivoVO != null) {
			nivelArchivoDbEntity.insert(nivelArchivoVO);
		}
		return nivelArchivoVO;
	}

	public void actualizarListaNiveles(List listaNivelesArchivo,
			List listaNivelesArchivoEliminados) throws ArchivosException {
		iniciarTransaccion();

		// Eliminar los Archivos

		if (!ListUtils.isEmpty(listaNivelesArchivoEliminados)) {
			Iterator iterador = listaNivelesArchivoEliminados.iterator();
			while (iterador.hasNext()) {
				Object objeto = iterador.next();
				if (objeto instanceof NivelArchivoVO) {
					NivelArchivoVO nivelArchivoVO = (NivelArchivoVO) objeto;
					if (!nivelArchivoVO.isNuevoElemento()) {
						eliminarNivel(nivelArchivoVO);
					}

				}
			}
		}

		// Actualizar los Valores
		if (!ListUtils.isEmpty(listaNivelesArchivo)) {
			Iterator iterador = listaNivelesArchivo.iterator();
			while (iterador.hasNext()) {
				Object objeto = iterador.next();
				if (objeto instanceof NivelArchivoVO) {
					NivelArchivoVO nivelArchivoVO = (NivelArchivoVO) objeto;
					if (nivelArchivoVO.isNuevoElemento()) {
						insertaNivelArchivo(nivelArchivoVO);
					} else {
						actualizaNivelArchivo(nivelArchivoVO);
					}
				}
			}
		}

		commit();
	}

	private void eliminarNivel(NivelArchivoVO nivelArchivoVO)
			throws ArchivosException {
		if (nivelArchivoVO != null) {
			List listaArchivos = archivoDbEntity
					.getArchivosXNivel(nivelArchivoVO.getId());

			if (ListUtils.isEmpty(listaArchivos)) {
				nivelArchivoDbEntity.delete(nivelArchivoVO);
			} else {
				throw new ArchivosException(
						ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_NIVEL_ARCHIVO);
			}
		}

	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionNivelesArchivoBI#setNivelesArchivoAsociado(java.util
	 * .List, java.util.List)
	 */
	public List setNivelesArchivoAsociado(List listaNiveles, List listaArchivos) {
		if (!ListUtils.isEmpty(listaNiveles)
				&& !ListUtils.isEmpty(listaArchivos))
			for (int i = 0; i < listaNiveles.size(); i++) {
				NivelArchivoVO nivelArchivoVO = (NivelArchivoVO) listaNiveles
						.get(i);
				for (int j = 0; j < listaArchivos.size(); j++) {
					ArchivoVO archivoVO = (ArchivoVO) listaArchivos.get(j);
					if (nivelArchivoVO.getId().equalsIgnoreCase(
							archivoVO.getIdnivel())) {
						nivelArchivoVO.setArchivoAsociado(true);
						break;
					}
				}

			}
		return listaNiveles;

	}
}
