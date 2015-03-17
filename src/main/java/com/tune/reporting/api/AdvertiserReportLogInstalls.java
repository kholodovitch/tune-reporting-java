package com.tune.reporting.api;

/**
 * AdvertiserReportLogInstalls.java
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
 * TUNE Service API endpoint 'advertiser/stats/installs'.
 */
public class AdvertiserReportLogInstalls extends AdvertiserReportLogBase {

  /**
   * Constructor.
   *
   */
  public AdvertiserReportLogInstalls(
  ) throws TuneSdkException {
    super(
      "advertiser/stats/installs",
      true,
      true
    );

    /*
     * Fields recommended in suggested order.
     */
    this.setFieldsRecommended(new HashSet<String>(Arrays.asList(
        "id",
        "created",
        "status",
        "site_id",
        "site.name",
        "publisher_id",
        "publisher.name",
        "advertiser_ref_id",
        "advertiser_sub_campaign_id",
        "advertiser_sub_campaign.ref",
        "publisher_sub_campaign_id",
        "publisher_sub_campaign.ref",
        "user_id",
        "device_id",
        "os_id",
        "google_aid",
        "ios_ifa",
        "ios_ifv",
        "windows_aid",
        "referral_url",
        "is_view_through"
    )));
  }
}
