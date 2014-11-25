package com.tune.sdk.examples;

/**
 * ExampleClientAccountUsers.java
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
 * @package   com.tune.sdk
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-24 09:34:47 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.util.*;

import com.tune.sdk.shared.*;
import com.tune.sdk.management.shared.service.*;

/**
 * Example using TuneManagementClient to connect with 'account/users'
 */
public class ExampleClientAccountUsers {

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception
     */
    public static void main(String args[]) {

        String api_key = null;

        if (args.length > 0) {
            api_key = args[0];
            if (!api_key.matches("[a-zA-Z0-9]+")) {
                throw new IllegalArgumentException(
                    String.format("Invalid [api_key]: '%s'", api_key)
                );
            }
        } else {
            throw new IllegalArgumentException("Missing [api_key]");
        }

        System.out.println( String.format("api_key = '%s'", api_key) );

        System.out.println( "\n============================================" );
        System.out.println(   "= Tune Management Client Account Users     =" );
        System.out.println(   "============================================" );

        try {
            String controller = "account/users";
            String action = "find.json";

            Map<String, String> query_string_dict = new HashMap<String, String>();

            query_string_dict.put("fields", "first_name,last_name,email");
            query_string_dict.put("limit", "5");

            TuneManagementClient client = new TuneManagementClient(
                controller,
                action,
                api_key,
                query_string_dict
            );

            client.call();

            System.out.println( client.getResponse().toString() );

        } catch ( TuneSdkException e ) {
            System.out.println( "TuneSdkException: '" + e.getMessage() + "'" );
            System.out.print( "Stack Trace: " + Arrays.toString(e.getStackTrace()) );
        } catch ( Exception e ) {
            System.out.println( "Exception: '" + e.getMessage() + "'" );
            System.out.print( "Stack Trace: " + Arrays.toString(e.getStackTrace()) );
        }

        System.out.println(   "============================================" );
        System.out.println(   "=   The End                                =" );
        System.out.println(   "============================================" );
    }
}
