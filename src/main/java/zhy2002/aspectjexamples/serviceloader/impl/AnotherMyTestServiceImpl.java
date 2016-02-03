package zhy2002.aspectjexamples.serviceloader.impl;

import zhy2002.aspectjexamples.serviceloader.MyTestService;

/**
 * Another implementation.
 */
public class AnotherMyTestServiceImpl extends MyTestService {
    @Override
    public String getMessage() {
        return "AnotherMyTestServiceImpl";
    }
}
