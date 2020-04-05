package cz.mendelu.xotradov;


import hudson.Extension;
import hudson.model.*;
import jenkins.model.Jenkins;

import java.util.logging.Logger;

@SuppressWarnings("unused")
@Extension
public class SimpleQueueUpdateAction implements RootAction {
    private final static Logger logger = Logger.getLogger(SimpleQueueUpdateAction.class.getName());
    public final Queue queue;

    public SimpleQueueUpdateAction(){
        queue = Jenkins.get().getQueue();
    }
    public SimpleQueueUpdateAction(Queue queue) {
        this.queue = queue;
    }
    public Queue.Item[] getItems(){
        return queue.getItems();
    }

    public boolean isFilterQueue(){
        return false;
        // TODO return podle queue
    }

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
