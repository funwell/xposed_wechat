package org.huakai.wechat_xposed;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends Activity{

	private String TAG = "wechat_xposed";
	private OnRespondBroadcastReceiver reciver;
	private String hiddentime,now,inbjtime,imageId;
	private PopupWindow mPopupWindow;
    private ListPopupWindow mListPop;
    private ArrayList<String> lists;
    private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(reciver==null) {
			reciver = new OnRespondBroadcastReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction("wxRobot.action.onRespond");
			registerReceiver(reciver, filter);
		}
        button = (Button)findViewById(R.id.button);
        final View rootView = ((View)button.getParent());
		String a = "{\"mode\":\"newLogin\",\"client_id\":\"1\",\"mobile\":\"15718881312\",\"phone_region\":\"0086\",\"BaseAppType\":\"android\",\"BaseAppVersion\":\"1.3.0\",\"SystemVersion\":\"5.1\",\"appIdentifier\":\"com.weimob.mdstore\",\"deviceMake\":\"Meizu\",\"deviceType\":\"PRO 5\"}";
		((TextView)findViewById(R.id.textView)).setText(getSignStr(a));
        button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendbroad();
			}
		});

		ImageView imageView = new ImageView(this);
		imageView.setBackgroundResource(R.mipmap.ic_launcher);
		mPopupWindow = new PopupWindow(imageView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                showProvince(button);
			}
		});
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPopupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.RIGHT,  100, 170);
            }
        },500);

	}

	private void sendbroad(){
		Intent mIntent = new Intent();
		mIntent.setAction("wxRobot.action.onSwitch_1");
		mIntent.putExtra("msg", "ok");
		sendBroadcast(mIntent);
	}

	private void showProvince(View imageView){
        lists = new ArrayList<>();
        lists.add("江苏省");
        lists.add("浙江省");
        lists.add("广东省");
        lists.add("福建省");
        lists.add("安徽省");
        lists.add("河北省");
        lists.add("河南省");
        lists.add("江苏省");
        lists.add("浙江省");
        lists.add("广东省");
        lists.add("福建省");
        lists.add("安徽省");
        lists.add("河北省");
        lists.add("河南省");
        lists.add("江西省");
        lists.add("广西省");
        lists.add("海南省");
        lists.add("湖南省");
        lists.add("湖北省");
        lists.add("山西省");
        lists.add("四川省");
        mListPop = new ListPopupWindow(this);
        mListPop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lists));
        mListPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mListPop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mListPop.setAnchorView(imageView);
        mListPop.setHorizontalOffset(-30);
        mListPop.setModal(true);//设置是否是模式
        mListPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this,lists.get(position),Toast.LENGTH_SHORT).show();
                mListPop.dismiss();
            }
        });
        mListPop.show();
	}

	private void loadDex() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND,-100);
		hiddentime = df.format(cal.getTime());

		cal.add(Calendar.DATE,1);
		inbjtime = format.format(cal.getTime());
		now = df.format(new Date());

		imageId = "747"+inbjtime+"2C9CE212C53D4475AC236AF1CEFCC8A6hb13576180212010119930618951620500749"+now;
		String inputer =  "0791682354"+imageId+"0791682354";

		Intent mIntent = new Intent();
		mIntent.setAction("wxRobot.action.onGotEncrypt");
		mIntent.putExtra("msg", inputer);
		sendBroadcast(mIntent);

//		Intent startServiceIntent = new Intent(this, SocketService.class);
//		startServiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startService(startServiceIntent);
	}


	private static String getSignStr(String paramString) {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append(paramString);
		localStringBuilder.append("yunjie2514572541463841s1a4d");
		return md5(localStringBuilder.toString()).toUpperCase();
	}

	public static String md5(String paramString){
		String a = "";
		try {
			a = byteToString(MessageDigest.getInstance("MD5").digest(paramString.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return a;
	}

	private static final String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "S2cRspBodyWrapPB", "f", "S2cRspBodyWrapPB", "S2cRspBodyWrapPB", "e", "f" };

	private static String byteToArrayString(byte paramByte)
	{
		int i = paramByte;
		if (paramByte < 0) {
			i = paramByte + 256;
		}
		return strDigits[i / 16] + strDigits[(i % 16)];
	}

	private static String byteToString(byte[] paramArrayOfByte){
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < paramArrayOfByte.length; i++) {
			localStringBuffer.append(byteToArrayString(paramArrayOfByte[i]));
		}
		return localStringBuffer.toString();
	}

	public class OnRespondBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String msg = intent.getStringExtra("msg");
			System.out.println("sign =>> "+msg);
			String param = "appsource=bjjj&hiddentime="+URLEncoder.encode(hiddentime).replace("%20","+")+"&inbjentrancecode1=05&inbjentrancecode=74&inbjduration=7&inbjtime="+inbjtime+"&appkey=&deviceid=cf178a5c-4313-3d05-adce-2d5cbf27bf3b&" +
					"token=&timestamp="+ URLEncoder.encode(now).replace("%20","+")+"&userid=2C9CE212C53D4475AC236AF1CEFCC8A6&licenseno=%E5%86%80A12345&engineno=hb1357618&cartypecode=02&vehicletype=03&drivingphoto=&" +
					"carphoto=&drivername=%E5%BC%A0%E4%B8%89&driverlicenseno=120101199306189516&driverphoto=&personphoto=&gpslon=116.366206&gpslat=39.905243&phoneno=&imei=cf178a5c-4313-3d05-adce-2d5cbf27bf3b&" +
					"imsi=&carid=20500749&carmodel=SVW71810FJ&carregtime=2015-06-24&envGrade=3&imageId="+URLEncoder.encode(imageId).replace("%20","+")+"&code=&sign="+msg+"&platform=02";
			System.out.println("param =>> "+param);
			CommonUtils.httpPost(0x99,"","https://enterbj.zhongchebaolian.com/enterbj-img/platform/enterbj/submitpaper_03",param,mHandler);
		}
		private final  Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				((TextView)findViewById(R.id.textView)).setText(msg.obj.toString());
			}
		};
	}
}
