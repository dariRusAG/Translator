FROM openjdk:19
ADD src .
VOLUME javadocker
RUN javac Main.java
CMD ["java", "Main"]