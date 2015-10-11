package presenter;
/**
 * The Generate program implements an application that realize the methods from CommonCommand.
 * This class apply the generate method that create maze3d by Recursive Backtracker algorithm
 * and check the command theat we get from the client.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 10-10-2015
 */


public class Generate extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Generate(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		presenter.getModel().generate(command);
					
	}
}
