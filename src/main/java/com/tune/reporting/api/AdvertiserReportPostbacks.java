package com.tune.reporting.api;

/**
 * AdvertiserReportPostbacks.java
 *
 * <p>
 * Copyright (c) 2014 Tune, Inc
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
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-12-12 05:24:55 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.base.endpoints.AdvertiserReportLogsBase;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Tune Management API endpoint 'advertiser/stats/postbacks'.
 */
public class AdvertiserReportPostbacks extends AdvertiserReportLogsBase {

  /**
   * Constructor.
   *
   * @param apiKey       Tune MobileAppTracking API Key.
   * @param validateFields   Validate fields used by actions' parameters.
   */
  public AdvertiserReportPostbacks(
      final String apiKey,
      final Boolean validateFields
 ) {
    super(
      "advertiser/stats/postbacks",
      apiKey,
      false,
      true,
      validateFields
   );

    /*
     * Fields recommended in suggested order.
     */
    this.setFieldsRecommended = new HashSet<String>(Arrays.asList(
      "id",
      "stat_install_id",
      "stat_event_id",
      "stat_open_id",
      "created",
      "status",
      "site_id",
      "site.name",
      "site_event_id",
      "site_event.name",
      "site_event.type",
      "publisher_id",
      "publisher.name",
      "attributed_publisher_id",
      "attributed_publisher.name",
      "url",
      "http_result"
   ));
  }
}
