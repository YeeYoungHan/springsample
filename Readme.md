# 스프링 샘플 프로젝트
스프링 프레임워크 기반 샘플 프로그램 개발 프로젝트입니다.

### 개요
본 프로젝트의 목표는 다음과 같습니다.

* 스프링 프레임워크 기반 게시판 개발
* 마이바티스 테스트
* 스프링 프레임워크 테스트

### 개발자 정보
본 프로젝트를 진행하는 개발자 정보는 다음과 같습니다.

* 이메일: websearch@naver.com
* 블로그: http://blog.naver.com/websearch

### MySQL 설정 방법

* MySQL 서버가 설치되어 있지 않다면 MySQL 서버를 설치한다.
* SpringNoticeBoard/sql/CreateTable.sql 을 이용하여서 spring 데이터베이스 및 spring 테이블을 생성한다.
* spring 데이터베이스에 접근할 수 있는 spring 계정을 생성한다.
  * spring 계정의 비밀번호를 spring 으로 설정한다.
  * SpringNoticeBoard/src/main/webapp/WEB-INF/spring/root-context.xml 파일에 데어터베이스 접속 정보를 수정한다.

### 폴더 설명

* SpringNoticeBoard
  * 스프링 프레임워크 기반 게시판 개발 프로젝트

* TestMyBatis
  * MyBatis 테스트 프로젝트

* TestSpring
  * 스프링 프레임워크 테스트

* TestSpringMvc
  * MVC 기반 스프링 웹 프로젝트 테스

* TestSpringWeb
  * Simple Spring Web Maven 프로젝트 테스트


