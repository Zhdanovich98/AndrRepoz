package com.example.prog_002;

import java.util.Comparator;

public class Contacts {


    private int id;
    private String name;
    private String surname;
    private String filial;
    private String otdel;
    private String post;
    private String phone;
    private String mail;
    private String image;
    Contacts(){

    }

    Contacts(String name, String surname, String filial, String otdel,String post, String phone, String mail, String image){
        this.name=name;
        this.surname=surname;
        this.filial=filial;
        this.otdel=otdel;
        this.post=post;
        this.phone=phone;
        this.mail = mail;
        this.image = image;
    }

    Contacts(int id, String name, String surname, String filial, String otdel,String post, String phone, String mail, String image){
        this.id=id;
        this.name=name;
        this.surname=surname;
        this.filial=filial;
        this.otdel=otdel;
        this.post=post;
        this.phone=phone;
        this.mail = mail;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getOtdel() {
        return otdel;
    }

    public void setOtdel(String otdel) {
        this.otdel = otdel;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public static Comparator<Contacts> nameComparator = new Comparator<Contacts>() {

        public int compare(Contacts s1, Contacts s2) {
            String contactName1 = s1.getName().toUpperCase();
            String contactName2 = s2.getName().toUpperCase();

            return contactName1.compareTo(contactName2);
        }};

    @Override
    public String toString()  {
       return this.name + "\n" + this.phone;
    }

}

