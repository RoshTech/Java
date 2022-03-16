package AgeCare;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMsg;
    @FXML
    private ImageView username, password;

    private String pageUrl, pageTitle;
    private Database db;


    public void onLoginClicked() throws Exception {

        String result = db.checkCreds(usernameField.getText(), passwordField.getText());

        if (result.equals("Doctor"))
            doctorGUI();
        else if (result.equals("Nurse"))
            nurseGUI();
        else if (result.equals("admin"))
            adminGUI();
        else
            errorMsg.setText("Incorrect Username/Password!!");
    }

    public void doctorGUI() throws Exception {
        setUser(usernameField.getText());
        pageUrl = "doctor_menu.fxml";
        pageTitle = "Doctor Menu";
        setScreen(pageUrl, pageTitle, 600, 600);
    }

    public void nurseGUI() throws Exception {
        setUser(usernameField.getText());
        pageUrl = "nurse_menu.fxml";
        pageTitle = "Nurse Menu";
        setScreen(pageUrl, pageTitle, 600, 600);
    }

    public void adminGUI() throws Exception {
        setUser(usernameField.getText());
        pageUrl = "manager_menu.fxml";
        pageTitle = "Manager Menu";
        setScreen(pageUrl, pageTitle, 800, 600);
    }


    public void setScreen(String pageUrl, String pageTitle, int v, int h) throws Exception {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(pageUrl));
        primaryStage.setTitle(pageTitle);
        primaryStage.setScene(new Scene(root, v, h));
        primaryStage.show();
    }

    public void setUser(String user){

        try{
            FileWriter fw = new FileWriter("./Files/CurrentUser.txt");
            fw.write(user);
            fw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File imageUsername = new File("Image/username.jpg");
        Image UsernameImg = new Image(imageUsername.toURI().toString());
        username.setImage(UsernameImg);

        File imagePassword = new File("Image/password1.jpg");
        Image PasswordImg = new Image(imagePassword.toURI().toString());
        password.setImage(PasswordImg);

        db = new Database();
    }

}
