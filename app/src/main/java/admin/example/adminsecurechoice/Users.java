package admin.example.adminsecurechoice;

public class Users {


    private  String Name,Email,MobileNo;

    public Users ()
    {

    }

    public Users (String NAME,String EMAIL,String MOBILE) {
        this.Name = NAME;
        this.Email = EMAIL;
        this.MobileNo=MOBILE;

    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }
}
