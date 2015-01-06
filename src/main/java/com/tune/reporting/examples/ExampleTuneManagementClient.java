package com.tune.reporting.examples;

/**
 * ExampleTuneManagementClient.java
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
 * @version   $Date: 2015-01-05 22:52:04 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.api.SessionAuthenticate;
import com.tune.reporting.base.service.TuneManagementClient;
import com.tune.reporting.base.service.TuneManagementResponse;
import com.tune.reporting.helpers.SdkConfig;
import com.tune.reporting.helpers.TuneSdkException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Example using TuneManagementClient to connect with 'account/users'.
 */
public final class ExampleTuneManagementClient {

  /**
   * The request has succeeded.
   */
  public static final int HTTP_STATUS_OK = 200;

  /**
   * Constructor.
   */
  private ExampleTuneManagementClient() {
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
    String apiKey = null;

    SdkConfig sdkConfig = SdkConfig.getInstance();

    if (args.length > 0) {
      apiKey = args[0];
      sdkConfig.setApiKey(apiKey);
    }

    apiKey = sdkConfig.getAuthKey();

    SessionAuthenticate sessionAuthenticate = new SessionAuthenticate();
    TuneManagementResponse response = sessionAuthenticate.apiKey(apiKey);
    String sessonToken = response.getData().toString();
    System.out.println(sessonToken);

    sdkConfig.setApiKey(apiKey);
    run();

    sdkConfig.setSessionToken(sessonToken);
    run();
  }

  public static void run() throws Exception {

    SdkConfig sdkConfig = SdkConfig.getInstance();

    String authKey = sdkConfig.getAuthKey();
    String authType = sdkConfig.getAuthType();

    System.out.println(
        "\033[34m" + "================================" + "\033[0m"
    );
    System.out.println(
        "\033[34m" + " Begin TUNE Management Client   " + "\033[0m"
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

      TuneManagementClient client = new TuneManagementClient(
          controller,
          action,
          authKey,
          authType,
          mapQueryString
      );

      client.call();

      TuneManagementResponse response = client.getResponse();

      System.out.println(" TuneManagementResponse:");
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
