package me.chonchol.andropos.helper;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import me.chonchol.andropos.R;

/**
 * Created by mehedi.chonchol on 24-Oct-18.
 */

public class ViewDialog {
    Activity activity;
    Dialog dialog;

    public ViewDialog(Activity activity) {
        this.activity = activity;
    }

    public void show(){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.andropos_dialog);

        ImageView imageView = dialog.findViewById(R.id.andropos_loading_image);

        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(imageView);
        Glide.with(activity)
                .load(R.drawable.ic_loading_bot)
                .into(imageViewTarget);

        dialog.show();
    }

    public void hide(){
        dialog.dismiss();
    }
}
