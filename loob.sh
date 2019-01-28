#!/usr/bin/env bash
echo ""> l.log
for i in {1..20}
do
   curl http://35.226.36.84/clone/actuator/info >>l.log
#   curl http://35.188.1.114/actuator/health >>l.log
   echo "" >>l.log
done