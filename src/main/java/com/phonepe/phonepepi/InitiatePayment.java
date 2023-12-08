package com.phonepe.phonepepi;

import com.phonepe.sdk.pg.Env;
import com.phonepe.sdk.pg.payments.v1.PhonePePaymentClient;
import com.phonepe.sdk.pg.common.http.PhonePeResponse;
import com.phonepe.sdk.pg.payments.v1.models.request.PgPayRequest;
import com.phonepe.sdk.pg.payments.v1.models.response.*;
import org.springframework.stereotype.Component;
import com.phonepe.sdk.pg.payments.v1.models.response.PgPaymentInstrument;
import com.phonepe.sdk.pg.payments.v1.models.response.PgTransactionStatusResponse;
import com.phonepe.sdk.pg.payments.v1.models.response.UPIPaymentInstrumentResponse;

import javax.ws.rs.POST;
import java.lang.reflect.Method;

@Component
public class InitiatePayment {

    private String merchantId = "PGTESTPAYUAT";
    private String saltKey = "099eb0cd-02cf-4e2a-8aca-3e6c6aff0399";
    private Integer saltIndex = 1;
    private Env env = Env.UAT.SIMULATOR;
    private boolean shouldPublishEvents = false;

    private PhonePePaymentClient phonepeClient;



    String merchantTransactionId = "MT7850590068188104";
    private String merchantUserId="MUID123";
    private String callbackurl="https://phonepe-integration.onrender.com/payment-callback/"+merchantId+"/"+merchantTransactionId;
    private String redirectUrl = "https://phonepe-integration.onrender.com/payment-callback/"+merchantId+"/"+merchantTransactionId;

    public String initiate(int amount){
        phonepeClient = new PhonePePaymentClient(merchantId, saltKey, saltIndex, env, shouldPublishEvents);
        PgPayRequest pgPayRequest=PgPayRequest.PayPagePayRequestBuilder()
                .amount(amount)
                .merchantId(merchantId)
                .merchantTransactionId(merchantTransactionId)
                .callbackUrl(callbackurl)
                .merchantUserId(merchantUserId)
                .redirectUrl(redirectUrl)
                .redirectMode("POST")
                .build();

        PhonePeResponse<PgPayResponse> payResponse=phonepeClient.pay(pgPayRequest);
        PayPageInstrumentResponse payPageInstrumentResponse=(PayPageInstrumentResponse)payResponse.getData().getInstrumentResponse();
        String url=payPageInstrumentResponse.getRedirectInfo().getUrl();
        return url;
    }

    String getStatus(){
        PhonePeResponse<PgTransactionStatusResponse> statusResponse=phonepeClient.checkStatus(merchantTransactionId);
        PgPaymentInstrument pgPaymentInstrument=statusResponse.getData().getPaymentInstrument();
        String res = statusResponse.getMessage();
        String resCode = statusResponse.getCode();
        System.out.println(statusResponse);
        return res +" :: "+ resCode +" :: "+ statusResponse.getSuccess();
    }
}
