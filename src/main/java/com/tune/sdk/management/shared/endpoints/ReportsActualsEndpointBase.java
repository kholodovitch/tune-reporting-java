/**
 * ReportsActualsEndpointBase.java
 *
 * Copyright (c) 2014 Tune, Inc
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * Java Version 1.6
 *
 * @category  Tune
 * @package   tune.management.shared.endpoints
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-19 21:21:08 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk.management.shared.endpoints;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import com.tune.sdk.shared.*;
import com.tune.sdk.management.shared.service.*;


/**
 * Base class for Tune Mangement API actuals endpoints.
 */
public class ReportsActualsEndpointBase extends ReportsEndpointBase {


    protected static final Set<String> timestamps = new HashSet<String>(Arrays.asList(
        "hour",
        "datehour",
        "date",
        "week",
        "month"
    ));

    /**
     * Constructor
     *
     * @param controller                Tune Management API endpoint name.
     * @param api_key                   Tune MobileAppTracking API Key.
     * @param filter_debug_mode         Remove debug mode information from results.
     * @param filter_test_profile_id    Remove test profile information from results.
     * @param validate_fields           Validate fields used by actions' parameters.
     */
    public ReportsActualsEndpointBase(
        String controller,
        String api_key,
        Boolean filter_debug_mode,
        Boolean filter_test_profile_id,
        Boolean validate_fields
    ) {
        super(
            controller,
            api_key,
            filter_debug_mode,
            filter_test_profile_id,
            validate_fields
        );
    }

    /**
     * Counts all existing records that match filter criteria
     * and returns an array of found model data.
     *
     * @param start_date        YYYY-MM-DD HH:MM:SS
     * @param end_date          YYYY-MM-DD HH:MM:SS
     * @param filter            Filter the results and apply conditions
     *                                  that must be met for records to be included in data.
     * @param response_timezone Setting expected time for data
     * @throws Exception
     */
    public TuneManagementResponse count(
        String start_date,
        String end_date,
        String group,
        String filter,
        String response_timezone
    ) throws Exception {
        EndpointBase.validateDateTime("start_date", start_date);
        EndpointBase.validateDateTime("end_date", end_date);

        if ((null != filter) && !filter.isEmpty()) {
            filter = super.validateFilter(filter);
        }
        if ((null != group) && !group.isEmpty()) {
            group = super.validateGroup(group);
        }

        Map<String, String> query_string_dict = new HashMap<String, String>();
        query_string_dict.put("start_date", start_date);
        query_string_dict.put("end_date", end_date);
        query_string_dict.put("group", group);
        query_string_dict.put("filter", filter);
        query_string_dict.put("response_timezone", response_timezone);

        return super.callRecords(
            "count",
            query_string_dict
        );
    }

    /**
     * Finds all existing records that match filter criteria
     * and returns an array of found model data.
     *
     * @param start_date        YYYY-MM-DD HH:MM:SS
     * @param end_date          YYYY-MM-DD HH:MM:SS
     * @param fields            No value returns default fields, "*" returns all
     *                                  available fields, or provide specific fields.
     * @param group             Group results using this endpoint's fields.
     * @param filter            Filter the results and apply conditions that
     *                                  must be met for records to be included in data.
     * @param limit            Limit number of results, default 10, 0 shows all.
     * @param page             Pagination, default 1.
     * @param sort               Sort by field name, ASC (default) or DESC
     * @param timestamp         Set to breakdown stats by timestamp choices:
     *                                  hour, datehour, date, week, month.
     * @param response_timezone Setting expected timezone for data. Default is set by account.
     *
     * @return object
     * @throws Exception
     */
    public TuneManagementResponse find(
        String start_date,
        String end_date,
        String fields,
        String group,
        String filter,
        int limit,
        int page,
        Map<String, String> sort,
        String timestamp,
        String response_timezone
    ) throws Exception {
        EndpointBase.validateDateTime("start_date", start_date);
        EndpointBase.validateDateTime("end_date", end_date);

        if ((null != group) && !group.isEmpty()) {
            group = super.validateGroup(group);
        }
        if ((null != filter) && !filter.isEmpty()) {
            filter = super.validateFilter(filter);
        }

        String sort_str = null;
        if ((null != sort) && !sort.isEmpty()) {

            Set<String> fields_set = null;
            if ((null != fields) && !fields.isEmpty()) {
                fields_set = EndpointBase.explode(fields, "\\,");
            }
            sort_str = super.validateSort(fields_set, sort);

            if ((null != fields_set) && !fields_set.isEmpty()) {
                fields = EndpointBase.implode(fields_set, ",");
            }
        }

        if ((null != fields) && !fields.isEmpty()) {
            fields = super.validateFields(fields);
        }

        // timestamp
        if ((null != timestamp) && !timestamp.isEmpty()) {
            if (!ReportsActualsEndpointBase.timestamps.contains(timestamp)) {
                 throw new IllegalArgumentException(
                         String.format("Parameter 'timestamp' iis invalid: '%s'.", timestamp)
                 );
            }
        }

        Map<String, String> query_string_dict = new HashMap<String, String>();
        query_string_dict.put("start_date", start_date);
        query_string_dict.put("end_date", end_date);
        query_string_dict.put("fields", fields);
        query_string_dict.put("group", group);
        query_string_dict.put("filter", filter);
        query_string_dict.put("limit", Integer.toString(limit));
        query_string_dict.put("page", Integer.toString(page));
        query_string_dict.put("sort", sort_str);
        query_string_dict.put("timestamp", timestamp);
        query_string_dict.put("response_timezone", response_timezone);

        return super.callRecords(
            "find",
            query_string_dict
        );
    }

    /**
     * Places a job into a queue to generate a report that will contain
     * records that match provided filter criteria, and it returns a job
     * identifier to be provided to action /export/download.json to download
     * completed report.
     *
     * @param start_date        YYYY-MM-DD HH:MM:SS
     * @param end_date          YYYY-MM-DD HH:MM:SS
     * @param fields            No value returns default fields, "*" returns all
     *                                  available fields, or provide specific fields.
     * @param group             Group results using this endpoint's fields.
     * @param filter            Filter the results and apply conditions that
     *                                  must be met for records to be included in data.
     * @param timestamp         Set to breakdown stats by timestamp choices:
     *                                  hour, datehour, date, week, month.
     * @param format            Export format for downloaded report: json, csv.
     * @param response_timezone Setting expected timezone for data. Default is set by account.
     *
     * @return object
     * @throws Exception
     */
    public TuneManagementResponse export(
        String start_date,
        String end_date,
        String fields,
        String group,
        String filter,
        String timestamp,
        String format,
        String response_timezone
    ) throws Exception {
        EndpointBase.validateDateTime("start_date", start_date);
        EndpointBase.validateDateTime("end_date", end_date);

        if ((null != group) && !group.isEmpty()) {
            group = super.validateGroup(group);
        }
        if ((null != filter) && !filter.isEmpty()) {
            filter = super.validateFilter(filter);
        }
        if ((null != fields) && !fields.isEmpty()) {
            fields = super.validateFields(fields);
        }
        // timestamp
        if ((null != timestamp) && !timestamp.isEmpty()) {
            if (!ReportsActualsEndpointBase.timestamps.contains(timestamp)) {
                 throw new IllegalArgumentException(
                         String.format("Parameter 'timestamp' is invalid: '%s'.", timestamp)
                 );
            }
        }
        if ((null == format) || format.isEmpty()) {
            format = "csv";
        }
        if (!EndpointBase.report_export_formats.contains(format)) {
            throw new IllegalArgumentException(
                 String.format("Parameter 'format' is invalid: '%s'.", format)
            );
        }
        if ( format.equals("csv") && ((null == fields) || fields.isEmpty())) {
            throw new IllegalArgumentException(
                     String.format("Parameter 'fields' needs to be defined if report format is: '%s'.", format)
               );
        }

        Map<String, String> query_string_dict = new HashMap<String, String>();
        query_string_dict.put("start_date", start_date);
        query_string_dict.put("end_date", end_date);
        query_string_dict.put("fields", fields);
        query_string_dict.put("group", group);
        query_string_dict.put("filter", filter);
        query_string_dict.put("timestamp", timestamp);
        query_string_dict.put("format", format);
        query_string_dict.put("response_timezone", response_timezone);

        return super.callRecords(
            "find_export_queue",
            query_string_dict
        );
    }

    /**
     * Query status of insight reports. Upon completion will
     * return url to download requested report.
     *
     * @param job_id    Provided Job Identifier to reference
     *                          requested report on export queue.
     * @throws TuneSdkException
     */
    public TuneManagementResponse status(
        String job_id
    ) throws TuneSdkException {
        if ( (null == job_id) || job_id.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'job_id' is not defined.");
        }
        if ( (null == this.api_key) || this.api_key.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'api_key' is not defined.");
        }

        Map<String, String> query_string_dict = new HashMap<String, String>();
        query_string_dict.put("job_id", job_id);

        TuneManagementClient client = new TuneManagementClient(
            "export",
            "download",
            this.api_key,
            query_string_dict
        );

        client.call();

        return client.getResponse();
    }

    /**
     * Helper function for fetching report upon completion.
     *
     * @param job_id        Job identifier assigned for report export.
     * @param verbose       For debug purposes to monitor job export completion status.
     * @param sleep         Polling delay for checking job completion status.
     *
     * @return null
     * @throws TuneSdkException
     * @throws TuneServiceException
     */
    public TuneManagementResponse fetch(
        String job_id,
        Boolean verbose,
        int sleep
    ) throws TuneServiceException, TuneSdkException {
        if ( (null == job_id) || job_id.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'job_id' is not defined.");
        }
        return super.fetchRecords(
            "export",
            "download",
            job_id,
            verbose,
            sleep
        );
    }
}