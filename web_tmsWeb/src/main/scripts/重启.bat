@echo ���ù���ԱȨ��
mode con lines=30 cols=60
%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit

taskkill /f /im Tomcat8.exe
net start Tomcat8