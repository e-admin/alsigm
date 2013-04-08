package gcontrol.exceptions;

import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;

public class UsuariosNotAllowedException extends ActionNotAllowedException {

	private static final long serialVersionUID = 1L;
	public static final int XUSUARIO_YA_EXISTE = 1;
	public static final int XORGANO_NO_EXISTE_EL_SISTEMA_ES_NECESARIO_CREARLO = 2;
	public static final int XLISTA_NO_BORRABLE_TIENE_ELEMENTOS_DEL_CUADRO_ASOCIADOS = 3;
	public static final int XLISTA_NO_BORRABLE_TIENE_DESCRIPTORES_ASOCIADOS = 4;
	public static final int XLISTA_NO_BORRABLE_TIENE_FORMATOS_DE_FICHA_ASOCIADOS = 5;

	public static final int USUARIO_TIENE_PREVISIONES_EN_ELABORACION = 6;
	public static final int USUARIO_TIENE_RELACIONES_EN_ELABORACION = 7;
	public static final int USUARIO_ES_GESTOR_SERIES = 8;
	public static final int USUARIO_TIENE_PRESTAMOS_EN_CURSO = 9;
	public static final int USUARIO_TIENE_CONSULTAS_EN_CURSO = 10;
	public static final int USUARIO_NO_PERTENECE_A_NINGUN_ARCHIVO = 11;
	public static final int USUARIO_TIENE_RELACIONES = 12;
	public static final int XLISTA_NO_BORRABLE_PERTENECE_A_PRODUCTOR_SERIE = 13;
	public static final int USUARIO_TIENE_ASOCIADO_USUARIO_SALA_CONSULTA = 14;

	public UsuariosNotAllowedException(int codError) {
		super("", codError, ArchivoModules.USUARIOS_MODULE);
	}

}
