package com.udacity.jwdnd.course1.cloudstorage.model.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotes(int userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription}, userid = #{userid} WHERE noteid = #{noteId}")
    int update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int delete(Integer noteId);
}
