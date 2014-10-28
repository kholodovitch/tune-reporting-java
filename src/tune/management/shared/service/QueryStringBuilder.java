/**
 * TuneManagementClient.java
 * Tune_API_Java
 * 
 * @version  0.9.0
 * @link     http://www.tune.com
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
 */

package tune.management.shared.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import tune.shared.*;

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
     */
    public void add(String name, String value) {
        if (this.query_string != null && !this.query_string.isEmpty()) {
            this.query_string += "&";
        }
        
        encode(name, value);
    }
    
    /**
     * URL query string element's name and value
     */
    private void encode(String name, String value) {
        try {
            this.query_string += URLEncoder.encode(name, "UTF-8");
            this.query_string += "=";
            this.query_string += URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }
    
    public String getQuery() {
        return this.query_string;
    }
    
    public String toString() {
        return this.getQuery();
    }
}