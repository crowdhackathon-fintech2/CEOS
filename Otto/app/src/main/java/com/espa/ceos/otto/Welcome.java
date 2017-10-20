package com.espa.ceos.otto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.BuyButtonText;
import com.google.android.gms.wallet.fragment.Dimension;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentState;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.simplify.android.sdk.Card;
import com.simplify.android.sdk.CardToken;
import com.simplify.android.sdk.Simplify;


//Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();

//        Intent intent = new Intent(getApplicationContext(), Nfc.class);
//        startActivity(intent);

public class Welcome extends AppCompatActivity  {
    Simplify simplify;

    private GoogleApiClient mGoogleApiClient;
    private SupportWalletFragment mWalletFragment;
    private SupportWalletFragment mXmlWalletFragment;
    private MaskedWallet mMaskedWallet;

    private static final int MASKET_WALLET_REQUEST_CODE = 888;
    public static final int FULL_WALLET_REQUEST_CODE = 889;
    public static String WALLET_FRAGMENT_ID="wallet_fragment";

    private double cartTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        simplify = new Simplify();
        simplify.setApiKey("sbpb_OWNjOWUxY2QtMzZlMy00ZDNhLWE3OTctM2M4N2M0ZTI4YTc2");
        Card card = new Card()
                .setNumber("")
                .setExpMonth("07")
                .setExpYear("18")
                .setCvc("");
        simplify.createCardToken(card, new CardToken.Callback() {
            @Override
            public void onSuccess(CardToken cardToken) {
                Toast.makeText(getApplicationContext(), "Success.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();


            }
        });
        mWalletFragment=(SupportWalletFragment) getSupportFragmentManager().findFragmentByTag(WALLET_FRAGMENT_ID);
        WalletFragmentInitParams startParams;
        WalletFragmentInitParams.Builder startParamsBuilder=WalletFragmentInitParams.newBuilder().setMaskedWalletRequest(generateMaskedWalletRequest()).setMaskedWalletRequestCode(MASKET_WALLET_REQUEST_CODE);
        startParams=startParamsBuilder.build();
        if (mWalletFragment==null){
            WalletFragmentStyle walletFragmentStyle=new WalletFragmentStyle().setBuyButtonText(BuyButtonText.BUY_WITH_GOOGLE).setBuyButtonWidth(Dimension.MATCH_PARENT);
            WalletFragmentOptions walletFragmentOptions=WalletFragmentOptions.newBuilder().setEnvironment(WalletConstants.ENVIRONMENT_SANDBOX).setFragmentStyle(walletFragmentStyle).setMode(WalletFragmentMode.BUY_BUTTON).build();
            mWalletFragment=SupportWalletFragment.newInstance(walletFragmentOptions);
            mWalletFragment.initialize(startParams);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.wallet_button_holder,mWalletFragment,WALLET_FRAGMENT_ID).commit();
        mGoogleApiClient=new GoogleApiClient.Builder(this).addConnectionCallbacks
    }


}
