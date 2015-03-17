package com.tune.reporting.base.service;

/**
 * TuneServiceClient.java
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

/**
 * TUNE MobileAppTracking Management API access class.
 */
public final class TuneServiceClient {

  /** TUNE Service API Service. */
  public static final String TUNE_MANAGEMENT_API_BASE_URL
      = "https://api.mobileapptracking.com";
  /** TUNE Service API Controller Version. */
  public static final String TUNE_MANAGEMENT_API_VERSION
      = "v2";

  /**
   * TUNE Service API request.
   */
  private TuneServiceRequest request = null;

  /**
   * TUNE Service API response.
   */
  private TuneServiceResponse response = null;

  /**
   * Get request property for this request.
   *
   * @return TuneServiceRequest
   */
  public TuneServiceRequest getRequest() {
    return this.request;
  }

  /**
   * Get response property for this request.
   *
   * @return TuneServiceResponse
   */
  public TuneServiceResponse getResponse() {
    return this.response;
  }

  /**
   * Constructor.
   *
   * @param controller      TUNE Service API endpoint name.
   * @param action          TUNE Service API endpoint's action name.
   * @param mapQueryString  Action's query string parameters.
   */
  public TuneServiceClient(
    final String controller,
    final String action,
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

    // set up the request
    this.request = new TuneServiceRequest(
      controller,
      action,
      null,
      null,
      mapQueryString,
      TUNE_MANAGEMENT_API_BASE_URL,
      TUNE_MANAGEMENT_API_VERSION
    );
  }

  /**
   * Call TUNE Service API Service with provided request.
   *
   * @return Boolean True if successful posting request, else False.
   * @throws TuneSdkException If fails to post request.
   */
  public Boolean call(
    final String strAuthKey,
    final String strAuthType
  )
    throws TuneSdkException
  {
    if ((null == strAuthKey) || strAuthKey.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'strAuthKey' is not defined.");
    }
    if ((null == strAuthType) || strAuthType.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'strAuthType' is not defined.");
    }
    if (null == this.request) {
      throw new TuneSdkException("Property 'request' was not defined.");
    }

    this.request.setAuthKey(strAuthKey);
    this.request.setAuthType(strAuthType);

    return this.call();
  }

  /**
   * Call TUNE Service API Service with provided request.
   *
   * @return Boolean True if successful posting request, else False.
   * @throws TuneSdkException If fails to post request.
   */
  public Boolean call()
    throws TuneSdkException
  {
    if (null == this.request) {
      throw new TuneSdkException("Property 'request' was not defined.");
    }

    Boolean success = false;
    this.response = null;

    try {
      // make the request
      TuneServiceProxy proxy = new TuneServiceProxy(this.request);
      if (proxy.execute()) {
        success = true;
        this.response = proxy.getResponse();
      }
    } catch (Exception ex) {
      throw new TuneSdkException(
        String.format(
          "Error: Request URL: '%'\nMessage: '%s'\n",
          this.request.getServiceUrl(),
          ex.getMessage()
        ),
        ex
      );
    }

    return success;
  }

  /**
   * TUNE Service API full service request.
   *
   * @return String Full TUNE Service API request with Query String.
   * @throws TuneSdkException If fails to post request.
   */
  public String getServiceUrl() throws TuneSdkException {
    return this.request.getServiceUrl();
  }
}
