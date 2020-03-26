package ex5;


import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class MergeThread implements Runnable {


	final int[] array;
	ReentrantLock lock = new ReentrantLock();

	MergeThread (int[] values) {
		this.array = values;
	}


	private void merge(final int startIndex, final int endIndex) {


			int[] auxArray = new int[endIndex - startIndex + 1];


			final int middleIndex = ((endIndex + startIndex) / 2);

			int leftIterator = startIndex;
			int rightIterator = middleIndex + 1;

			int auxIndex = 0;

			lock.lock();

			while(leftIterator <= middleIndex  && rightIterator <= endIndex) {
				// System.out.println(array[leftIterator] + ":" + array[rightIterator]);
				if (array[leftIterator] < array[rightIterator]) {
					auxArray[auxIndex++] = array[leftIterator++];
				} else {
					auxArray[auxIndex++] = array[rightIterator++];
				}
			}
			
			while (leftIterator <= middleIndex) {
				auxArray[auxIndex++] = array[leftIterator++];
			}
			while (rightIterator <= endIndex) {
				auxArray[auxIndex++] = array[rightIterator++];
			}

			for (int index = startIndex; index <= endIndex; ++index) {
				array[index] = auxArray[index - startIndex];
			}

			lock.unlock();

	};

	private void mergeSort(final int startIndex, final int endIndex) {

		if (startIndex < endIndex) {

			final int middleIndex = ((endIndex + startIndex) / 2);
			// System.out.println(startIndex + ":" + middleIndex + ":" + endIndex);

			(new Thread(() -> {
				mergeSort(startIndex, middleIndex);
			})).start();
	
			(new Thread(() -> {
				mergeSort(middleIndex + 1, endIndex);
			})).start();

			merge(startIndex, endIndex);
		}

	};


	public int[] getArraySorted() {
		return this.array;
	}

	@Override
	public void run () {
		mergeSort(0, this.array.length - 1);
	}

};
