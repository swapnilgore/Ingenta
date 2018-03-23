package com.automation.support;

import com.automation.report.ConfigFileReadWrite;

public interface IFrameworkConstant {

    String frameworkConfigFile = "configurations/framework.properties";
    String baseUrl = ConfigFileReadWrite.read(frameworkConfigFile, "baseUrl");
    String browser = ConfigFileReadWrite.read(frameworkConfigFile, "browser");
    boolean defaultUserDataFile = Boolean.parseBoolean(ConfigFileReadWrite.read(frameworkConfigFile, "defaultUserDataFile"));
    String customizedUserDataFile = ConfigFileReadWrite.read(frameworkConfigFile, "customizedUserDataFile");
}
