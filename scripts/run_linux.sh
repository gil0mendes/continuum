#!/bin/bash
cd `dirname $0`
java -Xms128m -Xmx512m -classpath *:lib/*:lib/lwjgl/jar/* -Djava.library.path=lib/lwjgl/native/linux com.continuum.Main

