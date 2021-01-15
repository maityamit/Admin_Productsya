package admin.example.adminsecurechoice;

public class Contact {

    private  String Text,Sender;

    public Contact ()
    {

    }

    public Contact (String TEXT,String SENDER) {
        this.Text = TEXT;
        this.Sender = SENDER;



    }


    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }
}
