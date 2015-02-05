package common.manager.impl;

import java.util.List;

import common.manager.IArchidocManager;
import common.util.ListUtils;

import deposito.SignaturaUtil;

public abstract class ArchidocManagerBaseImpl implements IArchidocManager {

	public boolean hasPermisos(String idUsuario, String[] permisos) {
		List listaPermisos = getPermisoRolDBEntity().getPermisosUsuario(
				idUsuario, permisos);

		if (ListUtils.isNotEmpty(listaPermisos)) {
			return true;
		}
		return false;
	}

	public boolean existeSignaturaEnOtraTransferencia(String idArchivo,
			String signatura, String idRelEntrega,
			boolean signaturacionPorArchivo) {
		return SignaturaUtil.existeSignaturaEnOtraTransferencia(signatura,
				idArchivo, idRelEntrega, getUnidadInstalacionDBEntity(),
				getUnidadInstalacionReeaDBEntity(), getUiReeaCRDBEntity(),
				getHuecoDBEntity(), signaturacionPorArchivo);
	}

	public boolean existeSignaturaDeposito(String idArchivo, String signatura,
			boolean signaturacionPorArchivo) {
		return SignaturaUtil.existeSignaturaDeposito(signatura, idArchivo,
				getUnidadInstalacionDBEntity(),
				getUnidadInstalacionReeaDBEntity(), getUiReeaCRDBEntity(),
				getHuecoDBEntity(), signaturacionPorArchivo);
	}

}
