package com.jelastic.util;

public interface JelasticProperties {
    String JELASTIC_PREDEPLOY_HOOK  = "jelastic-predeploy-hook";
    String JELASTIC_POSTDEPLOY_HOOK = "jelastic-postdeploy-hook";
    String NODE_GROUP               = "nodegroup";
    String DELAY                    = "delay";
    String ENVIRONMENT              = "environment";
    String CONTEXT                  = "context";
    String JELASTIC_PASSWORD        = "jelastic-password";
    String JELASTIC_HOSTER          = "jelastic-hoster";

    String UPLOAD_URL = "https://%s/1.0/storage/uploader/rest/upload";

    String FID_VALUE     = "123456";
    String FID_PARAM     = "fid";
    String SESSION_PARAM = "session";
    String FILE_PARAM    = "file";
}
