package AgeCare;

import jdk.jshell.spi.ExecutionControl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    Connection conn = null;

    public void setup() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:Database/AbleCare.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addStaff(String id, String name, String username, String password, String startTime, String stopTime) {
        try {
            setup();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO STAFF(ID, Name, Username, Password, StartTime, StopTime) VALUES(?,?,?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, startTime);
            ps.setString(6, stopTime);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String displayStaff(String id) {
        String s = "";

        try {
            setup();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM STAFF WHERE ID = '" + id + "';");
            while (rs.next()) {
                s = rs.getString("Name") + "." + rs.getString("Username") + "." + rs.getString("Password") + "." + rs.getString("StartTime") + "." + rs.getString("StopTime");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

    public ArrayList<String> fetchStaff(String id) {
        String s = "";
        ArrayList<String> staffID = new ArrayList<>();
        try {
            setup();
            ResultSet rs = conn.createStatement().executeQuery("SELECT ID FROM STAFF WHERE ID LIKE '" + id + "%';");
            while (rs.next()) {
                s = rs.getString("ID");
                staffID.add(s);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return staffID;
    }

    public void updateStaff(String id, String name, String username, String password, String startTime, String stopTime) {
        try {
            setup();
            PreparedStatement ps = conn.prepareStatement("UPDATE STAFF SET Name = ?, Username = ?, Password = ?, StartTime = ?, StopTime = ? WHERE ID = ?");
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, startTime);
            ps.setString(5, stopTime);
            ps.setString(6, id);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String checkCreds(String username, String password) {
        String result = "", s = "";

        if (username.equals("admin") && password.equals("admin")) {
            result = "admin";
            return result;
        } else if (username.startsWith("D"))
            s = "Doctor";
        else if (username.startsWith("N"))
            s = "Nurse";
        else
            result = "";

        try {
            setup();
            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT * FROM STAFF WHERE Username = '" + username + "' AND Password = '" + password + "';");
            if (rs.next()) result = s;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String checkPatientBed(String bedId) {
        String s = "";
        try {
            setup();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM PATIENT WHERE BedID = '" + bedId + "';");
            while (rs.next()) {
                s = rs.getString("PatientID") + ":" +
                        rs.getString("Name") + ":" + rs.getString("Gender") + ":" +
                        rs.getString("Age") + ":" + rs.getString("DoctorID") + ":" +
                        rs.getString("NurseID") + ":" + rs.getString("Admit") + ":" + rs.getString("Discharge");
            }
            return s;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

    public String getStaffId(String patientID, String staff) {
        String s = "";
        try {
            setup();
            ResultSet rs = conn.createStatement().executeQuery("SELECT " + staff + " FROM PATIENT WHERE PatientID = '" + patientID + "';");
            while (rs.next())
                s = rs.getString(staff);
            return s;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

    public boolean isPIdExist(String patientID) {
        try {
            setup();
            ResultSet rs = conn.createStatement().executeQuery("SELECT PatientID FROM PATIENT WHERE PatientID = '" + patientID + "';");
            if (rs.next()) return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean isStaffIdExist(String ID) {
        try {
            setup();
            ResultSet rs = conn.createStatement().executeQuery("SELECT ID FROM STAFF WHERE ID = '" + ID + "';");
            if (rs.next()) return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<String> checkBed() throws SQLException {
        setup();
        String s = "";
        ArrayList<String> beds = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery("SELECT BedID,Gender FROM Patient;");
        while (rs.next()) {
            s = rs.getString("BedID") + ":" + rs.getString("Gender");
            beds.add(s);
        }
        return beds;
    }

    public boolean availBed(String id) {
        try {
            setup();
            ResultSet rs = conn.createStatement().executeQuery("SELECT BedID FROM Patient WHERE BedID = '" + id + "';");
            if (rs.next()) return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void addPatients(String id, String name, String gender, String age, String doctorID, String nurseID, String admit, String discharge, String bedID) throws SQLException {
        try {
            setup();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Patient(PatientID,Name,Gender,Age, DoctorID, NurseID,Admit,Discharge,BedId) VALUES(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, gender);
            ps.setString(4, age);
            ps.setString(5, doctorID);
            ps.setString(6, nurseID);
            ps.setString(7, admit);
            ps.setString(8, discharge);
            ps.setString(9, bedID);
            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePatientBed(String patientId, String bedId) {
        try {
            setup();
            PreparedStatement ps = conn.prepareStatement("UPDATE PATIENT SET BedID = ? WHERE PatientId = ?");
            ps.setString(1, bedId);
            ps.setString(2, patientId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void dischargePatient(String patientID){
        try{
            setup();
            PreparedStatement ps = conn.prepareStatement("UPDATE PATIENT SET BedID = ? WHERE PatientId = ?");
            ps.setString(1,null);
            ps.setString(2,patientID);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
