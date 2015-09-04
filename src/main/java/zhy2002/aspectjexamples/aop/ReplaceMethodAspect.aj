package zhy2002.aspectjexamples.aop;


import java.util.logging.Logger;

public aspect ReplaceMethodAspect {

    private static Logger logger = Logger.getLogger(ReplaceMethodAspect.class.getName());

    pointcut reportMethod() : execution(void *.*Report());

    void around() : reportMethod(){

        logger.info("Reports are not implemented.");

    }
}
