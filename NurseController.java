package AgeCare;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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

public class NurseController implements Initializable {

    @FXML
    private Button viewPatientBtn;
    @FXML
    private Button vw1r1b1, vw1r1b2, vw1r2b1, vw1r2b2, vw1r3b1, vw1r4b1, vw1r4b2, vw1r4b3, vw1r5b1, vw1r5b2, vw1r5b3, vw1r5b4, vw1r6b1, vw1r6b2, vw1r6b3, vw1r6b4;
    @FXML
    private Button vw2r1b1, vw2r1b2, vw2r2b1, vw2r2b2, vw2r3b1, vw2r4b1, vw2r4b2, vw2r4b3, vw2r5b1, vw2r5b2, vw2r5b3, vw2r5b4, vw2r6b1, vw2r6b2, vw2r6b3, vw2r6b4;
    @FXML
    private Label vpId, vpName, vpGender, vpAge, vpDId, vpAdmit, vpDischarge;
    @FXML
    private GridPane gridPane;
    private boolean mvPatient;

    private ArrayList<Button> VButtons = new ArrayList<>();
    private Database db;
    private File file;
    private Scanner scanner;


    public void viewPatient(ActionEvent event) {
        String matchBed = "";
        for (Button b : VButtons
        ) {
            if (event.getSource() == b)
                matchBed = event.getSource().toString().substring(11, 17);
        }
        if (!matchBed.isEmpty() && mvPatient) {
            db.updatePatientBed(vpId.getText(), matchBed);
            mvPatient = false;
            checkBeds(VButtons, 11, 17);
        } else if (!matchBed.isEmpty())
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
        vpDId.setText(str.nextToken());
        String nurse = str.nextToken();
        vpAdmit.setText(str.nextToken());
        vpDischarge.setText(str.nextToken());
    }

    public void movePatient() {
        if (vpId.getText().isEmpty())
            alertMessage("Select a bed to view Patient and then click Move", "Move Patient");
        else {
            alertMessage("Select a new bed for Patient ID: " + vpId.getText(), "Confirm Move Patient");
            mvPatient = true;
        }
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

    public void viewPrescription() throws IOException {
        if (getCurrentUser().equals(db.getStaffId(vpId.getText(), "NurseID"))) {
            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            file = new File("./Files/Prescription.txt");
            scanner = new Scanner(file);
            gridPane = new GridPane();
            Label id = new Label();
            Label name = new Label();
            Label Pid = new Label();
            Label Pname = new Label();
            id.setText("Patient ID");
            name.setText("Name");
            Pid.setText(vpId.getText());
            Pname.setText(vpName.getText());
            Label Administered = new Label();
            Administered.setText("Administered? Check the box, if YES");

            gridPane.add(id, 1, 0);
            gridPane.add(name, 2, 0);
            gridPane.add(Pid, 1, 1);
            gridPane.add(Pname, 2, 1);
            gridPane.add(Administered, 2, 2);

            ArrayList<String> prescription = new ArrayList<>();
            ArrayList<CheckBox> checkBoxes = new ArrayList<>();

            try {
                while (scanner.hasNextLine()) {
                    StringTokenizer str = new StringTokenizer(scanner.nextLine(), ": ");
                    if (str.nextToken().equals(vpId.getText())) {
                        prescription.add(str.nextToken());
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            scanner.close();
            for (int i = 0; i < prescription.size(); i++) {
                CheckBox checkBox = new CheckBox(prescription.get(i));
                gridPane.add(checkBox, 2, i + 3);
                checkBoxes.add(checkBox);
            }
            gridPane.setHgap(10);
            gridPane.setVgap(10);


            EventHandler eh = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (event.getSource() instanceof CheckBox) {
                        CheckBox chk = (CheckBox) event.getSource();
                        System.out.println("Patient ID: " + vpId.getText() + ", Medicine: " + chk.getText() + ", Administered at: " + ldt.format(dtf));
                        try {
                            FileWriter fw = new FileWriter("./Files/PatientActivities.txt", true);
                            fw.write("Patient ID: " + vpId.getText() + ", Medicine: " + chk.getText() + ", Administered at: " + ldt.format(dtf) + ", By Staff ID: " + getCurrentUser() + "\n");
                            fw.close();
                        } catch (Exception e) {
                            alertMessage(e.getMessage(), "Error while writing to file");
                        }
                    }
                }
            };
            for (CheckBox c : checkBoxes
            ) {
                c.setOnAction(eh);
            }

            Stage stage = new Stage();
            stage.setTitle("View Prescription");
            stage.setUserData(vpId);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(gridPane, 400, 400));
            stage.show();
        } else
            alertMessage("Sorry Nurse! You are not allowed to administer Medicine to this patient", "Unauthorised!");

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


    public void setUp() {
        db = new Database();
        mvPatient = false;
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

    public void alertMessage(String content, String title) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("");
        alert.setContentText(content);
        alert.setTitle(title);
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUp();
        checkBeds(VButtons, 11, 17);
    }
}
