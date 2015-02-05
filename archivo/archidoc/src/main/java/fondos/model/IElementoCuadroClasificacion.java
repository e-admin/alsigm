package fondos.model;

import util.TreeModelItem;
import fondos.vos.ElementoCuadroClasificacionVO;

public interface IElementoCuadroClasificacion extends
		ElementoCuadroClasificacionVO, TreeModelItem {

	public final static int TEMPORAL = 0;
	public final static int NO_VIGENTE = 1;
	public final static int VIGENTE = 2;
	public static final int ESTADO_ELIMINADO = 3;

	// public void setNivel(NivelCFVO nivel);

	public void setParentElement(ElementoCuadroClasificacionVO parentElement);

	public void addChild(IElementoCuadroClasificacion child);

}
