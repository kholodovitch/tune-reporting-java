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
# version   $Date: 2014-11-24 13:38:13 $
# link      https://developers.mobileapptracking.com
#

.PHONY: ant-clean ant-build ant-examples ant-tests maven-clean maven-tests maven-package maven-gpg-sign maven-deploy docs-doxygen docs-javadoc

ant-clean:
	sudo rm -fR ./docs/javadoc/*
	sudo rm -fR ./docs/doxygen/*
	sudo rm -fR ./junit/*
	ant clean

ant-build:
	ant build

ant-examples:
	ant example -DAPI_KEY=$(api_key)

ant-tests:
	ant test -DAPI_KEY=$(api_key)

maven-gpg-sign:
	find src/ -name \*.asc -exec rm {} \;
	find src/ -name \*.java -exec gpg --passphrase '$(passphrase)' -ab {} \;
	find src/ -name \*.asc -exec gpg --verify {} \;

maven-clean:
	mvn clean

maven-tests:
	mvn test -DAPI_KEY=$(api_key)

maven-package:
	mvn package -DAPI_KEY=$(api_key)

maven-deploy:
	mvn clean deploy -e -Dgpg.passphrase=$(passphrase) -DAPI_KEY=$(api_key)

docs-doxygen:
	sudo rm -fR ./docs/doxygen/*
	sudo doxygen ./docs/Doxyfile
	x-www-browser docs/doxygen/html/index.html

docs-javadoc:
	sudo rm -fR ./docs/javadoc/*
	ant docs-javadoc
	x-www-browser docs/javadoc/index.html