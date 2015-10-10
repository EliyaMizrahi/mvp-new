package guiView;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MazeDisplayer extends Canvas {

	protected Maze3d myMaze;
	protected Position currPosition;
	protected Solution<Position> solution;

	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
		this.currPosition = new Position(0, 0, 0);
	}

	
	public Maze3d getMyMaze() {
		return myMaze;
	}
	
	public void setMyMaze(Maze3d maze) {
		this.myMaze = maze;
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				redraw();
			}
		});

	}

	public Position getCurrPosition() {
		return currPosition;
	}
	
	public void setCurrentPosition(Position position) {
		this.currPosition = position;
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				redraw();
			}
		});

	}


	public Solution<Position> getSolution() {
		return solution;
	}


	public void setSolution(Solution<Position> solution) {
		this.solution = solution;
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				redraw();
			}
		});
	}

}
