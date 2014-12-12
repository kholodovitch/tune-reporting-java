package com.tune.reporting.examples;

/**
 * ExampleTuneManagementClient.java
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
import com.tune.reporting.helpers.TuneSdkException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Example using TuneManagementClient to connect with 'account/users'.
 */
public class ExampleTuneManagementClient {

  /**
   * The main method.
   *
   * @param args the arguments
   * @throws Exception  If example should fail.
   */
  public static void main(final String[] args) {

    String apiKey = null;

    if (args.length > 0) {
      apiKey = args[0];
      if (!apiKey.matches("[a-zA-Z0-9]+")) {
        throw new IllegalArgumentException(
          String.format("Invalid [apiKey]: '%s'", apiKey)
       );
      }
    } else {
      throw new IllegalArgumentException("Missing [apiKey]");
    }

    System.out.println(String.format("apiKey = '%s'", apiKey));

    System.out.println("============================================");
    System.out.println(" Tune Management Client Account Users   ");
    System.out.println("============================================");

    try {
      String controller = "account/users";
      String action = "find.json";

      Map<String, String> mapQueryString = new HashMap<String, String>();

      mapQueryString.put("fields", "first_name,last_name,email");
      mapQueryString.put("limit", "5");

      TuneManagementClient client = new TuneManagementClient(
          controller,
          action,
          apiKey,
          mapQueryString
     );

      client.call();

      System.out.println(client.getResponse().toString());

    } catch (TuneSdkException e) {
      System.out.println("TuneSdkException: '" + e.getMessage() + "'");
      System.out.print("Stack Trace: " + Arrays.toString(e.getStackTrace()));
    } catch (Exception e) {
      System.out.println("Exception: '" + e.getMessage() + "'");
      System.out.print("Stack Trace: " + Arrays.toString(e.getStackTrace()));
    }

    System.out.println("============================================");
    System.out.println("   The End                ");
    System.out.println("============================================");
  }
}
