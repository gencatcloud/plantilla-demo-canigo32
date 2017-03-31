FROM gencatcloud/java:8

VOLUME /tmp
COPY maven/plantillaCanigo.war /app.war

COPY run.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh 

CMD ["/entrypoint.sh"]