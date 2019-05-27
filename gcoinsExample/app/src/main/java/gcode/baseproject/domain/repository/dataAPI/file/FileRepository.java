package gcode.baseproject.domain.repository.dataAPI.file;

import gcode.baseproject.domain.model.dataAPI.file.FileResponse;
import gcode.baseproject.interactors.api.NetworkManager;
import gcode.baseproject.interactors.api.data.file.FileService;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class FileRepository implements   IFileRepository {

    private FileService fileService;
    public  FileRepository(){
        fileService = NetworkManager.getInstance().create(FileService.class);
    }

    @Override
    public Single<String> getFileById(String token, String idFormatData) {
        return fileService.getFileByIdFormatData(token,idFormatData)
                .map(new Function<FileResponse, String>() {
                    @Override
                    public String apply(FileResponse fileResponse) throws Exception {
                        return fileResponse.getEncodedPath();
                    }
                });
    }
}
