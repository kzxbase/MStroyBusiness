package kz.xbase.mstroy.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import kz.xbase.mstroy.R;


public class AlertDialogManager {

    public AlertDialogManager(Context context, String title, String message, boolean isUpdate){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(message)
                .dividerColor(context.getResources().getColor(R.color.grey))
                .positiveText("OK")
                .positiveColorRes(R.color.grey);
//        if(isUpdate){
//            builder.cancelable(false);
//            builder.onAny((dialog, which) -> {
//                if (which == DialogAction.POSITIVE) {
//                    final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
//                    try {
//                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                    } catch (android.content.ActivityNotFoundException anfe) {
//                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                    }
//                }
//            });
//        }

        builder.show();
    }
}
