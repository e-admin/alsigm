package transferencias.vos;

public interface IUnidadInstalacionVO {

	public abstract String getIdFormato();

	public abstract void setIdFormato(String formato);

	public abstract String getIdRelEntrega();

	public abstract void setIdRelEntrega(String idRelacion);

	public abstract boolean isDevolver();

	public abstract void setDevolver(boolean devolver);

	public abstract int getEstadoCotejo();

	public abstract void setEstadoCotejo(int estadoCotejo);

	public abstract int getOrden();

	public abstract void setOrden(int numeroCaja);

	public abstract String getSignaturaUI();

	public abstract void setSignaturaUI(String signatura);

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getNotasCotejo();

	public abstract void setNotasCotejo(String notasCotejo);

	public abstract boolean isConErrores();

	public abstract boolean isPendiente();

	public abstract boolean isRevisada();

	// TODO ELIMINAR CUANDO TENGA MAPEO DE COLUMNAS
	public abstract String getDevolucion();

	public abstract void setDevolucion(String devolucion);

	public abstract boolean isChanged();

	public abstract void setChanged(boolean changed);

	public abstract String getSignaturaUIOrigen();

	public abstract String getNumeroUI();

	public abstract boolean isBorrable();

	public void setBorrable(boolean borrable);

	public void setIduiubicada(String iduiubicada);

	public String getIduiubicada();
}