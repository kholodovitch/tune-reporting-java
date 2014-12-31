package com.tune.reporting.base.endpoints;

/**
 * AdvertiserReportCohortBase.java
 *
 * <p>
 * Copyright (c) 2014 TUNE, Inc.
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
 * @copyright 2014 TUNE, Inc. (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-12-31 12:27:54 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.base.service.TuneManagementResponse;
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
 * Base class for TUNE Management API reports insights endpoints.
 */
public class AdvertiserReportCohortBase extends AdvertiserReportBase {

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
   * @param controller          TUNE Management API endpoint name.
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
   * @param startDate    YYYY-MM-DD HH:MM:SS
   * @param endDate      YYYY-MM-DD HH:MM:SS
   * @param cohortType     Cohort types: click, install
   * @param group       Group results using this endpoint's fields.
   * @param cohortInterval   Cohort intervals:
   *                      year_day, year_week, year_month, year
   * @param filter      Apply constraints based upon values associated with
   *                  this endpoint's fields.
   * @param responseTimezone Setting expected timezone for results,
   *                  default is set in account.
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final TuneManagementResponse count(
      final String startDate,
      final String endDate,
      final String cohortType,
      final String cohortInterval,
      final String group,
      final String filter,
      final String responseTimezone
  ) throws  TuneSdkException,
          TuneServiceException {
    EndpointBase.validateDateTime("start_date", startDate);
    EndpointBase.validateDateTime("end_date", endDate);

    AdvertiserReportCohortBase.validateCohortType(cohortType);
    AdvertiserReportCohortBase.validateCohortInterval(cohortInterval);

    String groupV = null;
    String filterV = null;

    if ((null != group) && !group.isEmpty()) {
      groupV = super.validateGroup(group);
    }
    if ((null != filter) && !filter.isEmpty()) {
      filterV = super.validateFilter(filter);
    }

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("start_date", startDate);
    mapQueryString.put("end_date", endDate);
    mapQueryString.put("cohort_type", cohortType);
    mapQueryString.put("interval", cohortInterval);
    mapQueryString.put("group", groupV);
    mapQueryString.put("filter", filterV);
    mapQueryString.put("response_timezone", responseTimezone);

    return super.callRecords(
      "count",
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
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   */
  public final TuneManagementResponse status(
      final String jobId
  ) throws TuneSdkException {
    if ((null == jobId) || jobId.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'jobId' is not defined."
      );
    }
    if ((null == this.getApiKey()) || this.getApiKey().isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'apiKey' is not defined."
      );
    }

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("job_id", jobId);

    return super.call(
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
   * @param response @see TuneManagementResponse
   *
   * @return String Report Job Id on Export queue.
   * @throws TuneServiceException If service fails to handle post request.
   * @throws TuneSdkException If fails to post request.
   */
  public static String parseResponseReportJobId(
      final TuneManagementResponse response
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
   * @param response @see TuneManagementResponse
   *
   * @return String Report URL download from Export queue.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public static String parseResponseReportUrl(
      final TuneManagementResponse response
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
