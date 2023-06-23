package com.messas.dataentryuser;

public class UserModel {
    String ide,phonenumber,name,dob,statusss,totaltaka,paidtakaa,duetakka,image,uuid,time,refer;

    public UserModel() {
    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStatusss() {
        return statusss;
    }

    public void setStatusss(String statusss) {
        this.statusss = statusss;
    }

    public String getTotaltaka() {
        return totaltaka;
    }

    public void setTotaltaka(String totaltaka) {
        this.totaltaka = totaltaka;
    }

    public String getPaidtakaa() {
        return paidtakaa;
    }

    public void setPaidtakaa(String paidtakaa) {
        this.paidtakaa = paidtakaa;
    }

    public String getDuetakka() {
        return duetakka;
    }

    public void setDuetakka(String duetakka) {
        this.duetakka = duetakka;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public UserModel(String ide, String phonenumber, String name, String dob, String statusss, String totaltaka,
                     String paidtakaa, String duetakka, String image, String uuid, String time, String refer) {
        this.ide = ide;
        this.phonenumber = phonenumber;
        this.name = name;
        this.dob = dob;
        this.statusss = statusss;
        this.totaltaka = totaltaka;
        this.paidtakaa = paidtakaa;
        this.duetakka = duetakka;
        this.image = image;
        this.uuid = uuid;
        this.time = time;
        this.refer = refer;
    }
}
