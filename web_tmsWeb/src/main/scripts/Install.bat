@echo off
@echo 调用管理员权限
mode con lines=30 cols=60
%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit

title 网管系统安装
@echo 正在启动网管系统安装服务...
cd /d %~dp0

@echo 设置系统程序的路径...
set CUR_DIR=%cd%
cd %CUR_DIR%\..
set home_dir=%cd%

set my_home=%home_dir%\mysql
set tomcat_home=%home_dir%\tomcat
set FileZila_Home=%CUR_DIR%\FileZillaServer
set SSH_Home=%CUR_DIR%\freeSSHd

@echo 设置Java运行环境...
set JAVA_HOME=%home_dir%\jdk
set CLASSPATH=.;%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\jre\lib\rt.jar;%classpath%
set path=%JAVA_HOME%\bin;%my_home%\bin;%tomcat_home%\bin;%path%

@echo 修改FTP服务器、SFTP服务器以及程序配置文件...
@echo export JAVA_TOOL_OPTIONS='-Dfile.encoding=UTF-8'
call "%CUR_DIR%\tools\InstallWizard.exe" -path %home_dir%
rem 延迟执行其他程序
ping 127.0.0.1 -n 4 > nul

@echo ...添加Mysql数据库服务
cd %my_home%\bin
set myIni=%my_home%\my.ini
call mysqld --install MySQL --defaults-file=%myIni%
net start MySQL

@echo ...添加Tomcat8服务
cd %tomcat_home%\bin
call service install
sc config Tomcat8 start= auto
net start Tomcat8

@echo 安装FTP服务，并开机启动...
cd %FileZila_Home%
"FileZilla Server.exe" /install auto
@echo 启动FTP服务...
"FileZilla server.exe" /start

@echo 安装SFTP服务，并开机启动...
cd %SSH_Home%
FreeSSHDService.exe /service
sc config FreeSSHDService start=auto
net start FreeSSHDService

@echo 添加系统环境变量
setx IPMS_HOME "%home_dir%" -m

@echo 添加防火墙出站和入站规则
netsh advfirewall firewall add rule name="21 ftp in allow" protocol=TCP dir=in localport=21 action=allow
netsh advfirewall firewall add rule name="8021 ftp in allow" protocol=TCP dir=in localport=8021 action=allow
netsh advfirewall firewall add rule name="8022 sftp in allow" protocol=TCP dir=in localport=8022 action=allow
netsh advfirewall firewall add rule name="8990 ftps in allow" protocol=TCP dir=in localport=8990 action=allow
netsh advfirewall firewall add rule name="80 http allow" protocol=TCP dir=in localport=80 action=allow
netsh advfirewall firewall add rule name="8009 AJP allow" protocol=TCP dir=in localport=8009 action=allow
netsh advfirewall firewall add rule name="8080 http allow" protocol=TCP dir=in localport=8080 action=allow
netsh advfirewall firewall add rule name="8443 https allow" protocol=TCP dir=in localport=8443 action=allow
netsh advfirewall firewall add rule name="9108 TCP-Comm allow" protocol=TCP dir=in localport=9108 action=allow
netsh advfirewall firewall add rule name="9400-9410 ftp pasv in allow" protocol=TCP dir=in localport=9400-9410 action=allow
netsh advfirewall firewall add rule name="5071 Udp-FDD allow" protocol=UDP dir=in localport=5071 action=allow
netsh advfirewall firewall add rule name="9202 Udp-LTE or OMT allow" protocol=UDP dir=in localport=9202 action=allow
netsh advfirewall firewall add rule name="9102 Udp-Comm or OMT allow" protocol=UDP dir=in localport=9102 action=allow
netsh advfirewall firewall add rule name="21 ftp out allow" protocol=TCP dir=out localport=21 action=allow
netsh advfirewall firewall add rule name="8021 ftp out allow" protocol=TCP dir=out localport=8021 action=allow
netsh advfirewall firewall add rule name="9107 TCP out allow" protocol=TCP dir=out localport=9107 action=allow
netsh advfirewall firewall add rule name="5070 Udp-FDD out allow" protocol=UDP dir=out localport=5070 action=allow
netsh advfirewall firewall add rule name="9101 Udp-Comm or OMT out allow" protocol=UDP dir=out localport=9101 action=allow
netsh advfirewall firewall add rule name="9201 Udp-LTE or OMT out allow" protocol=UDP dir=out localport=9201 action=allow

cd %home_dir%
@echo 程序安装完成，正常退出...


