<h2>tune-api-java</h2>
<h2>Tune API SDK for Java 1.6</h2>
<h3>Incorporate Tune API services.</h3>
<h4>Update:  $Date: 2014-11-21 14:12:20 $</h4>
<h4>Version: 0.9.0</h4>
===

### Overview
Tune API client for PHP developers.

The utility focus of the SDKs is upon the Advertiser Reporting endpoints. Even though the the breadth of the Management API goes beyond just reports, it is these endpoints that our customers primarily access. The second goal of the SDKs is to assure that our customers’ developers are using best practices in gathering reports in the most optimal way.

### Documentation

Please see documentation here:

[Tune API SDKs](https://developers.mobileapptracking.com/tune-api-sdks/)

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


<a name="sdk_installation_zip"></a>
#### Via ZIP file:

[Click here to download the source code
(.zip)](https://github.com/MobileAppTracking/tune-api-java/archive/master.zip) for `tune-api-java`.

<a name="sdk_code_samples"></a>
### Code Samples

<a name="sdk_examples"></a>
#### SDK Examples

Run the following script to view execution of all examples:

```bash
    $ make api_key=[API_KEY] examples
```

<a name="sdk_unittests"></a>
#### SDK Unittests

Run the following script to view execution of all unittests:

```bash
    $ make api_key=[API_KEY] tests
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

Report issues using the [Github Issue Tracker](https://github.com/MobileAppTracking/tune-api-java/issues) or Email [sdk@tune.com](mailto:sdk@tune.com).
