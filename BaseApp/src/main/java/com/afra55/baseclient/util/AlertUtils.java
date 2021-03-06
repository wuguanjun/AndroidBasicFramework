package com.afra55.baseclient.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

import com.afra55.baseclient.view.CusstomDialog;


public class AlertUtils {

	private static CusstomDialog dialog = null;
	/**
	 * 弹出框
	 * @param context
	 * @param title
	 * @param content
	 * @param ok
	 * @param cancel
	 * @param lOk
	 * @param lCancel
	 * @return
	 */
	public static CusstomDialog.Builder showAlert(final Context context,
			final String title, String content, final String ok,
			final String cancel, final OnClickListener lOk,
			final OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing() || (dialog!=null && dialog.isShowing())) {
			Toast.makeText(context, "null"+ (dialog!=null && dialog.isShowing()), Toast.LENGTH_SHORT).show();
			return null;
		}
		CusstomDialog.Builder builder = new CusstomDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(content);
		builder.setNegativeButton(cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(lCancel!=null){

					lCancel.onClick(dialog, which);
				}
			}
		});
		builder.setPositiveButton(ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
				lOk.onClick(dialog, which);
			}
		});
		dialog = builder.create();
		dialog.show();
		return builder;
	}

	public static CusstomDialog.Builder showConfirmAlert(final Context context,
			final String title, String content) {
		if (context instanceof Activity && ((Activity) context).isFinishing() || (dialog!=null && dialog.isShowing())) {
			return null;
		}
		CusstomDialog.Builder builder = new CusstomDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(content);
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		dialog.show();
		return builder;
	}

	public static CusstomDialog.Builder showConfirmAlert(final Context context,
			final String title, final String confirm, String content) {
		if (context instanceof Activity && ((Activity) context).isFinishing()  || (dialog!=null && dialog.isShowing())) {
			return null;
		}
		CusstomDialog.Builder builder = new CusstomDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(content);
		builder.setPositiveButton(confirm, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		dialog.show();
		return builder;
	}

	public static CusstomDialog.Builder showConfirmAlert(final Context context,
			final String title, String content, final OnClickListener lOk) {
		if (context instanceof Activity && ((Activity) context).isFinishing()  || (dialog!=null && dialog.isShowing())) {
			return null;
		}
		CusstomDialog.Builder builder = new CusstomDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(content);
		builder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				lOk.onClick(dialog, which);
			}
		});
		dialog = builder.create();
		dialog.show();
		return builder;
	}
}
