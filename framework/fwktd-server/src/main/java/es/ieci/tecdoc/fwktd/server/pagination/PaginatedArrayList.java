package es.ieci.tecdoc.fwktd.server.pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Lista paginada y ordenada.
 *
 * @param <E>
 */
public class PaginatedArrayList<E> implements List<E> {

	/**
	 * Información de la página actual.
	 */
	private PageInfo pageInfo = null;

	/**
	 * Contenido de la página.
	 */
	private List<E> list = new ArrayList<E>();

	/**
	 * Número total de elementos.
	 */
	private int fullListSize = 0;

	/**
	 * Constructor.
	 *
	 * @param pageInfo
	 *            Información de la página actual.
	 */
	public PaginatedArrayList(PageInfo pageInfo) {
		setPageInfo(pageInfo);
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public int getFullListSize() {
		return fullListSize;
	}

	public void setFullListSize(int fullListSize) {
		this.fullListSize = fullListSize;
	}

	public int getPageNumber() {
		return this.pageInfo.getPageNumber();
	}

	public int getObjectsPerPage() {
		return this.pageInfo.getObjectsPerPage();
	}

	public void setSize(int size) {
		this.fullListSize = size;
	}

	public int size() {
		return this.fullListSize;
	}

	public boolean isEmpty() {
		return (this.fullListSize == 0);
	}

	public boolean contains(Object o) {
		return getList().contains(o);
	}

	public Iterator<E> iterator() {
		return getList().iterator();
	}

	public Object[] toArray() {
		return getList().toArray();
	}

	public <T> T[] toArray(T[] a) {
		return getList().toArray(a);
	}

	public boolean add(E o) {
		return getList().add(o);
	}

	public boolean remove(Object o) {
		return this.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return getList().containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return getList().addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return getList().addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		return this.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return getList().retainAll(c);
	}

	public void clear() {
		getList().clear();
	}

	public E get(int index) {
		return getList().get(index);
	}

	public E set(int index, E element) {
		return getList().set(index, element);
	}

	public void add(int index, E element) {
		getList().add(index, element);
	}

	public E remove(int index) {
		return getList().remove(index);
	}

	public int indexOf(Object o) {
		return getList().indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return getList().lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return getList().listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return getList().listIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return getList().subList(fromIndex, toIndex);
	}
}
