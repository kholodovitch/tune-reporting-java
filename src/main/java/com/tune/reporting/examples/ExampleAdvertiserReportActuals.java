package com.tune.reporting.examples;

/**
 * ExampleAdvertiserReportActuals.java
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
 * @version   $Date: 2015-01-05 22:52:04 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */


import com.tune.reporting.api.AdvertiserReportActuals;
import com.tune.reporting.api.SessionAuthenticate;
import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneManagementResponse;

import com.tune.reporting.helpers.ReportReaderCsv;
import com.tune.reporting.helpers.ReportReaderJson;
import com.tune.reporting.helpers.SdkConfig;
import com.tune.reporting.helpers.TuneServiceException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Example of tune.reporting.api.AdvertiserReportActuals.
 */
public final class ExampleAdvertiserReportActuals {

  /**
   * The request has succeeded.
   */
  public static final int HTTP_STATUS_OK = 200;

  /**
   * Constructor.
   */
  private ExampleAdvertiserReportActuals() {
    //not called
  }

  /**
   * The main method.
   *
   * @param args the arguments
   * @throws Exception  If example should fail.
   */
  public static void main(
      final String[] args
  ) throws Exception {
    String apiKey = null;

    SdkConfig sdkConfig = SdkConfig.getInstance();

    if (args.length > 0) {
      apiKey = args[0];
      sdkConfig.setApiKey(apiKey);
    }

    apiKey = sdkConfig.getAuthKey();

    SessionAuthenticate sessionAuthenticate = new SessionAuthenticate();
    TuneManagementResponse response = sessionAuthenticate.apiKey(apiKey);
    String sessonToken = response.getData().toString();
    System.out.println(sessonToken);

    sdkConfig.setApiKey(apiKey);
    run();

    sdkConfig.setSessionToken(sessonToken);
    run();
  }

  public static void run() throws Exception {

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

    System.out.println(
        "\033[34m" + "===============================================" + "\033[0m"
    );
    System.out.println(
        "\033[34m" + " Begin TUNE Advertiser Report Actuals " + "\033[0m"
    );
    System.out.println(
        "\033[34m" + "===============================================" + "\033[0m"
    );

    AdvertiserReportActuals advertiserReport
        = new AdvertiserReportActuals();

    System.out.println("====================================================");
    System.out.println(" Fields Advertiser Report Actuals Default.          ");
    System.out.println("====================================================");

    Set<String> setFieldsDefault
        = advertiserReport.getFieldsSet(EndpointBase.TUNE_FIELDS_DEFAULT);
    if ((null != setFieldsDefault) && !setFieldsDefault.isEmpty()) {
      for (String field : setFieldsDefault) {
        System.out.println(field);
      }
    } else {
      System.out.println("No default fields");
    }

    System.out.println("====================================================");
    System.out.println(" Fields Advertiser Report Actuals Recommended.      ");
    System.out.println("====================================================");

    Set<String> fieldsRecommended
        = advertiserReport.getFieldsSet(EndpointBase.TUNE_FIELDS_RECOMMENDED);
    if ((null != fieldsRecommended) && !fieldsRecommended.isEmpty()) {
      for (String field : fieldsRecommended) {
        System.out.println(field);
      }
    } else {
      System.out.println("No recommended fields");
    }

    System.out.println("====================================================");
    System.out.println(" Count Advertiser Report Actuals records.       ");
    System.out.println("====================================================");
    TuneManagementResponse response = advertiserReport.count(
        startDate,
        endDate,
        "site_id,publisher_id",   // group
        "(publisher_id > 0)",   // filter
        "America/Los_Angeles"   // responseTimezone
    );

    if ((response.getHttpCode() != HTTP_STATUS_OK)
        || (null != response.getErrors())
    ) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    System.out.println(" JSON:");
    System.out.println(response.toJson());

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
    System.out.println(" Find Advertiser Report Actuals records.        ");
    System.out.println("====================================================");

    // build sort
    Map<String, String> sort = new HashMap<String, String>();
    sort.put("installs", "DESC");

    // build fields
    String strFieldsRecommended
        = advertiserReport.getFields(EndpointBase.TUNE_FIELDS_RECOMMENDED);

    response = advertiserReport.find(
        startDate,
        endDate,
        strFieldsRecommended,       // fields
        "site_id,publisher_id",     // group
        "(publisher_id > 0)",       // filter
        5,                          // limit
        0,                          // page
        sort,
        "datehour",                 // timestamp
        "America/Los_Angeles"       // responseTimezone
    );

    if ((response.getHttpCode() != HTTP_STATUS_OK)
        || (null != response.getErrors())
    ) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    System.out.println(" JSON:");
    System.out.println(response.toJson());

    System.out.println("====================================================");
    System.out.println(" Export Advertiser Report Actuals CSV   ");
    System.out.println("====================================================");

    response = advertiserReport.export(
        startDate,
        endDate,
        strFieldsRecommended,       // fields
        "site_id,publisher_id",     // group
        "(publisher_id > 0)",       // filter
        "datehour",                 // timestamp
        "csv",                      // report format
        "America/Los_Angeles"       // responseTimezone
    );

    if ((response.getHttpCode() != HTTP_STATUS_OK)
        || (null != response.getErrors())
    ) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    System.out.println(" JSON:");
    System.out.println(response.toJson());

    String csvJobId
        = AdvertiserReportActuals.parseResponseReportJobId(response);
    System.out.println(String.format(" CSV Job ID: '%s'", csvJobId));

    System.out.println("====================================================");
    System.out.println(" Fetching Advertiser Report Actuals CSV   ");
    System.out.println("====================================================");

    response = advertiserReport.fetch(
        csvJobId       // Job ID
    );

    if ((response.getHttpCode() != HTTP_STATUS_OK)
        || (null != response.getErrors())
    ) {
      throw new Exception(
          String.format(
            "Failed: %d: '%s'", response.getHttpCode(), response.toString()
          )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    String csvReportUrl
        = AdvertiserReportActuals.parseResponseReportUrl(response);
    System.out.println(String.format(" CSV Report URL: '%s'", csvReportUrl));

    System.out.println("====================================================");
    System.out.println(" Print Advertiser Report Actuals CSV    ");
    System.out.println("====================================================");

    ReportReaderCsv csvReader = new ReportReaderCsv(csvReportUrl);
    csvReader.read();
    csvReader.prettyPrint(5);

    System.out.println("====================================================");
    System.out.println(" Export Advertiser Report Actuals JSON  ");
    System.out.println("====================================================");

    response = advertiserReport.export(
        startDate,
        endDate,
        strFieldsRecommended,       // fields
        "site_id,publisher_id",     // group
        "(publisher_id > 0)",       // filter
        "datehour",                 // timestamp
        "json",                     // report format
        "America/Los_Angeles"       // responseTimezone
    );

    if ((response.getHttpCode() != HTTP_STATUS_OK)
        || (null != response.getErrors())
    ) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    System.out.println(" JSON:");
    System.out.println(response.toJson());

    final String jsonJobId
        = AdvertiserReportActuals.parseResponseReportJobId(response);
    System.out.println(String.format(" CSV Job ID: '%s'", csvJobId));

    System.out.println("====================================================");
    System.out.println(" Fetching Advertiser Report Actuals JSON  ");
    System.out.println("====================================================");

    response = advertiserReport.fetch(
        jsonJobId      // Job ID
    );

    if ((response.getHttpCode() != HTTP_STATUS_OK)
        || (null != response.getErrors())
    ) {
      throw new Exception(
        String.format(
          "Failed: %d: '%s'",
          response.getHttpCode(),
          response.toString()
        )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    System.out.println(" JSON:");
    System.out.println(response.toJson());

    String jsonReportUrl
        = AdvertiserReportActuals.parseResponseReportUrl(response);
    System.out.println(
        String.format(" JSON Report URL: '%s'", jsonReportUrl)
    );

    System.out.println("====================================================");
    System.out.println(" Print Advertiser Report Actuals JSON   ");
    System.out.println("====================================================");

    ReportReaderJson jsonReader = new ReportReaderJson(jsonReportUrl);
    jsonReader.read();
    jsonReader.prettyPrint(5);

    System.out.println("\033[32m" + "=====================" + "\033[0m");
    System.out.println("\033[32m" + " End Example         " + "\033[0m");
    System.out.println("\033[32m" + "=====================" + "\033[0m");
  }
}
