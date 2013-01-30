/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.webservice;

import org.mule.api.processor.MessageProcessor;
import org.mule.webservice.api.WebServiceInterface;

public abstract class AsbtarctWebServiceOperationRouter implements MessageProcessor
{
    protected WebServiceInterface webServiceInterface;

    public AsbtarctWebServiceOperationRouter(WebServiceInterface webServiceInterface)
    {
        this.webServiceInterface = webServiceInterface;
    }

}