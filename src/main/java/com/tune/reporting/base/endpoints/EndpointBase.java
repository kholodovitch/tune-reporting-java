package com.tune.reporting.base.endpoints;

/**
 * EndpointBase.java
 *
 * <p>
 * Copyright (c) 2015 TUNE, Inc.
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
 * @copyright 2015 TUNE, Inc. (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2015-01-05 09:40:09 $
 * @link      https://developers.mobileapptracking.com @endlink
 * </p>
 */

import com.tune.reporting.base.service.TuneManagementClient;
import com.tune.reporting.base.service.TuneManagementResponse;
import com.tune.reporting.helpers.SdkConfig;
import com.tune.reporting.helpers.TuneSdkException;
import com.tune.reporting.helpers.TuneServiceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


/**
 * Base class for all TUNE Management API endpoints.
 */
public class EndpointBase {

  /**
   * The request has succeeded.
   */
  public static final int HTTP_STATUS_OK = 200;

  /** Gather all fields for this endpoint. */
  public static final int TUNE_FIELDS_ALL   = 0;
  /** Gather default fields for this endpoint. */
  public static final int TUNE_FIELDS_DEFAULT = 1;
  /** Gather related fields for this endpoint. */
  public static final int TUNE_FIELDS_RELATED = 2;
  /** Gather minimal set of fields for this endpoint. */
  public static final int TUNE_FIELDS_MINIMAL = 4;
  /** Gather recommended fields for this endpoint. */
  public static final int TUNE_FIELDS_RECOMMENDED = 8;

  /**
   * TUNE Reporting SDK Configuration.
   */
  private SdkConfig sdkConfig = null;

  /**
   * TUNE Management API Endpoint.
   */
  private String controller = null;

  /**
   * MobileAppTracking API Key.
   */
  private String apiKey = null;

  /**
   * TUNE Management API Endpoint's fields.
   */
  private Map<String, Map<String, String>> endpointFields = null;

  /**
   * Validate action's parameters against this endpoint' fields.
   */
  private Boolean validateFields = false;

  /**
   * Endpoint's model name.
   */
  private String endpointModelName = null;

  /**
   * Parameter 'sort' directions.
   */
  public static final Set<String> SORT_DIRECTIONS
      = new HashSet<String>(Arrays.asList(
          "DESC",
          "ASC"
      ));

  /**
   * Parameter 'filter' expression operations.
  */
  protected static final Set<String> FILTER_OPERATIONS
      = new HashSet<String>(Arrays.asList(
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
  protected static final Set<String> FILTER_CONJUNCTIONS
      = new HashSet<String>(Arrays.asList(
          "AND",
          "OR"
      ));

  /**
   * Recommended fields for report exports.
   */
  private Set<String> fieldsRecommended = null;

  /**
   * Get recommended fields for an endpoint.
   *
   * @return Set
   */
  protected final Set<String> getFieldsRecommended() {
    return this.fieldsRecommended;
  }

  /**
   * Set recommended fields for an endpoint.
   *
   * @param fieldsRecommended   Set of recommended fields for this endpoint.
   */
  protected final void setFieldsRecommended(
      final Set<String> fieldsRecommended
  ) {
    this.fieldsRecommended = fieldsRecommended;
  }

  /**
   * Parameter 'format' for export report.
   */
  protected static final Set<String> REPORT_EXPORT_FORMATS
      = new HashSet<String>(Arrays.asList(
          "csv",
          "json"
     ));

  /**
   * Constructor.
   *
   * @param controller      TUNE Management API Endpoint
   */
  public EndpointBase(
      final String controller
  ) throws TuneSdkException {
    // controller
    if ((null == controller) || controller.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'controller' is not defined."
      );
    }

    try {
      this.sdkConfig = SdkConfig.getInstance();
    } catch (TuneSdkException e) {
      throw e;
    }

    String apiKey = this.sdkConfig.getApiKey();
    Boolean validateFields = this.sdkConfig.getValidateFields();

    // apiKey
    if ((null == apiKey) || apiKey.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'apiKey' is not defined.");
    }

    this.controller = controller;
    this.apiKey = apiKey;
    this.validateFields = validateFields;
  }

  /**
   * Get controller property for this request.
   *
   * @return String
   */
  public final String getController() {
    return this.controller;
  }

  /**
   * Get API Key property for this request.
   *
   * @return String
   */
  public final String getApiKey() {
    return this.apiKey;
  }

  /**
   * Call TUNE Management API service for this controller.
   *
   * @param action          TUNE Management API endpoint's action name
   * @param mapQueryString  Action's query string parameters
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   */
  protected final TuneManagementResponse call(
      final String action,
      final Map<String, String> mapQueryString
  ) throws TuneSdkException {
    // action
    if ((null == action) || action.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'action' is not defined.");
    }

    TuneManagementClient client = new TuneManagementClient(
        this.controller,
        action,
        this.apiKey,
        mapQueryString
    );

    client.call();

    return client.getResponse();
  }

  /**
   * Provide complete definition for this endpoint.
   *
   * @return TuneManagementResponse
   *
   * @throws TuneSdkException If fails to post request.
   */
  public final TuneManagementResponse getDefine()
    throws TuneSdkException {
    return this.call("define", null);
  }

  /**
   * Gather all fields available for this endpoint.
   *
   * @return String   Comma delimited set of all fields available for this
   *          endpoint.
   *
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final String getFields()
    throws  TuneSdkException,
            TuneServiceException {
    return this.getFields(TUNE_FIELDS_ALL);
  }

  /**
   * Gather reqested set of fields for this endpoint.
   *
   * @param enumFieldsSelection Which set of fields for this endpoint.
   *
   * @return String   Comma delimited set of all fields available for this
   *          endpoint.
   *
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final String getFields(
      final int enumFieldsSelection
  ) throws TuneSdkException, TuneServiceException {
    // build fields
    StringBuilder sb = new StringBuilder();
    String loopDelim = "";
    Set<String> fields = this.getFieldsSet(enumFieldsSelection);

    if ((null == fields) || fields.isEmpty()) {
      return null;
    }

    for (String field : fields) {
      sb.append(loopDelim);
      sb.append(field.trim());

      loopDelim = ",";
    }
    return sb.toString();
  }

  /**
   * Get model name for this endpoint.
   *
   * @return String
   *
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final String getModelName()
    throws  TuneSdkException,
            TuneServiceException {
    if (null == this.endpointFields) {
      this.getFieldsSet(TUNE_FIELDS_ALL);
    }

    return this.endpointModelName;
  }

  /**
   * Gather reqested set of fields for this endpoint.
   *
   * @return Set   All fields available for this endpoint.
   *
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final Set<String> getFieldsSet()
    throws  TuneSdkException,
            TuneServiceException {
    return this.getFieldsSet(TUNE_FIELDS_ALL);
  }

  /**
   * Get all fields for assigned endpoint.
   *
   * @param enumFieldsSelection Bitwise selection of requested fields.
   *
   * @return Set Array of fields for endpoint.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final Set<String> getFieldsSet(
      final int enumFieldsSelection
  ) throws TuneSdkException, TuneServiceException {
    if ((this.validateFields
        || ((enumFieldsSelection & TUNE_FIELDS_RECOMMENDED) == 0))
        && (null == this.endpointFields)
    ) {
      this.getEndpointFields();

      if ((null == this.endpointFields) || this.endpointFields.isEmpty()) {
        throw new TuneSdkException(
          String.format(
            "Failed to get fields for endpoint: '%s'.",
            this.controller
          )
        );
      }
    }

    if (enumFieldsSelection == TUNE_FIELDS_ALL) {
      return this.endpointFields.keySet();
    }

    if ((enumFieldsSelection & TUNE_FIELDS_RECOMMENDED) != 0) {
      return this.fieldsRecommended;
    }

    if (((enumFieldsSelection & TUNE_FIELDS_DEFAULT) == 0)
        && ((enumFieldsSelection & TUNE_FIELDS_RELATED) != 0)
    ) {
      return this.endpointFields.keySet();
    }

    Map<String, Map<String, String>> fieldsFiltered
        = new HashMap<String, Map<String, String>>();

    Iterator<Map.Entry<String, Map<String, String>>> it
        = this.endpointFields.entrySet().iterator();

    while (it.hasNext()) {
      Map.Entry<String, Map<String, String>> pairs = it.next();

      String fieldName = pairs.getKey();
      Map<String, String> fieldInfo = pairs.getValue();

      if (((enumFieldsSelection & TUNE_FIELDS_RELATED) == 0)
          && ((enumFieldsSelection & TUNE_FIELDS_MINIMAL) == 0)
          && (Boolean.parseBoolean(fieldInfo.get("related")))) {
        continue;
      }

      if (((enumFieldsSelection & TUNE_FIELDS_DEFAULT) == 0)
          && (Boolean.parseBoolean(fieldInfo.get("related")))) {
        fieldsFiltered.put(fieldName, fieldInfo);
      }

      if (((enumFieldsSelection & TUNE_FIELDS_DEFAULT) != 0)
          && (Boolean.parseBoolean(fieldInfo.get("default")))) {
        if (((enumFieldsSelection & TUNE_FIELDS_MINIMAL) != 0)
            && (Boolean.parseBoolean(fieldInfo.get("related")))) {
          Set<String> relatedFields = new HashSet<String>();
          relatedFields.add(".name");
          relatedFields.add(".ref");

          for (String relatedField : relatedFields) {
            if (fieldName.endsWith(relatedField)) {
              fieldsFiltered.put(fieldName, fieldInfo);
            }
          }
          continue;
        }
        fieldsFiltered.put(fieldName, fieldInfo);
        continue;
      }

      if (((enumFieldsSelection & TUNE_FIELDS_DEFAULT) != 0)
          && (Boolean.parseBoolean(fieldInfo.get("related")))) {
        fieldsFiltered.put(fieldName, fieldInfo);
        continue;
      }
    }

    return fieldsFiltered.keySet();
  }

  /**
   * Fetch all fields from model and related models of this endpoint.
   *
   * @return Map All endpoint fields.
   *
   * @throws TuneServiceException If service fails to handle post request.
   * @throws TuneSdkException If fails to post request.
   */
  protected final Map<String, Map<String, String>> getEndpointFields()
    throws  TuneServiceException,
            TuneSdkException {

    Map<String, String> mapQueryString = new HashMap<String, String>();
    mapQueryString.put("controllers", this.controller);
    mapQueryString.put("details", "modelName,fields");

    TuneManagementClient client = new TuneManagementClient(
        "apidoc",
        "get_controllers",
        this.apiKey,
        mapQueryString
    );

    client.call();

    TuneManagementResponse response = client.getResponse();
    int httpCode = response.getHttpCode();
    JSONArray data = (JSONArray) response.getData();

    if (httpCode != HTTP_STATUS_OK) {
      String requestUrl = response.getRequestUrl();
      throw new TuneServiceException(
        String.format("Connection failure '%s': '%s'", requestUrl, httpCode)
      );
    }

    if ((null == data) || (data.length() == 0)) {
      throw new TuneServiceException(
        String.format(
          "Failed to get fields for endpoint: '%s'.",
          this.controller
        )
      );
    }

    try {
      JSONObject endpointMetaData = data.getJSONObject(0);

      this.endpointModelName = endpointMetaData.getString("modelName");
      JSONArray endpointFields = endpointMetaData.getJSONArray("fields");

      Map<String, Map<String, String>> fieldsFound
          = new HashMap<String, Map<String, String>>();
      Map<String, Set<String>> relatedFields
          = new HashMap<String, Set<String>>();

      for (int i = 0; i < endpointFields.length(); i++) {
        JSONObject endpointField = endpointFields.getJSONObject(i);
        Boolean fieldRelated = endpointField.getBoolean("related");
        String fieldType = endpointField.getString("type");
        String fieldName = endpointField.getString("name");
        Boolean fieldDefault = endpointField.has("fieldDefault")
            ? endpointField.getBoolean("fieldDefault")
            : false;

        if (fieldRelated) {
          if (fieldType.equals("property")) {
            String relatedProperty = fieldName;
            if (!relatedFields.containsKey(relatedProperty)) {
              relatedFields.put(relatedProperty, new HashSet<String>());
            }
            continue;
          }

          String[] fieldRelatedNameParts = fieldName.split("\\.");
          String relatedProperty = fieldRelatedNameParts[0];
          String relatedFieldName = fieldRelatedNameParts[1];

          if (!relatedFields.containsKey(relatedProperty)) {
            relatedFields.put(relatedProperty, new HashSet<String>());
          }

          Set<String> relatedFieldFields = relatedFields.get(relatedProperty);
          relatedFieldFields.add(relatedFieldName);
          relatedFields.put(relatedProperty, relatedFieldFields);
          continue;
        }

        Map<String, String> fieldFoundInfo = new HashMap<String, String>();
        fieldFoundInfo.put("default", Boolean.toString(fieldDefault));
        fieldFoundInfo.put("related", "false");
        fieldsFound.put(fieldName, fieldFoundInfo);
      }

      Map<String, Map<String, String>> fieldsFoundMerged
          = new HashMap<String, Map<String, String>>();
      Iterator<Map.Entry<String, Map<String, String>>> it
          = fieldsFound.entrySet().iterator();

      while (it.hasNext()) {
        Map.Entry<String, Map<String, String>> pairs = it.next();

        String fieldFoundName = pairs.getKey();
        Map<String, String> fieldFoundInfo = pairs.getValue();

        fieldsFoundMerged.put(fieldFoundName, fieldFoundInfo);

        if ((fieldFoundName != "_id") && fieldFoundName.endsWith("_id")) {
          String relatedProperty
              = fieldFoundName.substring(0, fieldFoundName.length() - 3);
          if (relatedFields.containsKey(relatedProperty)
              && !relatedFields.get(relatedProperty).isEmpty()) {
            for (String relatedFieldName : relatedFields.get(relatedProperty)) {
              if ("id" == relatedFieldName) {
                continue;
              }
              String relatedPropertyFieldName
                  = String.format("%s.%s", relatedProperty, relatedFieldName);
              Map<String, String> relatedPropertyFieldInfo
                  = new HashMap<String, String>();
              relatedPropertyFieldInfo.put(
                  "default",
                  fieldFoundInfo.get("default")
              );
              relatedPropertyFieldInfo.put("related", "true");
              fieldsFoundMerged.put(
                  relatedPropertyFieldName,
                  relatedPropertyFieldInfo
              );
            }
          } else {
            Map<String, String> relatedPropertyFieldInfo
                = new HashMap<String, String>();
            relatedPropertyFieldInfo.put(
                "default",
                fieldFoundInfo.get("default")
            );
            relatedPropertyFieldInfo.put("related", "true");
            String relatedPropertyFieldName
                = String.format("%s.%s", relatedProperty, "name");
            fieldsFoundMerged.put(
                relatedPropertyFieldName,
                relatedPropertyFieldInfo
            );
          }
        }
      }

      this.endpointFields = fieldsFoundMerged;

    } catch (JSONException ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    } catch (Exception ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    }

    return this.endpointFields;
  }

  /**
   * Validate query string parameter 'fields' having valid endpoint's fields.
   *
   * @param fields  Set of fields to validate.
   *
   * @return String Validated fields.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final String validateFields(
      final List<String> fields
  ) throws  TuneSdkException,
            TuneServiceException {
    if ((null == fields) || fields.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'fields' is not defined.");
    }

    if (EndpointBase.hasDuplicate(fields)) {
      throw new IllegalArgumentException("Parameter 'fields' has duplicates.");
    }

    if (this.validateFields) {
      if (null == this.endpointFields || this.endpointFields.isEmpty()) {
        this.getFieldsSet();
      }
      Set<String> endpointFields = this.endpointFields.keySet();
      for (String field : fields) {
        field = field.trim();
        if (field.isEmpty()) {
          throw new TuneSdkException(
            String.format(
              "Parameter 'fields' contains an empty field.", field
            )
         );
        }
        if (!endpointFields.contains(field)) {
          throw new TuneSdkException(
            String.format(
              "Parameter 'fields' contains an invalid field: '%s'.", field
            )
         );
        }
      }
    }

    return EndpointBase.implode(fields, ",");
  }

  /**
   * Validate parameter 'fields' within string.
   *
   * @param fields Provide comma delimited set of fields to validate.
   *
   * @return String Validated fields.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final String validateFields(
      final String fields
  ) throws  TuneSdkException,
            TuneServiceException {
    if ((null == fields) || fields.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'fields' is not defined.");
    }

    String[] fieldsArray = fields.split("\\,");
    if (fieldsArray.length == 0) {
      throw new IllegalArgumentException("Parameter 'fields' is not defined.");
    }

    List<String> setFields = Arrays.asList(fieldsArray);

    return this.validateFields(setFields);
  }

  /**
   * Validate query string parameter 'group' having valid endpoint's fields.
   *
   * @param group Set of fields.
   *
   * @return String         Validated group parameter.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final String validateGroup(
      final List<String> group
  ) throws  TuneSdkException,
            TuneServiceException {
    return this.validateFields(group);
  }

  /**
   * Validate query string parameter 'group' having valid endpoint's fields.
   *
   * @param group Comma delimited string of fields.
   *
   * @return String         Validated group parameter.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final String validateGroup(
      final String group
  ) throws  TuneSdkException,
            TuneServiceException {
    return this.validateFields(group);
  }

  /**
   * Validate query string parameter 'sort' having valid endpoint's
   * fields and direction.
   *
   * @param fields Fields to validate sort parameters.
   * @param sort Provide a set of sort pairs of field and direction to validate.
   *
   * @return String     Validated sort parameter.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final String validateSort(
      Set<String> fields,
      final Map<String, String> sort
  ) throws  TuneSdkException,
            TuneServiceException {
    if ((null == sort) || sort.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'sort' is not defined.");
    }

    Set<String> sortFields = sort.keySet();

    if (this.validateFields) {
      if (null == this.endpointFields || this.endpointFields.isEmpty()) {
        this.getFieldsSet();
      }
      Set<String> endpointFields = this.endpointFields.keySet();
      for (String sortField : sortFields) {
        sortField = sortField.trim();
        if (sortField.isEmpty()) {
          throw new IllegalArgumentException(
            String.format(
              "Parameter 'sort' contains an empty field.",
              sortField
            )
          );
        }
        if (!endpointFields.contains(sortField)) {
          throw new IllegalArgumentException(
            String.format(
              "Parameter 'sort' contains an invalid field: '%s'.",
              sortField
            )
          );
        }
        String sortDirection = sort.get(sortField);

        sortDirection = sortDirection.toUpperCase();

        if (!EndpointBase.SORT_DIRECTIONS.contains(sortDirection)) {
          throw new IllegalArgumentException(
            String.format(
              "Parameter 'sort' contains an invalid direction: '%s'.",
              sortDirection
            )
          );
        }
      }
    }

    StringBuilder sb = new StringBuilder();
    for (String sortField : sortFields) {
      sortField = sortField.trim();
      String sortDirection = sort.get(sortField);

      if ((null != fields) && !fields.contains(sortField)) {
        fields.add(sortField);
      } else {
        fields = new HashSet<String>();
        fields.add(sortField);
      }
      sortDirection = sortDirection.toUpperCase();
      String sortParameter = String.format("%s:%s", sortField, sortDirection);
      if (sb.length() == 0) {
        sb.append(sortParameter);
      } else {
        sb.append(",").append(sortParameter);
      }
    }

    return sb.toString();
  }

  /**
   * Validate query string parameter 'filter' having valid endpoint's fields
   * and filter expressions.
   *
   * @param filter  Provide SQL filter parameter to validate.
   *
   * @return String       Validated filter request.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public final String validateFilter(
      final String filter
  ) throws  TuneSdkException,
            TuneServiceException {
    if ((null == filter) || filter.isEmpty()) {
      throw new IllegalArgumentException("Parameter 'filter' is not defined.");
    }

    if (!EndpointBase.isParenthesesBalanced(filter)) {
      throw new TuneSdkException(
        String.format(
          "Invalid parameter 'filter' is not parentheses balanced: '%s'.",
          filter
        )
      );
    }
    String filterPruned = filter;
    filterPruned = filterPruned.replaceAll("\\(", " ");
    filterPruned = filterPruned.replaceAll("\\)", " ");
    filterPruned = filterPruned.trim().replaceAll(" +", " ");
    String[] filterParts = filterPruned.split(" ");

    Set<String> endpointFields = null;
    if (this.validateFields) {
      if (null == this.endpointFields || this.endpointFields.isEmpty()) {
        this.getFieldsSet();
      }

      endpointFields = this.endpointFields.keySet();
    }

    for (String filterPart : filterParts) {
      filterPart = filterPart.trim();
      if (filterPart.isEmpty()) {
        continue;
      }

      if (filterPart.startsWith("'") && filterPart.endsWith("'")) {
        continue;
      }

      try {
        Integer.parseInt(filterPart);
        continue;
      } catch (NumberFormatException e) {
        // ignore
      }

      if (EndpointBase.FILTER_OPERATIONS.contains(filterPart)) {
        continue;
      }

      if (EndpointBase.FILTER_CONJUNCTIONS.contains(filterPart)) {
        continue;
      }

      if (filterPart.matches("[a-z0-9\\.\\_]+")) {
        if (this.validateFields) {
          if (endpointFields.contains(filterPart)) {
            continue;
          }
        } else {
          continue;
        }
      }

      throw new IllegalArgumentException(
        String.format(
          "Parameter 'filter' is invalid: '%s': '%s'.",
          filter,
          filterPart
        )
      );
    }

    return String.format("(%s)", filter);
  }

  /**
   * Validates that provided date time is either "yyyy-MM-dd"
   * or "yyyy-MM-dd HH:mm:ss".
   *
   * @param paramName   Which date time parameter, typically
   *                    'start_date' or 'end_date'.
   * @param dateTime   Date time
   * @return String Validated date time.
   */
  public static String validateDateTime(
      final String paramName,
      final String dateTime
  ) {
    if ((null == paramName) || paramName.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'paramName' is not defined."
      );
    }
    if ((null == dateTime) || dateTime.isEmpty()) {
      throw new IllegalArgumentException(
        String.format("Parameter '%s' is not defined.", paramName)
      );
    }
    SimpleDateFormat simpleDateFormat
        = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateTimeFormat
        = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date date = simpleDateFormat.parse(dateTime);
      if (dateTime.equals(simpleDateFormat.format(date))) {
        return dateTime;
      }

      Date datetime = simpleDateTimeFormat.parse(dateTime);
      if (dateTime.equals(simpleDateTimeFormat.format(datetime))) {
        return dateTime;
      }

      throw new IllegalArgumentException(
        String.format(
          "Parameter '%s' is invalid date-time: '%s'.", paramName, dateTime
        )
      );
    } catch (ParseException ex) {
      throw new IllegalArgumentException(
        String.format(
          "Parameter '%s' is invalid date-time: '%s'.", paramName, dateTime
        ),
        ex
      );
    }
  }

  /**
   * Helper function for fetching report document given provided job identifier.
   *
   * <p>
   * Requesting for report url is not the same for all report endpoints.
   * </p>
   *
   * @param exportController  Controller for report export status.
   * @param exportAction      Action for report export status.
   * @param jobId             Job Identifier of report on queue.
   *
   * @return TuneManagementResponse
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  protected final TuneManagementResponse fetchRecords(
      final String exportController,
      final String exportAction,
      final String jobId
  ) throws TuneServiceException,
          TuneSdkException {
    if ((null == exportController) || exportController.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'exportController' is not defined."
      );
    }
    if ((null == exportAction) || exportAction.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'exportAction' is not defined."
      );
    }
    if ((null == jobId) || jobId.isEmpty()) {
      throw new IllegalArgumentException(
        "Parameter 'jobId' is not defined."
      );
    }
    if ((null == this.apiKey) || this.apiKey.isEmpty()) {
      throw new TuneSdkException("Parameter 'apiKey' is not defined.");
    }

    try {
      this.sdkConfig = SdkConfig.getInstance();
    } catch (TuneSdkException e) {
      throw e;
    }

    Integer sleep = this.sdkConfig.getFetchSleep();
    Integer timeout = this.sdkConfig.getFetchTimeout();
    Boolean verbose = this.sdkConfig.getFetchVerbose();

    ReportExportWorker exportWorker = new ReportExportWorker(
        exportController,
        exportAction,
        this.apiKey,
        jobId,
        verbose,
        sleep,
        timeout
    );

    if (verbose) {
      System.out.println("Starting worker...");
    }
    if (exportWorker.run()) {
      if (verbose) {
        System.out.println("Completed worker...");
      }
    }

    TuneManagementResponse response = exportWorker.getResponse();
    if (null == response) {
      throw new TuneServiceException(
        "Report export request no response."
      );
    }

    int httpCode = response.getHttpCode();
    if (httpCode != HTTP_STATUS_OK) {
      throw new TuneServiceException(
        String.format("Report export request error: '%d'", httpCode)
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
   * @return String Stringified contents of this object.
   */
  public final String toString() {
    return String.format(
      "Endpoint '%s', API Key: '%s",
      this.controller,
      this.apiKey
    );
  }

  /**
   * Parse response and gather job identifier.
   *
   * @param response @see TuneManagementResponse
   *
   * @return String  Report Job ID upon Export queue.
   * @throws TuneServiceException If service fails to handle post request.
   * @throws TuneSdkException If fails to post request.
   */
  public static String parseResponseReportJobId(
      final TuneManagementResponse response
  ) throws  TuneServiceException,
            TuneSdkException {
    String jobId = null;
    if (null == response) {
      throw new IllegalArgumentException(
        "Parameter 'response' is not defined."
      );
    }
    Object jdata = response.getData();
    if (null == jdata) {
      throw new TuneServiceException(
        "Report request failed to get export data."
      );
    }
    jobId = jdata.toString();
    if ((null == jobId) || jobId.isEmpty()) {
      throw new TuneSdkException(
        "Parameter 'jobId' is not defined."
      );
    }
    return jobId;
  }

  /**
   * Parse response and gather report url.
   *
   * @param response @see TuneManagementResponse
   *
   * @return String   Report URL download from Export queue.
   * @throws TuneSdkException If fails to post request.
   * @throws TuneServiceException If service fails to handle post request.
   */
  public static String parseResponseReportUrl(
      final TuneManagementResponse response
  ) throws TuneSdkException, TuneServiceException {

    if (null == response) {
      throw new IllegalArgumentException(
        "Parameter 'response' is not defined."
      );
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

    JSONObject jdataInternal = null;
    try {
      jdataInternal = jdata.getJSONObject("data");
    } catch (JSONException ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    } catch (Exception ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    }

    if (null == jdataInternal) {
      throw new TuneServiceException(
        String.format(
          "Export data response does not contain 'data', response: %s",
          response.toString()
        )
      );
    }

    if (!jdataInternal.has("url")) {
      throw new TuneSdkException(
      String.format(
        "Export response 'data' does not contain 'url', response: %s",
        response.toString()
     )
     );
    }

    String jdataInternalUrl = null;
    try {
      jdataInternalUrl = jdataInternal.getString("url");
    } catch (JSONException ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    } catch (Exception ex) {
      throw new TuneSdkException(ex.getMessage(), ex);
    }

    if ((null == jdataInternalUrl) || jdataInternalUrl.isEmpty()) {
      throw new TuneSdkException(
        String.format(
          "Export response 'url' is not defined, response: %s",
          response.toString()
        )
      );
    }

    return jdataInternalUrl;
  }

  /**
   * Validate any parentheses within string are balanced.
   * @param str  Contents partitioned with parentheses.
   * @return Boolean If contents contains balanced parentheses returns true.
   */
  public static boolean isParenthesesBalanced(
      final String str
  ) {
    if (str.isEmpty()) {
      return true;
    }

    Stack<Character> stack = new Stack<Character>();
    for (int i = 0; i < str.length(); i++) {
      char current = str.charAt(i);
      if (current == '{' || current == '(' || current == '[') {
        stack.push(current);
      }

      if (current == '}' || current == ')' || current == ']') {
        if (stack.isEmpty()) {
          return false;
        }

        char last = stack.peek();
        if (current == '}'
            && last == '{' || current == ')'
            && last == '(' || current == ']'
            && last == '[') {
          stack.pop();
        } else {
          return false;
        }
      }
    }

    return stack.isEmpty();
  }

  /**
   * Given a list of strings, determine it contains no duplicates.
   * @param all   An iterable set of strings to valid if it contain duplicates.
   * @return Boolean  If duplicate is found then return true.
   */
  public static boolean hasDuplicate(
      final Iterable<String> all
  ) {
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
   * @param all   An iterable set of strings to combine.
   * @param glue  Delimiter to combine set of strings.
   * @return String Delimited by provided glue.
   */
  public static String implode(
      final Iterable<String> all,
      final String glue
  ) {
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
   * @param all   Contents delimited.
   * @param glue  Delimiter.
   * @return Set
   */
  public static Set<String> explode(
      final String all,
      final String glue
  ) {
    String[] items = all.split(glue);

    Set<String> set = new HashSet<String>();
    for (String item : items) {
      set.add(item.trim());
    }

    return set;
  }
}
