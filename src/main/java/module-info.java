module sio.gestionsubventions {
    requires javafx.controls;
    requires javafx.fxml;
    opens sio.gestionsubventions to javafx.fxml;
    opens sio.gestionsubventions.Model;
    exports sio.gestionsubventions;
}