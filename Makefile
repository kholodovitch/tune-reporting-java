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
# version   $Date: 2014-11-21 14:12:20 $
# link      https://developers.mobileapptracking.com
#

.PHONY: clean build dist examples tests docs-doxygen docs-javadoc 

clean:
	sudo rm -fR ./docs/javadoc/*
	sudo rm -fR ./docs/doxygen/*
	sudo rm -fR ./junit/*
	ant clean

build:
	ant build

dist:
	ant dist

examples:
	ant example -DAPI_KEY=$(api_key)

tests:
	ant test -DAPI_KEY=$(api_key)

docs-doxygen:
	sudo rm -fR ./docs/doxygen/*
	sudo doxygen ./docs/Doxyfile
	x-www-browser docs/doxygen/html/index.html

docs-javadoc:
	sudo rm -fR ./docs/javadoc/*
	ant docs-javadoc
	x-www-browser docs/javadoc/index.html