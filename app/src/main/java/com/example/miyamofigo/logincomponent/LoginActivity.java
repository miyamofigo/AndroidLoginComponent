package com.example.miyamofigo.logincomponent;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AccountAuthenticatorActivity {

   final static String ACCOUNT_TYPE = "miyamofigo.example.com";
   final static String PARAM_NAME = "username_for_login";
   final static String PARAM_AUTHTOKEN = "token_for_login";
   final static String PARAM_PASSWORD = "password_for_login";
   final static String PARAM_AUTHTOKEN_TYPE = "logincomponent";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);

      final EditText nameEdit = (EditText) findViewById(R.id.username);
      final EditText passwordEdit = (EditText) findViewById(R.id.password);
      Button loginBotton = (Button) findViewById(R.id.loginbutton);
      loginBotton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            String username = nameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            submit(username, password);
         }
      });
   }

   public void submit(final String username, final String password) {

      new AsyncTask<Void, Void, Intent>(){
         @Override
         protected Intent doInBackground(Void... params) {
            // dummy token acquisition
            //TODO: You have to implement server connection to get a token.
            final String authToken = "dummy";

            //final Intent res = new Intent();
            final Intent res = new Intent(LoginActivity.this, MainActivity.class);
            res.putExtra(PARAM_NAME, username);
            res.putExtra(PARAM_AUTHTOKEN, authToken);
            res.putExtra(PARAM_PASSWORD, password);

            return res;
         }

         @Override
         protected void onPostExecute(Intent intent) {
            final String accountName = intent.getStringExtra(PARAM_NAME);
            final String accountPassword = intent.getStringExtra(PARAM_PASSWORD);
            final String authToken = intent.getStringExtra(PARAM_AUTHTOKEN);
            final Account account = new Account(accountName, ACCOUNT_TYPE);

            // To check if the account is new or not in some way.
            // TODO: A way of check the account has to be implemented.
            //        For example, it is to pass a boolean parameter when this activity launched.
            final boolean newAccount = true;
            AccountManager manager = AccountManager.get(LoginActivity.this.getApplicationContext());
            if (newAccount) {
               manager.addAccountExplicitly(account, accountPassword, null);
               manager.setAuthToken(account, PARAM_AUTHTOKEN_TYPE, authToken);
            } else {
               manager.setPassword(account, accountPassword);
            }

            /* final Bundle bundle = new Bundle();
            bundle.putString(AccountManager.KEY_ACCOUNT_NAME, accountName);
            bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
            bundle.putString(AccountManager.KEY_AUTHTOKEN, authToken);

            setAccountAuthenticatorResult(bundle);
            setResult(RESULT_OK, intent); */
            startActivity(intent);
            finish();
         }
      }.execute();
   }
}