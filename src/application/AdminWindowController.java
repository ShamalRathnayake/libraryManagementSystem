package application;





import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.mysql.cj.protocol.Resultset;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class AdminWindowController implements Initializable{

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnLogout;

    @FXML
    private AnchorPane pnlRemove;

    @FXML
    private Button btnDeleteUser;

    @FXML
    private TableView<TableClass> tblUsers;
    
    private ObservableList<TableClass> data;
    
    @FXML
    private TableColumn<TableClass, String> tblUserId;

    @FXML
    private TableColumn<TableClass, String> tblFirstName;

    @FXML
    private TableColumn<TableClass, String> tblLastName;

    @FXML
    private TableColumn<TableClass, String> tblPhone;

    @FXML
    private TableColumn<TableClass, String> tblEmail;

    @FXML
    private TableColumn<TableClass, String> tblUsername;

    @FXML
    private TableColumn<TableClass, String> tblRole;

    @FXML
    private TableView<TableClass> tblDashUser;

    @FXML
    private TableColumn<TableClass, String> tblDashId;

    @FXML
    private TableColumn<TableClass, String> tblDashFirstName;

    @FXML
    private TableColumn<TableClass, String> tblDashLastName;

    @FXML
    private TableColumn<TableClass, String> tblDashPhone;

    @FXML
    private TableColumn<TableClass, String> tblDashEmail;

    @FXML
    private TableColumn<TableClass, String> tblDashUsername;

    @FXML
    private TableColumn<TableClass, String> tblDashRole;


    @FXML
    private TextField txtUserId;

    @FXML
    private AnchorPane pnlRegister;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtPhone;

    @FXML
    private DatePicker dobSelect;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Button btnAddUser;

    @FXML
    private Button btnResetUser;

    @FXML
    private RadioButton rbtnAdmin;

    @FXML
    private ToggleGroup group1;

    @FXML
    private RadioButton rbtnLibrarian;

    @FXML
    private AnchorPane pnlDashboard;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	updateTable(tblDashUser, tblDashId, tblDashFirstName, tblDashLastName, tblDashPhone, tblDashEmail, tblDashUsername, tblDashRole);
    	updateTable(tblUsers, tblUserId, tblFirstName, tblLastName, tblPhone, tblEmail, tblUsername, tblRole);
    	
		
	}
    

    private void updateTable(TableView table,TableColumn id,TableColumn firstname,TableColumn lastname,TableColumn phone,TableColumn email,TableColumn username,TableColumn role) {
    	try {
    		Connection con = DatabaseConnection.getdbDriver().getConnection();
    		data = FXCollections.observableArrayList();
    		String query = "SELECT id,firstname,lastname,phone,email,username,role FROM user";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				data.add(new TableClass(rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
				
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
   		 
    		alert.setTitle("Database Error");
    		alert.setHeaderText("Connecting to Database Failed");
    		alert.setContentText("Please Run WAMP/LAMP/XAMPP on Your Computer\nMake Sure the Corresponding Database Exists\n" + e.getMessage());
    		 
    		alert.showAndWait();
		}
    	
    	id.setCellValueFactory(new PropertyValueFactory<>("id"));
    	firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    	lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    	phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    	email.setCellValueFactory(new PropertyValueFactory<>("email"));
    	username.setCellValueFactory(new PropertyValueFactory<>("username"));
    	role.setCellValueFactory(new PropertyValueFactory<>("role"));
    	
    	table.setItems(data);
	}


	@FXML
    void addUser(ActionEvent event) {
    	
    	String firstName = txtFirstName.getText();
    	String lastName = txtLastName.getText();
        LocalDate dob = dobSelect.getValue();
    	String address = txtAddress.getText();
    	String phone = txtPhone.getText();
    	String email = txtEmail.getText();
    	String username = txtUsername.getText();
    	String password = txtPassword.getText();
    	String confirmPassword = txtConfirmPassword.getText();
    	RadioButton rbtn =(RadioButton)group1.getSelectedToggle();
    	String role = rbtn.getText();
    	
    	
    	if(validateInputs(firstName, lastName, phone,username, password, confirmPassword)) {
    		
    		try {
    			
    		 	Connection con = DatabaseConnection.getdbDriver().getConnection();
				Statement stmt = con.createStatement();
				String query = "INSERT INTO `user`( `firstname`, `lastname`, `dob`, `address`, `phone`, `email`, `username`, `password`, `role`) VALUES ('"+ firstName +"','"+lastName+"','"+dob+"','"+address+"','"+phone+"','"+email+"','"+username+"','"+password+"','"+role+"')";
				int x = stmt.executeUpdate(query); 
	            if (x > 0) {
	            	Alert alert = new Alert(AlertType.INFORMATION);
		    		 
		    		alert.setTitle("Success");
		    		alert.setHeaderText("Database Entry Successfull");
		    		alert.setContentText("The User was Added to the Database");
		    		 
		    		alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
		    		 
		    		alert.setTitle("Database Error");
		    		alert.setHeaderText("Database Entry Unsuccessfull");
		    		alert.setContentText("The User was NOT Added to the Database");
		    		 
		    		alert.showAndWait();

				}
	            stmt.close();
	            con.close();
	            clearInputFields();
				
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
	    		 
	    		alert.setTitle("Database Error");
	    		alert.setHeaderText("Connecting to Database Failed");
	    		alert.setContentText("Please Run WAMP/LAMP/XAMPP on Your Computer\nMake Sure the Corresponding Database Exists\n" + e.getMessage());
	    		 
	    		alert.showAndWait();
			}
    	}


    }

    private void clearInputFields() {
    	LocalDate arg0 = LocalDate.now();
    	txtFirstName.setText("");
    	txtLastName.setText("");
        dobSelect.getEditor().clear();
    	txtAddress.setText("");
    	txtPhone.setText("");
    	txtEmail.setText("");
    	txtUsername.setText("");
    	txtPassword.setText("");
    	txtConfirmPassword.setText("");
    	
    	
		
	}

	private boolean validateInputs(String firstName, String lastName, String phone, String username, String password,
			String confirmPassword) {
		if (firstName.isBlank() || firstName.isEmpty() ||  phone.isBlank() || phone.isEmpty() || username.isBlank() || username.isEmpty() || password.isBlank() || password.isEmpty() || confirmPassword.isBlank() || confirmPassword.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
   		 
    		alert.setTitle("Input Error");
    		alert.setHeaderText("Fields Empty or Contain Whitespace");
    		alert.setContentText("Please Fill All Required Fields\nPasswords need to be 8 characters long");
    		 
    		alert.showAndWait();
		}else {
			if(!password.equals(confirmPassword)) {
				Alert alert = new Alert(AlertType.ERROR);
		   		 
	    		alert.setTitle("Input Error");
	    		alert.setHeaderText("Passwords Do NOT Match");
	    		alert.setContentText("Please Fill Passwords Again");
	    		 
	    		alert.showAndWait();
	    		
	    		txtPassword.setText("");
	    		txtConfirmPassword.setText("");
			}else {
				return true;
			}
		}
		return false;
	}

	@FXML
    void clearFields(ActionEvent event) {
		clearInputFields();

    }

    @FXML
    void deleteUser(ActionEvent event) {
    	try {
    		String userId = txtUserId.getText();
		 	Connection con = DatabaseConnection.getdbDriver().getConnection();
			Statement stmt = con.createStatement();
			String query = "DELETE FROM user WHERE id = '"+ userId +"'";
			
			int x = stmt.executeUpdate(query); 
            if (x > 0) {
            	Alert alert = new Alert(AlertType.INFORMATION);
	    		 
	    		alert.setTitle("Success");
	    		alert.setHeaderText("Database Entry Successfull");
	    		alert.setContentText("The User was Deleted from the Database");
	    		 
	    		alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
	    		 
	    		alert.setTitle("Database Error");
	    		alert.setHeaderText("Database Entry Unsuccessfull");
	    		alert.setContentText("The User was NOT Deleted from the Database");
	    		 
	    		alert.showAndWait();

			}
            stmt.close();
            con.close();
            clearInputFields();
            
        	updateTable(tblUsers, tblUserId, tblFirstName, tblLastName, tblPhone, tblEmail, tblUsername, tblRole);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
    		 
    		alert.setTitle("Database Error");
    		alert.setHeaderText("Connecting to Database Failed");
    		alert.setContentText("Please Run WAMP/LAMP/XAMPP on Your Computer\nMake Sure the Corresponding Database Exists\n" + e.getMessage());
    		 
    		alert.showAndWait();
		}

    }

    @FXML
    void logoutUser(ActionEvent event) {
    	try {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sample.fxml"));
        	Parent root1 = fxmlLoader.load();
        	Stage stage = new Stage();
        	stage.initStyle(StageStyle.DECORATED);
        	
        	stage.setScene(new Scene(root1));
    		stage.show();
    		Stage oldStage = (Stage)btnLogout.getScene().getWindow();
    		oldStage.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
   		 
    		alert.setTitle("Logout Error");
    		alert.setHeaderText("Ending Session Failed");
    		alert.setContentText( e.getMessage());
    		 
    		alert.showAndWait();
		}

    }

    @FXML
    void showDashboard(ActionEvent event) {
    	pnlDashboard.toFront();

    }

    @FXML
    void showRegister(ActionEvent event) {
    	pnlRegister.toFront();

    }

    @FXML
    void showRemove(ActionEvent event) {
    	pnlRemove.toFront();
    	
    	updateTable(tblUsers, tblUserId, tblFirstName, tblLastName, tblPhone, tblEmail, tblUsername, tblRole);

    }

	

}
