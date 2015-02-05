package uk.co.mmscomputing.concurrent;
/*
	Thread- und Netzwerk-Programmierung mit Java
	ISBN 3-89864-133-3
*/
/*
  In order to be 'compatible' with Sun's new java.util.concurrent package in 1.5.0 I changed this package
  constructor => fairness parameter is always true
  down => acquire
  up   => release
*/

public class Semaphore extends Object{

  private int    initpermits;                    // initially available permits
  private int    available;                      // currently available permits
  private int    waiting;                        // threads that currently are waiting

  public Semaphore(int permits, boolean fair) {  // Use always fair=true
    initpermits=permits;                         // Sun allows negative permit values
    available=initpermits;                        
    waiting=0;
  }

  public boolean isFair(){return true;}          // always true; This implementation is only 'weakly fair'
  public int availablePermits(){return available;}
/*
  protected void finalize() throws Throwable{
    if(available!=initpermits){
      int x=available-initpermits;               // threads that haven't been released yet.
      System.err.println("Semaphore : "+x+" pending operations.");
    }
    super.finalize();
  }
*/
  public synchronized void acquire()throws InterruptedException{
    while(available<=0){                         // if no permits are available
      try{
        waiting++;                               // thread is now waiting
        wait();                                  // wait until notified
      }finally{
        waiting--;                               // thread is not waiting anymore
      }
    }
    available--;                                 // got a permit, decrease available permits
  }
  
  public synchronized boolean tryAcquire(long timeout,TimeUnit unit)throws InterruptedException{
    if(available<=0){                            // if no permits are available
      if(timeout<=0){return false;}              // if time is not positive return immediatly
      try{
        waiting++;                               // wait only once; neglect "spurious wakeups" here
        wait(timeout);                           // wait until notified or timed out (timeout in MILLISECONDS)
      }finally{
        waiting--;                               // thread is not waiting anymore
      }
      if(available<=0){return false;}            // still nothing available
    }
    available--;                                 // got a permit, decrease available permits
    return true;
  }

  public synchronized void release(){
    available++;                                 // release a permit, increase available permits
    if((available>0)&&(waiting>0)){              // if init permits < 0 we need to just count up first
      notify();                         
    }
  }

  public void release(int permits){
    for(int i=0;i<permits;i++){
      release();
    }
  }
/*
  public void releaseAll(){
    while(waiting>0){
      release();
    }
  }
*/
}