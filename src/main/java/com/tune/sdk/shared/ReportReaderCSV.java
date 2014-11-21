/**
 * ReportReaderCSV.java
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
 * @version   $Date: 2014-11-21 11:11:02 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk.shared;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.net.URL;
import java.net.MalformedURLException;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 */
public class ReportReaderCSV extends ReportReaderBase
{
    public ReportReaderCSV(
        String report_url
    ) {
        super(
            report_url
        );
    }

    /**
    * Returns a null when the input stream is empty
    */
    public static List<String> parseLine(BufferedReader r) throws Exception {
        int ch = r.read();
        while (ch == '\r') {
            ch = r.read();
        }
        if (ch<0) {
            return null;
        }
        List<String> store = new ArrayList<String>();
        StringBuffer curVal = new StringBuffer();
        boolean inquotes = false;
        boolean started = false;
        while (ch>=0) {
            if (inquotes) {
                started=true;
                if (ch == '\"') {
                    inquotes = false;
                }
                else {
                    curVal.append((char)ch);
                }
            }
            else {
                if (ch == '\"') {
                    inquotes = true;
                    if (started) {
                        // if this is the second quote in a value, add a quote
                        // this is for the double quote in the middle of a value
                        curVal.append('\"');
                    }
                }
                else if (ch == ',') {
                    store.add(curVal.toString());
                    curVal = new StringBuffer();
                    started = false;
                }
                else if (ch == '\r') {
                    //ignore LF characters
                }
                else if (ch == '\n') {
                    //end of a line, break out
                    break;
                }
                else {
                    curVal.append((char)ch);
                }
            }
            ch = r.read();
        }
        store.add(curVal.toString());
        return store;
    }

    /**
     * Using provided report download URL, extract contents appropriate to the content's format.
     *
     * @return Boolean
     */
    public Boolean read() throws TuneSdkException
    {
        try {
            URL url_csv_report = new URL(this.getReportUrl());
            Reader in = new InputStreamReader(url_csv_report.openStream(), "UTF-8");
            BufferedReader br_csv = new BufferedReader(in);

            List<List<String>> csv_lines = new ArrayList<List<String>>();

            List<String> csv_line_values = ReportReaderCSV.parseLine(br_csv);

            while (csv_line_values!=null) {
                csv_lines.add(csv_line_values);
                csv_line_values = ReportReaderCSV.parseLine(br_csv);
            }

            List<String> csv_headers = csv_lines.get(0);

            for(int i = 1; i < csv_lines.size(); i++) {
                Map<String, String> csv_item_map = new HashMap<String, String>();
                List<String> csv_line_items = csv_lines.get(i);
                for(int j=0; j<csv_line_items.size(); j++) {
                    csv_item_map.put(csv_headers.get(j), csv_line_items.get(j));
                }

                this.report_map_list.add(csv_item_map);
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