package com.example.miyamofigo.logincomponent;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;

public class Authenticator extends AbstractAccountAuthenticator {

    final Context mContext;

    public Authenticator(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Bundle editProperties(
            AccountAuthenticatorResponse response,
            String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(
            AccountAuthenticatorResponse response,
            String accountType,
            String authTokenType,
            String[] requiredFeatures,
            Bundle options) throws NetworkErrorException {

        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
                        response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(
            AccountAuthenticatorResponse response,
            Account account,
            Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(
            AccountAuthenticatorResponse response,
            Account account,
            String authTokenType,
            Bundle options) throws NetworkErrorException {

        //TODO: You have to check the token type before token acquisition.
        final AccountManager manager = AccountManager.get(mContext);
        String authToken = manager.peekAuthToken(account, authTokenType);

        //if (authToken == null | authToken.isEmpty()) {
        // API 9 or later is required for String/isEmpty method.
        if (authToken == null | authToken.length() == 0) {
            final String password = manager.getPassword(account);
            if (password != null) {
                // dummy token acquisition
                //TODO: You have to implement server connection to get a token.
                authToken = "dummy";
            }
        }

        Bundle result = new Bundle();
        //if(authToken != null && !authToken.isEmpty()) {
        // API 9 or later is required for String/isEmpty method.
        if (authToken != null && authToken.length() != 0) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        } else {
           //TODO: In the case User input for authentication is required,
           //       inflate login activity and then, set KEY_INT to the return Bundle.
        }

        return result;
    }


    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }


    @Override
    public Bundle updateCredentials(
            AccountAuthenticatorResponse response,
            Account account,
            String authTokenType,
            Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(
            AccountAuthenticatorResponse response,
            Account account,
            String[] features) throws NetworkErrorException {
        return null;
    }
}
