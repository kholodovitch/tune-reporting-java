package com.tune.reporting.base.service;

/**
 * TuneServiceRequest.java
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
 * @version   $Date: 2015-03-06 12:26:07 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.helpers.TuneSdkException;

import java.util.Map;
import java.lang.System;

/**
 * Contents of a TUNE Service API request.
 */
public final class TuneServiceRequest {

  /**
   * TUNE Reporting SDK name.
   */
  private final String SDK_NAME = "tune-reporting-java";

  /**
   * TUNE Reporting SDK version.
   */
  private final String SDK_VERSION = "0.9.9";

  /**
   * TUNE Service API endpoint requested.
   */
  private String controller = null;

  /**
   * TUNE Service API endpoint action requested.
   * @var String
   */
  private String action = null;

  /**
   * TUNE Reporting Authentication Key:
   * MobileAppTracking API_KEY or Session token.
   */
  private String strAuthKey = null;

  /**
   * TUNE Reporting Authentication Type:
   * api_key OR session_token.
   */
  private String strAuthType = null;

  /**
   * Query String dictionary.
   * @var Map
   */
  private Map<String, String> mapQueryString = null;

  /**
   * TUNE Service API URL.
   * @var String
   */
  private String apiUrlBase = null;

  /**
   * TUNE Service API Version.
   * @var String
   */
  private String apiUrlVersion = null;

  /**
   * Instantiates a new base request.
   *
   * @param controller        TUNE Service API controller.
   * @param action            TUNE Service API controller's action.
   * @param strAuthKey        TUNE Reporting Authentication Key.
   * @param strAuthType       TUNE Reporting Authentication Type.
   * @param mapQueryString    Query string elements appropriate to
   *                          the requested controller's action.
   * @param apiUrlBase        TUNE Service API base url.
   * @param apiUrlVersion     TUNE Service API version.
   *
   */
  public TuneServiceRequest(
      final String        controller,
      final String        action,
      final String        strAuthKey,
      final String        strAuthType,
      final Map<String, String> mapQueryString,
      final String        apiUrlBase,
      final String        apiUrlVersion
  ) {
    // -----------------------------------------------------------------
    // validate inputs
    // -----------------------------------------------------------------
    // controller
    if ((null == controller) || controller.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'controller' is not defined."
      );
    }
    // action
    if ((null == action) || action.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'action' is not defined.");
    }

    this.controller       = controller;
    this.action           = action;
    this.strAuthKey       = strAuthKey;
    this.strAuthType      = strAuthType;
    this.mapQueryString   = mapQueryString;
    this.apiUrlBase       = apiUrlBase;
    this.apiUrlVersion    = apiUrlVersion;
  }

  /**
   * Get controller for this request.
   *
   * @return String
   */
  public String getController() {
    return this.controller;
  }

  /**
   * Set controller for this request.
   *
   * @param controller Endpoint controller name
   */
  public void setController(final String controller) {
    this.controller = controller;
  }

  /**
   * Get controller action for this request.
   *
   * @return String
   */
  public String getAction() {
    return this.action;
  }

  /**
   * Set controller action for this request.
   *
   * @param action Endpoint controller's action name.
   */
  public void setAction(final String action) {
    this.action = action;
  }

  /**
   * Get TUNE Reporting Authentication Key.
   *
   * @return String TUNE MobileAppTracking Key.
   */
  public String getAuthKey() {
    return this.strAuthKey;
  }

  /**
   * Set TUNE Reporting Authentication Key.
   *
   * @param strAuthKey TUNE Reporting Authentication Key.
   */
  public void setAuthKey(String strAuthKey) {
    this.strAuthKey = strAuthKey;
  }

  /**
   * Get TUNE Reporting Authentication Type.
   *
   * @return String TUNE Reporting Authentication Type.
   */
  public String getAuthType() {
    return this.strAuthType;
  }

  /**
   * Set TUNE Reporting Authentication Type.
   *
   * @param strAuthType TUNE Reporting Authentication Type.
   */
  public void setAuthType(String strAuthType) {
    this.strAuthType = strAuthType;
  }

  /**
   * Get query string map property.
   *
   * @return Map Query String component of TUNE Service API request.
   */
  public Map<String, String> getQueryData() {
    return this.mapQueryString;
  }

  /**
   * Set query string map property.
   * @param mapQueryString  Map of TUNE Service API query string parameters.
   */
  public void setQueryData(final Map<String, String> mapQueryString) {
    this.mapQueryString = mapQueryString;
  }

  /**
   * Create query string using provide values in set properties
   * of this request object.
   *
   * @return String Query String component of TUNE Service API request.
   * @throws TuneSdkException If fails to post request.
   */
  public String getQueryString() throws TuneSdkException {
    QueryStringBuilder qs = new QueryStringBuilder();

    try {
      qs.add("sdk", this.SDK_NAME);
      qs.add("ver", this.SDK_VERSION);

      // Set authentication
      if ((this.strAuthType != null)
          && (this.strAuthKey != null)
          && !this.strAuthType.isEmpty()
          && !this.strAuthKey.isEmpty()
      ) {
        qs.add(this.strAuthType, this.strAuthKey);
      }

      if (this.mapQueryString != null && !this.mapQueryString.isEmpty()) {
        for (Map.Entry<String, String> entry : this.mapQueryString.entrySet()) {
          String key = entry.getKey();
          String value = entry.getValue();
          qs.add(key, value);
        }
      }

      qs.add("_", Long.toString(System.currentTimeMillis()));
    } catch (TuneSdkException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new TuneSdkException("Unexpected: " + ex.getMessage(), ex);
    }

    return qs.toString();
  }

  /**
   * TUNE Service API service path.
   *
   * @return String Full TUNE Service API request without Query String.
   */
  public String getPath() {
    String requestPath = String.format(
      "%s/%s/%s/%s",
      this.apiUrlBase,
      this.apiUrlVersion,
      this.controller,
      this.action
    );

    return requestPath;
  }

  /**
   * TUNE Service API full service request.
   *
   * @return String Full TUNE Service API request with Query String.
   * @throws TuneSdkException If fails to post request.
   */
  public String getServiceUrl() throws TuneSdkException {
    String requestUrl = String.format(
        "%s?%s",
        this.getPath(),
        this.getQueryString()
    );

    return requestUrl;
  }
}
