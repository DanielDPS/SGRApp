package gcode.baseproject.interactors.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import gcode.baseproject.domain.model.data.EvidenceData;
import gcode.baseproject.domain.model.format.FormatData;
import gcode.baseproject.domain.model.question.Question;
import gcode.baseproject.interactors.db.dao.CustomerDao;
import gcode.baseproject.interactors.db.dao.FormatDao;
import gcode.baseproject.interactors.db.dao.FormatSectionDao;
import gcode.baseproject.interactors.db.dao.OptionDao;
import gcode.baseproject.interactors.db.dao.QuestionDao;
import gcode.baseproject.interactors.db.dao.TokenDao;
import gcode.baseproject.interactors.db.dao.data.EvidenceDataDao;
import gcode.baseproject.interactors.db.dao.data.FormatDataDao;
import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.dao.data.SectionDataDao;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.interactors.db.entities.TokenEntity;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;

@Database(
        entities = {
        TokenEntity.class,CustomerEntity.class,
                FormatEntity.class, FormatSectionEntity.class, QuestionEntity.class,
                OptionEntity.class, SectionDataEntity.class, FormatDataEntity.class, EvidenceDataEntity.class, QuestionDataEntity.class},
        version = 1,
        exportSchema = false)

public abstract class ApplicationDatabase extends RoomDatabase {

    private static ApplicationDatabase mInstance;

    private static final String DB_NAME = "gCoins2";

    public static ApplicationDatabase getDatabase() {
        return mInstance;
    }

    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (ApplicationDatabase.class) {
                mInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        ApplicationDatabase.class,
                        DB_NAME
                ).build();
            }
        }
    }
    public abstract TokenDao getTokenDao();
    public abstract  CustomerDao getCustomerDao();
    public  abstract FormatDao getFormatDao();
    public abstract FormatSectionDao getFormatSectionDao();
    public  abstract QuestionDao getQuestionDao();
    public abstract OptionDao getOptionDao();
    public  abstract SectionDataDao getSectionDataDao();
    public abstract FormatDataDao getFormatDataDao();
    public abstract EvidenceDataDao getEvidenceDataDao();
    public  abstract QuestionDataDao getQuestionDataDao();
}
