package com.tune.sdk.management.api.advertiser.stats;

/**
 * Retention.java
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
 * @package   com.tune.sdk.management.api.advertiser.stats
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-24 09:34:47 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import com.tune.sdk.management.shared.endpoints.*;
import com.tune.sdk.management.shared.service.TuneManagementResponse;
import com.tune.sdk.shared.TuneSdkException;
import com.tune.sdk.shared.TuneServiceException;


/**
 * Tune Management API endpoint 'advertiser/stats/retention'
 *
 * @example ExampleReportRetention.java
 */
public class Retention extends ReportsInsightEndpointBase {

    /**
     * Constructor
     *
     * @param api_key           Tune MobileAppTracking API Key.
     * @param validate_fields   Validate fields used by actions' parameters.
     */
    public Retention(
        String api_key,
        Boolean validate_fields
    ) {
        super(
            "advertiser/stats/retention",
            api_key,
            false,
            true,
            validate_fields
        );

        /*
         * Fields recommended in suggested order.
         */
        this.set_fields_recommended = new HashSet<String>(Arrays.asList(
             "site_id"
            ,"site.name"
            ,"install_publisher_id"
            ,"install_publisher.name"
            ,"installs"
            ,"opens"
        ));
    }

    /**
     * Finds all existing records that match filter criteria
     * and returns an array of found model data.
     *
     * @param start_date        YYYY-MM-DD HH:MM:SS
     * @param end_date          YYYY-MM-DD HH:MM:SS
     * @param cohort_type       Cohort types: click, install
     * @param cohort_interval   Cohort intervals: year_day, year_week, year_month, year
     * @param fields            Present results using these endpoint's fields.
     * @param group             Group results using this endpoint's fields.
     * @param filter            Apply constraints based upon values associated with
     *                                  this endpoint's fields.
     * @param limit             Limit number of results, default 10, 0 shows all
     * @param page              Pagination, default 1.
     * @param sort              Sort results using this endpoint's fields. Directions: DESC, ASC
     * @param response_timezone Setting expected timezone for results,
     *                                  default is set in account.
     *
     * @return object
     * @throws Exception
     */
    public TuneManagementResponse find(
            String start_date,
            String end_date,
            String cohort_type,
            String cohort_interval,
            String fields,
            String group,
            String filter,
            int limit,
            int page,
            Map<String, String> sort,
            String response_timezone
    ) throws Exception {
        EndpointBase.validateDateTime("start_date", start_date);
        EndpointBase.validateDateTime("end_date", end_date);

        ReportsInsightEndpointBase.validateCohortType(cohort_type);
        ReportsInsightEndpointBase.validateCohortInterval(cohort_interval);

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

        Map<String, String> query_string_dict = new HashMap<String, String>();
        query_string_dict.put("start_date", start_date);
        query_string_dict.put("end_date", end_date);
        query_string_dict.put("cohort_type", cohort_type);
        query_string_dict.put("interval", cohort_interval);
        query_string_dict.put("fields", fields);
        query_string_dict.put("group", group);
        query_string_dict.put("filter", filter);
        query_string_dict.put("limit", Integer.toString(limit));
        query_string_dict.put("page", Integer.toString(page));
        query_string_dict.put("sort", sort_str);
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
     * @param cohort_type       Cohort types: click, install
     * @param cohort_interval   Cohort intervals: year_day, year_week, year_month, year.
     * @param fields            Present results using these endpoint's fields.
     * @param group             Group results using this endpoint's fields.
     * @param filter            Apply constraints based upon values associated with
     *                                  this endpoint's fields.
     * @param response_timezone Setting expected timezone for results,
     *                                  default is set in account.
     *
     * @return object
     * @throws Exception
     * @throws \InvalidArgumentException
     */
    public TuneManagementResponse export(
        String start_date,
        String end_date,
        String cohort_type,
        String cohort_interval,
        String fields,
        String group,
        String filter,
        String response_timezone
    ) throws Exception {
        EndpointBase.validateDateTime("start_date", start_date);
        EndpointBase.validateDateTime("end_date", end_date);

        ReportsInsightEndpointBase.validateCohortType(cohort_type);
        ReportsInsightEndpointBase.validateCohortInterval(cohort_interval);

        if ((null != fields) && !fields.isEmpty()) {
            fields = super.validateFields(fields);
        }
        if ((null != group) && !group.isEmpty()) {
            group = super.validateGroup(group);
        }
        if ((null != filter) && !filter.isEmpty()) {
            filter = super.validateFilter(filter);
        }

        Map<String, String> query_string_dict = new HashMap<String, String>();
        query_string_dict.put("start_date", start_date);
        query_string_dict.put("end_date", end_date);
        query_string_dict.put("cohort_type", cohort_type);
        query_string_dict.put("interval", cohort_interval);
        query_string_dict.put("fields", fields);
        query_string_dict.put("group", group);
        query_string_dict.put("filter", filter);
        query_string_dict.put("response_timezone", response_timezone);

        return super.callRecords(
            "export",
            query_string_dict
        );
    }

    /**
     * Helper function for fetching report document given provided job identifier.
     *
     * @param job_id            Job Identifier of report on queue.
     * @param verbose           For debugging purposes only.
     * @param sleep             How long worker should sleep before
     *                                  next status request.
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
        return super.fetchRecords(
            this.controller,
            "status",
            job_id,
            verbose,
            sleep
        );
    }
}
