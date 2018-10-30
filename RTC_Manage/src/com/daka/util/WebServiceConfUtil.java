package com.daka.util;

import org.apache.log4j.Logger;

public class WebServiceConfUtil extends AbstractConfigurationUtil
{

    private static final Logger logger = Logger.getLogger(WebServiceConfUtil.class);

    private static WebServiceConfUtil instance = new WebServiceConfUtil();

    private WebServiceConfUtil()
    {
    }

    public static WebServiceConfUtil getInstance()
    {
        return instance;
    }

    @Override
    protected String getConfurationFilePath()
    {
        return "/resources/webservice.properties";
    }

    @Override
    protected Logger getLogger()
    {
        return logger;
    }

}
