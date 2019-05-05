package ir.markazandroid.UniEngine.object;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;

import java.io.Serializable;

/**
 * Created by Ali on 2/18/2019.
 */
@JSON
public class GhasedakResponse implements Serializable {
    /*{
        "ResultCode": 0,
            "Note": "تراکنش با موفقیت انجام شد",
            "TransactionID": 0,
            "Amount": 0,
            "MobileNumber": "",
            "TransactionTime": "2/16/2019 3:36:55 PM",
            "Creadit": 20200
    }*/

    private int resultCode;
    private String note;
    private long transactionID;
    private long amount;
    private String mobileNumber;
    private String transactionTime;
    private long credit;
    private String url;

    @JSON(name = "ResultCode")
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @JSON(name = "Note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @JSON(name = "TransactionID")
    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    @JSON(name = "Amount")
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @JSON(name = "MobileNumber")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @JSON(name = "TransactionTime")
    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    @JSON(name = "Creadit")
    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    @JSON(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
