package common.pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

/**
 * Lista paginada y ordenada.
 */
public class PaginatedDataList implements PaginatedList, List {

	/** Información de la página actual. */
	private PageInfo pageInfo = null;

	/** Contenido de la página. */
	private List list = new ArrayList();

	/** Número total de elementos. */
	private int fullListSize = 0;

	/** Identificador de la lista completa. */
	private String searchId = null;

	/**
	 * Constructor.
	 */
	public PaginatedDataList(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	/**
	 * Obtiene la información de la página.
	 * 
	 * @return Información de la página.
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * Establece la información de la página.
	 * 
	 * @param pageInfo
	 *            Información de la página.
	 */
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	/**
	 * Returns the current partial list
	 * 
	 * @return the current partial list
	 */
	public List getList() {
		return this.list;
	}

	/**
	 * Returns the page number of the partial list (starts from 1)
	 * 
	 * @return the page number of the partial list
	 */
	public int getPageNumber() {
		return this.pageInfo.getPageNumber();
	}

	/**
	 * Returns the number of objects per page. Unless this page is the last one
	 * the partial list should thus have a size equal to the result of this
	 * method
	 * 
	 * @return the number of objects per page
	 */
	public int getObjectsPerPage() {
		return this.pageInfo.getObjectsPerPage();
	}

	/**
	 * Returns the size of the full list
	 * 
	 * @return the size of the full list
	 */
	public int getFullListSize() {
		return this.fullListSize;
	}

	/**
	 * Sets the size of the full list
	 * 
	 * @param size
	 *            the size of the full list
	 */
	public void setFullListSize(int size) {
		this.fullListSize = size;
	}

	/**
	 * Sets the size of the full list
	 * 
	 * @param size
	 *            the size of the full list
	 */
	public void setSize(int size) {
		this.fullListSize = size;
	}

	/**
	 * Returns the sort criterion used to externally sort the full list
	 * 
	 * @return the sort criterion used to externally sort the full list
	 */
	public String getSortCriterion() {
		return this.pageInfo.getSortCriterion();
	}

	/**
	 * Returns the sort direction used to externally sort the full list
	 * 
	 * @return the sort direction used to externally sort the full list
	 */
	public SortOrderEnum getSortDirection() {
		return this.pageInfo.getSortDirection();
	}

	/**
	 * Returns an ID for the search used to get the list. It may be null. Such
	 * an ID can be necessary if the full list is cached, in a way or another
	 * (in the session, in the business tier, or anywhere else), to be able to
	 * retrieve the full list from the cache
	 * 
	 * @return the search ID
	 */
	public String getSearchId() {
		return this.searchId;
	}

	/**
	 * Sets the ID for the search used to get the list.
	 * 
	 * @param searchId
	 *            the search ID
	 */
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#size()
	 */
	public int size() {
		return this.fullListSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#clear()
	 */
	public void clear() {
		this.list.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return (this.fullListSize == 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		return this.list.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#get(int)
	 */
	public Object get(int index) {
		return this.list.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(int)
	 */
	public Object remove(int index) {
		return this.list.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, Object element) {
		this.list.add(index, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(Object o) {
		return this.list.add(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return this.list.remove(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection c) {
		return this.list.addAll(index, c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection c) {
		return this.list.addAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection c) {
		return this.list.containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection c) {
		return this.list.removeAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection c) {
		return this.list.retainAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#iterator()
	 */
	public Iterator iterator() {
		return this.list.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#subList(int, int)
	 */
	public List subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator()
	 */
	public ListIterator listIterator() {
		return this.list.listIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator listIterator(int index) {
		return this.list.listIterator(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public Object set(int index, Object element) {
		return this.list.set(index, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	public Object[] toArray(Object[] a) {
		return this.list.toArray(a);
	}

}
