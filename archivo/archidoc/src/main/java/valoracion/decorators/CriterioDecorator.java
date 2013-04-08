package valoracion.decorators;

import org.displaytag.decorator.TableDecorator;

import valoracion.vos.CriterioFechaVO;

/**
 * Decorador para formatear las columna de manera adecuada en función de la
 * columna que se desea mostrar.
 */
public class CriterioDecorator extends TableDecorator {

	public String getFechaInicial() {
		StringBuffer fecha = new StringBuffer();
		CriterioFechaVO criterio = (CriterioFechaVO) getCurrentRowObject();

		if (criterio.getFechaInicial() != null) {
			fecha.append(criterio.getFechaInicial().getOperador());
			if (criterio.getFechaInicial().getDia() != null
					&& criterio.getFechaInicial().getDia().trim().length() > 0) {
				fecha.append(" ").append(criterio.getFechaInicial().getDia());

				if (criterio.getFechaInicial().getMes() != null
						&& criterio.getFechaInicial().getMes().trim().length() > 0)
					fecha.append("/").append(
							criterio.getFechaInicial().getMes());
			} else {
				if (criterio.getFechaInicial().getMes() != null
						&& criterio.getFechaInicial().getMes().trim().length() > 0)
					fecha.append(" ").append(
							criterio.getFechaInicial().getMes());
			}
		}

		return fecha.toString();
	}

	public String getFechaFinal() {
		StringBuffer fecha = new StringBuffer();
		CriterioFechaVO criterio = (CriterioFechaVO) getCurrentRowObject();

		if (criterio.getFechaFinal() != null) {
			fecha.append(criterio.getFechaFinal().getOperador());
			if (criterio.getFechaFinal().getDia() != null
					&& criterio.getFechaFinal().getDia().trim().length() > 0) {
				fecha.append(" ").append(criterio.getFechaFinal().getDia());

				if (criterio.getFechaFinal().getMes() != null
						&& criterio.getFechaFinal().getMes().trim().length() > 0)
					fecha.append("/").append(criterio.getFechaFinal().getMes());
			} else {
				if (criterio.getFechaFinal().getMes() != null
						&& criterio.getFechaFinal().getMes().trim().length() > 0)
					fecha.append(" ").append(criterio.getFechaFinal().getMes());
			}
		}

		return fecha.toString();
	}
}