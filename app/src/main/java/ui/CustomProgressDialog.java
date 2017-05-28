package ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.loginandsignup.R;

/**
 * Created by Rozerin on 28.05.2017.
 */

public class CustomProgressDialog extends ProgressDialog {

    private AnimationDrawable animationDrawable;

    public CustomProgressDialog(Context context) {

        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_custom_progress_dialog);
        ImageView la = (ImageView) findViewById(R.id.animation);
        la.setBackgroundResource(R.drawable.progressbar);
        animationDrawable = (AnimationDrawable) la.getBackground();


    }
}
