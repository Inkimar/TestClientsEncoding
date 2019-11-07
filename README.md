This project is only to test **base64-encoding** for large files , 6GB <= file <=500MB
There is always a crash in the base64-line itself, either 'NegativeArraySizeException' or 
'java-lang-outofmemoryerror-java-heap-space-error' 

When we are able to base64 files up to 3GB we can 'start the mediaserver'

in linux , the testfile is created like this .. and soforth

``` > fallocate -l 500mb /tmp/500mb.zip```

``` > fallocate -l 1000mb /tmp/1000mb.zip```

Setting the main-java file in the pom.xml in the 'org.apache.maven.plugins'

**Testing the Guava-encoding , Steps : 1 to 6**

(1) 500MB

1. mvn clean package
2. java -Xms512m **-Xmx12G** -jar target/mediaClient.jar 

Result
Encoding = OK

Posting to Mediaserver = OK

(2) 1000MB

1. mvn clean package
2. java -Xms512m **-Xmx12G** -jar target/mediaClient.jar /tmp/1000mb.zip

String metadataFormatted = StringEscapeUtils.unescapeJavaScript(metadata.toString()); <p>
-> import org.apache.commons.text.StringEscapeUtils; (upgrade : using  the latest version of 'commons-text', version 1.8)

StringEntity : https://stackoverflow.com/questions/21022174/json-string-too-large-for-stringentity


here -> OutOfMemoryError

action = increase heap

1. mvn clean package
2. java -Xms512m **-Xmx16G** -jar target/mediaClient.jar /tmp/1000mb.zip

error:

```
Exception in thread "main" java.lang.IllegalArgumentException: Negative buffer size
        at java.io.StringWriter.<init>(StringWriter.java:67)
        at org.apache.commons.text.translate.CharSequenceTranslator.translate(CharSequenceTranslator.java:67)
        at org.apache.commons.text.StringEscapeUtils.unescapeEcmaScript(StringEscapeUtils.java:613)
        at se.nrm.bio.mediaserver.testing.base64.NewClientEncodeGuavaPost.posting(NewClientEncodeGuavaPost.java:74)
        at se.nrm.bio.mediaserver.testing.base64.NewClientEncodeGuavaPost.main(NewClientEncodeGuavaPost.java:48)
```


RESULT= 


(3) 1500MB

(4) 2000MB

(5) 2500MB

(6) 3000MB

