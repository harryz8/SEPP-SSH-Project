FROM gradle:8.11.1
WORKDIR /usr/src/ssh
COPY . .
RUN gradle installDist

FROM gradle:jdk21
WORKDIR /root/
COPY --from=0 /usr/src/ssh/build/install/SSH_Fridge_Contents_Tracker .
CMD ["./bin/SSH_Fridge_Contents_Tracker"]