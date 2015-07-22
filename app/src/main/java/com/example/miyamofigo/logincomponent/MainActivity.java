package com.example.miyamofigo.logincomponent;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends Activity {
    private AccountManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = AccountManager.get(getApplicationContext());
        Account[] accounts = manager.getAccounts();

        Account user = null;
        for(Account account : accounts) {
            if (account.type.equals(LoginActivity.ACCOUNT_TYPE)) {
                user = account;
            }
        }

        if (user == null) {
            manager.addAccount(
                    LoginActivity.ACCOUNT_TYPE,
                    null,
                    null,
                    null,
                    this,
                    null,
                    null);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        Account[] accounts = manager.getAccountsByType(LoginActivity.ACCOUNT_TYPE);
        if (Build.VERSION.SDK_INT < 22)
            for(Account account : accounts)
                manager.removeAccount(account,
                                       new AccountManagerCallback<Boolean>() {
                                           @Override
                                           public void run(AccountManagerFuture<Boolean> future) {}
                                       },
                                       null);
        else
            for(Account account : accounts)
                manager.removeAccountExplicitly(account);
        super.onDestroy();
    }
}
