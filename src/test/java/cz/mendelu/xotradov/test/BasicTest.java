package cz.mendelu.xotradov.test;

import cz.mendelu.xotradov.MoveAction;
import cz.mendelu.xotradov.SimpleQueueWidget;
import hudson.model.*;
import hudson.model.queue.QueueTaskFuture;
import hudson.widgets.Widget;
import jenkins.model.Jenkins;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.SleepBuilder;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class BasicTest {
    public static Logger logger = Logger.getLogger(BasicTest.class.getName());
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    @Test
    public void widgetTest() throws Exception {
        Widget widget = jenkinsRule.jenkins.getWidgets().get(1);
        assertTrue(widget instanceof SimpleQueueWidget);
    }
    @Test
    public void oneBuildSuccesTest() throws Exception {
        FreeStyleProject projectA = createProject("projectA",1000);
        QueueTaskFuture futureA = schedule(projectA);
        while (!Queue.getInstance().getBuildableItems().isEmpty()) {
            Thread.sleep(10);
        }
        jenkinsRule.assertBuildStatusSuccess(futureA);
    }

    @Test
    public void twoItemsUpperDownTest() throws Exception {
        FreeStyleProject projectA = createProject("projectA",20000);
        QueueTaskFuture futureA = schedule(projectA); //projectA.doBuild(req,rsp,7000);
        FreeStyleProject projectB = createProject("projectB", 20000);
        QueueTaskFuture futureB = schedule(projectB);
        Queue queue = Queue.getInstance();
        while (!queue.getBuildableItems().isEmpty()){
            Thread.sleep(10);
        }
        //now can be queue filled predictably
        FreeStyleProject projectC = createProject("projectC", 20000);
        QueueTaskFuture futureC = schedule(projectC);
        FreeStyleProject projectD = createProject("projectD",20000);
        QueueTaskFuture futureD = schedule(projectD);
        while (queue.getBuildableItems().size() != 2){
            Thread.sleep(5);
        }
        assertEquals(projectD.getDisplayName(),queue.getItems()[0].task.getDisplayName());
        assertEquals(projectC.getDisplayName(),queue.getItems()[1].task.getDisplayName());
        assertTrue(jenkinsRule.jenkins.hasPermission(Jenkins.ADMINISTER));
        assertEquals(2,queue.getBuildableItems().size());
        assertEquals(2,queue.getItems().length);
        MoveAction moveAction = (MoveAction)jenkinsRule.jenkins.getActions().get(1);
        moveAction.moveDown(queue.getItems()[0],queue);
        //Waiting for maintainerThread or Snapshot update
        Thread.sleep(6000);
        assertEquals(projectC.getDisplayName(),queue.getItems()[0].task.getDisplayName());
        assertEquals(projectD.getDisplayName(),queue.getItems()[1].task.getDisplayName());
    }

    @Test
    public void twoItemsLowerUpTest() throws Exception {
        FreeStyleProject projectA = createProject("projectA",20000);
        QueueTaskFuture futureA = schedule(projectA); //projectA.doBuild(req,rsp,7000);
        FreeStyleProject projectB = createProject("projectB", 20000);
        QueueTaskFuture futureB = schedule(projectB);
        Queue queue = Queue.getInstance();
        while (!queue.getBuildableItems().isEmpty()){
            Thread.sleep(10);
        }
        //now can be queue filled predictably
        FreeStyleProject projectC = createProject("projectC", 20000);
        QueueTaskFuture futureC = schedule(projectC);
        FreeStyleProject projectD = createProject("projectD",20000);
        QueueTaskFuture futureD = schedule(projectD);
        while (queue.getBuildableItems().size() != 2){
            Thread.sleep(5);
        }
        assertEquals(projectD.getDisplayName(),queue.getItems()[0].task.getDisplayName());
        assertEquals(projectC.getDisplayName(),queue.getItems()[1].task.getDisplayName());
        assertTrue(jenkinsRule.jenkins.hasPermission(Jenkins.ADMINISTER));
        assertEquals(2,queue.getBuildableItems().size());
        assertEquals(2,queue.getItems().length);
        MoveAction moveAction = (MoveAction)jenkinsRule.jenkins.getActions().get(1);
        moveAction.moveUp(queue.getItems()[1],queue);
        //Waiting for maintainerThread or Snapshot update
        Thread.sleep(6000);
        assertEquals(projectC.getDisplayName(),queue.getItems()[0].task.getDisplayName());
        assertEquals(projectD.getDisplayName(),queue.getItems()[1].task.getDisplayName());
    }

    @Test
    public void backAndForthTest() throws Exception {
        FreeStyleProject projectA = createProject("projectA",20000);
        QueueTaskFuture futureA = schedule(projectA); //projectA.doBuild(req,rsp,7000);
        FreeStyleProject projectB = createProject("projectB", 20000);
        QueueTaskFuture futureB = schedule(projectB);
        Queue queue = Queue.getInstance();
        while (!queue.getBuildableItems().isEmpty()){
            Thread.sleep(10);
        }
        //now can be queue filled predictably
        FreeStyleProject projectC = createProject("projectC", 20000);
        QueueTaskFuture futureC = schedule(projectC);
        FreeStyleProject projectD = createProject("projectD",20000);
        QueueTaskFuture futureD = schedule(projectD);
        while (queue.getBuildableItems().size() != 2){
            Thread.sleep(5);
        }
        assertEquals(projectD.getDisplayName(),queue.getItems()[0].task.getDisplayName());
        assertEquals(projectC.getDisplayName(),queue.getItems()[1].task.getDisplayName());
        assertTrue(jenkinsRule.jenkins.hasPermission(Jenkins.ADMINISTER));
        assertEquals(2,queue.getBuildableItems().size());
        assertEquals(2,queue.getItems().length);
        MoveAction moveAction = (MoveAction)jenkinsRule.jenkins.getActions().get(1);
        moveAction.moveUp(queue.getItems()[1],queue);
        //Waiting for maintainerThread or Snapshot update
        Thread.sleep(6000);
        assertEquals(projectC.getDisplayName(),queue.getItems()[0].task.getDisplayName());
        assertEquals(projectD.getDisplayName(),queue.getItems()[1].task.getDisplayName());
        moveAction.moveDown(queue.getItems()[0],queue);
        Thread.sleep(6000);
        assertEquals(projectD.getDisplayName(),queue.getItems()[0].task.getDisplayName());
        assertEquals(projectC.getDisplayName(),queue.getItems()[1].task.getDisplayName());
    }

    private QueueTaskFuture schedule(FreeStyleProject projectA) throws Exception {
        QueueTaskFuture futureA = projectA.scheduleBuild2(0);
        if (futureA == null) {
            throw new Exception("the task could not be scheduled");
        }
        boolean enteredTheQueueA = false;
        while (!enteredTheQueueA) {
            for (Queue.BuildableItem item : Queue.getInstance().getBuildableItems()) {
                if (item.task.getDisplayName() != null && item.task.getDisplayName().equals(projectA.getDisplayName())) {
                    enteredTheQueueA = true;
                }
            }
            for (Computer computer: Jenkins.get().getComputers()){
                if (computer!=null){
                    for (Executor executor: computer.getExecutors()){
                        if(executor.getCurrentWorkUnit()!=null && executor.getCurrentWorkUnit().context.task.getDisplayName().equals(projectA.getDisplayName()))return futureA;
                    }
                }
            }
            Thread.sleep(5);
        }
        return futureA;
    }

    private FreeStyleProject createProject(@Nonnull String projectName, long time) throws IOException {
        FreeStyleProject projectA = jenkinsRule.createFreeStyleProject(projectName);
        projectA.getBuildersList().add(new SleepBuilder(time));
        projectA.setDisplayName(projectName);
        return projectA;
    }
}
