package com.example.a82173.friendcircle.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.activity.LoginActivity;
import com.example.a82173.friendcircle.activity.MainActivity;
import com.example.a82173.friendcircle.activity.ModifyPwdActivity;
import com.example.a82173.friendcircle.activity.PersonalCircleActivity;

public class LeftFragment extends Fragment implements OnClickListener {
	private View classcircleView;
	private View myClasscircleView;
	private View modifypwdView;
	private View cancellationView;
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_menu, null);
		findViews(view);
		
		return view;
	}

	public void findViews(View view) {
		classcircleView = view.findViewById(R.id.classcircle);
		myClasscircleView = view.findViewById(R.id.myclasscircle);
		modifypwdView = view.findViewById(R.id.modifypwd);
		cancellationView = view.findViewById(R.id.cancellation);
		
		classcircleView.setOnClickListener(this);
		myClasscircleView.setOnClickListener(this);
		modifypwdView.setOnClickListener(this);
		cancellationView.setOnClickListener(this);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		intent = new Intent();
		switch (v.getId()) {
		case R.id.classcircle: //班级圈
			intent.setClass(getContext(), MainActivity.class);
			getContext().startActivity(intent);
			getActivity().finish();
			break;
		case R.id.myclasscircle:// 我的班级圈
			intent.setClass(getContext(), PersonalCircleActivity.class);
			getContext().startActivity(intent);
			getActivity().finish();
			break;
		case R.id.modifypwd: //修改密码
			intent.setClass(getContext(), ModifyPwdActivity.class);
			getContext().startActivity(intent);
			break;
		case R.id.cancellation: // 注销
			intent.setClass(getContext(), LoginActivity.class);
			getContext().startActivity(intent);
			getActivity().finish();
			break;
		default:
			break;
		}
	}

}
