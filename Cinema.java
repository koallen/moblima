public class Cinema {

    public enum ClassOfCinema { GOLD, MAX, NORMAL }


	private ClassOfCinema classOfCinema;
	private Seat[][] seats;
	private int cinemaIndex;
	private int numberOfEmptySeat;
	private int row;
	private int col;



	public Cinema(ClassOfCinema cinemaClass, int row, int col, Seat[] seats, int cinemaID, int noOfEmptySeat){
		classOfCinema = cinemaClass;
		this.row = row;
		this.col = col;
		for (int i=0; i<row; i++){
			for (int j=0; j<col; j++){
				this.seats[i][j]=seats[i*col+j];
                this.seats[i][j].setID('A'+i, j+1);
			}
		}

		cinemaIndex = cinemaID;
		numberOfEmptySeat = noOfEmptySeat;
	}

	public ClassOfCinema getClassOfCinema(){
		return classOfCinema;
	}

	public Seat getSeat(int row, int col){
		return seats[row][col];
	}

	public int getCinemaIndex(){
		return cinemaIndex;
	}

	public int getNumberOfEmptySeat(){
		return numberOfEmptySeat;
	}

	public void setNumberOfEmptySeat(int numberOfEmptySeat){
		this.numberOfEmptySeat = numberOfEmptySeat;
	}

	public Seat[][] getSeatLayout(){
		return seats;
	}

	public int getRow(){
		return row;
	}

	public int getCol(){
		return col;
	}

}
