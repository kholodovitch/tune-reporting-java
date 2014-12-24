package com.tune.reporting.base.service;

/**
 * Request.java
 *
 * <p>
 * Copyright (c) 2014 TUNE, Inc.
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
 * @copyright 2014 TUNE, Inc. (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-12-24 13:23:15 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.helpers.TuneSdkException;

import java.util.Map;

/**
 * Contents of a TUNE Mangement API request.
 */
public class TuneManagementRequest {

  /**
   * TUNE Reporting SDK name.
   */
  private final String SDK_NAME = "tune-reporting-java";

  /**
   * TUNE Reporting SDK version.
   */
  private final String SDK_VERSION = "0.9.4";

  /**
   * TUNE Management API endpoint requested.
   */
  private String controller = null;

  /**
   * TUNE Management API endpoint action requested.
   * @var String
   */
  private String action = null;

  /**
   * TUNE Management API key.
   * @var String
   */
  private String apiKey = null;

  /**
   * Query String dictionary.
   * @var Map
   */
  private Map<String, String> mapQueryString = null;

  /**
   * TUNE Management API URL.
   * @var String
   */
  private String apiUrlBase = null;

  /**
   * TUNE Management API Version.
   * @var String
   */
  private String apiUrlVersion = null;

  /**
   * Instantiates a new base request.
   *
   * @param controller    TUNE Management API controller
   * @param action      TUNE Management API controller's action
   * @param apiKey       User's API Key provide by their
   * MobileAppTracking platform account.
   * @param mapQueryString Query string elements appropriate to
   * the requested controller's action.
   * @param apiUrlBase    TUNE Management API base url.
   * @param apiUrlVersion   TUNE Management API version.
   *
   */
  public TuneManagementRequest(
      final String        controller,
      final String        action,
      final String        apiKey,
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
    // apiKey
    if ((null == apiKey) || apiKey.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'apiKey' is not defined.");
    }

    this.controller     = controller;
    this.action       = action;
    this.apiKey       = apiKey;
    this.mapQueryString   = mapQueryString;
    this.apiUrlBase     = apiUrlBase;
    this.apiUrlVersion  = apiUrlVersion;
  }

  /**
   * Get controller for this request.
   *
   * @return String
   */
  public final String getController() {
    return this.controller;
  }

  public final void setController(String controller) {
    this.controller = controller;
  }

  /**
   * Get controller action for this request.
   *
   * @return String
   */
  public final String getAction() {
    return this.action;
  }

  public final void setAction(final String action) {
    this.action = action;
  }

  /**
   * Get apiKey property.
   */
  public final String getApiKey() {
    return this.apiKey;
  }

  /**
   * Set apiKey property.
   * @param apiKey  TUNE MobileAppTracking Key
   */
  public void setApiKey(final String apiKey) {
    this.apiKey = apiKey;
  }

  /**
   * Get query string map property.
   */
  public final Map<String, String> getQueryData() {
    return this.mapQueryString;
  }

  /**
   * Set query string map property.
   * @param mapQueryString  Map of TUNE Management API query string parameters.
   */
  public final void setQueryData(final Map<String, String> mapQueryString) {
    this.mapQueryString = mapQueryString;
  }

  /**
   * Create query string using provide values in set properties
   * of this request object.
   *
   * @return String Query String component of TUNE Management API request.
   * @throws TuneSdkException If fails to post request.
   */
  public final String getQueryString() throws TuneSdkException {
    QueryStringBuilder qs = new QueryStringBuilder();

    if (!this.apiKey.isEmpty()) {
      try {
        qs.add("sdk_name", this.SDK_NAME);
        qs.add("sdk_version", this.SDK_VERSION);
        qs.add("api_key", this.apiKey);

        if (this.mapQueryString != null && !this.mapQueryString.isEmpty()) {
          for (Map.Entry<String, String> entry : this.mapQueryString.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            qs.add(key, value);
          }
        }
      } catch (TuneSdkException ex) {
        throw ex;
      } catch (Exception ex) {
        throw new TuneSdkException("Unexpected: " + ex.getMessage(), ex);
      }
    }

    return qs.toString();
  }

  /**
   * TUNE Management API service path.
   *
   * @return String Full TUNE Management API request without Query String.
   */
  public final String getPath() {
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
   * TUNE Management API full service request.
   *
   * @return String Full TUNE Management API request with Query String.
   * @throws TuneSdkException If fails to post request.
   */
  public final String getUrl() throws TuneSdkException {
    String requestUrl = String.format(
        "%s?%s",
        this.getPath(),
        this.getQueryString()
   );

    return requestUrl;
  }
}
