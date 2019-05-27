package gcode.baseproject.domain.repository.dataAPI.addFile;

import gcode.baseproject.domain.model.dataAPI.addFile.AddFileResponse;
import gcode.baseproject.domain.model.pdf.Format;
import gcode.baseproject.interactors.api.NetworkManager;
import gcode.baseproject.interactors.api.data.addFile.AddFileService;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class AddFileRepository implements  IAddFileRepository {

    private AddFileService addFileService;
    public  AddFileRepository(){
        addFileService= NetworkManager.getInstance().create(AddFileService.class);
    }


    @Override
    public Single<String> addFilePdf(String token, FormatDataEntity formatData, FileDataEntity file, String encodedFile) {
        return addFileService.addFile(token,file.getFkFormatData(),formatData.getEstado02(),file.getType(),encodedFile)
                .map(new Function<AddFileResponse, String>() {
                    @Override
                    public String apply(AddFileResponse addFileResponse) throws Exception {
                        return addFileResponse.getEncodedPathMessage();
                    }
                });
    }
}
