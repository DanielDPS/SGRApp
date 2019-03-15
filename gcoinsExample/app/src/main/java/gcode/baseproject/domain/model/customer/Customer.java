package gcode.baseproject.domain.model.customer;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;
import java.util.Date;

public class Customer {

    @SerializedName("cid")
    private  String Id;
    @SerializedName("cidentificador")
    private String Identifier;
    @SerializedName("crazonsoc")
    private  String BusinessName;
    @SerializedName("cactividad")
    private  String Activity;
    @SerializedName("cdireccion")
    private  String Direction;
    @SerializedName("centidad")
    private  String Entity;
    @SerializedName("ccorreo01")
    private  String Mail1;
    @SerializedName("ccorreo02")
    private  String Mail2;
    @SerializedName("ccorreo03")
    private  String Mail3;
    @SerializedName("ctelefono01")
    private  String Number1;
    @SerializedName("ctelefono02")
    private  String Number2;
    @SerializedName("cfechaini")
    private String InitialDate;
    @SerializedName("cpermisocre")
    private  String CreationPermission;
    @SerializedName("crfc")
    private  String Rfc;
    @SerializedName("crepresentante")
    private  String Representative;
    @SerializedName("ccreacion")
    private String Creation;
    @SerializedName("cmodificacion")
    private  String Upgrade;
    @SerializedName("cactivo")
    private String Active;

    public  void setId(String id){
        Id=id;
    }
    public  String getId(){
        return Id;
    }
    public void setIdentifier(String identifier){
        Identifier= identifier;
    }
    public  String getIdentifier(){
        return Identifier;
    }
    public  void setBusinessName(String businessname){
        BusinessName= businessname;
    }
    public  String getBusinessName(){
        return BusinessName;
    }
    public  void setActivity(String activity){
        Activity = activity;
    }
    public  String getActivity(){
        return Activity;
    }
    public  void setDirection(String direction){
        Direction= direction;
    }
    public  String getDirection(){
        return Direction;
    }
    public  void setEntity(String entity){
        Entity = entity;
    }
    public  String getEntity(){
        return Entity;
    }

    public  void setMail1(String mail1){
        Mail1=mail1;
    }
    public  String getMail1(){
        return Mail1;
    }
    public  void setMail2(String mail2){
        Mail2= mail2;
    }
    public  String getMail2(){
        return Mail2;
    }
    public  void setMail3(String mail3){
        Mail3= mail3;
    }
    public  String getMail3(){
        return Mail3;
    }
    public void setNumber1(String number1){
        Number1 = number1;
    }
    public  String getNumber1(){
        return Number1;
    }
    public  void setNumber2(String number2){
        Number2= number2;
    }
    public  String getNumber2(){
        return Number2;
    }
    public  void setInitialDate(String initialdate){
        InitialDate = initialdate;
    }
    public  String getInitialDate(){
        return InitialDate;
    }
    public  void setCreationPermission(String creationpermission){
        CreationPermission = creationpermission;
    }
    public  String getCreationPermission(){
        return CreationPermission;
    }
    public  void setRfc(String rfc ){
        Rfc= rfc;
    }
    public  String getRfc(){
        return Rfc;
    }
    public  void setRepresentative(String representative){
        Representative= representative;
    }
    public String getRepresentative(){
        return Representative;
    }
    public  void setCreation(String creation){
        Creation = creation;
    }
    public  String getCreation(){
        return Creation;
    }
    public  void setUpgrade(String upgrade){
        Upgrade = upgrade;
    }
    public  String getUpgrade(){
        return Upgrade;
    }
    public void setActive(String active){
        Active = active;
    }
    public  String getActive(){
        return Active;
    }

}
