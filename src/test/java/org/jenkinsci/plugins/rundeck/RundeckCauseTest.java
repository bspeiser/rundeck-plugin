package org.jenkinsci.plugins.rundeck;

import hudson.EnvVars;
import org.junit.Assert;
import org.jvnet.hudson.test.HudsonTestCase;
import org.rundeck.api.domain.RundeckExecution;

import java.util.Date;

/**
 * Tests for {@link RundeckCause}
 *
 * @author Roshan Revankar
 */
public class RundeckCauseTest extends HudsonTestCase {

    public void testBuildEnvVarsArgstring(){
        RundeckExecution execution = new RundeckExecution();
        execution.setId(1L);
        execution.setUrl("http://localhost:4440/execution/follow/1");
        execution.setStatus( RundeckExecution.ExecutionStatus.SUCCEEDED);
        execution.setStartedAt(new Date(1310159014640L));
        execution.setArgstring("-simpleOption simpleValue -optionWithOneHyphen value-value -optionWithTwoHyphens value-value-value -optionWithTrailingHyphen value-value-");

        RundeckCause.RundeckExecutionEnvironmentContributingAction rundeckExecutionEnvironmentContributingAction
                = new RundeckCause.RundeckExecutionEnvironmentContributingAction(execution);

        EnvVars envVars = new EnvVars();
        rundeckExecutionEnvironmentContributingAction.buildEnvVars(null,envVars);

        Assert.assertEquals("simpleValue", envVars.expand("$RDECK_EXEC_ARG_simpleOption"));
        Assert.assertEquals("value-value", envVars.expand("$RDECK_EXEC_ARG_optionWithOneHyphen"));
        Assert.assertEquals("value-value-value", envVars.expand("$RDECK_EXEC_ARG_optionWithTwoHyphens"));
        Assert.assertEquals("value-value-", envVars.expand("$RDECK_EXEC_ARG_optionWithTrailingHyphen"));

    }
}
