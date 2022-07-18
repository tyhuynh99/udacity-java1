package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class NoteService {
    private final NoteMapper mapper;
    private final UserMapper userMapper;

    public NoteService(NoteMapper mapper, UserMapper userMapper) {
        this.mapper = mapper;
        this.userMapper = userMapper;
    }

    public List<Note> findAll() {
        return mapper.findAll(getCurrentUserId());
    }

    public Note findById(int noteId) {
        return mapper.findById(noteId);
    }

    public int deleteById(int noteId) {
        return mapper.deleteById(noteId);
    }

    private int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User user = userMapper.getUser(username);
        return user.getUserId();
    }
}
