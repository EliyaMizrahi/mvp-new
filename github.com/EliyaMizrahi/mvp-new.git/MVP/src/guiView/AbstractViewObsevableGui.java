package guiView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import view.AbstractViewObservable;

/**
 * The AbstractViewObsevableGui program implements an application that inherits AbstractViewObservable.
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 11-10-2015
 *
 */
public abstract class AbstractViewObsevableGui extends AbstractViewObservable {
	
	/**
	 * Constructor
	 * @param in
	 * @param out
	 */
	public AbstractViewObsevableGui(BufferedReader in, PrintWriter out) {
		super(in, out);
	}
	
	public abstract void start();
	public abstract void displayArr(String[] string);
	public abstract void display(String message);
	public abstract void displayCrossSectionBy(int[][] maze2d);
	public abstract void displaySolution(Solution<Position> solution);
	public abstract void setCommand(HashMap<String, Command> commandMap);
	public abstract void exit();
	
}
