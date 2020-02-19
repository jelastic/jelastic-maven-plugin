package com.jelastic;

import com.jelastic.api.environment.response.NodeSSHResponses;
import com.jelastic.util.PackageTypeEnum;
import com.jelastic.util.UploadResponse;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which deploy artifact to Jelastic Cloud Platform
 *
 * @goal deploy
 * @phase install
 */
public class DeployMojo extends AbstractJelasticMojo {
    public void execute() throws MojoExecutionException {
        if (!PackageTypeEnum.isSupported(getPackaging())) {
            getLog().info("Skipping deploy artifact. Artifact packaging " + getPackaging() + " is not supported");
            return;
        }
        getLog().info("File Upload: Starting");
        UploadResponse uploadResponse = upload();
        if (uploadResponse.getResult() == 0) {
            if (uploadResponse.getResult() == 0) {
                getLog().info("File Upload : SUCCESS");
                getLog().info("File URL : " + uploadResponse.getFile());
                getLog().info("File size : " + uploadResponse.getSize());
                getLog().info("------------------------------------------------------------------------");

                if (isUploadOnly()) {
                    return;
                }
                NodeSSHResponses deploy = deploy(uploadResponse.getName(), uploadResponse.getFile());
                if (deploy.getResult() == 0) {
                    getLog().info("Deploy file : SUCCESS");
                    getLog().info(deploy.getRaw());
                } else {
                    getLog().error("Deploy : FAILED");
                    getLog().error("Error : " + deploy.getError());
                }
            }
        } else {
            getLog().error("File upload : FAILED");
            getLog().error("Error : " + uploadResponse.getError());
            throw new MojoExecutionException("Upload failed : " + uploadResponse.getError());
        }
    }
}
