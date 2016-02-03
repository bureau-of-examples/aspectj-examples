package zhy2002.aspectjexamples.serviceloader.impl;

import zhy2002.aspectjexamples.serviceloader.MyTestService;

/**
 * Created by jzhang on 4/02/2016.
 */
public class MyTestServiceImpl extends MyTestService{


    @Override
    public String getMessage() {
        return "MyTestServiceImpl";
    }
}
