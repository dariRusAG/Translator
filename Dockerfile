FROM openjdk:19
ADD run.sh .
CMD ["sh", "run.sh"]
