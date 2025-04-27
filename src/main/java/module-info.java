/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module Login_Empresa_IFCoin {
 
//    requires javafx.controlsEmpty;
    requires javafx.controls;
//    requires javafx.fxmlEmpty;
    requires javafx.fxml;
//    requires javafx.baseEmpty;
    requires javafx.base;
//    requires javafx.graphicsEmpty;
    requires javafx.graphics;
    requires java.sql;
    requires org.slf4j;
    requires kernel;
    requires org.bouncycastle.pkix;
    requires org.bouncycastle.util;
    requires org.bouncycastle.provider;
    requires layout;
    requires io;
    requires commons;
    requires pdfa;
    requires mysql.connector.j;
    requires com.google.protobuf;
    requires java.base;
    requires java.desktop;
    
    opens application to javafx.fxml;
    opens controller to javafx.fxml;
    opens model.classes to javafx.fxml;
    opens view.utils to javafx.fxml;
    
    exports application;
    exports controller;
    exports model.classes;
    exports view.utils;
}
