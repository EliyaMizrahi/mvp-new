package guiView;

import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


public class MazeWindow extends BasicWindow {

	protected Position currPosition;
	protected Maze3d myMaze;
	protected Solution<Position> solution;
	protected SelectionListener generateListener, solveListener, exitListener, saveListener, loadListener,
			propertiesListener;
	protected DisposeListener disposeExit;
	protected KeyListener keyListener;
	protected ArrayList<MazeDisplayer> widgetDisplayerList;
	protected Menu menuBar, fileMenu, gameMenu;
	protected MenuItem fileSaveItem, fileLoadItem, fileExitItem, filePropertiesItem, gameSolveItem, gameStartItem;
	protected MenuItem fileMenuHeader, gameMenuHeader;
	protected Label label;
	protected Button playButton;
	protected String file, chooseXmlFile;

	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		this.widgetDisplayerList = new ArrayList<MazeDisplayer>();
		playButton = new Button(shell, 0);
		chooseXmlFile = null;
		updateWidgets();
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));

		menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);

		gameMenu = new Menu(shell, SWT.DROP_DOWN);
		gameMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		gameMenuHeader.setText("&Game");

		gameMenuHeader.setMenu(gameMenu);
		gameStartItem = new MenuItem(gameMenu, SWT.PUSH);
		gameStartItem.setText("&Start");
		gameSolveItem = new MenuItem(gameMenu, SWT.PUSH);
		gameSolveItem.setText("&Solve");

		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		filePropertiesItem = new MenuItem(fileMenu, SWT.PUSH);
		filePropertiesItem.setText("&Properties");

		fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Save");

		fileLoadItem = new MenuItem(fileMenu, SWT.PUSH);
		fileLoadItem.setText("&Load");

		fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Exit");

		fileExitItem.addSelectionListener(exitListener);
		fileSaveItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Save");
				fd.setFilterPath("");
				String[] txtString = { "*.txt" };
				fd.setFilterExtensions(txtString);
				setFile(fd.open());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		fileSaveItem.addSelectionListener(saveListener);

		fileLoadItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Load");
				fd.setFilterPath("");
				String[] txtString = { "*.txt" };
				fd.setFilterExtensions(txtString);
				setFile(fd.open());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		fileLoadItem.addSelectionListener(loadListener);
		gameStartItem.addSelectionListener(generateListener);

		MazeDisplayer maze = new guiView.Maze3d(shell, SWT.BORDER, 'y');
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		widgetDisplayerList.add(maze);

		gameSolveItem.addSelectionListener(solveListener);
		playButton.addKeyListener(keyListener);
		filePropertiesItem.addSelectionListener(propertiesListener);

	}

	public void updateWidgets() {
		for (MazeDisplayer widget : widgetDisplayerList) {
			if (myMaze != null) {
				widget.setMyMaze(myMaze);
			}
			if (currPosition != null) {
				widget.setCurrentPosition(currPosition);
			}
			if (solution != null) {
				widget.setSolution(solution);
			}
		}
	}

	public void movePlayer(int y, int x, int z) {
		if (y >= 0 && y < myMaze.getSizeY() && x >= 0 && x < myMaze.getSizeX() && z >= 0 && z < myMaze.getSizeZ()) {
			if (myMaze.getCell(y, x, z) == 0) {
				currPosition.setY(y);
				currPosition.setX(x);
				currPosition.setZ(z);
				updateWidgets();
			}
		}
	}

	public Position getCurrPosition() {
		return currPosition;
	}

	public void setCurrPosition(Position currPosition) {
		this.currPosition = currPosition;
		updateWidgets();
	}

	public Maze3d getMyMaze() {
		return myMaze;
	}

	public void setMyMaze(Maze3d myMaze) {
		this.myMaze = myMaze;
		updateWidgets();
	}

	public Solution<Position> getSolution() {
		return solution;
	}

	public void setSolution(Solution<Position> solution) {
		this.solution = solution;
		updateWidgets();
	}

	public void displaySolutionMaze(String solution) {
		MessageBox solutionMaze = new MessageBox(shell, SWT.ICON_INFORMATION);
		solutionMaze.setText("solution");
		solutionMaze.setMessage(solution);
		solutionMaze.open();
	}

	public void messegeBox(String string) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
				messageBox.setMessage(string);
				messageBox.setText("Pay Attention!");
				messageBox.open();

			}
		});
	}

	public void setGenerateListener(SelectionListener generateListener) {
		this.generateListener = generateListener;
	}

	public void setSolveListener(SelectionListener solveListener) {
		this.solveListener = solveListener;
	}

	public DisposeListener getDisposeExit() {
		return disposeExit;
	}

	public void setDisposeExit(DisposeListener disposeExit) {
		this.disposeExit = disposeExit;
		shell.addDisposeListener(disposeExit);
	}

	public void setExitListener(SelectionListener exitListener) {
		this.exitListener = exitListener;
	}

	public SelectionListener getSaveListener() {
		return saveListener;
	}

	public void setSaveListener(SelectionListener saveListener) {
		this.saveListener = saveListener;
	}

	public SelectionListener getLoadListener() {
		return loadListener;
	}

	public void setLoadListener(SelectionListener loadListener) {
		this.loadListener = loadListener;
	}

	public SelectionListener getPropertiesListener() {
		return propertiesListener;
	}

	public void setPropertiesListener(SelectionListener propertiesListener) {
		this.propertiesListener = propertiesListener;
	}

	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFile() {
		return file;
	}

	public void setChooseXmlFile(String chooseXmlFile) {
		this.chooseXmlFile = chooseXmlFile;
	}

	public String getChooseXmlFile() {
		return chooseXmlFile;
	}
}
