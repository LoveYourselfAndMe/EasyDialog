package kale.easydialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import kale.ui.view.DialogInterface.OnCancelListener;
import kale.ui.view.DialogInterface.OnClickListener;
import kale.ui.view.DialogInterface.OnDismissListener;
import kale.ui.view.DialogInterface.OnItemClickListener;
import kale.ui.view.DialogInterface.OnMultiChoiceClickListener;
import kale.ui.view.MultiChoiceDialog;
import kale.ui.view.ProgressDialog;
import kale.ui.view.SimpleDialog;
import kale.ui.view.SingleChoiceDialog;

/**
 * 关于更多对话框的设置请参考：http://www.cnblogs.com/tianzhijiexian/p/3867731.html
 */
public class MainActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignViews();
        setViews();
    }

    private Button simpleDialogBtn;

    private Button singleDialogBtn;

    private Button multiDialogBtn;

    private Button simplePgDialogBtn;

    private Button simplePgHDialogBtn;

    private void assignViews() {
        simpleDialogBtn = (Button) findViewById(R.id.simple_dialog_btn);
        singleDialogBtn = (Button) findViewById(R.id.single_dialog_btn);
        multiDialogBtn = (Button) findViewById(R.id.multi_dialog_btn);
        simplePgDialogBtn = (Button) findViewById(R.id.simple_pg_dialog_btn);
        simplePgHDialogBtn = (Button) findViewById(R.id.simple_pg_h_dialog_btn);
    }


    private void setViews() {
        // 最简单提示对话框
        simpleDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Dial().show(getSupportFragmentManager(), "dd");
                setSimpleDialog();
            }
        });

        // 单选对话框
        singleDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleChoiceDialog();
            }
        });

        // 多选对话框
        multiDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiChoiceDialog();
            }
        });

        // 最简单的进度条对话框
        simplePgDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cricualProgressDialog();
            }
        });

        // 有进度的对话框
        simplePgHDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog();
            }
        });
    }

    private void progressDialog() {
        ProgressDialog.Builder builder = new ProgressDialog.Builder(false);
        builder.setTitle("横向进度条");
        builder.setMax(100);
        builder.setIndeterminate(false);//设置不显示明确的进度
        builder.setProgress(40);

        final ProgressDialog dialog = builder.create();

        dialog.show(getSupportFragmentManager(), TAG);

        //启动线程，模拟一个耗时的操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                int Progress = 0;
                while (Progress < 100) {
                    try {
                        Thread.sleep(100);
                        Progress++;
                        // dialog.setProgress(Progress);
                        dialog.incrementProgressBy(1);// 进度条一次加1
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();// 完成后消失
            }
        }).start();
    }

    private void cricualProgressDialog() {
        ProgressDialog.Builder builder = new ProgressDialog.Builder(true);
        builder.setTitle("圆形进度条");
        builder.setMessage("test message");
        builder.setIndeterminate(true);//设置不显示明确的进度
        // builder.setIndeterminate(false);// 设置显示明确的进度

        // 点击空白处，点击返回键都会触发onCancel->onDismiss
        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface builder) {
                Log.d(TAG, "onCancel");
            }
        });
        builder.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface builder) {
                Log.d(TAG, "onDismiss");
            }
        });
        ProgressDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show(getSupportFragmentManager(), TAG);
    }

    private void multiChoiceDialog() {
        MultiChoiceDialog.Builder builder = new MultiChoiceDialog.Builder();
        builder.setData(new String[]{"Android", "ios", "wp"}, new boolean[]{true, false, true});
        builder.setOnMultiChoiceClickListener(new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Log.d(TAG, "onClick pos = " + which + " , isChecked = " + isChecked);
            }
        });
        builder.create().show(getSupportFragmentManager(), TAG);
    }

    private void singleChoiceDialog() {
        SingleChoiceDialog.Builder builder = new SingleChoiceDialog.Builder();
        builder.setTitle("Single Choice Dialog");
        builder.setData(new String[]{"Android", "ios", "wp"}, 1);
        builder.setOnItemSelectedListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DialogInterface dialog, int position, long id) {
                Log.d(TAG, "onItemClick pos = " + position);
                dialog.dismiss();
            }
        });
        SingleChoiceDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), TAG);
    }

    private void setSimpleDialog() {
        SimpleDialog.Builder builder = new SimpleDialog.Builder();
        builder.setTitle("Title");
        builder.setMessage("Message");

        // onCancel - > onDismiss
        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "onCancel");
            }
        });
        builder.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.d(TAG, "onDismiss");
            }
        });
        // 设置对话框上的按钮 ok->dismiss
        builder.setPositiveListener("ok---------df", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick ok");
            }
        });

        // cancel -> dismiss
        builder.setNegativeListener("cancel dfsdsfdasf", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick cancel");
                dialog.dismiss();
            }
        });

        SimpleDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show(getSupportFragmentManager(), TAG);
    }

}