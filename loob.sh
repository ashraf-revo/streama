#!/usr/bin/env bash
echo ""> l.log
for i in {1..20}
do
   curl http://127.0.0.1:8001/api/v1/namespaces/default/services/gateway/proxy/clone/actuator/health >>l.log
   echo "" >>l.log
done