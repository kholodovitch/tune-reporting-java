package com.tune.reporting.base.service;

/**
 * Proxy.java
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

import com.tune.reporting.helpers.TuneSdkException;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * The Class Proxy.
 */
public final class TuneManagementProxy {

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
   * @return object
   */
  public TuneManagementResponse getResponse() {
    return this.response;
  }

  /** The uri. */
  private String uri = null;

  /**
   * Instantiates a new service proxy.
   *
   * @param request @see TuneManagementRequest
   */
  public TuneManagementProxy(
      final TuneManagementRequest request
  ) {
    this.request = request;
  }

  /**
   * Execute request.
   *
   * @return Boolean True if successful posting request, else False.
   * @throws TuneSdkException If fails to post request.
   */
  public boolean execute()
    throws TuneSdkException {
    this.response = null;
    boolean isSuccess = false;
    try {
      this.uri = this.request.getUrl();
      if (this.postRequest()) {
        isSuccess = true;
      }
    } catch (TuneSdkException e) {
      throw e;
    } catch (Exception e) {
      throw new TuneSdkException("Unexpected: Failed to process request.", e);
    }

    return isSuccess;
  }

  /**
   * Post request to TUNE Management API Service
   *
   * @return Boolean True if successful posting request, else False.
   * @throws TuneSdkException If fails to post request.
   */
  protected boolean postRequest() throws TuneSdkException {
    URL url = null;
    HttpsURLConnection connection = null;

    try {
      url = new URL(this.uri);
    } catch (MalformedURLException e) {
      throw new TuneSdkException(
        String.format("MalformedURLException: '%s'", this.uri),
        e
      );
    }

    try {
      // connect to the server over HTTPS and submit the payload
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setUseCaches(false);
      connection.setAllowUserInteraction(false);
      connection.connect();

      // Gets the status code from an HTTP response message.
      final int responseHttpCode = connection.getResponseCode();

      // Returns an unmodifiable Map of the header fields.
      // The Map keys are Strings that represent the response-header
      // field names. Each Map value is an unmodifiable List of Strings
      // that represents the corresponding field values.
      final Map<String, List<String>> responseHeaders
          = connection.getHeaderFields();

      final String    requestUrl = url.toString();

      // Gets the HTTP response message, if any, returned along
      // with the response code from a server.
      String responseRaw = connection.getResponseMessage();

      // Pull entire JSON raw response
      BufferedReader br
          = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
              );
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line + "\n");
      }
      br.close();

      responseRaw = sb.toString();

      // decode to JSON
      JSONObject responseJson = new JSONObject(responseRaw);


      this.response = new TuneManagementResponse(
        responseRaw,
        responseJson,
        responseHttpCode,
        responseHeaders,
        requestUrl.toString()
      );
    } catch (Exception e) {
      throw new TuneSdkException(
        String.format(
          "Problems executing request: %s: '%s'",
          e.getClass().toString(),
          e.getMessage()
        ),
        e
      );
    }

    return true;
  }
}
