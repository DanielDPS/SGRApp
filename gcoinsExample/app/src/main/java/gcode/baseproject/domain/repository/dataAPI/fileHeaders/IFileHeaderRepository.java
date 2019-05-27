package gcode.baseproject.domain.repository.dataAPI.fileHeaders;

import java.util.List;

import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeader;
import io.reactivex.Single;

public interface IFileHeaderRepository {
    Single<List<FileHeader>> getFileHeaders(String token,String idFormatData);
}
