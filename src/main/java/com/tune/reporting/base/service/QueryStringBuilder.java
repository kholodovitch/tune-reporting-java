package com.tune.reporting.base.service;

/**
 * QueryStringBuilder.java
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
 * @version   $Date: 2014-12-24 13:23:15 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.base.endpoints.EndpointBase;
import com.tune.reporting.helpers.TuneSdkException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Helper utility for building query string intended for
 * TUNE Mangement API request.
 */
public class QueryStringBuilder {

  /** Buffer for holding constructed query string. */
  private String strQueryString = "";

  /**
   * Constructor.
   */
  public QueryStringBuilder() {

  }

  /**
   * Constructor.
   *
   * @param name  Query string parameter's name.
   * @param value Query string parameter's value.
   */
  public QueryStringBuilder(
      final String name,
      final String value
  ) {
    encode(name, value);
  }

  /**
   * Add element to query string.
   *
   * @param name  Query string parameter's name.
   * @param value Query string parameter's value.
   *
   * @throws IllegalArgumentException If invalid value is
   * provided to a parameter.
   * @throws TuneSdkException If fails to post request.
   */
  public final void add(
      String name,
      String value
  ) throws  IllegalArgumentException,
            TuneSdkException {

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
        String[] sortParameters = value.split("\\,");
        if (sortParameters.length > 0) {
          for (String sortParameter : sortParameters) {
            String[] sortParameterParts = sortParameter.split("\\:");
            if (sortParameterParts.length > 0) {
              String sortField = sortParameterParts[0];
              String sortDirection = sortParameterParts[1];

              sortDirection = sortDirection.toUpperCase();

              if (!EndpointBase.SORT_DIRECTIONS.contains(sortDirection)) {
                throw new IllegalArgumentException(
                  String.format(
                      "Invalid 'sort' direction: '%s'.", sortDirection
                  )
               );
              }

              String sortName = String.format("sort[%s]", sortField);
              String sortValue = sortDirection;

              this.encode(sortName, sortValue);
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
   * URL query string element's name and value.
   *
   * @param name  Encode query string parameter's name.
   * @param value Encode query string parameter's value.
   */
  private void encode(
      final String name,
      final String value
  ) {
    try {
      if (this.strQueryString != null && !this.strQueryString.isEmpty()) {
        this.strQueryString += "&";
      }

      this.strQueryString += URLEncoder.encode(name, "UTF-8");
      this.strQueryString += "=";
      this.strQueryString += URLEncoder.encode(value, "UTF-8");
    } catch (UnsupportedEncodingException ex) {
      throw new RuntimeException("Broken VM does not support UTF-8");
    }
  }

  /**
   * Get query string.
   *
   * @return String
   */
  public final String getQuery() {
    return this.strQueryString;
  }

  /**
   * Get a String object representing this object.
   *
   * @return String
   */
  public final String toString() {
    return this.getQuery();
  }
}
