#!/bin/bash
#get run mem 

wget http://192.168.29.233:8000/SM4.java
wget http://192.168.29.233:8000/SM4_1.java
wget http://192.168.29.233:8000/SM4_2.java
wget http://192.168.29.233:8000/SM4_3.java
wget http://192.168.29.233:8000/SM4_4.java

javac SM4.java
java -Xms1536M -Xmx1536M -XX:+PrintGC -verbose:gc SM4 64

javac SM4_1.java
java -Xms1536M -Xmx1536M -XX:+PrintGC -verbose:gc SM4_1 64

javac SM4_2.java
java -Xms8M -Xmx8M -XX:+PrintGC -verbose:gc SM4_2 64

javac SM4_3.java
java -Xms128M -Xmx128M -XX:+PrintGC -verbose:gc SM4_3 64

javac SM4_4.java
java -Xms256M -Xmx256M -XX:+PrintGC -verbose:gc SM4_4 64