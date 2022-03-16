package AgeCare;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DoctorController implements Initializable {


    @FXML
    private Button vw1r1b1, vw1r1b2, vw1r2b1, vw1r2b2, vw1r3b1, vw1r4b1, vw1r4b2, vw1r4b3, vw1r5b1, vw1r5b2, vw1r5b3, vw1r5b4, vw1r6b1, vw1r6b2, vw1r6b3, vw1r6b4;
    @FXML
    private Button vw2r1b1, vw2r1b2, vw2r2b1, vw2r2b2, vw2r3b1, vw2r4b1, vw2r4b2, vw2r4b3, vw2r5b1, vw2r5b2, vw2r5b3, vw2r5b4, vw2r6b1, vw2r6b2, vw2r6b3, vw2r6b4;
    @FXML
    private Label vpId, vpName, vpGender, vpAge, vpNId, vpAdmit, vpDischarge;
    @FXML
    private Label pID, pName;
    @FXML
    private TextArea details;


    private ArrayList<Button> VButtons = new ArrayList<>();
    FileWriter fw;
    private Database db;
    private File file;
    private Scanner scanner;

    public void viewPatient(ActionEvent event) throws Exception {
        String matchBed = "";
        for (Button b : VButtons
        ) {
            if (event.getSource() == b)
                matchBed = event.getSource().toString().substring(11, 17);
        }
        if (!matchBed.isEmpty())
            displayPatient(matchBed);

        else
            System.out.println("No details");
    }

    public void displayPatient(String matchBed) {
        String result = db.checkPatientBed(matchBed);
        StringTokenizer str = new StringTokenizer(result, ":");
        vpId.setText(str.nextToken());
        vpName.setText(str.nextToken());
        vpGender.setText(str.nextToken());
        vpAge.setText(str.nextToken());
        String doctor = str.nextToken();
        vpNId.setText(str.nextToken());
        vpAdmit.setText(str.nextToken());
        vpDischarge.setText(str.nextToken());
    }

    public void checkBeds(ArrayList<Button> buttons, int start, int stop) {
        String bedId, gender;
        try {
            ArrayList<String> beds = db.checkBed();
            for (String bed : beds
            ) {
                StringTokenizer str = new StringTokenizer(bed, ":");
                bedId = str.nextToken();
                gender = str.nextToken();
                for (Button b : buttons
                ) {
                    if (bedId.equals(b.toString().substring(start, stop))) {
                        if (gender.equals("M"))
                            b.setStyle("-fx-background-color: blue ; -fx-text-fill: blue");
                        else
                            b.setStyle("-fx-background-color: red ; -fx-text-fill: red");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addPrescription() {
        if (getCurrentUser().equals(db.getStaffId(vpId.getText(), "DoctorID"))) {

            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("addPrescription.fxml"));
                stage.setTitle("Add Prescription");
                stage.setUserData(vpId);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root, 400, 400));
                stage.show();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else
            alertMessage("Sorry Doctor! You are not allowed to prescribe medicine to this patient");

    }

    public void alertMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.setTitle("Unauthorised!");
        alert.show();
    }


    public String getCurrentUser() {
        String s = "";
        try {
            file = new File("./Files/CurrentUser.txt");
            scanner = new Scanner(file);
            while (scanner.hasNextLine())
                s = scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

    public void onAdd(ActionEvent event) {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        System.out.println(details.getText() + stage.getUserData().toString().substring(33, 37));
        //pID.setText(stage.getUserData().toString().substring(33, 37));
        try {
            fw = new FileWriter("./Files/Prescription.txt", true);
            fw.write(stage.getUserData().toString().substring(33, 37) + ": " + details.getText() + "\n");
            fw.close();
            fw = new FileWriter("./Files/PatientActivities.txt", true);
            fw.write("Patient ID: " + stage.getUserData().toString().substring(33, 37) + ", Medicine: " + details.getText() + ", Prescribed at: " + ldt.format(dtf) + ", By Staff ID: " + getCurrentUser() + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUp() {
        db = new Database();
        VButtons.add(vw1r1b1);
        VButtons.add(vw1r1b2);
        VButtons.add(vw1r2b1);
        VButtons.add(vw1r2b2);
        VButtons.add(vw1r3b1);
        VButtons.add(vw1r4b1);
        VButtons.add(vw1r4b2);
        VButtons.add(vw1r4b3);
        VButtons.add(vw1r5b1);
        VButtons.add(vw1r5b2);
        VButtons.add(vw1r5b3);
        VButtons.add(vw1r5b4);
        VButtons.add(vw1r6b1);
        VButtons.add(vw1r6b2);
        VButtons.add(vw1r6b3);
        VButtons.add(vw1r6b4);

        VButtons.add(vw2r1b1);
        VButtons.add(vw2r1b2);
        VButtons.add(vw2r2b1);
        VButtons.add(vw2r2b2);
        VButtons.add(vw2r3b1);
        VButtons.add(vw2r4b1);
        VButtons.add(vw2r4b2);
        VButtons.add(vw2r4b3);
        VButtons.add(vw2r5b1);
        VButtons.add(vw2r5b2);
        VButtons.add(vw2r5b3);
        VButtons.add(vw2r5b4);
        VButtons.add(vw2r6b1);
        VButtons.add(vw2r6b2);
        VButtons.add(vw2r6b3);
        VButtons.add(vw2r6b4);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUp();
        checkBeds(VButtons, 11, 17);

    }

}
