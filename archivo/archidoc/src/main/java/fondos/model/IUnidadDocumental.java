package fondos.model;

import java.util.List;

import fondos.vos.UnidadDocumentalVO;

/**
 * Métodos de establecimiento de valores para los campos de una unidad
 * documental del cuadro de clasificación de fondos documentales
 */
public interface IUnidadDocumental extends UnidadDocumentalVO {

	public void setCodSistProductor(String codSistemaProductor);

	public void setDenominacion(String denominacion);

	public void setIdElementocf(String idElementocf);

	public void setIdSerie(String idSerie);

	public void setNumExp(String numExp);

	public void setTipoDocumental(String tipoDocumental);

	public void setMarcasBloqueo(int marcasBloqueo);

	public void setNombreProductor(String nombreProductor);

	public List getInteresados();
}
