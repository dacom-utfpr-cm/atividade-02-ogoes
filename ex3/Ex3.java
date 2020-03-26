package ex3;


import java.lang.Math;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;



public class Ex3 {
	static List<Integer> primes = new ArrayList<Integer>();

	public static void calculatePrimes(final double limit) {
		double logBase = 10;
		int interactions = (int) Math.log(limit) / (int)Math.log(logBase);

		List<Thread> threads = new ArrayList<Thread>();

		int threadsSum = 0;

		int iterator = 0;
		int start = 1;
		while ((iterator++) < interactions) {
			threadsSum += iterator;


			final int begin = start;
			final int end = (int) Math.pow(logBase, iterator);


			for (int i = 0; i < threadsSum; ++i) {

				final int threadsNum = threadsSum;

				final int range = ((end - begin) / threadsNum);
				final int parcela = (range * i);
				final int init = begin + parcela;


				Thread thread = new Thread(() -> {

					for (int test = init + 1; test < init + range; ++test) {
						boolean isPrime = true;

						synchronized(Ex3.primes) {
							for (Integer prime: primes) {
								if (test % prime == 0) isPrime = false;
							}

							if (isPrime) primes.add(test);
						}
					}

				});


				threads.add(thread);
				thread.start();
			}
			start = (int) Math.pow(logBase, iterator);
		}

		for(Thread thread: threads) {
			try {
				thread.join();
			} catch(InterruptedException exception) {
				System.out.println(exception);
			}
		} 

		Collections.sort(primes);

	};

	public static void main(String[] args) {

		calculatePrimes(100000);


		System.out.println(primes);
	}
};
