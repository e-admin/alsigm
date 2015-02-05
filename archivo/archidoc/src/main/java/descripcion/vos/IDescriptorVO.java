/*
 * Created on 07-nov-2005
 *
 */
package descripcion.vos;

import java.util.Date;

/**
 * @author ABELRL
 * 
 */
public interface IDescriptorVO {
	public abstract int getEstado();

	public abstract void setEstado(int estado);

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getIddescrsistext();

	public abstract void setIddescrsistext(String iddescrsistext);

	public abstract String getIdfichadescr();

	public abstract void setIdfichadescr(String idfichadescr);

	public abstract String getIdlista();

	public abstract void setIdlista(String idlista);

	public abstract String getIdsistext();

	public abstract void setIdsistext(String idsistext);

	public abstract String getNombre();

	public abstract void setNombre(String nombre);

	public abstract String getTienedescr();

	public abstract void setTienedescr(String tienedescr);

	public abstract Date getTimestamp();

	public abstract void setTimestamp(Date timestamp);

	public abstract int getTipodescriptor();

	public abstract void setTipodescriptor(int tipo);
}