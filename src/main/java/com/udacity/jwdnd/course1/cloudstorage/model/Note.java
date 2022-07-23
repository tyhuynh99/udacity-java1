package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    int noteId;
    String noteTitle;
    String noteDescription;
    int userid;

    public Note() {
    }

    public Note(String noteTitle, String noteDescription, int userid) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userid = userid;
    }
    

    public Note(int noteId, String noteTitle, String noteDescription, int userid) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userid = userid;
    }

    public int getNoteid() {
        return noteId;
    }

    public void setNoteid(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public int getUserId() {
        return userid;
    }

    public void setUserId(int userid) {
        this.userid = userid;
    }

}
