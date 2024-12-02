module com.sshgroup.ssh_fridge_contents_tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.unsupported.desktop;
    requires java.rmi;
    requires org.hibernate.orm.core;


    opens com.sshgroup.ssh_fridge_contents_tracker to javafx.fxml;
    exports com.sshgroup.ssh_fridge_contents_tracker;
}