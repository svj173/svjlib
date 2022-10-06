#!/bin/bash
# Start WEdit6. Use classpath inside JAR. Delete all '0D' !!!
#echo java = $JAVA_HOME

export MODULE_HOME=/usr/lib/wedit6
#echo module.home = $MODULE_HOME

exec $JAVA_HOME/bin/java -Dmodule.home=$MODULE_HOME -Dfile.encoding=UTF-8 -Dlog4j.configurationFile=$MODULE_HOME/conf/logs.xml -jar $MODULE_HOME/lib/svjlib.jar &
echo "OK"
