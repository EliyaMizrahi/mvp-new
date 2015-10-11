package presenter;
/**
 * The Save program implements an application that realize the methods from CommonCommand.
 * This class apply the saveMaze method that save the maze into a file.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 10-10-2015
 */
public class Save extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Save(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		if (tempArr.length < 1 ){
			presenter.getView().display("Invalid parameters");
		} else if(tempArr[0].equals("zip")){
			presenter.getModel().saveToZip();
		}else{
			String nameMaze = tempArr[0];
			//String fileName = tempArr[1];
			presenter.getModel().saveMaze(nameMaze);
		}
	}

}
