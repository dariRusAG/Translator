FROM openjdk:19
ADD Main.java .
RUN javac Main.java
CMD ["java", "Main"]