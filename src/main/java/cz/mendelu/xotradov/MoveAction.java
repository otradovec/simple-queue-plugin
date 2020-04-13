package cz.mendelu.xotradov;

import hudson.Extension;
import hudson.model.Queue;
import hudson.model.RootAction;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.annotation.CheckForNull;
import java.io.IOException;
import java.util.logging.Logger;

@SuppressWarnings("unused")
@Extension
public class MoveAction implements RootAction {
    public static final String MOVE_TYPE_PARAM_NAME= "moveType";
    @CheckForNull
    @Override
    public String getIconFileName() {
            return null;
    }

    @CheckForNull
    @Override
    public String getDisplayName() {
        return null;
    }

    @CheckForNull
    @Override
    public String getUrlName() {
        return "simpleMove";
    }
    public void doMove(final StaplerRequest request, final StaplerResponse response) {
        Jenkins j;
        if ((j = Jenkins.getInstanceOrNull()) != null) {
            Queue queue = j.getQueue();
            if (queue != null) {
                queue.getItem(Integer.valueOf(request.getParameter(MOVE_TYPE_PARAM_NAME)));
            }
        }
        try {
            response.sendRedirect2(request.getRootPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
