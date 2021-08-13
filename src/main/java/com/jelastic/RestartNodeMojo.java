package com.jelastic;

import com.jelastic.api.environment.response.NodeSSHResponse;
import com.jelastic.util.PackageTypeEnum;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which restarts a node by its ID
 *
 * @goal restartNode
 */
public class RestartNodeMojo extends AbstractJelasticMojo {

    /**
     * The ID of the node to restart
     *
     * @parameter
     * @required
     */
    private int nodeId;

    public void execute() throws MojoExecutionException {
        if (!PackageTypeEnum.isSupported(getPackaging())) {
            getLog().info("Skipping deploy artifact. Artifact packaging " + getPackaging() + " is not supported");
            return;
        }
        getLog().info("Node restart (Node ID " + nodeId + ") : Starting");

        NodeSSHResponse restart = restartNode(nodeId);
        if (restart.getResult() == 0) {
            getLog().info("Node restart (Node ID " + nodeId + ") : SUCCESS");
            getLog().info(restart.getRaw());
        } else {
            getLog().error("Node restart : FAILED");
            getLog().error("Error : " + restart.getError());
        }
    }

}
