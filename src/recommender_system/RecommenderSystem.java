package recommender_system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecommenderSystem {	
	
	private UserItemMatrix usersMatrix;
	
	public RecommenderSystem(int nbUsers, int nbItems) { 
		usersMatrix = new UserItemMatrix(nbUsers, nbItems);
	}
	
	/**
	 * here the matrix given is the predicted one
	 * @param k is the the size of the list of item to 
	 * recommend to the user at index u
	 * @return the list of the k most rated item
	 */
	public List<Integer> kItem(int k, int u) {
		
		//Test if param k given is below the number of item from users
		if(k > usersMatrix.getUsers().get(u).getNbItems() || k<=0) {
			throw new IllegalArgumentException("the k number"
				+ " must be below the nbItem wihch is : " 
					+ usersMatrix.getUsers().get(0).getNbItems());
		}
		if (k> usersMatrix.getUsers().get(u).getInitialUnratedItems().size()) {
			System.out.println("This user has not enought unrated items");
			return new ArrayList<Integer>();
		}
		
		ArrayList<Rating> resRatings = new ArrayList<Rating>();
		User user = usersMatrix.getUsers().get(u);
		
		ArrayList<Integer> initUnratedItems = user.getInitialUnratedItems();
		
		if (initUnratedItems.isEmpty()) {
			resRatings = user.getRatings();
		}
		else {
			for (Integer integer : initUnratedItems) {
				resRatings.add(user.getRatings().get(integer));
			}
		}
		
		
		// sort the collection
		Collections.sort(resRatings);

		//Select only the Top-k most rated
		List<Rating> kRatings = resRatings.subList(
				resRatings.size() - k, resRatings.size());
		
		ArrayList<Integer> kItems = new ArrayList<Integer>();
		
		for (Rating rating : kRatings) {
			kItems.add(rating.getItem());
		}
		
		return kItems;
	}
	
	@Override
	public String toString() {
		
		return usersMatrix.getComputations() 
				+ usersMatrix.toStringPredictedMatrix();
	}
}
