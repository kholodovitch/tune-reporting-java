/**
 * TestAccountUsers.java
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
 * @package   tune.tests
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-21 11:11:02 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk;

import java.util.Map;
import java.util.HashMap;

import java.io.StringWriter;
import java.io.PrintWriter;

import java.lang.Throwable;

import com.tune.sdk.management.api.account.Users;
import com.tune.sdk.management.shared.endpoints.EndpointBase;
import com.tune.sdk.management.shared.service.TuneManagementResponse;

import com.tune.sdk.shared.TuneSdkException;
import com.tune.sdk.shared.TuneServiceException;

import junit.framework.TestCase;

/**
 *
 */
public class TestItemsAccountUsers extends TestCase {

    public static void main(String args[])
    {
    }

    /** The api_key. */
    private String api_key = null;

    /** account_users. */
    private Users account_users = null;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        this.api_key = System.getProperty("API_KEY");

        this.account_users = new Users(this.api_key, true);
    }

    /**
     *
     * Test access to application configuration file.
     */
    public void test_ApiKey()
    {
        TestCase.assertNotNull(this.api_key);
    }

    public void test_Fields_Default()
    {
        String str_fields_default = null;
        try {
            str_fields_default = this.account_users.getFields(EndpointBase.TUNE_FIELDS_DEFAULT);
        } catch (TuneSdkException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("TuneSdkException: %s, %s", ex.getMessage(), errors.toString()));
        } catch (TuneServiceException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("TuneServiceException: %s, %s", ex.getMessage(), errors.toString()));
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("Exception: %s, %s", ex.getMessage(), errors.toString()));
        }

        TestCase.assertNotNull(str_fields_default);
        TestCase.assertFalse(str_fields_default.isEmpty());
    }

    public void test_Fields_Recommended()
    {
        String str_fields_recommended = null;
        try {
            str_fields_recommended = this.account_users.getFields(EndpointBase.TUNE_FIELDS_RECOMMENDED);
        } catch (TuneSdkException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("TuneSdkException: %s, %s", ex.getMessage(), errors.toString()));
        } catch (TuneServiceException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("TuneServiceException: %s, %s", ex.getMessage(), errors.toString()));
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("Exception: %s, %s", ex.getMessage(), errors.toString()));
        }

        TestCase.assertNull(str_fields_recommended);
    }

    public void test_Count()
    {
        TuneManagementResponse response = null;
        try {
            response = this.account_users.count(
                null    // filter
            );
        } catch (TuneSdkException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("TuneSdkException: %s, %s", ex.getMessage(), errors.toString()));
        } catch (TuneServiceException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("TuneServiceException: %s, %s", ex.getMessage(), errors.toString()));
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("Exception: %s, %s", ex.getMessage(), errors.toString()));
        }

        TestCase.assertNotNull(response);
        TestCase.assertEquals(200, response.getHttpCode());
        TestCase.assertNull(response.getErrors());
    }

    public void test_Find()
    {
        TuneManagementResponse response = null;
        try {
            // build sort
            Map<String, String> sort = new HashMap<String, String> ();
            sort.put("created", "DESC");

            // build fields
            String str_fields_default = account_users.getFields(EndpointBase.TUNE_FIELDS_DEFAULT);

            response = account_users.find(
                str_fields_default,             // fields
                null,                           // filter
                5,                              // limit
                0,                              // page
                sort                            // sort
            );
        } catch (TuneSdkException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("TuneSdkException: %s, %s", ex.getMessage(), errors.toString()));
        } catch (TuneServiceException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("TuneServiceException: %s, %s", ex.getMessage(), errors.toString()));
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            TestCase.fail(String.format("Exception: %s, %s", ex.getMessage(), errors.toString()));
        }

        TestCase.assertNotNull(response);
        TestCase.assertEquals(200, response.getHttpCode());
        TestCase.assertNull(response.getErrors());
    }

    public void test_Export()
    {

    }
}
