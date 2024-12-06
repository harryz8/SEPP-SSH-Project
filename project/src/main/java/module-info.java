module com.sshgroup.ssh_fridge_contents_tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires jdk.jfr;
    requires jdk.unsupported.desktop;
    requires java.rmi;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.transaction;
    requires jakarta.cdi;

    opens com.sshgroup.ssh_fridge_contents_tracker to javafx.fxml;
    exports com.sshgroup.ssh_fridge_contents_tracker;
}