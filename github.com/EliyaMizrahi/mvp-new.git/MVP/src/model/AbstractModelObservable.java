package model;

import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;


public abstract class AbstractModelObservable extends Observable implements Model {

	protected HashMap<String, Maze3d> hashMaze;
	private HashMap<String, Object> commandMap;
	protected Properties properties;
	protected ExecutorService threadPool;

	public AbstractModelObservable() {
		this.hashMaze = new HashMap<String, Maze3d>();
		this.commandMap = new HashMap<String, Object>();
		threadPool = Executors.newCachedThreadPool();
		this.properties= new Properties();
		properties.defultProp();
	}

	public abstract void generate(String nameMaze);
	public abstract Maze3d getMaze3d(String nameMaze);
	public abstract void getMazeCrossSectionBy(String by, String nameMaze, int index);
	public abstract void saveMaze(String fileMaze);
	public abstract void loadMaze(String fileName, String nameMaze);
	public abstract void mazeSize(String nameMaze);
	public abstract void fileSize(String nameMaze);
	public abstract void solveMaze(String nameMaze);
	public abstract Solution<Position> getMazeSolution(String nameMaze);
	public abstract void saveToZip();
	public abstract void loadFromZip();
	public abstract void exit();
	public abstract void moveUp();
	public abstract void moveDown();
	public abstract void moveLeft();
	public abstract void moveRight();
	public abstract void moveBackward();
	public abstract void moveForward();
	
	public abstract Position getPositionFromHash(String nameMaze);

	protected void setNotifyObserversName(String command, Object obj) {
		if (obj != null) {
			commandMap.put(command, obj);
		}
		setChanged();
		notifyObservers(command);
	}

	@Override
	public Object getUserCommand(String command) {
		return commandMap.get(command);
	}
	
	public Properties getProperties() {
		return properties;
	}


	public void setProperties(Properties p) {
		this.properties = p;
		ExecutorService thread = threadPool;
		threadPool = Executors.newFixedThreadPool(properties.getNumOfThread()) ;
		thread.shutdown();
	}
}
