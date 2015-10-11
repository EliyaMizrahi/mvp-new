package presenter;
/**
 * The Solve program implements an application that realize the methods from CommonCommand.
 * This class apply the solveMaze method that solve the maze by some algorithm.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 10-10-2015
 */
public class Solve extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Solve(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		String nameMaze = tempArr[0];
		//String nameAlgorithms = tempArr[1];
		presenter.getModel().solveMaze(nameMaze);
	}
}
