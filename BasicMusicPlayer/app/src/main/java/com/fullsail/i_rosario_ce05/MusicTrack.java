package com.fullsail.i_rosario_ce05;

public class MusicTrack {
    private String mArtist;
    private String mName;
    private String mArtwork;
    private String mUri;

    public MusicTrack() {
        mArtist = mName = mUri = mArtwork ="";

    }

    public MusicTrack(String _artist, String _name, String _uri, String _artwork) {
        mArtist = _artist;
        mName = _name;
        mArtwork = _artwork;
        mUri = _uri;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getName() {
        return mName;
    }

    public String getArtwork() {
        return mArtwork;
    }

    public String getUri() {
        return mUri;
    }

}