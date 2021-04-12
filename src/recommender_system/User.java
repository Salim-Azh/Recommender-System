package recommender_system;

import java.util.ArrayList;
import java.util.Random;

/**
 *  This class represent a rater
 * @author Salim Azharhoussen and Nicolas Galois
 */

public class User {
	
	/**
	 * index of the user in the matrix
	 */
	private int index;
	
	/**
	 * ratings contains this user ratings for all items
	 * if a the user has not rated on item the item will 
	 * be rated with -1.
	 */
	private ArrayList<Rating> ratings;
	
	/**
	 * Contains the index of the initial unrated items in the ratings collection
	 * (before any prediction)
	 */
	private final ArrayList<Integer> initialUnratedItems;
	
	/**
	 * threshold for where a rate is consider good
	 */
	private static final int GOOD_RATE_FLAG = 60;

	/**
	 * Create a user object with random ratings for nbItems items.
	 * The rates take their values between 1 and 100. Unrated items takes
	 * -1. 
	 * @param nbItems is the number of item rated
	 * @param index is the index of the user in the matrix
	 */
	public User(int nbItems , int index) {
		ratings = new ArrayList<Rating>();
		this.index = index;
		//First generate two list of random 
		//value between 1 and 10
		int randmax = 10;
		int randmin = 1;
		
		ArrayList<Integer> v1 = new ArrayList<Integer>();
		ArrayList<Integer> v2 = new ArrayList<Integer>();

		for (int i = 0 ; i < nbItems ; i++){
			v1.add(User.randInt(randmin, randmax)); 
			v2.add(User.randInt(randmin, randmax));
		}
		
		//Then we process a transformation on the two list
		//And add the result to ratings
		for (int i = 0; i < nbItems; i++) {
			Random r = new Random();
			int flag = r.nextInt(10);
			// probability of flag > 1 = 4/5
			// means that we want almost 1/5 of the
			// matrix to -1
			if (flag > 1) {
				int note = 0;
				note += v1.get(i)*v2.get(i);
				ratings.add(new Rating(i, note, this));
			} else {
				ratings.add(new Rating(i, -1, this));
			}
		}
		
		//Save the initial unrated items for kItem
		initialUnratedItems = new ArrayList<Integer>();
		for (Rating rating : ratings) {
			if (rating.isUnrated()) {
				initialUnratedItems.add(rating.getItem());
			}
		}
	}
	  
	/**
	 * @return the user rating on all items. Unrated item has the value -1
	 */
	public ArrayList<Rating> getRatings() {
		return ratings;
	}
	
	/**
	 * @param ratings It size must be the same as the current rating size
	 */
	public void setRatings(ArrayList<Rating> ratings) {
		if (ratings.size() != this.ratings.size()) {
			throw new IllegalArgumentException("The collection lenght"
					+ " is not the same as the current ratings collection");
		}
		this.ratings = ratings;
	}
	
	private void setRating(int indexRate, Rating rate) {
		getRatings().set(indexRate, rate);
	}
	
	/**
	 * @return the initial unrated items of this user
	 */
	public ArrayList<Integer> getInitialUnratedItems() {
		return initialUnratedItems;
	}
	
	/**
	 * @return return the number of the user in the matrix
	 */
	public int getIndex() {
		return index;
	}
	
	/**
    * Generates a pseudo-random integer in the range [min, max]
    * @param min : the starting value of the range (inclusive)
    * @param max : the ending value of the range (inclusive)
    */
    public static int randInt(int min, int max)
    {
        if (min > max || (max - min + 1 > Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("Invalid range");
        }
 
        return new Random().nextInt(max - min + 1) + min;
    }
	
    /**
     * @return true if this user had no initial unrated items
     */
    public boolean isEmptyInitialUnratedItems() {
    	return initialUnratedItems.isEmpty();
    }
    
    /**
     * @return true if one of the items is still unrated by this user
     * else return false 
     */
    public boolean checkUnratedItems() {
    	for (Rating rating : ratings) {
			if (rating.isUnrated()) {
				return true;
			}
		}
    	return false;
    }
	
    /**
     * @param user
     * @return return the dotProduct between the ratings of this and user
     */
 	public double dotProduct(User user) {
 			double prod = 0;
 			for (int i = 0; i < this.getRatings().size(); i++) {
 				if (!this.getRatings().get(i).isUnrated() && 
 						!user.getRatings().get(i).isUnrated()) {
 					prod += this.getRatings().get(i).getRate()*user.
 							getRatings().get(i).getRate();
 				}
 			}
 			return prod;
 	}

 	/**
 	 * @param user
 	 * @return the cosine similarity between the ratings of the this
 	 * and the ratings of a given user 
 	 */
	public double getSimilarity(User user) {
		
		double v1N = Math.sqrt(this.dotProduct(this));
		double v2N = Math.sqrt(user.dotProduct(user));
		double res = 0;
		if (v1N<v2N) {
			res = this.dotProduct(user) / Math.pow(v2N,2);
		}
		else {
			res = this.dotProduct(user) / Math.pow(v1N,2);
		}
		return Math.round(res*10000)/10000.0; //precision: four digits
	}
    
	/**
	 * @return the number of items
	 */
	public int getNbItems() {
		return ratings.size();
	}
	
	/**
	 * @param users is the collection of similar users for this user
	 */
	public void setUnratedRatedBySimilar(ArrayList<User> users) {
		resetUnratedRatedBySimilar();
		for (User user : users) {
			for (int i = 0; i < ratings.size(); i++) {
				if (this.ratings.get(i).isUnrated()
						&& !user.ratings.get(i).isUnrated()) {
					this.ratings.get(i).setRatedBySimilarUsers(true);
				}
			}
		}
	}
	
	/**
	 * reset all the unrated rated by similar to false
	 */
	public void resetUnratedRatedBySimilar() {
		for (Rating rating : ratings) {
			rating.setRatedBySimilarUsers(false);
		}
	}
	
	/**
	 * @param users
	 */
	public void setLikeRanking(ArrayList<User> users) {
		for (Rating rating : ratings) {
			if (rating.isUnratedAndRatedBySimilarUsers()) {
				int countLike = 0;
				int sumRate = 0;
				for (User user : users) {
					int similarUserRate = user.getRatings().
							get(rating.getItem()).getRate();
					if (similarUserRate!=-1) {
						countLike++;
						sumRate += similarUserRate;
					}
				}
				if (countLike!=0) {
					rating.getRanking().
						setNbLikeFromSimilarUsers(sumRate/countLike);
				}
				else {
					rating.getRanking().setNbLikeFromSimilarUsers(0);
				}
			}
		}
	}
	
	/**
	 * @param userRatingsMatrix
	 */
	public void setAssociatedLike(ArrayList<User> users) {
		for (Rating rating : ratings) {
			if (rating.isUnrated()) {
		
				int countLike = 0;
				int sumRate = 0;
				
				for (User user : users) {
					int userRate = user.getRatings().
							get(rating.getItem()).getRate();
					if (userRate!=-1) {
						countLike++;
						sumRate += userRate;
					}
				}
				
				rating.getRanking().setAssociatedRate(sumRate/countLike);
			}
		}
	}
	
	public void setPopularity(ArrayList<User> users) {
		for (Rating rating : ratings) {
			
			if (rating.isUnrated()) {
				int countLike = 0;
			
				for (User user : users) {
					int userRate = user.getRatings().
							get(rating.getItem()).getRate();
				
					if (userRate>User.GOOD_RATE_FLAG) {
						countLike++;
					}
				}

				rating.getRanking().setPopularity(countLike);
			}
		}
	}
	
	/**
	 * Function used to fill the missing notes with the score of this item
	 */
	public void setMissingNotes() {
		for (Integer indexNote : getInitialUnratedItems()) {
			int score = getRatings().get(indexNote).getRanking().getScoring(new ScoringCoeff());
			setRating(indexNote, new Rating(indexNote, score, this));
		}
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User u = (User) obj;
        if (u.getNbItems() != this.getNbItems()) {
        	return false;
        }
        for (int i = 0; i < this.ratings.size(); i++) {
     	   if (u.getRatings().get(i).equals(this.ratings.get(i)) == false) {
     		   return false;
     	   }
        }
        return true;
	}
	
	@Override
	public String toString() {
		String str="";
		for (Rating rating : ratings) {
			str+= rating.toString();
		}
		return str+"\n";
	}
}
