FROM openjdk:19
ADD src .
RUN javac Main.java
CMD ["java", "Main"]