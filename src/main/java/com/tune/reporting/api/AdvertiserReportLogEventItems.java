package com.tune.reporting.api;

/**
 * AdvertiserReportLogEventItems.java
 *
 * <p>
 * Copyright (c) 2015 TUNE, Inc.
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
 * @copyright 2015 TUNE, Inc. (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2015-03-05 23:27:46 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.base.endpoints.AdvertiserReportLogBase;
import com.tune.reporting.helpers.TuneSdkException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * TUNE Service API endpoint 'advertiser/stats/event/items'.
 */
public class AdvertiserReportLogEventItems extends AdvertiserReportLogBase {

  /**
   * Constructor.
   *
   */
  public AdvertiserReportLogEventItems(
  ) throws TuneSdkException {
    super(
      "advertiser/stats/event/items",
      false,
      true
    );

    /*
     * Fields recommended in suggested order.
     */
    this.setFieldsRecommended(new HashSet<String>(Arrays.asList(
        "id",
        "created",
        "site_id",
        "site.name",
        "campaign_id",
        "campaign.name",
        "site_event_id",
        "site_event.name",
        "site_event_item_id",
        "site_event_item.name",
        "quantity",
        "value_usd",
        "country_id",
        "country.name",
        "region_id",
        "region.name",
        "agency_id",
        "agency.name",
        "advertiser_sub_site_id",
        "advertiser_sub_site.name",
        "advertiser_sub_campaign_id",
        "advertiser_sub_campaign.name",
        "currency_code",
        "value"
    )));
  }
}
