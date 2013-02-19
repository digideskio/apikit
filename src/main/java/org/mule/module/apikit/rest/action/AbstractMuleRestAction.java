
package org.mule.module.apikit.rest.action;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.module.apikit.AbstractWebServiceOperation;
import org.mule.module.apikit.rest.Representation;
import org.mule.module.apikit.rest.RestException;
import org.mule.module.apikit.rest.RestRequest;
import org.mule.module.apikit.rest.protocol.MediaTypeNotAcceptableException;
import org.mule.module.apikit.rest.util.RestContentTypeParser;

import java.util.Collection;
import java.util.Collections;

public abstract class AbstractMuleRestAction extends AbstractWebServiceOperation implements MuleRestAction
{

    protected ActionType type;
    protected Representation representation;

    @Override
    public ActionType getType()
    {
        return type;
    }

    @Override
    public MuleEvent handle(RestRequest request) throws RestException
    {
        if (getRepresentation() != null)
        {
            Collection<Representation> representations = Collections.singletonList(getRepresentation());
            String bestMatch = RestContentTypeParser.bestMatch(representations, request.getProtocolAdaptor().getAcceptedContentTypes());
            if (bestMatch == null)
            {
                throw new MediaTypeNotAcceptableException();
            }
        }
        try
        {
            return getHandler().process(request.getMuleEvent());
        }
        catch (MuleException e)
        {
            throw new RestException();
        }
    }

    public Representation getRepresentation()
    {
        return representation;
    }

}