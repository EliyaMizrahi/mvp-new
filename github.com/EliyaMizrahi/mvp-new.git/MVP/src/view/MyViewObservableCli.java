package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;

public class MyViewObservableCli extends AbstractViewObservable {

	private ExecutorService threadPool;

	public MyViewObservableCli(BufferedReader in, PrintWriter out) {
		super(in, out);
		this.threadPool = Executors.newCachedThreadPool();
	}

	@Override
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					out.println("Please enter command");
					out.flush();
					String line = in.readLine();
					while (!(line.equals("exit"))) {
						setChanged();
						notifyObservers(line);
						try {
							threadPool.awaitTermination(3, TimeUnit.SECONDS);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						out.println("Please enter a new command");
						out.flush();
						line = in.readLine();
					}
					setChanged();
					notifyObservers(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).start();

	}

	@Override
	public void displayArr(String[] string) {
		if (string != null) {
			for (String s : string) {
				out.println(s);
			}
			out.flush();
		} else {
			out.println("null");
			out.flush();
		}
	}

	@Override
	public void display(String message) {
		if (message != null) {
			out.println(message);
			out.flush();
		} else {
			out.println("error");
			out.flush();
		}
	}


	@Override
	public void displayCrossSectionBy(int[][] maze2d) {
		for (int i = 0; i < maze2d.length; i++) {
			for (int j = 0; j < maze2d[i].length; j++) {
				out.print(maze2d[i][j]);
			}
			out.println();
			out.flush();
		}
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		solution.printStack();
	}

	@Override
	public void setCommand(HashMap<String, Command> commandMap) {
		this.commandMap = commandMap;
	}

	@Override
	public void displayMaze(Maze3d maze) {
		maze.print();
	}

	@Override
	public void displayPosition(Position position) {
		out.println(position);
		out.flush();
	}
	
	@Override
	public void exit() {
		out.println("Everything successfully closed");
		out.flush();
	}

	@Override
	public void setProperties(Properties prop) {
		if (!prop.getChooseView().equals("Command line"))
		{
			setChanged();
			notifyObservers("replaceUserInterface");
		}
		
	}
}
