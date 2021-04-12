package recommender_system;

/**
 * This class is responsible for storing an item ranking
 * @author Salim Azharhoussen and Nicolas Galois
 */
public class Ranking {
    private int itemIndex;
    private int nbLikeFromSimilarUsers;
    private int associatedRate;
    private int popularity;

    public Ranking(int itemIndex){
      this.itemIndex = itemIndex;
      this.associatedRate = 0;
      this.popularity=0;
    }
    
    public int getAssociatedRate() {
		return associatedRate;
	}
    
    public int getItemIndex() {
		return itemIndex;
	}
    
    public int getNbLikeFromSimilarUsers() {
		return nbLikeFromSimilarUsers;
	}
    
    public int getPopularity() {
		return popularity;
	}
    
    public void setAssociatedRate(int associatedRate) {
		this.associatedRate = associatedRate;
	}
    
    public void setNbLikeFromSimilarUsers(int nbLikeFromSimilarUsers) {
		this.nbLikeFromSimilarUsers = nbLikeFromSimilarUsers;
	}
    
    public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
    
   
    /**
     *   
     * @param scoringCoeff 
     * @return the score
     */
    public int getScoring(ScoringCoeff scoringCoeff) {
    	return (int)Math.round(scoringCoeff.getLikeCoefficient() * nbLikeFromSimilarUsers + 
    			scoringCoeff.getRateCoefficient() * associatedRate + 
    			scoringCoeff.getPopularityCoefficient() * popularity);
    }
    
    @Override
    public String toString() {
    	return "\nItem"+ itemIndex +": Mean Similar:" + 
    			nbLikeFromSimilarUsers + ", Mean overall:" + 
    			associatedRate + ", Popularity: " + popularity + 
    			", Scoring: " + getScoring(new ScoringCoeff());
    }
}
