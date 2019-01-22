@echo off
@echo 调用管理员权限
mode con lines=30 cols=60
%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit

title 网管系统卸载
@echo 正在启动网管系统卸载服务...
cd /d %~dp0

@echo 设置基本环境信息
set CUR_DIR=%cd%
cd %CUR_DIR%\..
set home_dir=%cd%
set my_home=%home_dir%\mysql
set tomcat_home=%home_dir%\tomcat
set FileZilla_Home=%CUR_DIR%\FileZillaServer
set SSH_Home=%CUR_DIR%\freeSSHd


@echo 设置Java运行环境...
set JAVA_HOME=%home_dir%\jdk
set classpath=.;%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\jre\lib\rt.jar;%classpath%
set path=%JAVA_HOME%\bin;%my_home%\bin;%tomcat_home%;%path%

@echo ...卸载Mysql服务项...
net stop MySQL
call mysqld --remove MySQL

@echo ...卸载tomcat8服务项...
taskkill /f /im Tomcat8.exe
cd %tomcat_home%\bin
call service remove Tomcat8
cd %home_dir%

@echo 卸载FTP服务项...
cd %FileZilla_Home%
"FileZilla server.exe" /stop
rem "FileZilla server.exe" /uninstall
sc delete "FileZilla Server"

@echo 卸载SFTP服务项...
cd %SSH_Home%
net stop FreeSSHDService
sc delete FreeSSHDService
cd %home_dir%

@echo 删除系统安装目录的环境变量，并重启桌面立即生效
set bin_home="%IPMS_HOME%\bin"
Reg Delete "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v "IPMS_HOME" /f 2>nul

@echo 删除防火墙入站和出站规则
netsh advfirewall firewall delete rule name="21 ftp in allow" protocol=TCP dir=in localport=21
netsh advfirewall firewall delete rule name="8021 ftp in allow" protocol=TCP dir=in localport=8021
netsh advfirewall firewall delete rule name="8022 sftp in allow" protocol=TCP dir=in localport=8022
netsh advfirewall firewall delete rule name="8990 ftps in allow" protocol=TCP dir=in localport=8990
netsh advfirewall firewall delete rule name="80 http allow" protocol=TCP dir=in localport=80
netsh advfirewall firewall delete rule name="8009 AJP allow" protocol=TCP dir=in localport=8009
netsh advfirewall firewall delete rule name="8080 http allow" protocol=TCP dir=in localport=8080
netsh advfirewall firewall delete rule name="8443 https allow" protocol=TCP dir=in localport=8443
netsh advfirewall firewall delete rule name="9108 TCP-Comm allow" protocol=TCP dir=in localport=9108
netsh advfirewall firewall delete rule name="9400-9410 ftp pasv in allow" protocol=TCP dir=in localport=9400-9410
netsh advfirewall firewall delete rule name="5071 Udp-FDD allow" protocol=UDP dir=in localport=5071
netsh advfirewall firewall delete rule name="9202 Udp-LTE or OMT allow" protocol=UDP dir=in localport=9202
netsh advfirewall firewall delete rule name="9102 Udp-Comm or OMT allow" protocol=UDP dir=in localport=9102
netsh advfirewall firewall delete rule name="21 ftp out allow" protocol=TCP dir=out localport=21
netsh advfirewall firewall delete rule name="8021 ftp out allow" protocol=TCP dir=out localport=8021
netsh advfirewall firewall delete rule name="9107 TCP out allow" protocol=TCP dir=out localport=9107
netsh advfirewall firewall delete rule name="5070 Udp-FDD out allow" protocol=UDP dir=out localport=5070
netsh advfirewall firewall delete rule name="9101 Udp-Comm or OMT out allow" protocol=UDP dir=out localport=9101
netsh advfirewall firewall delete rule name="9201 Udp-LTE or OMT out allow" protocol=UDP dir=out localport=9201

@echo 重启桌面
rem taskkill /f /im explorer.exe&&start explorer.exe&&explorer.exe %bin_home%

@echo ..................................................
@echo ...............应用程序完成卸载...................
@echo ..................................................
