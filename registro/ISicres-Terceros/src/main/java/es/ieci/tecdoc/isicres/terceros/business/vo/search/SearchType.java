package es.ieci.tecdoc.isicres.terceros.business.vo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 *
 * @author IECISA
 *
 */
public class SearchType extends ValuedEnum {

	public static final int FISICO_VALUE = 1;
	public static final SearchType FISICO = new SearchType("FISICO",
			FISICO_VALUE);
	public static final int JURIDICO_VALUE = 2;
	public static final SearchType JURIDICO = new SearchType("JURIDICO",
			JURIDICO_VALUE);

	protected SearchType(String name, int value) {
		super(name, value);
	}

	public static SearchType getEnum(String name) {
		return (SearchType) getEnum(SearchType.class, name);
	}

	public static SearchType getEnum(int value) {
		return (SearchType) getEnum(SearchType.class, value);
	}

	@SuppressWarnings("unchecked")
	public static Map getEnumMap() {
		return getEnumMap(SearchType.class);
	}

	@SuppressWarnings("unchecked")
	public static List<SearchType> getEnumList() {
		return getEnumList(SearchType.class);
	}

	@SuppressWarnings("unchecked")
	public static Iterator<SearchType> iterator() {
		return iterator(SearchType.class);
	}

	private static final long serialVersionUID = 2615421051939386728L;

}
