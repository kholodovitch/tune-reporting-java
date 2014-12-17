<h2>tune-reporting-java</h2>
<h2>Tune Reporting SDK for Java 1.6</h2>
<h3>Incorporate Tune Reporting services.</h3>
<h4>Update:  $Date: 2014-12-16 12:00:00 $
<h4>Version: 0.9.2
===

<a id="TOP"></a>
### Table of Contents

<ul>
    <li><a href="#sdk_overview">Overview</a>
        <ul>
            <li><a href="#sdk_overview_available">Available Tune Reporting SDKs</a></li>
            <li><a href="#sdk_overview_mobile">Tune SDKs for Mobile Apps</a></li>
            <li><a href="#sdk_overview_dev_community">Developers Community</a></li>
        </ul>
    </li>
    <li><a href="#sdk_install">SDK Installation</a>
        <ul>
            <li><a href="#sdk_install_prereq">Prerequisites</a>
                <ul>
                    <li><a href="#sdk_install_prereq_env">Environment</a></li>
                    <li><a href="#sdk_install_prereq_ini">java.ini</a></li>
                    <li><a href="#sdk_install_prereq_apikey">Environment</a></li>
                </ul>
            </li>
            <li><a href="#sdk_install_choices">Choices</a>
                <ul>
                    <li><a href="#sdk_install_method_maven">Composer</a></li>
                    <li><a href="#sdk_install_method_zip">ZIP</a></li>
                    <li><a href="#sdk_prerequisites_api_key">Environment</a></li>
                </ul>
            </li>
            <li><a href="#sdk_install_library">Library</a></li>
        </ul>
    </li>

    <li><a href="#sdk_gendoc">SDK Generated Documentation</a>
        <ul>
            <li><a href="#sdk_gendoc_doxygen">Doxygen</a></li>
            <li><a href="#sdk_gendoc_javadoc">Javadoc</a></li>
        </ul>
    </li>

    <li><a href="#sdk_advertiser_reporting_overview">Advertiser Reporting Overview</a>
    </li>

    <li><a href="#sdk_exporting_reports">Exporting Advertiser Reports</a>
    </li>

    <li><a href="#sdk_sources">SDK Sources</a>
        <ul>
            <li><a href="#sdk_sources_lib">Library</a></li>
            <li><a href="#sdk_sources_examples">Examples</a></li>
            <li><a href="#sdk_sources_tests">Tests</a></li>
        </ul>
    </li>

    <li><a href="#sdk_classes">SDK Classes</a>
        <ul>
            <li><a href="#sdk_classes_service">Tune Management Service Classes</a></li>
            <li><a href="#sdk_report_readers">Helper Classes</a></li>
            <li><a href="#sdk_classes_exceptions">Exception Classes</a></li>
        </ul>
    </li>

    <li>
        <a href="#sdk_methods">Advertiser Reporting Methods</a>
        <ul>
            <li><a href="#sdk_method_count"><code>count()</code></a></li>
            <li><a href="#sdk_method_find"><code>find()</code></a></li>
            <li><a href="#sdk_method_export"><code>export()</code></a></li>
            <li><a href="#sdk_method_status"><code>status()</code></a></li>
            <li><a href="#sdk_method_fetch"><code>fetch()</code></a></li>
            <li><a href="#sdk_method_fields"><code>fields()</code></a></li>
            <li><a href="#sdk_method_define"><code>define()</code></a></li>
        </ul>
    </li>

    <li><a href="#sdk_reporting_fields">Advertiser Reporting Fields</a>
    </li>

    <li>
        <a href="#sdk_parameters">Advertiser Reporting Parameters</a>
        <ul>
            <li><a href="#sdk_parameter_fields"><code>fields</code></a></li>
            <li><a href="#sdk_parameter_group"><code>group</code></a></li>
            <li><a href="#sdk_parameter_sort"><code>sort</code></a></li>
            <li><a href="#sdk_parameter_filter"><code>filter</code></a></li>
        </ul>
    </li>

    <li><a href="#sdk_license">MIT License</a>
    </li>

    <li><a href="#sdk_issues">SDK Issues</a>
    </li>
</ul>

<p>
<a href="#TOP">
<img alt="Return to Top" src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/b_top.gif" border="0">
</a>
</p>

<!-- Overview -->

<a id="sdk_overview" name="sdk_overview"></a>
### Overview

The **Tune Reporting SDKs** addressed in this posting are for creating hosted applications which require handling requests to **Tune Management API services** with utility focus is upon Advertiser Reporting endpoints.

Even though the the breadth of the Management API goes beyond just reports, it is these reporting endpoints that our customers primarily access.

The second goal of the SDKs is to assure that our customers’ developers are using best practices in gathering reports in the most optimal way.

<a id="sdk_overview_available" name="sdk_overview_available"></a>
#### Available Tune Reporting SDKs

Supported programming languages for Tune Reporting SDKs are:

<ul>
    <li><b>PHP</b>: <a href="https://github.com/MobileAppTracking/tune-reporting-php" target="_blank">tune-reporting-php</a></li>
    <li><b>Python</b>: <a href="https://github.com/MobileAppTracking/tune-reporting-python" target="_blank">tune-reporting-python</a></li>
    <li><b>Java</b>: <a href="https://github.com/MobileAppTracking/tune-reporting-java" target="_blank">tune-reporting-java</a></li>
    <li><b>Node.js</b>: <a href="https://github.com/MobileAppTracking/tune-reporting-node" target="_blank">tune-reporting-node</a></li>
    <li><b>Go</b>: Coming soon</li>
    <li><b>C#</b>: Coming soon</li>
</ul>

<a id="sdk_overview_mobile" name="sdk_overview_mobile"></a>
#### Tune SDKs for Mobile Apps

The **Tune Reporting SDKs** should absolutely not be included within Mobile Apps.

All information pertaining to **Tune SDKs for Mobile Apps** are found [here](http://developers.mobileapptracking.com/sdks/).

<a id="sdk_overview_dev_community" name="sdk_overview_dev_community"></a>
#### Developers Community

Developer Community portal for MobileAppTracking™ (MAT), the industry leader in mobile advertising attribution and analytics. From API documentation to best practices, get everything you need to be successful with MAT.

[https://developers.mobileapptracking.com](https://developers.mobileapptracking.com)

Additional positions on Tune Reporting SDKs can be found here:

[https://developers.mobileapptracking.com/tune-reporting-sdks/](https://developers.mobileapptracking.com/tune-reporting-sdks/)

<p>
<a href="#TOP">
<img alt="Return to Top" src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/b_top.gif" border="0">
</a>
</p>

<!-- Installation -->

<a id="sdk_install" name="sdk_install"></a>
### SDK Installation

This section detail what is required to use this SDK and how to install it for usage.

<a id="sdk_install_prereq" name="sdk_install_prereq"></a>
#### Installation Prerequisites

<a id="sdk_install_prereq_env" name="sdk_install_prereq_env"></a>
##### Environment

These are the basic requirements to use this SDK:

    * Java 2.7 or Java 3.0

<a id="sdk_install_prereq_apikey" name="sdk_install_prereq_apikey"></a>
##### Generate API Key

To use SDK to access Advertiser Reporting endpoints of Tune Management API, it requires a MobileAppTracking API Key: [Generate API Key](http://developers.mobileapptracking.com/generate-api-key/).

<a id="sdk_install_choices" name="sdk_install_choices"></a>
#### Installation Choices

You can install this either via **Maven** or by downloading the **ZIP** source.

<a id="sdk_install_method_maven" name="sdk_install_method_maven"></a>
##### Via Maven:

*Tune Reporting SDK for Java* is available using Maven.

At present the jar is available from a public [maven](http://maven.apache.org/download.html) repository.

Use the following dependency in your project:

```xml
    <dependency>
       <groupId>com.tune.reporting</groupId>
       <artifactId>tune-reporting</artifactId>
       <version>0.9.2</version>
       <scope>compile</scope>
    </dependency>
```

<a id="sdk_install_method_zip" name="sdk_install_method_zip"></a>
##### Via ZIP file:

If you want to compile it yourself, here's how.

Requires maven, download from http://maven.apache.org/download.html

```bash
    $ git clone git@github.com:MobileAppTracking/tune-reporting
    $ cd tune-reporting
    $ mvn install
```

You may need to run the above commands with `sudo`.

<p>
<a href="#TOP">
<img alt="Return to Top" src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/b_top.gif" border="0">
</a>
</p>

<!-- Generated Documentation -->

<a id="sdk_gendoc" name="sdk_gendoc"></a>
### SDK Generated Documentation

SDK code is well commented and to see full documentation of its source using the provided Makefile commands that initiate code documentation generators.
<a id="sdk_gendoc_doxygen" name="sdk_gen_doc_doxygen"></a>
#### Doxygen

The following will generate <a href="http://en.wikipedia.org/wiki/Doxygen" title="Doxygen" target="_blank">Doxygen</a> from Java codebase:

This code documentation generation requires installation of [Doxygen](http://www.stack.nl/~dimitri/doxygen/index.html).

```bash
    $ make docs-doxygen
```

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/TuneReporting_Java_Doxygen.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/TuneReporting_Java_Doxygen-400x213.png" alt="Tune-Reporting Java Doxygen Generated" width="400" height="213">
</a>

<a id="sdk_gendoc_javadoc" name="sdk_gen_doc_javadoc"></a>
#### Javadoc

The following will generate [Javadoc](http://en.wikipedia.org/wiki/Javadoc) from Java codebase:

This code documentation generation may require installation of [Javadoc](http://www.oracle.com/technetwork/articles/java/index-jsp-135444.html).

<pre lang="bash">
    $ make docs-javadoc
</pre>

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/TuneReporting_Java_Javadoc.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/TuneReporting_Java_Javadoc-400x222.png" alt="Tune-Reporting Java Javadoc Generated" width="400" height="222">
</a>

<p>
<a href="#TOP">
<img alt="Return to Top" src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/b_top.gif" border="0">
</a>
</p>

<p>&nbsp;</p>
<a id="sdk_advertiser_reporting_overview" name="sdk_advertiser_reporting_overview"></a>
### Advertiser Reporting Overview

The utility focus of the SDKs is upon the <a href="/advertiser-reporting-endpoints/">Advertiser Reporting endpoints</a>. Even though the the breadth of the Management API goes beyond just reports, it is these endpoints that our customers primarily access. The second goal of the SDKs is to assure that our customers' developers are using best practices in gathering reports in the most optimal way.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/tune_advertiser_reporting_classes.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/tune_advertiser_reporting_classes.png" alt="Tune Advertiser Reporting Classes" width="500" height="350" /></a>

The endpoints interfaced by TUNE API SDKs provide access in gathering four types of reports:

<dl>
<dt>Log Reports</dt>
<dd>
Log reports provide measurement records for each Click, Install, Event, Event Item and Postback. Instead of being aggregated, the data is on a per transaction / request basis. MobileAppTracking&trade; (MAT) uses these logs to generate the aggregated data for the Actuals and Cohort reports. Note that we don’t provide Log reports for Impressions and Opens currently.

Advertiser Reporting classes that perform Log Reports are:
<ul>
    <li><code>AdvertiserReportClicks</code>: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__clicks/">/advertiser/stats/clicks/</a></li>
    <li><code>AdvertiserReportEventItems</code>:<a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__event__items/">/advertiser/stats/event/items/</a></li>
    <li><code>AdvertiserReportEvents</code>:<a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__events/">/advertiser/stats/events/</a></li>
    <li><code>AdvertiserReportInstalls</code>:<a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__installs/">/advertiser/stats/installs/</a></li>
    <li><code>AdvertiserReportPostbacks</code>:<a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__postbacks/">/advertiser/stats/postbacks/</a></li>
</ul>

</dd>
<dt>Actuals Report</dt>
<dd>
The Actuals report gives you quick insight into the performance of your apps and advertising partners (publishers). Use this report for reconciliation, testing, debugging, and ensuring that all measurement and attribution continues to operate smoothly. MAT generates this report by aggregating all the logs of each request (MAT updates the report every 5 minutes).

Actuals report endpoint include: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats/">/advertiser/stats/</a>: Reports' class <a href="#sdk-advertiser-report-actuals"><code>AdvertiserReportActuals</code></a>

Advertiser Reporting class that perform Actuals Reports is:
<ul>
    <li><code>AdvertiserReportActuals</code>: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats/">/advertiser/stats/</a></li>
</ul>
</dd>
<dt>Cohort Report</dt>
<dd>
The Cohort report analyzes user behavior back to click date time (Cohort by Click) or to install date time (Cohort by Install). Depending on whether you view the results based on click or install, the data in the report is vastly different.

Advertiser Reporting class that perform Cohort Reports is:
<ul>
    <li><code>AdvertiserReportCohort</code>: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__ltv">/advertiser/stats/ltv</a></li>
</ul>
</dd>
<dt>Retention Report</dt>
<dd>
The Retention report shows you how many of your installed users open or engage with your app over time (how users continue to get value from the app). AdvertiserReportRetention reports are particularly good for evaluating the quality of users as opposed to the quantity of users (as in the case of user acquisition campaigns). For more information about retention reports, please visit <a href="http://support.mobileapptracking.com/entries/42179044-Running-AdvertiserReportRetention-Reports">Running AdvertiserReportRetention Reports</a>.

Advertiser Reporting class that perform Retention Reports are:
<ul>
    <li><code>AdvertiserReportRetention</code>: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__retention">/advertiser/stats/retention</a></li>
</ul>
</dd>
</dl>

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/tune_management_service_reporting_endpoints.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/tune_management_service_reporting_endpoints.png" alt="Management API Advertiser Reports covered by Tune Reporting SDKs." width="592" height="292" /></a>

<p>
<a href="#TOP">
<img alt="Return to Top" src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/b_top.gif" border="0">
</a>
</p>

<a id="sdk_exporting_reports" name="sdk_exporting_reports"></a>
### Exporting Advertiser Reports
Currently, there are two different ways of handling advertiser report exports. Both approaches require (A) an action to request that a report be exported and (B) another action to request the report status (if ready to be exported), and if ready, then provide a URL to download the completed report.

Logs and Actuals reports all request an export using action <code>find_export_queue.json</code>, which returns a <code>job_id</code>. You then pass the <code>job_id</code> onto another endpoint <code>Export::download.json</code>, which performs the status checking and report URL retrieval.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-report-exports1.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-report-exports1-600x569.png" alt="Exporting logs and actuals reports." width="600" height="569" /></a>

Cohort and AdvertiserReportRetention reports all request an export using action <code>export.json</code>, which also returns a <code>job_id</code>. You then pass the <code>job_id</code> onto another action <code>status.json</code>, which performs the status checking and report URL retrieval.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-insight-report-exports.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-insight-report-exports-600x459.png" alt="Export cohort and retention reports." width="600" height="459" /></a>

<p>
<a href="#TOP">

<img alt="Return to Top" src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/b_top.gif" border="0">
</a>
</p>

<!-- Sources -->

<a id="sdk_sources" name="sdk_sources"></a>
### SDK Sources

The key contents of SDK is **src**, which contains the library; followed by the **examples**, and **tests**.

File **Makefile** provides shortcuts for executing examples and tests.

```
├── AUTHORS.md
├── build.xml
├── CHANGES.md
├── docs
├── LICENSE.md
├── Makefile
├── pom.xml
├── README
├── README.html
├── README.md
└── src
```

<a id="sdk_sources_lib" name="sdk_sources_lib"></a>
#### Library

File **TuneReporting.java** is the root of this Library.

Library folder **src** contains the key functionality related to **Advertiser Reporting classes** are defined within folder **com.tune.reporting.api**.

Client classes that connect with the **Tune Management API Service** are defined within folder **com.tune.reporting.base.service**.

Helper class for both the Library and Examples are defined within folder **com.tune.reporting.helpers**.
```
src/main/java/
└── com
    └── tune
        └── reporting
            ├── api
            │   ├── AdvertiserReportActuals.java
            │   ├── AdvertiserReportClicks.java
            │   ├── AdvertiserReportCohort.java
            │   ├── AdvertiserReportEventItems.java
            │   ├── AdvertiserReportEvents.java
            │   ├── AdvertiserReportInstalls.java
            │   ├── AdvertiserReportPostbacks.java
            │   ├── AdvertiserReportRetention.java
            │   └── Export.java
            ├── base
            │   ├── endpoints
            │   │   ├── AdvertiserReportActualsBase.java
            │   │   ├── AdvertiserReportBase.java
            │   │   ├── AdvertiserReportInsightBase.java
            │   │   ├── AdvertiserReportLogsBase.java
            │   │   └── EndpointBase.java
            │   └── service
            │       ├── QueryStringBuilder.java
            │       ├── TuneManagementClient.java
            │       ├── TuneManagementProxy.java
            │       ├── TuneManagementRequest.java
            │       └── TuneManagementResponse.java
            └── helpers
                ├── ReportExportWorker.java
                ├── ReportReaderBase.java
                ├── ReportReaderCsv.java
                ├── ReportReaderJson.java
                ├── TuneSdkException.java
                └── TuneServiceException.java
```

<a id="sdk_sources_examples" name="sdk_sources_examples"></a>
#### SDK Examples

Run the following script to view execution of all examples:
```bash
    $ make api_key=[API_KEY] ant-examples
```

Each Advertiser Report class defined in **com.tune.reporting.api** has an example:

```
src/test/java/
└── com
    └── tune
        └── reporting
            └── examples
                ├── ExampleAdvertiserReportActuals.java
                ├── ExampleAdvertiserReportClicks.java
                ├── ExampleAdvertiserReportCohort.java
                ├── ExampleAdvertiserReportEventItems.java
                ├── ExampleAdvertiserReportEvents.java
                ├── ExampleAdvertiserReportInstalls.java
                ├── ExampleAdvertiserReportPostbacks.java
                ├── ExampleAdvertiserReportRetention.java
                └── ExampleTuneManagementClient.java
```

<a id="sdk_sources_tests" name="sdk_sources_tests"></a>
#### SDK Tests


Run the following script to view execution of all unittests:
```bash
    $ make api_key=[API_KEY] ant-tests
```

Each Advertiser Report class defined in **com.tune.reporting.api** has a test:

```
src/test/java/
└── com
    └── tune
        └── reporting
            ├── TestAdvertiserReportActuals.java
            ├── TestAdvertiserReportClicks.java
            ├── TestAdvertiserReportCohort.java
            ├── TestAdvertiserReportEventItems.java
            ├── TestAdvertiserReportEvents.java
            ├── TestAdvertiserReportInstalls.java
            ├── TestAdvertiserReportPostbacks.java
            ├── TestAdvertiserReportRetention.java
            └── TestTuneManagementClient.java
```

<p>
<a href="#TOP">
<img alt="Return to Top" src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/b_top.gif" border="0">
</a>
</p>

<!-- Classes -->
<a id="sdk_classes" name="sdk_classes"></a>
### SDK Classes

<!-- Tune Management API Service -->
<a id="sdk_classes_service" name="sdk_classes_service"></a>
#### Tune Management API Service Classes

<ul>
    <li><code>TuneManagementClient</code> - Connects with <a href="http://developers.mobileapptracking.com/management-api/" target="_blank">Tune Management API Service</a></li>
    <li><code>TuneManagementRequest</code> - Defines request to Tune Management API Service containing:
        <ul>
            <li>Controller / Endpoint</li>
            <li>Action</li>
            <li>Query String Parameters
                <ul>
                    <li>API Key</li>
                </ul>
            </li>
        </ul>
    </li>
    <li><code>TuneManagementResponse</code> - Complete response from Tune Management API Service containing:
        <ul>
            <li>Status Code</li>
            <li>Data</li>
            <li>Errors</li>
        </ul>
    </li>
</ul>

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/tune_reporting_service_classes.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/tune_reporting_service_classes.png" alt="Tune Management Service Classes" width="217" height="163" /></a>

<!-- Example Helpers -->
<a id="sdk_report_readers" name="sdk_report_readers"></a>
#### Report Readers Classes

<ul>
    <li><code>ReportReaderCSV</code> - Reads exported CSV report using downloaded URL.</li>
    <li><code>ReportReaderJSON</code> - Reads exported JSON report using downloaded URL.</li>
</ul>

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/tune_reporting_reader_classes.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/tune_reporting_reader_classes.png" alt="Report Reader Helper Classes." width="217" height="163" title="Click to Expand" /></a>

<!-- Exceptions -->
<a id="sdk_classes_exceptions" name="sdk_classes_exceptions"></a>
#### Custom Exceptions Classes

<ul>
    <li><code>TuneSdkException</code> - Exception thrown if error occurs within Tune Reporting SDK.</li>
    <li><code>TuneServiceException</code> - Exception thrown if error condition is returned from Tune Management Service.</li>
</ul>

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/tune_reporting_exceptions.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/tune_reporting_exceptions.png" alt="Custom Exceptions." width="217" height="163" title="Click to Expand" /></a>


<a id="sdk_methods" name="sdk_methods"></a>
### Advertiser Reporting Methods

<strong>Important to note on Sample Code:</strong> The example provided pertain to only Advertiser Reports class <code>AdvertiserReportClicks</code>. The fields used theses sample primarily pertain to the available fields for the record and related records for the the associated endpoint <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__clicks/">/advertiser/stats/clicks</a> of this class. Do not expect that these fields will be available if used with other Advertiser Records classes.

The benefit of using Tune API SDKs is it provides the same interface across all advertiser reports. The following class diagram lists what are all the expected functions. The signature of the expected parameters for each function will be consistent with the action it is interfacing.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-sdk-class.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-sdk-class-600x262.png" alt="Report classes available methods." width="600" height="262" title="Click to Expand" /></a>

<a id="sdk_method_count" name="sdk_method_count"></a>
##### Method <code>count()</code>

Finds all existing records matching provided filter criteria and returns total count. It returns a populated instance of <code>class Response</code>, class of Tune API SDK, with <strong>record count</strong>.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-action-count.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-action-count-700x247.png" alt="Function count()" width="700" height="247" title="Click to Expand" /></a>

<!-- Java -->
```java
    AdvertiserReportClicks reportLogsClicks
        = new AdvertiserReportClicks(apiKey, true);
        
    TuneManagementResponse response = reportLogsClicks.count(
        startDate,
        endDate,
        null,     // filter
        "America/Los_Angeles"
    );

    if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    Object data = response.getData();
    if (null == data) {
      throw new TuneServiceException(
        "Report request failed to get export data."
      );
    }

    if (!(data instanceof Integer)) {
      throw new TuneServiceException("Data expected to be type integer.");
    }

    int count
        = Integer.parseInt(data.toString());
    System.out.println(String.format(" Count: '%d'", count));
```

<a id="sdk_method_find" name="sdk_method_find"></a>
##### Method <code>find()</code>

Gathers all existing records that match filter criteria and returns an array of found model data. Even though calling the action <code>find.json</code> is commonly used for gathering data, however it is not the preferred way of gathering full reports. It returns a populated instance of <code>class Response</code> with <strong>records</strong>.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-action-find.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-action-find-700x511.png" alt="Function find()" width="700" height="511" title="Click to Expand" /></a>

<!-- Java -->
```java
    AdvertiserReportClicks reportLogsClicks
        = new AdvertiserReportClicks(apiKey, true);
    String strFieldsRecommended
        = reportLogsClicks.getFields(EndpointBase.TUNE_FIELDS_RECOMMENDED);

    TuneManagementResponse response = reportLogsClicks.find(
        startDate,
        endDate,
        strFieldsRecommended,  // fields
        null,      // filter
        5,         // limit
        0,         // page
        sort,
        "America/Los_Angeles"     // responseTimezone
    );

    if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());
```

<a id="sdk_method_export" name="sdk_method_export"></a>
##### Method <code>export()</code>

Provides the same signature as function find(), accept parameters <code>limit</code> and <code>page</code>, because this function's intent is to request export of a full report. It returns a populated instance of <code>class Response</code> with <strong>job identifier</strong> of report in queue and ready to be processed. Format of content can be requested to be either CSV or JSON.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-action-export.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-action-export-700x338.png" alt="Function export()" width="700" height="338" title="Click to Expand" /></a>

<!-- Java -->
```java
    AdvertiserReportClicks reportLogsClicks
        = new AdvertiserReportClicks(apiKey, true);
    TuneManagementResponse response = reportLogsClicks.export(
        startDate,
        endDate,
        strFieldsRecommended,     // fields
        null,                     // filter
        "csv",                    // format
        "America/Los_Angeles"     // responseTimezone
    );

    if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
      throw new Exception(
        String.format(
          "Failed: %d: '%s'", response.getHttpCode(), response.toString()
        )
     );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    String csvJobId = AdvertiserReportClicks.parseResponseReportJobId(response);
    System.out.println(String.format(" CSV Job ID: '%s'", csvJobId));
```

<a id="sdk_method_status" name="sdk_method_status"></a>
##### Method <code>status()</code>


As discussed in <a href="#exporting-reports">Exporting Advertise Reports</a>, for gathering report export status records' classes <strong>Cohort (AdvertiserReportCohorts)</strong> and <strong>AdvertiserReportRetention</strong> uses it own method <code>status()</code>. Its purpose is the same as method <code>Export::download()</code>.


<a id="sdk_method_fetch" name="sdk_method_fetch"></a>
##### Method <code>fetch()</code>

A helper function that creates a threaded worker that handles the status request appropriate to it class. This function handles the polling of the service waiting for status of "complete" and its "report url". Upon completion, fetch downloads data into a reader that parses the contents that is appropriate requested content format type, CSV or JSON.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-action-fetch.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/management-api-action-fetch-700x337.png" alt="Function fetch()" width="700" height="337" title="Click to Expand" /></a>

<!-- Java -->
```java
    AdvertiserReportClicks reportLogsClicks
        = new AdvertiserReportClicks(apiKey, true);
    TuneManagementResponse response = reportLogsClicks.fetch(
        csvJobId,     // Job ID
        true,         // verbose
        10            // sleep in seconds
   );

    if ((response.getHttpCode() != 200) || (null != response.getErrors())) {
      throw new Exception(
        String.format(
          "Failed: %d: %s", response.getHttpCode(), response.toString()
        )
      );
    }

    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());

    String csvReportUrl = AdvertiserReportClicks.parseResponseReportUrl(response);
    System.out.println(String.format(" CSV Report URL: '%s'", csvReportUrl));

    System.out.println("====================================================");
    System.out.println(" Print Advertiser Report Clicks CSV report.   ");
    System.out.println("====================================================");

    ReportReaderCsv csvReader = new ReportReaderCsv(csvReportUrl);
    csvReader.read();
    csvReader.prettyPrint(5);
```

<a id="sdk_method_fields" name="sdk_method_fields"></a>
##### Method <code>fields()</code>

Method <strong>fields()</strong> returns a listing of all the fields that can be used that can be used by that report endpoint, which will include all the field of its immediate record and all its related records.

<!-- Java -->
```java
    AdvertiserReportClicks reportLogsClicks
        = new AdvertiserReportClicks(apiKey, true);
    Set<String> setFieldsRecommended
        = reportLogsClicks.getFieldsSet(EndpointBase.TUNE_FIELDS_RECOMMENDED);
    if ((null != setFieldsRecommended) && !setFieldsRecommended.isEmpty()) {
      for (String field : setFieldsRecommended) {
        System.out.println(field);
      }
    } else {
      System.out.println("No recommended fields");
    }
```

<a id="sdk_method_define" name="sdk_method_define"></a>
##### Method <code>define()</code>

Method <strong>define()</strong> returns the complete meta-data of an endpoint. Available actions, associated record it is bound and its fields, related records and its fields.

<!-- Java -->
```java
    AdvertiserReportClicks reportLogsClicks
        = new AdvertiserReportClicks(apiKey, true);
    TuneManagementResponse response = reportLogsClicks.define()
    System.out.println(" TuneManagementResponse:");
    System.out.println(response.toString());
```

<p>
<a href="#TOP">
<img alt="Return to Top" src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/b_top.gif" border="0">
</a>
</p>

<a id="sdk_reporting_fields" name="sdk_reporting_fields"></a>
### Advertiser Reporting Fields

It is important to understand that every endpoint has its own unique set of fields based upon the model its data is associated with, and the model's related entities.

Making a request with a field name that does not exist with the endpoint's set of available fields can cause a service error. So it is important to make sure that the field names used within method parameter type <code>fields</code>, <code>filter</code>, <code>sort</code>, and <code>group</code> are appropriate to the endpoint it is calling.

Two helpful functions that come with every report class are <code>define()</code> and <code>fields()</code>. Every advertiser reports endpoint has a different data model associated with it. With that, what fields are available are not consistent across records. So use these functions to understand available field choices.

Function <code>define()</code> returns a complete metadata mapping of the endpoint, and function <code>fields()</code> returns a complete listing of all field names of the model associated with the endpoint function was called through, and endpoint's related entities' field names.

In addition, the constructor for every advertiser records' class has a bool parameter <code>validate</code> which checks that the field names used within parameter values are valid.

Another tool is to pre-build your request using <a href="/management-api/explorer/root/">Management API Root Endpoints Explorer</a>.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/api_explorer_record_fields.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/api_explorer_record_fields-600x436.png" alt="API Explorer -- Record and Related Record&#039;s Fields for a specific endpoint." width="600" height="436" title="Click to Expand" /></a>

<ul>
    <li>AdvertiserReportClicks' fields: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__clicks/">/advertiser/stats/clicks fields</a></li>
    <li>AdvertiserReportEventItems' fields: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__event__items/">/advertiser/stats/event/items fields</a></li>
    <li>AdvertiserReportEvents' fields: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__events/">/advertiser/stats/events fields</a></li>
    <li>AdvertiserReportInstalls' fields: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__installs/">/advertiser/stats/installs fields</a></li>
    <li>AdvertiserReportPostbacks' fields: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__postbacks/">/advertiser/stats/postbacks fields</a></li>
    <li>AdvertiserReportActuals' fields: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats/">/advertiser/stats fields</a></li>
    <li>AdvertiserReportCohorts' fields: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__ltv/">/advertiser/stats/ltv fields</a></li>
    <li>AdvertiserReportRetention' fields: <a href="http://developers.mobileapptracking.com/management-api/explorer/root/endpoint/#/advertiser__stats__retention/">/advertiser/stats/retention fields</a></li>
</ul>

<p>
<a href="#TOP">
<img alt="Return to Top" src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/b_top.gif" border="0">
</a>
</p>


<a id="sdk_parameters" name="sdk_parameters"></a>
### Advertiser Reporting Parameters

Most of the functions provided for the record classes have the following parameters in common. All of these parameters are based upon knowledge of available fields for that endpoint, which are discovered using endpoint's functions <code>define()</code> and <code>fields()</code>.

<p>&nbsp;</p>
<a id="sdk_parameter_fields" name="sdk_parameter_fields"></a>
##### Parameter <code>fields</code>

Parameter <strong>fields</strong> can either be an array of field names, or a string containing comma-delimited field named:

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/parameter-fields.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/parameter-fields.png" alt="Parameter fields" width="655" height="121" /></a>

<p>&nbsp;</p>
<a id="sdk_parameter_group" name="sdk_parameter_group"></a>
##### Parameter <code>group</code>

Parameter <strong>group</strong> is the same as parameter <code>fields</code> can either be an array of field names, or a string containing comma-delimited field named:

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/parameter-group.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/parameter-group.png" alt="Parameter group" width="655" height="121" /></a>

<p>&nbsp;</p>
<a id="sdk_parameter_sort" name="sdk_parameter_sort"></a>
##### Parameter <code>sort</code>

Parameter <strong>sort</strong> is a dictionary (associative array), where the key is the field name and value is the expected sort direction of either <code>"DESC"</code> or <code>"ASC"</code>.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/parameter-sort.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/parameter-sort-700x107.png" alt="Parameter sort" width="700" height="107" /></a>

<p>&nbsp;</p>
<a id="sdk_parameter_filter" name="sdk_parameter_filter"></a>
##### Parameter <code>filter</code>

Parameter <strong>filter</strong> is a string that contains a set of query expressions based upon matching conditions to endpoint's fields. It is especially important to to provide an invalid field name because that will cause request to fail.

<a href="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/parameter-filter.png">
<img src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/parameter-filter-700x350.png" alt="Parameter filter" width="700" height="350" /></a>

<p>
<a href="#TOP">
<img alt="Return to Top" src="https://raw.githubusercontent.com/MobileAppTracking/tune-reporting-java/master/docs/images/b_top.gif" border="0">
</a>
</p>

<!-- Licenses -->

<a id="sdk_license" name="sdk_license"></a>
### License

[MIT License](http://opensource.org/licenses/MIT)

<a id="sdk_issues" name="sdk_issues"></a>
### Reporting Issues

Report issues using the [Github Issue Tracker](https://github.com/MobileAppTracking/tune-reporting-java/issues) or Email [sdk@tune.com](mailto:sdk@tune.com).
