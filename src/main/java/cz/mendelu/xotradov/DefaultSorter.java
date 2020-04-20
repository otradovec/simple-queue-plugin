package cz.mendelu.xotradov;

import hudson.model.Queue;
import hudson.model.queue.AbstractQueueSorterImpl;
import hudson.model.queue.QueueSorter;

import java.util.List;

public class DefaultSorter extends AbstractQueueSorterImpl {
    @Override
    public void sortBuildableItems(List<Queue.BuildableItem> list) {
        list.sort(this); // sort is ascending order
    }
}
