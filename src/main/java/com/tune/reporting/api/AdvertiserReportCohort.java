package com.tune.reporting.api;

/**
 * AdvertiserReportCohort.java
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

import com.tune.reporting.base.endpoints.AdvertiserReportInsightBase;
import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneManagementResponse;
import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.helpers.TuneServiceException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Tune Management API endpoint 'advertiser/stats/ltv'.
 */
public class AdvertiserReportCohort extends AdvertiserReportInsightBase {

  /**
   * Constructor.
   *
   * @param apiKey       Tune MobileAppTracking API Key.
   * @param validateFields   Validate fields used by actions' parameters.
   */
  public AdvertiserReportCohort(
      final String apiKey,
      final Boolean validateFields
 ) {
    super(
        "advertiser/stats/ltv",
        apiKey,
        false,
        true,
        validateFields
   );

    /*
     * Fields recommended in suggested order.
     */
    this.setFieldsRecommended = new HashSet<String>(Arrays.asList(
      "site_id",
      "site.name",
      "publisher_id",
      "publisher.name",
      "rpi",
      "epi"
   ));
  }

  /**
   * Finds all existing records that match filter criteria
   * and returns an array of found model data.
   *
   * @param startDate       YYYY-MM-DD HH:MM:SS
   * @param endDate         YYYY-MM-DD HH:MM:SS
   * @param cohortType      Cohort types: click, install
   * @param cohortInterval   Cohort intervals:
   *                        year_day, year_week, year_month, year.
   * @param aggregationType  Aggregation types:
   *                        cumulative, incremental.
   * @param fields          Present results using these endpoint's fields.
   * @param group           Group results using this endpoint's fields.
   * @param filter          Apply constraints based upon values associated with
   *                        this endpoint's fields.
   * @param limit           Limit number of results, default 10, 0 shows all
   * @param page            Pagination, default 1.
   * @param sort            Sort results using this endpoint's fields.
   *                        Directions: DESC, ASC
   * @param responseTimezone  Setting expected timezone for results,
   *                          default is set in account.
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   * @throws IllegalArgumentException If invalid value is provided
   * to a parameter
   */
  public final TuneManagementResponse find(
      final String startDate,
      final String endDate,
      final String cohortType,
      final String cohortInterval,
      final String aggregationType,
      String fields,
      String group,
      String filter,
      final int limit,
      final int page,
      final Map<String, String> sort,
      final String responseTimezone
 ) throws  TuneSdkException,
            TuneServiceException,
            IllegalArgumentException {
    EndpointBase.validateDateTime("start_date", startDate);
    EndpointBase.validateDateTime("end_date", endDate);

    AdvertiserReportInsightBase.validateCohortType(cohortType);
    AdvertiserReportInsightBase.validateCohortInterval(cohortInterval);
    AdvertiserReportInsightBase.validateAggregationTypes(aggregationType);

    if ((null != group) && !group.isEmpty()) {
      group = super.validateGroup(group);
    }
    if ((null != filter) && !filter.isEmpty()) {
      filter = super.validateFilter(filter);
    }

    String strSort = null;
    if ((null != sort) && !sort.isEmpty()) {
      Set<String> setFields = null;
      if ((null != fields) && !fields.isEmpty()) {
        setFields = EndpointBase.explode(fields, "\\,");
      }
      strSort = super.validateSort(setFields, sort);

      if ((null != setFields) && !setFields.isEmpty()) {
        fields = EndpointBase.implode(setFields, ",");
      }
    }

    if ((null != fields) && !fields.isEmpty()) {
      fields = super.validateFields(fields);
    }

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("start_date", startDate);
    mapQueryString.put("end_date", endDate);
    mapQueryString.put("cohort_type", cohortType);
    mapQueryString.put("interval", cohortInterval);
    mapQueryString.put("aggregation_type", aggregationType);
    mapQueryString.put("fields", fields);
    mapQueryString.put("group", group);
    mapQueryString.put("filter", filter);
    mapQueryString.put("limit", Integer.toString(limit));
    mapQueryString.put("page", Integer.toString(page));
    mapQueryString.put("sort", strSort);
    mapQueryString.put("response_timezone", responseTimezone);

    return super.callRecords(
      "find",
      mapQueryString
   );
  }

  /**
   * Places a job into a queue to generate a report that will contain
   * records that match provided filter criteria, and it returns a job
   * identifier to be provided to action /export/download.json to download
   * completed report.
   *
   * @param startDate       YYYY-MM-DD HH:MM:SS
   * @param endDate         YYYY-MM-DD HH:MM:SS
   * @param cohortType      Cohort types: click, install.
   * @param cohortInterval   Cohort intervals:
   *                          year_day, year_week, year_month, year.
   * @param aggregationType  Aggregation types: cumulative, incremental.
   * @param fields      Present results using these endpoint's fields.
   * @param group       Group results using this endpoint's fields.
   * @param filter      Apply constraints based upon values associated with
   *                    this endpoint's fields.
   * @param responseTimezone  Setting expected timezone for results,
   *                          default is set in account.
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   * @throws IllegalArgumentException If invalid value
   * is provided to a parameter
   */
  public final TuneManagementResponse export(
      final String startDate,
      final String endDate,
      final String cohortType,
      final String cohortInterval,
      final String aggregationType,
      String fields,
      String group,
      String filter,
      final String responseTimezone
 ) throws  TuneSdkException,
            TuneServiceException,
            IllegalArgumentException {
    EndpointBase.validateDateTime("start_date", startDate);
    EndpointBase.validateDateTime("end_date", endDate);

    AdvertiserReportInsightBase.validateCohortType(cohortType);
    AdvertiserReportInsightBase.validateCohortInterval(cohortInterval);
    AdvertiserReportInsightBase.validateAggregationTypes(aggregationType);

    if ((null != group) && !group.isEmpty()) {
      group = super.validateGroup(group);
    }
    if ((null != filter) && !filter.isEmpty()) {
      filter = super.validateFilter(filter);
    }
    if ((null != fields) && !fields.isEmpty()) {
      fields = super.validateFields(fields);
    }

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("start_date", startDate);
    mapQueryString.put("end_date", endDate);
    mapQueryString.put("cohort_type", cohortType);
    mapQueryString.put("interval", cohortInterval);
    mapQueryString.put("aggregation_type", aggregationType);
    mapQueryString.put("fields", fields);
    mapQueryString.put("group", group);
    mapQueryString.put("filter", filter);
    mapQueryString.put("filter", filter);
    mapQueryString.put("response_timezone", responseTimezone);

    return super.callRecords(
      "export",
      mapQueryString
   );
  }

  /**
   * Helper function for fetching report document given provided job identifier.
   *
   * @param jobId       Job Identifier of report on queue.
   * @param verbose     For debugging purposes only.
   * @param sleep       How long worker should sleep before
   *                    next status request.
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   * @throws IllegalArgumentException If invalid value
   * is provided to a parameter.
   */
  public final TuneManagementResponse fetch(
      final String jobId,
      final Boolean verbose,
      final int sleep
 ) throws IllegalArgumentException, TuneServiceException, TuneSdkException {
    return super.fetchRecords(
      this.controller,
      "status",
      jobId,
      verbose,
      sleep
   );
  }
}
