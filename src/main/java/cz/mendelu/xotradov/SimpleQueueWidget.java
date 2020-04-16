package cz.mendelu.xotradov;

import hudson.Extension;
import hudson.model.Queue;
import hudson.widgets.Widget;
import jenkins.model.Jenkins;

@SuppressWarnings("unused")
@Extension(ordinal=199)
public class SimpleQueueWidget extends Widget {
    public Queue.Item[] getItems(){
        return Jenkins.get().getQueue().getItems();
    }

    public boolean isFilterQueue(){
        return false;
        // TODO return podle queue
    }
}
