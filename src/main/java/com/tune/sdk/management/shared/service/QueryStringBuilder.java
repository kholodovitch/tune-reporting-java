package com.tune.sdk.management.shared.service;

/**
 * QueryStringBuilder.java
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
 * @package   com.tune.sdk.management.shared.service
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-24 09:34:47 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.tune.sdk.management.shared.endpoints.EndpointBase;
import com.tune.sdk.shared.TuneSdkException;

public class QueryStringBuilder {

    private String query_string = "";

    /**
     * Constructor
     */
    public QueryStringBuilder() {
    }

    /**
     * Constructor
     */
    public QueryStringBuilder(String name, String value) {
        encode(name, value);
    }

    /**
     * Add element to query string.
     * @throws TuneSdkException
     */
    public void add(String name, String value) throws TuneSdkException {

        if (null == name) {
            throw new IllegalArgumentException(
                String.format("Parameter 'name' must be defined.")
            );
        }
        name = name.trim();
        if (null == name || name.isEmpty()) {
            throw new IllegalArgumentException(
                String.format("Parameter 'name' must be defined.")
            );
        }
        if (null == value || value.isEmpty()) {
            return;
        }
        value = value.trim();
        try {
            if (name.equals("fields")) {
                value = value.replaceAll("\\s+", "");
                this.encode(name, value);
            } else if (name.equals("group")) {
                value = value.replaceAll("\\s+", "");
                this.encode(name, value);
            } else if (name.equals("sort")) {
                String[] sort_parameters = value.split("\\,");
                if (sort_parameters.length > 0) {
                    for (String sort_parameter : sort_parameters) {
                        String[] sort_parameter_parts = sort_parameter.split("\\:");
                        if (sort_parameter_parts.length > 0) {
                            String sort_field = sort_parameter_parts[0];
                            String sort_direction = sort_parameter_parts[1];

                            sort_direction = sort_direction.toUpperCase();

                            if (!EndpointBase.sort_directions.contains(sort_direction)) {
                                throw new IllegalArgumentException(
                                    String.format("Invalid 'sort' direction: '%s'.", sort_direction)
                                );
                            }

                            String sort_name = String.format("sort[%s]", sort_field);
                            String sort_value = sort_direction;

                            this.encode(sort_name, sort_value);
                        }
                    }
                }
            } else if (name.equals("sort")) {
                value = value.replaceAll("\\s+", " ");
                encode(name, value);
            } else {
                encode(name, value);
            }
        } catch (Exception ex) {
            throw new TuneSdkException(
                String.format("Failed to add query string parameter (%s, %s): %s.",
                        name,
                        value,
                        ex.getMessage()
                        ),
                ex
            );
        }
    }

    /**
     * URL query string element's name and value
     *
     * @param name
     * @param value
     */
    private void encode(String name, String value) {
        try {
            if (this.query_string != null && !this.query_string.isEmpty()) {
                this.query_string += "&";
            }

            this.query_string += URLEncoder.encode(name, "UTF-8");
            this.query_string += "=";
            this.query_string += URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    /**
     * Get query string.
     * @return String
     */
    public String getQuery() {
        return this.query_string;
    }

    /**
     * Get a String object representing this object.
     *
     * @return
     */
    public String toString() {
        return this.getQuery();
    }
}