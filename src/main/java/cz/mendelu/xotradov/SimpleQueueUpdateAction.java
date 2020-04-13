package cz.mendelu.xotradov;


import hudson.Extension;
import hudson.model.*;
import jenkins.model.Jenkins;

import java.util.logging.Logger;

@SuppressWarnings("unused")
@Extension
public class SimpleQueueUpdateAction implements RootAction {
    private final static Logger logger = Logger.getLogger(SimpleQueueUpdateAction.class.getName());
    private final Queue queue;


    public SimpleQueueUpdateAction(){
        queue = Jenkins.get().getQueue();
    }
    public SimpleQueueUpdateAction(Queue queue) {
        this.queue = queue;
    }
    public static String getMoveTypeName(){return MoveAction.MOVE_TYPE_PARAM_NAME;}
    public Queue.Item[] getItems(){
        return queue.getItems();
    }

    public boolean isFilterQueue(){
        return false;
        // TODO return podle queue
    }

    public void downClicked(int id){
        logger.info("downClicked "+String.valueOf(id));
        try {
            Queue.Item item = queue.getItem(id);
            if (item!=null){
                down(id);
            }
        }catch (Exception e){
            logger.info("Queue item not existing");
        }
    }
    public void down(int id){
        // TODO posunuti itemu
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
