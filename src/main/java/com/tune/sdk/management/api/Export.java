/**
 * Export.java
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
 * @package   tune.management.api
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-04 14:57:37 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

package com.tune.sdk.management.api;

import java.util.*;

import com.tune.sdk.management.shared.endpoints.*;
import com.tune.sdk.management.shared.service.*;

/**
 * Tune Management API endpoint '/export/'
 */
public class Export extends EndpointBase {

    /**
     * Constructor
     *
     * @param api_key MobileAppTracking API Key
     */
    public Export(
        String api_key
    ) {
        super(
            "export",
            api_key,
            false
        );
    }

   /**
     * Action 'download' for polling export queue for status information on request report to be exported.
     *
     * @param job_id Job identifier assigned for report export.
     *
     * @return object @see Response
     * @throws Exception
     * @throws \InvalidArgumentException
     */
    public TuneManagementResponse download(
        String job_id
    ) throws Exception {
        if ( (null == job_id) || job_id.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'job_id' is not defined.");
        }

        Map<String,String> query_string_dict = new HashMap<String,String>();
        query_string_dict.put("job_id", job_id);

        return super.call(
            "download",
            query_string_dict
        );
    }
}