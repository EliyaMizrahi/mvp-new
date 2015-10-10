package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;
/**
 * The Model is an Interface class that has the following methods.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 10-10-2015
 */


public interface Model {
	public void generate(String nameMaze);
	public Maze3d getMaze3d(String nameMaze);
	public void getMazeCrossSectionBy(String by, String nameMaze, int index);
	public void saveMaze(String fileName);
	public void loadMaze(String fileName , String nameMaze);
	public void mazeSize(String nameMaze);
	public void fileSize(String nameMaze);
	public void solveMaze(String nameMaze);
	public Solution<Position> getMazeSolution(String nameMaze);
	public Object getUserCommand(String command);
	public void saveToZip();
	public void loadFromZip();
	public void exit();
	
	public void moveUp();
	public void moveDown();
	public void moveLeft();
	public void moveRight();
	public void moveBackward();
	public void moveForward();
	public Position getPositionFromHash(String nameMaze);
	
	public void setProperties(Properties properties);
	public Properties getProperties();
	
}
