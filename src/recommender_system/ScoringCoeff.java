package recommender_system;

/**
 * This class stores the coefficient that will be used in the Scoring function
 * @author Salim Azharhoussen and Nicolas Galois
 *
 */
public class ScoringCoeff {
	
	/**
	 * coefficient for like
	 */
	private double likeCoefficient;
	
	/**
	 * coefficient for the number of associated rates in the corpus 
	 */
	private double rateCoefficient;
	
	/**
	 * coefficient for the popularity of an item
	 */
	private double popularityCoefficient;
	
	/**
	 * Default coefficient for like
	 */
	private static final double DEFAULT_LIKE_COEFF = 0.6;
	
	/**
	 * Default coefficient for the number of associated rates in the corpus 
	 */
	private static final double DEFAULT_RATE_COEFF = 0.2;
	
	/**
	 * Default coefficient for the popularity of an item
	 */
	private static final double DEFAULT_POPULARITY_COEFF = 0.2;
	
	
	/**
	 * Create a ScoringCoeff object with customize coefficient
	 * @param likeCoefficient
	 * @param rateCoefficient
	 * @param popularityCoefficient
	 */
	public ScoringCoeff(double likeCoefficient, 
			double rateCoefficient, 
			double popularityCoefficient) {
		
		this.likeCoefficient = likeCoefficient;
		this.rateCoefficient = rateCoefficient;
		this.popularityCoefficient = popularityCoefficient; 
	}
	
	/**
	 * Create a ScoringCoeff object with default coefficients
	 */
	public ScoringCoeff() {
		this.likeCoefficient = DEFAULT_LIKE_COEFF;
		this.rateCoefficient = DEFAULT_RATE_COEFF;
		this.popularityCoefficient = DEFAULT_POPULARITY_COEFF; 
	}
	public double getLikeCoefficient() {
		return likeCoefficient;
	}
	
	public double getPopularityCoefficient() {
		return popularityCoefficient;
	}
	
	public double getRateCoefficient() {
		return rateCoefficient;
	}
}
