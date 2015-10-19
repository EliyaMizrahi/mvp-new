package model;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.MazeSearchable;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * The MyModelObservable program implements an application that realize the methods from
 * AbstractModelObservable.
 * MyModelObservable consist from HashMap.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 11-10-2015
 *
 */
public class MyModelObservable extends AbstractModelObservable {

	private HashMap<Maze3d, Solution<Position>> mazeSolutionMap;
	private HashMap<String, Position> hashPosition;

	/**
	 * Constructor
	 */
	public MyModelObservable() {
		super();
		this.mazeSolutionMap = new HashMap<Maze3d, Solution<Position>>();
		this.hashPosition = new HashMap<String, Position>();
		threadPool = Executors.newFixedThreadPool(properties.getNumOfThread());

		try {
			loadFromZip();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to create maze3d by Recursive Backtracker algorithm.
	 * @param nameMaze
	 */
	@Override
	public void generate(String nameMaze) {
		String name = properties.getNameMaze();
		int y = properties.getSizeY();
		int x = properties.getSizeY();
		int z = properties.getSizeY();
		Future<Maze3d> fCallMaze = threadPool.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d myMaze = new MyMaze3dGenerator(y, x, z).generate(y, x, z);
				return myMaze;
			}
		});
		try {
			hashMaze.put(name, fCallMaze.get());
			hashPosition.put(name, fCallMaze.get().getStartPosition());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		setNotifyObserversName("mazeIsReady", getMaze3d(name));
	}

	
	/**
	 * This method is used to get the maze3d.
	 * @param Maze3d
	 */
	@Override
	public Maze3d getMaze3d(String nameMaze) {
		Maze3d myMaze = hashMaze.get(nameMaze);
		return myMaze;

	}

	/**
	 * This method is used to get cross section by y / x / z axis.
	 * @param by
	 * @param nameMaze
	 * @param index
	 */
	@Override
	public void getMazeCrossSectionBy(String by, String nameMaze, int index) {
		String name = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(name);

		try {
			switch (by) {

			case "Y":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByY(index));
				break;
			case "y":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByY(index));
				break;
			case "X":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByX(index));
				break;
			case "x":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByX(index));
				break;
			case "Z":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByZ(index));
				break;
			case "z":
				setNotifyObserversName("displayCrossSectionBy", myMaze.getCrossSectionByZ(index));
				break;
			default:
				setChanged();
				notifyObservers("Invalid parametars");
				return;
			}
		} catch (IndexOutOfBoundsException e) {
			setChanged();
			notifyObservers("Invalid index");
			return;
		}
	}

	/**
	 * This method is use to save the maze into a file. 
	 * @param nameMaze
	 * @param fileName
	 */
	@Override
	public void saveMaze(String fileName) {
		String name = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(name);
		try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			out.write(myMaze.toByteArray());
			out.close();
			setNotifyObserversName("saveMaze", fileName);
		} catch (FileNotFoundException e) {
			setNotifyObserversName("Invalid file", fileName);
		} catch (IOException e) {
			setNotifyObserversName("Invalid compress", name);
		}
	}

	/**
	 * This method is use to load the maze from a file.
	 * @param fileName
	 * @param nameMaze
	 */
	@Override
	public void loadMaze(String fileName, String nameMaze) {

		properties.setNameMaze(nameMaze);
		try {
			InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
			byte[] bArr = new byte[34586];
			int numByte = in.read(bArr);
			in.read(bArr);
			in.close();
			byte[] newbArr = new byte[numByte];
			for (int i = 0; i < newbArr.length; i++) {
				newbArr[i] = bArr[i];
			}
			Maze3d myMaze = new Maze3d(bArr);
			hashMaze.put(nameMaze, myMaze);
			hashPosition.put(nameMaze, myMaze.getStartPosition());
			setNotifyObserversName("loadMaze", getMaze3d(nameMaze));

		} catch (FileNotFoundException e) {
			setNotifyObserversName("Invalid file", fileName);

		} catch (IOException e) {
			setNotifyObserversName("Invalid maze", nameMaze);
		}
	}

	
	/**
	 * This method is use to display the size of the maze in memory.
	 * @param nameMaze
	 */
	@Override
	public void mazeSize(String nameMaze) {
		String name = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(name);
		int size = myMaze.getSizeY() * myMaze.getSizeX() * myMaze.getSizeZ() + 36;
		setNotifyObserversName("mazeSize", size);
	}

	
	/**
	 * This method is use to display the size of maze in the file.
	 * @param nameMaze
	 */
	@Override
	public void fileSize(String nameMaze) {
		Maze3d myMaze = hashMaze.get(nameMaze);
		if (myMaze == null) {
			setNotifyObserversName("null", nameMaze);
			return;
		} else {
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			MyCompressorOutputStream compOut = new MyCompressorOutputStream(bOut);
			try {
				compOut.write(myMaze.toByteArray());
				setNotifyObserversName("fileSize", bOut.size());
			} catch (IOException e) {
				setNotifyObserversName("Invalid compress", nameMaze);
			}
		}
	}

	/**
	 * This method is use to solve the maze by some algorithm.
	 * @param nameMaze
	 */
	@Override
	public void solveMaze(String nameMaze) {
		String name= properties.getNameMaze();
		String algo= properties.getAlgorithm();
		Maze3d myMaze = hashMaze.get(name);
		if (myMaze != null) {
			if (!(mazeSolutionMap.containsKey(myMaze))) {
				Future<Solution<Position>> fCallSolution = threadPool.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						Searcher<Position> algorithms;
						Searchable<Position> mazeSearchable = new MazeSearchable(myMaze);
						Solution<Position> solution = new Solution<Position>();

						switch (algo) {

						case "BFS":
							algorithms = new BFS<Position>();
							solution = algorithms.search(mazeSearchable);
							break;

						case "Astar-AirDistance":
							algorithms = new Astar<Position>(new MazeAirDistance());
							solution = algorithms.search(mazeSearchable);
							break;

						case "Astar-ManhattanDistance":
							algorithms = new Astar<Position>(new MazeManhattanDistance());
							solution = algorithms.search(mazeSearchable);
							break;

						default:
							setChanged();
							notifyObservers("Invalid algorithm");
							return null;
						}
						return solution;
					}
				});

				try {
					mazeSolutionMap.put(myMaze, fCallSolution.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			setNotifyObserversName("solutionIsReady", name);
		} else {
			setNotifyObserversName("null", name);
		}
	}

	/**
	 * This method is used to get maze solution 
	 * @param nameMaze
	 * @return solution
	 */
	@Override
	public Solution<Position> getMazeSolution(String nameMaze) {
		String name= properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(name);
		if (myMaze == null) {
			setNotifyObserversName("null", name);
			return null;
		} else {
			Solution<Position> solution = mazeSolutionMap.get(myMaze);
			return solution;
		}
	}

	/**
	 * This method is used to save the maze details to zip file
	 */
	@Override
	public void saveToZip() {
		try {
			ObjectOutputStream mazeOut = new ObjectOutputStream(
					new GZIPOutputStream(new FileOutputStream("fileMazeZip.zip")));
			mazeOut.writeObject(hashMaze);
			mazeOut.writeObject(mazeSolutionMap);
			mazeOut.flush();
			mazeOut.close();
			setNotifyObserversName("saveZip", "fileMazeZip.zip");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to load the maze details from zip file
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void loadFromZip() {

		try {

			FileInputStream fileMaze = new FileInputStream("fileMazeZip.zip");
			ObjectInputStream mazeIn = new ObjectInputStream(new GZIPInputStream(fileMaze));
			hashMaze = (HashMap<String, Maze3d>) mazeIn.readObject();
			mazeSolutionMap = (HashMap<Maze3d, Solution<Position>>) mazeIn.readObject();
			mazeIn.close();
			setNotifyObserversName("loadZip","fileMazeZip.zip");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * This method is use to close the project orderly.
	 */
	@Override
	public void exit() {
		saveToZip();
		threadPool.shutdownNow();
		try {
			while (!(threadPool.awaitTermination(10, TimeUnit.SECONDS)))
				;
			setChanged();
			notifyObservers("exit");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to get the maze solution from the HashMap
	 * @return HashMap
	 */
	public HashMap<Maze3d, Solution<Position>> getMazeSolutionMap() {
		return mazeSolutionMap;
	}

	/**
	 * This method is used to set the maze solution of the HashMap
	 * @param mazeSolutionMap
	 */
	public void setMazeSolutionMap(HashMap<Maze3d, Solution<Position>> mazeSolutionMap) {
		this.mazeSolutionMap = mazeSolutionMap;
	}

	/**
	 * This method is used to get the position from the HashMap
	 * @return HashMap
	 */
	public HashMap<String, Position> getHashPosition() {
		return hashPosition;
	}

	/**
	 * This method is used to set the position of the HashMap
	 * @param hashPosition
	 */
	public void setHashPosition(HashMap<String, Position> hashPosition) {
		this.hashPosition = hashPosition;
	}

	/**
	 * This method is used to update the position according to "up" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveUp() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Up") {
				position.setY(position.getY() - 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}
	}

	/**
	 * This method is used to update the position according to "down" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveDown() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Down") {
				position.setY(position.getY() + 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}

	}

	/**
	 * This method is used to update the position according to "left" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveLeft() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Left") {
				position.setZ(position.getZ() - 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}

	}

	/**
	 * This method is used to update the position according to "right" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveRight() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Right") {
				position.setZ(position.getZ() + 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}

	}

	/**
	 * This method is used to update the position according to "backward" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveBackward() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Backward") {
				position.setX(position.getX() - 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}

	}

	/**
	 * This method is used to update the position according to "forward" command that receive
	 * from the method getPossibleMoves
	 */
	@Override
	public void moveForward() {
		String nameMaze = properties.getNameMaze();
		Maze3d myMaze = hashMaze.get(nameMaze);
		Position position = hashPosition.get(nameMaze);
		String[] moves = myMaze.getPossibleMoves(position);
		for (String moveString : moves) {
			if (moveString == "Forward") {
				position.setX(position.getX() + 1);
				hashPosition.put(nameMaze, position);
				setNotifyObserversName("move", nameMaze);
			}
		}
	}

	/**
	 * This method is used to get specific position from the hash
	 * @param nameMaze
	 * @return Position
	 */
	@Override
	public Position getPositionFromHash(String nameMaze) {
		return hashPosition.get(nameMaze);
	}

}
