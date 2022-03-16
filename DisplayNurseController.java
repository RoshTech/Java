package AgeCare;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DisplayNurseController {

    File file;
    Scanner scanner;

    @FXML
    private Button nextBtn, previousBtn, load;
    @FXML
    private Label dNId, dNName, dNStart, dNStop;

    private ArrayList<String> id, name, start, stop;
    private int i;

    public void loadFile(ActionEvent event) throws FileNotFoundException {

        id = new ArrayList<>();
        name = new ArrayList<>();
        start = new ArrayList<>();
        stop = new ArrayList<>();

        file = new File("./Files/Nurses.txt");
        scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            StringTokenizer stringTokenizer = new StringTokenizer(scanner.nextLine(), ": ");
            id.add(stringTokenizer.nextToken());
            name.add(stringTokenizer.nextToken());
            start.add(stringTokenizer.nextToken());
            stop.add(stringTokenizer.nextToken());
        }
        dNId.setText(id.get(0));
        dNName.setText(name.get(0));
        dNStart.setText(start.get(0));
        dNStop.setText(stop.get(0));

        scanner.close();
        //i = 0;
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
        dNId.setText(id.get(i));
        dNName.setText(name.get(i));
        dNStart.setText(start.get(i));
        dNStop.setText(stop.get(i));
    }
}
