package com.tune.reporting.base.endpoints;

/**
 * AdvertiserReportBase.java
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

import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneManagementResponse;
import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.helpers.TuneServiceException;

import java.util.Map;

/**
 * Base class for Tune Mangement API reports endpoints.
 */
public class AdvertiserReportBase extends EndpointBase {
  /**
   * Remove debug mode information from results.
   */
  protected Boolean filterDebugMode = false;

  /**
   * Remove test profile information from results.
   */
  protected Boolean filterTestProfileId = false;

  /**
   * Constructor.
   *
   * @param controller        Tune Management API endpoint name.
   * @param apiKey           MobileAppTracking API Key.
   * @param filterDebugMode     Remove debug mode information from results.
   * @param filterTestProfileId  Remove test profile information from results.
   * @param validateFields       Validate fields used by actions' parameters.
   */
  public AdvertiserReportBase(
      final String controller,
      final String apiKey,
      final Boolean filterDebugMode,
      final Boolean filterTestProfileId,
      final Boolean validateFields
 ) {
    super(controller, apiKey, validateFields);

    this.filterDebugMode = filterDebugMode;
    this.filterTestProfileId = filterTestProfileId;
  }

  /**
   * Prepare action with provided query string parameters, then call
   * Management API service.
   *
   * @param action          Endpoint action to be called.
   * @param mapQueryString  Query string parameters for this action.
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   * @throws IllegalArgumentException If invalid value is provided to a parameter.
   */
  protected TuneManagementResponse callRecords(
      String action,
      Map<String, String> mapQueryString
 ) throws IllegalArgumentException, TuneSdkException {
    // action
    if ((null == action) || action.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'action' is not defined.");
    }
    // mapQueryString
    if ((null == mapQueryString) || mapQueryString.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'mapQueryString' is not defined.");
    }

    String filterSdk = "";

    if (this.filterDebugMode) {
      filterSdk = "(debug_mode=0 OR debug_mode is NULL)";
    }

    if (this.filterTestProfileId) {
      if (!filterSdk.isEmpty()) {
        filterSdk += " AND ";
      }

      filterSdk += "(test_profile_id=0 OR test_profile_id IS NULL)";
    }

    if (!filterSdk.isEmpty()) {
      if (mapQueryString.containsKey("filter")) {
        if ((null != mapQueryString.get("filter"))
              && !mapQueryString.get("filter").isEmpty()) {
          mapQueryString.put(
              "filter",
              "(" + mapQueryString.get("filter") + ") AND " + filterSdk
         );
        } else {
          mapQueryString.put("filter", filterSdk);
        }
      } else {
        mapQueryString.put("filter", filterSdk);
      }
    }

    if (mapQueryString.containsKey("filter")) {
      if (!mapQueryString.get("filter").isEmpty()) {
        mapQueryString.put(
            "filter",
            "(" + mapQueryString.get("filter") + ")"
       );
      }
    }

    return super.call(
      action,
      mapQueryString
   );
  }
}
