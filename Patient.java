package AgeCare;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String id, name, gender;
    private int age;
    private String doctorId, nurseId;
    private LocalDate admitDate, dischargeDate;
    private String bedID;
    private List<String> Presciption;

    public Patient(String id, String name, String gender, int age, String doctorId, String nurseId, LocalDate admitDate, LocalDate dischargeDate, String bedID) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.doctorId = doctorId;
        this.nurseId = nurseId;
        this.admitDate = admitDate;
        this.dischargeDate = dischargeDate;
        this.bedID = bedID;
        Presciption = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public LocalDate getAdmitDate() {
        return this.admitDate;
    }

    public LocalDate getDischargeDate() {
        return this.dischargeDate;
    }

    public String getBedID() {
        return this.bedID;
    }

    public String getDetails() {
        return this.id + ": " + this.name + ": " + this.gender + ": " + this.age + ": " + this.admitDate + ": " + this.dischargeDate + ": " + this.doctorId + ": " + this.nurseId + ": " + this.bedID;
    }

    public void addPrescription(String s) {
        Presciption.add(s);
    }
}
