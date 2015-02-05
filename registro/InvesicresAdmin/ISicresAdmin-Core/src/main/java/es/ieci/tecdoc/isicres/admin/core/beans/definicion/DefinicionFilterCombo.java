package es.ieci.tecdoc.isicres.admin.core.beans.definicion;

import java.util.ResourceBundle;

import es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFld;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminLibroManager;

public class DefinicionFilterCombo extends DefinicionFilterField{

	public DefinicionFilterCombo(int tipoLibro) {
		super(tipoLibro);
	}

	public FiltroImpl decode(String[] cadenas) {
		ArchiveFld field = ISicresRPAdminLibroManager.getCampo(tipoLibro, cadenas[0]);
		FiltroImpl filtro = new FiltroImpl();
		filtro.setCampo(field.getId());
		filtro.setOperador(DefinicionFilterField.textToIdFilter(cadenas[1]));
		filtro.setValor(cadenas[2]);
		if(cadenas.length>4)
			filtro.setNexo(DefinicionFilterField.textToIdNexo(cadenas[4]));
		else
			filtro.setNexo("");
		return filtro;
	}

	public String encodeFilter(FiltroImpl filtro) {
		StringBuffer sb = new StringBuffer();
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo()).getName());
		sb.append("|");
		sb.append(DefinicionFilterField.idFilterToTexto(filtro.getOperador()));
		sb.append("|");
		sb.append(filtro.getValor());
		sb.append("|");
		sb.append(idToTexto(filtro.getCampo(),filtro.getValor()));
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
			sb.append(" = ").append(filtro.getValor());
		else if(filtro.getOperador()==FiltroImpl.OPERADOR_DISTINTO_DE)
			sb.append(" <> ").append(filtro.getValor());

		sb.append(" ").append(filtro.getNexo()).append(" ");

		return sb.toString();
	}

	public int[] getOperators() {
		return new int[]{
				FiltroImpl.OPERADOR_IGUAL_A,
				FiltroImpl.OPERADOR_DISTINTO_DE
				};
	}

	private String idToTexto(int idCampo, String id) {
		ResourceBundle bundle = ResourceBundle.getBundle("es.ieci.tecdoc.isicres.admin.core.manager.rpadmin");
		StringBuffer sb = new StringBuffer("textoCombo.");
		sb.append(idCampo).append(".").append(id);
		return bundle.getString(sb.toString());
	}
}
