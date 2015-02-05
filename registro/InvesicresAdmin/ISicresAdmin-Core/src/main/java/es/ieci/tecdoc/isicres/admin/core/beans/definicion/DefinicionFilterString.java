package es.ieci.tecdoc.isicres.admin.core.beans.definicion;

import es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFld;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminLibroManager;

public class DefinicionFilterString extends DefinicionFilterField{

	public DefinicionFilterString(int tipoLibro) {
		super(tipoLibro);
	}

	public FiltroImpl decode(String[] cadenas) {
		ArchiveFld field = ISicresRPAdminLibroManager.getCampo(tipoLibro, cadenas[0]);
		FiltroImpl filtro = new FiltroImpl();
		filtro.setCampo(field.getId());
		filtro.setOperador(DefinicionFilterField.textToIdFilter(cadenas[1]));

		if (cadenas.length > 3){
			filtro.setValor(cadenas[3]);
		}else{
			filtro.setValor("");
		}

		if(cadenas.length>4){
			filtro.setNexo(DefinicionFilterField.textToIdNexo(cadenas[4]));
		}
		else{
			filtro.setNexo("");
		}

		return filtro;
	}

	public String encodeFilter(FiltroImpl filtro) {
		StringBuffer sb = new StringBuffer();
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo()).getName());
		sb.append("|");
		sb.append(DefinicionFilterField.idFilterToTexto(filtro.getOperador()));
		sb.append("|");
		sb.append("0");
		sb.append("|");
		sb.append(filtro.getValor());
		sb.append("|");
		sb.append(DefinicionFilterField.idNexoToTexto(filtro.getNexo()));
		sb.append("|");
		sb.append("$");
		return sb.toString();
	}

	public String encodeWhere(FiltroImpl filtro, String entidad) {
		StringBuffer sb = new StringBuffer();
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo()).getColName());

		if(filtro.getOperador()==FiltroImpl.OPERADOR_IGUAL_A)
			sb.append(" = '").append(filtro.getValor().toUpperCase()).append("'");
		else if(filtro.getOperador()==FiltroImpl.OPERADOR_EMPIEZA_POR)
			sb.append(" LIKE '").append(filtro.getValor().toUpperCase()).append("%'");
		else if(filtro.getOperador()==FiltroImpl.OPERADOR_TERMINA_POR)
			sb.append(" LIKE '%").append(filtro.getValor().toUpperCase()).append("'");
		else if(filtro.getOperador()==FiltroImpl.OPERADOR_CONTIENE)
			sb.append(" LIKE '%").append(filtro.getValor().toUpperCase()).append("%'");
		else if(filtro.getOperador()==FiltroImpl.OPERADOR_DISTINTO_DE)
			sb.append(" <> '").append(filtro.getValor().toUpperCase()).append("'");
		else if(filtro.getOperador()==FiltroImpl.OPERADOR_MAYOR_QUE)
			sb.append(" > '").append(filtro.getValor().toUpperCase()).append("'");
		else if(filtro.getOperador()==FiltroImpl.OPERADOR_MENOR_QUE)
			sb.append(" < '").append(filtro.getValor().toUpperCase()).append("'");
		else if(filtro.getOperador()==FiltroImpl.OPERADOR_MAYOR_O_IGUAL_QUE)
			sb.append(" >= '").append(filtro.getValor().toUpperCase()).append("'");
		else if(filtro.getOperador()==FiltroImpl.OPERADOR_MENOR_O_IGUAL_QUE)
			sb.append(" <= '").append(filtro.getValor().toUpperCase()).append("'");

		sb.append(" ").append(filtro.getNexo()).append(" ");

		return sb.toString();
	}

	public int[] getOperators() {
		return new int[]{
				FiltroImpl.OPERADOR_IGUAL_A,
				FiltroImpl.OPERADOR_EMPIEZA_POR,
				FiltroImpl.OPERADOR_TERMINA_POR,
				FiltroImpl.OPERADOR_CONTIENE,
				FiltroImpl.OPERADOR_DISTINTO_DE,
				FiltroImpl.OPERADOR_MAYOR_QUE,
				FiltroImpl.OPERADOR_MENOR_QUE,
				FiltroImpl.OPERADOR_MAYOR_O_IGUAL_QUE,
				FiltroImpl.OPERADOR_MENOR_O_IGUAL_QUE
			};
	}

}
