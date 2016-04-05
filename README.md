A CMIS server that exposes a filesystem directory.

Same as http://chemistry.apache.org/opencmis-fileshare-repository.html so all of the documentation applies. The only difference is that FileShare-no-metadata does not generate nor use any metadata files (no `.cmis.xml` nor `cmis.xml` files). Please report bugs at https://github.com/nicolas-raoul/FileShare-no-metadata/issues thank you!

Usage:

- Install maven
- Run `mvn install -Dmaven.test.skip=true`
- Copy the generated `chemistry-opencmis-server/chemistry-opencmis-server-fileshare/target/chemistry-opencmis-server-fileshare-1.0.0-SNAPSHOT.war` to your application server (ex: Tomcat's `webapps` folder)
