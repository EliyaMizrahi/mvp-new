package presenter;

public class Moves extends CommonCommand {

	/**
	 * Constructor
	 * @param presenter
	 */
	public Moves(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
			switch (command) {
			case "up":
				presenter.getModel().moveUp();
				break;
			case "down":
				presenter.getModel().moveDown();
				break;
			case "left":
				presenter.getModel().moveLeft();
				break;
			case "right":
				presenter.getModel().moveRight();
				break;
			case "backward":
				presenter.getModel().moveBackward();
				break;
			case "forward":
				presenter.getModel().moveForward();
				break;

			default:
				presenter.getView().display("Error");
				break;
			}

		}
	}





//String[] tempArr = command.split(" ");
//if (tempArr.length > 1) {
//	switch (tempArr[0]) {
//	case "up":
//		presenter.getModel().moveUp(tempArr[1]);
//		break;
//	case "down":
//		presenter.getModel().moveDown(tempArr[1]);
//		break;
//	case "left":
//		presenter.getModel().moveLeft(tempArr[1]);
//		break;
//	case "right":
//		presenter.getModel().moveRight(tempArr[1]);
//		break;
//	case "backward":
//		presenter.getModel().moveBackward(tempArr[1]);
//		break;
//	case "forward":
//		presenter.getModel().moveForward(tempArr[1]);
//		break;
//
//	default:
//		presenter.getView().display("Error");
//		break;
//	}
//
//}
//}
