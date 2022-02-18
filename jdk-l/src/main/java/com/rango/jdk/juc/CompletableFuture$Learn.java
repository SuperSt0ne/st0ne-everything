package com.rango.jdk.juc;


import com.rango.jdk.base.Base;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class CompletableFuture$Learn extends Base {
    public static void main(String[] args) throws Exception {
//        runAsync();

//        supplyAsync();

//        thenCombine();

//        thenCompose();

//        thenApply();

//        thenAcceptOrRun();

//        allOf();

//        allOfIfExceptionally();

        allOfHandleEx();
    }

    /**
     * 接受一个Runnable参数
     */
    public static void runAsync() {
        CompletableFuture.runAsync(() -> {
            printThreadName();
            System.out.println("----> result:" + 30);
        });
    }

    public static void supplyAsync() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            printThreadName();
            sleep(3);
            return 30;
        });
//        System.out.println("result:" + future.get(5, TimeUnit.SECONDS));
        printResult(future);
    }

    /**
     * 可以使用 thenApply() 处理和改变CompletableFuture的结果
     */
    public static void thenApply() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            printThreadName();
            sleep(3);
            return 30;
        }).thenApply(res -> {
            printThreadName();
            return res + 13;
        });
        printResult(future);
    }

    /**
     * thenAccept() 和 thenRun() 如果你不想从你的回调函数中返回任何东西，仅仅想在Future完成后运行一些代码片段，
     * 你可以使用thenAccept()和 thenRun()方法，这些方法经常在调用链的最末端的最后一个回调函数中使用。
     */
    public static void thenAcceptOrRun() {
        CompletableFuture<Void> futureAccept = CompletableFuture.supplyAsync(() -> {
            printThreadName();
            sleep(3);
            //此时 返回的值并没有用了
            return 30;
        }).thenAccept(res -> {
            int res2 = res + 13;
            printThreadName(res2 + "");
        });
        printResult(futureAccept);

        CompletableFuture<Void> futureRun = CompletableFuture.supplyAsync(() -> {
            printThreadName();
            sleep(3);
            //此时 返回的值并没有用了
            return 30;
        }).thenRun(() -> printThreadName("other opt"));
        printResult(futureRun);
    }


    /**
     * 两个线程异步执行后通过BiFunction处理结果
     * <p>
     * 使用最后处理完成的线程 来执行合并任务
     */
    public static void thenCombine() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(1);
            printThreadName("supplyAsync opt");
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            sleep(2);
            printThreadName("thenCombine supplyAsync opt");
            return " world";
        }), (f1, f2) -> {
            printThreadName("thenCombine BiFunction opt");
            return f1 + f2;
        });
        printResult(future);
    }

    /**
     * 链接执行
     */
    public static void thenCompose() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(3);
            printThreadName("supplyAsync opt");
            return "hello";
        }).thenCompose(str -> CompletableFuture.supplyAsync(() -> {
            sleep(2);
            printThreadName("second supplyAsync opt");
            return str + " world";
        }));
        printResult(future);
    }


    public static void allOf() {
        List<String> linkList = Arrays.asList("www.baidu.com", "teacher.bookln.cn", "www.ali.com");
        List<CompletableFuture<String>> completableFutureList = linkList.stream().map(link -> CompletableFuture.supplyAsync(() -> {
            printThreadName("downloading from link " + link + "......");
            return "download success from link " + link;
        })).collect(Collectors.toList());

        CompletableFuture<Void> aof = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));

        CompletableFuture<List<String>> endResult = aof.thenApply(res -> completableFutureList.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
        printResult(endResult);
    }

    public static void allOfIfExceptionally() {
        List<CompletableFuture<String>> cfs = Collections.singletonList(CompletableFuture.supplyAsync(() -> {
            exceptionally();
            return "excepted value after exceptionally method";
        }));
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(cfs.get(0));

        CompletableFuture<String> endResult = allOfFuture.thenApply(res -> {
            printThreadName("before exception print");
            return cfs.get(0).join();
        });
        printResult(endResult);
    }

    public static void allOfHandleEx() {
        Map<String, Object> data = new HashMap<>();
        CompletableFuture<Void> a = CompletableFuture.runAsync(() -> {
//            exceptionally();
            sleep(10);
            System.out.println("11");
            data.put("a", "11");
        }).handle((res, ex) -> {
            if (Objects.nonNull(ex)) {
                printThreadName("ex:" + ex.getMessage());
            }
            return res;
        });
        CompletableFuture<Object> b = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println("12");
            data.put("b", "12");
            return data;
        }).handle((res, ex) -> {
            if (Objects.nonNull(ex)) {
                printThreadName("ex:" + ex.getMessage());
            }
            return res;
        });
        CompletableFuture.allOf(a, b).join();
        System.out.println("data");
    }
}
