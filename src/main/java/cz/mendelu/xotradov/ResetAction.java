package cz.mendelu.xotradov;

import hudson.Extension;
import hudson.model.RootAction;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.annotation.CheckForNull;
import java.io.IOException;

@SuppressWarnings("unused")
@Extension
public class ResetAction implements RootAction {
    public void doReset(final StaplerRequest request, final StaplerResponse response) {
        //todo permision check
        SimpleQueueComparator.resetDesires();
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
