/* 
 * Copyright (C) 2018 Yee Young Han <websearch@naver.com> (http://blog.naver.com/websearch)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 */

package com.notice.board;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/** 설정 파일을 읽는 bean 클래스
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 */
@Configuration
@PropertySource("classpath:/properties/env.properties")
public class Setup
{
	@Inject
	Environment m_clsEnv;
	
	@Value("${TEST}")
	String m_strTest;
	
	@Value("${INT_TEST}")
	int m_iIntTest;
	
	@Value("${BOOL_TEST}")
	boolean m_bBoolTest;
	
	@Value("${BOOL_TEST2}")
	boolean m_bBoolTest2;
	
	// 설정파일에 존재하지 않는 값에 대한 기본값 설정 테스트
	@Value("${NULL:}")
	String m_strNull;
	
	@Value("${NULL:-1}")
	int m_iIntNull;
	
	@Value("${NULL:false}")
	boolean m_bBoolNull;
	
	public String Get( String strName )
	{
		return m_clsEnv.getProperty( strName );
	}
	
	@Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
	{
		return new PropertySourcesPlaceholderConfigurer();
  }
}
