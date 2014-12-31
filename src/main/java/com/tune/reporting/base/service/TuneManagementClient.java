package com.tune.reporting.base.service;

/**
 * TuneManagementClient.java
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
 * @version   $Date: 2014-12-31 09:56:30 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.helpers.TuneSdkException;

import java.util.Map;

/**
 * TUNE MobileAppTracking Management API access class.
 */
public final class TuneManagementClient {

  /** TUNE Management API Service. */
  public static final String TUNE_MANAGEMENT_API_BASE_URL
      = "https://api.mobileapptracking.com";
  /** TUNE Management API Controller Version. */
  public static final String TUNE_MANAGEMENT_API_VERSION
      = "v2";

  /**
   * Tune Management API request.
   */
  private TuneManagementRequest request = null;

  /**
   * Tune Management API response.
   */
  private TuneManagementResponse response = null;

/**
   * Get request property for this request.
   *
   * @return TuneManagementRequest
   */
  public TuneManagementRequest getRequest() {
    return this.request;
  }

  /**
   * Get response property for this request.
   *
   * @return TuneManagementResponse
   */
  public TuneManagementResponse getResponse() {
    return this.response;
  }

  /**
   * Constructor.
   *
   * @param controller      TUNE Management API endpoint name
   * @param action        TUNE Management API endpoint's action name
   * @param apiKey         TUNE MobileAppTracking API Key
   * @param mapQueryString   Action's query string parameters
   */
  public TuneManagementClient(
      final String controller,
      final String action,
      final String apiKey,
      final Map<String, String> mapQueryString
  ) {
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

    // set up the request
    this.request = new TuneManagementRequest(
      controller,
      action,
      apiKey,
      mapQueryString,
      TUNE_MANAGEMENT_API_BASE_URL,
      TUNE_MANAGEMENT_API_VERSION
    );
  }

  /**
   * Call TUNE Management API Service with provided request.
   *
   * @return Boolean True if successful posting request, else False.
   * @throws TuneSdkException If fails to post request.
   */
  public Boolean call() throws TuneSdkException {
    if (null == this.request) {
      throw new TuneSdkException("Property 'request' was not defined.");
    }

    Boolean success = false;
    this.response = null;

    try {
      // make the request
      TuneManagementProxy proxy = new TuneManagementProxy(this.request);
      if (proxy.execute()) {
        success = true;
        this.response = proxy.getResponse();
      }
    } catch (Exception ex) {
      throw new TuneSdkException(
      String.format(
        "Error: Request URL: '%'\nMessage: '%s'\n",
        this.request.getUrl(),
        ex.getMessage()
     ),
      ex
     );
    }

    return success;
  }
}
