package ex2;

import java.util.ArrayList;
import java.util.List;

public class Ex2 {
  public static void main(String[] args) {
    ThreadGroup group = new ThreadGroup("ThreadGroup");

    int threadNumber = 5;
    int iterator = 0;

    while ((iterator++) < 5) {
      final int threadId = iterator;
      (new Thread(group, () -> {
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          System.out.println("Thread[" + threadId + "] Interrompida");
        }
      })).start();
    }

    System.out.println("Threads Ativas: " + group.activeCount());

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
    	e.printStackTrace();
    }
    group.interrupt();
  };
};
