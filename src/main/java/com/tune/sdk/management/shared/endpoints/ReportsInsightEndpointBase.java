package com.tune.sdk.management.shared.endpoints;

/**
 * ReportsInsightEndpointBase.java
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
 * @package   com.tune.sdk.management.shared.endpoints
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-24 09:34:47 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import org.json.JSONObject;
import org.json.JSONException;

import com.tune.sdk.shared.*;
import com.tune.sdk.management.shared.service.*;

/**
 * Base class for Tune Mangement API reports insights endpoints.
 */
public class ReportsInsightEndpointBase extends ReportsEndpointBase {

    /**
     * Allowed Cohort interval values.
     */
    protected static final Set<String> cohort_intervals
        = new HashSet<String>(Arrays.asList(
            "year_day",
            "year_week",
            "year_month",
            "year"
        ));

    /**
     * Allowed Cohort types values.
     */
    protected static final Set<String> cohort_types
        = new HashSet<String>(Arrays.asList(
            "click",
            "install"
        ));

    /**
     * Allowed aggregation types values.
     */
    protected static final Set<String> aggregation_types
        = new HashSet<String>(Arrays.asList(
            "incremental",
            "cumulative"
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
    public ReportsInsightEndpointBase(
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
     * @param cohort_type       Cohort types: click, install
     * @param group             Group results using this endpoint's fields.
     * @param cohort_interval   Cohort intervals: year_day, year_week, year_month, year
     * @param filter            Apply constraints based upon values associated with
     *                                  this endpoint's fields.
     * @param response_timezone Setting expected timezone for results,
     *                                  default is set in account.
     *
     * @return object
     * @throws Exception
     */
    public TuneManagementResponse count(
        String start_date,
        String end_date,
        String cohort_type,
        String cohort_interval,
        String group,
        String filter,
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

        Map<String, String> query_string_dict = new HashMap<String, String>();
        query_string_dict.put("start_date", start_date);
        query_string_dict.put("end_date", end_date);
        query_string_dict.put("cohort_type", cohort_type);
        query_string_dict.put("interval", cohort_interval);
        query_string_dict.put("group", group);
        query_string_dict.put("filter", filter);
        query_string_dict.put("response_timezone", response_timezone);

        return super.callRecords(
            "count",
            query_string_dict
        );
    }

    /**
     * Query status of insight reports. Upon completion will
     * return url to download requested report.
     *
     * @param job_id    Provided Job Identifier to reference
     *                          requested report on export queue.
     * @throws Exception
     */
    public TuneManagementResponse status(
        String job_id
    ) throws Exception {
        if ( (null == job_id) || job_id.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'job_id' is not defined.");
        }
        if ( (null == this.api_key) || this.api_key.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'api_key' is not defined.");
        }

        Map<String, String> query_string_dict = new HashMap<String, String>();
        query_string_dict.put("job_id", job_id);

        return super.call(
            "status",
            query_string_dict
        );
    }

    /**
     * Validate value is expected cohort type.
     *
     * @param cohort_type
     * @return
     * @throws IllegalArgumentException
     */
    public static Boolean validateCohortType(String cohort_type)
    		throws IllegalArgumentException
    {
        if ((null == cohort_type) || cohort_type.isEmpty()) {
            throw new IllegalArgumentException(
                "Parameter 'cohort_type' is not defined."
            );
        }

        if (!ReportsInsightEndpointBase.cohort_types.contains(cohort_type)) {
            throw new IllegalArgumentException(
                String.format("Parameter 'cohort_type' is invalid: '%s'.", cohort_type)
            );
        }

        return true;
    }

    /**
     * Validate value is expected cohort interval.
     *
     * @param cohort_interval
     * @return
     * @throws IllegalArgumentException
     */
    public static Boolean validateCohortInterval(String cohort_interval)
    		throws IllegalArgumentException
    {
        if ((null == cohort_interval) || cohort_interval.isEmpty()) {
            throw new IllegalArgumentException(
                "Parameter 'cohort_interval' is not defined."
            );
        }

        if (!ReportsInsightEndpointBase.cohort_intervals.contains(cohort_interval)) {
            throw new IllegalArgumentException(
                    String.format("Parameter 'cohort_interval' is invalid: '%s'.", cohort_interval)
             );
        }

        return true;
    }

    /**
     * Validate value is valid aggregation type.
     *
     * @param aggregation_type
     * @return
     * @throws IllegalArgumentException
     */
    public static Boolean validateAggregationTypes(String aggregation_type)
    		throws IllegalArgumentException
    {
        if ((null == aggregation_type) || aggregation_type.isEmpty()) {
            throw new IllegalArgumentException(
                "Parameter 'aggregation_type' is not defined."
            );
        }

        if (!ReportsInsightEndpointBase.aggregation_types.contains(aggregation_type)) {
            throw new IllegalArgumentException(
                    String.format("Parameter 'aggregation_type' is invalid: '%s'.", aggregation_type)
             );
        }

        return true;
    }

    /**
     * Parse response and gather job identifier.
     *
     * @param response
     * @return
     * @throws TuneServiceException
     * @throws TuneSdkException
     */
    public static String parseResponseReportJobId(
        TuneManagementResponse response
    ) throws TuneServiceException, TuneSdkException {
        if (null == response) {
            throw new IllegalArgumentException("Parameter 'response' is not defined.");
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
                    "Export data does not contain report 'job_id', response: %s",
                    response.toString()
                )
            );
        }

        String report_job_id = null;
        try {
            report_job_id = jdata.getString("job_id");
        } catch (JSONException ex) {
            throw new TuneSdkException(ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new TuneSdkException(ex.getMessage(), ex);
        }

        if ((null == report_job_id) || report_job_id.isEmpty()) {
            throw new TuneSdkException(
                String.format(
                    "Export response 'job_id' is not defined, response: %s",
                    response.toString()
                )
            );
        }

        return report_job_id;
    }

    /**
     * Parse response and gather report url.
     *
     * @param response
     * @return
     * @throws TuneSdkException
     * @throws TuneServiceException
     */
    public static String parseResponseReportUrl(
        TuneManagementResponse response
    ) throws TuneSdkException, TuneServiceException {

        if (null == response) {
            throw new IllegalArgumentException("Parameter 'response' is not defined.");
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

        String report_url = null;
        try {
            report_url = jdata.getString("url");
        } catch (JSONException ex) {
            throw new TuneSdkException(ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new TuneSdkException(ex.getMessage(), ex);
        }

        if ((null == report_url) || report_url.isEmpty()) {
            throw new TuneSdkException(
                String.format(
                    "Export response 'url' is not defined, response: %s",
                    response.toString()
                )
            );
        }

        return report_url;
    }
}