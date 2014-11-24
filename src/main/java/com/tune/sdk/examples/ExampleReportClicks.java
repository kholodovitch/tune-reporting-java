package com.tune.sdk.examples;

/**
 * ExampleReportClicks.java
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
 * @package   com.tune.sdk
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-21 17:34:43 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Calendar;

import java.text.SimpleDateFormat;

import com.tune.sdk.management.api.advertiser.stats.Clicks;
import com.tune.sdk.management.shared.endpoints.EndpointBase;
import com.tune.sdk.management.shared.service.TuneManagementResponse;

import com.tune.sdk.shared.TuneSdkException;
import com.tune.sdk.shared.TuneServiceException;
import com.tune.sdk.shared.ReportReaderCSV;
import com.tune.sdk.shared.ReportReaderJSON;


/**
 * Example of tune.management.api.advertiser.stats.Clicks.
 */
public class ExampleReportClicks {

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {

        String api_key = null;

        if (args.length > 0) {
            api_key = args[0];
            if (!api_key.matches("[a-zA-Z0-9]+")) {
                throw new IllegalArgumentException(
                    String.format("Invalid [api_key]: '%s'", api_key)
                );
            }
        } else {
            throw new IllegalArgumentException("Missing [api_key]");
        }

        System.out.println( String.format("api_key = '%s'", api_key) );

        Date now = new Date();

        GregorianCalendar calendar_yesterday = new GregorianCalendar();
        calendar_yesterday.setTime(now);
        calendar_yesterday.add(Calendar.DATE, -1);
        Date yesterday = calendar_yesterday.getTime();

        SimpleDateFormat date_format = new SimpleDateFormat( "yyyy-MM-dd" );

        String start_date = date_format.format( yesterday );
        start_date = String.format("%s 00:00:00", start_date);

        String end_date = date_format.format( yesterday );
        end_date = String.format("%s 23:59:59", end_date);

        System.out.println( "\n============================================" );
        System.out.println(   "= Tune Management Reports Clicks (Logs)    =" );
        System.out.println(   "============================================" );

        Clicks reports_logs_clicks = new Clicks(api_key, true);

        System.out.println( "======================================================" );
        System.out.println( " Fields of Reports Clicks DEFAULT.                    " );
        System.out.println( "======================================================" );

        Set<String> set_fields_default = reports_logs_clicks.getFieldsSet(EndpointBase.TUNE_FIELDS_DEFAULT);
        if ((null != set_fields_default) && !set_fields_default.isEmpty()) {
            for (String field : set_fields_default) {
                System.out.println(field);
            }
        } else {
            System.out.println("No default fields");
        }

        System.out.println( "======================================================" );
        System.out.println( " Fields of Reports Clicks RECOMMENDED.                " );
        System.out.println( "======================================================" );

        Set<String> set_fields_recommended = reports_logs_clicks.getFieldsSet(EndpointBase.TUNE_FIELDS_RECOMMENDED);
        if ((null != set_fields_recommended) && !set_fields_recommended.isEmpty()) {
            for (String field : set_fields_recommended) {
                System.out.println(field);
            }
        } else {
            System.out.println("No recommended fields");
        }

        System.out.println( "======================================================" );
        System.out.println( " Count Reports Clicks records.                        " );
        System.out.println( "======================================================" );

        TuneManagementResponse response = reports_logs_clicks.count(
            start_date,
            end_date,
            null,           // filter
            "America/Los_Angeles"
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: %s", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        System.out.println( String.format("api_key = '%s'", api_key) );

        Object data = response.getData();
        if (null == data) {
            throw new TuneServiceException("Report request failed to get export data.");
        }

        if (!(data instanceof Integer)) {
            throw new TuneServiceException("Data expected to be type integer.");
        }

        int count  = Integer.parseInt(data.toString());
        System.out.println( String.format("= Count: '%d'", count ));

        System.out.println( "======================================================" );
        System.out.println( " Find Reports Clicks records.                         " );
        System.out.println( "======================================================" );

        // build sort
        Map<String, String> sort = new HashMap<String, String> ();
        sort.put("created", "DESC");

        // build fields
        String str_fields_recommended = reports_logs_clicks.getFields(EndpointBase.TUNE_FIELDS_RECOMMENDED);

        response = reports_logs_clicks.find(
            start_date,
            end_date,
            str_fields_recommended,	// fields
            null,			// filter
            5,           		// limit
            0,       			// page
            sort,
            "America/Los_Angeles"   	// response_timezone
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: %s", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        System.out.println( "======================================================" );
        System.out.println( " Export Reports Clicks CSV report.                    " );
        System.out.println( "======================================================" );

        response = reports_logs_clicks.export(
            start_date,
            end_date,
            str_fields_recommended,		// fields
            null,				// filter
            "csv",                           	// format
            "America/Los_Angeles"   		// response_timezone
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: '%s'", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        String csv_job_id = Clicks.parseResponseReportJobId(response);
        System.out.println(String.format("= CSV Job ID: '%s'", csv_job_id));

        System.out.println( "======================================================" );
        System.out.println( " Fetching Reports Clicks CSV report.                  " );
        System.out.println( "======================================================" );

        response = reports_logs_clicks.fetch(
            csv_job_id,                     // Job ID
            true,                           // verbose
            10                              // sleep in seconds
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: '%s'", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        String str_csv_report_url = Clicks.parseResponseReportUrl(response);
        System.out.println(String.format("= CSV Report URL: '%s'", str_csv_report_url));

        System.out.println( "======================================================" );
        System.out.println( " Print Items Reports Clicks CSV report.               " );
        System.out.println( "======================================================" );

        ReportReaderCSV csv_reader = new ReportReaderCSV(str_csv_report_url);
        csv_reader.read();
        csv_reader.prettyPrint(5);

        System.out.println( "======================================================" );
        System.out.println( " Export Account Users JSON report.                    " );
        System.out.println( "======================================================" );

        response = reports_logs_clicks.export(
            start_date,
            end_date,
            str_fields_recommended,		// fields
            null,				// filter
            "json",                           	// format
            "America/Los_Angeles"   		// response_timezone
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: '%s'", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        String json_job_id = Clicks.parseResponseReportJobId(response);
        System.out.println(String.format("= JSON Job ID: '%s'", json_job_id));

        System.out.println( "======================================================" );
        System.out.println( " Fetching Account Users JSON report.                  " );
        System.out.println( "======================================================" );

        response = reports_logs_clicks.fetch(
            json_job_id,                    // Job ID
            true,                           // verbose
            10                              // sleep in seconds
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: '%s'", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        String str_json_report_url = Clicks.parseResponseReportUrl(response);
        System.out.println(String.format("= JSON Report URL: '%s'", str_json_report_url));

        System.out.println( "======================================================" );
        System.out.println( " Print Items Account Users JSON report.               " );
        System.out.println( "======================================================" );

        ReportReaderJSON json_reader = new ReportReaderJSON(str_json_report_url);
        json_reader.read();
        json_reader.prettyPrint(5);

        System.out.println(   "============================================" );
        System.out.println(   "=   The End                                =" );
        System.out.println(   "============================================" );
    }
}
