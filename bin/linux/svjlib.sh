#!/bin/bash
# Start WEdit. Use classpath inside JAR. Delete all '0D' !!!
BASEDIR=`pwd`
export MODULE_HOME=`cd $BASEDIR/.. ; pwd`
exec java -Dmodule.home=$MODULE_HOME -Dfile.encoding=UTF-8 -Dlog4j.configurationFile=$MODULE_HOME/conf/logs.xml -jar $MODULE_HOME/lib/svjlib.jar &
#echo "OK"
