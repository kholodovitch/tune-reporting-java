#   Makefile
#
#   Copyright (c) 2014 Tune, Inc
#   All rights reserved.
#
#   Permission is hereby granted, free of charge, to any person obtaining a copy
#   of this software and associated documentation files (the "Software"), to deal
#   in the Software without restriction, including without limitation the rights
#   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
#   copies of the Software, and to permit persons to whom the Software is
#   furnished to do so, subject to the following conditions:
#
#   The above copyright notice and this permission notice shall be included in
#   all copies or substantial portions of the Software.
#
#   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
#   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
#   FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
#   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
#   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
#   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
#   THE SOFTWARE.
#
# category  Tune
# package   tune.tests
# author    Jeff Tanner <jefft@tune.com>
# copyright 2014 Tune (http://www.tune.com)
# license   http://opensource.org/licenses/MIT The MIT License (MIT)
# version   $Date: 2015-03-05 23:03:20 $
# link      https://developers.mobileapptracking.com
#

.PHONY: lint ant-clean ant-lint ant-build ant-examples ant-tests mvn-clean mvn-lint mvn-tests mvn-package mvn-gpg-sign mvn-deploy mvn-release docs-doxygen docs-javadoc

# ANT

ant-clean:
	find src/ -name \*.asc -exec rm {} \;
	sudo rm -fR ./docs/javadoc/*
	sudo rm -fR ./docs/doxygen/*
	sudo rm -fR ./junit/*
	sudo rm -fR ./files/*
	sudo rm -fR ./build/*
	if [ -f overview-frame.html ]; then sudo rm overview-frame.html; fi
	ant clean

ant-lint:
	ant checkstyle

ant-build:
	ant build

ant-example:
	ant example -DAPI_KEY=$(api_key)

ant-test:
	ant test -DAPI_KEY=$(api_key)

# Maven sign java classes #1
mvn-gpg-sign:
	find src/ -name \*.asc -exec rm {} \;
	find src/ -name \*.java -exec gpg --passphrase '$(passphrase)' -ab {} \;
	find src/ -name \*.asc -exec gpg --verify {} \;

# Requires uncommentting 'mvn-checkstyle-plugin' in pom.xml
mvn-lint:
	mvn site

mvn-clean:
	sudo rm -fR ./build/*
	find src/ -name \*.asc -exec rm {} \;
	mvn clean

mvn-test:
	mvn test -DAPI_KEY=$(api_key)

mvn-package:
	mvn package -DAPI_KEY=$(api_key)

# Maven deploy #2
mvn-deploy:
	find src/ -name \*.asc -exec rm {} \;
	find src/ -name \*.java -exec gpg --passphrase '$(passphrase)' -ab {} \;
	find src/ -name \*.asc -exec gpg --verify {} \;
	mvn clean deploy -e -DperformRelease=true -Dgpg.passphrase=$(passphrase) -DAPI_KEY=$(api_key) -Dgpg.keyname=$(keyname)

# Maven release #3
mvn-release:
	mvn -e nexus-staging:release

docs-doxygen:
	sudo rm -fR ./docs/doxygen/*
	sudo doxygen ./docs/Doxyfile
	x-www-browser docs/doxygen/html/index.html

docs-javadoc:
	sudo rm -fR ./docs/javadoc/*
	ant docs-javadoc
	x-www-browser docs/javadoc/index.html