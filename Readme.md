# AstroWebService
The AstroWebService is a simple webservice allowing consumers to get planets and cusps positions. The AstroAPI uses Swiss Ephemeris.

### Version: 2.0.0

### License
GNU public version 3

### Documentation
- [Ephemeris API](http://docs.astrologyapi.apiary.io)

### Prerequisites
- Java 8
- Maven 3	
- [astrologyAPI.jar](https://github.com/Kibo/AstroAPI) in file:///${project.basedir}/localMavenRepo

**Deploy astroAPI.jar to Maven local repository**
``` 
mvn deploy:deploy-file -DgroupId=cz.kibo.api -DartifactId=astrologyAPI -Dversion=1.0.0 -Durl=file:./localMavenRepo/ -DrepositoryId=localMavenRepo -DupdateReleaseInfo=true -Dfile=astroAPI-1.0.0.jar
```

### Run
To see the application in action, run the cz.kibo.astrology.service.Bootstrap program using your IDE.

### Install
**jar**
1. mvn clean package -Pjar
2. java -jar target/webservice.jar
(The application will start the embedded Jetty server at http://localhost:8080)

**war**
1. mvn clean package -Pwar
2. Deploy to your own Java container (Jetty, Tomcat, GlassFish, ...)

**OpenShift v3**
1. mvn clean package -Popenshift
2. Deploy to [Red Hat JBoss Web Server](https://access.redhat.com/documentation/en-us/red_hat_jboss_middleware_for_openshift/3/html-single/red_hat_jboss_web_server_for_openshift/)
3. Create [Persisten Volume](https://docs.openshift.com/enterprise/3.0/dev_guide/persistent_volumes.html) for Ephemeris data files.
4. [Copy ephemeris data](https://docs.openshift.com/enterprise/3.1/dev_guide/copy_files_to_container.html) to mounted folder.

``` 
$ oc login ( use Copy Login Command from web )
Create project webservice from web
$ oc project webservice
$ oc new-build --binary=true --name=webservice --image-stream=jboss-webserver30-tomcat8-openshift:1.2
$ oc start-build webservice --from-dir=./target --follow
$ oc new-app webservice
$ oc get svc -o name
$ oc expose svc/webservice
```
```
Create Persisten Volume for Ephemeris data files
Mount: /data

$ oc rsync /data/ephemeris devpod1234:/data
```

### Live demo
- [service](http://webservice2-webservice.7e14.starter-us-west-2.openshiftapps.com/)
