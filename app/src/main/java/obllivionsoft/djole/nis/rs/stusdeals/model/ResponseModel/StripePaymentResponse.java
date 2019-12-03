package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.SerializedName;

public  class StripePaymentResponse {
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public String status;
}
