package org.eu.hanana.reimu.lib.satori.v1.protocol;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;

public class SignalBodyIdentify extends AbstractSignalBody implements IHasSn{
    public SignalBodyIdentify(){
        this(null);
    }
    public SignalBodyIdentify(String token){
        this(token,-1);
    }
    public SignalBodyIdentify(int sn){
        this(null,sn);
    }
    public SignalBodyIdentify(String tk,int sn){
        this.sn=sn;
        this.token=tk;
    }
    public String token;
    public int sn;

    @Override
    public int getSn() {
        return sn;
    }
}
