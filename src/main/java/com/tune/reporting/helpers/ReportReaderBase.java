package com.tune.reporting.helpers;

/**
 * ReportReaderBase.java
 *
 * <p>
 * Copyright (c) 2014 TUNE, Inc.
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
 * @copyright 2014 TUNE, Inc. (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-12-21 22:34:43 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base abstract class for handling report reading.
 */
public abstract class ReportReaderBase {

  protected String reportUrl = null;

  protected List<Map<String, String>> reportMapList
      = new ArrayList<Map<String, String>>();

  public ReportReaderBase(
      final String reportUrl
 ) {
    this.reportUrl = reportUrl;
  }

  /**
   * Using provided report download URL, extract contents appropriate
   * to the content's format.
   *
   * @return Boolean  If successful in reading remote report returns true.
   */
  public abstract Boolean read() throws TuneSdkException;

  /**
   * Using report data pulled from remote file referenced by download URL,
   * pretty print all its contents.
   */
  public final void prettyPrint() {
    this.prettyPrint(0);
  }

  /**
   * Using report data pulled from remote file referenced by download URL,
   * pretty print some of it's contents.
   *
   * @param limit Limit the number of rows to print.
   */
  public final void prettyPrint(final int limit) {
    int maxLimit = this.reportMapList.size();
    if (0 < limit) {
      maxLimit = Math.min(limit, maxLimit);
    }
    for (int i = 0; i < maxLimit; i++) {
      System.out.println(
          String.format("%d: %s",
            i + 1,
            this.reportMapList.get(i)
         )
     );
    }
  }

  /**
   * Report URL provided by export status upon completion.
   *
   * @return String Report URL for export download.
   */
  public final String getReportUrl() {
    return this.reportUrl;
  }

  /**
   * Report contents mapped.
   *
   * @return List List of report contents.
   */
  public final List<Map<String, String>> getReportMap() {
    return this.reportMapList;
  }

  /**
   * Report contents mapped size.
   *
   * @return int Size of the report.
   */
  public final int getReportSize() {
    return this.reportMapList.size();
  }
}
