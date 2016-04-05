A CMIS server that exposes a filesystem directory.

Same as http://chemistry.apache.org/opencmis-fileshare-repository.html so all of the documentation applies. The only difference is that FileShare-no-metadata does not generate nor use any metadata files (no `.cmis.xml` nor `cmis.xml` files). Please report bugs at https://github.com/nicolas-raoul/FileShare-no-metadata/issues thank you!

# Usage

- Download the latest `fileshare-no-metadata.war` from the [Releases section](https://github.com/nicolas-raoul/FileShare-no-metadata/releases).
- Copy it to your application server (ex: Tomcat's `webapps` folder)

# How to build yourself

- Install maven
- Run `mvn install -Dmaven.test.skip=true`
- The generated war file is `chemistry-opencmis-server/chemistry-opencmis-server-fileshare/target/chemistry-opencmis-server-fileshare-1.0.0-SNAPSHOT.war`
 
# Notes

- Not intended for production use
- Does not support chunked uploads
