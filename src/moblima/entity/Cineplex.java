package moblima.entity;
/**
 * Represents a Cineplex organized by MOBLIMA system
 * Contains name of cineplex and
 * a list of cinemas in this Cineplex
 * @author SSP2 Team 1
 */
public class Cineplex{
	/**
	 * Name of a Cineplex
	 */
	private String cineplexName;
	/**
	 * List of cinemas in this Cineplex
	 */
	private Cinema[] cinema;
	/**
	 * Creates a Cineplex with given name,
	 * a list of cinemas and number of cinemas in this Cineplex
	 * @param cineplexName Name of this Cineplex
	 * @param cinema List of cinema in this Cineplex
	 * @param numOfCinema Number of cinema in this Cineplex
	 */
	public Cineplex(String cineplexName, Cinema[] cinema, int numOfCinema) {
		cinema = new Cinema[numOfCinema];
		this.cineplexName = cineplexName;
		for (int i=0;i<numOfCinema;i++){
			this.cinema[i]=cinema[i];
		}
	}
	/**
	 * Gets the name of this Cineplex
	 * @return the name of this Cineplex
	 */
	public String getCineplexName() {
		return cineplexName;
	}
	/**
	 * Gets a certain cinema in this Cineplex
	 * with the given cinema ID
	 * @param cinemaID the cinema ID of the requested cinema
	 * @return the requested cinema
	 */
	public Cinema getCinema(int cinemaID) {
		return cinema[cinemaID];
	}
	/**
     * Gets the string representation 
     * for the name of this cineplex
     * @return string representation of cineplex name
     */
	public String toString() {
		return cineplexName;
	}
}
