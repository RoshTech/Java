package AgeCare;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ManagerController implements Initializable {

    //------------------ Main Panel controls -----------------------------------------

    @FXML
    private AnchorPane welcomePane, addPatientPane, viewPatientPane, addDoctorPane, viewStaffPane, addNursePane;
    private FileWriter fw;
    private File file;
    private Scanner scanner;
    private Database db;

    ObservableList<String> startTimeList, stopTimeList;
    //--------------------------------------------------------------------------------

    //------------------ Bed details ----------------------------------------------------

    @FXML
    private Button w1r1b1, w1r1b2, w1r2b1, w1r2b2, w1r3b1, w1r4b1, w1r4b2, w1r4b3, w1r5b1, w1r5b2, w1r5b3, w1r5b4, w1r6b1, w1r6b2, w1r6b3, w1r6b4;
    @FXML
    private Button w2r1b1, w2r1b2, w2r2b1, w2r2b2, w2r3b1, w2r4b1, w2r4b2, w2r4b3, w2r5b1, w2r5b2, w2r5b3, w2r5b4, w2r6b1, w2r6b2, w2r6b3, w2r6b4;

    @FXML
    private Button vw1r1b1, vw1r1b2, vw1r2b1, vw1r2b2, vw1r3b1, vw1r4b1, vw1r4b2, vw1r4b3, vw1r5b1, vw1r5b2, vw1r5b3, vw1r5b4, vw1r6b1, vw1r6b2, vw1r6b3, vw1r6b4;
    @FXML
    private Button vw2r1b1, vw2r1b2, vw2r2b1, vw2r2b2, vw2r3b1, vw2r4b1, vw2r4b2, vw2r4b3, vw2r5b1, vw2r5b2, vw2r5b3, vw2r5b4, vw2r6b1, vw2r6b2, vw2r6b3, vw2r6b4;


    private final ArrayList<Button> Buttons = new ArrayList<>();
    private final ArrayList<Button> VButtons = new ArrayList<>();

    //--------------------------------------------------------------------------------------

    //------------------ Add Patient Panel controls ---------------------------------------
    @FXML
    private TextField patientID, patientName, patientGender, patientAge;
    @FXML
    private DatePicker admitDate, dischargeDate;
    @FXML
    private ComboBox<String> doctorList, nurseList, staffID;
    private String wrb;  // to map the ward,room and bed
    List<Patient> patients = new ArrayList<>();
    //-------------------------------------------------------------------------------------

    //------------ View Patient Details --------------------------------------------------
    @FXML
    private Label vpId, vpName, vpGender, vpAge, vpDId, vpNId, vpAdmit, vpDischarge;
    private boolean mvPatient;
    //-----------------------------------------------------------------------------------

    //------------------------ Staff -----------------------------------------------------
    List<Staff> staffs = new ArrayList<>();
    //-------------------------------------------------------------------------------------

    //-----------------------Doctor Panel Controls ---------------------------------
    @FXML
    private TextField doctorID, doctorName, doctorUsername, doctorPassword;
    @FXML
    private ComboBox<String> start1, stop1;
    //------------------------------------------------------------------------------


    //-----------------------Nurse Panel Controls ---------------------------------
    @FXML
    private TextField nurseID, nurseName, nurseUsername, nursePassword;
    @FXML
    private ComboBox<String> start2, stop2;
    //------------------------------------------------------------------------------

    //--------------------- View / Modify Staff ------------------------------------
    @FXML
    private TextField staffName, staffPassword;
    @FXML
    private Label staffUsername;
    @FXML
    private ComboBox<String> staffStart, staffStop;


    public void addPatientDetails() {
        addPatientPane.setVisible(true);
        viewPatientPane.setVisible(false);
        addDoctorPane.setVisible(false);
        addNursePane.setVisible(false);
        viewStaffPane.setVisible(false);
        welcomePane.setVisible(false);
        checkBeds(Buttons, 10, 16);
        setStaffList();
    }


    public void viewPatientDetails() {
        addPatientPane.setVisible(false);
        viewPatientPane.setVisible(true);
        addDoctorPane.setVisible(false);
        addNursePane.setVisible(false);
        viewStaffPane.setVisible(false);
        welcomePane.setVisible(false);
        setViewPatientPane();
    }

    public void setViewPatientPane() {
        vpId.setText("");
        vpName.setText("");
        vpGender.setText("");
        vpAge.setText("");
        vpDId.setText(null);
        vpNId.setText(null);
        vpAdmit.setText(null);
        vpDischarge.setText(null);
        checkBeds(VButtons, 11, 17);
    }

    public void addDoctorDetails() {
        addPatientPane.setVisible(false);
        viewPatientPane.setVisible(false);
        addDoctorPane.setVisible(true);
        addNursePane.setVisible(false);
        viewStaffPane.setVisible(false);
        welcomePane.setVisible(false);
    }

    public void addNurseDetails() {
        addPatientPane.setVisible(false);
        viewPatientPane.setVisible(false);
        addDoctorPane.setVisible(false);
        addNursePane.setVisible(true);
        viewStaffPane.setVisible(false);
        welcomePane.setVisible(false);

    }

    public void viewStaffDetails() {
        addPatientPane.setVisible(false);
        viewPatientPane.setVisible(false);
        addDoctorPane.setVisible(false);
        addNursePane.setVisible(false);
        viewStaffPane.setVisible(true);
        welcomePane.setVisible(false);
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
                        else if (gender.equals("F"))
                            b.setStyle("-fx-background-color: red ; -fx-text-fill: red");
                        else
                            b.setStyle("-fx-background-color: white ; -fx-text-fill: white ; -fx-border-color: black");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addPatient() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if (db.isPIdExist(patientID.getText()))
            alertMessage("Patient ID already exists", "ID Exists");
        else if (patientID.getText().isEmpty() || patientName.getText().isEmpty() || patientGender.getText().isEmpty() || patientAge.getText().isEmpty() || doctorList.getValue().toString().isEmpty() || nurseList.getValue().toString().isEmpty() || admitDate.getValue().toString().isEmpty() || dischargeDate.getValue().toString().isEmpty())
            alertMessage("Please fill in all fields", "Empty entry");
        else if (wrb.isEmpty())
            alertMessage("Please select the Bed", "Bed not selected");
        else {
            Patient newPatient = new Patient(patientID.getText(), patientName.getText(), patientGender.getText(), Integer.parseInt(patientAge.getText()), doctorList.getValue().toString(), nurseList.getValue().toString(), admitDate.getValue(), dischargeDate.getValue(), wrb);
            patients.add(newPatient);
            try {
                db.addPatients(patientID.getText(), patientName.getText(), patientGender.getText(), patientAge.getText(), doctorList.getValue().toString(), nurseList.getValue().toString(), admitDate.getValue().toString(), dischargeDate.getValue().toString(), wrb);
                fw = new FileWriter("./Files/PatientActivities.txt", true);
                fw.write("Patient ID: " + patientID.getText() + ", Admitted on: " + ldt.format(dtf) + ", By Staff ID: " + getCurrentUser() + "\n");
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            patientID.setText("");
            patientName.setText("");
            patientGender.setText("");
            patientAge.setText("");
            doctorList.setValue(null);
            nurseList.setValue(null);
            admitDate.setValue(null);
            dischargeDate.setValue(null);
            System.out.println(newPatient.getDetails());
        }
    }


    public void allotBed(ActionEvent event) {

        //Need to check with a list of beds that are already allotted
        String s = "";

        for (Button b : Buttons
        ) {
            if (event.getSource() == b) {
                wrb = event.getSource().toString().substring(10, 16);
                if (db.availBed(wrb)) {
                    System.out.println("Bed already occupied!!");
                    alertMessage("Bed already occupied by another patient!!!", "Occupied Bed");
                    break;
                } else {
                    System.out.println("Entering else condition" + wrb);
                    if (patientGender.getText().equals("M"))
                        s = "-fx-background-color: blue ; -fx-text-fill: blue";
                    else if (patientGender.getText().equals("F"))
                        s = "-fx-background-color: red ; -fx-text-fill: red";
                    else
                        s = "-fx-background-color: white ; -fx-text-fill: white ; -fx-border-color: black";

                    b.setStyle(s);
                }
            }
        }
    }

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
            setViewPatientPane();

        } else if (!matchBed.isEmpty())
            displayPatient(matchBed);
        else
            System.out.println("No details");
    }

    public void displayPatient(String matchBed) {
        String result = db.checkPatientBed(matchBed);
        if (!result.isEmpty()) {
            StringTokenizer str = new StringTokenizer(result, ":");
            vpId.setText(str.nextToken());
            vpName.setText(str.nextToken());
            vpGender.setText(str.nextToken());
            vpAge.setText(str.nextToken());
            vpDId.setText(str.nextToken());
            vpNId.setText(str.nextToken());
            vpAdmit.setText(str.nextToken());
            vpDischarge.setText(str.nextToken());
        }
    }

    public void movePatient(ActionEvent event) {
        if (vpId.getText().isEmpty())
            alertMessage("Select a bed to view Patient and then click Move", "Move Patient");
        else {
            alertMessage("Select a new bed for Patient ID: " + vpId.getText(), "Confirm Move Patient");
            mvPatient = true;
        }
    }


    public void dischargePatient() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Discharge Patient ID: " + vpId.getText() + " ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            String s = "Patient ID: " + vpId.getText() + "\n";
            s += "Name: " + vpName.getText() + "\n";
            s += "Doctor ID: " + vpDId.getText() + "\n";
            s += "Nurse ID: " + vpNId.getText() + "\n";
            s += "Admit Date: " + vpAdmit.getText() + "\n";
            s += "Discharge Date: " + vpDischarge.getText() + "\n";
            s += "Medicines Prescribed and Administered: \n";
            System.out.println(s);
            try {
                file = new File("./Files/PatientActivities.txt");
                scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);
                    StringTokenizer str = new StringTokenizer(line, ",");
                    String pId = str.nextToken().split(": ")[1];
                    if (pId.equals(vpId.getText()) && str.hasMoreTokens())
                        s += line + "\n";
                }
                scanner.close();
                db.dischargePatient(vpId.getText());
                file = new File("./Files/" + vpId.getText() + ".txt");
                file.createNewFile();
                fw = new FileWriter(file, true);
                fw.write(s);
                fw.close();
            } catch (Exception e) {
                alertMessage(e.getMessage(), "Error writing to file");
            }
            System.out.println("Yes pressed");
        }

    }


    public void addDoctor() {
        int start, stop;
        start = Integer.parseInt(start1.getValue().toString().split(":")[0]);
        stop = Integer.parseInt(stop1.getValue().toString().split(":")[0]);
        System.out.println(stop - start);

        if (db.isStaffIdExist(doctorID.getText()))
            alertMessage("Doctor ID already exists!!", "Doctor ID exists");
        else if (doctorID.getText().isEmpty() || doctorName.getText().isEmpty() || doctorUsername.getText().isEmpty() || doctorPassword.getText().isEmpty() || start1.getValue().toString().isEmpty() || stop1.getValue().toString().isEmpty())
            alertMessage("Field must not be empty! Please fill all fields", "Empty field");
        else {
            try {
                allotShifts(stop - start, "doctor");
                Staff newDoctor = new Doctor(doctorID.getText(), doctorName.getText(), doctorUsername.getText(), doctorPassword.getText(), start1.getValue().toString(), stop1.getValue().toString());
                staffs.add(newDoctor);
                System.out.println(newDoctor.getDetails());
                db.addStaff(doctorID.getText(), doctorName.getText(), doctorUsername.getText(), doctorPassword.getText(), start1.getValue().toString(), stop1.getValue().toString());
            } catch (Exception e) {
                alertMessage(e.getMessage(), "Overtime Workload!!!");
            }
            doctorID.setText("");
            doctorName.setText("");
            doctorUsername.setText("");
            doctorPassword.setText("");
            start1.setValue(null);
            stop1.setValue(null);
        }
    }


    public void addNurse() {
        int start, stop;
        start = Integer.parseInt(start2.getValue().toString().split(":")[0]);
        stop = Integer.parseInt(stop2.getValue().toString().split(":")[0]);
        System.out.println(start2.getValue().toString().split(":")[0] + " ----:---- " + stop2.getValue().toString().split(":")[0]);
        System.out.println(stop - start);
        if (db.isStaffIdExist(nurseID.getText()))
            alertMessage("Nurse ID already Exists", "Nurse ID Exists");
        else if (nurseID.getText().isEmpty() || nurseName.getText().isEmpty() || nurseUsername.getText().isEmpty() || nursePassword.getText().isEmpty() || start2.getValue().toString().isEmpty() || stop2.getValue().toString().isEmpty())
            alertMessage("Field must not be empty! Please fill all fields", "Empty field");
        else {
            try {
                allotShifts(stop - start, "nurse");
                Staff newNurse = new Nurse(nurseID.getText(), nurseName.getText(), nurseUsername.getText(), nursePassword.getText(), start2.getValue().toString(), stop2.getValue().toString());
                staffs.add(newNurse);
                System.out.println(newNurse.getDetails());
                db.addStaff(nurseID.getText(), nurseName.getText(), nurseUsername.getText(), nursePassword.getText(), start2.getValue().toString(), stop2.getValue().toString());
            } catch (Exception e) {
                alertMessage(e.getMessage(), "Overtime Workload!!!");
            }
            nurseID.setText("");
            nurseName.setText("");
            nurseUsername.setText("");
            nursePassword.setText("");
            start2.setValue(null);
            stop2.setValue(null);
        }
    }

    public void setStaffDetails() {
        System.out.println(staffID.getValue());
        StringTokenizer str = new StringTokenizer(db.displayStaff(staffID.getValue()), ".");
        staffName.setText(str.nextToken());
        staffUsername.setText(str.nextToken());
        staffPassword.setText(str.nextToken());
        staffStart.setValue(str.nextToken());
        staffStop.setValue(str.nextToken());
    }

    public void modifyStaff() {
        int start, stop;
        start = Integer.parseInt(staffStart.getValue().toString().split(":")[0]);
        stop = Integer.parseInt(staffStop.getValue().toString().split(":")[0]);
        if (staffName.getText().isEmpty() || staffPassword.getText().isEmpty() || staffStart.getValue().toString().isEmpty() || staffStop.getValue().toString().isEmpty())
            alertMessage("Field must not be empty! Please fill all fields", "Empty field");
        else {
            try {
                if (staffID.getValue().startsWith("D"))
                    allotShifts(stop - start, "doctor");
                else if (staffID.getValue().startsWith("N"))
                    allotShifts(stop - start, "nurse");
                else
                    alertMessage("Invalid ID", "Invalid ID");
                db.updateStaff(staffID.getValue(), staffName.getText(), staffUsername.getText(), staffPassword.getText(), staffStart.getValue().toString(), staffStop.getValue().toString());
            } catch (Exception e) {
                alertMessage(e.getMessage(), "Overtime Workload!!!");
            }
            staffID.setValue("Select Staff");
            staffName.setText("");
            staffUsername.setText("");
            staffPassword.setText("");
            staffStart.setValue(null);
            staffStop.setValue(null);
        }
    }

    public void setStartTime() {
        List<String> startTime = new ArrayList<>();
        for (int i = 0; i < 25; i++)
            startTime.add(i + ":00");
        startTimeList = FXCollections.observableArrayList(startTime);
    }

    public void setStopTime() {
        List<String> stopTime = new ArrayList<>();
        for (int i = 0; i < 25; i++)
            stopTime.add(i + ":00");
        stopTimeList = FXCollections.observableArrayList(stopTime);
    }


    public void allotShifts(int diff, String s) throws ShiftTimingException {
        if (diff != 1 && s.equals("doctor"))
            throw new ShiftTimingException("Doctor works more than an hour!!!");
        else if (diff > 8 && s.equals("nurse"))
            throw new ShiftTimingException("Nurse shift timing is greater than 8HRS");
        else
            System.out.println("Valid");
    }

    public void setStaffList() {
        doctorList.setItems(FXCollections.observableArrayList(db.fetchStaff("D")));
        nurseList.setItems(FXCollections.observableArrayList(db.fetchStaff("N")));
        ArrayList<String> StaffList = new ArrayList<>();
        StaffList.addAll(db.fetchStaff("D"));
        StaffList.addAll(db.fetchStaff("N"));
        staffID.setItems(FXCollections.observableArrayList(StaffList));
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
        wrb = "";
        mvPatient = false;

        setStartTime();
        setStopTime();
        setStaffList();
        start1.setItems(startTimeList);
        start2.setItems(startTimeList);
        staffStart.setItems(startTimeList);
        stop1.setItems(stopTimeList);
        stop2.setItems(stopTimeList);
        staffStop.setItems(stopTimeList);

        Buttons.add(w1r1b1);
        Buttons.add(w1r1b2);
        Buttons.add(w1r2b1);
        Buttons.add(w1r2b2);
        Buttons.add(w1r3b1);
        Buttons.add(w1r4b1);
        Buttons.add(w1r4b2);
        Buttons.add(w1r4b3);
        Buttons.add(w1r5b1);
        Buttons.add(w1r5b2);
        Buttons.add(w1r5b3);
        Buttons.add(w1r5b4);
        Buttons.add(w1r6b1);
        Buttons.add(w1r6b2);
        Buttons.add(w1r6b3);
        Buttons.add(w1r6b4);

        Buttons.add(w2r1b1);
        Buttons.add(w2r1b2);
        Buttons.add(w2r2b1);
        Buttons.add(w2r2b2);
        Buttons.add(w2r3b1);
        Buttons.add(w2r4b1);
        Buttons.add(w2r4b2);
        Buttons.add(w2r4b3);
        Buttons.add(w2r5b1);
        Buttons.add(w2r5b2);
        Buttons.add(w2r5b3);
        Buttons.add(w2r5b4);
        Buttons.add(w2r6b1);
        Buttons.add(w2r6b2);
        Buttons.add(w2r6b3);
        Buttons.add(w2r6b4);

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
    }
}

