package cz.mendelu.xotradov;

import hudson.Plugin;
import hudson.model.Queue;
import hudson.widgets.Widget;
import jenkins.model.Jenkins;

import java.util.logging.Logger;


/**
 * @author Jaroslav Otradovec
 */
public class SimpleQueuePlugin extends Plugin {
    private final static Logger logger = Logger.getLogger(SimpleQueuePlugin.class.getName());

    @Override
    public void postInitialize() throws Exception {
        super.postInitialize();
        if (Jenkins.get().getWidgets().isEmpty()){
            logger.info("Empty widget list!");
        }else {
            Widget queueWidget = Jenkins.get().getWidgets().get(0);
            Jenkins.get().getWidgets().remove(queueWidget);
            logger.info("Removing BuildQueueWidget");
        }
    }

}
