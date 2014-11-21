/**
 * TuneManagementClient.java
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
 * @package   tune.management.shared.service
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-19 21:21:08 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk.management.shared.service;

import java.util.Map;
import com.tune.sdk.shared.*;


/**
 * Tune MobileAppTracking Management API access class
 */
public final class TuneManagementClient {

    public static final String TUNE_MANAGEMENT_API_BASE_URL = "https://api.mobileapptracking.com";
    public static final String TUNE_MANAGEMENT_API_VERSION  = "v2";

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
     * @return Response
     */
    public TuneManagementResponse getResponse()
    {
        return this.response;
    }

    /**
     * Constructor
     *
     * @param controller            Tune Management API endpoint name
     * @param action                Tune Management API endpoint's action name
     * @param api_key               Tune MobileAppTracking API Key
     * @param query_string_dict     Action's query string parameters
     */
    public TuneManagementClient(
        String controller,
        String action,
        String api_key,
        Map<String, String> query_string_dict
    ) {
        // controller
        if ( (null == controller) || controller.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'controller' is not defined.");
        }
        // action
        if ( (null == action) || action.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'action' is not defined.");
        }
        // api_key
        if ( (null == api_key) || api_key.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'api_key' is not defined.");
        }

        // set up the request
        this.request = new TuneManagementRequest(
            controller,
            action,
            api_key,
            query_string_dict,
            TUNE_MANAGEMENT_API_BASE_URL,
            TUNE_MANAGEMENT_API_VERSION
        );
    }

    /**
     * Call Tune Management API Service with provided request.
     *
     * @return Boolean
     * @throws \TuneSdkException
     * @throws \Exception
     */
    public Boolean call() throws TuneSdkException
    {
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
                String.format("Error: Request URL: '%'\nMessage: '%s'\n",
                        this.request.getUrl(),
                        ex.getMessage()),
                    ex );
        }

        return success;
    }
}
