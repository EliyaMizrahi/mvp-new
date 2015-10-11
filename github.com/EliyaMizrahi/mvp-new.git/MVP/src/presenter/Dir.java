package presenter;

import java.io.File;

/**
 * The Dir program implements an application that realize the methods from CommonCommand.
 * This class display whole files and folders in some path.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 10-10-2015
 */
public class Dir extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Dir(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		if (command == null) {
			presenter.getView().display("Invalid parameters");
		} else {
			try {
				File file = new File(command);
				if (file.list().length == 0) {
					presenter.getView().display("The file is not exist");
				} else { 
					presenter.getView().displayArr(file.list());
				}
			} catch (NullPointerException e) {
				presenter.getView().display("Invalid path");
			}
		}
	}
}