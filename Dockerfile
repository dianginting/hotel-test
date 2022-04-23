FROM alpine:3
COPY build/libs/hotel-test-0.1-all.jar hotelcli.jar
EXPOSE 8098
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-XX:+IdleTuningGcOnIdle", "-Xtune:virtualized", "-jar", "hotelcli.jar"]