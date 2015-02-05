package ieci.tecdoc.sgm.rpadmin.beans;

public class FiltrosImpl  extends ColeccionGeneralImpl{

	public FiltroImpl get(int index) {
		return (FiltroImpl)lista.get(index);
	}

	public void add(FiltroImpl fila) {
		lista.add(fila);
	}
}
