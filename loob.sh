#!/usr/bin/env bash
echo ""> l.log
for i in {1..20}
do
#   curl http://104.198.195.171/actuator/info >>l.log
   curl http://35.222.163.99/clone/actuator/info >>l.log
   echo "" >>l.log
done