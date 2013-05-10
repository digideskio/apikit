/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.apikit.rest;

import org.mule.module.apikit.rest.resource.RestResource;

import com.google.common.net.MediaType;

import javax.print.attribute.standard.Media;

public interface RestDocumentationStrategy
{

    MediaType getDocumentationMediaType();
    
    Object documentResource(RestResource resource, RestRequest request);
    
}


