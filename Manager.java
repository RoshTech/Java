package AgeCare;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Manager {


    @FXML
    private TextField doctorID, doctorName, doctorUsername, doctorPassword, startTimeDoctor, stopTimeDoctor;


    @FXML
    private DatePicker admitDate, dischargeDate;

    @FXML
    private Button addPatient, addDoctor, addNurse;

    @FXML
    private TextField startTimeNurse, stopTimeNurse;

    List<Patient> patients = new ArrayList<Patient>();
    List<Staff> staffs = new ArrayList<Staff>();

    FileWriter fw;

    public void addDoctor() {
        int start, stop;
        start = Integer.parseInt(startTimeDoctor.getText());
        stop = Integer.parseInt(stopTimeDoctor.getText());
        System.out.println(stop - start);
        try {
            allotShifts(stop - start, "doctor");

            Staff newDoctor = new Doctor(doctorID.getText(), doctorName.getText(), doctorUsername.getText(), doctorPassword.getText(), startTimeDoctor.getText(), stopTimeDoctor.getText());
            staffs.add(newDoctor);
            System.out.println(newDoctor.getDetails());
            try {
                fw = new FileWriter("./Files/Doctors.txt", true);
                fw.write(newDoctor.getDetails() + "\n");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setContentText(e.getMessage());
            alert.setTitle("Overtime Workload!!!");
            alert.show();
        }

        doctorID.setText("");
        doctorName.setText("");
        doctorUsername.setText("");
        doctorPassword.setText("");
        startTimeDoctor.setText("");
        stopTimeDoctor.setText("");

    }

//    public void addNurse() {
//        int start, stop;
//        start = Integer.parseInt(startTimeNurse.getText());
//        stop = Integer.parseInt(stopTimeNurse.getText());
//        System.out.println(stop - start);
//        try {
//            allotShifts(stop - start, "nurse");
//            Staff newNurse = new Nurse(nurseID.getText(), nurseName.getText(), nurseUsername.getText(), nursePassword.getText(), startTimeNurse.getText(), stopTimeNurse.getText());
//            staffs.add(newNurse);
//            System.out.println(newNurse.getDetails());
//            try {
//                fw = new FileWriter("./Files/Nurses.txt", true);
//                fw.write(newNurse.getDetails() + "\n");
//                fw.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setHeaderText("");
//            alert.setContentText(e.getMessage());
//            alert.setTitle("Overtime Workload!!!");
//            alert.show();
//        }
//
//        nurseID.setText("");
//        nurseName.setText("");
//        nurseUsername.setText("");
//        nursePassword.setText("");
//        startTimeNurse.setText("");
//        stopTimeNurse.setText("");
//
//    }


//    public void addPatient(ActionEvent event) {
//        Patient newPatient = new Patient(patientID.getText(), patientName.getText(), Integer.parseInt(patientAge.getText()), admitDate.getValue(), dischargeDate.getValue());
//        patients.add(newPatient);
//        try {
//            fw = new FileWriter("./Files/Patients.txt", true);
//            fw.write(newPatient.getDetails() + "\n");
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        patientID.setText("");
//        patientName.setText("");
//        patientAge.setText("");
//        admitDate.setValue(null);
//        dischargeDate.setValue(null);
//
//
//        System.out.println(newPatient.getDetails());
//
//    }


    public void movePatient() {
        /*
         *  Move patient from a bed/room/ward to another bed/room/ward
         *  This will notify the nurse in-charge and perform action
         *  Use pId, (ward/room/bed)ID to map changes
         * */

    }

    public void allotShifts(int diff, String s) throws ShiftTimingException {
        if (diff != 1 && s.equals("doctor"))
            throw new ShiftTimingException("Doctor works more than an hour!!!");
        else if (diff > 8 && s.equals("nurse"))
            throw new ShiftTimingException("Nurse shift timing is greater than 8HRS");
        else
            System.out.println("Valid");


    }

    public void updateDoctorDetails() {
        /*
         * Update Doctor's details - username,password,phone no., shifts
         * */
    }

    public void updateNurseDetails() {
        /*
         * Update Nurse's details - username,password,phone no., shifts
         * */
    }


}
