package com.tune.reporting.api;

/**
 * Export.java
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

import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.base.service.TuneServiceResponse;
import com.tune.reporting.helpers.TuneSdkException;

import java.util.HashMap;
import java.util.Map;

/**
 * TUNE Service API endpoint '/export/'.
 *
 */
public class Export extends EndpointBase {

  /**
   * Constructor.
   *
   */
  public Export(
  ) throws TuneSdkException {
    super(
      "export"
    );
  }

 /**
   * Action 'download' for polling export queue for status information
   * on request report to be exported.
   *
   * @param jobId Job identifier assigned for report export.
   *
   * @return TuneServiceResponse
   * @throws TuneSdkException If fails to post request.
   */
  public final TuneServiceResponse download(
    final String strAuthKey,
    final String strAuthType,
    final String jobId
  ) throws TuneSdkException {
    if ((null == jobId) || jobId.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'jobId' is not defined.");
    }

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("job_id", jobId);

    return super.call(
      "download",
      strAuthKey,
      strAuthType,
      mapQueryString
    );
  }
}
