package com.tune.reporting.base.endpoints;

/**
 * AdvertiserReportCohortBase.java
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
 * @version   $Date: 2015-03-06 12:26:07 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.base.service.TuneServiceResponse;
import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.helpers.TuneServiceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Base class for TUNE Service API reports insights endpoints.
 */
public abstract class AdvertiserReportCohortBase extends AdvertiserReportBase {

  /**
   * Allowed Cohort interval values.
   */
  protected static final Set<String> COHORT_INTERVALS
    = new HashSet<String>(Arrays.asList(
      "year_day",
      "year_week",
      "year_month",
      "year"
    ));

  /**
   * Allowed Cohort types values.
   */
  protected static final Set<String> COHORT_TYPES
    = new HashSet<String>(Arrays.asList(
      "click",
      "install"
    ));

  /**
   * Allowed aggregation types values.
   */
  protected static final Set<String> AGGREGATION_TYPES
    = new HashSet<String>(Arrays.asList(
      "incremental",
      "cumulative"
    ));

  /**
   * Constructor.
   *
   * @param controller          TUNE Service API endpoint name.
   * @param filterDebugMode     Remove debug mode information from results.
   * @param filterTestProfileId Remove test profile information from results.
   */
  public AdvertiserReportCohortBase(
    final String controller,
    final Boolean filterDebugMode,
    final Boolean filterTestProfileId
  ) throws TuneSdkException {
    super(
      controller,
      filterDebugMode,
      filterTestProfileId
    );
  }

  /**
   * Counts all existing records that match filter criteria
   * and returns an array of found model data.
   *
   * @param strAuthKey   API Key or Session Token
   * @param strAuthType  "api_key" or "session_token"
   * @param mapParams    Mapping of: <p><dl>
   * <dt>start_date</dt><dd>YYYY-MM-DD HH:MM:SS</dd>
   * <dt>end_date</dt><dd>YYYY-MM-DD HH:MM:SS</dd>
   * <dt>cohort_type</dt><dd>Cohort types: click, install</dd>
   * <dt>cohort_interval</dt><dd>Cohort intervals:
   *                    year_day, year_week, year_month, year</dd>
   * <dt>group</dt><dd>Group results using this endpoint's fields.</dd>
   * <dt>filter</dt><dd>Apply constraints based upon values associated with
   *                    this endpoint's fields.</dd>
   * <dt>response_timezone</dt><dd>Setting expected timezone for results,
   *                          default is set in account.</dd>
   * </dl><p>
   *
   * @return TuneServiceResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final TuneServiceResponse count(
    final String strAuthKey,
    final String strAuthType,
    final Map<String, Object> mapParams
  ) throws  TuneSdkException,
          TuneServiceException
  {
    if ((null == strAuthKey) || strAuthKey.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'strAuthKey' is not defined.");
    }
    if ((null == strAuthType) || strAuthType.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'strAuthType' is not defined.");
    }
    if (!mapParams.containsKey("start_date")) {
      throw new IllegalArgumentException(
        "Parameter 'start_date' is not defined."
      );
    }
    final String startDate = (String) mapParams.get("start_date");

    if (!mapParams.containsKey("end_date")) {
      throw new IllegalArgumentException(
        "Parameter 'end_date' is not defined."
      );
    }
    final String endDate = (String) mapParams.get("end_date");

    EndpointBase.validateDateTime("start_date", startDate);
    EndpointBase.validateDateTime("end_date", endDate);

    String filterV = null;
    if (mapParams.containsKey("filter")) {
      String filter = (String) mapParams.get("filter");
      if ((null != filter) && !filter.isEmpty()) {
        filterV = super.validateFilter(strAuthKey, strAuthType, filter);
      }
    }

    String groupV = null;
    if (mapParams.containsKey("group")) {
      String group = (String) mapParams.get("group");
      if ((null != group) && !group.isEmpty()) {
        groupV = super.validateGroup(strAuthKey, strAuthType, group);
      }
    }

    String responseTimezone = null;
    if (mapParams.containsKey("response_timezone")) {
      responseTimezone = (String) mapParams.get("response_timezone");
    }

    if (!mapParams.containsKey("cohort_type")) {
      throw new IllegalArgumentException(
        "Parameter 'cohort_type' is not defined."
      );
    }
    final String cohortType = (String) mapParams.get("cohort_type");

    if (!mapParams.containsKey("cohort_interval")) {
      throw new IllegalArgumentException(
        "Parameter 'cohort_interval' is not defined."
      );
    }
    final String cohortInterval = (String) mapParams.get("cohort_interval");

    AdvertiserReportCohortBase.validateCohortType(cohortType);
    AdvertiserReportCohortBase.validateCohortInterval(cohortInterval);

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("start_date", startDate);
    mapQueryString.put("end_date", endDate);
    mapQueryString.put("cohort_type", cohortType);
    mapQueryString.put("interval", cohortInterval);
    mapQueryString.put("group", groupV);
    mapQueryString.put("filter", filterV);
    mapQueryString.put("response_timezone", responseTimezone);

    return super.callService(
      "count",
      strAuthKey,
      strAuthType,
      mapQueryString
    );
  }

  /**
   * Query status of insight reports. Upon completion will
   * return url to download requested report.
   *
   * @param jobId  Provided Job Identifier to reference
   *              requested report on export queue.
   *
   * @return TuneServiceResponse
   * @throws TuneSdkException If fails to post request.
   */
  public final TuneServiceResponse status(
    final String strAuthKey,
    final String strAuthType,
    final String jobId
  ) throws TuneSdkException
  {
    if ((null == strAuthKey) || strAuthKey.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'strAuthKey' is not defined.");
    }
    if ((null == strAuthType) || strAuthType.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'strAuthType' is not defined.");
    }
    if ((null == jobId) || jobId.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'jobId' is not defined."
      );
    }

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("job_id", jobId);

    return super.call(
      strAuthKey,
      strAuthType,
      "status",
      mapQueryString
    );
  }

  /**
   * Validate value is expected cohort type.
   *
   * @param cohortType  Provide cohort type to validate.
   *
   * @return Bool Cohort type is valid.
   */
  public static Boolean validateCohortType(
    final String cohortType
  ) {
    if ((null == cohortType) || cohortType.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'cohortType' is not defined."
      );
    }

    if (!AdvertiserReportCohortBase.COHORT_TYPES.contains(cohortType)) {
      throw new IllegalArgumentException(
        String.format(
          "Parameter 'cohortType' is invalid: '%s'.",
          cohortType
        )
      );
    }

    return true;
  }

  /**
   * Validate value is expected cohort interval.
   *
   * @param cohortInterval  Provide cohort interval to validate.
   *
   * @return Boolean Cohort interval is valid.
   */
  public static Boolean validateCohortInterval(
    final String cohortInterval
  ) {
    if ((null == cohortInterval) || cohortInterval.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'cohortInterval' is not defined."
      );
    }

    if (!AdvertiserReportCohortBase.COHORT_INTERVALS.contains(cohortInterval)) {
      throw new IllegalArgumentException(
        String.format(
          "Parameter 'cohortInterval' is invalid: '%s'.",
          cohortInterval
        )
      );
    }

    return true;
  }

  /**
   * Validate value is valid aggregation type.
   *
   * @param aggregationType Provide aggregation type to validate.
   *
   * @return Boolean Aggretation type is valid.
   */
  public static Boolean validateAggregationTypes(
      final String aggregationType
  ) {
    if ((null == aggregationType) || aggregationType.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'aggregationType' is not defined."
      );
    }

    if (!AdvertiserReportCohortBase.AGGREGATION_TYPES.contains(aggregationType)) {
      throw new IllegalArgumentException(
        String.format(
          "Parameter 'aggregationType' is invalid: '%s'.",
          aggregationType
        )
      );
    }

    return true;
  }

  /**
   * Parse response and gather job identifier.
   *
   * @param response @see TuneServiceResponse
   *
   * @return String Report Job Id on Export queue.
   * @throws TuneServiceException If service fails to handle post request.
   * @throws TuneSdkException If fails to post request.
   */
  public static String parseResponseReportJobId(
      final TuneServiceResponse response
  ) throws TuneServiceException, TuneSdkException {
    if (null == response) {
      throw new IllegalArgumentException(
        "Parameter 'response' is not defined."
      );
    }

    JSONObject jdata = (JSONObject) response.getData();
    if (null == jdata) {
      throw new TuneServiceException(
        "Report request failed to get export data."
      );
    }

    if (!jdata.has("job_id")) {
      throw new TuneSdkException(
        String.format(
          "Export data does not contain report 'jobId', response: %s",
          response.toString()
        )
      );
    }

    String reportJobId = null;
    try {
      reportJobId = jdata.getString("job_id");
    } catch (JSONException ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    } catch (Exception ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    }

    if ((null == reportJobId) || reportJobId.isEmpty()) {
      throw new TuneSdkException(
        String.format(
          "Export response 'jobId' is not defined, response: %s",
          response.toString()
        )
      );
    }

    return reportJobId;
  }

  /**
   * Parse response and gather report url.
   *
   * @param response @see TuneServiceResponse
   *
   * @return String Report URL download from Export queue.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public static String parseResponseReportUrl(
      final TuneServiceResponse response
  ) throws TuneSdkException, TuneServiceException {
    if (null == response) {
      throw new IllegalArgumentException(
        "Parameter 'response' is not defined."
      );
    }

    JSONObject jdata = (JSONObject) response.getData();
    if (null == jdata) {
      throw new TuneServiceException(
       "Report export response failed to get data."
      );
    }

    if (!jdata.has("url")) {
      throw new TuneSdkException(
        String.format(
          "Export data does not contain report 'url', response: %s",
          response.toString()
        )
      );
    }

    String reportUrl = null;
    try {
      reportUrl = jdata.getString("url");
    } catch (JSONException ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    } catch (Exception ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    }

    if ((null == reportUrl) || reportUrl.isEmpty()) {
      throw new TuneSdkException(
        String.format(
          "Export response 'url' is not defined, response: %s",
          response.toString()
        )
      );
    }

    return reportUrl;
  }
}
