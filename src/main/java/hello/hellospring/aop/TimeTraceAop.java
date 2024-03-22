package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeTraceAop {
    // TimeTraceAop를 SpringConfig에 bean으로 등록할 때 '&& !target(hello.hellospring.SpringConfig)' 추가해줘야함.
    // 왜냐하면 @Around 에서 aop대상을 지정하는데, 이때 package하위 모든 파일이 들어가기 때문에
    // SpringConfig의 timeTraceAop도 aop처리하게됨. 이때 자기 자신을 보기때문에 순환 참조가 들어감.
    // 따라서 대상에서 제외해 주어야함.
    @Around("execution(* hello.hellospring..*(..)) && !target(hello.hellospring.SpringConfig)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try{
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
            System.out.println("END: " + joinPoint.toString() +"  "+ timeMs + "ms");
        }
    }
}
