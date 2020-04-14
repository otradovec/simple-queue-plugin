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

/**
 * @author Jaroslav Otradovec
 */
@SuppressWarnings("unused")
@Extension
public class MoveAction implements RootAction {
    private static Logger logger = Logger.getLogger(MoveAction.class.getName());
    public static final String MOVE_TYPE_PARAM_NAME= "moveType";
    public static final String ITEM_ID_PARAM_NAME="itemId";
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
                try {
                    Queue.Item item = queue.getItem(Long.parseLong(request.getParameter(ITEM_ID_PARAM_NAME)));
                    MoveType moveType = MoveType.valueOf(request.getParameter(MOVE_TYPE_PARAM_NAME));
                    if (item != null){
                        switch (moveType){
                            case UP:
                                moveUp(item,queue);
                                break;
                            case DOWN:
                                moveDown(item,queue);
                                break;
                        }
                    }
                }catch (NumberFormatException nfe){
                    //wrong item id String
                    logger.info("Wrong item id");
                }catch (IllegalArgumentException iae){
                    //wrong moveType String
                    logger.info("Wrong move type");
                }
            }
        }
        try {
            response.sendRedirect2(request.getRootPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void moveDown(Queue.Item item, Queue queue) {
        logger.info(String.valueOf(queue.countBuildableItems()));

    }

    private void moveUp(Queue.Item item, Queue queue) {
        logger.info(queue.getItems().toString());
    }

}
