package es.ieci.tecdoc.isicres.admin.core.beans.definicion;

import es.ieci.tecdoc.isicres.admin.beans.TipoAsunto;
import es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFld;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminLibroManager;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminTipoAsuntoManager;



public class DefinicionFilterTipoAsunto extends DefinicionFilterField{

	String entidad;
	public DefinicionFilterTipoAsunto(int tipoLibro, String entidad) {
		super(tipoLibro);
		this.entidad=entidad;
	}

	public FiltroImpl decode(String[] cadenas) {
		ArchiveFld field = ISicresRPAdminLibroManager.getCampo(tipoLibro, cadenas[0]);
		FiltroImpl filtro = new FiltroImpl();
		filtro.setCampo(field.getId());
		filtro.setOperador(DefinicionFilterField.textToIdFilter(cadenas[1]));
		if(cadenas.length>3){
			filtro.setValor(cadenas[3]);
		}else{
			filtro.setValor("");
		}
		if(cadenas.length>4)
			filtro.setNexo(DefinicionFilterField.textToIdNexo(cadenas[4]));
		else
			filtro.setNexo("");
		return filtro;
	}

	public String encodeFilter(FiltroImpl filtro) {
		StringBuffer sb = new StringBuffer();
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getName());
		sb.append("|");
		sb.append(DefinicionFilterField.idFilterToTexto(filtro.getOperador()));
		sb.append("|");
		try {
			TipoAsunto tipoAsunto = ISicresRPAdminTipoAsuntoManager
					.getTipoAsuntoByCode(filtro.getValor(), entidad);
			sb.append(tipoAsunto.getId());
		} catch (Exception e) {
			sb.append("0");
		}
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
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());

		int id = 0;
		try {
			TipoAsunto tipoAsunto = ISicresRPAdminTipoAsuntoManager
					.getTipoAsuntoByCode(filtro.getValor(), entidad);
			id = tipoAsunto.getId();
		} catch (Exception e1) {
			id = 0;
		}

		if (filtro.getOperador() == FiltroImpl.OPERADOR_IGUAL_A) {
			sb.append(" = ").append(id);
		}

		else if (filtro.getOperador() == FiltroImpl.OPERADOR_DISTINTO_DE) {
			sb.append(" <> ").append(id);
		}

		sb.append(" ").append(filtro.getNexo()).append(" ");

		return sb.toString();
	}

	public int[] getOperators() {
		return new int[]{FiltroImpl.OPERADOR_IGUAL_A,
				FiltroImpl.OPERADOR_DISTINTO_DE,
			};
	}

}
