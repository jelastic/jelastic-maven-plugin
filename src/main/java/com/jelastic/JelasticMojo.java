package com.jelastic;

/**
 * User: Igor.Yova@gmail.com
 * Date: 6/8/11
 * Time: 10:30 AM
 * <p>
 * http://app.hivext.com/1.0/users/authentication/rest/signin
 * http://api.hivext.com/1.0/storage/uploader/rest/upload
 * http://app.hivext.com/1.0/data/base/rest/createobject
 * http://live.jelastic.com/deploy/DeployArchive
 * <p>
 * http://app.hivext.com/1.0/users/authentication/rest/signin
 * http://api.hivext.com/1.0/storage/uploader/rest/upload
 * http://app.hivext.com/1.0/data/base/rest/createobject
 * http://live.jelastic.com/deploy/DeployArchive
 * <p>
 * http://app.hivext.com/1.0/users/authentication/rest/signin
 * http://api.hivext.com/1.0/storage/uploader/rest/upload
 * http://app.hivext.com/1.0/data/base/rest/createobject
 * http://live.jelastic.com/deploy/DeployArchive
 * <p>
 * http://app.hivext.com/1.0/users/authentication/rest/signin
 * http://api.hivext.com/1.0/storage/uploader/rest/upload
 * http://app.hivext.com/1.0/data/base/rest/createobject
 * http://live.jelastic.com/deploy/DeployArchive
 * <p>
 * http://app.hivext.com/1.0/users/authentication/rest/signin
 * http://api.hivext.com/1.0/storage/uploader/rest/upload
 * http://app.hivext.com/1.0/data/base/rest/createobject
 * http://live.jelastic.com/deploy/DeployArchive
 * <p>
 * http://app.hivext.com/1.0/users/authentication/rest/signin
 * http://api.hivext.com/1.0/storage/uploader/rest/upload
 * http://app.hivext.com/1.0/data/base/rest/createobject
 * http://live.jelastic.com/deploy/DeployArchive
 * <p>
 * http://app.hivext.com/1.0/users/authentication/rest/signin
 * http://api.hivext.com/1.0/storage/uploader/rest/upload
 * http://app.hivext.com/1.0/data/base/rest/createobject
 * http://live.jelastic.com/deploy/DeployArchive
 * <p>
 * http://app.hivext.com/1.0/users/authentication/rest/signin
 * http://api.hivext.com/1.0/storage/uploader/rest/upload
 * http://app.hivext.com/1.0/data/base/rest/createobject
 * http://live.jelastic.com/deploy/DeployArchive
 * <p>
 * http://app.hivext.com/1.0/users/authentication/rest/signin
 * http://api.hivext.com/1.0/storage/uploader/rest/upload
 * http://app.hivext.com/1.0/data/base/rest/createobject
 * http://live.jelastic.com/deploy/DeployArchive
 * <p>
 * http://app.hivext.com/1.0/users/authentication/rest/signin
 * http://api.hivext.com/1.0/storage/uploader/rest/upload
 * http://app.hivext.com/1.0/data/base/rest/createobject
 * http://live.jelastic.com/deploy/DeployArchive
 */


/**
 *        http://app.hivext.com/1.0/users/authentication/rest/signin
 *        http://api.hivext.com/1.0/storage/uploader/rest/upload
 *        http://app.hivext.com/1.0/data/base/rest/createobject
 *        http://live.jelastic.com/deploy/DeployArchive
 */

import com.jelastic.model.*;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Proxy;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @threadSafe
 */
public abstract class JelasticMojo extends AbstractMojo {
    private final static String WAR_TYPE = "war";
    private final static String EAR_TYPE = "ear";
    private final static String JAR_TYPE = "jar";

    private final static String HTTP_PROTOCOL = "http";
    private final static String HTTPS_PROTOCOL = "https";

    private final static String SCHEMA = HTTPS_PROTOCOL;
    private final static String VERSION = "1.0";
    private final static String URL_AUTHENTICATION = "/" + VERSION + "/users/authentication/rest/signin";
    private final static String URL_UPLOADER = "/" + VERSION + "/storage/uploader/rest/upload";
    private final static String URL_CREATE_OBJECT = "/deploy/createobject";
    private final static String URL_DEPLOY = "/deploy/DeployArchive";
    private final static String URL_LOG_OUT = "/users/authentication/rest/signout";
    private final static String URL_GET_ARCHIVES = "/GetArchives";
    private final static String URL_DELETE_ARCHIVE = "/DeleteArchive";
    private final static int SAME_FILES_LIMIT = 5;
    private final static String COMMENT_PREFIX = "Uploaded by Jelastic Maven plugin";
    //Properties
    private final static String JELASTIC_PREDEPLOY_HOOK_PROPERTY = "jelastic-predeploy-hook";
    private final static String JELASTIC_POSTDEPLOY_HOOK_PROPERTY = "jelastic-postdeploy-hook";
    private final static String NODE_GROUP_PROPERTY = "nodegroup";
    private final static String ENVIRONMENT_PROPERTY = "environment";
    private final static String CONTEXT_PROPERTY = "context";
    private final static String JELASTIC_EMAIL_PROPERTY = "jelastic-email";
    private final static String JELASTIC_PASSWORD_PROPERTY = "jelastic-password";
    private final static String JELASTIC_HOSTER_PROPERTY = "jelastic-hoster";
    private final static String JELASTIC_ACTION_KEY = "action-key";
    private final static String JELASTIC_DEPLOYMENT_ARTIFACT_NAME = "jelastic.deployment.artifactName";
    private final static String JELASTIC_ARTIFACT_NAME = "jelastic-artifact";
    private final static String JELASTIC_COMMENT_PROPERTY = "jelastic-comment";
    private final static String JELASTIC_HEADERS_PROPERTY = "jelastic-headers";
    private final static String JELASTIC_SESSION_PROPERTY = "jelastic-session";
    private final static String JELASTIC_TOKEN_PROPERTY = "jelastic-apitoken";
    //Env. vars
    private final static String MAVEN_DEPLOY_ARTIFACT_ENV = "MAVEN_DEPLOY_ARTIFACT";
    public static final String BATCH_MODE = "batch.mode";
    private static ObjectMapper mapper = new ObjectMapper();
    private static Properties properties = new Properties();
    /**
     * Used to look up Artifacts in the remote repository.
     *
     * @parameter expression= "${component.org.apache.maven.artifact.resolver.ArtifactResolver}"
     * @required
     * @readonly
     */
    protected ArtifactResolver artifactResolver;
    private int port = -1;
    private long totalSize;
    private int numSt;
    private CookieStore cookieStore = null;
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
    private String packaging;

    /**
     * The maven project.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */

    private MavenProject project;

    /**
     * The Maven session.
     *
     * @parameter expression="${session}"
     * @readonly
     * @required
     */
    private MavenSession mavenSession;

    /**
     * Headers Properties.
     *
     * @parameter
     */
    private Map<String, String> headers;

    /**
     * Email Properties.
     *
     * @parameter
     */
    private String email;

    /**
     * Comment Properties.
     *
     * @parameter
     */
    private String comment;


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
    private String api_hoster;


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
     * Api token Properties.
     *
     * @parameter
     */
    private String apiToken;

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

    public static DefaultHttpClient wrapClient(DefaultHttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getUrlGetArchives() {
        return URL_GET_ARCHIVES;
    }

    public static String getUrlDeleteArchive() {
        return URL_DELETE_ARCHIVE;
    }

    boolean isWar() {
        if (WAR_TYPE.equals(packaging)) {
            return true;
        } else if (EAR_TYPE.equals(packaging)) {
            return true;
        } else if (JAR_TYPE.equals(packaging)) {
            return true;
        }

        return false;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    private String getShema() {
        return SCHEMA;
    }

    private String getApiJelastic() {
        if (System.getProperty(JELASTIC_HOSTER_PROPERTY) != null && System.getProperty(JELASTIC_HOSTER_PROPERTY).length() > 0) {
            api_hoster = System.getProperty(JELASTIC_HOSTER_PROPERTY);
        }

        return api_hoster;
    }

    private int getPort() {
        return port;
    }

    private CookieStore getCookieStore() {
        return cookieStore;
    }

    private String getUrlAuthentication() {
        return URL_AUTHENTICATION;
    }

    private String getUrlUploader() {
        return URL_UPLOADER;
    }

    private String getUrlCreateObject() {
        return URL_CREATE_OBJECT;
    }

    private String getUrlDeploy() {
        return URL_DEPLOY;
    }

    private String getUrlLogOut() {
        return URL_LOG_OUT;
    }

    private String getEmail() {
        return getProperty(JELASTIC_EMAIL_PROPERTY, email);
    }

    private String getPassword() {
        return getProperty(JELASTIC_PASSWORD_PROPERTY, password);
    }

    private String getContext() {
        return getProperty(CONTEXT_PROPERTY, context);
    }

    private String getEnvironment() {
        return getProperty(ENVIRONMENT_PROPERTY, environment);
    }

    private String getNodeGroup() {
        return getProperty(NODE_GROUP_PROPERTY, nodeGroup);
    }

    private String getApiToken() {
        String apiTokenFromProps = System.getProperty(JELASTIC_TOKEN_PROPERTY);
        if (apiTokenFromProps != null && apiTokenFromProps.length() > 0) {
            apiToken = apiTokenFromProps;
        }

        return apiToken;
    }

    private String getProperty(String name, String defaultValue) {
        if (isExternalParameterPassed()) {
            if (properties.getProperty(name) != null && properties.getProperty(name).length() > 0) {
                return properties.getProperty(name);
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    private String getPreDeployHookFilePath() {
        return System.getProperty(JELASTIC_PREDEPLOY_HOOK_PROPERTY);
    }

    private String getPostDeployHookFilePath() {
        return System.getProperty(JELASTIC_POSTDEPLOY_HOOK_PROPERTY);
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

    private String getActionKey() {
        return System.getProperty(JELASTIC_ACTION_KEY);
    }

    /*private String getCustomArtifactNameFromPomProps() {
        String propNames = "";
        Enumeration<?> propEnum = project.getProperties().propertyNames();
        while (propEnum.hasMoreElements()) {
            propNames += ", " + propEnum.nextElement().toString();
        }

        getLog().info("***** prop names: " + propNames);

        String artifactName = project.getProperties().getProperty(JELASTIC_DEPLOYMENT_ARTIFACT_NAME);
        getLog().info("***** properties size: " + project.getProperties().size());
        getLog().info("***** " + JELASTIC_DEPLOYMENT_ARTIFACT_NAME + ": " + artifactName);

        return artifactName;
    }*/

    private String getCustomArtifactName() {
        String artifactName = getCustomArtifactNameFromProps();
        if (artifactName == null || artifactName.length() == 0) {
            artifactName = artifact;
            if (artifactName == null || artifactName.length() == 0) {
                return getCustomArtifactNameFromEnvVar();
            }
        }

        return artifactName;
    }

    private String getCustomArtifactNameFromProps() {
        return System.getProperty(JELASTIC_ARTIFACT_NAME);
    }

    private String getCustomArtifactNameFromEnvVar() {
        //TODO: MSH: Переделать на использование другой проперти, добавит отдельную для имени проекта
        String comment = System.getProperty(JELASTIC_COMMENT_PROPERTY);
        getLog().debug("***** comment: " + comment);
        if (comment == null || comment.length() == 0) {
            return null;
        }

        //Get value for MAVEN_DEPLOY_ARTIFACT_project_name
        String projectName = comment.replaceAll(" ", "_").replaceAll("-", "_");
        String envVar = MAVEN_DEPLOY_ARTIFACT_ENV + "_" + projectName;
        String value = System.getenv(envVar);
        getLog().debug(envVar + "=" + value);

        if (value == null || value.length() == 0) {
            //Get value for MAVEN_DEPLOY_ARTIFACT
            value = System.getenv(MAVEN_DEPLOY_ARTIFACT_ENV);
            getLog().debug(MAVEN_DEPLOY_ARTIFACT_ENV + "=" + value);
        }

        return value;
    }

    Authentication authentication() throws MojoExecutionException {
        Authentication authentication = new Authentication();
        String jelasticHeaders = System.getProperty(JELASTIC_HEADERS_PROPERTY);

        getLog().debug(JELASTIC_HEADERS_PROPERTY + "=" + jelasticHeaders);

        if (jelasticHeaders != null && jelasticHeaders.length() > 0) {
            try {
                headers = mapper.readValue(URLDecoder.decode(jelasticHeaders, "UTF8"), Map.class);
                getLog().debug("headers=" + headers);
            } catch (IOException e) {
                getLog().error(e.getMessage(), e);
            }
        }

        String apiToken = getApiToken();
        if (System.getProperty(JELASTIC_SESSION_PROPERTY) != null && System.getProperty(JELASTIC_SESSION_PROPERTY).length() > 0) {
            getLog().debug("auth by " + JELASTIC_SESSION_PROPERTY);
            authentication.setSession(System.getProperty(JELASTIC_SESSION_PROPERTY));
            authentication.setResult(0);
        } else if (apiToken != null && apiToken.length() > 0) {
            getLog().debug("auth by apitoken");
            authentication.setSession(apiToken);
            authentication.setResult(0);
        } else {
            getLog().debug("auth by email/password");

            Proxy mavenProxy = getMavenProxy();
            UsernamePasswordCredentials usernamePasswordCredentials = getProxyCredential(mavenProxy);
            HttpHost http_proxy = createHttpProxyProxy(mavenProxy);

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
                httpclient.getParams().setParameter("http.protocol.single-cookie-header", Boolean.TRUE);

                httpclient = wrapClient(httpclient);
                if (http_proxy != null) {
                    httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, http_proxy);

                    if (usernamePasswordCredentials != null) {
                        httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), usernamePasswordCredentials);
                    }
                }

                httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, http_proxy);

                List<NameValuePair> qparams = new ArrayList<NameValuePair>();
                qparams.add(new BasicNameValuePair("login", getEmail()));
                qparams.add(new BasicNameValuePair("password", getPassword()));

                URI uri = URIUtils.createURI(getShema(), getApiJelastic(), getPort(), getUrlAuthentication(), null, null);
                getLog().debug(uri.toString());

                HttpPost httpPost = new HttpPost(uri);
                httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String responseBody = httpclient.execute(httpPost, responseHandler);
                cookieStore = httpclient.getCookieStore();

                List<Cookie> cookies = cookieStore.getCookies();
                for (Cookie cookie : cookies) {
                    getLog().debug(cookie.getName() + " = " + cookie.getValue());
                }

                getLog().debug(responseBody);
                authentication = mapper.readValue(responseBody, Authentication.class);
            } catch (URISyntaxException e) {
                getLog().error(e.getMessage(), e);
            } catch (ClientProtocolException e) {
                getLog().error(e.getMessage(), e);
            } catch (IOException e) {
                getLog().error(e.getMessage(), e);
            }
        }

        return authentication;
    }

    public UpLoader upload(Authentication authentication) throws MojoExecutionException {
        UpLoader upLoader = null;
        Proxy mavenProxy = getMavenProxy();
        UsernamePasswordCredentials usernamePasswordCredentials = getProxyCredential(mavenProxy);
        HttpHost http_proxy = createHttpProxyProxy(mavenProxy);

        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            httpclient = wrapClient(httpclient);
            if (http_proxy != null) {
                httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, http_proxy);
                if (usernamePasswordCredentials != null) {
                    httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), usernamePasswordCredentials);
                }
            }

            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, http_proxy);
            httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
            httpclient.getParams().setParameter("http.protocol.single-cookie-header", Boolean.TRUE);
            httpclient.setCookieStore(getCookieStore());

            for (Cookie cookie : httpclient.getCookieStore().getCookies()) {
                getLog().debug(cookie.getName() + " = " + cookie.getValue());
            }

            File[] files = outputDirectory.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.isFile() && pathname.getName().matches(".*\\.(" + WAR_TYPE + "|" + EAR_TYPE + "|" + JAR_TYPE + ")$");
                }
            });

            if (files == null || files.length == 0) {
                throw new MojoExecutionException("Output directory doesn't contain artifacts");
            }

            //The biggest is the first
            List<File> fileList = new ArrayList<File>(Arrays.asList(files));
            Collections.sort(fileList, new Comparator<File>() {
                public int compare(File o1, File o2) {
                    if (o1.length() > o2.length()) {
                        return -1;
                    }

                    if (o1.length() < o2.length()) {
                        return 1;
                    }

                    return 0;
                }
            });

            String customArtifactName = getCustomArtifactName();
            if (customArtifactName != null && customArtifactName.length() > 0) {
                String artifactPath = outputDirectory + File.separator + customArtifactName;
                getLog().debug("Custom artifact path: " + artifactPath);
                artifactFile = new File(artifactPath);
                if (!artifactFile.exists()) {
                    artifactFile = fileList.get(0);
                }
            } else {
                //ignore default artifact file (remove this for rollback 'The biggest is the first' algorithm)
                artifactFile = null;
            }

            if (artifactFile == null || !artifactFile.exists()) {
                artifactFile = fileList.get(0);
            }

            getLog().debug("Found artifacts:");

            for (File file : fileList) {
                getLog().debug("\t" + (artifactFile.getName().equalsIgnoreCase(file.getName()) ? "(*) " : "  * ") + file.getName() + " - " + file.length());
            }

            getLog().debug("Selected artifact: " + artifactFile.getAbsolutePath());

            /*if (!artifactFile.exists()) {
                String externalFileName = getWarNameFromWarPlugin();
                if (externalFileName != null) {
                    String artifactPath = outputDirectory + File.separator + externalFileName + "." + packaging;
                    File extPlufinConfiguration = new File(artifactPath);
                    if (!extPlufinConfiguration.exists()) {
                        throw new MojoExecutionException("First build artifact and try again. Artifact not found " + extPlufinConfiguration.getName());
                    }

                    getLog().info("Found another configuration artifact name is " + extPlufinConfiguration.getName());

                    artifactFile = new File(artifactPath);
                } else {
                    throw new MojoExecutionException("First build artifact and try again. Artifact not found " + artifactFile.getName());
                }
            }*/

            getLog().info("File Uploading Progress :");

            final boolean batchMode = Boolean.getBoolean(System.getProperty(BATCH_MODE, "false"));
            CustomMultiPartEntity multipartEntity = new CustomMultiPartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, new CustomMultiPartEntity.ProgressListener() {
                public void transferred(long num) {
                    if (((int) ((num / (float) totalSize) * 100)) != numSt) {
                        if (!batchMode) {
                            getLog().info("[" + (int) ((num / (float) totalSize) * 100) + "%]");
                        }
                        numSt = ((int) ((num / (float) totalSize) * 100));
                    }
                }
            });

            multipartEntity.addPart("fid", new StringBody("123456"));
            multipartEntity.addPart("session", new StringBody(authentication.getSession()));
            multipartEntity.addPart("file", new FileBody(artifactFile));

            totalSize = multipartEntity.getContentLength();

            URI uri = URIUtils.createURI(getShema(), getApiJelastic(), getPort(), getUrlUploader(), null, null);
            getLog().debug(uri.toString());
            HttpPost httpPost = new HttpPost(uri);
            addHeaders(httpPost);
            httpPost.setEntity(multipartEntity);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpPost, responseHandler);
            getLog().debug(responseBody);
            upLoader = mapper.readValue(responseBody, UpLoader.class);

            if (!isEmpty(upLoader.getFile())) {
                String fileUrl = upLoader.getFile();
                fileUrl = fileUrl.replaceFirst(HTTP_PROTOCOL, HTTPS_PROTOCOL);
                if (isAvailableByHttps(fileUrl)) {
                    upLoader.setFile(fileUrl);
                }
            }
        } catch (URISyntaxException e) {
            getLog().error(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            getLog().error(e.getMessage(), e);
        } catch (IOException e) {
            getLog().error(e.getMessage(), e);
        }

        return upLoader;
    }

    private String getArtifactComment() {
        String comment = COMMENT_PREFIX;
        String localComment = null;
        String jelasticComment = System.getProperty(JELASTIC_COMMENT_PROPERTY);

        if (StringUtils.isNotEmpty(jelasticComment)) {
            localComment = jelasticComment;
        } else if (project.getModel().getDescription() != null) {
            localComment = project.getModel().getDescription();
        }

        if (StringUtils.isNotEmpty(localComment)) {
            comment += ". " + localComment.replaceAll("\\n", "");
        }

        return comment;
    }

    public CreateObject createObject(UpLoader upLoader, final Authentication authentication) {
        final String comment = getArtifactComment();

        Map<String, String> params = new HashMap<String, String>();

        params.put("charset", "UTF-8");
        params.put("session", authentication.getSession());
        params.put("type", "JDeploy");
        params.put("data", "{'name':'" + artifactFile.getName() + "', 'archive':'" + upLoader.getFile() + "', 'link':0, 'size':" + upLoader.getSize() + ", 'comment':'" + comment + "'}");

        CreateObject createObject = makeRequest(getUrlCreateObject(), params, CreateObject.class);

        new Thread(new Runnable() {
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("charset", "UTF-8");
                params.put("session", authentication.getSession());

                Archives archives = makeRequest(getUrlGetArchives(), params, Archives.class);

                if (archives == null || archives.getResult() != 0 || archives.getResponse().getResult() != 0 || archives.getResponse().getObjects().isEmpty()) {
                    return;
                }

                List<Integer> ids = new ArrayList<Integer>();

                for (Archive archive : archives.getResponse().getObjects()) {
                    if (archive.getName().equals(artifactFile.getName()) && StringUtils.isNotEmpty(archive.getComment()) && archive.getComment().startsWith(COMMENT_PREFIX)) {
                        ids.add(archive.getId());
                    }
                }

                if (ids.size() < SAME_FILES_LIMIT) {
                    return;
                }

                Collections.sort(ids);

                for (int id : ids.subList(0, ids.size() - SAME_FILES_LIMIT)) {
                    Map<String, String> parameters = new HashMap<String, String>(params);
                    parameters.put("id", String.valueOf(id));

                    getLog().debug("parameters: " + parameters);
                    makeRequest(getUrlDeleteArchive(), parameters, null);
                }
            }
        }).start();

        return createObject;
    }

    public <T> T makeRequest(String url, Map<String, String> params, Class<T> clazz) {
        Proxy mavenProxy = getMavenProxy();
        UsernamePasswordCredentials usernamePasswordCredentials = getProxyCredential(mavenProxy);
        HttpHost http_proxy = createHttpProxyProxy(mavenProxy);

        try {
            final DefaultHttpClient httpclient = wrapClient(new DefaultHttpClient());

            if (http_proxy != null) {
                httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, http_proxy);

                if (usernamePasswordCredentials != null) {
                    httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), usernamePasswordCredentials);
                }
            }

            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, http_proxy);
            httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
            httpclient.getParams().setParameter("http.protocol.single-cookie-header", Boolean.TRUE);

            httpclient.setCookieStore(getCookieStore());

            for (Cookie cookie : httpclient.getCookieStore().getCookies()) {
                getLog().debug(cookie.getName() + " = " + cookie.getValue());
            }

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : params.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, params.get(key)));
            }

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");

            URI uri = URIUtils.createURI(getShema(), getApiJelastic(), getPort(), url, null, null);
            getLog().debug(uri.toString());

            HttpPost httpPost = new HttpPost(uri);
            addHeaders(httpPost);
            httpPost.setEntity(entity);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            String responseBody = httpclient.execute(httpPost, responseHandler);
            getLog().debug(responseBody);

            if (clazz != null) {
                return clazz.cast(mapper.readValue(responseBody, clazz));
            }
        } catch (URISyntaxException e) {
            getLog().error(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            getLog().error(e.getMessage(), e);
        } catch (IOException e) {
            getLog().error(e.getMessage(), e);
        }

        return null;
    }

    public Deploy deploy(Authentication authentication, UpLoader upLoader, CreateObject createObject) {
        Deploy deploy = null;
        Proxy mavenProxy = getMavenProxy();
        UsernamePasswordCredentials usernamePasswordCredentials = getProxyCredential(mavenProxy);
        HttpHost http_proxy = createHttpProxyProxy(mavenProxy);

        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            httpclient = wrapClient(httpclient);
            if (http_proxy != null) {
                httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, http_proxy);
                if (usernamePasswordCredentials != null) {
                    httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), usernamePasswordCredentials);
                }
            }

            httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
            httpclient.getParams().setParameter("http.protocol.single-cookie-header", Boolean.TRUE);
            httpclient.setCookieStore(getCookieStore());

            for (Cookie cookie : httpclient.getCookieStore().getCookies()) {
                getLog().debug(cookie.getName() + " = " + cookie.getValue());
            }

            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            qparams.add(new BasicNameValuePair("charset", "UTF-8"));
            qparams.add(new BasicNameValuePair("session", authentication.getSession()));
            qparams.add(new BasicNameValuePair("archiveUri", upLoader.getFile()));
            qparams.add(new BasicNameValuePair("archiveName", upLoader.getName()));
            qparams.add(new BasicNameValuePair("newContext", getContext()));
            qparams.add(new BasicNameValuePair("domain", getEnvironment()));
            qparams.add(new BasicNameValuePair("nodeGroup", getNodeGroup()));

            String preDeployHookContent = getPreDeployHookContent();
            if (preDeployHookContent != null) {
                qparams.add(new BasicNameValuePair("preDeployHook", preDeployHookContent));
            }

            String postDeployHookContent = getPostDeployHookContent();
            if (postDeployHookContent != null) {
                qparams.add(new BasicNameValuePair("postDeployHook", postDeployHookContent));
            }

            String actionKey = getActionKey();
            if (actionKey != null) {
                qparams.add(new BasicNameValuePair("actionkey", actionKey));
            }

            URI uri = URIUtils.createURI(getShema(), getApiJelastic(), getPort(), getUrlDeploy(), URLEncodedUtils.format(qparams, "UTF-8"), null);
            getLog().debug(uri.toString());
            HttpGet httpPost = new HttpGet(uri);
            addHeaders(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpPost, responseHandler);
            getLog().debug(responseBody);
            deploy = mapper.readValue(responseBody, Deploy.class);
        } catch (URISyntaxException e) {
            getLog().error(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            getLog().error(e.getMessage(), e);
        } catch (IOException e) {
            getLog().error(e.getMessage(), e);
        }

        return deploy;
    }

    public LogOut logOut(Authentication authentication) {
        LogOut logOut = null;
        Proxy mavenProxy = getMavenProxy();
        UsernamePasswordCredentials usernamePasswordCredentials = getProxyCredential(mavenProxy);
        HttpHost http_proxy = createHttpProxyProxy(mavenProxy);

        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            httpclient = wrapClient(httpclient);
            if (http_proxy != null) {
                httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, http_proxy);
                if (usernamePasswordCredentials != null) {
                    httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), usernamePasswordCredentials);
                }
            }
            httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
            httpclient.getParams().setParameter("http.protocol.single-cookie-header", Boolean.TRUE);
            httpclient.setCookieStore(getCookieStore());

            for (Cookie cookie : httpclient.getCookieStore().getCookies()) {
                getLog().debug(cookie.getName() + " = " + cookie.getValue());
            }

            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            qparams.add(new BasicNameValuePair("charset", "UTF-8"));
            qparams.add(new BasicNameValuePair("session", authentication.getSession()));

            URI uri = URIUtils.createURI(getShema(), getApiJelastic(), getPort(), getUrlLogOut(), URLEncodedUtils.format(qparams, "UTF-8"), null);
            getLog().debug(uri.toString());
            HttpGet httpPost = new HttpGet(uri);
            addHeaders(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpPost, responseHandler);
            getLog().debug(responseBody);
            logOut = mapper.readValue(responseBody, LogOut.class);
        } catch (URISyntaxException e) {
            getLog().error(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            getLog().error(e.getMessage(), e);
        } catch (IOException e) {
            getLog().error(e.getMessage(), e);
        }
        return logOut;
    }

    private void addHeaders(AbstractHttpMessage message) {
        if (headers != null) {
            for (String key : headers.keySet()) {
                String value = headers.get(key);
                getLog().debug(key + "=" + value);
                message.addHeader(key, value);
            }
        }
    }

    public String getWarNameFromWarPlugin() {
        MavenProject mavenProject = ((MavenProject) getPluginContext().get("project"));
        List<Plugin> plugins = mavenProject.getOriginalModel().getBuild().getPlugins();
        for (Plugin plugin : plugins) {
            if (plugin.getArtifactId().equals("maven-war-plugin")) {
                Xpp3Dom xpp3Dom = (Xpp3Dom) plugin.getConfiguration();
                Xpp3Dom[] xpp3Doms = xpp3Dom.getChildren();
                for (Xpp3Dom dom : xpp3Doms) {
                    if (dom.getName().equals("warName")) {
                        return dom.getValue();
                    }
                }
            }
        }
        return null;
    }

    private Proxy getMavenProxy() {
        List<Proxy> proxyList = mavenSession.getSettings().getProxies();
        for (Proxy proxy : proxyList) {
            if (proxy.getProtocol().equalsIgnoreCase("http") || proxy.isActive()) {
                return proxy;
            } else if (proxy.getProtocol().equalsIgnoreCase("https") || proxy.isActive()) {
                return proxy;
            }
        }

        return null;
    }

    private HttpHost createHttpProxyProxy(Proxy proxy) {
        if (proxy == null) {
            return null;
        }

        return new HttpHost(proxy.getHost(), proxy.getPort(), proxy.getProtocol());
    }

    private UsernamePasswordCredentials getProxyCredential(Proxy proxy) {
        UsernamePasswordCredentials credentials = null;
        if (proxy != null) {
            if (proxy.getUsername() != null || proxy.getPassword() != null) {
                credentials = new UsernamePasswordCredentials(proxy.getUsername(), proxy.getPassword());
            }
        }

        return credentials;
    }

    private boolean isAvailableByHttps(String fileUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();
            connection.setConnectTimeout(5000);
            return connection.getResponseCode() == 200;
        } catch (IOException e) {
        }

        return false;
    }

    private boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
