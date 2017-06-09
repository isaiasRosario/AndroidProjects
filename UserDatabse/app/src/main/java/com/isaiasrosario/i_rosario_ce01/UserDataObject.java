// Isaias Rosario
// MDF3 - 1604
// UserDataObject Class

package com.isaiasrosario.i_rosario_ce01;

import java.io.Serializable;

public class UserDataObject implements Serializable {

    private String mFirstName;
    private String mLastName;
    private String mAge;

    public UserDataObject(){
        mFirstName = mLastName = mAge = "";
    }

    public UserDataObject(String _firstName, String _lastName, String _age){
        mFirstName = _firstName;
        mLastName = _lastName;
        mAge = _age;
    }

    public String getFirstName(){
        return mFirstName;
    }

    public String getLastName(){
        return mLastName;
    }

    public String getAge(){ return mAge; }
}
