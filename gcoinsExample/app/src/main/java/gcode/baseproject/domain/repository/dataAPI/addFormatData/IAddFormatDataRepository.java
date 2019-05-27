package gcode.baseproject.domain.repository.dataAPI.addFormatData;

import java.util.List;

import gcode.baseproject.domain.model.dataAPI.addFile.AddFileResponse;
import gcode.baseproject.domain.model.dataAPI.addFormatData.FormatDataResponse;
import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeader;
import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeaderResponse;
import gcode.baseproject.domain.model.pdf.Format;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import io.reactivex.Single;

public interface IAddFormatDataRepository {
    Single<FormatDataResponse> addFormatData(String token, String idFormatData, String idFormat, String idCustomer, String lastUpdate, String identifier, String initialDate, String endDate, int status);
    void UpdateFormat(int s1,int s2,String enddate,String id);
    List<FileDataEntity> getFilesByFkFormatData(String idFormatData);
    Single<AddFileResponse> SendFileOne(String token, FileDataEntity file, String encodedPath, int status);
    Single<AddFileResponse> SendFileTwo(String token, FileDataEntity file, String encodedPath, int status);
    Single<AddFileResponse> SendFileThree(String token, FileDataEntity file, String encodedPath, int status);
    Single<List<FileHeader>> getFilesHeadersAPI(String token, String id);
}
