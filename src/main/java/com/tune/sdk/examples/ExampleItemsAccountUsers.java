package com.tune.sdk.examples;

/**
 * ExampleAccountUsers.java
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
 * @version   $Date: 2014-11-24 09:34:47 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import com.tune.sdk.management.api.account.Users;
import com.tune.sdk.management.shared.endpoints.EndpointBase;
import com.tune.sdk.management.shared.service.TuneManagementResponse;

import com.tune.sdk.shared.TuneSdkException;
import com.tune.sdk.shared.TuneServiceException;
import com.tune.sdk.shared.ReportReaderCSV;
import com.tune.sdk.shared.ReportReaderJSON;

/**
 * Example of tune.management.api.account.Users.
 */
public class ExampleItemsAccountUsers {

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

        System.out.println( "\n============================================" );
        System.out.println(   "= Tune Management Account Users      =" );
        System.out.println(   "============================================" );

        Users account_users = new Users(api_key, true);

        System.out.println( "======================================================" );
        System.out.println( " Fields of Account Users records DEFAULT.             " );
        System.out.println( "======================================================" );

        Set<String> set_fields_default = account_users.getFieldsSet(EndpointBase.TUNE_FIELDS_DEFAULT);
        if ((null != set_fields_default) && !set_fields_default.isEmpty()) {
            for (String field : set_fields_default) {
                System.out.println(field);
            }
        } else {
            System.out.println("No default fields");
        }

        // build fields
        String str_fields_default = account_users.getFields(EndpointBase.TUNE_FIELDS_DEFAULT);

        if ((null != str_fields_default) && !str_fields_default.isEmpty()) {
            System.out.println(str_fields_default);
        } else {
            System.out.println("No default fields");
        }

        System.out.println( "======================================================" );
        System.out.println( " Fields of Account Users records RECOMMENDED.         " );
        System.out.println( "======================================================" );

        Set<String> set_fields_recommended = account_users.getFieldsSet(EndpointBase.TUNE_FIELDS_RECOMMENDED);
        if ((null != set_fields_recommended) && !set_fields_recommended.isEmpty()) {
            for (String field : set_fields_recommended) {
                System.out.println(field);
            }
        } else {
            System.out.println("No recommended fields");
        }

        // build fields
        String str_fields_recommended = account_users.getFields(EndpointBase.TUNE_FIELDS_RECOMMENDED);

        if ((null != str_fields_recommended) && !str_fields_recommended.isEmpty()) {
            System.out.println(str_fields_recommended);
        } else {
            System.out.println("No recommended fields");
        }

        System.out.println( "======================================================" );
        System.out.println( " Count Account Users records.                         " );
        System.out.println( "======================================================" );

        TuneManagementResponse response = account_users.count(
            null    // filter
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: %s", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        System.out.println(String.format("= Count: %d", response.getData()));

        System.out.println( "======================================================" );
        System.out.println( " Find Account Users records.                          " );
        System.out.println( "======================================================" );

        // build sort
        Map<String, String> sort = new HashMap<String, String> ();
        sort.put("created", "DESC");

        response = account_users.find(
            str_fields_default,             // fields
            null,                           // filter
            5,                              // limit
            0,                              // page
            sort                            // sort
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: %s", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        System.out.println( "======================================================" );
        System.out.println( " Export Account Users CSV report.                     " );
        System.out.println( "======================================================" );

        response = account_users.export(
            str_fields_default,             // fields
            null,                           // filter
            "csv"                           // format
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: '%s'", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        String csv_job_id = Users.parseResponseReportJobId(response);
        System.out.println(String.format("= CSV Job ID: '%s'", csv_job_id));

        System.out.println( "======================================================" );
        System.out.println( " Fetching Account Users CSV report.                   " );
        System.out.println( "======================================================" );

        response = account_users.fetch(
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

        String str_csv_report_url = Users.parseResponseReportUrl(response);
        System.out.println(String.format("= CSV Report URL: '%s'", str_csv_report_url));

        System.out.println( "======================================================" );
        System.out.println( " Print Account Users CSV report.                      " );
        System.out.println( "======================================================" );

        ReportReaderCSV csv_reader = new ReportReaderCSV(str_csv_report_url);
        csv_reader.read();
        csv_reader.prettyPrint(5);

        System.out.println( "======================================================" );
        System.out.println( " Export Account Users JSON report.                    " );
        System.out.println( "======================================================" );

        response = account_users.export(
            str_fields_default,             // fields
            null,                           // filter
            "json"                          // format
        );

        if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
            throw new Exception(
                String.format("Failed: %d: '%s'", response.getHttpCode(), response.toString())
            );
        }

        System.out.println( "= TuneManagementResponse:" );
        System.out.println( response.toString());

        String json_job_id = Users.parseResponseReportJobId(response);
        System.out.println(String.format("= JSON Job ID: '%s'", json_job_id));

        System.out.println( "======================================================" );
        System.out.println( " Fetching Account Users JSON report.                  " );
        System.out.println( "======================================================" );

        response = account_users.fetch(
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

        String str_json_report_url = Users.parseResponseReportUrl(response);
        System.out.println(String.format("= JSON Report URL: '%s'", str_json_report_url));

        System.out.println( "======================================================" );
        System.out.println( " Print Account Users JSON report.                     " );
        System.out.println( "======================================================" );

        ReportReaderJSON json_reader = new ReportReaderJSON(str_json_report_url);
        json_reader.read();
        json_reader.prettyPrint(5);

        System.out.println( "======================================================" );
        System.out.println( " End Account Users example.                           " );
        System.out.println( "======================================================" );
    }
}