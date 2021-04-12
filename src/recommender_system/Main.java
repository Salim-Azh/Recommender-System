package recommender_system;

import java.util.List;
import java.util.Scanner;

public class Main {

	public static void userFriendlyKItems(RecommenderSystem recSys, 
			int k, int userIndex) {
		//The k items for a user
		List<Integer> kList = recSys.kItem(k, userIndex);
		System.out.print(">>>The " + k + " items recommended "
				+ "from the initial non-rated items for user"
				+ userIndex+" are: ");
		for (int i = kList.size()-1; i >=0 ; i--) {
			System.out.print(kList.get(i) + " ");
		}
	}
	
	public static void main(String[] args) {
		
		//All the computation will be printed in the console.
		System.out.println(">>>Un premier exemple avec une matrice 5,20 pour"
				+ "comprendre le fonctionnement");
		
		RecommenderSystem recSys1 = new RecommenderSystem(5,20);
		System.out.println(recSys1);
		userFriendlyKItems(recSys1, 2, 0);
		
		System.out.println("\n>>>Vous avez compris ? Tapez Entrer pour avoir la version 100,100");
		
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		sc.close();
		RecommenderSystem recSys2 = new RecommenderSystem(50, 100);
		//The final predicted Matrix
		System.out.println(recSys2);
		userFriendlyKItems(recSys2, 5, 0);
		
	}

}
