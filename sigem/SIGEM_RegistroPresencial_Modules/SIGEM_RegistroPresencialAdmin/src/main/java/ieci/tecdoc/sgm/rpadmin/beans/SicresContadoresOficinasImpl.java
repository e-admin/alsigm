package ieci.tecdoc.sgm.rpadmin.beans;


public class SicresContadoresOficinasImpl  extends ColeccionGeneralImpl{

	public SicresContadorOficinaImpl get(int index) {
		return (SicresContadorOficinaImpl)lista.get(index);
	}

	public void add(SicresContadorOficinaImpl item) {
		lista.add(item);
	}
	
	public int contador(int id) {
		for(int i=0;i<lista.size();i++) {
			SicresContadorOficinaImpl contador = get(i);
			if(contador.getOficina()==id)
				return contador.getNumReg();
		}
		return 0;
	}
	
}
