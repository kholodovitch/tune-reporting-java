package com.tune.reporting.helpers;

/**
 * ReportReaderCsv.java
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


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Read remote CSV report.
 */
public class ReportReaderCsv extends ReportReaderBase {
  /**
   * Constructor.
   *
   * @param reportUrl Report URL provide upon completion on Export queue.
   */
  public ReportReaderCsv(
      String reportUrl
 ) {
    super(
      reportUrl
   );
  }

  /**
  * Returns a null when the input stream is empty.
  */
  public static List<String> parseLine(
      BufferedReader csvBufferReader
 ) throws Exception {
    int ch = csvBufferReader.read();
    while (ch == '\r') {
      ch = csvBufferReader.read();
    }
    if (ch < 0) {
      return null;
    }
    List<String> store = new ArrayList<String>();
    StringBuffer curVal = new StringBuffer();
    boolean inquotes = false;
    boolean started = false;
    while (ch >= 0) {
      if (inquotes) {
        started = true;
        if (ch == '\"') {
          inquotes = false;
        } else {
          curVal.append((char)ch);
        }
      } else {
        if (ch == '\"') {
          inquotes = true;
          if (started) {
            // if this is the second quote in a value, add a quote
            // this is for the double quote in the middle of a value
            curVal.append('\"');
          }
        } else if (ch == ',') {
          store.add(curVal.toString());
          curVal = new StringBuffer();
          started = false;
        } else if (ch == '\r') {
          //ignore LF characters
        } else if (ch == '\n') {
          //end of a line, break out
          break;
        } else {
          curVal.append((char)ch);
        }
      }
      ch = csvBufferReader.read();
    }
    store.add(curVal.toString());
    return store;
  }

  /**
   * Using provided report download URL, extract contents appropriate
   * to the content's format.
   *
   * @return Boolean  If successful in reading remote report returns true.
   */
  public Boolean read()
    throws TuneSdkException {
    try {
      URL csvReportUrl = new URL(this.getReportUrl());
      Reader in = new InputStreamReader(csvReportUrl.openStream(), "UTF-8");
      BufferedReader csvBufferReader = new BufferedReader(in);

      List<List<String>> csvLines
          = new ArrayList<List<String>>();

      List<String> csvLineValues
          = ReportReaderCsv.parseLine(csvBufferReader);

      while (csvLineValues != null) {
        csvLines.add(csvLineValues);
        csvLineValues = ReportReaderCsv.parseLine(csvBufferReader);
      }

      List<String> csvHeaders = csvLines.get(0);

      for (int i = 1; i < csvLines.size(); i++) {
        Map<String, String> csvItemMap = new HashMap<String, String>();
        List<String> csvLineItems = csvLines.get(i);
        for (int j = 0; j < csvLineItems.size(); j++) {
          csvItemMap.put(csvHeaders.get(j), csvLineItems.get(j));
        }

        this.reportMapList.add(csvItemMap);
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
