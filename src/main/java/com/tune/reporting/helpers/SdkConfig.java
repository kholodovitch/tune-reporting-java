package com.tune.reporting.helpers;

/**
 * SdkConfig.java
 *
 * <p>
 * Copyright (c) 2015 TUNE, Inc.
 * All rights reserved.
 * </p>
 *
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * </p>
 *
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * </p>
 *
 * <p>
 * Java Version 1.6
 * </p>
 *
 * <p>
 * @category  tune-reporting
 * @package   com.tune.reporting
 * @author    Jeff Tanner jefft@tune.com
 * @copyright 2015 TUNE, Inc. (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2015-01-05 09:40:09 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.Properties;

/**
 * Singleton for pulling SDK Configuration.
 */
public final class SdkConfig {

  /**
   * TUNE Reporting SDK Configuration File.
   */
  public static final String SDK_CONFIG_FILENAME
      = "tune_reporting_sdk_config.properties";

  /**
   * Instance of this singleton.
   */
  private static SdkConfig instance = null;

  /**
   * Content of SDK Configuration.
   */
  private Properties sdkConfig;

  /**
   * Sync.
   */
  private static Object syncObject = new Object();

  /**
   * Instantiates a new sdk config.
   *
   * @throws TuneSdkException Upon failure to read SDK configuration.
   */
  private SdkConfig() throws TuneSdkException {
    this.sdkConfig = new Properties();
    String srcDirectory = new File("").getAbsolutePath();
    String strSdkConfigFilePath
        = srcDirectory + "/config/" + SDK_CONFIG_FILENAME;
    File fileSdkConfig = new File(strSdkConfigFilePath);
    if(!fileSdkConfig.exists() || fileSdkConfig.isDirectory()) {
      throw new TuneSdkException(
        String.format(
          "Tune Reporting SDK configuration file does not exist: '%s'.",
          strSdkConfigFilePath
        )
      );
    }

    try {
      InputStream in = new FileInputStream(fileSdkConfig);
      this.sdkConfig.load(in);
    } catch (IOException e) {
      throw new TuneSdkException(
        String.format(
          "IOException: Problems getting SDK configuration: '%s', error: '%s'",
          strSdkConfigFilePath,
          e.getMessage()
        ),
        e
      );
    } catch (Exception e) {
      throw new TuneSdkException(
        String.format(
          "Unexpected: Problems getting SDK configuration: '%s', error: '%s'",
          strSdkConfigFilePath,
          e.getMessage()
        ),
        e
      );
    }
  }

  /**
   * Gets the single instance of SdkConfig.
   *
   * @return SdkConfig          Singleton instance of SdkConfig.
   * @throws TuneSdkException   Upon failure to read SDK configuration.
   */
  public static SdkConfig getInstance() throws TuneSdkException {
    if (null == SdkConfig.instance) {
      synchronized (SdkConfig.syncObject) {
        if (null == SdkConfig.instance) {
          SdkConfig.instance = new SdkConfig();
        }
      }
    }
    return SdkConfig.instance;
  }

  /**
   * Gets the config value.
   *
   * @param key     The key to be placed into this property list.
   * @return the config value
   */
  public String getConfigValue(
      final String key
  ) {
    if (null == this.sdkConfig) {
      throw new NullPointerException("Reference to 'sdkConfig' is null.");
    }
    if ((null == key) || key.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'key' is null.");
    }

    return this.sdkConfig.getProperty(key);
  }

  /**
   * Sets the config value.
   *
   * @param key     The key to be placed into this property list.
   * @param value   The value corresponding to key.
   */
  public void setConfigValue(
      final String key,
      final String value
  ) {
    if (null == this.sdkConfig) {
      throw new NullPointerException("Reference to 'sdkConfig' is null.");
    }
    if ((null == key) || key.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'key' is not defined.");
    }
    if ((null == value) || value.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'value' is not defined.");
    }

    this.sdkConfig.setProperty(key, value);
  }

  /**
   * Get API_KEY from TUNE Reporting SDK configuration.
   *
   * @return String   TUNE MobileAppTracking API Key.
   */
  public String getApiKey()
  {
    String apiKey = this.getConfigValue("tune_reporting_api_key_string");

    if (!apiKey.matches("[a-zA-Z0-9]+")) {
      throw new IllegalArgumentException(
        String.format("Invalid 'tune_reporting_api_key_string': '%s'", apiKey)
      );
    }

    if (apiKey.equals("API_KEY")) {
      throw new IllegalArgumentException(
        String.format("Invalid 'tune_reporting_api_key_string': '%s'", apiKey)
      );
    }

    return apiKey;
  }

  /**
   * Set API_KEY from TUNE Reporting SDK configuration.
   *
   * @param apiKey   TUNE MobileAppTracking API Key.
   */
  public void setApiKey(String apiKey)
  {
    this.setConfigValue("tune_reporting_api_key_string", apiKey);
  }

  /**
   * Get if to validate fields.
   *
   * @return Boolean  Validate fields used by parameters.
   */
  public Boolean getValidateFields()
  {
    String configValue = this.getConfigValue("tune_reporting_validate_fields_boolean");
    if (configValue.equalsIgnoreCase("true") || configValue.equalsIgnoreCase("false")) {
      return Boolean.valueOf(configValue);
    }
    return false;
  }

  /**
   * Get fetch sleep in seconds.
   *
   * @return Integer  sleep in seconds.
   */
  public Integer getFetchSleep()
  {
    String configValue = this.getConfigValue("tune_reporting_export_status_sleep_seconds");
    return Integer.parseInt(configValue);
  }

  /**
   * Get fetch timeout in seconds.
   *
   * @return Integer  timeout in seconds.
   */
  public Integer getFetchTimeout()
  {
    String configValue = this.getConfigValue("tune_reporting_export_status_timeout_seconds");
    return Integer.parseInt(configValue);
  }

  /**
   * Get if to validate fields.
   *
   * @return Boolean  Validate fields used by parameters.
   */
  public Boolean getFetchVerbose()
  {
    String configValue = this.getConfigValue("tune_reporting_export_status_verbose_boolean");
    if (configValue.equalsIgnoreCase("true") || configValue.equalsIgnoreCase("false")) {
      return Boolean.valueOf(configValue);
    }
    return false;
  }
}
