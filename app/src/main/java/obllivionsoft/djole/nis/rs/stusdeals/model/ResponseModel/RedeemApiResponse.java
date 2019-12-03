package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class RedeemApiResponse {
    @Expose
    @SerializedName("data")
    public Data data;

    public static class Data {
        @Expose
        @SerializedName("id")
        public String id;
        @Expose
        @SerializedName("paymentInstrumentType")
        public String paymentInstrumentType;
        @Expose
        @SerializedName("_student")
        public String _student;
        @Expose
        @SerializedName("_deal")
        public String _deal;
    }
}
