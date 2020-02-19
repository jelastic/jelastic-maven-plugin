package com.jelastic;

import com.jelastic.util.PackageTypeEnum;
import com.jelastic.util.UploadResponse;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which publish artifact to Jelastic Cloud Platform
 *
 * @goal publish
 */
public class PublishMojo extends AbstractJelasticMojo {
    public void execute() throws MojoExecutionException {
        if (!PackageTypeEnum.isSupported(getPackaging())) {
            getLog().info("Skipping deploy artifact. Artifact packaging " + getPackaging() + " is not supported");
            throw new MojoExecutionException("Failed to publish artifact. Artifact packaging " + getPackaging() + " is not supported");
        }
        UploadResponse uploadResponse = upload();
        if (uploadResponse.getResult() == 0) {
            getLog().info("File UpLoad : SUCCESS");
            getLog().info("File URL : " + uploadResponse.getFile());
            getLog().info("File size :" + uploadResponse.getSize());
            getLog().info("------------------------------------------------------------------------");
        } else {
            getLog().error("File upload : FAILED");
            getLog().error("Error : " + uploadResponse.getError());
            throw new MojoExecutionException("Upload failed : " + uploadResponse.getError());
        }
    }
}
