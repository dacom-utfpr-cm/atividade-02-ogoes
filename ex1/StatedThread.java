package ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatedThread implements Runnable {
  static int loopsNumber;


  // static Lock lock = new Lock();
  static Boolean waiting = false;

  boolean monitor;

  StatedThread (boolean monitor) {
  	this.monitor = monitor;
  }

  void timeWainting(Random random) {
    try {
      Thread.sleep((random.nextInt() & 0x7fff) % 1000); // wait random time, [0, 1000] milliseconds
    } catch (InterruptedException e) {
    }
  }

  void wainting() {
  	synchronized (waiting) {
	    try {
	      waiting.wait();
	    } catch (InterruptedException e) {
	    	e.printStackTrace();
	      // System.out.println(e);
	    }  		
  	}

    // 	wait();
    // 	
  }

  @Override
  public void run() {
    Random randomObject = new Random();

    int loopCounter = 0;
    while ((loopCounter++) < StatedThread.loopsNumber) {
      boolean action = (randomObject.nextInt() & 0x7fff) % 2 == 0? true: false;

      if (action) {
        timeWainting(randomObject);
      } else {
      	wainting();

      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
      }
    }
  };
};
