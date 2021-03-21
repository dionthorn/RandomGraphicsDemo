module RandomGraphicsDemo {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires java.logging;
    requires java.desktop;
    requires javafx.swing;

    exports org.dionthorn.graphicsdemo to javafx.graphics;
}