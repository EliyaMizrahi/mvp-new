package presenter;
/**
 * The FileSize program implements an application that realize the methods from CommonCommand.
 * This class apply the fileSize method that display the size of maze in the file.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 10-10-2015
 */

public class FileSize extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public FileSize(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tampArr = command.split(" ");
		if (tampArr.length != 2){
			presenter.getView().display("Invalid parameters");
		} else{
			String nameMaze = tampArr[1];
			presenter.getModel().fileSize(nameMaze);
		}
	}

}
