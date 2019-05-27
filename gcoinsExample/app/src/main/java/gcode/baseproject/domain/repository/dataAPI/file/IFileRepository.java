package gcode.baseproject.domain.repository.dataAPI.file;

import gcode.baseproject.domain.model.dataAPI.file.FileResponse;
import io.reactivex.Single;

public interface IFileRepository {
    Single<String> getFileById(String token,String idFormatData);
}
