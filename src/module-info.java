module javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires java.base;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    
    opens application;
    opens db;
    opens model.entities;
    opens view;
    opens view.util;
}


