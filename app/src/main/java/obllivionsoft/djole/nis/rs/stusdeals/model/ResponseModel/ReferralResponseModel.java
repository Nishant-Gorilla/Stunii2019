package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class ReferralResponseModel {
    @Expose
    @SerializedName("from")
    public List<From> from;
    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("status")
    public int status;

    public static class From {
        @Expose
        @SerializedName("__v")
        public int __v;
        @Expose
        @SerializedName("referral_from")
        public String referral_from;
        @Expose
        @SerializedName("created_at")
        public String created_at;
        @Expose
        @SerializedName("updated_at")
        public String updated_at;
        @Expose
        @SerializedName("_id")
        public String _id;
    }
}
