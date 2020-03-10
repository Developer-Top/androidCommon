package com.newing.core.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：LiMing
 * @date ：2019-10-22
 * @desc ：
 */
public class BaseModel implements BaseModelInterface {
    public    Logger              logger = LoggerFactory.getLogger(getClass());


    @Override
    public void setPrestener(BasePresenterInterface basePresenterInterface) {

    }
}
