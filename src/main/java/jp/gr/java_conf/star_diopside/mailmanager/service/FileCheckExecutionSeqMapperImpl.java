package jp.gr.java_conf.star_diopside.mailmanager.service;

import jp.gr.java_conf.star_diopside.mailmanager.dao.mapper.FileCheckResultMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * ファイルチェック実行シーケンスMapperクラス
 */
@Repository
public class FileCheckExecutionSeqMapperImpl implements FileCheckExecutionSeqMapper {

    /** ファイルチェック結果Mapper */
    @Autowired
    private FileCheckResultMapper fileCheckResultMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Integer getNextVal() {

        Integer executionId = this.fileCheckResultMapper.getMaxExecutionId();

        if (executionId == null) {
            return 1;
        }

        return executionId + 1;
    }
}
