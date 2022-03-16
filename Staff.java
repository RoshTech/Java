package AgeCare;

public abstract class Staff {
    private String ID, name, userName, password;
    private String startTime, stopTime;

    public Staff(String ID, String name, String userName, String password, String startTime, String stopTime) {
        this.ID = ID;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public String getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getStopTime() {
        return this.stopTime;
    }

    public String getUserPass() {
        return this.userName + ": " + this.password;
    }

    public String getDetails() {
        return this.ID + ": " + this.name + ": " + this.userName + ": " + this.password + ": " + this.startTime + ": " + this.stopTime;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
