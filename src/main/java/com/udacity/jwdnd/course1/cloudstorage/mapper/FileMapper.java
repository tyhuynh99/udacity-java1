package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Mapper
public interface FileMapper {
    
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contentType}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> findAll(int userid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File findByFilename(String filename);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File findByFileId(int fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer deleteByFileId(int fileId);
}
