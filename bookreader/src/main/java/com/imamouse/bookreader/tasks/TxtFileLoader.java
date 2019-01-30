package com.imamouse.bookreader.tasks;

import com.imamouse.bookreader.bean.FileReadRecordBean;
import com.imamouse.bookreader.bean.TxtFileMsg;
import com.imamouse.bookreader.bean.TxtMsg;
import com.imamouse.bookreader.interfaces.ILoadListener;
import com.imamouse.bookreader.interfaces.ITxtTask;
import com.imamouse.bookreader.main.FileReadRecordDB;
import com.imamouse.bookreader.main.TxtReaderContext;
import com.imamouse.bookreader.utils.ELogger;
import com.imamouse.bookreader.utils.FileCharsetDetector;
import com.imamouse.bookreader.utils.FileHashUtil;
import com.imamouse.bookreader.utils.FileUtil;

import java.io.File;


/**
 * created by ： bifan-wei
 */

public class TxtFileLoader {
    private String tag = "TxtFileLoader";

    public void load(String filePath, TxtReaderContext readerContext, ILoadListener loadListener) {
        load(filePath, null, readerContext, loadListener);
    }

    public void load(String filePath, String fileName, TxtReaderContext readerContext, ILoadListener loadListener) {
        if (!FileUtil.FileExist(filePath)) {
            loadListener.onFail(TxtMsg.FileNoExist);
            return;
        }
        loadListener.onMessage("initFile start");
        initFile(filePath, fileName, readerContext);
        ELogger.log(tag, "initFile done");
        loadListener.onMessage("initFile done");
        ITxtTask txtTask = new FileDataLoadTask();
        txtTask.Run(loadListener, readerContext);

    }

    private void initFile(String filePath, String fileName, TxtReaderContext readerContext) {
        File file = new File(filePath);
        TxtFileMsg fileMsg = new TxtFileMsg();
        fileMsg.FileSize = file.getTotalSpace();
        fileMsg.FilePath = filePath;
        fileMsg.FileCode = new FileCharsetDetector().getCharset(new File(filePath));

        fileMsg.CurrentParagraphIndex = 0;
        fileMsg.CurrentParagraphIndex = 0;
        fileMsg.PreParagraphIndex = 0;
        fileMsg.PreCharIndex = 0;
        if (fileName == null || fileName.trim().length() == 0) {
            fileName = file.getName();
        }
        fileMsg.FileName = fileName;

        //获取之前的播放进度记录
        FileReadRecordDB readRecordDB = new FileReadRecordDB(readerContext.getContext());
        readRecordDB.createTable();
        try {
            FileReadRecordBean r = readRecordDB.getRecordByHashName(FileHashUtil.getMD5Checksum(filePath));
            if (r != null) {
                fileMsg.PreParagraphIndex = r.paragraphIndex;
                fileMsg.PreCharIndex = r.chartIndex;
            }
        } catch (Exception e) {
        }
        readRecordDB.createTable();
        readerContext.setFileMsg(fileMsg);
    }
}
