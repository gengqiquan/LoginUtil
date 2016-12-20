package com.gengqiquan.loginutil;

import android.content.Context;
import android.content.Intent;


/**
 * 登录回调类
 *
 * @author gengqiquan
 *         time 2015年5月20日13:24:16
 */
public class LoginUtil {
    private static Class<?> mLoginActivity = null;
    public static String USER_TOKEN = null;
    private static LoginCheckInterceptor mLoginCheckInterceptor = () -> {
        if (!checkNULL(LoginUtil.USER_TOKEN)) {
            return true;
        }
        return false;
    };
    // 登录回调接口
    public static LoginUtil.ICallBack CALLBACK;

    /**
     * 检查是否登录
     * 登录直接执行操作，未登录跳转登录，登录后继续执行操作，放弃登录则什么都不做
     * <p>
     * author gengqiquan
     * date 2016/12/20 9:52
     */
    public static void doActionNeedLogin(final Context context, final LoginForCallBack callBack) {
        if (isLogin()) {
            // 登录状态直接执行登录回调前需要做的操作
            callBack.callBack();
        } else {
            LoginUtil.CALLBACK = new ICallBack() {
                @Override
                public void postExec() {
                    // 登录回调后执行登录回调前需要做的操作
                    if (isLogin()) {
                        // 这里需要再次判断是否登录，防止用户取消登录，取消则不执行登录成功需要执行的回调操作
                        callBack.callBack();
                        //防止调用界面的回调方法中有传进上下文的引用导致内存溢出
                        LoginUtil.CALLBACK = null;
                    }
                }
            };
            if (context != null) {
                Intent intent = new Intent(context, mLoginActivity);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        }
    }

    /**
     * 检查是否登录
     * 已登录什么都不做
     * 未登录跳转登录，登录后继续执行操作，放弃登录则什么都不做
     * <p>
     * author gengqiquan
     * date 2016/12/20 9:52
     */
    public static void doActionJustAfterLogin(final Context context, final LoginForCallBack callBack) {
        if (!isLogin()) {
            LoginUtil.CALLBACK = new ICallBack() {
                @Override
                public void postExec() {
                    // 登录回调后执行登录回调前需要做的操作
                    if (isLogin()) {
                        // 这里需要再次判断是否登录，防止用户取消登录，取消则不执行登录成功需要执行的回调操作
                        callBack.callBack();
                        //防止调用界面的回调方法中有传进上下文的引用导致内存溢出
                        LoginUtil.CALLBACK = null;
                    }
                }
            };
            if (context != null) {
                Intent intent = new Intent(context, mLoginActivity);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        }
    }

    /**
     * 需要登录才能执行的操作
     * 登录直接执行操作，未登录跳转登录，登录后什么都不做
     * <p>
     * author gengqiquan
     * date 2016/12/20 9:52
     */
    public static void doActionAlreadyLogin(Context context, final LoginForCallBack callBack) {
        if (isLogin()) {
            // 登录状态直接执行登录回调前需要做的操作
            callBack.callBack();
        } else {
            Intent intent = new Intent(context, mLoginActivity);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 跳转登录，什么都不做
     * <p>
     * author gengqiquan
     * date 2016/12/20 9:52
     */
    public static void doLogin(Context context) {
        if (!isLogin()) {
            Intent intent = new Intent(context, mLoginActivity);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 判断是否登录，是返回真
     * <p>
     * author gengqiquan
     * date 2016/12/20 9:57
     */
    public static boolean isLogin() {
        if (mLoginCheckInterceptor != null && mLoginCheckInterceptor.isLogin()) {
            return true;
        }
        return false;
    }

    /**
     * 添加自定义判断登录拦截器
     * <p>
     * author gengqiquan
     * date 2016/12/20 15:49
     */
    public static void addLoginCheckInterceptor(LoginCheckInterceptor loginCheckInterceptor) {
        mLoginCheckInterceptor = loginCheckInterceptor;
    }

    /**
     * 添加自定义判断登录拦截器
     * <p>
     * author gengqiquan
     * date 2016/12/20 15:49
     */
    public static void setLoginActivity(Class loginActivity) {
        mLoginActivity = loginActivity;
    }

    // 判断是否NULL
    private static boolean checkNULL(String str) {
        return str == null || "null".equals(str) || "".equals(str);

    }

    // 声明一个登录成功回调的接口
    public interface ICallBack {
        // 在登录操作及信息获取完成后调用这个方法来执行登录回调需要做的操作
        void postExec();
    }

    @FunctionalInterface
    public interface LoginForCallBack {
        void callBack();
    }

    @FunctionalInterface
    public interface LoginCheckInterceptor {
        boolean isLogin();
    }
}
