package com.tune.reporting;

/**
 * TestTuneServiceClient.java
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

import com.tune.reporting.base.service.TuneServiceClient;
import com.tune.reporting.base.service.TuneServiceResponse;
import com.tune.reporting.helpers.SdkConfig;
import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.base.endpoints.AdvertiserReportBase;
import com.tune.reporting.api.SessionAuthenticate;

import junit.framework.TestCase;

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
 * Test TuneServiceClient to connect with 'account/users'.
 */
public class TestTuneServiceClient extends TestCase {

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
   * Test client.
   */
  public void test_Client() {

    boolean isSuccess = false;
    TuneServiceResponse response = null;

    String strSessionToken = null;

    try {
      String controller = "account/users";
      String action = "find.json";

      Map<String, String> mapQueryString = new HashMap<String, String>();

      mapQueryString.put("fields", "first_name,last_name,email");
      mapQueryString.put("limit", "5");

      TuneServiceClient client = new TuneServiceClient(
          controller,
          action,
          mapQueryString
      );

      strSessionToken = this.getSessionToken();

      isSuccess = client.call(
        strSessionToken,
        "session_token"
      );
      response = client.getResponse();
    } catch (TuneSdkException ex) {
      TestCase.fail("TuneSdkException: " + ex.getMessage());
    } catch (Exception ex) {
      TestCase.fail("Exception: " + ex.getMessage());
    }

    TestCase.assertTrue(isSuccess);
    TestCase.assertNotNull(response);
  }
}
