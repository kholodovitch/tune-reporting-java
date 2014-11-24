package com.tune.sdk.shared;

/**
 * TuneServiceException.java
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
 * @package   com.tune.sdk.shared
 * @author    Jeff Tanner <jefft@tune.com>
 * @copyright 2014 Tune (http://www.tune.com)
 * @license   http://opensource.org/licenses/MIT The MIT License (MIT)
 * @version   $Date: 2014-11-21 17:34:43 $
 * @link      https://developers.mobileapptracking.com @endlink
 *
 */

/**
 * The Class TuneServiceException.
 */
public class TuneServiceException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3461168669128080352L;

    /** The message. */
    private String message = null;

    /**
     * Instantiates a new Tune sdk exception.
     */
    public TuneServiceException() {
        super();
    }

    /**
     * Instantiates a new Tune sdk exception.
     *
     * @param message the message
     */
    public TuneServiceException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Instantiates a new Tune sdk exception.
     *
     * @param cause the cause
     */
    public TuneServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Tune sdk exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public TuneServiceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String toString() {
        return message;
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        return message;
    }
}
