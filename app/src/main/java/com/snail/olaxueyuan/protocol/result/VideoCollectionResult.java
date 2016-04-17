package com.snail.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.snail.olaxueyuan.protocol.model.VideoCollection;

/**
 * Created by tianxiaopeng on 16/1/22.
 */
public class VideoCollectionResult extends ServiceResult {
    public String apicode;
    public String message;
    @SerializedName("result")
    public VideoCollection videoCollection;
}
