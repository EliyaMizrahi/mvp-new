package presenter;

import java.io.Serializable;

public class Properties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sizeY;
	private int sizeX;
	private int sizeZ;
	private int numOfThread;
	private String algorithm;
	private String typeOfMaze;
	private String nameMaze;
	private String chooseView;
	private char axis;

	public Properties() {
		super();
	}
	
	public void defultProp(){
		this.sizeY = 2;
		this.sizeX = 2;
		this.sizeZ = 2;
		this.numOfThread = 5;
		this.algorithm = "BFS";
		this.typeOfMaze = "MyMaze3dGenerator";
		this.nameMaze = "mymaze";
		this.chooseView="Gui";
		this.axis='y';
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeZ() {
		return sizeZ;
	}

	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
	}

	public int getNumOfThread() {
		return numOfThread;
	}

	public void setNumOfThread(int numOfThread) {
		this.numOfThread = numOfThread;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getTypeOfMaze() {
		return typeOfMaze;
	}

	public void setTypeOfeMaze(String typeOfMaze) {
		this.typeOfMaze = typeOfMaze;
	}

	public String getNameMaze() {
		return nameMaze;
	}

	public void setNameMaze(String nameMaze) {
		this.nameMaze = nameMaze;
	}

	public String getChooseView() {
		return chooseView;
	}

	public void setChooseView(String chooseView) {
		this.chooseView = chooseView;
	}

	public char getAxis() {
		return axis;
	}

	public void setAxis(char axis) {
		this.axis = axis;
	}
}
