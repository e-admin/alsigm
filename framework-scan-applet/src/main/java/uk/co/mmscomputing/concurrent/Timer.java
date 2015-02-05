package uk.co.mmscomputing.concurrent;

public class Timer extends Semaphore{

  private   boolean       waiting;
  protected int           timeout;
  protected int           delay;
  protected TimerListener listener;

  public Timer(int timeout){
    super(0,true);
    this.timeout=timeout;
  }

  public void setDelay(int td){this.delay=td;}
  public void setListener(TimerListener listener){this.listener=listener;}

  public void acquire()throws InterruptedException{
    waiting=true;
    new CountdownThread().start();
    super.acquire();
    waiting=false;
  }
  
  public boolean tryAcquire(long timeout,TimeUnit unit)throws InterruptedException{
    waiting=true;
    new CountdownThread().start();
    boolean r=super.tryAcquire(timeout,unit);
    waiting=false;
    return r;
  }

  class CountdownThread extends Thread{
    public void run(){  
      int countdown=timeout;            

      listener.begin(countdown);
      while(waiting){
        try{
          countdown-=delay;
          sleep(delay);
          listener.tick(countdown);
        }catch(InterruptedException e){}
      }
      listener.end(countdown);
    }
  }
}