package cz.mendelu.xotradov;

import hudson.Extension;
import hudson.model.Queue;
import hudson.model.RootAction;
import hudson.model.queue.QueueSorter;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.annotation.CheckForNull;
import java.io.IOException;
import java.util.logging.Logger;

@SuppressWarnings("unused")
@Extension
public class ResetAction implements RootAction {
    private static Logger logger = Logger.getLogger(ResetAction.class.getName());
    public void doReset(final StaplerRequest request, final StaplerResponse response) {
        //todo permision check
        QueueSorter queueSorter = Jenkins.get().getQueue().getSorter();
        if (queueSorter instanceof SimpleQueueSorter){
            ((SimpleQueueSorter) queueSorter).reset();
        }
        try {
            response.sendRedirect2(request.getRootPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @CheckForNull
    @Override
    public String getIconFileName() {
        return "/plugin/simple-queue/images/reset_64.png";
    }

    @CheckForNull
    @Override
    public String getDisplayName() {
        return "Reset Simple-Queue";
    }

    @CheckForNull
    @Override
    public String getUrlName() {
        return "simpleQueueReset";
    }

}
