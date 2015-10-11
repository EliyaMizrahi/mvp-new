package presenter;

/**
 * The Load program implements an application that realize the methods from CommonCommand.
 * This class apply the loadMaze method that load a maze from a file.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 10-10-2015
 */

public class Load extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Load(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		if (tempArr.length < 1) {
			presenter.getView().display("Invalid parameters");
		} else if (tempArr[0].equals("zip")) {
			presenter.getModel().loadFromZip();
		} else {
			String fileName = tempArr[0];
			String nameMaze = tempArr[1];
			presenter.getModel().loadMaze(fileName, nameMaze);
		}
	}
}
