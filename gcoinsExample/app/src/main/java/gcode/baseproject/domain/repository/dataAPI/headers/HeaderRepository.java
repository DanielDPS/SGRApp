package gcode.baseproject.domain.repository.dataAPI.headers;

import java.util.List;

import gcode.baseproject.domain.model.dataAPI.headers.Header;
import gcode.baseproject.domain.model.dataAPI.headers.HeaderResponse;
import gcode.baseproject.interactors.api.NetworkManager;
import gcode.baseproject.interactors.api.data.headers.HeaderService;
import gcode.baseproject.interactors.api.format.FormatService;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class HeaderRepository implements  IHeaderRepository{

    private  HeaderService headerService;
    public  HeaderRepository(){
         headerService=  NetworkManager.getInstance().create(HeaderService.class);
    }


    @Override
    public Single<List<Header>> getHeaderLis(String token, String lastUpdate, String idFormat) {
        return headerService.getHeaders(token,lastUpdate,idFormat)
                .map(new Function<HeaderResponse, List<Header>>() {
                    @Override
                    public List<Header> apply(HeaderResponse headerResponse) throws Exception {
                        return headerResponse.getListHeaders();
                    }
                });
    }
}
