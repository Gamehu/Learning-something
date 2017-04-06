#!/bin/bash
JAVA_HOME=/application/jdk
#export JAVA_HOME=/usr/local/java/jdk1.8.0_60              ###jdk
java=$JAVA_HOME/bin/java
LJ=/application/smsTrans/lib/smsTrans.jar

#JAVA_OPTS=$JAVA_OPTS:"-Xms1024m -Xmx1024m"  
#export JAVA_OPTS  


java -jar $LJ  &
