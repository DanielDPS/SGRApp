package gcode.baseproject.domain.repository.dataAPI.addFormatData;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeader;
import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeaderResponse;
import gcode.baseproject.interactors.api.data.fileHeaders.FileHeaderService;

public class GetFileHeadersFromApiAsyncTask extends AsyncTask<Void,Void, List<FileHeaderResponse>> {

    private FileHeaderService fileHeaderService;
    private  String token;
    private  String idFormatData;
    private  List<FileHeaderResponse> response;
    public  GetFileHeadersFromApiAsyncTask(FileHeaderService service,String token,String id){
        this.fileHeaderService =service;
        this.token=token;
        this.idFormatData=id;
    }
    public  List<FileHeaderResponse> getResponse(){
        return  response;
    }
    @Override
    protected List<FileHeaderResponse> doInBackground(Void... voids) {
        return response = fileHeaderService.getFileHeaderFromAPI(token,idFormatData);
    }
}
