package com.tune.reporting;

/**
 * TestAdvertiserReportLogClicks.java
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
 * @version   $Date: 2015-03-05 16:09:11 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.api.AdvertiserReportLogClicks;
import com.tune.reporting.api.SessionAuthenticate;
import com.tune.reporting.base.endpoints.AdvertiserReportBase;
import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneServiceResponse;

import com.tune.reporting.helpers.ReportReaderCsv;
import com.tune.reporting.helpers.ReportReaderJson;
import com.tune.reporting.helpers.SdkConfig;
import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.helpers.TuneServiceException;

import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Test of tune.reporting.api.AdvertiserReportLogClicks.
 */
public class TestAdvertiserReportLogClicks extends TestCase {
  /** Instance. */
  protected AdvertiserReportBase advertiserReport = null;

  /** Start Date. */
  protected String startDate = null;

  /** End Date. */
  protected String endDate = null;

  /** Service API Key */
  protected String strApiKey = null;

  public static void main(final String[] args) {
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp()
    throws Exception
  {
    this.advertiserReport = new AdvertiserReportLogClicks();

    this.strApiKey = System.getProperty("API_KEY");
    TestCase.assertNotNull(this.strApiKey);
    TestCase.assertFalse(this.strApiKey.isEmpty());

    if ((strApiKey != null) && !strApiKey.isEmpty()) {
      SdkConfig sdkConfig = SdkConfig.getInstance();
      sdkConfig.setApiKey(strApiKey);
    }

    this.strApiKey = System.getProperty("API_KEY");
    TestCase.assertNotNull(this.strApiKey);
    TestCase.assertFalse(this.strApiKey.isEmpty());

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
    this.startDate = String.format("%s 00:00:00", startDate);

    String endDate = dateFormat.format(dateYesterday);
    this.endDate = String.format("%s 23:59:59", endDate);
  }

  protected String getSessionToken()
    throws Exception
  {
    SdkConfig sdkConfig = SdkConfig.getInstance();

    String strApiKey = sdkConfig.getApiKey();
    SessionAuthenticate sessionAuthenticate = new SessionAuthenticate();
    TuneServiceResponse response = sessionAuthenticate.apiKey(strApiKey);
    String strSessonToken = response.getData().toString();

    return strSessonToken;
  }

  /**
   * Test provided authKey is not null.
   */
  public void test_ApiKey() {
    TestCase.assertNotNull(this.strApiKey);
  }

  /**
   * Test action "count".
   */
  public void test_Count() {
    TuneServiceResponse response = null;

    String strSessionToken = null;

    try {
      strSessionToken = this.getSessionToken();

      Map<String, Object> mapParamsCount = new HashMap<String, Object>();
      mapParamsCount.put("start_date", startDate);
      mapParamsCount.put("end_date", endDate);
      mapParamsCount.put("filter", null);
      mapParamsCount.put("response_timezone", "America/Los_Angeles");

      response = this.advertiserReport.count(
        strSessionToken,
        "session_token",
        mapParamsCount
      );
    } catch (TuneSdkException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneSdkException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (TuneServiceException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneServiceException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (Exception ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "Exception: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    }

    TestCase.assertNotNull(response);
    TestCase.assertEquals(200, response.getHttpCode());
    TestCase.assertNull(response.getErrors());
  }

  /**
   * Test endpoint's default fields.
   */
  public void test_Fields_Default() {
    String strFieldsDefault = null;

    String strSessionToken = null;
    try {
      strSessionToken = this.getSessionToken();

      strFieldsDefault = this.advertiserReport.getFields(
        strSessionToken,
        "session_token",
        EndpointBase.TUNE_FIELDS_DEFAULT
      );
    } catch (TuneSdkException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneSdkException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (TuneServiceException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneServiceException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (Exception ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "Exception: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    }

    TestCase.assertNotNull(strFieldsDefault);
    TestCase.assertFalse(strFieldsDefault.isEmpty());
  }

  /**
   * Test endpoint's recommended fields.
   */
  public void test_Fields_Recommended() {
    String strFieldsRecommended = null;

    String strSessionToken = null;
    try {
      strSessionToken = this.getSessionToken();

      strFieldsRecommended = this.advertiserReport.getFields(
        strSessionToken,
        "session_token",
        EndpointBase.TUNE_FIELDS_RECOMMENDED
      );
    } catch (TuneSdkException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneSdkException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (TuneServiceException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneServiceException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (Exception ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "Exception: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    }

    TestCase.assertNotNull(strFieldsRecommended);
    TestCase.assertFalse(strFieldsRecommended.isEmpty());
  }

  /**
   * Test this endpoint's action "find".
   */
  public void test_Find() {
    TuneServiceResponse response = null;

    String strSessionToken = null;

    try {
      strSessionToken = this.getSessionToken();

      // build sort
      Map<String, String> sort = new HashMap<String, String>();
      sort.put("created", "DESC");

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

      strSessionToken = this.getSessionToken();

      response = this.advertiserReport.find(
        strSessionToken,
        "session_token",
        mapParamsFind
      );

    } catch (TuneSdkException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneSdkException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (TuneServiceException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneServiceException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (Exception ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "Exception: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    }

    TestCase.assertNotNull(response);
    TestCase.assertEquals(200, response.getHttpCode());
    TestCase.assertNull(response.getErrors());
  }

  /**
   * Test this endpoint's action "export".
   */
  public void test_Export() {
    TuneServiceResponse response = null;

    String strSessionToken = null;

    try {
      strSessionToken = this.getSessionToken();

      // build fields
      String strFieldsRecommended = advertiserReport.getFields(
        strSessionToken,
        "session_token",
        EndpointBase.TUNE_FIELDS_RECOMMENDED
      );

      Map<String, Object> mapParamsExportCSV = new HashMap<String, Object>();
      mapParamsExportCSV.put("start_date", startDate);
      mapParamsExportCSV.put("end_date", endDate);
      mapParamsExportCSV.put("fields", strFieldsRecommended);
      mapParamsExportCSV.put("filter", null);
      mapParamsExportCSV.put("format", "csv");
      mapParamsExportCSV.put("response_timezone", "America/Los_Angeles");

      strSessionToken = this.getSessionToken();

      response = this.advertiserReport.export(
        strSessionToken,
        "session_token",
        mapParamsExportCSV
      );

    } catch (TuneSdkException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneSdkException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (TuneServiceException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneServiceException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (Exception ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "Exception: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    }

    TestCase.assertNotNull(response);
    TestCase.assertEquals(200, response.getHttpCode());
    TestCase.assertNotNull(response.getData());
    TestCase.assertNull(response.getErrors());

    String csvJobId  = null;

    try {
      csvJobId = AdvertiserReportLogClicks.parseResponseReportJobId(response);
    } catch (TuneSdkException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneSdkException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (TuneServiceException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneServiceException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (Exception ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "Exception: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    }

    TestCase.assertNotNull(csvJobId);

    String csvReportUrl  = null;
    try {
      strSessionToken = this.getSessionToken();

      response = this.advertiserReport.fetch(
        strSessionToken,
        "session_token",
        csvJobId             // Job ID
      );

    } catch (TuneSdkException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneSdkException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (TuneServiceException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneServiceException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (Exception ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "Exception: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    }

    TestCase.assertNotNull("Status should have response.", response);
    TestCase.assertEquals("Status successful HTTP code.", 200, response.getHttpCode());
    TestCase.assertNotNull("Status response should have data.", response.getData());
    TestCase.assertNull("Status response should have not errors.", response.getErrors());

    try {
      csvReportUrl = AdvertiserReportLogClicks.parseResponseReportUrl(response);
    } catch (TuneSdkException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneSdkException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (TuneServiceException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "TuneServiceException: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    } catch (Exception ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      TestCase.fail(
          String.format(
            "Exception: Message: \"%s\", Stack Trace: %s",
            ex.getMessage(),
            errors.toString()
          )
      );
    }

    TestCase.assertNotNull("Should have CSV Report URL", csvReportUrl);
  }

}
