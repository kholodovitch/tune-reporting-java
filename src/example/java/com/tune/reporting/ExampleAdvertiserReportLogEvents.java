package com.tune.reporting.example;

/**
 * ExampleAdvertiserReportLogEvents.java
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
 * @version   $Date: 2015-03-05 23:27:46 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.api.AdvertiserReportLogEvents;
import com.tune.reporting.api.SessionAuthenticate;
import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneServiceResponse;

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
 * Example of tune.reporting.api.AdvertiserReportLogEvents.
 */
public final class ExampleAdvertiserReportLogEvents extends ExampleReportBase {

  /**
   * Constructor.
   */
  private ExampleAdvertiserReportLogEvents() {
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
    if (args.length > 0) {
      String strApiKey = args[0];
      ExampleReportBase.setup(strApiKey);
    }

    run();
  }

  public static void run() throws Exception {

    String strSessionToken = null;

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

    System.out.println(
        "\033[34m" + "===============================================" + "\033[0m"
    );
    System.out.println(
        "\033[34m" + " Begin TUNE Advertiser Report Log Events  " + "\033[0m"
    );
    System.out.println(
        "\033[34m" + "===============================================" + "\033[0m"
    );

    AdvertiserReportLogEvents advertiserReport
        = new AdvertiserReportLogEvents();

    System.out.println("====================================================");
    System.out.println(" Fields Advertiser Report Log Events Default.       ");
    System.out.println("====================================================");

    strSessionToken = ExampleReportBase.getSessionToken();

    Set<String> setFieldsDefault = advertiserReport.getFieldsSet(
      strSessionToken,
      "session_token",
      EndpointBase.TUNE_FIELDS_DEFAULT
    );

    if ((null != setFieldsDefault) && !setFieldsDefault.isEmpty()) {
      for (String field : setFieldsDefault) {
        System.out.println(field);
      }
    } else {
      System.out.println("No default fields");
    }

    System.out.println("====================================================");
    System.out.println(" Fields Advertiser Report Log Events Recommended.   ");
    System.out.println("====================================================");

    strSessionToken = ExampleReportBase.getSessionToken();

    Set<String> fieldsRecommended = advertiserReport.getFieldsSet(
      strSessionToken,
      "session_token",
      EndpointBase.TUNE_FIELDS_RECOMMENDED
    );

    if ((null != fieldsRecommended) && !fieldsRecommended.isEmpty()) {
      for (String field : fieldsRecommended) {
        System.out.println(field);
      }
    } else {
      System.out.println("No recommended fields");
    }

    System.out.println("====================================================");
    System.out.println(" Count Advertiser Report Log Events records.        ");
    System.out.println("====================================================");

    Map<String, Object> mapParamsCount = new HashMap<String, Object>();
    mapParamsCount.put("start_date", startDate);
    mapParamsCount.put("end_date", endDate);
    mapParamsCount.put("filter", null);
    mapParamsCount.put("response_timezone", "America/Los_Angeles");

    strSessionToken = ExampleReportBase.getSessionToken();

    TuneServiceResponse response = advertiserReport.count(
      strSessionToken,
      "session_token",
      mapParamsCount
    );

    if ((response.getHttpCode() != HTTP_STATUS_OK)
        || (null != response.getErrors())) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneServiceResponse:");
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
    System.out.println(" Find Advertiser Report Log Events records.         ");
    System.out.println("====================================================");

    // build sort
    Map<String, String> sort = new HashMap<String, String>();
    sort.put("created", "DESC");

    strSessionToken = ExampleReportBase.getSessionToken();

    // build fields
    String strFieldsRecommended = advertiserReport.getFields(
      strSessionToken,
      "session_token",
      EndpointBase.TUNE_FIELDS_RECOMMENDED
    );

    Map<String, Object> mapParamsFind = new HashMap<String, Object>();
    mapParamsFind.put("start_date", startDate);
    mapParamsFind.put("end_date", endDate);
    mapParamsFind.put("fields", strFieldsRecommended);
    mapParamsFind.put("filter", null);
    mapParamsFind.put("limit", 5);
    mapParamsFind.put("page", 0);
    mapParamsFind.put("sort", sort);
    mapParamsFind.put("response_timezone", "America/Los_Angeles");

    strSessionToken = ExampleReportBase.getSessionToken();

    response = advertiserReport.find(
      strSessionToken,
      "session_token",
      mapParamsFind
    );

    if ((response.getHttpCode() != HTTP_STATUS_OK)
        || (null != response.getErrors())) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneServiceResponse:");
    System.out.println(response.toString());

    System.out.println(" JSON:");
    System.out.println(response.toJson());

    System.out.println("====================================================");
    System.out.println(" Export Advertiser Report Log Events CSV    ");
    System.out.println("====================================================");

    Map<String, Object> mapParamsExportCSV = new HashMap<String, Object>();
    mapParamsExportCSV.put("start_date", startDate);
    mapParamsExportCSV.put("end_date", endDate);
    mapParamsExportCSV.put("fields", strFieldsRecommended);
    mapParamsExportCSV.put("filter", null);
    mapParamsExportCSV.put("format", "csv");
    mapParamsExportCSV.put("response_timezone", "America/Los_Angeles");

    strSessionToken = ExampleReportBase.getSessionToken();

    response = advertiserReport.export(
      strSessionToken,
      "session_token",
      mapParamsExportCSV
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

    System.out.println(" TuneServiceResponse:");
    System.out.println(response.toString());

    System.out.println(" JSON:");
    System.out.println(response.toJson());

    String csvJobId
        = AdvertiserReportLogEvents.parseResponseReportJobId(response);
    System.out.println(String.format(" CSV Job ID: '%s'", csvJobId));

    System.out.println("====================================================");
    System.out.println(" Fetching Advertiser Report Log Events CSV  ");
    System.out.println("====================================================");

    strSessionToken = ExampleReportBase.getSessionToken();

    response = advertiserReport.fetch(
      strSessionToken,
      "session_token",
      csvJobId     // Job ID
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

    System.out.println(" TuneServiceResponse:");
    System.out.println(response.toString());

    System.out.println(" JSON:");
    System.out.println(response.toJson());

    String csvReportUrl
        = AdvertiserReportLogEvents.parseResponseReportUrl(response);
    System.out.println(String.format(" CSV Report URL: '%s'", csvReportUrl));

    System.out.println("====================================================");
    System.out.println(" Print Advertiser Report Log Events CSV     ");
    System.out.println("====================================================");

    ReportReaderCsv csvReader = new ReportReaderCsv(csvReportUrl);
    csvReader.read();
    csvReader.prettyPrint(5);

    System.out.println("====================================================");
    System.out.println(" Export Advertiser Report Log Events JSON   ");
    System.out.println("====================================================");

    Map<String, Object> mapParamsExportJSON = new HashMap<String, Object>();
    mapParamsExportJSON.put("start_date", startDate);
    mapParamsExportJSON.put("end_date", endDate);
    mapParamsExportJSON.put("fields", strFieldsRecommended);
    mapParamsExportJSON.put("filter", null);
    mapParamsExportJSON.put("response_timezone", "America/Los_Angeles");
    mapParamsExportJSON.put("format", "json");

    strSessionToken = ExampleReportBase.getSessionToken();

    response = advertiserReport.export(
      strSessionToken,
      "session_token",
      mapParamsExportJSON
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

    System.out.println(" TuneServiceResponse:");
    System.out.println(response.toString());

    System.out.println(" JSON:");
    System.out.println(response.toJson());

    String jsonJobId
        = AdvertiserReportLogEvents.parseResponseReportJobId(response);
    System.out.println(String.format(" JSON Job ID: '%s'", jsonJobId));

    System.out.println("====================================================");
    System.out.println(" Fetching Advertiser Report Log Events JSON ");
    System.out.println("====================================================");

    strSessionToken = ExampleReportBase.getSessionToken();

    response = advertiserReport.fetch(
      strSessionToken,
      "session_token",
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

    System.out.println(" TuneServiceResponse:");
    System.out.println(response.toString());

    System.out.println(" JSON:");
    System.out.println(response.toJson());

    String jsonReportUrl
        = AdvertiserReportLogEvents.parseResponseReportUrl(response);
    System.out.println(String.format(" JSON Report URL: '%s'", jsonReportUrl));

    System.out.println("====================================================");
    System.out.println(" Print Advertiser Report Log Events JSON    ");
    System.out.println("====================================================");

    ReportReaderJson jsonReader = new ReportReaderJson(jsonReportUrl);
    jsonReader.read();
    jsonReader.prettyPrint(5);

    System.out.println("\033[32m" + "=====================" + "\033[0m");
    System.out.println("\033[32m" + " End Example         " + "\033[0m");
    System.out.println("\033[32m" + "=====================" + "\033[0m");
  }
}
