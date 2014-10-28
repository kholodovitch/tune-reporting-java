/**
 * 
 *  Tune_Request_Example.java
 *  Tune_API_Java
 *  
 *  Example code using Tune SDK.
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
import java.util.Arrays;
import java.util.Properties;
import java.util.*;

import tune.shared.*;
import tune.management.shared.service.*;

public class Example_Client {

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception 
     */
    public static void main(String args[]) throws Exception {

        System.out.println( "\n======================================" );
        System.out.println(   "= Tune Management Client example     =" );
        System.out.println(   "======================================" );

        try {
            String controller = "account/users";
            String action = "find.json";
            String api_key = args[0];

            Map<String, String> query_data_map = new HashMap();
            
            query_data_map.put("fields", "first_name,last_name,email");
            query_data_map.put("limit", "5");
            
            TuneManagementClient client = new TuneManagementClient(
                controller,
                action,
                api_key,
                query_data_map
            );
            
            client.call();

            System.out.println( client.getResponse().toString() );

        } catch ( TuneServiceException e ) {
            System.out.println( "TuneServiceException: '" + e.getMessage() + "'" );
        } catch ( TuneSdkException e ) {
            System.out.println( "TuneSdkException: '" + e.getMessage() + "'" );
            System.out.print( "Stack Trace: " + Arrays.toString(e.getStackTrace()) );
        } catch ( Exception e ) {
            System.out.println( "Exception: '" + e.getMessage() + "'" );
            System.out.print( "Stack Trace: " + Arrays.toString(e.getStackTrace()) );
        }

        System.out.println(   "===============================" );        
        System.out.println(   "=   The End                   =" );
        System.out.println(   "===============================" );
    }    
}
