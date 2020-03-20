package ex1;

import java.util.ArrayList;
import java.util.List;

public class Ex1 {
  static void threadMonitor(List<Thread> threads) {
    boolean hasTerminated = false;

    while (!hasTerminated) {
      hasTerminated = true;

      // System.out.println("\u001B[s");

      int iterator = 0;
      while ((iterator++) < threads.size()) {


        Thread thread = threads.get(iterator - 1);
        System.out.println("\u001B[K" + "Thread[" + iterator + "] -> " + thread.getState());


        hasTerminated &= (thread.getState() == Thread.State.TERMINATED);
        if (thread.getState() == Thread.State.WAITING) {
        	synchronized(StatedThread.waiting) {

        		try {
        			Thread.sleep(100); // wait to free thread
	        		StatedThread.waiting.notify();
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
        	}
        }
      }

      System.out.println("\u001B[" + iterator + "A");

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
      }
    }
  };

  public static void main(final String[] args) {
    List<Thread> threads = new ArrayList<Thread>();

    int threadNumber = StatedThread.loopsNumber = 10;

    int iterator = 0;
    while ((iterator++) < threadNumber) {
      threads.add(new Thread(new StatedThread(iterator == threadNumber)));
    }

    (new Thread(() -> { threadMonitor(threads); })).start();

    try {
    	Thread.sleep(1000); // Wait to see the NEW state of threads
    } catch (InterruptedException e) {
    }

    for (Thread thread : threads) {
      thread.start();
    }

    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
      }
    }
  }
};
