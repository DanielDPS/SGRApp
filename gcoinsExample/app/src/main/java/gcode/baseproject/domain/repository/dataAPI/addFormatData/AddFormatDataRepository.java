package gcode.baseproject.domain.repository.dataAPI.addFormatData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import gcode.baseproject.domain.model.dataAPI.addFile.AddFileResponse;
import gcode.baseproject.domain.model.dataAPI.addFormatData.FormatDataResponse;
import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeader;
import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeaderResponse;
import gcode.baseproject.domain.model.pdf.Format;
import gcode.baseproject.domain.repository.dataFile.GetFilesByFkFormatDataAsyncTask;
import gcode.baseproject.interactors.api.NetworkManager;
import gcode.baseproject.interactors.api.data.addFile.AddFileService;
import gcode.baseproject.interactors.api.data.addFormatData.FormatDataService;
import gcode.baseproject.interactors.api.data.fileHeaders.FileHeaderService;
import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.data.FileDataDao;
import gcode.baseproject.interactors.db.dao.data.FormatDataDao;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import gcode.baseproject.view.viewmodel.dataAPI.addFormatData.AddFormatDataViewModel;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class AddFormatDataRepository implements  IAddFormatDataRepository {


    private FormatDataService formatDataService;
    private FormatDataDao formatDataDao;
    private FileDataDao fileDataDao;
    private AddFileService addFileService;
    private  FileHeaderService fileHeaderService;
    public  AddFormatDataRepository(){
        formatDataService= NetworkManager.getInstance().create(FormatDataService.class);
        formatDataDao = ApplicationDatabase.getDatabase().getFormatDataDao();
        fileDataDao= ApplicationDatabase.getDatabase().getFileDataDao();
        addFileService= NetworkManager.getInstance().create(AddFileService.class);
        fileHeaderService = NetworkManager.getInstance().create(FileHeaderService.class);
    }

    @Override
    public Single<FormatDataResponse> addFormatData(String token, String idFormatData, String idFormat, String idCustomer, String lastUpdate, String identifier, String initialDate, String endDate, int status) {
        return formatDataService.addFormatData(token,idFormatData,idFormat,idCustomer,lastUpdate,identifier,initialDate,endDate,status)
                .map(new Function<FormatDataResponse, FormatDataResponse>() {
                    @Override
                    public FormatDataResponse apply(FormatDataResponse formatDataResponse) throws Exception {
                        return formatDataResponse;
                    }
                });
    }

    @Override
    public void UpdateFormat(int s1, int s2, String enddate, String id) {
        UpdateFormatDataAsyncTask task = new UpdateFormatDataAsyncTask(formatDataDao,s1,s2,enddate,id);
        task.execute();
    }

    @Override
    public List<FileDataEntity> getFilesByFkFormatData(String idFormatData) {
        GetFilesByFkFormatDataAsyncTask task= new GetFilesByFkFormatDataAsyncTask(fileDataDao,idFormatData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getFiles();
    }

    @Override
    public Single<AddFileResponse> SendFileOne(String token, FileDataEntity file, String encodedPath, int status) {
        return addFileService.addFile(token,file.getFkFormatData(),status,file.getType(),encodedPath)
                .map(new Function<AddFileResponse, AddFileResponse>() {
                    @Override
                    public AddFileResponse apply(AddFileResponse addFileResponse) throws Exception {
                        return  addFileResponse;
                    }
                });
    }

    @Override
    public Single<AddFileResponse> SendFileTwo(String token, FileDataEntity file, String encodedPath, int status) {
        return addFileService.addFile(token,file.getFkFormatData(),status,file.getType(),encodedPath)
                .map(new Function<AddFileResponse, AddFileResponse>() {
                    @Override
                    public AddFileResponse apply(AddFileResponse addFileResponse) throws Exception {
                        return  addFileResponse;

                    }
                });
    }

    @Override
    public Single<AddFileResponse> SendFileThree(String token, FileDataEntity file, String encodedPath, int status) {
        return addFileService.addFile(token,file.getFkFormatData(),status,file.getType(),encodedPath)
                .map(new Function<AddFileResponse, AddFileResponse>() {
                    @Override
                    public AddFileResponse apply(AddFileResponse addFileResponse) throws Exception {
                        return  addFileResponse;
                    }
                });
    }

    @Override
    public  Single<List<FileHeader>> getFilesHeadersAPI(String token, String id) {

        return  fileHeaderService.getFileHeaders(token,id)
                .map(new Function<FileHeaderResponse, List<FileHeader>>() {
                    @Override
                    public List<FileHeader> apply(FileHeaderResponse fileHeaderResponse) throws Exception {
                        return fileHeaderResponse.getListFileHeaders();
                    }
                });
    }


}
