package recommender_system;

/**
 * This class is represent a rate given on an item
 * @author Salim Azharhoussen and Nicolas Galois
 *
 */
public class Rating implements Comparable<Rating> {
	
	/**
	 * The number/index of the rated item
	 */
	private int item;
	
	/**
	 * The item rate
	 */
	private int rate;
	
	/**
	 * The item rater
	 */
	private User rater;
	
	/**
	 * True if this Rating is unrated and rated by similar users
	 */
	private boolean ratedBySimilarUsers;
	
	/**
	 * Contains the item ranking used for prediction
	 */
	private Ranking ranking;
	
	/**
	 * Create an instance of Rating
	 * @param item is the id of the item 
	 * @param rate is the given rate
	 * @param rater the item rater
	 */
	public Rating(int item, int rate, User rater) {
		this.item = item;
		this.rate = rate;
		this.ratedBySimilarUsers = false;
		this.rater = rater;
		this.ranking = new Ranking(item);
	}
	
	/**
	 * @return the item index of this rating
	 */
	public int getItem() {
		return item;
	}
	
	/**
	 * @return the rate for this rating
	 */
	public int getRate() {
		return rate;
	}
	
	/**
	 * @return the rater
	 */
	public User getRater() {
		return rater;
	}
	
	/**
	 * @param ratedBySimilarUsers
	 */
	public void setRatedBySimilarUsers(boolean ratedBySimilarUsers) {
		this.ratedBySimilarUsers = ratedBySimilarUsers;
	}
	
	public Ranking getRanking() {
		return ranking;
	}
	
	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}
	
	public void setRankingAssociatedRate(int mean) {
		ranking.setAssociatedRate(mean);
	}
	/**
	 * Return true if the item is unrated
	 * @return true if rate = -1. Else false
	 */
	public boolean isUnrated() {
		return rate==-1;
	}
	
	public boolean isUnratedAndRatedBySimilarUsers(){
		return isUnrated() && ratedBySimilarUsers;
	}
	
	@Override
	public String toString() {
		return rate+"\t";
	}

	@Override
	public int compareTo(Rating rating) {
		if (this.rate>rating.getRate()) return 1;
		if (this.rate<rating.getRate()) return -1;
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
		Rating rating = (Rating)obj;
		return this.rate == rating.getRate();
	}
}
