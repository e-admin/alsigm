package ieci.tdw.ispac.ispaccatalog.breadcrumbs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class BreadCrumbsContainer{

	private LinkedList stack;
	
	public BreadCrumbsContainer() {
		stack = new LinkedList();
	}

	public void print(){
		Iterator it = stack.iterator();
		while(it.hasNext()){
			BreadCrumb bc = (BreadCrumb)it.next();
			System.out.println(">>" + bc.getName());
			System.out.println(">>" + bc.getTitle());
			System.out.println(">>" + bc.getURL());
		}
	}

	public void pushBreadCrumb(BreadCrumb b){
		if(!stacked(b)){
			stack.addLast(b);	
		}else{
			bringToTheTop(b);
		}
		
	}
	
	public BreadCrumb popBreadCrumb(){
		BreadCrumb bc = null;
		try{
			bc = (BreadCrumb)stack.removeLast();
		}catch(NoSuchElementException e){
			return null;
		}
		return bc;
	}
	
	public void cleanBreadCrumbs(){
		stack.clear();
	}
	
	public boolean stacked(BreadCrumb bc){
		Iterator it = stack.iterator();
		while(it.hasNext()){
			if(bc.equals((BreadCrumb)it.next())){
				return true;
			}
		}
		return false;
	}
	
	public void bringToTheTop(BreadCrumb bc){
		while( (!stack.isEmpty()) && (!((BreadCrumb)stack.getLast()).equals(bc))){
			stack.removeLast();
		}
	}
	
	public List getList(){
		return this.stack;
	}
}
