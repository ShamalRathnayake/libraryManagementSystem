package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LibrarianWindowController  implements Initializable {

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
    private TableView<BookTableClass> tblRemoveBooks;

    @FXML
    private TableColumn<BookTableClass, String> tblBookId;

    @FXML
    private TableColumn<BookTableClass, String> tblBookName;

    @FXML
    private TableColumn<BookTableClass, String> tblBookAuthor;

    @FXML
    private TableColumn<BookTableClass, String> tblISBN;

    @FXML
    private Button btnDeleteBook;

    @FXML
    private TextField txtBookId;
    
    @FXML
    private TextField txtReturnBookId;
    
    @FXML
    private TextField txtBookId1;

    @FXML
    private AnchorPane pnlDashboard;

    @FXML
    private Button btnDeleteUser1;

    @FXML
    private TextField txtUserId1;

    @FXML
    private AnchorPane pnlRegister;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtTSlug;

    @FXML
    private TextField txtASlug;

    @FXML
    private Button btnAddBook;
    
    @FXML
    private Button btnReturnBook;

    @FXML
    private Button btnResetUser;

    @FXML
    private TextField txtAuthor;
    
    @FXML
    private TableView<BookTableClass> tblBooks;
    
    private ObservableList<BookTableClass> data;
    
    @FXML
    private TableColumn<BookTableClass, String> tblDashId;

    @FXML
    private TableColumn<BookTableClass, String> tblDashTitle;

    @FXML
    private TableColumn<BookTableClass, String> tblDashAuthor;

    @FXML
    private TableColumn<BookTableClass, String> tblDashIsbn;
    
    @FXML
    private Button btnissueBook;
    
    @FXML
    private TableColumn<BookTableClass, String> tblDashIssued;
    
    @FXML
    private TableColumn<BookTableClass, String> tblIssued;


    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	updateBookTable(tblBooks, tblDashId, tblDashTitle, tblDashAuthor, tblDashIsbn, tblDashIssued);
    	updateBookTable(tblRemoveBooks, tblBookId, tblBookName, tblBookAuthor, tblISBN, tblIssued);
    	
		
	}
    
    
    @FXML
    void addBook(ActionEvent event) {
    	String title = txtTitle.getText();
    	String author = txtAuthor.getText();
    	String titleSlug = txtTSlug.getText();
    	String authorSlug = txtASlug.getText();
    	String isbn13 = txtISBN.getText();
    	
    	if(validateInputs(title, author, titleSlug, authorSlug, isbn13)) {
    		
    		try {
    			
    		 	Connection con = DatabaseConnection.getdbDriver().getConnection();
				Statement stmt = con.createStatement();
				String query = "INSERT INTO `book`( `title`, `author`, `titleSlug`, `authorSlug`, `isbn13`) VALUES ('"+ title +"','"+author+"','"+titleSlug+"','"+authorSlug+"','"+isbn13+"')";
				int x = stmt.executeUpdate(query); 
	            if (x > 0) {
	            	Alert alert = new Alert(AlertType.INFORMATION);
		    		 
		    		alert.setTitle("Success");
		    		alert.setHeaderText("Database Entry Successfull");
		    		alert.setContentText("The Book was Added to the Database");
		    		 
		    		alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
		    		 
		    		alert.setTitle("Database Error");
		    		alert.setHeaderText("Database Entry Unsuccessfull");
		    		alert.setContentText("The Book was NOT Added to the Database");
		    		 
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
    	txtTitle.setText("");
    	txtAuthor.setText("");
        txtTSlug.setText("");
    	txtASlug.setText("");
    	txtISBN.setText("");
    	
    	
    	
		
	}
    
    private boolean validateInputs(String title, String author, String titleSlug, String authorSlug, String isbn13) {
		if (title.isBlank() || title.isEmpty() ||  titleSlug.isBlank() || titleSlug.isEmpty() || authorSlug.isBlank() || authorSlug.isEmpty() || isbn13.isBlank() || isbn13.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
   		 
    		alert.setTitle("Input Error");
    		alert.setHeaderText("Fields Empty or Contain Whitespace");
    		alert.setContentText("Please Fill All Required Fields\nPasswords need to be 8 characters long");
    		 
    		alert.showAndWait();
		}else {
			
				return true;
			
		}
		return false;
	}

    @FXML
    void clearFields(ActionEvent event) {
    	clearInputFields();

    }

    @FXML
    void deleteBook(ActionEvent event) {
    	try {
    		String bookId = txtBookId.getText();
		 	Connection con = DatabaseConnection.getdbDriver().getConnection();
			Statement stmt = con.createStatement();
			String query = "DELETE FROM book WHERE id = '"+ bookId +"'";
			
			int x = stmt.executeUpdate(query); 
            if (x > 0) {
            	Alert alert = new Alert(AlertType.INFORMATION);
	    		 
	    		alert.setTitle("Success");
	    		alert.setHeaderText("Database Entry Successfull");
	    		alert.setContentText("The Book was Deleted from the Database");
	    		 
	    		alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
	    		 
	    		alert.setTitle("Database Error");
	    		alert.setHeaderText("Database Entry Unsuccessfull");
	    		alert.setContentText("The Book was NOT Deleted from the Database");
	    		 
	    		alert.showAndWait();

			}
            stmt.close();
            con.close();
            clearInputFields();
            updateBookTable(tblRemoveBooks,tblBookId,tblBookName,tblBookAuthor,tblISBN,tblIssued);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
    		 
    		alert.setTitle("Database Error");
    		alert.setHeaderText("Connecting to Database Failed");
    		alert.setContentText("Please Run WAMP/LAMP/XAMPP on Your Computer\nMake Sure the Corresponding Database Exists\n" + e.getMessage());
    		 
    		alert.showAndWait();
		}


    }

    @FXML
    void issueBook(ActionEvent event) {
    	try {
    		String bookId = txtBookId.getText();
    		
		 	Connection con = DatabaseConnection.getdbDriver().getConnection();
			Statement stmt = con.createStatement();
			String query = "UPDATE book set issued = 'true' WHERE id = '"+ bookId +"'";
			
			int x = stmt.executeUpdate(query); 
            if (x > 0) {
            	Alert alert = new Alert(AlertType.INFORMATION);
	    		 
	    		alert.setTitle("Success");
	    		alert.setHeaderText("Database Entry Successfull");
	    		alert.setContentText("The Book status was Updated");
	    		 
	    		alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
	    		 
	    		alert.setTitle("Database Error");
	    		alert.setHeaderText("Database Entry Unsuccessfull");
	    		alert.setContentText("The Book status was NOT Updated" );
	    		 
	    		alert.showAndWait();

			}
            stmt.close();
            con.close();
            clearInputFields();
            updateBookTable(tblBooks, tblDashId, tblDashTitle, tblDashAuthor, tblDashIsbn, tblDashIssued);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
    		 
    		alert.setTitle("Database Error");
    		alert.setHeaderText("Connecting to Database Failed");
    		alert.setContentText("Please Run WAMP/LAMP/XAMPP on Your Computer\nMake Sure the Corresponding Database Exists\n" + e.getMessage());
    		 
    		alert.showAndWait();
		}



    }


    @FXML
    void returnBook(ActionEvent event) {
    	try {
    		String bookId = txtReturnBookId.getText();
    		
		 	Connection con = DatabaseConnection.getdbDriver().getConnection();
			Statement stmt = con.createStatement();
			String query = "UPDATE book set issued = '' WHERE id = '"+ bookId +"'";
			
			int x = stmt.executeUpdate(query); 
            if (x > 0) {
            	Alert alert = new Alert(AlertType.INFORMATION);
	    		 
	    		alert.setTitle("Success");
	    		alert.setHeaderText("Database Entry Successfull");
	    		alert.setContentText("The Book status was Updated");
	    		 
	    		alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
	    		 
	    		alert.setTitle("Database Error");
	    		alert.setHeaderText("Database Entry Unsuccessfull");
	    		alert.setContentText("The Book status was NOT Updated" );
	    		 
	    		alert.showAndWait();

			}
            stmt.close();
            con.close();
            clearInputFields();
            updateBookTable(tblBooks, tblDashId, tblDashTitle, tblDashAuthor, tblDashIsbn, tblDashIssued);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
    		 
    		alert.setTitle("Database Error");
    		alert.setHeaderText("Connecting to Database Failed");
    		alert.setContentText("Please Run WAMP/LAMP/XAMPP on Your Computer\nMake Sure the Corresponding Database Exists\n" + e.getMessage());
    		 
    		alert.showAndWait();
		}



    }
    
    
    private void updateBookTable(TableView table,TableColumn id,TableColumn title,TableColumn author,TableColumn isbn, TableColumn issued) {
    	try {
    		Connection con = DatabaseConnection.getdbDriver().getConnection();
    		data = FXCollections.observableArrayList();
    		String query = "SELECT id,title,author,isbn13,issued FROM book";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				data.add(new BookTableClass(rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
				
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
   		 
    		alert.setTitle("Database Error");
    		alert.setHeaderText("Connecting to Database Failed");
    		alert.setContentText("Please Run WAMP/LAMP/XAMPP on Your Computer\nMake Sure the Corresponding Database Exists\n" + e.getMessage());
    		 
    		alert.showAndWait();
		}
    	
    	id.setCellValueFactory(new PropertyValueFactory<>("id"));
    	title.setCellValueFactory(new PropertyValueFactory<>("title"));
    	author.setCellValueFactory(new PropertyValueFactory<>("author"));
    	isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
    	issued.setCellValueFactory(new PropertyValueFactory<>("issued"));
    	
    	
    	table.setItems(data);
		
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
    	updateBookTable(tblBooks, tblDashId, tblDashTitle, tblDashAuthor, tblDashIsbn, tblDashIssued);

    }

    @FXML
    void showRegister(ActionEvent event) {
    	pnlRegister.toFront();

    }

    @FXML
    void showRemove(ActionEvent event) {
    	pnlRemove.toFront();
    	updateBookTable(tblRemoveBooks, tblBookId, tblBookName, tblBookAuthor, tblISBN, tblIssued);

    }

}
