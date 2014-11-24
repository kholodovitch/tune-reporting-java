package com.tune.sdk.management.shared.service;

/**
 * Response.java
 *
 * Copyright (c) 2014 Tune, Inc
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * Java Version 1.6
 *
 * @category  Tune
 * @package   com.tune.sdk.management.shared.service
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-24 09:34:47 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.json.JSONException;

import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * Tune Management API Response.
 */
public class TuneManagementResponse {

    /**
     * Tune Management API request URL.
     */
    private String      request_url;

    /**
     * @var Tune Management API Service response.
     */
    private String      response_raw;

    /**
     * Property of full JSON response returned from service of
     * Tune Management API.
     */
    private JSONObject  response_json;

    /**
     * Property of HTTP response code returned from curl after completion for
     * Tune Management API request.
     */
    private int         response_http_code;

    /**
     * Property of HTTP response headers returned from curl after completion for
     * Tune Management API request.
     */
    private Map<String, List<String>> response_headers;

    /**
     * Constructor
     *
     * @param response_raw          Tune Management API Service full response.
     * @param response_json         Tune Management API Service response JSON.
     * @param response_http_code    Tune Management API Service response HTTP code.
     * @param response_headers      Tune Management API Service response HTTP headers.
     * @param request_url           Tune Management API request URL
     */
    public TuneManagementResponse(
        String              response_raw,
        JSONObject          response_json,
        int                 response_http_code,
        Map<String, List<String>> response_headers,
        String              request_url
    )
    {
        this.response_raw       = response_raw;
        this.response_json      = response_json;
        this.response_http_code = response_http_code;
        this.response_headers   = response_headers;
        this.request_url        = request_url;
    }

    public String getRequestUrl()
    {
        return this.request_url;
    }
    public void setRequestUrl(String request_url)
    {
        this.request_url = request_url;
    }

    /**
     * @return string
     */
    public String getRaw()
    {
        return this.response_raw;
    }
    public void setRaw(String raw)
    {
        this.response_raw = raw;

        try {
            this.response_json = new JSONObject(this.response_raw);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return JSON object
     */
    public JSONObject getJSON()
    {
        return this.response_json;
    }
    public void setJSON(JSONObject json)
    {
        this.response_json = json;
    }

    /**
     * HTTP code returned within Tune Management API reponse.
     *
     * @return int
     */
    public int getHttpCode()
    {
        return this.response_http_code;
    }
    public void setHttpCode(int http_code)
    {
        this.response_http_code = http_code;
    }

    /**
     * HTTP headers returned within Tune Management API reponse.
     *
     * @return Map
     */
    public Map<String, List<String>> getHeaders()
    {
        return this.response_headers;
    }
    public void setHeaders(Map<String, List<String>> headers)
    {
        this.response_headers = headers;
    }

    /**
     * Get raw representing only the "data" response from Tune Management API.
     *
     * @return String
     */
    public String toStringRaw()
    {
        String response_raw_print = "";
        try {
            //tokenize the ugly JSON string
            JSONTokener tokener = new JSONTokener(this.getRaw());
             // convert it to JSON object
            JSONObject finalResult = new JSONObject(tokener);
            // To string method prints it with specified indentation.
            response_raw_print = finalResult.toString(4);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // convert it to JSON object

        return response_raw_print;
    }

    /**
     * Get a String object representing this response.
     *
     * @return String
     */
    public String toString()
    {
        int status_code = this.getStatusCode();
        int response_size = this.getResponseSize();
        Object data = this.getData();
        String data_str = (null != data) ? data.toString() : "Null";
        String errors = this.getErrors();
        String errors_str = (null != errors) ? errors.toString() : "Null";
        int http_code = this.getHttpCode();
        Map<String, List<String>>  http_headers = this.getHeaders();
        String http_headers_str = null;

        if ((null != http_headers) && !http_headers.isEmpty()) {
            Iterator<Map.Entry<String, List<String>>> it = http_headers.entrySet().iterator();
            http_headers_str = "";
            while (it.hasNext()) {
                Map.Entry<String, List<String>> pairs = it.next();

                String header_name = pairs.getKey();
                List<String> header_values = pairs.getValue();

                http_headers_str += String.format("\n\t '%s':", header_name);
                for (String header_value : header_values) {
                    http_headers_str += String.format("\n\t\t '%s':", header_value);
                }
            }
        } else {
            http_headers_str = "Null";
        }

        String response_pretty_print = "";
        try {
            JSONTokener tokener = new JSONTokener(this.getRaw()); //tokenize the ugly JSON string
            JSONObject finalResult = new JSONObject(tokener);
            response_pretty_print = finalResult.toString(4);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // convert it to JSON object

        return String.format(
               "\nrequest_url:\t\t '%s'"
             + "\nhttp_code:\t\t '%d'"
             + "\nhttp_headers:\t\t '%s'"
             + "\nraw_response:\n%s\n",
            this.request_url,
            http_code,
            http_headers_str,
            response_pretty_print
        );
    }

    /**
     * Return "data" contents of Tune Management API JSON response.
     *
     * @return JSONObject
     */
    public Object getData()
    {
        if (this.response_json.has("data")) {
            try {
                if (!this.response_json.isNull("data")) {
                    return this.response_json.get("data");
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    /**
     * Return "response_size" contents of Tune Management API JSON response.
     *
     * @return int
     */
    public int getResponseSize()
    {
        if (this.response_json.has("response_size")) {
            try {
                return this.response_json.getInt("response_size");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return 0;
    }

    /**
     * Return "status_code" contents of Tune Management API JSON response.
     *
     * @return int
     */
    public int getStatusCode()
    {
        if (this.response_json.has("status_code")) {
            try {
                return this.response_json.getInt("status_code");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return 0;
    }

    /**
     * Return "errors" contents of Tune Management API JSON response.
     *
     * @return String
     */
    public String getErrors()
    {
        if (this.response_json.has("errors")) {
            try {
                if (this.response_json.has("errors")) {
                    Object errors = this.response_json.get("errors");

                    if (null != errors) {
                        if (errors instanceof JSONArray) {
                            JSONArray arr = this.response_json.getJSONArray("errors");
                            if (arr.length() > 0) {
                                String errors_str = "";
                                for (int i = 0; i < arr.length(); i++) {
                                    JSONObject error = arr.getJSONObject(i);

                                    errors_str += String.format("\n\t'%s'", error.toString());
                                }
                                return errors_str;
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
     * Return "debug" contents of Tune Management API JSON response.
     *
     * @return String
     */
    public String getDebugs()
    {
        if (this.response_json.has("debugs")) {
            try {
                return this.response_json.getString("debugs");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return "";
    }
}
