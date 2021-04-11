javac -encoding utf-8 SM4.java
java -Xms1536M -Xmx1536M -XX:+PrintGC -verbose:gc SM4 64

javac -encoding utf-8 SM4_1.java
java -Xms1536M -Xmx1536M -XX:+PrintGC -verbose:gc SM4_1 64

javac -encoding utf-8 SM4_2.java
java -Xms8M -Xmx8M -XX:+PrintGC -verbose:gc SM4_2 64

javac -encoding utf-8 SM4_3.java
java -Xms128M -Xmx128M -XX:+PrintGC -verbose:gc SM4_3 64

javac -encoding utf-8 SM4_4.java
java -Xms256M -Xmx256M -XX:+PrintGC -verbose:gc SM4_4 64