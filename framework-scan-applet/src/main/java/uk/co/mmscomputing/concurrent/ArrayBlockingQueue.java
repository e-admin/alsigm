package uk.co.mmscomputing.concurrent;
/*
	Thread- und Netzwerk-Programmierung mit Java
	ISBN 3-89864-133-3
  p.67
*/

public class ArrayBlockingQueue extends Object{
  protected Object[]  buffer;
  protected int       size,front,rear;
  protected boolean   fcarry,rcarry;
  protected Semaphore empty,full;
  protected Object    pt,gt;

  public ArrayBlockingQueue(int init){
    if(init<1){
      throw new IllegalArgumentException(getClass().getName()
        +".<init>(int init)\n\tParameter init must be greater than zero."
      );
    }
    buffer=new Object[init];
    size=init;
    front=0;rear=0;
    fcarry=false;rcarry=false;
    empty=new Semaphore(init,true);
    full=new Semaphore(0,true);
    pt=new Object();
    gt=new Object();
  }

  public boolean isEmpty(){
    return(front==rear)&&(fcarry==rcarry);  
  }

  public boolean isFull(){
    return(front==rear)&&(fcarry!=rcarry);  
  }

  synchronized public int size(){
    int f=front,r=rear;
    if((fcarry!=rcarry)||(r<f)){r+=size;}
    return(r-f);
  }

  public int remainingCapacity(){
    return size-size();
  }

  protected void add(Object v){
    buffer[rear]=v;
    rear++;if(rear>=size){rear-=size;rcarry=!rcarry;}
  }

  public void put(Object v)throws InterruptedException{
    empty.acquire();
    synchronized(pt){add(v);}
    full.release();
  }

  public boolean offer(Object v){
    try{
      return offer(v,0,TimeUnit.MILLISECONDS);                // try to put object into array, but don't wait
    }catch(InterruptedException ie){
      ie.printStackTrace();
      return false;
    }
  }


  public boolean offer(Object v,long timeout,TimeUnit unit)throws InterruptedException{
    if(v==null){
      throw new NullPointerException(getClass().getName()+".offer(Object v,long timeout,TimeUnit unit)\n\tObject v is null.");
    }
    if(empty.tryAcquire(timeout,unit)){
      synchronized(pt){add(v);}
      full.release();
      return true;
    }
    return false;
  }

  protected Object remove(){
    Object v=buffer[front];
    front++;if(front>=size){front-=size;fcarry=!fcarry;}
    return v;
  }

  public Object take()throws InterruptedException{
    Object v;
    full.acquire();
    synchronized(gt){v=remove();}
    empty.release();
    return v;
  }

  public Object poll(){
    try{
      return poll(0,TimeUnit.MILLISECONDS);                   // try to get object, but don't wait
    }catch(InterruptedException ie){
      ie.printStackTrace();
      return null;
    }
  }
 
  public Object poll(long timeout,TimeUnit unit)throws InterruptedException{
    Object v=null;
    if(full.tryAcquire(timeout,unit)){                        // if we get a permit
      synchronized(gt){v=remove();}                           // get Object
      empty.release();
    }                                                         // else return null
    return v;
  }

  public void clear(){while(poll()!=null){}}
}
