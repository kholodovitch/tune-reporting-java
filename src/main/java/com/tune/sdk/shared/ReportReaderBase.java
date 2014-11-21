/**
 * ReportReaderBase.java
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

/**
 *
 */
public abstract class ReportReaderBase {

    protected String report_url = null;

    protected List<Map<String, String>> report_map_list = new ArrayList<Map<String, String>>();

    public ReportReaderBase(
        String report_url
    ) {
        this.report_url = report_url;
    }

    /**
     * Using provided report download URL, extract contents appropriate
     * to the content's format.
     *
     * @return Boolean
     */
    public abstract Boolean read() throws TuneSdkException;

    /**
     * Using report data pulled from remote file referenced by download URL,
     * pretty print all its contents.
     *
     * @return void
     */
    public void prettyPrint() {
        this.prettyPrint(0);
    }

    /**
     * Using report data pulled from remote file referenced by download URL,
     * pretty print some of it's contents.
     *
     * @param int $limit Limit the number of rows to print.
     *
     * @return void
     */
    public void prettyPrint(int limit) {
        int max_limit = this.report_map_list.size();
        if (0 < limit) {
            max_limit = Math.min(limit, max_limit);
        }
        for(int i=0; i<max_limit; i++) {
            System.out.println(
                String.format( "%d: %s",
                    i + 1,
                    this.report_map_list.get(i)
                )
            );
        }
    }

    /**
     * Report URL provided by export status upon completion.
     *
     * @return String
     */
    public String getReportUrl()
    {
        return this.report_url;
    }

    /**
     * Report contents mapped.
     *
     * @return List<Map<String, String>>
     */
    public List<Map<String, String>> getReportMap()
    {
        return this.report_map_list;
    }

    /**
     * Report contents mapped size.
     *
     * @return int
     */
    public int getReportSize()
    {
        return this.report_map_list.size();
    }
}