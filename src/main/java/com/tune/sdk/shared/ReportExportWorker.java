/**
 * ReportExportWorker.java
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
 * @package   tune.shared
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-19 21:21:08 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk.shared;

import org.json.*;

import java.util.*;
import java.lang.reflect.*;

import com.tune.sdk.management.shared.service.*;
import com.tune.sdk.management.shared.endpoints.*;

/**
 *
 */
public class ReportExportWorker {

    /**
     * @var null|string
     */
    private String export_controller = null;
    /**
     * @var null|string
     */
    private String export_action = null;
    /**
     * @var null|string
     */
    private String api_key = null;
    /**
     * @var null|string
     */
    private String job_id = null;
    /**
     * @var int
     */
    private int sleep = 10;

    /**
     * @var Boolean
     */
    private Boolean verbose = false;

    /**
     * @var object @see TuneManagementResponse
     */
    private TuneManagementResponse response = null;

    /**
     * Constructor
     *
     * @param export_controller      Controller for report export status.
     * @param export_action          Action for report export status.
     * @param api_key                MobileAppTracking API Key
     * @param job_id                 Provided Job Identifier to reference requested report on export queue.
     * @param verbose                Debug purposes only to view progress of job on export queue.
     * @param sleep                  Polling delay between querying job status on export queue.
     */
    public ReportExportWorker(
        String export_controller,
        String export_action,
        String api_key,
        String job_id,
        Boolean verbose,
        int sleep
    ) {
        if ( (null == export_controller) || export_controller.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'export_controller' is not defined.");
        }
        if ( (null == export_action) || export_action.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'export_action' is not defined.");
        }
        if ( (null == api_key) || api_key.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'api_key' is not defined.");
        }
        if ( (null == job_id) || job_id.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'job_id' is not defined.");
        }

        this.export_controller = export_controller;
        this.export_action = export_action;

        this.api_key = api_key;
        this.job_id = job_id;
        this.sleep = sleep;

        this.verbose = verbose;
        this.response = null;
    }

    /**
     * Poll export status and upon completion gather download URL referencing requested report.
     * @throws TuneSdkException
     * @throws TuneServiceException
     *
     * @throws \Tune\Shared\TuneSdkException
     * @throws \Tune\Shared\TuneServiceException
     */
    public Boolean run() throws TuneSdkException, TuneServiceException
    {
        String status = null;
        TuneManagementResponse response = null;
        int attempt = 0;

        Map<String,String> query_string_dict = new HashMap<String,String>();
        query_string_dict.put("job_id", job_id);

        TuneManagementClient client = null;
        try {
            client = new TuneManagementClient(
                this.export_controller,
                this.export_action,
                this.api_key,
                query_string_dict
            );
        } catch (IllegalArgumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        while (true) {
            try {
                client.call();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            response = client.getResponse();

            // Failed to return response.
            if (null == response) {
                throw new TuneSdkException("No response returned from export request.");
            }

            String request_url = response.getRequestUrl();
            int response_http_code = response.getHttpCode();

            // Failed to get successful service response.
            if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
                throw new TuneServiceException(
                    String.format("Service failed request: %d: %s", response.getHttpCode(), response.toString())
                );
            }

            // Failed to get data.
            JSONObject jdata = (JSONObject) response.getData();
            if (null == jdata) {
                throw new TuneServiceException("Report request failed to get export data.");
            }

            try {
                status = jdata.getString("status");
            } catch (JSONException ex) {
                throw new TuneSdkException(ex.getMessage(), ex);
            } catch (Exception ex) {
                throw new TuneSdkException(ex.getMessage(), ex);
            }

            System.out.println(
                String.format("\n= status: %s", status)
            );

            if (status.equals("fail") || status.equals("complete")) {
                break;
            }

            attempt += 1;
            if (this.verbose) {
                System.out.println(
                    String.format("\n= attempt: %d, response: %s", attempt, response.toString())
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
                String.format("\n= status: %s, response: %s", status, response.toString())
            );
        }

        if (this.verbose) {
            System.out.println(
                String.format("\n= attempt: %d, response: %s", attempt, response.toString())
            );
        }

        this.response = response;

        return true;
    }

    /**
     * Property that will hold completed report downloaded from Management API service.
     *
     * @return object
     */
    public TuneManagementResponse getResponse()
    {
        return this.response;
    }
}