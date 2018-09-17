package cn.stayzeal.andr.common.utils;

public class FailMessageManager {

    //flag: is request fail message already shown
    //
    private boolean mMessageAlreadyShown = false;

    //clear fail message shown flag
    //
    public void clearShownFlag() {
        mMessageAlreadyShown = false;
    }

    //when network is connected, and fail message not shown to user, show message to user
    //
    public void tryShowFailMessage() {
//        if(MyApplication.isNetworkConnected()) {
        if (!mMessageAlreadyShown) {
            showFailMessage();
            mMessageAlreadyShown = true;
        }
//        }
    }

    public void showFailMessage() {
        ToastUtil.show("网络连接不可用，请检查网络");
    }

}
