<h2>tune-reporting-java</h2>
<h2>Tune Reporting API SDK for Java 1.6</h2>
<h3>Incorporate Tune Reporting API services.</h3>
<h4>Update:  $Date: 2014-12-12 13:00:00 $</h4>
<h4>Version: 0.9.2</h4>
===

### Overview

Java helper library for Tune Reporting API services.

The utility focus of this SDK is upon the Advertiser Reporting endpoints.

Even though the the breadth of the Management API goes beyond just reports, it is these endpoints that our customers primarily access.

The second goal of the SDKs is to assure that our customers’ developers are using best practices in gathering reports in the most optimal way.

### Documentation

Please see documentation here:

[Tune Reporting API SDKs](https://developers.mobileapptracking.com/tune-reporting-sdks/)

<a name="sdk_requirements"></a>
### SDK Requirements

<a name="sdk_prerequisites"></a>
#### Prerequisites

    * Java >= 1.6

<a name="generate_api_key"></a>
#### Generate API Key

To use SDK, it requires you to [Generate API Key](http://developers.mobileapptracking.com/generate-api-key/)

<a name="sdk_installation"></a>
### Installation

##### Maven

*Tune Reporting API SDK for Java* is available using Maven.  At present the jar is available from a public [maven](http://maven.apache.org/download.html) repository.

Use the following dependency in your project:

```xml
       <dependency>
          <groupId>com.tune.reporting</groupId>
          <artifactId>tune-reporting</artifactId>
          <version>0.9.2</version>
          <scope>compile</scope>
       </dependency>
```

##### Github Compile

If you want to compile it yourself, here's how:

```bash
    $ git clone git@github.com:MobileAppTracking/tune-reporting
    $ cd tune-reporting
    $ mvn install       # Requires maven, download from http://maven.apache.org/download.html
```

##### Pre-built Jar

Pre-built jars are available [here](http://search.maven.org/#browse%7C-1416163511). Select the directory for
the latest version and download this jar file:

* tune-reporting-x.x.x.jar

<a name="sdk_installation_zip"></a>
##### Via ZIP file:

[Click here to download the source code
(.zip)](https://github.com/MobileAppTracking/tune-reporting-java/archive/master.zip) for `tune-reporting`.


<a name="sdk_examples"></a>
#### SDK Examples

Run the following script to view execution of all examples:

```bash
    $ make api_key=[API_KEY] ant-examples
```

<a name="sdk_unittests"></a>
#### SDK Unittests

Run the following script to view execution of all unittests:

```bash
    $ make api_key=[API_KEY] ant-tests
```

```bash
    $ make api_key=[API_KEY] maven-tests
```

<a name="sdk_documentation"></a>
#### SDK Documentation

The following will generate [Javadoc](http://en.wikipedia.org/wiki/Javadoc) from Java codebase:

```bash
    $ make docs-javadoc
```

The following will generate [Doxygen](http://en.wikipedia.org/wiki/Doxygen) from Java codebase:

```bash
    $ make docs-doxygen
```

<a name="license"></a>
### License

[MIT License](http://opensource.org/licenses/MIT)

<a name="sdk_reporting_issues"></a>
### Reporting Issues

Report issues using the [Github Issue Tracker](https://github.com/MobileAppTracking/tune-reporting-java/issues) or Email [sdk@tune.com](mailto:sdk@tune.com).
