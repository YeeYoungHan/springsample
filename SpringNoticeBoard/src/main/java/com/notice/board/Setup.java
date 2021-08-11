package com.notice.board;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/properties/env.properties")
public class Setup
{
	@Inject
	Environment m_clsEnv;
	
	public String Get( String strName )
	{
		return m_clsEnv.getProperty( strName );
	}
}
