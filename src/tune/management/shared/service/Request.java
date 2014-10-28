/**
 * Request.java
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

import java.util.HashMap;
import java.util.Map;

import tune.shared.*;

public class Request {

    /**
     *
     * @var string
     */
    private String controller = null;

    /**
     *
     * @var string
     */
    private String action = null;

    /**
     *
     * Tune Management API key
     * @var string
     */
    private String api_key = null;

    /**
     *
     * Query String dictionary
     * @var Map
     */
    private Map<String, String> query_data_map = null;

    /**
     *
     * Tune Management API URL
     * @var String
     */
    private String api_url_base = null;

    /**
     *
     * Tune Management API Version
     * @var String
     */
    private String api_url_version = null;

    /**
     * Instantiates a new base request.
     *
     * @param String controller
     * @param String action
     * @param String api_key
     * @param Map query_data_map
     * @param String api_url_base
     * @param String api_url_version
     *
     */
    public Request(
        String              controller,
        String              action,
        String              api_key,
        Map<String, String> query_data_map,
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
        this.query_data_map     = query_data_map;
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
        return this.query_data_map;
    }

    /**
     * Set _query_data_map property
     * @param $query_data_map
     */
    public void setQueryData(Map<String, String> query_data_map)
    {
        this.query_data_map = query_data_map;
    }

    /**
     * Create query string using provide values in set properties of this request object.
     *
     * @return string
     */
    public String getQueryString()
    {
        QueryStringBuilder qs = new QueryStringBuilder();

        if ( !this.api_key.isEmpty() ) {
            qs.add("api_key", this.api_key);
        }
        if (this.query_data_map != null && !this.query_data_map.isEmpty()) {
            for (Map.Entry<String, String> entry : this.query_data_map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                qs.add(key, value);
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
     */
    public String getUrl()
    {
        String request_url = String.format(
            "%s?%s",
            this.getPath(),
            this.getQueryString()
        );

        return request_url;
    }
}