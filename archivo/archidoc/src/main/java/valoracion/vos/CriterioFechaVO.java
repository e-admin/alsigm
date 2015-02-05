package valoracion.vos;

import ieci.core.db.DbEngine;
import ieci.core.guid.GuidManager;

import org.apache.log4j.Logger;

import common.exceptions.ArchivoModelException;
import common.vos.BaseVO;

/**
 * Clase con la informacion de las fecha para generar los criterios de busqueda
 * en la eliminacion de series documentales
 */
public class CriterioFechaVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(CriterioFechaVO.class);

	public final static int COMPARATOR_FECHA = 0;
	public final static int COMPARATOR_ID = 1;

	private String id = null;
	private FechaVO fechaInicial = null;
	private FechaVO fechaFinal = null;
	private int comparator = 0;

	public CriterioFechaVO(DbEngine dbEngine) {
		super();

		try {
			this.id = GuidManager.generateGUID(dbEngine);
		} catch (Exception e) {
			logger.error("Se ha producido un error generando el identificador para el criterio");
			throw new ArchivoModelException(e, "transform",
					"Error generando el identificador para el criterio");
		}
	}

	public CriterioFechaVO(String id) {
		super();

		this.id = id;
	}

	public FechaVO getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(FechaVO fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public FechaVO getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(FechaVO fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getComparator() {
		return comparator;
	}

	public void setComparator(int comparator) {
		this.comparator = comparator;
	}

	public boolean equals(Object vo) {
		boolean resultado = false;

		if (vo != null) {
			CriterioFechaVO criterio = (CriterioFechaVO) vo;

			if (criterio.getComparator() == COMPARATOR_FECHA
					&& this.getComparator() == COMPARATOR_FECHA) {
				// COMPARACION POR FECHAS
				if (criterio.getFechaInicial() != null) {
					if (criterio.getFechaFinal() != null) {
						if (criterio.getFechaInicial().getDia()
								.equals(this.getFechaInicial().getDia())
								&& criterio
										.getFechaInicial()
										.getMes()
										.equals(this.getFechaInicial().getMes())
								&& criterio
										.getFechaInicial()
										.getOperador()
										.equals(this.getFechaInicial()
												.getOperador())
								&& criterio.getFechaFinal().getDia()
										.equals(this.getFechaFinal().getDia())
								&& criterio.getFechaFinal().getMes()
										.equals(this.getFechaFinal().getMes())
								&& criterio
										.getFechaFinal()
										.getOperador()
										.equals(this.getFechaFinal()
												.getOperador()))
							resultado = true;
					} else {
						if (criterio.getFechaInicial().getDia()
								.equals(this.getFechaInicial().getDia())
								&& criterio
										.getFechaInicial()
										.getMes()
										.equals(this.getFechaInicial().getMes())
								&& criterio
										.getFechaInicial()
										.getOperador()
										.equals(this.getFechaInicial()
												.getOperador()))
							resultado = true;
					}
				} else {
					if (criterio.getFechaFinal().getDia()
							.equals(this.getFechaFinal().getDia())
							&& criterio.getFechaFinal().getMes()
									.equals(this.getFechaFinal().getMes())
							&& criterio.getFechaFinal().getOperador()
									.equals(this.getFechaFinal().getOperador()))
						resultado = true;
				}
			} else {
				// COMPARACION POR IDS
				if (criterio.getId().equals(this.getId()))
					resultado = true;
			}
		}

		return resultado;
	}
}
