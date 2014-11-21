/**
 * ReportsEndpointBase.java
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
 * @package   tune.management.shared.endpoints
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-19 21:21:08 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk.management.shared.endpoints;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import com.tune.sdk.management.shared.service.*;

/**
 * Base class for Tune Mangement API reports endpoints.
 */
public class ReportsEndpointBase extends EndpointBase
{
    /**
     * Remove debug mode information from results.
     */
    protected Boolean filter_debug_mode = false;

    /**
     * Remove test profile information from results.
     */
    protected Boolean filter_test_profile_id = false;

    /**
     * Constructor
     *
     * @param controller                Tune Management API endpoint name.
     * @param api_key                   MobileAppTracking API Key.
     * @param filter_debug_mode         Remove debug mode information from results.
     * @param filter_test_profile_id    Remove test profile information from results.
     * @param validate_fields           Validate fields used by actions' parameters.
     */
    public ReportsEndpointBase(
        String controller,
        String api_key,
        Boolean filter_debug_mode,
        Boolean filter_test_profile_id,
        Boolean validate_fields
    ) {
        super(controller, api_key, validate_fields);

        this.filter_debug_mode = filter_debug_mode;
        this.filter_test_profile_id = filter_test_profile_id;
    }

    /**
     * Prepare action with provided query string parameters, then call
     * Management API service.
     *
     * @param action              Endpoint action to be called.
     * @param query_string_dict   Query string parameters for this action.
     * @throws Exception
     *
     * @throws IllegalArgumentException
     */
    protected TuneManagementResponse callRecords(
        String action,
        Map<String,String> query_string_dict
    ) throws Exception {
        // action
        if ((null == action) || action.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'action' is not defined.");
        }
        // query_string_dict
        if ( (null == query_string_dict) || query_string_dict.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'query_string_dict' is not defined.");
        }

        String sdk_filter = "";

        if (this.filter_debug_mode) {
            sdk_filter = "(debug_mode=0 OR debug_mode is NULL)";
        }

        if (this.filter_test_profile_id) {
            if (!sdk_filter.isEmpty()) {
                sdk_filter += " AND ";
            }

            sdk_filter += "(test_profile_id=0 OR test_profile_id IS NULL)";
        }

        if (!sdk_filter.isEmpty()) {
            if (query_string_dict.containsKey("filter")) {
                if ((null != query_string_dict.get("filter")) &&
                        !query_string_dict.get("filter").isEmpty()) {
                    query_string_dict.put(
                        "filter",
                        "(" + query_string_dict.get("filter") + ") AND " + sdk_filter
                    );
                } else {
                    query_string_dict.put("filter", sdk_filter);
                }
            } else {
                query_string_dict.put("filter", sdk_filter);
            }
        }

        if (query_string_dict.containsKey("filter")) {
            if (!query_string_dict.get("filter").isEmpty()) {
                query_string_dict.put(
                    "filter",
                    "(" + query_string_dict.get("filter") + ")"
                );
            }
        }

        return super.call(
            action,
            query_string_dict
        );
    }

}