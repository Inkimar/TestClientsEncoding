This project is only to test **base64-encoding** for large files , 6GB <= file <=500MB
There is always a crash in the base64-line itself, either 'NegativeArraySizeException' or 
'java-lang-outofmemoryerror-java-heap-space-error' 

When we are able to base64 files up to 3GB we can 'start the mediaserver'

in linux , the testfile is created like this .. and soforth

``` > fallocate -l 1000mb /tmp/1000mb.zip```

Setting the main-java file in the pom.xml in the 'org.apache.maven.plugins'

**Testing the Guava-encoding , Steps : 1 to 6**

(1) 500MB
mvn clean package
java -Xms512m -Xmx12G -jar target/mediaClient.jar 

Result
Encoding = OK

Posting to Mediaserver = OK

(2) 1000MB

mvn clean package
java -Xms512m -Xmx12G -jar target/mediaClient.jar 

String metadataFormatted = StringEscapeUtils.unescapeJavaScript(metadata.toString());
-> import org.apache.commons.lang.StringEscapeUtils; (using version 2.6)

here -> OutOfMemoryError


RESULT= 


(3) 1500MB

(4) 2000MB

(5) 2500MB

(6) 3000MB

