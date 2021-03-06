package anyviewj.debug.actions;

import anyviewj.util.Strings;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.StepRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides utility methods for perform single-step operations.
 *
 * @author  ltt
 */
public class Stepping {

    /**
     * Clear any step requests that may still be associated with the
     * given thread.
     *
     * @param  vm      virtual machine.
     * @param  thread  thread on which to remove step requests.
     */
    protected static void clearPreviousStep(VirtualMachine vm,
                                            ThreadReference thread) {
        // A previous step may not have completed on this thread, in
        // which case we need to delete it.
        EventRequestManager erm = vm.eventRequestManager();
        List requests = new ArrayList();
        Iterator iter = erm.stepRequests().iterator();
        while (iter.hasNext()) {
            StepRequest request = (StepRequest) iter.next();
            if (request.thread().equals(thread)) {
                requests.add(request);
            }
        }
        erm.deleteEventRequests(requests);
    } // clearPreviousStep

    /**
     * Perform a single step operation in the given thread.
     *
     * @param  vm          virtual machine.
     * @param  thread      thread in which to step.
     * @param  size        how much to step (one of the StepRequest constants).
     * @param  depth       how to step (one of the StepRequest constants).
     * @param  onlyThread  true to suspend only this thread.
     * @param  excludes    space-separated list of classes to exclude.
     * @return  true if successful, false if error.
     */
    public static boolean step(VirtualMachine vm, ThreadReference thread,
                               int size, int depth, boolean onlyThread,
                               String excludes) {
        try {
            // Clear any previously set step requests on this thread.
            clearPreviousStep(vm, thread);

            // Ask the event request manager to create a step request.
            EventRequestManager mgr = vm.eventRequestManager();
            StepRequest request = mgr.createStepRequest(thread, size, depth);

            // Add class exclusions.
            String[] exclude = Strings.tokenize(excludes);
            for (int i = exclude.length - 1; i > -1; i--) {
                request.addClassExclusionFilter(exclude[i]);
            }

            // Skip the first event that is fired.
            request.addCountFilter(1);
            if (onlyThread) {
                // User wants to suspend this thread only.
                request.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
            }
            request.enable();
            return true;
        } catch (VMDisconnectedException vmde) {
            return false;
        }
    } // step
} // Stepping
