package ex4;


import java.util.List;
import java.util.ArrayList;

public class Ex4 {

	static Boolean found = false;
	static long index = -1;

	public static void findInArray(final int[] array, final int value, final int threadNumber) {

		if (array.length % threadNumber != 0) return;

		int range = array.length / threadNumber;

		int iterator = 0;

		List<Thread> threads = new ArrayList<Thread>();

		while (iterator < array.length) {
			final int index = iterator;

			Thread thread = new Thread(() -> {
				int verify = index;
				int end = verify + range;

				synchronized(Ex4.found) {

					while (!Ex4.found && verify < end && array[verify] != value) verify++;

					if (array[verify] == value) {
						Ex4.found = true;
						Ex4.index = verify;
					}

				}

			});

			threads.add(thread);
			thread.start();

			iterator += range;
		}


		for (Thread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException exception) {
				System.out.println(exception);
			}
		}
	};

	public static void main(String[] args) {
		int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


		findInArray(array, 4, 6);
		if (Ex4.found) {
			System.out.println("Numero encontrado, indice[" + Ex4.index + "]");
		}
	};
};
