FROM java:19.0.1
ADD Main.java .
RUN javac Main.java
CMD ["java", "Main"]