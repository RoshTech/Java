package AgeCare;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import java.io.File;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DisplayPatientController {
    @FXML
    private Label dPId, dPName, dPAge, dAdmit, dDischarge;

    @FXML
    private Button nextBtn, previousBtn, load;


    private ArrayList<String> id, name, age, admit, discharge;
    private int i;


    File file;
    Scanner scanner;

    public void loadFile(ActionEvent event) throws Exception {

        id = new ArrayList<>();
        name = new ArrayList<>();
        admit = new ArrayList<>();
        age = new ArrayList<>();
        discharge = new ArrayList<>();
        file = new File("./Files/Patients.txt");
        scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            StringTokenizer stringTokenizer = new StringTokenizer(scanner.nextLine(), ": ");
            id.add(stringTokenizer.nextToken());
            name.add(stringTokenizer.nextToken());
            age.add(stringTokenizer.nextToken());
            admit.add(stringTokenizer.nextToken());
            discharge.add(stringTokenizer.nextToken());

        }
        dPId.setText(id.get(0));
        dPName.setText(name.get(0));
        dPAge.setText(age.get(0));
        dAdmit.setText(admit.get(0));
        dDischarge.setText(discharge.get(0));
        scanner.close();
        // i=0;
    }


    public void showNext(ActionEvent event) throws Exception {
        i++;
        if (i < id.size())
            show(i);
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("");
            alert.setContentText("End of List");
            alert.setTitle("Error!!");
            alert.show();

        }
    }

    public void showPrevious(ActionEvent event) throws Exception {
        i--;
        show(i);
    }

    public void show(int i) {
        dPId.setText(id.get(i));
        dPName.setText(name.get(i));
        dPAge.setText(age.get(i));
        dAdmit.setText(admit.get(i));
        dDischarge.setText(discharge.get(i));

    }

}
