package solicitudes.consultas.decorators;

import solicitudes.consultas.vos.ConsultaVO;

/**
 * Decorador de un consultas. Devuelve la consulta con el tipo de comparador
 * adecuado para el campo solicitado(fecha entrega,fecha reserva...) o la salida
 * adecuada para ciertas columnas.
 */
public class ViewConsultaDecorator extends ConsultasBaseDecorator {
	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable por fecha estado
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getFestado() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_FESTADO);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable año.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getAno() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_ANO);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable estado.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getEstado() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_ESTADO);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable fecha de
	 * entrega.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getFEntrega() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_FENTREGA);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable fecha de estado.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getFEstado() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_FESTADO);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable fecha final de
	 * reserva.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getFfinalreserva() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_FFINAL_RESERVA);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable fecha inicial de
	 * reserva.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getFinicialreserva() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_FINICIAL_RESERVA);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable por id.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getId() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_NONE);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable por id de
	 * archivo.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getIdarchivo() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_IDARCHIVO);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable por nombre de
	 * usuario consultor.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getNusrconsultor() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_NOMUSRCONSULTOR);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable por nombre de
	 * organizacion consultor.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getNorgconsultor() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_NOMORGCONSULTOR);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable por orden.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getOrden() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_ORDEN);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable por tema.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getIdTema() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_TEMA);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable por tema.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getIdusrsolicitante() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_IDUSRSOLICITANTE);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable por tipo de
	 * entidad consultora.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getTipoentconsultora() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_TIPOENTCONSULTORA);
		return consulta;
	}

	/**
	 * Devuelve el objeto consulta {@link ConsultaVO} ordenable por fecha maxima
	 * de fin de consulta.
	 * 
	 * @return Objeto ConsultaVO {@link ConsultaVO}
	 */
	public ConsultaVO getFmaxfinconsulta() {
		ConsultaVO consulta = (solicitudes.consultas.vos.ConsultaVO) getCurrentRowObject();

		consulta.setComparatorType(ConsultaVO.COMPARABLE_TYPE_FMAXFINCONSULTA);
		return consulta;
	}
}
