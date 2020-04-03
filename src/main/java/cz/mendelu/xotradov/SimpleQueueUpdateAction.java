package cz.mendelu.xotradov;


import hudson.Extension;
import hudson.model.*;

@Extension
public class SimpleQueueUpdateAction implements RootAction {
    /*public final Queue queue;

    public SimpleQueueUpdateAction(Queue queue) {
        this.queue = queue;
    }*/

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public String getUrlName() {
        return "updateQueue";
    }


}
