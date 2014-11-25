package com.tune.sdk.management.shared.endpoints;

/**
 * EndpointBase.java
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
 * @package   com.tune.sdk.management.shared.endpoints
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-24 09:34:47 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

import java.util.Date;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.tune.sdk.shared.*;
import com.tune.sdk.management.shared.service.*;

/**
 * Base class for all Tune Mangement API endpoints.
 */
public class EndpointBase {

    public static final int TUNE_FIELDS_ALL     = 0;
    public static final int TUNE_FIELDS_DEFAULT = 1;
    public static final int TUNE_FIELDS_RELATED = 2;
    public static final int TUNE_FIELDS_MINIMAL = 4;
    public static final int TUNE_FIELDS_RECOMMENDED = 8;

    /**
     * Tune Management API Endpoint
     */
    protected String controller = null;

    /**
     * MobileAppTracking API Key
     */
    protected String api_key = null;

    /**
     * Tune Management API Endpoint's fields
     */
    protected Map<String,Map<String,String>> endpoint_fields = null;

    /**
     * Validate action's parameters against this endpoint' fields.
     */
    protected Boolean validate_fields = false;

    /**
     * Endpoint's model
     */
    protected String model_name = null;

    /**
     * Parameter 'sort' directions.
     */
    public static final Set<String> sort_directions = new HashSet<String>(Arrays.asList(
        "DESC",
        "ASC"
    ));

    /**
     * Parameter 'filter' expression operations.
    */
    protected static final Set<String> filter_operations = new HashSet<String>(Arrays.asList(
        "=",
        "!=",
        "<",
        "<=",
        ">",
        ">=",
        "IS",
        "NOT",
        "NULL",
        "IN",
        "LIKE",
        "RLIKE",
        "REGEXP",
        "BETWEEN"
    ));

    /**
     * Parameter 'filter' expression conjunctions.
    */
    protected static final Set<String> filter_conjunctions = new HashSet<String>(Arrays.asList(
        "AND",
        "OR"
    ));

    /**
     * Recommended fields for report exports.
     */
    protected Set<String> set_fields_recommended = null;

    /**
     * Parameter 'format' for export report.
     */
    protected static final Set<String> report_export_formats = new HashSet<String>(Arrays.asList(
            "csv",
            "json"
    ));

    /**
     * Constructor
     *
     * @param controller        Tune Management API Endpoint
     * @param api_key           MobileAppTracking API Key
     * @param validate_fields   Validate fields used by actions' parameters.
     */
    public EndpointBase(
        String controller,
        String api_key,
        Boolean validate_fields
    ) {
        // controller
        if ( (null == controller) || controller.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'controller' is not defined.");
        }
        // api_key
        if ( (null == api_key) || api_key.isEmpty() ) {
            throw new IllegalArgumentException("Parameter 'api_key' is not defined.");
        }

        this.controller = controller;
        this.api_key = api_key;
        this.validate_fields = validate_fields;
    }

    /**
     * Get controller property for this request.
     *
     * @return string
     */
    public String getController()
    {
        return this.controller;
    }

    /**
     * Get API Key property for this request.
     *
     * @return string
     */
    public String getApiKey()
    {
        return this.api_key;
    }

    /**
     * Call Tune Management API service for this controller.
     *
     * @param action               Tune Management API endpoint's action name
     * @param query_string_dict    Action's query string parameters
     * @return object @see Response
     * @throws TuneSdkException
     */
    protected TuneManagementResponse call(
        String action,
        Map<String,String> query_string_dict
    ) throws Exception
    {
        // action
        if ((null == action) || action.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'action' is not defined.");
        }

        TuneManagementClient client = new TuneManagementClient(
            this.controller,
            action,
            this.api_key,
            query_string_dict
        );

        client.call();

        return client.getResponse();
    }

    /**
     * Provide complete definition for this endpoint.
     */
    /**
     * Provide complete definition for this endpoint.
     * @return Tune
     * @throws Exception
     */
    public TuneManagementResponse getDefine() throws Exception
    {
        return this.call("define", null);
    }

    /**
     *
     * @return
     * @throws TuneSdkException
     * @throws TuneServiceException
     */
    public String getFields() throws TuneSdkException, TuneServiceException {
        return this.getFields(TUNE_FIELDS_ALL);
    }

    public String getFields(
        int enum_fields_selection
    ) throws TuneSdkException, TuneServiceException {
        // build fields
        StringBuilder sb = new StringBuilder();
        String loopDelim = "";
        Set<String> fields = this.getFieldsSet(enum_fields_selection);

        if ((null == fields) || fields.isEmpty()) {
            return null;
        }

        for(String field : fields) {

            sb.append(loopDelim);
            sb.append(field.trim());

            loopDelim = ",";
        }
        return sb.toString();
    }

    public Set<String> getFieldsSet() throws Exception
    {
        return this.getFieldsSet(TUNE_FIELDS_ALL);
    }

    /**
     * Get all fields for assigned endpoint.
     *
     * @param enum_fields_selection Bitwise selection of requested fields.
     * @return array
     * @throws TuneSdkException
     * @throws TuneServiceException
     */
    public Set<String> getFieldsSet(
        int enum_fields_selection
    ) throws TuneSdkException, TuneServiceException {
        if ((this.validate_fields
             || ((enum_fields_selection & TUNE_FIELDS_RECOMMENDED) == 0))
            && (null == this.endpoint_fields)
        ) {
            this.getEndpointFields();

            if ((null == this.endpoint_fields) || this.endpoint_fields.isEmpty()) {
                throw new TuneSdkException(
                    String.format("Failed to get fields for endpoint: '%s'.", this.controller)
                );
            }
        }

        if (enum_fields_selection == TUNE_FIELDS_ALL) {
            return this.endpoint_fields.keySet();
        }

        if ((enum_fields_selection & TUNE_FIELDS_RECOMMENDED) != 0) {
            return this.set_fields_recommended;
        }

        if (((enum_fields_selection & TUNE_FIELDS_DEFAULT) == 0)
            && ((enum_fields_selection & TUNE_FIELDS_RELATED) != 0)
            ) {
            return this.endpoint_fields.keySet();
        }

        Map<String,Map<String,String>> fields_filtered = new HashMap<String,Map<String,String>>();

        Iterator<Map.Entry<String, Map<String, String>>> it = this.endpoint_fields.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Map<String, String>> pairs = it.next();

            String field_name = pairs.getKey();
            Map<String, String> field_info = pairs.getValue();

            if (((enum_fields_selection & TUNE_FIELDS_RELATED) == 0)
                    && ((enum_fields_selection & TUNE_FIELDS_MINIMAL) == 0)
                    && (Boolean.parseBoolean(field_info.get("related")))) {
                continue;
            }

            if (((enum_fields_selection & TUNE_FIELDS_DEFAULT) == 0)
                    && (Boolean.parseBoolean(field_info.get("related")))) {
                fields_filtered.put(field_name, field_info);
            }

            if (((enum_fields_selection & TUNE_FIELDS_DEFAULT) != 0)
                    && (Boolean.parseBoolean(field_info.get("default")))) {
                if (((enum_fields_selection & TUNE_FIELDS_MINIMAL) != 0)
                        && (Boolean.parseBoolean(field_info.get("related")))) {

                    Set<String> related_fields = new HashSet<String>();
                    related_fields.add(".name");
                    related_fields.add(".ref");

                    for (String related_field : related_fields) {
                        if (field_name.endsWith(related_field)) {
                            fields_filtered.put(field_name, field_info);
                        }
                    }
                    continue;
                }
                fields_filtered.put(field_name, field_info);
                continue;
            }

            if (((enum_fields_selection & TUNE_FIELDS_DEFAULT) != 0)
                && (Boolean.parseBoolean(field_info.get("related")))) {
                fields_filtered.put(field_name, field_info);
                continue;
            }
        }

        return fields_filtered.keySet();
    }

    /**
     * Get model name for this endpoint.
     *
     * @return string
     */
    public String getModelName() throws Exception
    {
        if (null == this.endpoint_fields) {
            this.getFieldsSet(TUNE_FIELDS_ALL);
        }

        return this.model_name;
    }

    /**
     * Fetch all fields from model and related models of this endpoint.
     * @throws TuneServiceException
     * @throws TuneSdkException
     * @throws JSONException
     * @throws IllegalArgumentException
     */
    protected Map<String, Map<String,String>> getEndpointFields()
            throws TuneServiceException, TuneSdkException
    {
        Map<String,String> query_string_dict = new HashMap<String,String>();
        query_string_dict.put("controllers", this.controller);
        query_string_dict.put("details", "modelName,fields");

        TuneManagementClient client = new TuneManagementClient(
                "apidoc",
                "get_controllers",
                this.api_key,
                query_string_dict
        );

        client.call();

        TuneManagementResponse response = client.getResponse();
        int http_code = response.getHttpCode();
        JSONArray data = (JSONArray) response.getData();

        if (http_code != 200) {
            String request_url = response.getRequestUrl();
            throw new TuneServiceException(
                String.format("Connection failure '%s': '%s'", request_url, http_code)
            );
        }

        if ((null == data) || (data.length() == 0)) {
            throw new TuneServiceException(
                String.format("Failed to get fields for endpoint: '%s'.", this.controller)
            );
        }

        try {
            JSONObject endpoint_metadata = data.getJSONObject(0);

            this.model_name = endpoint_metadata.getString("modelName");
            JSONArray endpoint_fields = endpoint_metadata.getJSONArray("fields");

            Map<String, Map<String,String>> fields_found = new HashMap<String, Map<String,String>>();
            Map<String, Set<String>> related_fields = new HashMap<String, Set<String>>();

            for (int i = 0; i < endpoint_fields.length(); i++) {
                JSONObject endpoint_field = endpoint_fields.getJSONObject(i);
                Boolean field_related = endpoint_field.getBoolean("related");
                String field_type = endpoint_field.getString("type");
                String field_name = endpoint_field.getString("name");
                Boolean field_default = endpoint_field.has("fieldDefault")
                            ? endpoint_field.getBoolean("fieldDefault")
                            : false;

                if (field_related) {
                    if (field_type.equals("property")) {
                        String related_property = field_name;
                        if (!related_fields.containsKey(related_property)) {
                            related_fields.put(related_property, new HashSet<String>());
                        }
                        continue;
                    }

                    String[] field_related_name_parts = field_name.split("\\.");
                    String related_property = field_related_name_parts[0];
                    String related_field_name = field_related_name_parts[1];

                    if (!related_fields.containsKey(related_property)) {
                        related_fields.put(related_property, new HashSet<String>());
                    }

                    Set<String> related_field_fields = related_fields.get(related_property);
                    related_field_fields.add(related_field_name);
                    related_fields.put(related_property, related_field_fields);
                    continue;
                }

                Map<String, String> field_found_info = new HashMap<String, String>();
                field_found_info.put("default", Boolean.toString(field_default));
                field_found_info.put("related", "false");
                fields_found.put(field_name, field_found_info);
            }

            Map<String, Map<String, String>> fields_found_merged = new HashMap<String, Map<String, String>>();
            Iterator<Map.Entry<String, Map<String, String>>> it = fields_found.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<String, Map<String, String>> pairs = it.next();

                String field_found_name = pairs.getKey();
                Map<String, String> field_found_info = pairs.getValue();

                fields_found_merged.put(field_found_name, field_found_info);

                if ((field_found_name != "_id") && field_found_name.endsWith("_id")) {
                    String related_property = field_found_name.substring(0, field_found_name.length() - 3);
                    if (related_fields.containsKey(related_property) && !related_fields.get(related_property).isEmpty()) {
                        for (String related_field_name : related_fields.get(related_property)) {
                            if ("id" == related_field_name) {
                                continue;
                            }
                            String related_property_field_name = String.format("%s.%s", related_property, related_field_name);
                            Map<String, String> related_property_field_info = new HashMap<String, String>();
                            related_property_field_info.put("default", field_found_info.get("default"));
                            related_property_field_info.put("related", "true");
                            fields_found_merged.put(related_property_field_name, related_property_field_info);
                        }
                    } else {
                        Map<String, String> related_property_field_info = new HashMap<String, String>();
                        related_property_field_info.put("default", field_found_info.get("default"));
                        related_property_field_info.put("related", "true");
                        String related_property_field_name = String.format("%s.%s", related_property, "name");
                        fields_found_merged.put(related_property_field_name, related_property_field_info);
                    }
                }
            }

            this.endpoint_fields = fields_found_merged;

        } catch (JSONException ex) {
                throw new TuneSdkException(ex.getMessage(), ex);
        } catch (Exception ex) {
                throw new TuneSdkException(ex.getMessage(), ex);
        }

        return this.endpoint_fields;
    }

    /**
     * Validate query string parameter 'fields' having valid endpoint's fields
     *
     * @param fields
     *
     * @return string
     * @throws Exception
     */
    public String validateFields(List<String> fields) throws Exception
    {
        if ((null == fields) || fields.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'fields' is not defined.");
        }

        if (EndpointBase.hasDuplicate(fields)) {
            throw new IllegalArgumentException("Parameter 'fields' has duplicates.");
        }

        if (this.validate_fields) {
            if (null == this.endpoint_fields || this.endpoint_fields.isEmpty()) {
                this.getFieldsSet();
            }
            Set<String> endpoint_fields = this.endpoint_fields.keySet();
            for (String field : fields) {

                field = field.trim();
                if (field.isEmpty()) {
                    throw new TuneSdkException(
                        String.format("Parameter 'fields' contains an empty field.", field)
                    );
                }
                if (!endpoint_fields.contains(field)) {
                    throw new TuneSdkException(
                        String.format("Parameter 'fields' contains an invalid field: '%s'.", field)
                    );
                }
            }
        }

        return EndpointBase.implode(fields, ",");
    }

    /**
     * Validate parameter 'fields' within string.
     *
     * @param fields
     * @return String Validated fields.
     * @throws Exception
     */
    public String validateFields(String fields) throws Exception
    {
        if ((null == fields) || fields.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'fields' is not defined.");
        }

        String[] fields_array = fields.split("\\,");
        if (fields_array.length == 0) {
            throw new IllegalArgumentException("Parameter 'fields' is not defined.");
        }

        List<String> fields_set = Arrays.asList(fields_array);

        return this.validateFields(fields_set);
    }

    /**
     * Validate query string parameter 'group' having valid endpoint's fields
     *
     * @param group Select
     *
     * @return string
     * @throws Exception
     * @throws TuneSdkException
     */
    public String validateGroup(List<String> group) throws Exception
    {
        return this.validateFields(group);
    }
    public String validateGroup(String group) throws Exception
    {
        return this.validateFields(group);
    }

    /**
     * Validate query string parameter 'sort' having valid endpoint's fields and direction.
     *
     * @param sort
     *
     * @return bool
     * @throws Exception
     */
    public String validateSort(Set<String> fields, Map<String, String> sort) throws Exception
    {
        if ((null == sort) || sort.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'sort' is not defined.");
        }

        Set<String> sort_fields = sort.keySet();

        if (this.validate_fields) {
            if (null == this.endpoint_fields || this.endpoint_fields.isEmpty()) {
                this.getFieldsSet();
            }
            Set<String> endpoint_fields = this.endpoint_fields.keySet();
            for (String sort_field : sort_fields) {

                sort_field = sort_field.trim();
                if (sort_field.isEmpty()) {
                    throw new IllegalArgumentException(
                        String.format("Parameter 'sort' contains an empty field.", sort_field)
                    );
                }
                if (!endpoint_fields.contains(sort_field)) {
                    throw new IllegalArgumentException(
                        String.format("Parameter 'sort' contains an invalid field: '%s'.", sort_field)
                    );
                }
                String sort_direction = sort.get(sort_field);

                sort_direction = sort_direction.toUpperCase();

                if (!EndpointBase.sort_directions.contains(sort_direction)) {
                    throw new IllegalArgumentException(
                        String.format("Parameter 'sort' contains an invalid direction: '%s'.", sort_direction)
                    );
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String sort_field : sort_fields) {
            sort_field = sort_field.trim();
            String sort_direction = sort.get(sort_field);

            if ((null != fields) && !fields.contains(sort_field)) {
                fields.add(sort_field);
            } else {
                fields = new HashSet<String>();
                fields.add(sort_field);
            }
            sort_direction = sort_direction.toUpperCase();
            String sort_parameter = String.format("%s:%s", sort_field, sort_direction);
            if (sb.length() == 0) {
                 sb.append(sort_parameter);
            } else {
                sb.append(",").append(sort_parameter);
            }
        }

        return sb.toString();
    }

    /**
     * Validate query string parameter 'filter' having valid endpoint's fields
     * and filter expressions.
     *
     * @param filter
     *
     * @return string|void
     * @throws Exception
     */
    public String validateFilter(String filter) throws Exception
    {
        if ((null == filter) || filter.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'filter' is not defined.");
        }

        if (!EndpointBase.isParenthesesBalanced(filter)) {
            throw new TuneSdkException(
                String.format("Invalid parameter 'filter' is not parentheses balanced: '%s'.", filter)
            );
        }
        String filter_pruned = filter;
        filter_pruned = filter_pruned.replaceAll("\\(", " ");
        filter_pruned = filter_pruned.replaceAll("\\)", " ");
        filter_pruned = filter_pruned.trim().replaceAll(" +", " ");
        String[] filter_parts = filter_pruned.split(" ");

        Set<String> endpoint_fields = null;
        if (this.validate_fields) {
            if (null == this.endpoint_fields || this.endpoint_fields.isEmpty()) {
                this.getFieldsSet();
            }

            endpoint_fields = this.endpoint_fields.keySet();
        }

        for (String filter_part : filter_parts) {
            filter_part = filter_part.trim();
            if (filter_part.isEmpty()) {
                continue;
            }

            if (filter_part.startsWith("'") && filter_part.endsWith("'")) {
                continue;
            }

            try {
                Integer.parseInt(filter_part);
                continue;
            } catch(NumberFormatException e) {
                // ignore
            }

            if (EndpointBase.filter_operations.contains(filter_part)) {
                continue;
            }

            if (EndpointBase.filter_conjunctions.contains(filter_part)) {
                continue;
            }

            if (filter_part.matches("[a-z0-9\\.\\_]+")) {
                if (this.validate_fields) {
                    if (endpoint_fields.contains(filter_part)) {
                        continue;
                    }
                } else {
                    continue;
                }
            }

            throw new IllegalArgumentException(
                String.format("Parameter 'filter' is invalid: '%s': '%s'.", filter, filter_part)
            );
        }

        return String.format("(%s)", filter);
    }

    /**
     * Validates that provided date time is either "yyyy-MM-dd"
     * or "yyyy-MM-dd HH:mm:ss".
     *
     * @param param_name
     * @param date_time
     * @return
     * @throws IllegalArgumentException
     */
    public static String validateDateTime(String param_name, String date_time)
    		throws IllegalArgumentException
    {
        if ((null == param_name) || param_name.isEmpty()) {
            throw new IllegalArgumentException(
                "Parameter 'param_name' is not defined."
            );
        }
        if ((null == date_time) || date_time.isEmpty()) {
            throw new IllegalArgumentException(
                String.format("Parameter '%s' is not defined.", param_name)
            );
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(date_time);
            if (date_time.equals(simpleDateFormat.format(date))) {
                return date_time;
            }

            Date datetime = simpleDateTimeFormat.parse(date_time);
            if (date_time.equals(simpleDateTimeFormat.format(datetime))) {
                return date_time;
            }

            throw new IllegalArgumentException(
                String.format("Parameter '%s' is invalid date-time: '%s'.", param_name, date_time)
            );
        } catch (ParseException ex) {
            throw new IllegalArgumentException(
                String.format("Parameter '%s' is invalid date-time: '%s'.", param_name, date_time), ex
            );
        }
    }

    /**
     * Helper function for fetching report document given provided job identifier.
     *
     * Requesting for report url is not the same for all report endpoints.
     *
     * @param export_controller      Controller for report export status.
     * @param export_action          Action for report export status.
     * @param job_id                     Job Identifier of report on queue.
     * @param verbose                    For debugging purposes only.
     * @param sleep                      How long worker should sleep
     *                                              before next status request.
     * @return object @see TuneManagementResponse
     * @throws IllegalArgumentException
     * @throws TuneServiceException
     * @throws TuneSdkException
     */
    protected TuneManagementResponse fetchRecords(
        String export_controller,
        String export_action,
        String job_id,
        Boolean verbose,
        int sleep
    ) throws TuneServiceException, TuneSdkException {
        if ((null == export_controller) || export_controller.isEmpty()) {
            throw new IllegalArgumentException(
                "Parameter 'export_controller' is not defined."
            );
        }
        if ((null == export_action) || export_action.isEmpty()) {
            throw new IllegalArgumentException(
                "Parameter 'export_action' is not defined."
            );
        }
        if ((null == job_id) || job_id.isEmpty()) {
            throw new IllegalArgumentException(
                "Parameter 'job_id' is not defined."
            );
        }
        if ((null == this.api_key) || this.api_key.isEmpty()) {
            throw new TuneSdkException("Parameter 'api_key' is not defined.");
        }

        ReportExportWorker export_worker = new ReportExportWorker(
            export_controller,
            export_action,
            this.api_key,
            job_id,
            verbose,
            sleep
        );

        if (verbose) {
            System.out.println( "Starting worker..." );
        }
        if (export_worker.run()) {
            if (verbose) {
                System.out.println( "Completed worker..." );
            }
        }

        TuneManagementResponse response = export_worker.getResponse();
        if (null == response) {
            throw new TuneServiceException(
                "Report export request no response."
            );
        }

        int http_code = response.getHttpCode();
        if (http_code != 200) {
            throw new TuneServiceException(
                String.format("Report export request error: '%d'", http_code)
            );
        }

        JSONObject jdata = (JSONObject) response.getData();
        if (null == jdata) {
            throw new TuneServiceException(
                "Report export response failed to get data."
            );
        }

        if (!jdata.has("status")) {
            throw new TuneSdkException(
                String.format(
                    "Export data does not contain report 'status', response: %s",
                    response.toString()
                )
            );
        }

        String status = null;
        try {
            status = jdata.getString("status");
        } catch (JSONException ex) {
            throw new TuneSdkException(ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new TuneSdkException(ex.getMessage(), ex);
        }

        if (status.equals("fail")) {
            throw new TuneSdkException(
                String.format(
                    "Report export status '%s':, response: %s",
                    status,
                    response.toString()
                )
            );
        }

        return response;
    }

    /**
     * For debug purposes, provide string representation of this object.
     *
     * @return string
     */
    public String toString()
    {
        return String.format("Endpoint '%s', API Key: '%s", this.controller, this.api_key);
    }

    /**
     * Parse response and gather job identifier.
     *
     * @param response
     * @return
     * @throws TuneServiceException
     * @throws TuneSdkException
     */
    public static String parseResponseReportJobId(
        TuneManagementResponse response
    ) throws TuneServiceException, TuneSdkException {
        String job_id = null;
        if (null == response) {
            throw new IllegalArgumentException("Parameter 'response' is not defined.");
        }
        Object jdata = response.getData();
        if (null == jdata) {
            throw new TuneServiceException(
                "Report request failed to get export data."
            );
        }
        job_id = jdata.toString();
        if ((null == job_id) || job_id.isEmpty()) {
            throw new TuneSdkException(
                "Parameter 'job_id' is not defined."
            );
        }
        return job_id;
    }

    /**
     * Parse response and gather report url.
     *
     * @param response
     * @return
     * @throws TuneSdkException
     * @throws TuneServiceException
     */
    public static String parseResponseReportUrl(
        TuneManagementResponse response
    ) throws TuneSdkException, TuneServiceException {

        if (null == response) {
            throw new IllegalArgumentException("Parameter 'response' is not defined.");
        }

        JSONObject jdata = (JSONObject) response.getData();
        if (null == jdata) {
            throw new TuneServiceException(
                "Report export response failed to get data."
            );
        }

        if (!jdata.has("data")) {
            throw new TuneSdkException(
                String.format(
                    "Export data does not contain report 'data', response: %s",
                    response.toString()
                )
            );
        }

        JSONObject jdata_internal = null;
        try {
            jdata_internal = jdata.getJSONObject("data");
        } catch (JSONException ex) {
            throw new TuneSdkException(ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new TuneSdkException(ex.getMessage(), ex);
        }

        if (null == jdata_internal) {
            throw new TuneServiceException(
                String.format(
                    "Export data response does not contain 'data', response: %s",
                    response.toString()
                )
            );
        }

        if (!jdata_internal.has("url")) {
            throw new TuneSdkException(
                String.format(
                    "Export response 'data' does not contain 'url', response: %s",
                    response.toString()
                )
            );
        }

        String jdata_internal_url = null;
        try {
            jdata_internal_url = jdata_internal.getString("url");
        } catch (JSONException ex) {
            throw new TuneSdkException(ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new TuneSdkException(ex.getMessage(), ex);
        }

        if ((null == jdata_internal_url) || jdata_internal_url.isEmpty()) {
            throw new TuneSdkException(
                String.format(
                    "Export response 'url' is not defined, response: %s",
                    response.toString()
                )
            );
        }

        return jdata_internal_url;
    }

    /**
     * Validate any parentheses within string are balanced.
     * @param str
     * @return Boolean
     */
    public static boolean isParenthesesBalanced(String str)
    {
        if (str.isEmpty())
            return true;

        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < str.length(); i++)
        {
            char current = str.charAt(i);
            if (current == '{' || current == '(' || current == '[')
            {
                stack.push(current);
            }


            if (current == '}' || current == ')' || current == ']')
            {
                if (stack.isEmpty())
                    return false;

                char last = stack.peek();
                if (current == '}' && last == '{' || current == ')' && last == '(' || current == ']' && last == '[')
                    stack.pop();
                else
                    return false;
            }

        }

        return stack.isEmpty();
    }

    /**
     * Given a list of strings, determine it contains no duplicates.
     * @param all
     * @return Boolean
     */
    public static boolean hasDuplicate(Iterable<String> all) {
        Set<String> set = new HashSet<String>();
        // Set#add returns false if the set does not change, which
        // indicates that a duplicate element has been added.
        for (String each: all) {
            if (!set.add(each)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Implode array into a string separated by delimiters.
     * @param all
     * @param glue
     * @return String Delimited by provided glue.
     */
    public static String implode(Iterable<String> all, String glue) {
        StringBuilder sb = new StringBuilder();
        for (String item : all) {
            item = item.trim();
            if (sb.length() == 0) {
                 sb.append(item);
            } else {
                sb.append(glue).append(item);
            }
        }
        return sb.toString();
    }

    /**
     * Explode string into a set of string items.
     * @param all
     * @param glue
     * @return Set<String>
     */
    public static Set<String> explode(String all, String glue) {
        String[] items = all.split(glue);

        Set<String> set = new HashSet<String>();
        for (String item : items) {
            set.add(item.trim());
        }

        return set;
    }
}