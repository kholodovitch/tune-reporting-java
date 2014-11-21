/**
 * TestClientAccountUsers.java
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
 * @version   $Date: 2014-11-04 17:53:13 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk;

import java.util.*;

import com.tune.sdk.shared.*;
import com.tune.sdk.management.shared.service.*;

import junit.framework.TestCase;

/**
 *
 */
public class TestClientAccountUsers extends TestCase {

    public static void main(String args[])
    {
    }

    /** The api_key. */
    private String api_key = null;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        this.api_key = System.getProperty("API_KEY");
    }


    /**
     *
     * Test access to application configuration file.
     */
    public void test_ApiKey()
    {
        TestCase.assertNotNull(this.api_key);
    }

    /**
     *
     */
    public void test_Client() {

        boolean isSuccess = false;
        TuneManagementResponse response = null;
        try {
            String controller = "account/users";
            String action = "find.json";

            Map<String, String> query_string_dict = new HashMap<String, String>();

            query_string_dict.put("fields", "first_name,last_name,email");
            query_string_dict.put("limit", "5");

            TuneManagementClient client = new TuneManagementClient(
                controller,
                action,
                this.api_key,
                query_string_dict
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
