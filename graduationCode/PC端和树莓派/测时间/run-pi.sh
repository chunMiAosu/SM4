#!/bin/bash
#get run time

wget http://192.168.29.233:8000/SM4.java
wget http://192.168.29.233:8000/SM4_1.java
wget http://192.168.29.233:8000/SM4_2.java
wget http://192.168.29.233:8000/SM4_3.java
wget http://192.168.29.233:8000/SM4_4.java

javac SM4.java
for((i=0;i<5;i++));
do
java SM4 1 ;
done

for((i=0;i<5;i++));
do
java SM4 8 ;
done

for((i=0;i<5;i++));
do
java SM4 64 ;
done

for((i=0;i<5;i++));
do
java SM4 128 ;
done

javac SM4_1.java
for((i=0;i<5;i++));
do
java SM4_1 1 ;
done

for((i=0;i<5;i++));
do
java SM4_1 8 ;
done

for((i=0;i<5;i++));
do
java SM4_1 64 ;
done

for((i=0;i<5;i++));
do
java SM4_1 128 ;
done

javac SM4_2.java
for((i=0;i<5;i++));
do
java SM4_2 1 ;
done

for((i=0;i<5;i++));
do
java SM4_2 8 ;
done

for((i=0;i<5;i++));
do
java SM4_2 64 ;
done

for((i=0;i<5;i++));
do
java SM4_2 128 ;
done

for((i=0;i<5;i++));
do
java SM4_2 256 ;
done

for((i=0;i<5;i++));
do
java SM4_2 512 ;
done

for((i=0;i<5;i++));
do
java SM4_2 1024 ;
done

javac SM4_3.java
for((i=0;i<5;i++));
do
java SM4_3 1 ;
done

for((i=0;i<5;i++));
do
java SM4_3 8 ;
done

for((i=0;i<5;i++));
do
java SM4_3 64 ;
done

for((i=0;i<5;i++));
do
java SM4_3 128 ;
done

for((i=0;i<5;i++));
do
java SM4_3 256 ;
done

for((i=0;i<5;i++));
do
java SM4_3 512 ;
done

for((i=0;i<5;i++));
do
java SM4_3 1024 ;
done

javac SM4_4.java
for((i=0;i<5;i++));
do
java SM4_4 1 ;
done

for((i=0;i<5;i++));
do
java SM4_4 8 ;
done

for((i=0;i<5;i++));
do
java SM4_4 64 ;
done

for((i=0;i<5;i++));
do
java SM4_4 128 ;
done

for((i=0;i<5;i++));
do
java SM4_4 256 ;
done

for((i=0;i<5;i++));
do
java SM4_4 512 ;
done

for((i=0;i<5;i++));
do
java SM4_4 1024 ;
done