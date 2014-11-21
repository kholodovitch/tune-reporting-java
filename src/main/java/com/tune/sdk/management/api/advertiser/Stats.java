/**
 * Stats.java
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
 * @package   tune.management.api.advertiser
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-19 21:21:08 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk.management.api.advertiser;

import java.util.Arrays;
import java.util.HashSet;

import com.tune.sdk.management.shared.endpoints.ReportsActualsEndpointBase;

/**
 *
 */
public class Stats extends ReportsActualsEndpointBase {
    /**
     * Constructor
     *
     * @param api_key           Tune MobileAppTracking API Key.
     * @param validate_fields   Validate fields used by actions' parameters.
     */
    public Stats(
        String api_key,
        Boolean validate_fields
    ) {
        super(
            "advertiser/stats",
            api_key,
            true,
            true,
            validate_fields
        );

        /*
         * Fields recommended in suggested order.
         */
        this.set_fields_recommended = new HashSet<String>(Arrays.asList(
             "site_id"
            ,"site.name"
            ,"publisher_id"
            ,"publisher.name"
            ,"ad_impressions"
            ,"ad_impressions_unique"
            ,"ad_clicks"
            ,"ad_clicks_unique"
            ,"paid_installs"
            ,"paid_installs_assists"
            ,"non_installs_assists"
            ,"paid_events"
            ,"paid_events_assists"
            ,"non_events_assists"
            ,"paid_opens"
            ,"paid_opens_assists"
            ,"non_opens_assists"
        ));
    }
}