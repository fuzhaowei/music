package fzw.yuandi.music;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基类Activity
 * Created by fuzhaowei on 2016/10/28.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toast toast;
    protected final int DEBUG = 2;
    protected final int WARN = 5;
    protected final int INFO = 3;
    protected final int VERBOSE = 4;
    protected final int ERROR = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(onCreateView());
            initActivity();
            initUI();
            initData();
            setListener();
        } catch (Exception e) {
            setContentView(R.layout.error);
            LogShow(e.getMessage(), ERROR);
        }
    }

    /**
     * 返回布局
     */
    protected abstract int onCreateView();

    /**
     * 初始化有关Activity的类
     */
    protected void initActivity() {
    }

    /**
     * 初始化类
     */
    protected void initUI() {
    }

    /**
     * 给类赋值
     */
    protected void initData() {
    }

    /**
     * 设置监听
     */
    protected void setListener() {
    }

    /**
     * 原始版Toast
     *
     * @param cotent 内容
     */
    protected void ToastShow(String cotent) {
        if (toast != null) {
            toast.setText(cotent);
        } else {
            toast = new Toast(this);
            toast = Toast.makeText(this, cotent, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * 可以调整位置的Toast
     *
     * @param cotent  内容
     * @param gravity 位置
     */
    protected void ToastShow(String cotent, int gravity) {
        if (toast != null) {
            toast.setText(cotent);
        } else {
            toast = new Toast(this);
            toast = Toast.makeText(this, cotent, Toast.LENGTH_SHORT);
            toast.setGravity(gravity, 0, 0);
            toast.show();
        }
    }

    /**
     * 带图片的Toast
     *
     * @param cotent   内容
     * @param gravity  位置
     * @param resource 图片资源
     */
    protected void ToastShow(String cotent, int gravity, int resource) {
        LinearLayout toastView = null;
        ImageView imageCodeProject = null;
        if (toast != null) {
            toast.setText(cotent);
            imageCodeProject.setImageResource(resource);
            toastView.addView(imageCodeProject, 0);
        } else {
            toast = Toast.makeText(this, cotent, Toast.LENGTH_SHORT);
            toast.setGravity(gravity, 0, 0);
            toastView = (LinearLayout) toast.getView();
            imageCodeProject = new ImageView(getApplicationContext());
            imageCodeProject.setImageResource(resource);
            toastView.addView(imageCodeProject, 0);
            toast.show();
        }
    }

    /**
     * 打印信息
     *
     * @param content 内容
     * @param KEY     选择打印字体颜色
     */
    protected void LogShow(String content, int KEY) {
        switch (KEY) {
            case ERROR:
                Log.e(getClass().getName() + "：", content);
                break;
            case DEBUG:
                Log.d(getClass().getName() + "：", content);
                break;
            case INFO:
                Log.i(getClass().getName() + "：", content);
                break;
            case VERBOSE:
                Log.v(getClass().getName() + "：", content);
                break;
            case WARN:
                Log.w(getClass().getName() + "：", content);
                break;
        }
    }

    protected void Skip(Class cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }

    /**
     * 获取当前时间
     *
     * @param I 选择格式
     * @return 返回时间字符串
     */
    protected static String Time(int I) {
        String result = null;
        switch (I) {
            case 1:
                result = "yyyy-MM-dd:HH:mm";
                break;
            case 2:
                result = "yyyy-MM-dd";
                break;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(result);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取intent传过去的值
     *
     * @param key KEY
     * @return 接收到的值
     */
    public String getIntent(String key) {
        String str = getIntent().getStringExtra(key);
        return str;
    }

    /**
     * 将字符串转成时间戳
     *
     * @param time 将时间字符串传入其中，格式：yyyy-MM-dd:HH:mm or yyyy-MM-dd
     * @return 返回时间戳
     */
    protected String getTimeStamp(String time) {
        String re_StrTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        Long l = Long.valueOf(time);
        re_StrTime = simpleDateFormat.format(new Date(l * 1000L));
        return re_StrTime;
    }

    /**
     * 将时间戳转成字符串
     *
     * @param time 将时间戳传入其中，格式：yyyy-MM-dd:HH:mm or yyyy-MM-dd
     * @return 返回时间字符串
     */
    protected String getTimeString(String time) {
        String re_time = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        Date date;
        try {
            date = simpleDateFormat.parse(time);
            Long l = date.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    protected void Steep() {
        if (Build.VERSION.SDK_INT >= 19) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            ToastShow("您的手机暂不支持沉浸式模式");
        }
    }

}