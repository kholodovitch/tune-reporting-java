/**
 * Request.java
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
 * @version   $Date: 2014-11-19 07:02:45 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk.management.shared.service;
import java.util.Map;

import com.tune.sdk.shared.TuneSdkException;

/**
 * Class Tune Management Request
 */
public class TuneManagementRequest {

    /**
     *
     * Tune Management API endpoint requested.
     */
    private String controller = null;

    /**
     * Tune Management API endpoint action requested.
     */
    private String action = null;

    /**
     *
     * Tune Management API key
     */
    private String api_key = null;

    /**
     *
     * Query String dictionary
     * @var Map
     */
    private Map<String, String> query_string_dict = null;

    /**
     *
     * Tune Management API URL
     */
    private String api_url_base = null;

    /**
     *
     * Tune Management API Version
     */
    private String api_url_version = null;

    /**
     * Instantiates a new base request.
     *
     * @param controller        Tune Management API controller
     * @param action            Tune Management API controller's action
     * @param api_key           User's API Key provide by their MobileAppTracking platform account.
     * @param query_string_dict Query string elements appropriate to the requested controller's action.
     * @param api_url_base      Tune Management API base url.
     * @param api_url_version   Tune Management API version.
     *
     */
    public TuneManagementRequest(
        String              controller,
        String              action,
        String              api_key,
        Map<String, String> query_string_dict,
        String              api_url_base,
        String              api_url_version
    ){
        // -----------------------------------------------------------------
        // validate inputs
        // -----------------------------------------------------------------
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

        this.controller         = controller;
        this.action             = action;
        this.api_key            = api_key;
        this.query_string_dict  = query_string_dict;
        this.api_url_base       = api_url_base;
        this.api_url_version    = api_url_version;
    }

    /**
     *
     * Get controller for this request.
     *
     * @return string
     */
    public String getController()
    {
        return this.controller;
    }

    public void setController(String controller)
    {
        this.controller = controller;
    }

    /**
     *
     * Get controller action for this request.
     *
     * @return string
     */
    public String getAction()
    {
        return this.action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    /**
     * Get api_key property
     */
    public String getApiKey()
    {
        return this.api_key;
    }

    /**
     * Set api_key property
     * @param api_key
     */
    public void setApiKey( String api_key )
    {
        this.api_key = api_key;
    }

    /**
     * Get _query_data_map property
     */
    public Map<String, String> getQueryData()
    {
        return this.query_string_dict;
    }

    /**
     * Set _query_data_map property
     * @param query_string_dict
     */
    public void setQueryData(Map<String, String> query_string_dict)
    {
        this.query_string_dict = query_string_dict;
    }

    /**
     * Create query string using provide values in set properties of this request object.
     *
     * @return string
     * @throws TuneSdkException
     */
    public String getQueryString() throws TuneSdkException
    {
        QueryStringBuilder qs = new QueryStringBuilder();

        if ( !this.api_key.isEmpty() ) {
            try {
                qs.add("api_key", this.api_key);

                if (this.query_string_dict != null && !this.query_string_dict.isEmpty()) {
                    for (Map.Entry<String, String> entry : this.query_string_dict.entrySet()) {
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
     * Tune Management API service path
     *
     * @return string
     */
    public String getPath()
    {
        String request_path = String.format(
            "%s/%s/%s/%s",
            this.api_url_base,
            this.api_url_version,
            this.controller,
            this.action
        );

        return request_path;
    }

    /**
     * Tune Management API full service request
     *
     * @return string
     * @throws TuneSdkException
     */
    public String getUrl() throws TuneSdkException
    {
        String request_url = String.format(
            "%s?%s",
            this.getPath(),
            this.getQueryString()
        );

        return request_url;
    }
}