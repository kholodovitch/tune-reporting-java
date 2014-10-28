/**
 * 
 *  UnitTest_Request.java
 *  Tune_API_Java
 *
 *  Tune SDK JUnit Test
 * 
 *  Copyright (c) 2014 Tune, Inc
 *  All rights reserved.
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions: 
 * 
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software. 
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */ 

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import java.util.*;

import tune.shared.*;
import tune.management.shared.service.*;

import junit.framework.TestCase;

/**
 * The Class UnitTest_Request.
 */
public class Test_Client extends TestCase {
    
    public static void main(String args[])
    {
    }
    
    /** The api_key. */
    private String api_key = null;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        this.api_key = System.getProperty("api_key");
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
    public void test_Request() {

        boolean isSuccess = false;
        Response response = null;
        try {
            String controller = "account/users";
            String action = "find.json";

            Map<String, String> query_data_map = new HashMap();
            
            query_data_map.put("fields", "first_name,last_name,email");
            query_data_map.put("limit", "5");
            
            TuneManagementClient client = new TuneManagementClient(
                controller,
                action,
                this.api_key,
                query_data_map
            );
            
            isSuccess = client.call();
            response = client.getResponse();
        } catch (TuneServiceException ex) {
            TestCase.fail("TuneServiceException: " + ex.getMessage());
        } catch (TuneSdkException ex) {
            TestCase.fail("TuneSdkException: " + ex.getMessage());
        } catch (Exception ex) {
            TestCase.fail("Exception: " + ex.getMessage());
        }
        
        TestCase.assertTrue(isSuccess);
        TestCase.assertNotNull(response);
    }
}
