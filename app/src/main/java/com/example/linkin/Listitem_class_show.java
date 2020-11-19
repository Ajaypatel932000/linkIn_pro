package com.example.linkin;

public class Listitem_class_show {
    // this is class for custome data type
    private  String u_id,job_type,msg,seat,salary;

    public Listitem_class_show(String u_id, String job_type, String msg, String seat, String salary) {
        this.u_id = u_id;
        this.job_type = job_type;
        this.msg = msg;
        this.seat = seat;
        this.salary = salary;
    }
    // cunstructor for custome data type
    /*public Listitem_class_show(String u_id, String job_type, String msg, String seat) {
        this.u_id = u_id;
        this.job_type = job_type;
        this.msg = msg;
        this.seat = seat;
    }*/

    public String getSalary() {
        return salary;
    }

    public String getU_id() {
        return u_id;
    }

    public String getJob_type() {
        return job_type;
    }

    public String getMsg() {
        return msg;
    }

    public String getSeat() {
        return seat;
    }

}
