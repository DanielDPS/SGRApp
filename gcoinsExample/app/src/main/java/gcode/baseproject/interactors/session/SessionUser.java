package gcode.baseproject.interactors.session;

import gcode.baseproject.domain.model.account.Account;

public class SessionUser {

    private  static SessionUser _Instance= null;
    private  Account account;
    public static  SessionUser getInstance(){
        if (_Instance ==null)
            _Instance= new SessionUser();
        return _Instance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
