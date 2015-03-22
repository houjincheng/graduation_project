package anyviewj.debug.actions;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.manager.BreakpointManager;
import anyviewj.debug.breakpoint.ResolveException;
//import com.bluemarsh.jswat.ui.UIAdapter;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Class SetBreakAction allows the user to define new breakpoints.
 *
 * @author  ltt
 */
public class SetBreakAction extends AbstractDebugAction{
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new SetBreakAction object with the default action
     * command string of "setBreak".
     */
    public SetBreakAction() {
        super("setBreak");
    } // SetBreakAction

    /**
     * Performs the set breakpoint action.
     *
     * @param  event  action event
     */
    @Override
	public void actionPerformed(ActionEvent event) {
        // is there an active session?
    	//Session session = ConsoleCenter.getCurrentSession();

        // get class and line number from user
        Object[] messages = {
        	new JLabel(Bundle.getString("SetBreak.classField")),
            new JTextField(25),
            Bundle.getString("SetBreak.locationField"),
            new JTextField(25)
        };

        String className = null;
        String location = null;
        while (true) {
            // Show dialog asking user for breakpoint location.
            int response = JOptionPane.showOptionDialog(
            	new Frame(), messages, Bundle.getString("SetBreak.title"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null);
            if (response != JOptionPane.OK_OPTION) {
                return;
            }

            // Now assume the response is okay unless we find otherwise.
            className = ((JTextField) messages[1]).getText();
            location = ((JTextField) messages[3]).getText();
            if (location.length() == 0) {
                displayError(new Frame(), Bundle.getString(
                    "SetBreak.missingLocation"));
                continue;
            }

            // Attempt to create the breakpoint.
            //BreakpointManager brkman = (BreakpointManager)
            //    session.getManager(BreakpointManager.class);
            BreakpointManager brkman = ConsoleCenter.getcurrentBKManager();
            try {
                brkman.parseBreakpointSpec(className, location);
                System.out.println("breakpoint count="+brkman.getDefaultGroup().breakpointCount());
                //session.getUIAdapter().showMessage(
                //    UIAdapter.MESSAGE_NOTICE,
                //    Bundle.getString("SetBreak.breakpointAdded"));
                break;
            } catch (ClassNotFoundException cnfe) {
                displayError(new Frame(), Bundle.getString(
                    "SetBreak.invalidClassMsg"));
            } catch (ResolveException re) {
                displayError(new Frame(), re.errorMessage());
            } catch (Exception e) {
                displayError(new Frame(), e.getMessage());
            }
        }
    } // actionPerformed
} // SetBreakAction
