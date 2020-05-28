package com.seatrend.xj.electricbicyclesalesystem.activity;

import com.seatrend.xj.electricbicyclesalesystem.entity.FHEnity;
import com.seatrend.xj.electricbicyclesalesystem.http.thread.Priority;
import com.seatrend.xj.electricbicyclesalesystem.http.thread.PriorityRunnable;
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadConstants;
import com.seatrend.xj.electricbicyclesalesystem.util.CheckPawUtil;
import com.seatrend.xj.electricbicyclesalesystem.util.CheckUtil;
import com.seatrend.xj.electricbicyclesalesystem.util.CphmUtils;
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils;
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils;
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ly on 2019/11/5 9:46
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class JavaTest {

    static int[] ints = {1, 1, 1, 1, 1, 1};
    static int[] intx = {1, 1, 0, 1, 1, 1};
    static Collection<String> list = new ArrayList<>();
    static Thread t1, t2, t3;

    public static void main(String[] args) {
//        String result = "http://mv.cqccms.com.cn/incoc/GSViewEbike!viewCocEbike.action?vinCode=117321900000243mn1112222111412541";
//
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

//
//       String regEx = "(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)";
//        String result = "http://192.168.0.221:8099/electricVehicleSalePlatformXinjiang";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(result);
//        if(m.find()) {
//            System.out.println("ip:"+m.group(1));
//            System.out.println("port:"+m.group(2));
//        }
//
//        InputFilter inputFilter=new InputFilter() {
//
//            Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]");
//            @Override
//            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
//                Matcher matcher=  pattern.matcher(charSequence);
//                if(!matcher.find()){
//                    return null;
//                }else{
//                    return "";
//                }
//
//            }
//        };


//        macherString(regEx,result);
//        ArrayList<String> s = new ArrayList<>();
//        s.add("1");
//        s.remove("2");
//        System.out.print(s);

//        ArrayList<FHEnity.Data.FHList> list = new ArrayList<>();
//        FHEnity.Data.FHList a = new FHEnity.Data.FHList();
//        a.setFhbj("1");
//        FHEnity.Data.FHList a1 = new FHEnity.Data.FHList();
//        a1.setFhbj("1");
//        FHEnity.Data.FHList a2 = new FHEnity.Data.FHList();
//        a2.setFhbj("0");
//        list.add(a);
//        list.add(a1);
//        list.add(a2);
//        Collections.sort(list,mMyFhztCompare);
//
//        for(FHEnity.Data.FHList db :list){
//            System.out.println(db.getFhbj());
//        }
//
//        isCorrect("511023199308101876");
//        System.out.println(isCorrect("511023199308101876"));

//        try {
//            Runtime.getRuntime().exec("adb root");
////            Runtime.getRuntime().exec("adb pull sdcard/laiyu/11.txt D:\\lyzy\\caogao");
//            Runtime.getRuntime().exec("adb push D:\\lyzy\\11.txt sdcard/laiyu");
//            System.out.println("ggooggo");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String s = "110236";
//        System.out.print(s.substring(0,3));

//        String path = "D:\\lyzy\\111";
//        File file = new File(path);
//        if(file.exists()){
//            PhotoFileUtils.deleteFile(new File(path));
//            System.out.print("删除成功");
//        }


//        String s = "SS11111s";
//        System.out.println(CheckPawUtil.isSixPaw(s));


//优先任务的开启方式

//        for (int i = 1; i < 20; i++) {
//            PriorityRunnable priorityRunnable = new PriorityRunnable(Priority.Level.NORMAL, new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(Thread.currentThread().getName() + "优先级正常");
//                }
//            });
//            if (i % 3 == 1) {
//                priorityRunnable = new PriorityRunnable(Priority.Level.HIGH, new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println(Thread.currentThread().getName() + "优先级高");
//                    }
//                });
//            } else if (i % 5 == 0) {
//                priorityRunnable = new PriorityRunnable(Priority.Level.LOW, new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println(Thread.currentThread().getName() + "优先级低");
//                    }
//                });
//            }
//            ThreadPoolManager.Companion.getInstance().setQueueMode(false).execute(priorityRunnable);
//        }
//
//        for (int i = 1; i < 20; i++) {
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(Thread.currentThread().getName() + "普通");
//                }
//            };
//            ThreadPoolManager.Companion.getInstance().execute(runnable);
//        }
//
//        Runnable t1 = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(" 定时任务 t1");
//            }
//        };
//        Runnable t2 = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(" 定时任务 t2");
//            }
//        };
//        Runnable t3 = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(" 定时任务 t3");
//            }
//        };
//
//
//        ThreadPoolManager.Companion.getInstance().createSchedulePool(t1,0, 2000);
//        ThreadPoolManager.Companion.getInstance().createSchedulePool(t2,0, 2000);
//        ThreadPoolManager.Companion.getInstance().createSchedulePool(t3,0, 2000);
//
//        ThreadPoolManager.Companion.getInstance().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("每5秒一次运动  time = "+ StringUtils.longToStringData(System.currentTimeMillis()));
//            }
//        },0,5000);

//        Thread t1 = new Thread(){
//            @Override
//            public void run() {
//                for(;;){
//                    System.out.println(" tt");
//                }
//            }
//        };
//        t1.start();
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t1.stop();


//        System.out.println(StringUtils.insertCharToString("ABCDEFG","  ",9));
//        System.out.println(1132.0/720.0  );
//        System.out.println(1344.0/756  );
//        ArrayList<String> l = new ArrayList<>();
//        l.add("1");
//        l.add("2");
//        l.add("3");
//        l.add("4");
//        l.add("5");
//        ArrayList<String> r = new ArrayList<>();
//
//        r.add("6");
//        r.add("7");
//        for (int j = 0; j < r.size(); j++) {
//            for (int i = 0; i < l.size(); i++) {
//                if (l.get(i).equals(r.get(j))) {
//                    break;
//                }
//                if (i == l.size() - 1) {
//                    l.add(r.get(j));
//                }
//            }
//        }
//        System.out.println(GsonUtils.toJson(l));

        String s = StringUtils.insertXingLxdh("17610071263");
        String s1 = StringUtils.insertXingLxdh("02882539674");
        String s2 = StringUtils.insertXingLxdh("08308351873");
        System.out.println(s);
        System.out.println(s1);
        System.out.println(s2);
    }

    public static boolean isCorrect(String sfz) {
        String regx = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(sfz);
        return matcher.matches();
    }

    private static Comparator<? super FHEnity.Data.FHList> mMyFhztCompare = new Comparator<FHEnity.Data.FHList>() {
        @Override
        public int compare(FHEnity.Data.FHList t1, FHEnity.Data.FHList t2) {

            if ("0".equals(t1.getFhbj())) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    private static void macherString(String regEx, String matherString) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(matherString);
        System.out.println(matherString + " 匹配：" + matcher.matches());
    }

//    private static boolean checkData() {
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println("i = " + i);
//            if (list.get(i) == 0) {
//                list.set(i, 1);
//                System.out.println("Change");
//                break;
//            }
//            if (i == list.size() - 1) {
//                return true;
//            }
//
//        }
//
//        return checkData();
//    }
}
