package com.tune.reporting.api;

/**
 * AdvertiserReportCohortValues.java
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

import com.tune.reporting.base.endpoints.AdvertiserReportCohortBase;
import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneServiceResponse;
import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.helpers.TuneServiceException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TUNE Service API endpoint 'advertiser/stats/ltv'.
 */
public class AdvertiserReportCohortValues extends AdvertiserReportCohortBase {

  /**
   * Constructor.
   *
   */
  public AdvertiserReportCohortValues(
  ) throws TuneSdkException {
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
   * @param strAuthKey   API Key or Session Token
   * @param strAuthType  "api_key" or "session_token"
   * @param mapParams    Mapping of: <p><dl>
   * <dt>start_date</dt><dd>YYYY-MM-DD HH:MM:SS</dd>
   * <dt>end_date</dt><dd>YYYY-MM-DD HH:MM:SS</dd>
   * <dt>cohort_type</dt><dd>Cohort types: click, install</dd>
   * <dt>cohort_interval</dt><dd>Cohort intervals:
   *                    year_day, year_week, year_month, year</dd>
   * <dt>aggregation_type</dt> <dd>Aggregation types:
   *                        cumulative, incremental.</dd>
   * <dt>fields</dt><dd>Present results using these endpoint's fields.</dd>
   * <dt>group</dt><dd>Group results using this endpoint's fields.
   * <dt>filter</dt><dd>Apply constraints based upon values associated with
   *                    this endpoint's fields.</dd>
   * <dt>limit</dt><dd>Limit number of results, default 10, 0 shows all</dd>
   * <dt>page</dt><dd>Pagination, default 1.</dd>
   * <dt>sort</dt><dd>Sort results using this endpoint's fields.
   *                    Directions: DESC, ASC</dd>
   * <dt>response_timezone</dt><dd>Setting expected timezone for results,
   *                          default is set in account.</dd>
   * </dl><p>
   *
   * @return TuneServiceResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  @SuppressWarnings("unchecked")
  public final TuneServiceResponse find(
    final String strAuthKey,
    final String strAuthType,
    final Map<String, Object> mapParams
  ) throws  TuneSdkException,
            TuneServiceException {

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

    String fieldsV = null;
    String fields = null;
    if (mapParams.containsKey("fields")) {
      fields = (String) mapParams.get("fields");
      if ((null != fields) && !fields.isEmpty()) {
        fieldsV = super.validateFields(strAuthKey, strAuthType, fields);
      }
    }

    String strSort = null;
    if (mapParams.containsKey("sort")) {
      Map<String, String> sort = (HashMap<String, String>) mapParams.get("sort");
      if ((null != sort) && !sort.isEmpty()) {
        Set<String> setFields = null;
        if ((null != fields) && !fields.isEmpty()) {
          setFields = EndpointBase.explode(fields, "\\,");
        }
        strSort = super.validateSort(strAuthKey, strAuthType, setFields, sort);

        if ((null != setFields) && !setFields.isEmpty()) {
          fieldsV = EndpointBase.implode(setFields, ",");
        }
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

    String aggregationType = null;
    if (mapParams.containsKey("aggregation_type")) {
      aggregationType = (String) mapParams.get("aggregation_type");
    }

    Integer limit = 0;
    if (mapParams.containsKey("limit")) {
      limit = (Integer) mapParams.get("limit");
    }

    Integer page = 0;
    if (mapParams.containsKey("page")) {
      page = (Integer) mapParams.get("page");
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

    return super.callService(
      "find",
      strAuthKey,
      strAuthType,
      mapQueryString
    );
  }

  /**
   * Places a job into a queue to generate a report that will contain
   * records that match provided filter criteria, and it returns a job
   * identifier to be provided to action /export/download.json to download
   * completed report.
   *
   * @param strAuthKey   API Key or Session Token
   * @param strAuthType  "api_key" or "session_token"
   * @param mapParams    Mapping of: <p><dl>
   * <dt>start_date</dt><dd>YYYY-MM-DD HH:MM:SS</dd>
   * <dt>end_date</dt><dd>YYYY-MM-DD HH:MM:SS</dd>
   * <dt>cohort_type</dt><dd>Cohort types: click, install</dd>
   * <dt>cohort_interval</dt><dd>Cohort intervals:
   *                    year_day, year_week, year_month, year</dd>
   * <dt>aggregation_type</dt> <dd>Aggregation types:
   *                        cumulative, incremental.</dd>
   * <dt>fields</dt><dd>Present results using these endpoint's fields.</dd>
   * <dt>group</dt><dd>Group results using this endpoint's fields.
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
  public final TuneServiceResponse export(
    final String strAuthKey,
    final String strAuthType,
    final Map<String, Object> mapParams
  ) throws  TuneSdkException,
            TuneServiceException {
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

    String fieldsV = null;
    String fields = null;
    if (mapParams.containsKey("fields")) {
      fields = (String) mapParams.get("fields");
      if ((null != fields) && !fields.isEmpty()) {
        fieldsV = super.validateFields(strAuthKey, strAuthType, fields);
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

    String aggregationType = null;
    if (mapParams.containsKey("aggregation_type")) {
      aggregationType = (String) mapParams.get("aggregation_type");
    }

    Integer limit = 0;
    if (mapParams.containsKey("limit")) {
      limit = (Integer) mapParams.get("limit");
    }

    Integer page = 0;
    if (mapParams.containsKey("page")) {
      page = (Integer) mapParams.get("page");
    }

    String format = "csv";
    if (mapParams.containsKey("format")) {
      if (!EndpointBase.REPORT_EXPORT_FORMATS.contains(format)) {
        throw new IllegalArgumentException(
        String.format("Parameter 'format' is invalid: '%s'.", format)
       );
      }
      format = (String) mapParams.get("format");
    }
    if (format.equals("csv") && ((null == fields) || fields.isEmpty())) {
      throw new IllegalArgumentException(
        String.format(
          "Parameter 'fields' needs to be defined if report format is: '%s'.",
          format
        )
      );
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
    mapQueryString.put("format", format);
    mapQueryString.put("response_timezone", responseTimezone);

    return super.callService(
      "export",
      strAuthKey,
      strAuthType,
      mapQueryString
    );
  }

  /**
   * Helper function for fetching report document given provided job identifier.
   *
   * @param jobId       Job Identifier of report on queue.
   *
   * @return TuneServiceResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final TuneServiceResponse fetch(
    final String strAuthKey,
    final String strAuthType,
    final String jobId
  ) throws TuneServiceException, TuneSdkException {
    return super.fetchRecords(
      strAuthKey,
      strAuthType,
      this.getController(),
      "status",
      jobId
    );
  }
}