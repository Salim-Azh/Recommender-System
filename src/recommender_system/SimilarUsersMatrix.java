package recommender_system;

import java.util.ArrayList;

/**
 * This class is responsible for representing for each user 
 * in a UserItemMatrix instance the similar users for this user
 * @author Salim Azharhoussen and Nicolas Galois
 */
public class SimilarUsersMatrix {
	
	/**
	 * All the similar users
	 */
	private ArrayList<ArrayList<User>> similarUsers;
	
	/**
	 * Contains all the similarity estimation between all the users
	 */
	private CosineSimilarityMatrix similarities;
	
	/**
	 * Default value for the threshold from which a user is
	 * considered similar
	 */
	private static final double SIMILARITY_FLAG = 0.4;
	
	/**
	 * @param userRatingsMatrix
	 */
	public SimilarUsersMatrix(UserItemMatrix userRatingsMatrix) {
		similarities = new CosineSimilarityMatrix(userRatingsMatrix);
		similarUsers = new ArrayList<ArrayList<User>>();
		
		for (int i = 0; i < userRatingsMatrix.getUsers().size(); i++) {
			
			ArrayList<Double> userISims =  similarities.getSimilaryForUser(i);
			ArrayList<User> similarUser = new ArrayList<User>();
			
			for (int j = 0; j < userISims.size(); j++) {
			
				if (userISims.get(j)> SIMILARITY_FLAG && j!=i) {
					similarUser.add(userRatingsMatrix.getUsers().get(j));
				}
			}
			
			similarUsers.add(similarUser);
		}
	}
	
	/**
	 * @return the similarity matrix
	 */
	public ArrayList<ArrayList<User>> getSimilarUsers() {
		return similarUsers;
	}

	/**
	 * @return the cosine similarity matrix 
	 */
	public CosineSimilarityMatrix getSimilarities() {
		return similarities;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		for (int i = 0; i < similarUsers.size(); i++) {
			if (!similarUsers.get(i).isEmpty()) {
		
				str+="Similar users for user "+i+": ";
				
				for (int j = 0; j < similarUsers.get(i).size(); j++) {
					str+= "(user"+similarUsers.get(i).get(j).getIndex()
							+") ";//+similarUsers.get(i).get(j).toString();
				}
				
				str+="\n";
			}
		}
		return str;
	}
}
