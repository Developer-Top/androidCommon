package com.newing.utils.codec;

/**
 * Created by Administrator on 2017/7/3.
 */

public interface BinaryDecoder extends Decoder {
    byte[] decode(byte[] var1) throws DecoderException;
}
