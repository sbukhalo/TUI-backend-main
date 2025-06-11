#!/bin/bash
mongoimport --host 127.0.0.1 --db challenge --collection quotes --type json --file /docker-entrypoint-initdb.d/data.json --jsonArray

