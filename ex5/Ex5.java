package ex5;




public class Ex5 {

	public static void main(String[] args) {

		int[] array = {4, 1, 5, 8, 6, 7, -1};
		
		MergeThread sorter = new MergeThread(array);

		Thread thread = new Thread(sorter);


		thread.start();

		try {
			thread.join();
		} catch (InterruptedException e) {
			System.out.println(e);
		}



		System.out.println("Resultado: ");
		for(int i: sorter.getArraySorted()){
			System.out.print(i + " ");
		}

	};

};
