package application;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SampleController {

    @FXML
    private Button btnAdmin;

    @FXML
    private Button btnLib;

    @FXML
    private AnchorPane pnlAdmin;

    @FXML
    private TextField txtAdminUsr;

    @FXML
    private PasswordField txtAdminPass;
    
    @FXML
    private TextField txtLibUsr;

    @FXML
    private PasswordField txtLibPass;

    @FXML
    private Button btnAdminLogin;

    @FXML
    private AnchorPane pnlLib;

    @FXML
    private Button libLogin;

    @FXML
    void adminLogin(ActionEvent event) {
    	
    	String username = txtAdminUsr.getText();
    	String password = txtAdminPass.getText();
    	
    	
    	if (detailValidation(username,password)) {
			try {
				Connection con = DatabaseConnection.getdbDriver().getConnection();
				Statement stmt = con.createStatement();
				String query = "SELECT password,role FROM user WHERE username = '" + username + "'";
				
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					String pass = rs.getString(1);
					String role = rs.getString(2);
					if (pass.equals(password)) {
						String page = null;
						if(role.equals("Administrator")) {
							page = "AdminWindow.fxml";
						}else if(role.equals("Librarian")) {
							page = "LibrarianWindow.fxml";
						}else {
							page = null;
							Alert alert = new Alert(AlertType.ERROR);
				    		 
				    		alert.setTitle("Authentication Error");
				    		alert.setHeaderText("Authenticating User Failed");
				    		alert.setContentText("You are NOT a User");
				    		 
				    		alert.showAndWait();
						}
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(page));
                    	Parent root1 = (Parent) fxmlLoader.load();
                    	Stage stage = new Stage();
                    	stage.initStyle(StageStyle.DECORATED);
                    	stage.setTitle(role);
                    	stage.setScene(new Scene(root1));
            			stage.show();
            			
            			
						Stage oldStage = (Stage)btnAdminLogin.getScene().getWindow();
						oldStage.close();
					}else {
						
						Alert alert = new Alert(AlertType.ERROR);
			    		 
			    		alert.setTitle("Authentication Error");
			    		alert.setHeaderText("Authenticating User Failed");
			    		alert.setContentText("Wrong Password!!!");
			    		 
			    		alert.showAndWait();
					}
				}
				rs.close();
				stmt.close();
				con.close();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
	    		 
	    		alert.setTitle("Database Error");
	    		alert.setHeaderText("Connecting to Database Failed");
	    		alert.setContentText("Please Run WAMP/LAMP/XAMPP on Your Computer\nMake Sure the Corresponding Database Exists\n" + e.getMessage());
	    		 
	    		alert.showAndWait();
			} 
    		
		}

    }

    private boolean detailValidation(String username, String password) {
    	
    	if (username.isBlank() || username.isEmpty() || password.isBlank() || password.isEmpty() || password.length() < 8 ) {
    		Alert alert = new Alert(AlertType.ERROR);
    		 
    		alert.setTitle("Input Error");
    		alert.setHeaderText("Fields Empty or Contain Whitespace");
    		alert.setContentText("Please Fill All Fields\nPasswords need to be 8 characters long");
    		 
    		alert.showAndWait();
    		
    		return false;
		} else {
			
			return true;

		}


		
	}

	@FXML
    void libLogin(ActionEvent event) {

    }

    @FXML
    void showAdminPanel(ActionEvent event) {
    	pnlAdmin.toFront();

    }

    @FXML
    void showLibPanel(ActionEvent event) {
    	pnlLib.toFront();

    }

}
