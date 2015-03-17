package com.tune.reporting.example;

/**
 * ExampleTuneServiceClient.java
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
 * @version   $Date: 2015-03-05 23:27:46 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.api.SessionAuthenticate;
import com.tune.reporting.base.service.TuneServiceClient;
import com.tune.reporting.base.service.TuneServiceResponse;
import com.tune.reporting.helpers.SdkConfig;
import com.tune.reporting.helpers.TuneSdkException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Example using TuneServiceClient to connect with 'account/users'.
 */
public final class ExampleTuneServiceClient extends ExampleReportBase {

  /**
   * Constructor.
   */
  private ExampleTuneServiceClient() {
    //not called
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(
      final String[] args
  ) throws Exception {
    if (args.length > 0) {
      String strApiKey = args[0];
      ExampleReportBase.setup(strApiKey);
    }

    run();
  }

  public static void run() throws Exception {

    String strSessionToken = null;

    System.out.println(
        "\033[34m" + "================================" + "\033[0m"
    );
    System.out.println(
        "\033[34m" + " Begin TUNE Service Client   " + "\033[0m"
    );
    System.out.println(
        "\033[34m" + "================================" + "\033[0m"
    );

    try {
      String controller = "account/users";
      String action = "find.json";

      Map<String, String> mapQueryString = new HashMap<String, String>();

      mapQueryString.put("fields", "first_name,last_name,email");
      mapQueryString.put("limit", "5");

      strSessionToken = ExampleReportBase.getSessionToken();

      TuneServiceClient client = new TuneServiceClient(
        controller,
        action,
        mapQueryString
      );

      client.call(
        strSessionToken,
        "session_token"
      );

      TuneServiceResponse response = client.getResponse();

      System.out.println(" TuneServiceResponse:");
      System.out.println(response.toString());

      System.out.println(" JSON:");
      System.out.println(response.toJson());

    } catch (TuneSdkException e) {
      System.out.println("TuneSdkException: '" + e.getMessage() + "'");
      System.out.print("Stack Trace: " + Arrays.toString(e.getStackTrace()));
    } catch (Exception e) {
      System.out.println("Exception: '" + e.getMessage() + "'");
      System.out.print("Stack Trace: " + Arrays.toString(e.getStackTrace()));
    }

    System.out.println("\033[32m" + "=====================" + "\033[0m");
    System.out.println("\033[32m" + " End Example         " + "\033[0m");
    System.out.println("\033[32m" + "=====================" + "\033[0m");
  }
}
