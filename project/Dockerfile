FROM gradle:8.10.2
WORKDIR /usr/src/rps
COPY src/main/java/com/sshgroup/ssh_fridge_contents_tracker .
RUN gradle installDist

FROM gradle:jdk21
WORKDIR /root/
COPY --from=0 /usr/src/rps/app/build/install/app .
CMD ["./bin/app"]