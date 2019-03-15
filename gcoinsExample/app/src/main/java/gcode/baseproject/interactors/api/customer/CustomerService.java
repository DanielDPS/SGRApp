package gcode.baseproject.interactors.api.customer;

import gcode.baseproject.domain.model.customer.CustomerIdentifierResponse;
import gcode.baseproject.domain.model.customer.CustomerResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import io.reactivex.Single;
import retrofit2.http.POST;

public interface CustomerService {

    // FormUrlEncoded solo debe de ir cuando se manda uno o mas paramteros que no sean la cabezera
    // Los parametors del tipo @Field("Id") String id;
    @POST("clientes/get_all")
    @FormUrlEncoded
    Single<CustomerResponse> getCustomers(@Header("authorization") String authToken, @Field("last_update") String lastUpdate);

    @POST("datos_formatos/get_identificator")
    @FormUrlEncoded
    Single<CustomerIdentifierResponse> getCustomerIdentifier(@Header("authorization") String authToken, @Field("id_customer") String id);

}
