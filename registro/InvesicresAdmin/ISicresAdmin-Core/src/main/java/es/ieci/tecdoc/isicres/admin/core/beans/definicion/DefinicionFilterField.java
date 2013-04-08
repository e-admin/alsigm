package es.ieci.tecdoc.isicres.admin.core.beans.definicion;



import java.sql.SQLException;
import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.beans.LibroBean;
import es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;

public abstract class DefinicionFilterField {

	protected int tipoLibro;
	public static ArrayList filtros = new ArrayList();
	static {
		filtros.add("filtro 0");
		filtros.add("igual a");
		filtros.add("empieza por");
		filtros.add("termina en");
		filtros.add("contiene");
		filtros.add("distinto de");
		filtros.add("mayor que");
		filtros.add("menor que");
		filtros.add("mayor o igual que");
		filtros.add("menor o igual que");
		filtros.add("en la semana");
		filtros.add("en el mes");
		filtros.add("en el año");
		filtros.add("posterior a");
		filtros.add("anterior a");
	}

	public static String idFilterToTexto(int id) {
		return filtros.get(id).toString();
	}

	public static int textToIdFilter(String texto){
		return filtros.indexOf(texto);
	}

	public static String idNexoToTexto(String id) {
		if("AND".equals(id))
			return "y";
		else if("OR".equals(id))
			return "o";
		else
			return "";
	}

	public static String textToIdNexo(String texto){
		if("y".equals(texto))
			return "AND";
		else if("o".equals(texto))
			return "OR";
		else
			return "";
	}

	public static String idToType(int tipoLibro, int id) {
		if(tipoLibro==LibroBean.LIBRO_ENTRADA) {
			switch(id){
			case FiltroImpl.CAMPO_FECHA_REGISTRO:
			case FiltroImpl.CAMPO_FECHA_TRABAJO:
			case FiltroImpl.CAMPO_FECHA_REGISTRO_ORIGINAL: return FiltroImpl.TIPO_DATE;
			case FiltroImpl.CAMPO_ESTADO:
			case FiltroImpl.CAMPO_TIPO_REGISTRO_ORIGINAL: return FiltroImpl.TIPO_COMBO;
			case FiltroImpl.CAMPO_OFICINA_REGISTRO: return FiltroImpl.TIPO_OFICINAS;
			case FiltroImpl.CAMPO_ORIGEN:
			case FiltroImpl.CAMPO_DESTINO: return FiltroImpl.TIPO_UNIDADES_ADMIN;
			case FiltroImpl.CAMPO_TIPO_ASUNTO_ENTRADA: return FiltroImpl.TIPO_ASUNTO;
			case FiltroImpl.CAMPO_FECHA_DEL_DOCUMENTO_ENTRADA:  return FiltroImpl.TIPO_DATE;
			default:
				return FiltroImpl.TIPO_STRING;
			}
		} else {
			switch(id){
			case FiltroImpl.CAMPO_FECHA_REGISTRO:
			case FiltroImpl.CAMPO_FECHA_TRABAJO: return FiltroImpl.TIPO_DATE;
			case FiltroImpl.CAMPO_ESTADO: return FiltroImpl.TIPO_COMBO;
			case FiltroImpl.CAMPO_OFICINA_REGISTRO: return FiltroImpl.TIPO_OFICINAS;
			case FiltroImpl.CAMPO_ORIGEN:
			case FiltroImpl.CAMPO_DESTINO: return FiltroImpl.TIPO_UNIDADES_ADMIN;
			case FiltroImpl.CAMPO_TIPO_ASUNTO_SALIDA: return FiltroImpl.TIPO_ASUNTO;
			case FiltroImpl.CAMPO_FECHA_DEL_DOCUMENTO_SALIDA:  return FiltroImpl.TIPO_DATE;
			default:
				return FiltroImpl.TIPO_STRING;
			}
		}
	}

	public DefinicionFilterField(int tipoLibro) {
		this.tipoLibro = tipoLibro;
	}
	public abstract int[] getOperators();
	public abstract String encodeWhere(FiltroImpl filtro, String entidad)
			throws ISicresAdminDAOException, SQLException;
	public abstract String encodeFilter(FiltroImpl filtro);
	public abstract FiltroImpl decode(String[] cadenas);
}
