PDFDoclet README
================
Copyright (c) 2005 by Marcel Schoen 
Published under the Library GNU Public License (LGPL)


1. Introduction
2. Directory Overview
3. Prerequisites
4. Usage
5. Credits


1. Introduction
---------------
This is a javadoc doclet which creates PDF output for a given 
Java API, using another opensource library, "iText" (see 7). It 
requires nothing more than two JAR files (its own one and 
the one from iText) in order to create PDF files. Forget now  
about all the fuss of having to create MIF output first, then 
having to load it into a FrameMaker installation using some preinstalled 
batch application and finally needing Acrobat Distiller to create 
a PDF...

PDFDoclet is a straightforward, simple-to-use 100% pure Java solution. 
Kudos to the authors of iText for making it possible!


2. Directory Overview
---------------------

/README.txt ......... This file
/LICENSE.txt ........ PDFDoclet license
/TODO.txt ........... Known issues and bugs
/FAQ.txt ............ Some answers to Frequently Asked Questions
/RELEASE.txt ........ Release change notes
/build.xml .......... Demonstrates usage of PDFDoclet with ANT
/example.sh ......... Demonstrates usage of PDFDoclet with a script
/docs/ .............. HTML documentation (usage, configuration etc.)
/jar/ ......,........ All required libraries
/example/html ....... Example source tree for creating example PDF
/example/laby ....... Example source tree for creating example PDF
/example/test ....... Example source tree for creating example PDF
/example/results .... (created) Contains created PDF files


3. Prerequisites
----------------
PDFDoclet requires Java 1.4 because it uses functionality of the
doclet API available only in 1.4. It will not work with Java
versions older than 1.4.


4. Usage
--------
You can find examples for how to run the PDFDoclet with 
ANT in the file "build.xml". There is also an example 
shell-script "example.sh" which shows how to call the 
doclet directly with javadoc (just set the JAVA_HOME
environment variable correctly in the script).

The configuration properties / parameter details can be found
in the HTML documentation in the "docs" directory.


5. Credits
----------

Website Location:

  http://pdfdoclet.sourceforge.net


PDFDoclet written by:

  Marcel Schoen, Switzerland
  marcelschoen@yahoo.com


Additional code, fixes and improvements by:

  Fabrice Inconnu

  Xavier Gost

  Mark Bryan Yu
  
  Frank Baxter

  David Beutel
  jdb@getsu.com

  Lars Heller
  l.heller@web.de

  Ryan C. Brase


  and many other users and testers.
  
  
(If your name is missing here - sorry. 
 Just remind me to add it!)


iText PDF creation library written by:

  Bruno Iowagie
  http://www.lowagie.com/bruno

