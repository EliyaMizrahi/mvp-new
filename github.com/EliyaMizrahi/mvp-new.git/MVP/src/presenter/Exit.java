package presenter;
/**
 * The Exit program implements an application that realize the methods from CommonCommand.
 * This class apply the exit method close the project orderly.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 10-10-2015
 *
 */
public class Exit extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Exit(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		presenter.getModel().exit();
		presenter.getView().exit();
	}

}
