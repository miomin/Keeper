package scu.miomin.com.keeper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import scu.miomin.com.keeper.basedialog.BaseDialog;
import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;

/**
 * 描述:主界面 创建日期:2015/10/21
 *
 * @author 莫绪旻
 */
public class KeeperActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_keeper);
    }

    public void opendialog(View view) {
        BaseDialog.actionStartActivity(1, this, "1", "2", "3", "4");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    int returnedChoose = data.getIntExtra("returnedChoose", -1);
                    Toast.makeText(getApplication(), returnedChoose + "", Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }
    }
}
