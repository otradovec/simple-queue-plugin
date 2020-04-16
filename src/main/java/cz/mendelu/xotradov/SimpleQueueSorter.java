package cz.mendelu.xotradov;

import hudson.Extension;
import hudson.model.Queue;
import hudson.model.queue.QueueSorter;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class SimpleQueueSorter extends QueueSorter {
    private final QueueSorter originalQueueSorter;
    private final SimpleQueueComparator simpleQueueComparator;

    public SimpleQueueSorter(QueueSorter originalQueueSorter) {
        this.originalQueueSorter = originalQueueSorter;
        this.simpleQueueComparator = new SimpleQueueComparator();
    }

    @Override
    public void sortBuildableItems(List<Queue.BuildableItem> list) {
        if(this.originalQueueSorter != null) {
            this.originalQueueSorter.sortBuildableItems(list);
        }
        Collections.sort(list, simpleQueueComparator);
    }
    public SimpleQueueComparator getSimpleQueueComparator(){
        return simpleQueueComparator;
    }
}
