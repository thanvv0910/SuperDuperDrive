package com.udacity.jwdnd.course1.cloudstorage.model.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFilesByUserId(Integer userid);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFileExitsByName(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userid}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insert(File file);

    @Update("UPDATE FILES SET filename = #{file.fileName}, contenttype = #{file.contentType}, filesize = #{file.fileSize}, userid = #{file.userid} filedata = #{file.fileData} WHERE fileid = #{file.fileid}")
    int update(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    int delete(Integer fileid);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File getFileById(Integer fileid);
}
