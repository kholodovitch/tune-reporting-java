package com.tune.reporting.examples;

/**
 * ExampleAdvertiserReportEvents.java
 *
 * <p>
 * Copyright (c) 2014 Tune, Inc
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
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-12-12 05:24:55 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.api.AdvertiserReportEvents;
import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneManagementResponse;

import com.tune.reporting.helpers.ReportReaderCsv;
import com.tune.reporting.helpers.ReportReaderJson;

import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.helpers.TuneServiceException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Example of tune.reporting.api.AdvertiserReportEvents.
 */
public class ExampleAdvertiserReportEvents {

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

    System.out.println(String.format("apiKey = '%s'", apiKey));

    Date now = new Date();

    GregorianCalendar calendarYesterday = new GregorianCalendar();
    calendarYesterday.setTime(now);
    calendarYesterday.add(Calendar.DATE, -1);
    Date dateYesterday = calendarYesterday.getTime();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String startDate = dateFormat.format(dateYesterday);
    startDate = String.format("%s 00:00:00", startDate);

    String endDate = dateFormat.format(dateYesterday);
    endDate = String.format("%s 23:59:59", endDate);

    System.out.println("============================================");
    System.out.println(" Begin Advertiser Report Events   ");
    System.out.println("============================================");

    AdvertiserReportEvents reportLogsEvents
        = new AdvertiserReportEvents(apiKey, true);

    System.out.println("====================================================");
    System.out.println(" Fields Advertiser Report Events DEFAULT.  ");
    System.out.println("====================================================");

    Set<String> setFieldsDefault
        = reportLogsEvents.getFieldsSet(EndpointBase.TUNE_FIELDS_DEFAULT);
    if ((null != setFieldsDefault) && !setFieldsDefault.isEmpty()) {
      for (String field : setFieldsDefault) {
        System.out.println(field);
      }
    } else {
      System.out.println("No default fields");
    }

    System.out.println("====================================================");
    System.out.println(" Fields Advertiser Report Events RECOMMENDED.  ");
    System.out.println("====================================================");

    Set<String> setFieldsRecommended
        = reportLogsEvents.getFieldsSet(EndpointBase.TUNE_FIELDS_RECOMMENDED);
    if ((null != setFieldsRecommended) && !setFieldsRecommended.isEmpty()) {
      for (String field : setFieldsRecommended) {
        System.out.println(field);
      }
    } else {
      System.out.println("No recommended fields");
    }

    System.out.println("====================================================");
    System.out.println(" Count Advertiser Report Events records.    ");
    System.out.println("====================================================");

    TuneManagementResponse response = reportLogsEvents.count(
        startDate,
        endDate,
        null,     // filter
        "America/Los_Angeles"
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

    System.out.println(String.format("apiKey = '%s'", apiKey));

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
    System.out.println(" Find Advertiser Report Events records.     ");
    System.out.println("====================================================");

    // build sort
    Map<String, String> sort = new HashMap<String, String>();
    sort.put("created", "DESC");

    // build fields
    String strFieldsRecommended
        = reportLogsEvents.getFields(EndpointBase.TUNE_FIELDS_RECOMMENDED);

    response = reportLogsEvents.find(
        startDate,
        endDate,
        strFieldsRecommended,   // fields
        null,                   // filter
        5,                      // limit
        0,                      // page
        sort,
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
    System.out.println(" Export Advertiser Report Events CSV report.  ");
    System.out.println("====================================================");

    response = reportLogsEvents.export(
        startDate,
        endDate,
        strFieldsRecommended,   // fields
        null,                   // filter
        "csv",                  // format
        "America/Los_Angeles"   // responseTimezone
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

    String csvJobId = AdvertiserReportEvents.parseResponseReportJobId(response);
    System.out.println(String.format(" CSV Job ID: '%s'", csvJobId));

    System.out.println("====================================================");
    System.out.println(" Fetching Advertiser Report Events CSV report.  ");
    System.out.println("====================================================");

    response = reportLogsEvents.fetch(
        csvJobId,       // Job ID
        true,           // verbose
        10              // sleep in seconds
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

    String csvReportUrl = AdvertiserReportEvents.parseResponseReportUrl(response);
    System.out.println(String.format(" CSV Report URL: '%s'", csvReportUrl));

    System.out.println("====================================================");
    System.out.println(" Print Advertiser Report Events CSV report. ");
    System.out.println("====================================================");

    ReportReaderCsv csvReader = new ReportReaderCsv(csvReportUrl);
    csvReader.read();
    csvReader.prettyPrint(5);

    System.out.println("====================================================");
    System.out.println(" Export Advertiser Report Events JSON report.   ");
    System.out.println("====================================================");

    response = reportLogsEvents.export(
        startDate,
        endDate,
        strFieldsRecommended,  // fields
        null,    // filter
        "json",         // format
        "America/Los_Angeles"     // responseTimezone
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

    String jsonJobId = AdvertiserReportEvents.parseResponseReportJobId(response);
    System.out.println(String.format(" JSON Job ID: '%s'", jsonJobId));

    System.out.println("====================================================");
    System.out.println(" Fetching Advertiser Report Events JSON report.   ");
    System.out.println("====================================================");

    response = reportLogsEvents.fetch(
        jsonJobId,      // Job ID
        true,           // verbose
        10              // sleep in seconds
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

    String jsonReportUrl = AdvertiserReportEvents.parseResponseReportUrl(response);
    System.out.println(String.format(" JSON Report URL: '%s'", jsonReportUrl));

    System.out.println("====================================================");
    System.out.println(" Print Advertiser Report Events JSON report.  ");
    System.out.println("====================================================");

    ReportReaderJson jsonReader = new ReportReaderJson(jsonReportUrl);
    jsonReader.read();
    jsonReader.prettyPrint(5);

    System.out.println("============================================");
    System.out.println("   The End        ");
    System.out.println("============================================");
  }
}