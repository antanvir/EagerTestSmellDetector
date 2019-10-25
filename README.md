# EagerTestSmellDetector

Author : **A. N. TANVIR**

## "Eager Test Smell Detector tool" using JavaParser API.


I have downloaded JARs from the following sites:
* javaparser-symbol-solver-core(version 3.14.159265359) [ https://jar-download.com/artifacts/com.github.javaparser ]
* javaparser-core(version 3.5.7) [ https://jar-download.com/artifacts/com.github.javaparser/javaparser-core/3.5.7/source-code ]

I have implemented my Java File Extractor & Visitor, Test Method Parser following this ( https://tomassetti.me/getting-started-with-javaparser-analyzing-java-code-programmatically/ ) blog.

And To find out the invoked methods within a test method, this issue ( https://github.com/javaparser/javaparser/issues/490 ) on github was helpful.




## About The Detector


**Eager Test smell** is a case where a test method checks several methods of the tested object.


This tool fetches Java Unit Testing source files from the given directory (& also from sub-directories).Then it detects Eager Test Smells  in those test files. To be specific this tool analyzes every test method from that file, if finds eager test smells prints the method & its line numbers.
  
