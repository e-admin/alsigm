/**
 * 
 */
package fondos.vos;

import java.util.Date;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface IInfoProductorSerie {

	public abstract void setFechaFin(String fechaFin);

	public abstract void setFechaInicio(String fechaInicio);

	public abstract Date getFechaFin();

	public abstract void setFechaFin(Date fechaFin);

	public abstract Date getFechaInicio();

	public abstract void setFechaInicio(Date fechaInicio);

	public abstract String getIdDescriptor();

	public abstract void setIdDescriptor(String idDescriptor);

	public abstract String getNombre();

	public abstract void setNombre(String nombre);

	public abstract int getTipoProductor();

	public abstract void setTipoProductor(int tipoProductor);

	public abstract boolean isPuedeSerEliminado();

	public abstract void setPuedeSerEliminado(boolean puedeSerEliminado);

	public abstract String getSustituyeAHistorico();

	public abstract void setSustituyeAHistorico(String sustituyeAHistorico);

	public abstract String getNombreCorto();

	public abstract String[] getNodosOrgano();

	public abstract String getFirstOrgano();

	public abstract int getMarcas();

	public abstract void setMarcas(int marcas);

	/**
	 * @return the codigo
	 */
	public abstract String getCodigo();

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public abstract void setCodigo(String codigo);

	public abstract boolean isVigente();

	public abstract boolean isPermitidoPasarAHistorico();

	public abstract boolean isPermitidoReemplazar();

	public abstract void setGuid(String guid);

	public abstract String getGuid();

	public abstract boolean isSinGuardar();

	public abstract boolean isNuevo();

	public abstract String getTipoObjeto();

	public abstract boolean isPermitidoEliminar();

	public abstract boolean isPermitidoPasarAVigente();

	public abstract boolean isMostrarRadio();

	public abstract boolean isPasadoAHistorico();

	public abstract boolean isIncorporadoComoHistorico();

	public abstract boolean isSustituidoPorVigente();

	public abstract boolean isVigenteSeleccionable();

	public abstract boolean isEliminado();

	public abstract boolean isHistorico();

	public abstract String getDebug();

	public abstract String getTextoMarcas();

	public abstract void setProductorSerieVO(ProductorSerieVO productorSerieVO);

	public abstract ProductorSerieVO getProductorSerieVO();

	public abstract boolean isModificado();

	public abstract void setIdSerie(String idSerie);

	public abstract String getIdSerie();

	public abstract String getIdDescrSistExt();

	public abstract String getIdLCA();

	public abstract String getIdProductor();

	public abstract void setSustituidoVigente(String sustituidoVigente);

}