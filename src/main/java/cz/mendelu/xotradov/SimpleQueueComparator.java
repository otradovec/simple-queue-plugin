package cz.mendelu.xotradov;

import hudson.model.Queue;

import java.util.*;

public class SimpleQueueComparator implements Comparator<Queue.BuildableItem> {

    private static Hashtable<Long, List<Long>> moveDesires = new Hashtable<>();

    /**
     * @return -1 when first is more important, 1 when second is more important, default 0
     */
    @Override
    public int compare(Queue.BuildableItem buildableItem1, Queue.BuildableItem buildableItem2) {
        if (isFirstItemOverSecond(buildableItem1.getId(),buildableItem2.getId())){
            return -1;
        }else if (isFirstItemOverSecond(buildableItem2.getId(),buildableItem1.getId())){
            return 1;
        }else {
            return 0;
        }
    }
    public boolean isFirstItemOverSecond(long id0, long id1) {
        if (moveDesires.get(id0)!=null){
            return moveDesires.get(id0).contains(id1);
        }else {
            return false;
        }
    }

    /**
     * Add desire of order relationship between two items. If is present vice versa relation, it is deleted.
     * @param longA Id of desired more important item
     * @param longB Id of less important Queue.Item
     */
    public void addDesire( long longA, long longB){
        List<Long> bList;
        if (!moveDesires.containsKey(longA)){
            bList = new ArrayList<>();
            moveDesires.put(longA,bList);
        }else {
            bList = moveDesires.get(longA);
        }
        if(bList!=null){
            if(bList.isEmpty()){
                bList.add(longB);
            }else {
                if (!bList.contains(longB)){
                    bList.add(longB);  //Should it be Long instead of long?
                }
            }
            //Cleaning of previous order
            List<Long> aList = moveDesires.get(longB);
            if (aList != null && aList.contains(longA)) {
                aList.remove(longA);
                if (aList.isEmpty()) {
                    moveDesires.remove(longB);
                }
            }
        }
    }
    //TODO remove when item completed
    public static void removeDesireOfKey(long id){
        moveDesires.remove(id);
    }
    //TODO get rid of static
    public static void resetDesires(){
        moveDesires.clear();
    }

}
