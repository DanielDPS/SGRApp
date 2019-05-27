package gcode.baseproject.domain.repository.dataAPI.addFile;

import gcode.baseproject.domain.model.pdf.Format;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import io.reactivex.Single;

public interface IAddFileRepository {
    Single<String>  addFilePdf(String token, FormatDataEntity formatData, FileDataEntity file, String encodedFile);
}
