package paymentint;

import com.phonepe.sdk.pg.Env;
import com.phonepe.sdk.pg.payments.v1.PhonePePaymentClient;
import com.phonepe.sdk.pg.common.http.PhonePeResponse;
import com.phonepe.sdk.pg.payments.v1.models.request.PgPayRequest;
import com.phonepe.sdk.pg.payments.v1.models.response.PayPageInstrumentResponse;
import com.phonepe.sdk.pg.payments.v1.models.response.PgPayResponse;
import java.util.UUID;
import com.phonepe.sdk.pg.common.http.PhonePeResponse;
import com.phonepe.sdk.pg.payments.v1.models.response.PgPaymentInstrument;
import com.phonepe.sdk.pg.payments.v1.models.response.PgTransactionStatusResponse;
import com.phonepe.sdk.pg.payments.v1.models.response.UPIPaymentInstrumentResponse;

public class PI {

    private String merchantId = "PGTESTPAYUAT";
    private String saltKey = "099eb0cd-02cf-4e2a-8aca-3e6c6aff0399";
    private Integer saltIndex = 1;
    private Env env = Env.UAT.SIMULATOR;
    private boolean shouldPublishEvents = false;
//    String merchantTransactionId = UUID.randomUUID().toString().substring(0,34);
    String merchantTransactionId = "MT7850590068188104";
    private long amount=100;
    private String merchantUserId="MUID123";
    private String callbackurl="https://webhook.site/callback-url";

    // Instantiate PhonePayClient
    private PhonePePaymentClient phonepeClient = new PhonePePaymentClient(merchantId, saltKey, saltIndex, env, shouldPublishEvents);

    // redirect link got
    private String redirect_link = "http://localhost:8080/api/user/initiate-payment-status/MT7850590068188104";
    public PI(){
        System.out.println("PI is called");
    }

    public String initiate(){

        PgPayRequest pgPayRequest=PgPayRequest.PayPagePayRequestBuilder()
                .amount(amount)
                .merchantId(merchantId)
                .merchantTransactionId(merchantTransactionId)
                .callbackUrl(callbackurl)
                .merchantUserId(merchantUserId)
                .build();

        PhonePeResponse<PgPayResponse> payResponse=phonepeClient.pay(pgPayRequest);
        PayPageInstrumentResponse payPageInstrumentResponse=(PayPageInstrumentResponse)payResponse.getData().getInstrumentResponse();
        String url=payPageInstrumentResponse.getRedirectInfo().getUrl();
        System.out.println(payResponse);
        return url;
    }

    public void XVerify(){
        String xVerify = "a005532637c6a6e4a4b08ebc6f1144384353305a9cd253d995067964427cd0bb###1";
        String response = "{\"response\":\"eyJzdWNjZXNzIjpmYWxzZSwiY29kZSI6IlBBWU1FTlRfRVJST1IiLCJtZXNzYWdlIjoiUGF5bWVudCBGYWlsZWQiLCJkYXRhIjp7Im1lcmNoYW50SWQiOiJtZXJjaGFudElkIiwibWVyY2hhbnRUcmFuc2FjdGlvbklkIjoibWVyY2hhbnRUcmFuc2FjdGlvbklkIiwidHJhbnNhY3Rpb25JZCI6IkZUWDIzMDYwMTE1NDMxOTU3MTYzMjM5IiwiYW1vdW50IjoxMDAsInN0YXRlIjoiRkFJTEVEIiwicmVzcG9uc2VDb2RlIjoiUkVRVUVTVF9ERUNMSU5FX0JZX1JFUVVFU1RFRSIsInBheW1lbnRJbnN0cnVtZW50IjpudWxsfX0=\"}";

        boolean value = phonepeClient.verifyResponse(xVerify,response);
        if(value) System.out.println("XVerification succeded");
        else System.out.println("XVerification Failed");
    }

    public PhonePeResponse<PgTransactionStatusResponse> Status(){
        PhonePeResponse<PgTransactionStatusResponse> statusResponse=phonepeClient.checkStatus(merchantTransactionId);
        PgPaymentInstrument pgPaymentInstrument=statusResponse.getData().getPaymentInstrument();
        final UPIPaymentInstrumentResponse upiPaymentInstrumentResponse=(UPIPaymentInstrumentResponse)pgPaymentInstrument;
        String utr=upiPaymentInstrumentResponse.getUtr();
        String ifsc=upiPaymentInstrumentResponse.getIfsc();
//        System.out.println(utr);
//        System.out.println(ifsc);
//        System.out.println(statusResponse);
        return statusResponse;
//        this.XVerify(statusResponse);
    }

}

/*

phone pe redirect automatically after payment completion
http://127.0.0.1:8000/phonepe-response

 */