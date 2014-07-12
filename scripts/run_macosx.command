#!/bin/bash
java -Xms128m -Xmx512m -classpath *:./test/*:lib/*:lib/lwjgl/jar/* -Djava.library.path=lib/lwjgl/native/macosx com.continuum.Main

