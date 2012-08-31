package jp.mailmanager.dao.mapper;

import jp.mailmanager.dao.entity.FileCheckResult;

/**
 * ファイルチェック結果Mapperインタフェース
 */
public interface FileCheckResultMapper {

    /**
     * ファイルハッシュ値を条件にファイル内連番の最大値を取得する。
     * 
     * @param fileHash ファイルハッシュ値
     * @return ファイル内連番の最大値
     */
    Integer selectMaxFileSeqFromFileHash(String fileHash);

    /**
     * ファイルチェック結果テーブルにレコードを挿入する。
     * 
     * @param entity ファイルチェック結果
     * @return 処理件数
     */
    int insert(FileCheckResult entity);

}
