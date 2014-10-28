/**
 * Response.java
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

import org.json.*;
import java.util.*;

import tune.shared.*;

/**
 * The Class Response.
 */
public class Response {

    /**
     * Property of actual HTTP uri requested by by Tune Management API.
     * @var string $request_url
     */
    private String      request_url;
    private String      response_raw;
    /**
     * Property of full JSON response returned from service of Tune Management API.
     * @var array $response_json
     */
    private JSONObject  response_json;
    /**
     * Property of HTTP response code returned from curl after completion for Tune Management API request.
     * @var integer $response_http_code
     */
    private int         response_http_code;
    /**
     * Property of HTTP response headers returned from curl after completion for Tune Management API request.
     * @var array $response_headers
     */
    private Map<String, List<String>> response_headers;

    /**
     *
     */
    public Response()
    {
        this.request_url        = null;
        this.response_raw       = null;
        this.response_json      = null;
        this.response_headers   = null;
        this.response_http_code = response_http_code;
    }

    public String getRequestUrl()
    {
        return this.request_url;
    }
    public void setRequestUrl(String request_url)
    {
        this.request_url = request_url;
    }
    /**
     *
     * @return
     */
    public String getRaw()
    {
        return this.response_raw;
    }
    public void setRaw(String raw)
    {
        this.response_raw = raw;

        try {
            this.response_json = new JSONObject(this.response_raw);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return
     */
    public JSONObject getJSON()
    {
        return this.response_json;
    }
    public void setJSON(JSONObject json)
    {
        this.response_json = json;
    }

    /**
     * @return mixed
     */
    public int getHttpCode()
    {
        return this.response_http_code;
    }
    public void setHttpCode(int http_code)
    {
        this.response_http_code = http_code;
    }

    /**
     * @return mixed
     */
    public Map<String, List<String>> getHeaders()
    {
        return this.response_headers;
    }
    public void setHeaders(Map<String, List<String>> headers)
    {
        this.response_headers = headers;
    }

    /**
     *
     * @return
     */
    public String toString()
    {
        return    "\nstatus_code:\t " + this.getStatusCode()
                + "\nresponse_size:\t " + this.getResponseSize()
                + "\ndata:\t\t" + this.getData().toString()
                + "\nerror:\t\t" + this.getError().toString()
                + "\nhttp_code:\t\t" + this.getHttpCode()
                + "\nheaders:\t\t" + this.getHeaders().toString()
                ;
    }

    /**
     * @return mixed
     */
    public JSONArray getData()
    {
        if (this.response_json.has("data")) {
            try {
                return this.response_json.getJSONArray("data");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return new JSONArray();
    }

    /**
     * @return mixed
     */
    public int getResponseSize()
    {
        if (this.response_json.has("response_size")) {
            try {
                return this.response_json.getInt("response_size");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return 0;
    }

    /**
     * @return mixed
     */
    public int getStatusCode()
    {
        if (this.response_json.has("status_code")) {
            try {
                return this.response_json.getInt("status_code");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return 0;
    }

    /**
     * @return mixed
     */
    public String getError()
    {
        if (this.response_json.has("error")) {
            try {
                return this.response_json.getString("error");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return "";
    }
    

    /**
     * @return mixed
     */
    public String getDebugs()
    {
        if (this.response_json.has("debugs")) {
            try {
                return this.response_json.getString("debugs");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return "";
    }
}
