/**
 * ExampleReportRetention.java
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
 * @package   tune.examples
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-21 11:11:02 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tune.sdk.management.api.advertiser.stats.Retention;
import com.tune.sdk.management.shared.endpoints.EndpointBase;
import com.tune.sdk.management.shared.service.TuneManagementResponse;

import com.tune.sdk.shared.TuneSdkException;
import com.tune.sdk.shared.TuneServiceException;
import com.tune.sdk.shared.ReportReaderCSV;
import com.tune.sdk.shared.ReportReaderJSON;

/**
 *
 */
public class ExampleReportRetention {

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
        GregorianCalendar calendar_week_ago = new GregorianCalendar();
        calendar_week_ago.setTime(now);
        calendar_week_ago.add(Calendar.DATE, -8);
        Date week_ago = calendar_week_ago.getTime();

        GregorianCalendar calendar_yesterday = new GregorianCalendar();
        calendar_yesterday.setTime(now);
        calendar_yesterday.add(Calendar.DATE, -1);
        Date yesterday = calendar_yesterday.getTime();

        SimpleDateFormat date_format = new SimpleDateFormat( "yyyy-MM-dd" );

        String start_date = date_format.format( week_ago );
        start_date = String.format("%s 00:00:00", start_date);

        String end_date = date_format.format( yesterday );
        end_date = String.format("%s 23:59:59", end_date);

        System.out.println( "\n=========================================" );
        System.out.println(   "= Tune Management Reports Retention     =" );
        System.out.println(   "=========================================" );

        Retention reports_retention = new Retention(api_key, true);

        System.out.println( "======================================================" );
        System.out.println( " Fields of Reports Retention DEFAULT.                 " );
        System.out.println( "======================================================" );

        Set<String> set_fields_default = reports_retention.getFieldsSet(EndpointBase.TUNE_FIELDS_DEFAULT);
        if ((null != set_fields_default) && !set_fields_default.isEmpty()) {
            for (String field : set_fields_default) {
                System.out.println(field);
            }
        } else {
            System.out.println("No default fields");
        }

        System.out.println( "======================================================" );
        System.out.println( " Fields of Reports Retention RECOMMENDED.             " );
        System.out.println( "======================================================" );

        Set<String> set_fields_recommended = reports_retention.getFieldsSet(EndpointBase.TUNE_FIELDS_RECOMMENDED);
        if ((null != set_fields_recommended) && !set_fields_recommended.isEmpty()) {
            for (String field : set_fields_recommended) {
                System.out.println(field);
            }
        } else {
            System.out.println("No recommended fields");
        }

        System.out.println( "======================================================" );
        System.out.println( " Count Reports Retention records.                     " );
        System.out.println( "======================================================" );

        TuneManagementResponse response = reports_retention.count(
            start_date,
            end_date,
            "click",                        // cohort_type
            "year_day",                     // cohort_interval
            "site_id,install_publisher_id", // group
            "(install_publisher_id > 0)",   // filter
            "America/Los_Angeles"
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: %s", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

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
        System.out.println( " Find Reports Retention records.                      " );
        System.out.println( "======================================================" );

        // build sort
        Map<String, String> sort = new HashMap<String, String> ();
        sort.put("year_day", "ASC");
        sort.put("install_publisher_id", "ASC");

        // build fields
        String str_fields_recommended = reports_retention.getFields(Retention.TUNE_FIELDS_RECOMMENDED);

        response = reports_retention.find(
	    start_date,
	    end_date,
	    "click",                        // cohort_type
	    "year_day",                     // cohort_interval
	    str_fields_recommended,         // fields
	    "site_id,install_publisher_id", // group
	    "(install_publisher_id > 0)",   // filter
	    5,                              // limit
	    0,                              // page
	    sort,                           // sort
	    "America/Los_Angeles"           // response_timezone
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: %s", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        System.out.println( "======================================================" );
        System.out.println( " Export Retention CSV report.                         " );
        System.out.println( "======================================================" );

        response = reports_retention.export(
	    start_date,
	    end_date,
	    "click",                        // cohort_type
	    "year_day",                     // cohort_interval
	    str_fields_recommended,         // fields
	    "site_id,install_publisher_id", // group
	    "(install_publisher_id > 0)",   // filter
	    "America/Los_Angeles"           // response_timezone
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: %s", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        String csv_job_id = Retention.parseResponseReportJobId(response);
        System.out.println(String.format("= CSV Job ID: '%s'", csv_job_id));

        System.out.println( "======================================================" );
        System.out.println( " Fetching Retention CSV report.                       " );
        System.out.println( "======================================================" );

        response = reports_retention.fetch(
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

        String str_csv_report_url = Retention.parseResponseReportUrl(response);
        System.out.println(String.format("= CSV Report URL: '%s'", str_csv_report_url));

	System.out.println( "======================================================" );
        System.out.println( " Print Retention CSV report.                          " );
        System.out.println( "======================================================" );

        ReportReaderCSV csv_reader = new ReportReaderCSV(str_csv_report_url);
        csv_reader.read();
        csv_reader.prettyPrint(5);

        System.out.println( "======================================================" );
        System.out.println( " End Retention example.                               " );
        System.out.println( "======================================================" );
    }
}
