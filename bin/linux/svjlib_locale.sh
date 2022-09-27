#!/bin/bash
# Start WEdit. Use classpath inside JAR. Delete all '0D' !!!
#echo "-------------------"
echo java = $JAVA_HOME
BASEDIR=`pwd`
export MODULE_HOME=`cd $BASEDIR/.. ; pwd`
#export MODULE_HOME=/home/svj/programm/my/svjlib
#export JAVA_HOME=/usr/java/jdk1.6.0_05
echo module.home = $MODULE_HOME
exec $JAVA_HOME/bin/java -Dmodule.home=$MODULE_HOME -Dfile.encoding=UTF-8 -Dlog4j.configurationFile=$MODULE_HOME/conf/logs.xml -jar $MODULE_HOME/lib/svjlib.jar &
#CMD=$JAVA_HOME/bin/java -Dmodule.home=$MODULE_HOME -Dfile.encoding=UTF-8 -Dlog4j.configurationFile=$MODULE_HOME/conf/logs.xml -jar $MODULE_HOME/lib/svjlib.jar
#echo $CMD
#`$CMD &`
echo "OK"
