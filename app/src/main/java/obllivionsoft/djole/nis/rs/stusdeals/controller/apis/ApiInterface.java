package obllivionsoft.djole.nis.rs.stusdeals.controller.apis;




import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.DealCountRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.DemandRequestModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.EmailCheckModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.ForgotRequestModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.JobDetailRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.LoginRequestModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.ProviderDealRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.SignupRequestModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.SubCatDealsRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.SubCategoryRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.CategoryResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealCountResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealDetaillsModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DemandResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.EmailCheckResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ForgorRequestResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.GetImageResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.JobDetailsResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.JobResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.LoginResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.NearMeResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ProviderDealsModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ProviderResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.QrDealResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.RedeemApiResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.RedeemDealResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ReferralResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ReferralSuccessModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SearchResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SignupResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.StripePaymentResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.StuIDResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SubCatDealsResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SubCategoryResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.TestingDealsModels;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.VipDealsResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.VipResponseModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {


    @GET("categories")
    Call<CategoryResponseModel> getCategory();

    @GET("premiumOffers")
    Call<VipDealsResponse> getVipDeals();


    @GET
    Call<TestingDealsModels> allDeals(@Url String url);

    @GET
    Call<NearMeResponse> NearMeDeals(@Url String url);

    @GET
    Call<SearchResponseModel> getSerach(@Url String url);

    @GET("allJobs")
    Call<JobResponseModel> getJobs();
//>>>>>>>>parameter along with url>>>>>>>>>>>>>>>>>>>>>>>>>
    @GET
    Call<DealDetaillsModel> dealDetails(@Url String url);

    @GET
    Call<StuIDResponseModel> stuIdInfo(@Url String url,@Header("Content-Type") String content_type, @Header("Authorization") String authorization,@Header("access_token") String accessToken);

    @GET
    Call<RedeemApiResponse> logRedeem(@Url String url, @Header("Content-Type") String content_type, @Header("Authorization") String authorization, @Header("access_token") String accessToken);
    @GET
    Call<StripePaymentResponse> subscribe(@Url String url);

    @GET
    Call<QrDealResponse> getQrDeal(@Url String url);


    @GET
    Call<ReferralSuccessModel> postReferral(@Url String url);

    @GET
    Call<RedeemDealResponse> hitRedeemdata(@Url String url);

    @GET
    Call<VipResponseModel> checkVip(@Url String url);
    @GET("getReferrals")
    Call<ReferralResponseModel> getReferral();
//    @GET
//    Call<ProviderResponseModel> getProvider(@Url String url);
    @POST("studentSqid?")
    Call<GetImageResponse> uploadStuid (@Query("studentId") String token, @Body RequestBody requestBody);

    @GET("allProvider")
    Call<ProviderResponseModel> getProvider();
    @POST("create_gallary")
    Call<GetImageResponse> uploadimage(@Header("Content-Type") String content_type, @Header("Authorization") String authorization, @Body RequestBody requestBody);

    @POST("providerDetail")
    Call<ProviderDealsModel> getProviderDeals(@Header("Content-Type") String content_type,@Body ProviderDealRequest providerDealRequest);

    @POST("categoryDetail")
    Call<SubCategoryResponse> getSubCategory(@Header("Content-Type") String content_type, @Body SubCategoryRequest subCategoryRequest);

    @POST("countDealLimit")
    Call<DealCountResponse> flashDeal(@Body DealCountRequest dealCountRequest);


    @POST("emailVarification")
    Call<EmailCheckResponse> checkEmail(@Body EmailCheckModel emailCheckModel);

    @POST("subcategoryDetail")
    Call<SubCatDealsResponse> getSubCategoryDeals(@Header("Content-Type") String content_type, @Body SubCatDealsRequest subCatDealsRequest);

    @POST("jobDetail")
    Call<JobDetailsResponse> getJobDetails(@Header("Content-Type") String content_type, @Body JobDetailRequest jobDetailRequest);

    @POST("students/signin")
    Call<LoginResponseModel> loginUser(@Header("Content-Type") String content_type,@Header("Authorization") String authorization,@Header("access_token") String accessToken,@Body LoginRequestModel loginRequest);

    @POST("students/forgot")
    Call<ForgorRequestResponse> forgetPwd(@Header("Content-Type") String content_type, @Header("Authorization") String authorization, @Body ForgotRequestModel forgotRequestModel);

    @POST("demandDeal")
    Call<DemandResponseModel> demandDeal(@Header("Content-Type") String content_type, @Body DemandRequestModel demandRequestModel);

    @POST("students/signup")
    Call<SignupResponseModel> signUp(@Header("Content-Type") String content_type, @Header("Authorization") String authorization,@Header("access_token") String accessToken, @Body SignupRequestModel signupRequestModel);
//
//    @POST("customer_socialLogin")
//    Call<SocialResponseModel> loginsocial(@Body SocialRequestModel requestModel);
//
//    @GET("customer_logout")
//    Call<LogoutResponseModel> logout(@Header("Usertoken") String token);
//
//    @POST("customer_forgotPassword")
//    Call<ForgotResponse> forgotpwd(@Header("Usertoken") String token, @Body ForgotRequest forgotRequest);
//
//    @POST("customer_getProfile")
//    Call<GetProfileModel> getprofile(@Header("Usertoken") String token, @Body GetProfileRequest getProfileRequest);
//
//    @POST("customer_changePassword")
//    Call<ChangepwdResponse> changepwd(@Header("Usertoken") String token, @Body ChangePwdRequest changepwdResponse);
//
//    @POST("customer_updateProfile")
//    Call<UpdateProfileResponse> updateProfile(@Header("Usertoken") String token, @Body MultipartBody multipartBody);
//
//    @GET("get_category")
//    Call<GetCategoryResponse> getCategory();
//
//    @GET("getBannerData")
//    Call<BannerResponseModel> getBannerData();
//
//
//    @POST("fetchRestaurantDetail")
//    Call<FetchResponseModel> getRestaurants(@Header("Usertoken") String token, @Body FetchModelRequest fetchModelRequest);
//
//
//    @POST("customer_fetchRestaurant")
//    Call<FetchResponseModel> customer_fetchRestaurant(@Header("Usertoken") String token);
//
//
//    @POST("reviews")
//    Call<ReviewsResponseModel> getReviews(@Header("Usertoken") String token, @Body GetReviewRequest getReviewRequest);
//
//
//    @POST("filterfetchRestaurant")
//    Call<FetchResponseModel> filterRestaurants(@Header("Usertoken") String token, @Body FilterRequestModel filterRequestModel);
//
//    @POST("getRestaurantById")
//    Call<RestaurantDetailsResponse> getRestaurantsdetails(@Header("Usertoken") String token, @Body RestaurantDetailsRequest restaurantDetailsRequest);
//
//
//    @POST("customer_fetchItem")
//    Call<ResturantsMenuDetails> getRestaurantmenus(@Header("Usertoken") String token, @Body RestaurantDetailsRequest restaurantDetailsRequest);
//
//
//    @POST("add_order")
//    Call<CheckoutResponseModel> checkout(@Header("Usertoken") String token, @Body CheckoutRequestModel checkoutRequestModel);
//
//    @POST("get_past_order")
//    Call<PastOrderResponse> pastOrder(@Header("Usertoken") String token);
//
//    @POST("getReorderData")
//    Call<ReorderResponseModel> reOrder(@Header("Usertoken") String token, @Body ReorderRequestModel reorderRequestModel);
//
//
//
//    @POST("fetch_notification")
//    Call<NotificationResponseModel> fetchNotification(@Header("Usertoken") String token);
//
//
//    @POST("delete_notification")
//    Call<DeleteNotiResponse> deleteNotify(@Header("Usertoken") String token, @Body DeleteNotifyRequest deleteNotifyRequest);
//
//    @POST("deleteNotificationByCustomer")
//    Call<DeleteNotiResponse> deleteNotification(@Header("Usertoken") String token, @Body DeleteNotificationRequest deleteNotificationRequest);
//
//    @POST("get_upcoming_order")
//    Call<UpcomingResponseModel> upcomingOrder(@Header("Usertoken") String token);
//
//    @POST("get_orderDetail")
//    Call<SingleOrderResponse> getSingleorder(@Header("Usertoken") String token, @Body SingleOrderRequest singleOrderRequest);
//
//    @POST("add_review")
//    Call<AddReviewResponse> addReview(@Header("Usertoken") String token, @Body AddReviewRequest addReviewRequest);
//
//    @POST("cancel_order")
//    Call<CancelOrderResponse> cancelOrder(@Header("Usertoken") String token, @Body SingleOrderRequest singleOrderRequest);
//
//    @POST("Received_order")
//    Call<CancelOrderResponse> recieveOrder(@Header("Usertoken") String token, @Body SingleOrderRequest singleOrderRequest);
//
//
//    @GET("deleteCustomerAccount")
//    Call<DeleteAccountModel> deleteAccount(@Header("Usertoken") String token);




}