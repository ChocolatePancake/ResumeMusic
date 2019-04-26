package tech.com.commoncore.others;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.aries.ui.view.title.TitleBarView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import tech.com.commoncore.R;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.utils.ApplicationUtil;

/**
 * Desc: 用户协议说明.
 */
public class UserAgreementActivity extends BaseTitleActivity {
    private TextView mTvContent;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("用户协议");
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_user_agreement;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mTvContent =  findViewById(R.id.tv_content);

        String text = getFromAssets("RegistAndProtect.html");
        text = text.replaceAll("MT4期货原油软件", ApplicationUtil.getAppName(mContext));
        mTvContent.setText(Html.fromHtml(text));
    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "解析错误";
    }
}
