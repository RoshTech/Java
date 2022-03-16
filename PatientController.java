package AgeCare;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class PatientController {

    @FXML
    private TextField patientID, patientName, patientAge;

    @FXML
    private DatePicker admitDate, dischargeDate;

    @FXML
    private Button addPatient;

    List<Patient> patients = new ArrayList<Patient>();


//    public void addPatient(ActionEvent event) {
//        Patient newPatient = new Patient(patientID.getText(), patientName.getText(), Integer.parseInt(patientAge.getText()), admitDate.getValue(), dischargeDate.getValue());
//        patients.add(newPatient);
//        showPatient();
//    }

    public List<Patient> showPatient(){
        return patients;
    }
}
