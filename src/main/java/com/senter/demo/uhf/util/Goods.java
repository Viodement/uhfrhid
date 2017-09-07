package com.senter.demo.uhf.util;

/**
 * Created by Lenovo on 2017/5/5.
 */
public class Goods {
    private String name;
    private String date;
    private String job;
    private String jobId;

    public Goods() {
        super();
    }

    public Goods(String name, String date, String job, String jobId) {
        super();
        this.name = name;
        this.date = date;
        this.job = job;
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
