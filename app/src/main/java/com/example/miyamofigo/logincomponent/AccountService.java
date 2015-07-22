package com.example.miyamofigo.logincomponent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountService extends Service {

    private Authenticator authenticator;

    @Override
    public void onCreate(){
       this.authenticator = new Authenticator(this) ;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.authenticator.getIBinder();
    }
}
