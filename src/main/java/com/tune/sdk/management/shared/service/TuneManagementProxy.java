package com.tune.sdk.management.shared.service;

/**
 * Proxy.java
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
 * @version   $Date: 2014-11-21 17:34:43 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import com.tune.sdk.shared.*;

/**
 * The Class Proxy.
 */
public class TuneManagementProxy {

    private TuneManagementRequest request = null;
    private TuneManagementResponse response = null;

    /**
     * Get request property for this request.
     *
     * @return TuneManagementRequest
     */
    public TuneManagementRequest getRequest()
    {
        return this.request;
    }

    /**
     * Get response property for this request.
     *
     * @return object
     */
    public TuneManagementResponse getResponse()
    {
        return this.response;
    }

    /** The uri. */
    private String uri = null;

    /**
     * Instantiates a new service proxy.
     *
     * @param request @see TuneManagementRequest
     * @throws Exception the exception
     */
    public TuneManagementProxy( TuneManagementRequest request ) throws Exception
    {
        if (null == request) {
            throw new IllegalArgumentException("Parameter 'request' is not defined.");
        }

        this.request = request;
    }

    /**
     * Execute request.
     *
     * @return true, if successful
     * @throws TuneSdkException
     * @throws Exception the exception
     */
    public boolean execute() throws TuneSdkException
    {
        this.response = null;
        boolean isSuccess = false;
        try
        {
            this.uri = this.request.getUrl();
            if (this.postRequest()) {
                isSuccess = true;
            }
        }
        catch (TuneSdkException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new TuneSdkException( "Unexpected: Failed to process request.", e );
        }

        return isSuccess;
    }

    /**
     * Post request.
     *
     * @return true, if successful
     * @throws TuneSdkException
     * @throws Exception the exception
     */
    protected boolean postRequest() throws TuneSdkException
    {
        URL url = null;
        HttpsURLConnection connection = null;

        try {
            url = new URL(this.uri);
        } catch(MalformedURLException e) {
            throw new TuneSdkException( String.format("MalformedURLException: '%s'", this.uri), e );
        }

        try {
            // connect to the server over HTTPS and submit the payload
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.connect();

            String              response_raw = null;
            JSONObject          response_json = null;
            int                 response_http_code = 0;
            Map<String, List<String>> response_headers  = null;
            String              request_url = url.toString();

            // Gets the status code from an HTTP response message.
            response_http_code = connection.getResponseCode();

            // Returns an unmodifiable Map of the header fields.
            // The Map keys are Strings that represent the response-header
            // field names. Each Map value is an unmodifiable List of Strings
            // that represents the corresponding field values.
            response_headers = connection.getHeaderFields();

            // Gets the HTTP response message, if any, returned along
            // with the response code from a server.
            response_raw = connection.getResponseMessage();

            // Pull entire JSON raw response
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();

            response_raw = sb.toString();

            // decode to JSON
            response_json = new JSONObject(response_raw);

            this.response = new TuneManagementResponse(
                response_raw,
                response_json,
                response_http_code,
                response_headers,
                request_url.toString()
            );
        } catch (Exception e) {
            throw new TuneSdkException( String.format("Problems executing request: %s: '%s'", e.getClass().toString(), e.getMessage()), e );
        }

        return true;
    }
}
