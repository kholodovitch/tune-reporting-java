package com.tune.reporting;

/**
 * TestAdvertiserReportCohortValue.java
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
 * @version   $Date: 2015-01-05 09:40:09 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.api.AdvertiserReportCohortValue;
import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneManagementResponse;

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
 * Test of tune.reporting.api.AdvertiserReportCohortValue.
 */
public class TestAdvertiserReportCohortValue extends TestCase {

  public static void main(final String[] args) {
  }

  /** Instance. */
  private AdvertiserReportCohortValue advertiserReport = null;

  /** Start Date. */
  private String startDate = null;

  /** End Date. */
  private String endDate = null;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() {
    String apiKey = System.getProperty("API_KEY");
    TestCase.assertNotNull(apiKey);
    TestCase.assertFalse(apiKey.isEmpty());

    try {
      SdkConfig sdkConfig = SdkConfig.getInstance();
      sdkConfig.setApiKey(apiKey);
    } catch (TuneSdkException ex) {
      TestCase.fail("TuneSdkException: " + ex.getMessage());
    } catch (Exception ex) {
      TestCase.fail("Exception: " + ex.getMessage());
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
    this.startDate = String.format("%s 00:00:00", startDate);

    String endDate = dateFormat.format(dateYesterday);
    this.endDate = String.format("%s 23:59:59", endDate);

    try {
      this.advertiserReport = new AdvertiserReportCohortValue();
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
  }

  /**
   * Test action "count".
   */
  public void test_Count() {
    TuneManagementResponse response = null;

    try {
      response = this.advertiserReport.count(
        startDate,
        endDate,
        "click",        // cohortType
        "year_day",       // cohortInterval
        "site_id,publisher_id", // group
        "(publisher_id > 0)",   // filter
        "America/Los_Angeles"
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
   * Test endpoint's all fields.
   */
  public void test_Fields_All() {
    String strFieldsAll = null;
    try {
      strFieldsAll = this.advertiserReport.getFields(EndpointBase.TUNE_FIELDS_ALL);
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

    TestCase.assertNotNull(strFieldsAll);
    TestCase.assertFalse(strFieldsAll.isEmpty());
  }

  /**
   * Test endpoint's recommended fields.
   */
  public void test_Fields_Recommended() {
    String strFieldsRecommended = null;
    try {
      strFieldsRecommended = this.advertiserReport.getFields(EndpointBase.TUNE_FIELDS_RECOMMENDED);
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
    TuneManagementResponse response = null;

    try {
      // build sort
      Map<String, String> sort = new HashMap<String, String>();
      sort.put("created", "DESC");

      // build fields
      String strFieldsRecommended = advertiserReport.getFields(EndpointBase.TUNE_FIELDS_RECOMMENDED);

      response = this.advertiserReport.find(
        startDate,
        endDate,
        "click",        // cohortType
        "year_day",       // cohortInterval
        "cumulative",       // aggregationType
        strFieldsRecommended, // fields
        "site_id,publisher_id", // group
        "(publisher_id > 0)",   // filter
        5,            // limit
        0,            // page
        null,           // sort
        "America/Los_Angeles"   // responseTimezone
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
    TuneManagementResponse response = null;

    try {
      // build fields
      String strFieldsRecommended = advertiserReport.getFields(EndpointBase.TUNE_FIELDS_RECOMMENDED);

      response = this.advertiserReport.export(
        startDate,
        endDate,
        "click",        // cohortType
        "year_day",       // cohortInterval
        "cumulative",       // aggregationType
        strFieldsRecommended, // fields
        "site_id,publisher_id", // group
        "(publisher_id > 0)",   // filter
        "America/Los_Angeles"   // responseTimezone
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
      csvJobId = AdvertiserReportCohortValue.parseResponseReportJobId(response);
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
      response = this.advertiserReport.fetch(
        csvJobId          // Job ID
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

    try {
      csvReportUrl = AdvertiserReportCohortValue.parseResponseReportUrl(response);
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

    TestCase.assertNotNull(csvReportUrl);
  }
}
