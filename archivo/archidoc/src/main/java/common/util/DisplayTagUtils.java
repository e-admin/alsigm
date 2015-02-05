package common.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.properties.SortOrderEnum;
import org.displaytag.tags.TableTag;

import common.Constants;

public class DisplayTagUtils extends TableTag {

	/**
	 *
	 */
	private static final long serialVersionUID = 4799501657445275558L;
	public static String EXPORT_ACTION_REQUEST_PARAM_NAME = "d-3998205-e";

	/**
	 * Obtiene el número de pagína que se debe mostrar
	 * 
	 * @param request
	 * @param response
	 * @param servlet
	 * @param idTable
	 * @return
	 */
	public static String getPageNumber(HttpServletRequest request) {
		return request.getParameter("page");
	}

	public static String getOrderColumn(HttpServletRequest request) {
		return request.getParameter("sort");
	}

	public static int getDefaultOrderColumn() {
		return 1;
	}

	public static int getDefaultPageNumber() {
		return 1;
	}

	public static boolean isDisplayTagOperation(HttpServletRequest request) {
		if (isSorting(request) || isPaginating(request)) {
			return true;
		}
		return false;
	}

	public static boolean isBackInvocation(HttpServletRequest request) {
		Boolean isBack = (Boolean) request
				.getAttribute(Constants.IS_BACK_INVOCATION_KEY);
		if (Boolean.TRUE.equals(isBack)) {
			return true;
		}

		return false;
	}

	public static boolean isSorting(HttpServletRequest request) {
		return (getOrderColumn(request) != null);
	}

	public static boolean isPaginating(HttpServletRequest request) {
		return (getPageNumber(request) != null);
	}

	public static SortOrderEnum getOrderDirection(HttpServletRequest request) {
		String dir = request.getParameter("dir");
		if (dir == null)
			return null;

		SortOrderEnum retorno = SortOrderEnum.ASCENDING;
		if (SortOrderEnum.DESCENDING.getName().equalsIgnoreCase(dir)) {
			retorno = SortOrderEnum.DESCENDING;
		}

		return retorno;
	}

	public static SortOrderEnum getDefaultOrderDirection() {
		return SortOrderEnum.ASCENDING;
	}

	public static int getInitPos(int pageNumber, int pageSize) {
		int initPos = pageSize * (pageNumber - 1);
		if (initPos < 0) {
			initPos = 0;
		}

		return initPos;
	}

	public static int getEndPos(int pageNumber, int pageSize, int sizeList) {
		int endPos = getInitPos(pageNumber, pageSize) + pageSize - 1;
		if (endPos >= sizeList) {
			endPos = sizeList - 1;
		}
		return endPos;
	}

	/**
	 * Devuelve un String[] con los ids que se deben mostrar en cada página
	 * 
	 * @param int pageNumber
	 * @param int pageSize
	 * @param List
	 *            listaIdsElementos
	 * @return
	 */
	public static String[] getIds(int pageNumber, int pageSize,
			List listaIdsElementos) {

		List list = new ArrayList();
		// Si la lista tiene un elemento
		if (!ListUtils.isEmpty(listaIdsElementos)
				&& listaIdsElementos.size() == 1) {
			list = listaIdsElementos;
		} else {
			int initPos = getInitPos(pageNumber, pageSize);
			int endPos = getEndPos(pageNumber, pageSize,
					listaIdsElementos.size());

			if (initPos != -1 && endPos != -1) {
				if (initPos == endPos) {
					list.add(listaIdsElementos.get(endPos));
				} else {
					list = new ArrayList(listaIdsElementos.subList(initPos,
							endPos));

					if (!ListUtils.isEmpty(list)
							&& endPos < listaIdsElementos.size()) {
						// Añadimos el ultimo elemento de la lista
						list.add(listaIdsElementos.get(endPos));

					}
				}
			}
		}
		return StringUtils.toString(list.toArray());
	}

}
