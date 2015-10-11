package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;
/**
 * The View is an Interface class that has following methods.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 10-10-2015
 */
public interface View {
	
	 public void start();
	 public void displayArr(String[] arr);
	 public void display(String message);
	 public void displayCrossSectionBy(int[][] maze2d);
	 public void displaySolution(Solution<Position> solution);
	 public void setCommand(HashMap<String, Command> commandMap);
	 public void displayMaze(Maze3d maze);
	 public void displayPosition(Position position);
	 public void exit();
	 public void setProperties(Properties prop);

}
