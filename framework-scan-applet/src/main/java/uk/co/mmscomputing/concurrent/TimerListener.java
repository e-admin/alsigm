package uk.co.mmscomputing.concurrent;

public interface TimerListener{
  public void begin(int timeleft);
  public void tick(int timeleft);
  public void end(int timeleft);
}