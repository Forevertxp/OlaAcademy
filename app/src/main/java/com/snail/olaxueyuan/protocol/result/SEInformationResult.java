package com.snail.olaxueyuan.protocol.result;

import com.snail.olaxueyuan.protocol.model.SEInformation;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15-1-1.
 */
public class SEInformationResult extends ServiceResult {

    public boolean state;
    public String cage;
    public ArrayList<SEInformation> data;
    public String msg;
}
