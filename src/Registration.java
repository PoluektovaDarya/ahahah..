package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class Registration {
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private TextField nameField;
    @FXML private TextField secondNameField;
    @FXML private TextField thirdNameField;
    @FXML private Button btnregistration;

    @FXML
    private void initialize() {
        FXMLLoader loader = new FXMLLoader();
        btnregistration.setOnAction(event -> {
            boolean inBase = false;
            if (!(loginField.getText().isEmpty() || passwordField.getText().isEmpty()
                    || nameField.getText().isEmpty() || secondNameField.getText().isEmpty()|| thirdNameField.getText().isEmpty())){
                try{
                    String username = loginField.getText();
                    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data",
                            "root", "Mabe.131852")){
                        PreparedStatement statement = conn.prepareStatement
                                ("INSERT users(login, password, firstName, secondName, thirdname) VALUES(?, ?, ?, ?, ?)");
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
                        while(resultSet.next()){
                            if (resultSet.getString("login").equals(username)){
                                inBase = true;
                                break;
                            }
                        }
                        if (!inBase){
                            statement.setString(1, loginField.getText());
                            statement.setString(2, passwordField.getText());
                            statement.setString(3, nameField.getText());
                            statement.setString(4, secondNameField.getText());
                            statement.setString(5, thirdNameField.getText());
                            statement.executeUpdate();
                            btnregistration.getScene().getWindow().hide();
                            loader.setLocation(getClass().getResource("firstWindow.fxml"));

                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            assert root != null;
                            stage.setScene(new Scene(root));
                            stage.show();
                        }
                    }
                }
                catch(Exception ex){
                    System.out.println("Ошибка доступа к БД");
                }
            }
        });
    }
}
