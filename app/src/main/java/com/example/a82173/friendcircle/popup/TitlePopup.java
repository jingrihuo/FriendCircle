package com.example.a82173.friendcircle.popup;



import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.a82173.friendcircle.R;

import java.util.ArrayList;


//功能描述：标题按钮上的弹窗（继承自PopupWindow）

public class TitlePopup extends PopupWindow implements OnClickListener{

	private LinearLayout priase;
	private LinearLayout comment;
	private TextView mpriase;
	private TextView mcomment;

	private Context mContext;

	// 列表弹窗的间隔
	protected final int LIST_PADDING = 10;

	// 实例化一个矩形
	private Rect mRect = new Rect();

	// 坐标的位置（x、y）
	private final int[] mLocation = new int[2];

	// 屏幕的宽度和高度
	private int mScreenWidth, mScreenHeight;

	// 位置不在中心
	private int popupGravity = Gravity.NO_GRAVITY;

	// 弹窗子类项选中时的监听
	private OnItemOnClickListener mItemOnClickListener;

	// 定义弹窗子类项列表
	private ArrayList<ActionItem> mActionItems = new ArrayList<ActionItem>();

	public ArrayList<ActionItem> getmActionItems() {
		return mActionItems;
	}


	public TitlePopup(Context context) {
		this.mContext = context;
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.comment_popu, null);
		this.setContentView(view);
		priase = (LinearLayout) view.findViewById(R.id.popu_praise);
		comment = (LinearLayout) view.findViewById(R.id.popu_comment);
		mpriase = (TextView) view.findViewById(R.id.praise);
		mcomment = (TextView) view.findViewById(R.id.comment);
		priase.setOnClickListener(this);
		comment.setOnClickListener(this);

		// 设置可以获得焦点
		this.setFocusable(true);
		// 设置弹窗内可点击
		this.setTouchable(true);
		// 设置弹窗外可点击
		this.setOutsideTouchable(true);
		// 设置弹窗的宽度和高度
		this.setWidth(Util.dip2px(context, 180));
		this.setHeight(Util.dip2px(context, 40));
		this.update();

		this.setBackgroundDrawable(new BitmapDrawable());
		this.setAnimationStyle(R.style.cricleBottomAnimation);

		initAction();
	}

	//显示弹窗列表界面
	public void show(final View c) {
		// 获得点击屏幕的位置坐标
		c.getLocationOnScreen(mLocation);
		// 设置矩形的大小
		mRect.set(mLocation[0], mLocation[1], mLocation[0] + c.getWidth(),
				mLocation[1] + c.getHeight());
		mpriase.setText(mActionItems.get(0).mTitle);
		// 判断是否需要添加或更新列表子类项
		if (!this.isShowing()) {
			showAtLocation(c, Gravity.NO_GRAVITY, mLocation[0] - this.getWidth()
					- 10, mLocation[1] - ((this.getHeight() - c.getHeight()) / 2));
		}else {
			dismiss();
		}
	}
	//选项监听
	public void onClick(View v) {
		dismiss();
		switch (v.getId()) {
			case R.id.popu_comment:
				mItemOnClickListener.onItemClick(mActionItems.get(1), 1);
				break;
			case R.id.popu_praise:
				mItemOnClickListener.onItemClick(mActionItems.get(0), 0);
				break;
		}
	}

	//添加子类项
	public void addAction(ActionItem action) {
		if (action != null) {
			mActionItems.add(action);
		}
	}

	public void initAction(){
		addAction(new ActionItem(mContext,"赞",R.drawable.circle_comment));
		addAction(new ActionItem(mContext,"评论",R.drawable.circle_praise));
	}

	//根据位置得到子类项
	public ActionItem getAction(int position) {
		if (position < 0 || position > mActionItems.size())
			return null;
		return mActionItems.get(position);
	}

	//设置监听事件
	public void setItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
		this.mItemOnClickListener = onItemOnClickListener;
	}

	// 功能描述：弹窗子类项按钮监听事件
	public static interface OnItemOnClickListener {
		void onItemClick(ActionItem item, int position);
	}
}
