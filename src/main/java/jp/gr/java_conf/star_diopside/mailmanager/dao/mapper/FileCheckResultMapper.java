package jp.gr.java_conf.star_diopside.mailmanager.dao.mapper;

import jp.gr.java_conf.star_diopside.mailmanager.dao.entity.FileCheckResult;
import jp.gr.java_conf.star_diopside.mailmanager.dao.entity.FileCheckResultPK;

/**
 * ファイルチェック結果Mapperインタフェース
 */
public interface FileCheckResultMapper {

    /**
     * 実行IDの最大値を取得する。
     * 
     * @return 実行ID
     */
    Integer getMaxExecutionId();

    /**
     * ファイルハッシュ値を条件にファイル内連番の最大値を取得する。
     * 
     * @param param 実行ID、ファイルハッシュ値
     * @return ファイル内連番の最大値
     */
    Integer getMaxFileSeq(FileCheckResultPK param);

    /**
     * ファイルチェック結果テーブルにレコードを挿入する。
     * 
     * @param entity ファイルチェック結果
     * @return 処理件数
     */
    int insert(FileCheckResult entity);

}
