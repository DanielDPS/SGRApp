package gcode.baseproject.domain.repository.dataAPI.fileHeaders;

import java.util.List;

import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeader;
import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeaderResponse;
import gcode.baseproject.interactors.api.NetworkManager;
import gcode.baseproject.interactors.api.data.fileHeaders.FileHeaderService;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class FileHeaderRepository implements  IFileHeaderRepository {


    private FileHeaderService fileHeaderService ;
    public  FileHeaderRepository(){
        fileHeaderService = NetworkManager.getInstance().create(FileHeaderService.class);
    }
    @Override
    public Single<List<FileHeader>> getFileHeaders(String token, String idFormatData) {
        return fileHeaderService.getFileHeaders(token,idFormatData)
                .map(new Function<FileHeaderResponse, List<FileHeader>>() {
                    @Override
                    public List<FileHeader> apply(FileHeaderResponse fileHeaderResponse) throws Exception {
                        return fileHeaderResponse.getListFileHeaders();
                    }
                });
    }
}
