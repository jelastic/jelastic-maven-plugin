package com.jelastic;

import com.google.gson.reflect.TypeToken;
import com.jelastic.api.environment.Control;
import com.jelastic.api.environment.response.NodeSSHResponses;
import com.jelastic.exception.JelasticApiException;
import com.jelastic.model.GetDomainsResponse;
import com.jelastic.model.GetNodeGroupResponse;
import com.jelastic.model.NodeGroup;
import com.jelastic.model.NodeGroupName;
import com.jelastic.util.JelasticProperties;
import com.jelastic.util.UploadResponse;
import com.lindar.wellrested.WellRestedRequest;
import com.lindar.wellrested.vo.WellRestedResponse;
import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.jelastic.util.JelasticProperties.FID_PARAM;
import static com.jelastic.util.JelasticProperties.FID_VALUE;
import static com.jelastic.util.JelasticProperties.FILE_PARAM;
import static com.jelastic.util.JelasticProperties.GET_DOMAINS_PATH;
import static com.jelastic.util.JelasticProperties.GET_NODEGROUP_PATH;
import static com.jelastic.util.JelasticProperties.SESSION_PARAM;
import static com.jelastic.util.JelasticProperties.UPLOAD_URL;

public abstract class AbstractJelasticMojo extends AbstractMojo {
    private static Properties properties = new Properties();

    /**
     * The package output file.
     *
     * @parameter default-value = "${project.build.directory}/${project.build.finalName}.${project.packaging}"
     * @required
     * @readonly
     */
    private File artifactFile;

    /**
     * The packaging of the Maven project that this goal operates upon.
     *
     * @parameter expression = "${project.packaging}"
     * @required
     * @readonly
     */
    private @Getter String packaging;

    /**
     * Password Properties.
     *
     * @parameter
     */
    private String password;

    /**
     * Context Properties.
     *
     * @parameter default-value="ROOT"
     */
    private String context;


    /**
     * Context Properties.
     *
     * @parameter default-value="api.jelastic.com"
     */
    private String apiHost;


    /**
     * Environment name Properties.
     *
     * @parameter
     */
    private String environment;

    /**
     * Node group name Properties.
     *
     * @parameter
     */
    private String nodeGroup;

    /**
     * Node group display name (i.e. what is displayed in the Jelastic UI) Properties.
     *
     * @parameter
     */
    private String nodeGroupDisplayName;

    /**
     * Delay of sequential deploy
     *
     * @parameter
     */
    private String delay;

    /**
     * Artifact for deploy.
     *
     * @parameter
     */
    private String artifact;

    /**
     * Location of the file.
     *
     * @parameter expression="${project.build.directory}" default-value="${project.build.directory}"
     * @required
     */
    private File outputDirectory;

    private String getApiJelastic() {
        String jelasticHoster = System.getProperty(JelasticProperties.JELASTIC_HOSTER);
        if (StringUtils.isNotBlank(jelasticHoster)) {
            apiHost = jelasticHoster;
        }
        return apiHost;
    }

    private String getPassword() {
        return getProperty(JelasticProperties.JELASTIC_PASSWORD, password);
    }

    private String getContext() {
        return getProperty(JelasticProperties.CONTEXT, context);
    }

    private String getEnvironment() {
        return getProperty(JelasticProperties.ENVIRONMENT, environment);
    }

    private String getNodeGroup() {
        return getProperty(JelasticProperties.NODE_GROUP, nodeGroup);
    }

    private String getNodeGroupDisplayName() {
        String displayNameFromProperties = getProperty(JelasticProperties.NODE_GROUP_DISPLAY_NAME, nodeGroupDisplayName);
        if (StringUtils.isBlank(displayNameFromProperties)) {
            return displayNameFromProperties;
        }

        List<NodeGroup> nodeGroups = this.listNodeGroups();
        for (NodeGroup ng : nodeGroups) {
            // find the node group with the given display name and returns its name
            if (ng.getDisplayName().equals(displayNameFromProperties)) {
                return ng.getName();
            }
        }
        throw new IllegalArgumentException(String.format("[%s] is not a valid node group name", displayNameFromProperties));
    }

    public Integer getDelay() {
        String delayString = getProperty(JelasticProperties.DELAY, this.delay);
        if (delayString == null) {
            return null;
        }
        return Integer.valueOf(delayString);
    }

    private String getProperty(String name, String defaultValue) {
        String property = properties.getProperty(name);
        if (isExternalParameterPassed() && StringUtils.isNotBlank(property)) {
            return properties.getProperty(property);
        }
        return defaultValue;
    }

    private String getPreDeployHookFilePath() {
        return System.getProperty(JelasticProperties.JELASTIC_PREDEPLOY_HOOK);
    }

    private String getPostDeployHookFilePath() {
        return System.getProperty(JelasticProperties.JELASTIC_POSTDEPLOY_HOOK);
    }

    private String getPreDeployHookContent() {
        String preDeployHookFilePath = getPreDeployHookFilePath();
        String preDeployHookContent = null;

        if (preDeployHookFilePath != null && preDeployHookFilePath.length() > 0) {
            try {
                preDeployHookContent = readFileContent(preDeployHookFilePath);
            } catch (Exception ex) {
                getLog().info("Can't read [preDeployHook] from [" + preDeployHookFilePath + "]:" + ex.getMessage());
            }
        }

        return preDeployHookContent;
    }

    private String getPostDeployHookContent() {
        String postDeployHookFilePath = getPostDeployHookFilePath();
        String postDeployHookContent = null;

        if (postDeployHookFilePath != null && postDeployHookFilePath.length() > 0) {
            try {
                postDeployHookContent = readFileContent(postDeployHookFilePath);
            } catch (Exception ex) {
                getLog().info("Can't read [postDeployHook] from [" + postDeployHookFilePath + "]:" + ex.getMessage());
            }
        }

        return postDeployHookContent;
    }

    private String readFileContent(String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null) {
            sb.append(line).append("\n");
            line = buf.readLine();
        }

        return sb.toString();
    }

    public boolean isExternalParameterPassed() {
        if (System.getProperty("jelastic-properties") != null && System.getProperty("jelastic-properties").length() > 0) {
            try {
                properties.load(new FileInputStream(System.getProperty("jelastic-properties")));
            } catch (IOException e) {
                getLog().error(e.getMessage(), e);
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public boolean isUploadOnly() {
        String uploadOnly = System.getProperty("jelastic-upload-only");
        return uploadOnly != null && (uploadOnly.equalsIgnoreCase("1") || uploadOnly.equalsIgnoreCase("true"));
    }

    public UploadResponse upload() throws MojoExecutionException {
        HttpEntity httpEntity = MultipartEntityBuilder.create()
                                                      .addPart(FID_PARAM, new StringBody(FID_VALUE, ContentType.TEXT_PLAIN))
                                                      .addPart(SESSION_PARAM, new StringBody(getPassword(), ContentType.TEXT_PLAIN))
                                                      .addPart(FILE_PARAM, new FileBody(artifactFile)).build();
        WellRestedResponse wellRestedResponse = WellRestedRequest.builder().timeout(30 * 1000).url(String.format(UPLOAD_URL, getApiJelastic())).build().post().httpEntity(httpEntity).submit();
        if (wellRestedResponse.isValid()) {
            return wellRestedResponse.fromJson()
                                     .castTo(new TypeToken<UploadResponse>() {
                                     });
        }
        throw new MojoExecutionException(wellRestedResponse.getServerResponse());
    }

    public NodeSSHResponses deploy(String fileName, String fileUrl) {
        String url = getApiJelastic();
        Control control = new Control(null, getPassword(), "https://" + url + "/1.0/" + Control.SERVICE_PATH);
        Map<Object, Object> params = new HashMap<>();
        params.put("envName", getEnvironment());
        params.put("fileUrl", fileUrl);
        params.put("fileName", fileName);
        params.put("context", getContext());

        String deployNodeGroup = getNodeGroup();
        if (StringUtils.isBlank(deployNodeGroup)) {
            deployNodeGroup = getNodeGroupDisplayName();
        }
        params.put("nodeGroup", deployNodeGroup);

        String preDeployHookContent = getPreDeployHookContent();
        if (preDeployHookContent != null) {
            params.put("preDeployHook", preDeployHookContent);
        }

        String postDeployHookContent = getPostDeployHookContent();
        if (postDeployHookContent != null) {
            params.put("postDeployHook", postDeployHookContent);
        }

        Integer delay = getDelay();
        if (delay != null) {
            params.put("delay", delay);
            params.put("isSequential", true);
        }
        return control.deployApp(params);
    }

    private List<String> getNodeGroups() {
        final String url = String.format("https://%s%s?envName=%s&session=%s",
                                         getApiJelastic(),
                                         GET_DOMAINS_PATH,
                                         getEnvironment(),
                                         getPassword());

        final WellRestedResponse response = WellRestedRequest.builder()
                                                             .timeout(30 * 1000)
                                                             .url(url)
                                                             .build()
                                                             .get()
                                                             .submit();

        if (!response.isValid()) {
            throw new JelasticApiException(String.format("Could not get node groups from environment: %s", response.getServerResponse()));
        }

        final GetDomainsResponse getDomainsResponse = response.fromJson().castTo(GetDomainsResponse.class);
        return getDomainsResponse.getNodeGroups().stream().map(NodeGroupName::getNodeGroup).collect(Collectors.toList());
    }

    private NodeGroup getNodeGroup(String nodeGroupName) {
        final String url = String.format("https://%s%s?envName=%s&session=%s&nodeGroup=%s",
                                         getApiJelastic(),
                                         GET_NODEGROUP_PATH,
                                         getEnvironment(),
                                         getPassword(),
                                         nodeGroupName);

        final WellRestedResponse response = WellRestedRequest.builder()
                                                             .timeout(30 * 1000)
                                                             .url(url)
                                                             .build()
                                                             .get()
                                                             .submit();

        if (!response.isValid()) {
            throw new JelasticApiException(String.format("Could not get node groups by name: %s", response.getServerResponse()));
        }

        final GetNodeGroupResponse getNodeGroupResponse = response.fromJson().castTo(GetNodeGroupResponse.class);
        return getNodeGroupResponse.getNodeGroup();
    }

    private List<NodeGroup> listNodeGroups() {
        try {
            List<String> nodeGroupNames = this.getNodeGroups();
            return nodeGroupNames.stream().map(this::getNodeGroup).collect(Collectors.toList());
        } catch (final Exception ex) {
            getLog().error("Could not list node groups", ex);
        }
        return new ArrayList<>(0);
    }
}
