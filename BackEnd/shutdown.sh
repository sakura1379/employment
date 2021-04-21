#!/bin/bash
#PORT=$(netstat -ntulp | grep 8290)
PORT=$(netstat -tnl | grep 8290)
if [[ -n $PORT  ]]; then
        echo "ready to close..."
        RESULT="000"
        while [[ $RESULT == "000" ]]; do
                echo "closing down..."
                RESULT=$(/usr/bin/curl -X POST 127.0.0.1:12583/MyActuator/shutdown)
                sleep 2
        done
        echo "close off success"
fi