package gcode.baseproject.domain.model.user;

import android.provider.ContactsContract;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import androidx.room.Update;

public class User {

    @SerializedName("cid")
    private  String Id;
    @SerializedName("cidrol")
    private  String FkRole;
    @SerializedName("cnombre")
    private  String Name;
    @SerializedName("cusuario")
    private  String Username;
    @SerializedName("ccontrasena")
    private  String Password;
    @SerializedName("ctokenaut")
    private  String AuthenticationToken;
    @SerializedName("ctokenrec")
    private  String RecoverToken;
    @SerializedName("ctokenapi")
    private  String ApiToken;
    @SerializedName("cultimoacc")
    private DateTime LastAccess;
    @SerializedName("cimagen")
    private  String Image;
    @SerializedName("ccorreo")
    private  String Mail;
    @SerializedName("cplataforma")
    private  Integer Platform;
    @SerializedName("cidultimomod")
    private  char FkLastModule;
    @SerializedName("cidultimoacc")
    private  char FkLastAccess;
    @SerializedName("concurrencia")
    private  DateTime Concurrence;
    @SerializedName("ccreacion")
    private  DateTime Creation;
    @SerializedName("cmodificacion")
    private  DateTime Upgrade;
    @SerializedName("cactivo")
    private boolean Active;


    public  void setId(String id){
        Id=id;
    }
    public  String getId(){
        return Id;
    }
    public void setFkRole(String fkrole){
        FkRole =fkrole;
    }
    public  String getFkRole(){
        return FkRole;
    }
    public void setName(String name){
        Name=name;
    }
    public  String getName(){
        return Name;
    }
    public  void setUsername(String username){
        Username= username;
    }
    public  String getUsername(){
        return Username;
    }
    public  void setPassword(String password){
        Password= password;
    }
    public String getPassword(){
        return Password;
    }
    public  void setAuthenticationToken(String authenticationtoken){
        AuthenticationToken= authenticationtoken;
    }
    public  String getAuthenticationToken(){
        return AuthenticationToken;
    }
    public  void setRecoverToken(String recovertoken){
        RecoverToken= recovertoken;
    }
    public String getRecoverToken(){
        return RecoverToken;
    }
    public  void setApiToken(String apitoken){
        ApiToken= apitoken;
    }
    public  String getApiToken(){
        return ApiToken;
    }
    public  void setLastAccess(DateTime lastaccess){
        LastAccess =lastaccess;
    }
    public  DateTime getLastAccess(){
        return LastAccess;
    }
    public  void setImage(String image){
        Image= image;
    }
    public  String getImage(){
        return Image;
    }
    public void setMail(String mail){
        Mail= mail;
    }
    public  String getMail(){
        return Mail;
    }
    public  void setPlatform(Integer platform){
        Platform= platform;
    }
    public  Integer getPlatform(){
        return Platform;
    }
    public  void setFkLastModule(char fklastmodule){
        FkLastModule = fklastmodule;
    }
    public  char getFkLastModule(){
        return FkLastModule;
    }
    public void setFkLastAccess(char fklastaccess){
        FkLastAccess= fklastaccess;
    }
    public  char getFkLastAccess(){
        return FkLastAccess;
    }
    public  void setConcurrence(DateTime concurrence){
        Concurrence= concurrence;
    }
    public  DateTime getConcurrence(){
        return Concurrence;
    }
    public  void setCreation(DateTime creation){
        Creation= creation;
    }
    public  DateTime getCreation(){
        return Creation;
    }
    public  void setUpgrade(DateTime upgrade){
        Upgrade= upgrade;
    }
    public  DateTime getUpgrade(){
        return Upgrade;
    }
    public  void setActive(boolean active){
        Active = active;
    }
    public  boolean getActive(){
        return  Active;
    }



}

