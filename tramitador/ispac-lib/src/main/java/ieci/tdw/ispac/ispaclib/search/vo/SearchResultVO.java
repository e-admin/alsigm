package ieci.tdw.ispac.ispaclib.search.vo;

import ieci.tdw.ispac.api.item.IItemCollection;

public class SearchResultVO {
	
	public static int NO_LIMITED=-1;
	//Colleción con el resultado de la búsqueda
	IItemCollection resultados;
	//Número total de registros que satisfacen la búsqueda
	int numTotalRegistros;
	//Número máximo de registros resultantes de la búsqueda
	int numMaxRegistros=NO_LIMITED;
	
	public SearchResultVO() {
		super();
	}

	public SearchResultVO(IItemCollection resultados, int numTotalRegistros,
			int numMaxRegistros) {
		super();
		this.resultados = resultados;
		this.numTotalRegistros = numTotalRegistros;
		this.numMaxRegistros = numMaxRegistros;
	}

	public IItemCollection getResultados() {
		return resultados;
	}

	public void setResultados(IItemCollection resultados) {
		this.resultados = resultados;
	}

	public int getNumTotalRegistros() {
		return numTotalRegistros;
	}

	public void setNumTotalRegistros(int numTotalRegistros) {
		this.numTotalRegistros = numTotalRegistros;
	}

	public int getNumMaxRegistros() {
		return numMaxRegistros;
	}

	public void setNumMaxRegistros(int numMaxRegistros) {
		this.numMaxRegistros = numMaxRegistros;
	}
	

}
