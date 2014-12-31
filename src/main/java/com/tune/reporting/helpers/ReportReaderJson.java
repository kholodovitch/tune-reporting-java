package com.tune.reporting.helpers;

/**
 * ReportReaderJson.java
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
 * @version   $Date: 2014-12-31 09:56:30 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Read remote JSON report with provided url.
 */
public final class ReportReaderJson extends ReportReaderBase {
  /**
   * Constructor.
   *
   * @param reportUrl Report URL provide upon completion on Export queue.
   */
  public ReportReaderJson(
      final String reportUrl
  ) {
    super(
      reportUrl
    );
  }

  /**
   * Using provided report download URL, extract contents appropriate
   * to the content's format.
   *
   * @return Boolean  If successful in reading remote report returns true.
   * @throws TuneSdkException If fails to read report.
   */
  public Boolean read()
    throws TuneSdkException {
    try {
      URL jsonReportUrl = new URL(this.getReportUrl());
      BufferedReader jsonBufferReader
          = new BufferedReader(new InputStreamReader(jsonReportUrl.openStream()));
      String cvsSplitBy = ",";

      List<String> jsonListLines = new ArrayList<String>();

      String jsonBufferReaderLine;
      while ((jsonBufferReaderLine = jsonBufferReader.readLine()) != null) {
        jsonListLines.add(jsonBufferReaderLine);
      } // end while

      if (jsonListLines.size() == 0) {
        return false;
      }

      String jsonStr = jsonListLines.get(0);

      JSONArray jsonArray = new JSONArray(jsonStr);

      if (jsonArray.length() == 0) {
        return false;
      }

      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonItem = jsonArray.getJSONObject(i);

        Iterator<?> keys = jsonItem.keys();
        Map<String, String> jsonItemMap = new HashMap<String, String>();

        while (keys.hasNext()) {
          String key = (String) keys.next();
          Object value = jsonItem.get(key);
          jsonItemMap.put(key, value.toString());
        }

        this.reportMapList.add(jsonItemMap);
      }

    } catch (MalformedURLException ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    } catch (IOException ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    } catch (Exception ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    }

    return true;
  }
}
