#!/bin/sh

# Simple example shell script which demonstrates
# how to use the PDFDoclet with javadoc directly
# (which means: without ANT).

# Set the JAVA_HOME variable correctly !!
JAVA_HOME=/usr/local/java/j2se

PATH=$JAVA_HOME/bin

VERSION=1.0.2

DOCLET=com.tarsec.javadoc.pdfdoclet.PDFDoclet
JARS=jar/pdfdoclet-$VERSION-all.jar
PACKAGES="cvu.html cvu.html.test"

export JAVA_HOME PATH DOCLET JARS PACKAGES

javadoc -doclet $DOCLET -docletpath $JARS -pdf example/results/html.pdf -config example/html/config_html.properties -private -sourcepath example/html $PACKAGES
