package com.snail.olaxueyuan.protocol.result;

import com.snail.olaxueyuan.protocol.model.SEMsg;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15-3-23.
 */
public class SEMsgResult extends ServiceResult {
    public boolean state;
    public String msg;
    public ArrayList<SEMsg> data;
}
