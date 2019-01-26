#!/usr/bin/env bash
echo ""> l.log
for i in {1..10}
do
   curl http://gateway-streama.1d35.starter-us-east-1.openshiftapps.com/clone/actuator/info >>l.log
   echo "" >>l.log
done