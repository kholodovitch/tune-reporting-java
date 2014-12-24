package com.tune.reporting.base.service;

/**
 * Response.java
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TUNE Management API Response.
 */
public class TuneManagementResponse {

  /**
   * TUNE Management API request URL.
   */
  private String    requestUrl;

  /**
   * @var TUNE Management API Service response.
   */
  private String    responseRaw;

  /**
   * Property of full JSON response returned from service of
   * TUNE Management API.
   */
  private JSONObject  responseJson;

  /**
   * Property of HTTP response code returned from curl after completion for
   * TUNE Management API request.
   */
  private int     responseHttpCode;

  /**
   * Property of HTTP response headers returned from curl after completion for
   * TUNE Management API request.
   */
  private Map<String, List<String>> responseHeaders;

  /**
   * Constructor.
   *
   * @param responseRaw      TUNE Management API Service full response.
   * @param responseJson     TUNE Management API Service response JSON.
   * @param responseHttpCode TUNE Management API Service response HTTP code.
   * @param responseHeaders  TUNE Management API Service response HTTP headers.
   * @param requestUrl       TUNE Management API request URL
   */
  public TuneManagementResponse(
      final String                    responseRaw,
      final JSONObject                responseJson,
      final int                       responseHttpCode,
      final Map<String, List<String>> responseHeaders,
      final String                    requestUrl
 ) {
    this.responseRaw        = responseRaw;
    this.responseJson       = responseJson;
    this.responseHttpCode   = responseHttpCode;
    this.responseHeaders    = responseHeaders;
    this.requestUrl         = requestUrl;
  }

  /**
   * Get request URL.
   */
  public final String getRequestUrl() {
    return this.requestUrl;
  }

  /**
   * Set request URL.
   * @param requestUrl       TUNE Management API request URL
   */
  public final void setRequestUrl(final String requestUrl) {
    this.requestUrl = requestUrl;
  }

  /**
   * Get Raw response from TUNE Mangement API service.
   */
  public final String getRaw() {
    return this.responseRaw;
  }

  /**
   * Set Raw response from TUNE Mangement API service.
   * @param responseRaw  TUNE Management API Service full response.
   */
  public final void setRaw(final String responseRaw) {
    this.responseRaw = responseRaw;

    try {
      this.responseJson = new JSONObject(this.responseRaw);
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get Full JSON response from TUNE Mangement API service.
   */
  public final JSONObject getJson() {
    return this.responseJson;
  }

  /**
   * Set Full JSON response from TUNE Mangement API service.
   * @param responseJson TUNE Management API Service response JSON.
   */
  public final void setJson(final JSONObject responseJson) {
    this.responseJson = responseJson;
  }

  /**
   * Get HTTP code returned within TUNE Management API reponse.
   *
   * @return int
   */
  public final int getHttpCode() {
    return this.responseHttpCode;
  }

  /**
   * Set HTTP code returned within TUNE Management API reponse.
   * @param responseHttpCode TUNE Management API Service response HTTP code.
   */
  public final void setHttpCode(final int responseHttpCode) {
    this.responseHttpCode = responseHttpCode;
  }

  /**
   * Get HTTP headers returned within TUNE Management API reponse.
   *
   * @return Map
   */
  public final Map<String, List<String>> getHeaders() {
    return this.responseHeaders;
  }

  /**
   * Set HTTP headers returned within TUNE Management API reponse.
   * @param responseHeaders  TUNE Management API Service response HTTP headers.
   */
  public final void setHeaders(
    final Map<String, List<String>> responseHeaders
  ) {
    this.responseHeaders = responseHeaders;
  }

  /**
   * Get raw representing only the "data" response from TUNE Management API.
   *
   * @return String
   */
  public final String toStringRaw() {
    String responseRawPrint = "";
    try {
      //tokenize the ugly JSON string
      JSONTokener tokener = new JSONTokener(this.getRaw());
       // convert it to JSON object
      JSONObject finalResult = new JSONObject(tokener);
      // To string method prints it with specified indentation.
      responseRawPrint = finalResult.toString(4);
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } // convert it to JSON object

    return responseRawPrint;
  }

  /**
   * Get a String object representing this response.
   *
   * @return String
   */
  public final String toString() {
    int statusCode = this.getStatusCode();
    int responseSize = this.getResponseSize();
    Object data = this.getData();
    String dataStr = (null != data) ? data.toString() : "Null";
    String errors = this.getErrors();
    String errorsStr = (null != errors) ? errors.toString() : "Null";
    int httpCode = this.getHttpCode();
    Map<String, List<String>>  httpHeaders = this.getHeaders();
    String httpHeadersStr = null;

    if ((null != httpHeaders) && !httpHeaders.isEmpty()) {
      Iterator<Map.Entry<String, List<String>>> it
          = httpHeaders.entrySet().iterator();
      httpHeadersStr = "";
      while (it.hasNext()) {
        Map.Entry<String, List<String>> pairs = it.next();

        String headerName = pairs.getKey();
        List<String> headerValues = pairs.getValue();

        httpHeadersStr += String.format("\n\t '%s':", headerName);
        for (String headerValue : headerValues) {
          httpHeadersStr += String.format("\n\t\t '%s':", headerValue);
        }
      }
    } else {
      httpHeadersStr = "Null";
    }

    String responsePrettyPrint = "";
    try {
      JSONTokener tokener = new JSONTokener(this.getRaw());
      JSONObject finalResult = new JSONObject(tokener);
      responsePrettyPrint = finalResult.toString(4);
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } // convert it to JSON object

    return String.format(
      "\nrequest_url:\t\t '%s'"
      + "\nhttp_code:\t\t '%d'"
      + "\nhttpHeaders:\t\t '%s'"
      + "\nraw_response:\n%s\n",
      this.requestUrl,
      httpCode,
      httpHeadersStr,
      responsePrettyPrint
   );
  }

  /**
   * Return "data" contents of TUNE Management API JSON response.
   *
   * @return JSONObject
   */
  public Object getData() {
    if (this.responseJson.has("data")) {
      try {
        if (!this.responseJson.isNull("data")) {
          return this.responseJson.get("data");
        }
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
    }

    return null;
  }

  /**
   * Return "response_size" contents of TUNE Management API JSON response.
   *
   * @return int
   */
  public final int getResponseSize() {
    if (this.responseJson.has("response_size")) {
      try {
        return this.responseJson.getInt("response_size");
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
    }

    return 0;
  }

  /**
   * Return "status_code" contents of TUNE Management API JSON response.
   *
   * @return int
   */
  public final int getStatusCode() {
    if (this.responseJson.has("status_code")) {
      try {
        return this.responseJson.getInt("status_code");
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
    }

    return 0;
  }

  /**
   * Return "errors" contents of TUNE Management API JSON response.
   *
   * @return String
   */
  public final String getErrors() {
    if (this.responseJson.has("errors")) {
      try {
        if (this.responseJson.has("errors")) {
          Object errors = this.responseJson.get("errors");

          if (null != errors) {
            if (errors instanceof JSONArray) {
              JSONArray arr = this.responseJson.getJSONArray("errors");
              if (arr.length() > 0) {
                String errorsStr = "";
                for (int i = 0; i < arr.length(); i++) {
                  JSONObject error = arr.getJSONObject(i);

                  errorsStr
                      += String.format("\n\t'%s'", error.toString());
                }
                return errorsStr;
              }
            }
          }
        }
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
    }

    return null;
  }

  /**
   * Return "debug" contents of TUNE Management API JSON response.
   *
   * @return String
   */
  public final String getDebugs() {
    if (this.responseJson.has("debugs")) {
      try {
        return this.responseJson.getString("debugs");
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
    }

    return "";
  }
}
