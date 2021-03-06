package com.tune.reporting.base.endpoints;

/**
 * ReportExportWorker.java
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

import com.tune.reporting.base.service.TuneServiceClient;
import com.tune.reporting.base.service.TuneServiceResponse;
import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.helpers.TuneServiceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for handling repeated status request for Report
 * job on Export queue.
 */
public class ReportExportWorker {

  /**
   * The request has succeeded.
   */
  public static final int HTTP_STATUS_OK = 200;

  /**
   * Export controller for handling Report export status.
   * @var String
   */
  private String exportController = null;

  /**
   * Export action for handling Report export status.
   * @var String
   */
  private String exportAction = null;

  /**
   * TUNE Reporting Authentication Key:
   * MobileAppTracking API_KEY or Session token.
   */
  private String authKey = null;

  /**
   * TUNE Reporting Authentication Type:
   * api_key OR session_token.
   */
  private String authType = null;

  /**
   * Report Job Identifier on Export queue.
   * @var String
   */
  private String jobId = null;

  /**
   * Sleep duration between requesting Export status.
   * @var int
   */
  private int sleep = 10;

  /**
   * When to stop polling requests of Export status.
   * @var int
   */
  private int timeout = 0;

  /**
   * Within console, provide verbose feedback of export status.
   * @var Boolean
   */
  private Boolean verbose = false;

  /**
   * Response pertaining to last export status request.
   * @var object @see TuneServiceResponse
   */
  private TuneServiceResponse response = null;

  /**
   * Constructor.
   *
   * @param exportController  Controller for report export status.
   * @param exportAction      Action for report export status.
   * @param authKey           TUNE Reporting Authentication Key.
   * @param authType          TUNE Reporting Authentication Type.
   * @param jobId             Provided Job Identifier to reference
   *                          requested report on export queue.
   * @param verbose           Debug purposes only to view
   *                          progress of job on export queue.
   * @param sleep             Polling delay between querying
   *                          job status on export queue.
   * @param timeout           When to stop polling.
   */
  public ReportExportWorker(
      final String exportController,
      final String exportAction,
      final String authKey,
      final String authType,
      final String jobId,
      final Boolean verbose,
      final int sleep,
      final int timeout
  ) {
    if ((null == exportController) || exportController.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'exportController' is not defined."
      );
    }
    if ((null == exportAction) || exportAction.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'exportAction' is not defined."
      );
    }
    if ((null == authKey) || authKey.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'authKey' is not defined.");
    }
    if ((null == authType) || authType.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'authType' is not defined.");
    }
    if ((null == jobId) || jobId.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'jobId' is not defined.");
    }

    this.exportController = exportController;
    this.exportAction = exportAction;

    this.authKey = authKey;
    this.authType = authType;
    this.jobId = jobId;
    this.sleep = sleep;
    this.timeout = timeout;

    this.verbose = verbose;
    this.response = null;
  }

  /**
   * Poll export status and upon completion gather download URL
   * referencing requested report.
   *
   * @return Boolean If True upon successful completion.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final Boolean run()
    throws  TuneSdkException,
            TuneServiceException {
    String status = null;
    TuneServiceResponse response = null;
    int attempt = 0;
    int timeout = 0;

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("job_id", jobId);

    TuneServiceClient client = null;
    try {
      client = new TuneServiceClient(
        this.exportController,
        this.exportAction,
        mapQueryString
     );
    } catch (IllegalArgumentException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    while (true) {
      if (this.timeout > 0) {
        if (timeout >= this.timeout) {
          throw new TuneSdkException(
            String.format(
              "Fetch request for Job ID '%s' has expired after '%d' seconds. Service request URL: %s",
              jobId,
              timeout,
              client.getServiceUrl()
            )
          );
        }

        timeout += sleep;
      }

      try {
        client.call(
          this.authKey,
          this.authType
        );
      } catch (Exception e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }

      response = client.getResponse();

      // Failed to return response.
      if (null == response) {
        throw new TuneSdkException("No response returned from export request.");
      }

      String requestUrl = response.getRequestUrl();
      int responseHttpCode = response.getHttpCode();

      // Failed to get successful service response.
      if ((response.getHttpCode() != HTTP_STATUS_OK)
          || (null != response.getErrors())
      ) {
        throw new TuneServiceException(
          String.format(
            "Service failed request: %d: %s",
            response.getHttpCode(),
            response.toString()
         )
       );
      }

      // Failed to get data.
      JSONObject jdata = (JSONObject) response.getData();
      if (null == jdata) {
        throw new TuneServiceException(
          "Report request failed to get export data."
        );
      }

      try {
        status = jdata.getString("status");
      } catch (JSONException ex) {
        throw new TuneSdkException(ex.getMessage(), ex);
      } catch (Exception ex) {
        throw new TuneSdkException(ex.getMessage(), ex);
      }

      if (this.verbose) {
        System.out.println(
            String.format("= status: %s", status)
        );
      }

      if (status.equals("fail") || status.equals("complete")) {
        break;
      }

      attempt += 1;
      if (this.verbose) {
        System.out.println(
          String.format(
            "= attempt: %d\n= timeout: %d\n= response: %s",
            attempt,
            timeout,
            response.toString()
          )
        );
      }

      try {
        Thread.sleep(this.sleep * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    if (!status.equals("complete")) {
      throw new TuneServiceException(
          String.format(
            "= status: %s, response: %s", status, response.toString()
          )
      );
    }

    if (this.verbose) {
      System.out.println(
        String.format(
          "= attempt: %d\n= timeout: %d\n= response: %s",
          attempt,
          timeout,
          response.toString()
        )
      );
    }

    this.response = response;

    return true;
  }

  /**
   * Property that will hold completed report downloaded
   * from Management API service.
   *
   * @return TuneServiceResponse
   */
  public final TuneServiceResponse getResponse() {
    return this.response;
  }
}
