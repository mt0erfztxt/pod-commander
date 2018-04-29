FROM anapsix/alpine-java:8

COPY command.sh /command.sh
COPY demouser.crt /demouser.crt
COPY demouser.key /demouser.key
COPY pod-commander-demo.jar /pod-commander-demo.jar

CMD ["/bin/bash","-c","while true; do echo c01 `date '+%Y-%m-%d %H:%M:%S'`; sleep 60; done"]
