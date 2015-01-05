package com.tune.reporting.base.endpoints;

/**
 * AdvertiserReportLogBase.java
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

import com.tune.reporting.base.service.TuneManagementClient;
import com.tune.reporting.base.service.TuneManagementResponse;

import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.helpers.TuneServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Base class for TUNE Management API reports logs endpoints.
 */
public class AdvertiserReportLogBase extends AdvertiserReportBase {
  /**
   * Constructor.
   *
   * @param controller          TUNE Management API endpoint name.
   * @param filterDebugMode     Remove debug mode information from results.
   * @param filterTestProfileId Remove test profile information from results.
   */
  public AdvertiserReportLogBase(
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
   * @param filter      Filter the results and apply conditions
   *                  that must be met for records to be included in data.
   * @param responseTimezone Setting expected time for data
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final TuneManagementResponse count(
      final String startDate,
      final String endDate,
      final String filter,
      final String responseTimezone
  ) throws  TuneSdkException,
            TuneServiceException {
    EndpointBase.validateDateTime("start_date", startDate);
    EndpointBase.validateDateTime("end_date", endDate);

    String filterV = null;

    if ((null != filter) && !filter.isEmpty()) {
      filterV = super.validateFilter(filter);
    }

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("start_date", startDate);
    mapQueryString.put("end_date", endDate);
    mapQueryString.put("filter", filterV);
    mapQueryString.put("response_timezone", responseTimezone);

    return super.callRecords(
      "count",
      mapQueryString
   );
  }

  /**
   * Finds all existing records that match filter criteria
   * and returns an array of found model data.
   *
   * @param startDate      YYYY-MM-DD HH:MM:SS
   * @param endDate        YYYY-MM-DD HH:MM:SS
   * @param fields        No value returns default fields, "*"
   *                    returns all available fields,
   *                    or provide specific fields.
   * @param filter        Filter the results and apply conditions
   *                    that must be met for records to be
   *                    included in data.
   * @param limit         Limit number of results, default 10,
   *                    0 shows all.
   * @param page          Pagination, default 1.
   * @param sort          Expression defining sorting found
   *                    records in result set base upon provided
   *                    fields and its modifier (ASC or DESC).
   * @param responseTimezone   Setting expected timezone for results,
   *                    default is set in account.
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final TuneManagementResponse find(
      final String startDate,
      final String endDate,
      final String fields,
      final String filter,
      final int limit,
      final int page,
      final Map<String, String> sort,
      final String responseTimezone
  ) throws  TuneSdkException,
            TuneServiceException {
    EndpointBase.validateDateTime("start_date", startDate);
    EndpointBase.validateDateTime("end_date", endDate);

    String fieldsV = null;
    String filterV = null;

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
    mapQueryString.put("fields", fieldsV);
    mapQueryString.put("filter", filterV);
    mapQueryString.put("limit", Integer.toString(limit));
    mapQueryString.put("page", Integer.toString(page));
    mapQueryString.put("sort", strSort);
    mapQueryString.put("response_timezone", responseTimezone);

    return super.call(
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
   * @param startDate      YYYY-MM-DD HH:MM:SS
   * @param endDate        YYYY-MM-DD HH:MM:SS
   * @param fields        Provide fields if format is 'csv'.
   * @param filter        Filter the results and apply conditions
   *                    that must be met for records to be
   *                    included in data.
   * @param format        Export format: csv, json
   * @param responseTimezone   Setting expected timezone for results,
   *                    default is set in account.
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final TuneManagementResponse export(
      final String startDate,
      final String endDate,
      final String fields,
      final String filter,
      final String format,
      final String responseTimezone
  ) throws  TuneSdkException,
            TuneServiceException {
    EndpointBase.validateDateTime("start_date", startDate);
    EndpointBase.validateDateTime("end_date", endDate);

    String fieldsV = null;
    String filterV = null;

    if ((null != filter) && !filter.isEmpty()) {
      filterV = super.validateFilter(filter);
    }
    if ((null != fields) && !fields.isEmpty()) {
      fieldsV = super.validateFields(fields);
    }

    if (!EndpointBase.REPORT_EXPORT_FORMATS.contains(format)) {
      throw new IllegalArgumentException(
      String.format("Parameter 'format' is invalid: '%s'.", format)
     );
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
    mapQueryString.put("fields", fieldsV);
    mapQueryString.put("filter", filterV);
    mapQueryString.put("format", format);
    mapQueryString.put("response_timezone", responseTimezone);

    return super.callRecords(
      "find_export_queue",
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
      throw new IllegalArgumentException("Parameter 'jobId' is not defined.");
    }
    if ((null == this.getApiKey()) || this.getApiKey().isEmpty()) {
      throw new IllegalArgumentException("Parameter 'apiKey' is not defined.");
    }

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("job_id", jobId);

    TuneManagementClient client = new TuneManagementClient(
        "export",
        "download",
        this.getApiKey(),
        mapQueryString
    );

    client.call();

    return client.getResponse();
  }

  /**
   * Helper function for fetching report upon completion.
   *
   * @param jobId       Job identifier assigned for report export.
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException     If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final TuneManagementResponse fetch(
      final String jobId
  ) throws TuneServiceException, TuneSdkException {
    if ((null == jobId) || jobId.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'jobId' is not defined.");
    }
    return super.fetchRecords(
      "export",
      "download",
      jobId
    );
  }
}
