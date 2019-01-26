#!/usr/bin/env bash
echo ""> l.log
for i in {1..100}
do
   curl http://clone-streama.1d35.starter-us-east-1.openshiftapps.com/actuator/info >>l.log
   echo "\n" >>l.log
done