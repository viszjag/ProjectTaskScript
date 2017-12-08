<b>Zalando SE</b><br>
<b>Prerequisites to execute Test automation script</b><br>

1) Make sure java is installed on your system.

Also, set environment variables 'path' for Java for windows or linux machine
<b>Follow steps as mentioned in below link:</b><br>
[Setup enviroment path for variables](https://docs.oracle.com/javase/tutorial/essential/environment/paths.html)

2) Install Maven and set environment variable for same.
<b>Follow steps as mentioned in below link:</b><br>
[Install Maven on windows, linux.](http://www.baeldung.com/install-maven-on-windows-linux-mac)

3) Clone repository [https://github.com/viszjag/ZalandoSE.git] in your system to some folder.

4) Opearating system(unix/windows) on which user wants to run automation script and browser(chrome/firefox) can set in property file called config.properties under config folder within the project folder 

5) Open command prompt and navigate to directory path were repository was cloned or contains .git file
<b>Execute following command on command prompt:</b><br>

```bash
mvn clean test
```
This will execute the testscript.

6) Application execution log file will generated within the project folder - Application.log <br>
<b>Alternatively, html report is generated at /ZalandoSE/target/surefire-reports/emailable-report.html</b><br>

7) User can also import project into eclipse or intellij IDE as a maven project.
<b> Make sure you have installed maven plugin inside IDE. </b><br>
<b> Just run pom.xml inside project folder as: i) maven Clean and then ii) maven Test </b><br>

8) Also, can install testng plugin for IDE, and then run testng.xml as a TestNG Suite<br>


[Click here - video for execution steps ](https://www.4shared.com/video/74RZuC3Oei/ExecutionSteps.html)




