package com.tune.sdk.management.shared.endpoints;

/**
 * ItemsEndpointBase.java
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
 * @version   $Date: 2014-11-21 17:34:43 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import com.tune.sdk.shared.*;
import com.tune.sdk.management.shared.service.*;

/**
 * Base class for Tune Mangement API items endpoints.
 */
public class ItemsEndpointBase extends EndpointBase {

    /**
     * Constructor
     *
     * @param controller        Tune Management API endpoint name.
     * @param api_key           Tune MobileAppTracking API Key.
     * @param validate_fields   Validate fields used by actions' parameters.
     */
    public ItemsEndpointBase(
        String controller,
        String api_key,
        Boolean validate_fields
    ) {
        super(
            controller,
            api_key,
            validate_fields
        );
    }

    /**
     * Counts all existing records that match filter criteria
     * and returns an array of found model data.
     *
     * @param filter    Filter the results and apply conditions
     *                  that must be met for records to be included in data.
     */
    public TuneManagementResponse count(
        String filter
    ) throws Exception
    {
        if ((null != filter) && !filter.isEmpty()) {
            filter = this.validateFilter(filter);
        } else {
            filter = "";
        }

        Map<String,String> query_string_dict = new HashMap<String,String>();
        query_string_dict.put("filter", filter);

        return super.call(
            "count",
            query_string_dict
        );
    }

    /**
     * Finds all existing records that match filter criteria
     * and returns an array of found model data.
     *
     * @param fields                No value returns default fields, "*"
     *                              returns all available fields,
     *                              or provide specific fields.
     * @param filter                Filter the results and apply conditions
     *                              that must be met for records to be
     *                              included in data.
     * @param limit                 Limit number of results, default 10,
     *                              0 shows all.
     * @param page                  Pagination, default 1.
     * @param sort                  Expression defining sorting found
     *                              records in result set base upon provided
     *                              fields and its modifier (ASC or DESC).
     *
     * @return object @see Response
     */
    public TuneManagementResponse find(
        String fields,
        String filter,
        Integer limit,
        Integer page,
        Map<String,String> sort
    ) throws Exception
    {
        if ((null != filter) && !filter.isEmpty()) {
            filter = this.validateFilter(filter);
        }

        Set<String> fields_set = null;
        if ((null != fields) && !fields.isEmpty()) {
            String[] fields_arr = fields.split("\\,");
            fields_set = new HashSet<String>(Arrays.asList(fields_arr));
        }

        String str_sort = null;
        if ((null != sort) && !sort.isEmpty()) {
            str_sort = this.validateSort(fields_set, sort);
        }

        if ((null != fields) && !fields.isEmpty()) {
            fields = this.validateFields(fields);
        }

        Map<String,String> query_string_dict = new HashMap<String,String>();
        query_string_dict.put("fields", fields);
        query_string_dict.put("filter", filter);
        query_string_dict.put("limit", Integer.toString(limit));
        query_string_dict.put("page", Integer.toString(page));
        query_string_dict.put("sort", str_sort);

        return super.call(
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
     * @param fields                Provide fields if format is 'csv'.
     * @param filter                Filter the results and apply conditions
     *                                      that must be met for records to be
     *                                      included in data.
     * @param format                Export format: csv, json
     *
     * @return object @see TuneManagementResponse
     */
    public TuneManagementResponse export(
        String fields,
        String filter,
        String format
    ) throws Exception
    {
        if ((null != fields) && !fields.isEmpty()) {
            fields = this.validateFields(fields);
        }
        if ((null != filter) && !filter.isEmpty()) {
            filter = this.validateFilter(filter);
        }
        if ((null == format) || format.isEmpty()) {
            format = "csv";
        }

        if (!EndpointBase.report_export_formats.contains(format)) {
            throw new IllegalArgumentException(
                String.format("Parameter 'format' is invalid: '%s'.", format)
            );
        }

        if ("csv" == format) {
            if ((null == fields) || fields.isEmpty()) {
                throw new IllegalArgumentException(
                    "Parameter 'fields' needs to be defined if report format is 'csv'."
                );
            }
        }

        Map<String,String> query_string_dict = new HashMap<String,String>();
        query_string_dict.put("fields", fields.toString());
        query_string_dict.put("filter", filter);
        query_string_dict.put("format", format);

        return super.call(
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
     *
     * @return object @see Response
     */
    public TuneManagementResponse status(
        String job_id
    ) throws TuneSdkException, Exception, IllegalArgumentException
    {
        if ( (null == job_id) || job_id.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'job_id' is not defined.");
        }

        Map<String,String> query_string_dict = new HashMap<String,String>();
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
     * @return object @see TuneManagementResponse
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
