package recommender_system;

import java.util.ArrayList;

/**
 * This class represents the user and items rating matrix
 * @author Salim Azharhoussen and Nicolas Galois
 *
 */
public class UserItemMatrix {
	
	/**
	 * Contains n users with p items
	 */
	private ArrayList<User> users;
	
	/**
	 * Contains for each user his similarUsers
	 */
	private SimilarUsersMatrix similarUsersMatrix;
	
	private final String computations;
	
	/**
	 * Create a matrix of n users with p items
	 * @param n is the number of users in the matrix
	 * @param p is the number of items in the matrix 
	 */
	public UserItemMatrix(int n, int p) {
		if (n<=0 || p<=0) {
			throw new IllegalArgumentException("the matrix number"
					+ " of lines n and columns p must be positiv");
		}
		
		//Create the matrix
		//The fake data
		users = new ArrayList<User>();
		
		for (int i = 0; i < n; i++) {
			users.add(new User(p,i));
		}
		
		//Generate the cosine similarity matrix associated
		//And the similar users matrix
		similarUsersMatrix = new SimilarUsersMatrix(this);
		
		ArrayList<ArrayList<User>>similarUsers = 
				similarUsersMatrix.getSimilarUsers();
		
		//Retrieve the item rated by them
		for (ArrayList<User> users : similarUsers) {
			for (User user : users) {
				user.setUnratedRatedBySimilar(users);
			}
		}
		
		//Create rankings for all those items
		for (User user : users) {
			user.setLikeRanking(similarUsers.get(user.getIndex()));
			user.setAssociatedLike(users);
			user.setPopularity(users);
		}
		
		computations = toString();
		
		// replace all unRated notes to scoring
		for (User user : users) {
			user.setMissingNotes();
		}
	}

	/**
	 * Create an instance with a predefined collection of users
	 * @param users a collection of users
	 */
	/*public UserItemMatrix(ArrayList<User> users) {
		
		if (!isValid(users)) {
			throw new  IllegalArgumentException("The given "
					+ "matix cannot be empty. The number of rating"
					+ "has to be the same for all users in the matrix");
		}
		
		this.users = users;
		
		computation 
	}*/
	
	/**
	 * @return the user ratings matrix
	 */
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public String getComputations() {
		return computations;
	}

	/**
	 * @param users a collection of user
	 * @return true if the given users ratings matrix is valid 
	 * else return false
	 */
	private static boolean isValid(ArrayList<User> users) {
		if (users.isEmpty()) {
			return false;
		}
		
		int p = users.get(0).getNbItems();
		
		for (User user : users) {
			if (user.getNbItems() != p) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return the number of items of the matrix 
	 */
	public int getNbItems() {
		return this.getUsers().get(0).getNbItems();
	}
	
	@Override
	public String toString() {
		String str = ">>> user ratings matrix (users in line, items in column):\n";
		for (User user : users) {
			str += user.toString();
		}
		str += "\n";
		
		str += similarUsersMatrix.getSimilarities().toString();
		
		str += "\n>>> extracted similar users:\n";
		str += similarUsersMatrix.toString();
		
		str += "\n>>> items ranking:\n";
		for (User user : users) {
			str += "- For user "+ user.getIndex()+":";
			
			for (Rating r : user.getRatings()) {
				if (r.isUnrated()) {
					str += r.getRanking().toString();
				}
			}
			str += "\n";
		}
		return str;
	}
	
	public String toStringPredictedMatrix() {
		String str = ">>> user ratings predicted matrix "
				+ "(users in line, items in column):\n";
		
		for (User user : users) {
			str += user.toString();
		}
		
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			 return false;
	    }
		UserItemMatrix u = (UserItemMatrix) obj;
		if (u.getUsers().size() != users.size()) {
        	return false;
        }
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(u.getUsers().get(i)) == false) {
				return false;
			}
		}
		return true;
	}
}
