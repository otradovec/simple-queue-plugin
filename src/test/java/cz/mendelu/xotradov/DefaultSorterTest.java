package cz.mendelu.xotradov;

import cz.mendelu.xotradov.test.TestHelper;
import hudson.model.FreeStyleProject;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import static org.junit.Assert.*;

public class DefaultSorterTest {

    @Rule
    public JenkinsRule r = new JenkinsRule();
    private TestHelper helper = new TestHelper(r);

    @Test
    public void sortBuildableItems() throws Exception {
        helper.fillQueueFor(20000);
        FreeStyleProject C = helper.createAndSchedule("C",20000);
        FreeStyleProject D = helper.createAndSchedule("D",20000);
        FreeStyleProject E = helper.createAndSchedule("E",20000);
        assertEquals(E.getDisplayName(),r.jenkins.getQueue().getItems()[0].task.getDisplayName());
        assertEquals(D.getDisplayName(),r.jenkins.getQueue().getItems()[1].task.getDisplayName());
        assertEquals(C.getDisplayName(),r.jenkins.getQueue().getItems()[2].task.getDisplayName());
        assertNotEquals(E.getDisplayName(),r.jenkins.getQueue().getItems()[2].task.getDisplayName());
    }
}