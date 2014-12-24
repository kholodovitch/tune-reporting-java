package com.tune.reporting.examples;

/**
 * ExampleAdvertiserReportCohortValue.java
 *
 * <p>
 * Copyright (c) 2014 TUNE, Inc.
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
 * @copyright 2014 TUNE, Inc. (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-12-24 13:23:15 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.api.AdvertiserReportCohortValue;
import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneManagementResponse;

import com.tune.reporting.helpers.ReportReaderCsv;
import com.tune.reporting.helpers.TuneServiceException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Example of tune.reporting.api.AdvertiserReportCohortValue.
 */
public class ExampleAdvertiserReportCohortValue {

  /**
   * The main method.
   *
   * @param args the arguments
   * @throws Exception  If example should fail.
   */
  public static void main(final String[] args) throws Exception {

    String apiKey = null;

    if (args.length > 0) {
      apiKey = args[0];
      if (!apiKey.matches("[a-zA-Z0-9]+")) {
        throw new IllegalArgumentException(
          String.format("Invalid [apiKey]: '%s'", apiKey)
       );
      }
    } else {
      throw new IllegalArgumentException("Missing [apiKey]");
    }

    Date now = new Date();
    GregorianCalendar calendarWeekAgo = new GregorianCalendar();
    calendarWeekAgo.setTime(now);
    calendarWeekAgo.add(Calendar.DATE, -8);
    Date dateWeekAgo = calendarWeekAgo.getTime();

    GregorianCalendar calendarYesterday = new GregorianCalendar();
    calendarYesterday.setTime(now);
    calendarYesterday.add(Calendar.DATE, -1);
    Date dateYesterday = calendarYesterday.getTime();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String startDate = dateFormat.format(dateWeekAgo);
    startDate = String.format("%s 00:00:00", startDate);

    String endDate = dateFormat.format(dateYesterday);
    endDate = String.format("%s 23:59:59", endDate);

    System.out.println("\033[34m" + "===============================================" + "\033[0m");
    System.out.println("\033[34m" + " Begin TUNE Advertiser Report Cohort Value     " + "\033[0m");
    System.out.println("\033[34m" + "===============================================" + "\033[0m");


    AdvertiserReportCohortValue reportCohort
        = new AdvertiserReportCohortValue(apiKey, true);

    System.out.println("====================================================");
    System.out.println(" Fields Advertiser Report Cohort Value DEFAULT.    ");
    System.out.println("====================================================");

    Set<String> setFieldsDefault
        = reportCohort.getFieldsSet(EndpointBase.TUNE_FIELDS_DEFAULT);
    if ((null != setFieldsDefault) && !setFieldsDefault.isEmpty()) {
      for (String field : setFieldsDefault) {
        System.out.println(field);
      }
    } else {
      System.out.println("No default fields");
    }

    System.out.println("====================================================");
    System.out.println(" Fields Advertiser Report Cohort Value RECOMMENDED.  ");
    System.out.println("====================================================");

    Set<String> setFieldsRecommended
        = reportCohort.getFieldsSet(EndpointBase.TUNE_FIELDS_RECOMMENDED);
    if ((null != setFieldsRecommended) && !setFieldsRecommended.isEmpty()) {
      for (String field : setFieldsRecommended) {
        System.out.println(field);
      }
    } else {
      System.out.println("No recommended fields");
    }

    System.out.println("====================================================");
    System.out.println(" Count Advertiser Report Cohort Value records.      ");
    System.out.println("====================================================");

    TuneManagementResponse response = reportCohort.count(
        startDate,
        endDate,
        "click",    // cohortType
        "year_day",     // cohortInterval
        "site_id,publisher_id", // group
        "(publisher_id > 0)",   // filter
        "America/Los_Angeles"
    );

    if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    Object data = response.getData();
    if (null == data) {
      throw new TuneServiceException(
        "Report request failed to get export data."
      );
    }

    if (!(data instanceof Integer)) {
      throw new TuneServiceException("Data expected to be type integer.");
    }

    int count  = Integer.parseInt(data.toString());
    System.out.println(String.format(" Count: '%d'", count));

    System.out.println("====================================================");
    System.out.println(" Find Advertiser Report Cohort Value records.     ");
    System.out.println("====================================================");

    // build sort
    Map<String, String> sort = new HashMap<String, String>();
    sort.put("year_day", "ASC");
    sort.put("install_publisher_id", "ASC");

    String strFieldsRecommended
        = reportCohort.getFields(
            AdvertiserReportCohortValue.TUNE_FIELDS_RECOMMENDED
          );

    response = reportCohort.find(
        startDate,
        endDate,
        "click",                // cohortType
        "year_day",             // cohortInterval
        "cumulative",           // aggregationType
        strFieldsRecommended,   // fields
        "site_id,publisher_id", // group
        "(publisher_id > 0)",   // filter
        5,                      // limit
        0,                      // page
        null,                   // sort
        "America/Los_Angeles"   // responseTimezone
    );

    if ((response.getHttpCode() != 200)
        || (null != response.getErrors())) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    System.out.println("====================================================");
    System.out.println(" Export Advertiser Report Cohort Value CSV    ");
    System.out.println("====================================================");

    response = reportCohort.export(
        startDate,
        endDate,
        "click",                // cohortType
        "year_day",             // cohortInterval
        "cumulative",           // aggregationType
        strFieldsRecommended,   // fields
        "site_id,publisher_id", // group
        "(publisher_id > 0)",   // filter
        "America/Los_Angeles"   // responseTimezone
    );

    if ((response.getHttpCode() != 200)
        || (null != response.getErrors())) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
       )
     );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    String csvJobId = AdvertiserReportCohortValue.parseResponseReportJobId(response);
    System.out.println(String.format(" CSV Job ID: '%s'", csvJobId));

    System.out.println("====================================================");
    System.out.println(" Fetching Advertiser Report Cohort Value CSV  ");
    System.out.println("====================================================");

    response = reportCohort.fetch(
        csvJobId,       // Job ID
        true,           // verbose
        10              // sleep in seconds
   );

    if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
      throw new Exception(
        String.format(
          "Failed: %d: '%s'", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    String csvReportUrl = AdvertiserReportCohortValue.parseResponseReportUrl(response);
    System.out.println(String.format(" CSV Report URL: '%s'", csvReportUrl));

    System.out.println("====================================================");
    System.out.println(" Print Advertiser Report Cohort Value CSV     ");
    System.out.println("====================================================");

    ReportReaderCsv csvReader = new ReportReaderCsv(csvReportUrl);
    csvReader.read();
    csvReader.prettyPrint(5);

    System.out.println("\033[32m" + "=====================" + "\033[0m");
    System.out.println("\033[32m" + " End Example         " + "\033[0m");
    System.out.println("\033[32m" + "=====================" + "\033[0m");
  }
}