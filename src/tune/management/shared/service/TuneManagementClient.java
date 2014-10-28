/**
 * TuneManagementClient.java
 * Tune_API_Java
 *
 * @version  0.9.0
 * @link     http://www.tune.com
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
 */

package tune.management.shared.service;

import java.util.*;

import tune.shared.*;

public final class TuneManagementClient {

    public static final String TUNE_MANAGEMENT_API_BASE_URL = "https://api.mobileapptracking.com";
    public static final String TUNE_MANAGEMENT_API_VERSION  = "v2";

    private Request request = null;
    private Response response = null;

  /**
     * Get request property for this request.
     *
     * @return Request
     */
    public Request getRequest()
    {
        return this.request;
    }

    /**
     * Get response property for this request.
     *
     * @return Response
     */
    public Response getResponse()
    {
        return this.response;
    }

    /**
     * Constructor
     *
     * @param controller
     * @param action
     * @param api_key
     * @param query_data_map
     * @param is_debug_mode
     * @param response
     * @return
     */
    public TuneManagementClient(
        String controller,
        String action,
        String api_key,
        Map<String, String> query_data_map
    ) throws IllegalArgumentException, Exception
    {
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
        this.request = new Request(
            controller,
            action,
            api_key,
            query_data_map,
            TUNE_MANAGEMENT_API_BASE_URL,
            TUNE_MANAGEMENT_API_VERSION
        );
    }
    
    /**
     * Call Tune Management API Service with provided request.
     *
     * @return bool
     * @throws \TuneSdkException
     * @throws \Exception
     */
    public Boolean call() throws TuneSdkException, Exception
    {
        if (null == this.request) {
            throw new TuneSdkException("Request was not defined.");
        }

        Boolean success = false;
        this.response = null;

        try {
            // make the request
            Proxy proxy = new Proxy(this.request);
            if (proxy.execute()) {
                success = true;
                this.response = proxy.getResponse();
            }
        } catch (Exception ex) {
            throw ex;
        }

        return success;
    }
}
