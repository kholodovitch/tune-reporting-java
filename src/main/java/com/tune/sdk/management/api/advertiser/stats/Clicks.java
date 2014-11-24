package com.tune.sdk.management.api.advertiser.stats;

/**
 * Clicks.java
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
 * @package   com.tune.sdk.management.api.advertiser.stats
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-24 09:34:47 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.util.Arrays;
import java.util.HashSet;

import com.tune.sdk.management.shared.endpoints.*;

/**
 * Tune Management API endpoint 'advertiser/stats/clicks'
 *
 * @example ExampleReportClicks.java
 */
public class Clicks extends ReportsLogsEndpointBase {
    /**
     * Constructor
     *
     * @param api_key           Tune MobileAppTracking API Key.
     * @param validate_fields   Validate fields used by actions' parameters.
     */
    public Clicks(
        String api_key,
        Boolean validate_fields
    ) {
        super(
            "advertiser/stats/clicks",
            api_key,
            true,
            true,
            validate_fields
        );

        /*
         * Fields recommended in suggested order.
         */
        this.set_fields_recommended = new HashSet<String>(Arrays.asList(
            "id"
            ,"created"
            ,"site_id"
            ,"site.name"
            ,"publisher_id"
            ,"publisher.name"
            ,"is_unique"
            ,"advertiser_sub_campaign_id"
            ,"advertiser_sub_campaign.ref"
            ,"publisher_sub_campaign_id"
            ,"publisher_sub_campaign.ref"
        ));
    }
}