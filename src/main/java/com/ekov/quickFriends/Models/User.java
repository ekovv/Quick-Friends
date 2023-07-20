package com.ekov.quickFriends.Models;

public class User {
    public String FirstName;
    public String LastName;
    public String FatherName;
    public String City;
    public String School;
    public String ClassNumber;
    public String Email;
    public String Phone;
    public String about;

    @Override
    public String toString() {
        return "User{" +
                "FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", FatherName='" + FatherName + '\'' +
                ", City='" + City + '\'' +
                ", School='" + School + '\'' +
                ", ClassNumber='" + ClassNumber + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}

