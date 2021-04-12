package recommender_system;

import java.util.ArrayList;

/**
 * This class is responsible for generating the 
 * similarity matrix associated to a UserItemMatrix
 * Immutable class
 * @author Salim Azharhoussen and Nicolas Galois
 *
 */
public final class CosineSimilarityMatrix {
	
	/**
	 * Similarity matrix: contains the cosine similarity for each 
	 * user
	 */
	private final ArrayList<ArrayList<Double>> similarityMatrix;
	
	
	/**
	 * Create a cosine similarity matrix for the given userRatingsMatix
	 * @param userRatingsMatrix is the UserItemMatrix instance from which the similarity matrix
	 * will be generated
	 */
	public CosineSimilarityMatrix(UserItemMatrix userRatingsMatrix) {
		similarityMatrix = new ArrayList<ArrayList<Double>>();
		
		for (User i : userRatingsMatrix.getUsers()) {
 			ArrayList<Double> currentSimilarity = new ArrayList<Double>();
 			for (User j : userRatingsMatrix.getUsers()) {
 				currentSimilarity.add(i.getSimilarity(j));
 			}
 			similarityMatrix.add(currentSimilarity);
 		}
	}
	
	/**
	 * @return a matrix containing the similarity cosine for all the users 
	 */
	public ArrayList<ArrayList<Double>> getSimilarityMatrix() {
		return similarityMatrix;
	}
	
	public ArrayList<Double> getSimilaryForUser(int userIndex) {
		return this.similarityMatrix.get(userIndex);
	}
	
	@Override
	public String toString() {
		String str=">>> associated similarity matrix:\n";
		for (ArrayList<Double> arrayList : similarityMatrix) {
			for (Double double1 : arrayList) {
				str+= double1+"\t";
			}
			str+="\n";
		}
		return str;
	}
}
