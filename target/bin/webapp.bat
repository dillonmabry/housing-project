@REM ----------------------------------------------------------------------------
@REM Copyright 2001-2004 The Apache Software Foundation.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM ----------------------------------------------------------------------------
@REM

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-core\8.0.1\tomcat-embed-core-8.0.1.jar;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-logging-juli\8.0.1\tomcat-embed-logging-juli-8.0.1.jar;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-jasper\8.0.1\tomcat-embed-jasper-8.0.1.jar;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-el\8.0.1\tomcat-embed-el-8.0.1.jar;"%REPO%"\org\eclipse\jdt\core\compiler\ecj\4.3.1\ecj-4.3.1.jar;"%REPO%"\org\apache\tomcat\tomcat-jasper\8.0.1\tomcat-jasper-8.0.1.jar;"%REPO%"\org\apache\tomcat\tomcat-servlet-api\8.0.1\tomcat-servlet-api-8.0.1.jar;"%REPO%"\org\apache\tomcat\tomcat-juli\8.0.1\tomcat-juli-8.0.1.jar;"%REPO%"\org\apache\tomcat\tomcat-jsp-api\8.0.1\tomcat-jsp-api-8.0.1.jar;"%REPO%"\org\apache\tomcat\tomcat-el-api\8.0.1\tomcat-el-api-8.0.1.jar;"%REPO%"\org\apache\tomcat\tomcat-jasper-el\8.0.1\tomcat-jasper-el-8.0.1.jar;"%REPO%"\org\apache\tomcat\tomcat-api\8.0.1\tomcat-api-8.0.1.jar;"%REPO%"\org\apache\tomcat\tomcat-util-scan\8.0.1\tomcat-util-scan-8.0.1.jar;"%REPO%"\org\apache\tomcat\tomcat-util\8.0.1\tomcat-util-8.0.1.jar;"%REPO%"\org\apache\maven\maven-core\3.2.5\maven-core-3.2.5.jar;"%REPO%"\org\apache\maven\maven-model\3.2.5\maven-model-3.2.5.jar;"%REPO%"\org\codehaus\plexus\plexus-utils\3.0.20\plexus-utils-3.0.20.jar;"%REPO%"\org\apache\maven\maven-settings\3.2.5\maven-settings-3.2.5.jar;"%REPO%"\org\apache\maven\maven-settings-builder\3.2.5\maven-settings-builder-3.2.5.jar;"%REPO%"\org\codehaus\plexus\plexus-interpolation\1.21\plexus-interpolation-1.21.jar;"%REPO%"\org\codehaus\plexus\plexus-component-annotations\1.5.5\plexus-component-annotations-1.5.5.jar;"%REPO%"\org\sonatype\plexus\plexus-sec-dispatcher\1.3\plexus-sec-dispatcher-1.3.jar;"%REPO%"\org\sonatype\plexus\plexus-cipher\1.4\plexus-cipher-1.4.jar;"%REPO%"\org\apache\maven\maven-repository-metadata\3.2.5\maven-repository-metadata-3.2.5.jar;"%REPO%"\org\apache\maven\maven-artifact\3.2.5\maven-artifact-3.2.5.jar;"%REPO%"\org\apache\maven\maven-plugin-api\3.2.5\maven-plugin-api-3.2.5.jar;"%REPO%"\org\eclipse\sisu\org.eclipse.sisu.plexus\0.3.0.M1\org.eclipse.sisu.plexus-0.3.0.M1.jar;"%REPO%"\javax\enterprise\cdi-api\1.0\cdi-api-1.0.jar;"%REPO%"\javax\annotation\jsr250-api\1.0\jsr250-api-1.0.jar;"%REPO%"\javax\inject\javax.inject\1\javax.inject-1.jar;"%REPO%"\org\eclipse\sisu\org.eclipse.sisu.inject\0.3.0.M1\org.eclipse.sisu.inject-0.3.0.M1.jar;"%REPO%"\org\codehaus\plexus\plexus-classworlds\2.5.2\plexus-classworlds-2.5.2.jar;"%REPO%"\org\apache\maven\maven-model-builder\3.2.5\maven-model-builder-3.2.5.jar;"%REPO%"\org\apache\maven\maven-aether-provider\3.2.5\maven-aether-provider-3.2.5.jar;"%REPO%"\org\eclipse\aether\aether-api\1.0.0.v20140518\aether-api-1.0.0.v20140518.jar;"%REPO%"\org\eclipse\aether\aether-spi\1.0.0.v20140518\aether-spi-1.0.0.v20140518.jar;"%REPO%"\org\eclipse\aether\aether-util\1.0.0.v20140518\aether-util-1.0.0.v20140518.jar;"%REPO%"\org\eclipse\aether\aether-impl\1.0.0.v20140518\aether-impl-1.0.0.v20140518.jar;"%REPO%"\org\sonatype\sisu\sisu-guice\3.2.3\sisu-guice-3.2.3-no_aop.jar;"%REPO%"\aopalliance\aopalliance\1.0\aopalliance-1.0.jar;"%REPO%"\com\google\guava\guava\16.0.1\guava-16.0.1.jar;"%REPO%"\org\json\json\20160810\json-20160810.jar;"%REPO%"\org\jsoup\jsoup\1.8.3\jsoup-1.8.3.jar;"%REPO%"\com\example\housing-project\1.0.0\housing-project-1.0.0.jar
set EXTRA_JVM_ARGUMENTS=
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS% %EXTRA_JVM_ARGUMENTS% -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="webapp" -Dapp.repo="%REPO%" -Dbasedir="%BASEDIR%" launch.Main %CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=1

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@endlocal

:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
