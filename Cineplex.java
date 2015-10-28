public class Cineplex{
	private String cineplexName;
	private Cinema[] cinema;

	public Cineplex(String cineplexName, Cinema[] cinema, int numOfCinema){
		cinema = new Cinema[numOfCinema];
		this.cineplexName = cineplexName;
		for (int i=0;i<numOfCinema;i++){
			this.cinema[i]=cinema[i];
		}
	}

	public String getCineplexName(){
		return cineplexName;
	}

	public Cinema getCinema(int cinemaID){
		return cinema[cinemaID];
	}
}
