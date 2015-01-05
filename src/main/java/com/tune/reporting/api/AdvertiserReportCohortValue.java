package com.tune.reporting.api;

/**
 * AdvertiserReportCohortValue.java
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
 * @version   $Date: 2015-01-05 09:40:09 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.base.endpoints.AdvertiserReportCohortBase;
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
 * TUNE Management API endpoint 'advertiser/stats/ltv'.
 */
public class AdvertiserReportCohortValue extends AdvertiserReportCohortBase {

  /**
   * Constructor.
   *
   */
  public AdvertiserReportCohortValue() throws TuneSdkException {
    super(
      "advertiser/stats/ltv",
      false,
      true
    );

    /*
     * Fields recommended in suggested order.
     */
    this.setFieldsRecommended(new HashSet<String>(Arrays.asList(
        "site_id",
        "site.name",
        "publisher_id",
        "publisher.name",
        "rpi",
        "epi"
    )));
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
   */
  public final TuneManagementResponse find(
      final String startDate,
      final String endDate,
      final String cohortType,
      final String cohortInterval,
      final String aggregationType,
      final String fields,
      final String group,
      final String filter,
      final int limit,
      final int page,
      final Map<String, String> sort,
      final String responseTimezone
  ) throws  TuneSdkException,
            TuneServiceException {
    EndpointBase.validateDateTime("start_date", startDate);
    EndpointBase.validateDateTime("end_date", endDate);

    AdvertiserReportCohortBase.validateCohortType(cohortType);
    AdvertiserReportCohortBase.validateCohortInterval(cohortInterval);

    String fieldsV = null;
    String groupV = null;
    String filterV = null;

    if ((null != group) && !group.isEmpty()) {
      groupV = super.validateGroup(group);
    }
    if ((null != filter) && !filter.isEmpty()) {
      filterV = super.validateFilter(filter);
    }
    if ((null != fields) && !fields.isEmpty()) {
      fieldsV = super.validateFields(fields);
    }

    String strSort = null;
    if ((null != sort) && !sort.isEmpty()) {
      Set<String> setFields = null;
      if ((null != fields) && !fields.isEmpty()) {
        setFields = EndpointBase.explode(fields, "\\,");
      }
      strSort = super.validateSort(setFields, sort);

      if ((null != setFields) && !setFields.isEmpty()) {
        fieldsV = EndpointBase.implode(setFields, ",");
      }
    }

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("start_date", startDate);
    mapQueryString.put("end_date", endDate);
    mapQueryString.put("cohort_type", cohortType);
    mapQueryString.put("interval", cohortInterval);
    mapQueryString.put("aggregation_type", aggregationType);
    mapQueryString.put("fields", fields);
    mapQueryString.put("group", groupV);
    mapQueryString.put("filter", filterV);
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
   */
  public final TuneManagementResponse export(
      final String startDate,
      final String endDate,
      final String cohortType,
      final String cohortInterval,
      final String aggregationType,
      final String fields,
      final String group,
      final String filter,
      final String responseTimezone
  ) throws  TuneSdkException,
            TuneServiceException {
    EndpointBase.validateDateTime("start_date", startDate);
    EndpointBase.validateDateTime("end_date", endDate);

    AdvertiserReportCohortBase.validateCohortType(cohortType);
    AdvertiserReportCohortBase.validateCohortInterval(cohortInterval);
    AdvertiserReportCohortBase.validateAggregationTypes(aggregationType);

    String fieldsV = null;
    String groupV = null;
    String filterV = null;

    if ((null != group) && !group.isEmpty()) {
      groupV = super.validateGroup(group);
    }
    if ((null != filter) && !filter.isEmpty()) {
      filterV = super.validateFilter(filter);
    }
    if ((null != fields) && !fields.isEmpty()) {
      fieldsV = super.validateFields(fields);
    }

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("start_date", startDate);
    mapQueryString.put("end_date", endDate);
    mapQueryString.put("cohort_type", cohortType);
    mapQueryString.put("interval", cohortInterval);
    mapQueryString.put("aggregation_type", aggregationType);
    mapQueryString.put("fields", fieldsV);
    mapQueryString.put("group", groupV);
    mapQueryString.put("filter", filterV);
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
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final TuneManagementResponse fetch(
      final String jobId
  ) throws TuneServiceException, TuneSdkException {
    return super.fetchRecords(
      this.getController(),
      "status",
      jobId
    );
  }
}
