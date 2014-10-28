/**
 * Proxy.java
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import tune.shared.*;

/**
 * The Class Proxy.
 */
public class Proxy {
    
    private Request request = null;
    private Response response = null;

    /**
     * Get request property for this request.
     *
     * @return Request
     */
    public Request getRequest()
    {
        return this.request;
    }

    /**
     * Get response property for this request.
     *
     * @return object
     */
    public Response getResponse()
    {
        return this.response;
    }
    
    /** The uri. */
    private String uri = null;
    
    /**
     * Instantiates a new service proxy.
     *
     * @param request the request object
     * @throws Exception the exception
     */
    public Proxy( Request request ) throws Exception
    {
        if (null == request) {
            throw new IllegalArgumentException("Parameter 'request' is not defined.");
        }
        
        this.request = request;
    }

    /**
     * Execute request.
     *
     * @param responseSuccess the response success
     * @return true, if successful
     * @throws Exception the exception
     */
    public boolean execute() throws Exception
    {
        this.response = null;
        boolean isSuccess = false;
        try
        {
            this.uri = this.request.getUrl();
            if (this.postRequest()) {
                isSuccess = true;
            }
        }
        catch (TuneServiceException e)
        {
            throw e;
        }
        catch (TuneSdkException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new TuneSdkException( "Failed to process request.", e );
        }

        return isSuccess;
    }
    
    /**
     * Post request.
     *
     * @return true, if successful
     * @throws Exception the exception
     */
    protected boolean postRequest() throws Exception
    {
        String responseJsonEncoded = null;
        URL url = null;
        HttpsURLConnection connection = null;

        try {
            url = new URL(this.uri);
        } catch(MalformedURLException e) {
            throw new TuneSdkException( String.format("MalformedURLException: '%s'", this.uri), e );
        }

        try {
            // connect to the server over HTTPS and submit the payload
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.connect();

            int status = connection.getResponseCode();
            
            this.response = new Response();

            this.response.setHttpCode(status);
            this.response.setHeaders(connection.getHeaderFields());

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();

                    this.response.setRaw(sb.toString());
            }
        } catch (Exception e) {
            throw new TuneSdkException( String.format("Problems executing request: %s: '%s'", e.getClass().toString(), e.getMessage()), e );
        }

        return true;
    }

}
