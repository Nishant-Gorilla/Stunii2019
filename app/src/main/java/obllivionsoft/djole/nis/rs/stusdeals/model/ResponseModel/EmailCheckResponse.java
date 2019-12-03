package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class EmailCheckResponse {
    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("success")
    public boolean success;
}
