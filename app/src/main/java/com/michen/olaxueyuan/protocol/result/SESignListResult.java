package com.michen.olaxueyuan.protocol.result;

import com.michen.olaxueyuan.protocol.model.SESign;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/4/1.
 */
public class SESignListResult extends ServiceResult {
    public boolean state;
    public String msg;
    public String count;
    public ArrayList<SESign> data;
}
