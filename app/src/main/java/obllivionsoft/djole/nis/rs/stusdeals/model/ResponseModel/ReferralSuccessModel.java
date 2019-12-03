package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class ReferralSuccessModel {

    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("status")
    public String status;
}
