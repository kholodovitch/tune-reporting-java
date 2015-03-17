package com.tune.reporting.base.endpoints;

/**
 * AdvertiserReportBase.java
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

import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneServiceResponse;
import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.helpers.TuneServiceException;

import java.util.Map;

/**
 * Base class for TUNE Service API reports endpoints.
 */
public abstract class AdvertiserReportBase extends EndpointBase {
  /**
   * Remove debug mode information from results.
   */
  private Boolean filterDebugMode = false;

  /**
   * Remove test profile information from results.
   */
  private Boolean filterTestProfileId = false;

  /**
   * Constructor.
   *
   * @param controller          TUNE Service API endpoint name.
   * @param filterDebugMode     Remove debug mode information from results.
   * @param filterTestProfileId Remove test profile information from results.
   */
  public AdvertiserReportBase(
    final String controller,
    final Boolean filterDebugMode,
    final Boolean filterTestProfileId
  ) throws TuneSdkException {
    super(controller);

    this.filterDebugMode = filterDebugMode;
    this.filterTestProfileId = filterTestProfileId;
  }

  /**
   * Prepare action with provided query string parameters, then call
   * TUNE Service API service.
   *
   * @param action          Endpoint action to be called.
   * @param mapQueryString  Query string parameters for this action.
   *
   * @return TuneServiceResponse
   * @throws TuneSdkException If fails to post request.
   */
  protected final TuneServiceResponse callService(
    final String action,
    final String strAuthKey,
    final String strAuthType,
    final Map<String, String> mapQueryString
  ) throws TuneSdkException
  {
    // action
    if ((null == action) || action.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'action' is not defined."
      );
    }
    if ((null == strAuthKey) || strAuthKey.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'strAuthKey' is not defined.");
    }
    if ((null == strAuthType) || strAuthType.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'strAuthType' is not defined.");
    }
    // mapQueryString
    if ((null == mapQueryString) || mapQueryString.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'mapQueryString' is not defined."
      );
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
      strAuthKey,
      strAuthType,
      mapQueryString
    );
  }

  public abstract TuneServiceResponse count(
    final String strAuthKey,
    final String strAuthType,
    final Map<String, Object> mapParams
  ) throws  TuneSdkException,
            TuneServiceException;

  public abstract TuneServiceResponse find(
    final String strAuthKey,
    final String strAuthType,
    final Map<String, Object> mapParams
  ) throws  TuneSdkException,
            TuneServiceException;

  public abstract TuneServiceResponse export(
    final String strAuthKey,
    final String strAuthType,
    final Map<String, Object> mapParams
  ) throws  TuneSdkException,
            TuneServiceException;

  public abstract TuneServiceResponse status(
    final String strAuthKey,
    final String strAuthType,
    final String jobId
  ) throws TuneSdkException;

  public abstract TuneServiceResponse fetch(
    final String strAuthKey,
    final String strAuthType,
    final String jobId
  ) throws TuneServiceException, TuneSdkException;
}
