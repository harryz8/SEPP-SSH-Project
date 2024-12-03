module com.sshgroup.ssh_fridge_contents_tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires jdk.jfr;


    opens com.sshgroup.ssh_fridge_contents_tracker to javafx.fxml;
    exports com.sshgroup.ssh_fridge_contents_tracker;
}