package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int insertNote(Note note, int userId) {
        note.setUserid(userId);
        return noteMapper.insert(note);
    }

    public int updateNote(Note note) {
        return noteMapper.update(note);
    }

    public List<Note> getNotesByUserid(int userId) {
        return noteMapper.getNotes(userId);
    }

    public int deleteNote(int noteId) {
        return noteMapper.delete(noteId);
    }

}
