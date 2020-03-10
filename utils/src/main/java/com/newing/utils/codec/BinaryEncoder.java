package com.newing.utils.codec;

/**
 * Created by Administrator on 2017/7/3.
 */

public interface BinaryEncoder extends Encoder {
    byte[] encode(byte[] var1) throws EncoderException;
}
