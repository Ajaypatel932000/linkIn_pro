package com.example.linkin;

public class ListItem_std_show {

    public String email,number,job_type,message,vacant_seat,salary,name;

    public ListItem_std_show(String email, String number, String job_type, String message, String vacant_seat, String salary, String name) {
        this.email = email;
        this.number = number;
        this.job_type = job_type;
        this.message = message;
        this.vacant_seat = vacant_seat;
        this.salary = salary;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSalary() {
        return salary;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getJob_type() {
        return job_type;
    }

    public String getMessage() {
        return message;
    }

    public String getVacant_seat() {
        return vacant_seat;
    }
}
