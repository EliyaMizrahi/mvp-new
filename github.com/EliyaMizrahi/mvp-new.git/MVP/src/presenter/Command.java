package presenter;
/**
 * The Command is an Interface class that has one method that 
 * will be implement differently in each class.
 * 
 * @author Eliya Mizrahi & Mor Mordoch  
 * @version 1.0
 * @since 10-10-2015
 */
public interface Command {
	public void doCommand(String command);
}
