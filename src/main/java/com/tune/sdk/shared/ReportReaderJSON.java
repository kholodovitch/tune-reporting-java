package com.tune.sdk.shared;

/**
 * ReportReaderJSON.java
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
 * @package   com.tune.sdk.shared
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-24 09:34:47 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import java.net.URL;
import java.net.MalformedURLException;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Read remote JSON report with provided url.
 */
public class ReportReaderJSON extends ReportReaderBase
{
	/**
	 * Constructor
	 *
	 * @param report_url
	 */
    public ReportReaderJSON(
        String report_url
    ) {
        super(
            report_url
        );
    }

    /**
     * Using provided report download URL, extract contents appropriate to the content's format.
     *
     * @return Boolean
     */
    public Boolean read() throws TuneSdkException
    {
        try {
            URL url_json_report = new URL(this.getReportUrl());
            BufferedReader br_json = new BufferedReader(new InputStreamReader(url_json_report.openStream()));
            String cvsSplitBy = ",";

            List<String> list_lines_json = new ArrayList<String>();

            String br_json_line;
            while ((br_json_line = br_json.readLine()) != null) { // while loop begins here
                list_lines_json.add(br_json_line);
            } // end while

            if (list_lines_json.size() == 0) {
                return false;
            }

            String json_str = list_lines_json.get(0);

            JSONArray json_array = new JSONArray(json_str);

            if (json_array.length() == 0) {
                return false;
            }

            for (int i = 0; i < json_array.length(); i++) {
                JSONObject json_item = json_array.getJSONObject(i);

                Iterator<?> keys = json_item.keys();
                Map<String, String> json_item_map = new HashMap<String, String>();

                while( keys.hasNext() ){
                    String key = (String)keys.next();
                    Object value = json_item.get(key);
                    json_item_map.put(key, value.toString());
                }

                this.report_map_list.add(json_item_map);
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