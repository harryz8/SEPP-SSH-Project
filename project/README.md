## Before you run!!

You must add your ubuntu user to docker.
1. `sudo groupadd docker`
2. `sudo usermod -aG docker <your ubuntu username>`
3. `newgrp docker`
4. Then restart WSL / Ubuntu

## If the run button doesn't work in IntelliJ
1. Make sure that you have set IntelliJ up to use Java 21 or Java 17
2. Click the Gradle button: ![Gradle Elephant](readme_images/gradle.png)
3. Double click run that is under application: ![Gradle tab in IntelliJ](readme_images/gradle_tab.png)

## GUI mode
1. To run in GUI mode, run by using the command `./gradlew run --args="i"` in the terminal.
2. Any other argument, or no argument, will make it run in command line mode.