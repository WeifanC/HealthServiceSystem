package com.example.DentalHealth;

/**
 * user variable define
 */
public class UserInfo {
        private int id;
        private String name;
        private String SSN;
        private String address;
        private String age;
        private String gender;
        private String birth;
        private String insurance;
        private String identity;

    public UserInfo(int id, String name, String SSN, String address, String age, String gender, String birth, String insurance,String identity) {
        this.id = id;
        this.name = name;
        this.SSN = SSN;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.birth = birth;
        this.insurance = insurance;
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "PatientInfo{" +
                "id=" + id +
                ", patientname='" + name + '\'' +
                ", SSN='" + SSN + '\'' +
                ", address='" + address + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", birth='" + birth + '\'' +
                ", Insurance number='" + insurance + '\'' +
                ", Identity= '" + identity + '\''+
                '}';
    }

    /**
     * get all the info from the patient/ user
     * @return
     */
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identify) {
        this.identity = identity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }
}
