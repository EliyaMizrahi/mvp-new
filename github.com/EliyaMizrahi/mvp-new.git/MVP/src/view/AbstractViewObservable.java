package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;

public abstract class AbstractViewObservable extends Observable implements View {
	protected BufferedReader in;
	protected PrintWriter out;
	protected HashMap<String, Command> commandMap;
	protected HashMap<Command, Object> hashObj;


	public AbstractViewObservable(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		this.commandMap = new HashMap<String, Command>();
		this.hashObj = new HashMap<Command, Object>();
	}

	public abstract void start();
	public abstract void displayArr(String[] string);
	public abstract void display(String message);
	public abstract void displayCrossSectionBy(int[][] maze2d);
	public abstract void displaySolution(Solution<Position> solution);
	public abstract void setCommand(HashMap<String, Command> commandMap);
	public abstract void exit();
	public abstract void setProperties(Properties prop);
}
