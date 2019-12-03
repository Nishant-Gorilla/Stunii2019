package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.SerializedName;

public  class LoginResponseModel {
    @SerializedName("isVIP")
    public boolean isVIP;
    @SerializedName("phone_number")
    public String phone_number;
    @SerializedName("personal_email")
    public String personal_email;
    @SerializedName("institution")
    public String institution;
    @SerializedName("device_token")
    public String device_token;
    @SerializedName("access_token")
    public String access_token;
    @SerializedName("type")
    public String type;
    @SerializedName("photo")
    public String photo;
    @SerializedName("lname")
    public String lname;
    @SerializedName("fname")
    public String fname;
    @SerializedName("email")
    public String email;
    @SerializedName("_id")
    public String _id;
}
