package com.tune.reporting;

/**
 * TestTuneManagementClient.java
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

import com.tune.reporting.base.service.TuneManagementClient;
import com.tune.reporting.base.service.TuneManagementResponse;
import com.tune.reporting.helpers.TuneSdkException;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Test TuneManagementClient to connect with 'account/users'.
 */
public class TestTuneManagementClient extends TestCase {

  public static void main(final String[] args) {
  }

  /** The apiKey. */
  private String apiKey = null;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() {
    this.apiKey = System.getProperty("API_KEY");
  }

  /**
   * Test provided apiKey is not null.
   */
  public void test_ApiKey() {
    TestCase.assertNotNull(this.apiKey);
  }

  /**
   * Test client.
   */
  public void test_Client() {

    boolean isSuccess = false;
    TuneManagementResponse response = null;
    try {
      String controller = "account/users";
      String action = "find.json";

      Map<String, String> mapQueryString = new HashMap<String, String>();

      mapQueryString.put("fields", "first_name,last_name,email");
      mapQueryString.put("limit", "5");

      TuneManagementClient client = new TuneManagementClient(
          controller,
          action,
          this.apiKey,
          mapQueryString
     );

      isSuccess = client.call();
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
