# TextInputLayoutBug

This app showcases a bug where changing from a custom endIconDrawable to TextInputLayout.END_ICON_CLEAR_TEXT crashes the app.
An issue with the custom drawable not showing up is also showcased.

Expected outcome:

* When state is Loading, loading spinner is shown
* When state is Success, TextInputLayout.END_ICON_CLEAR_TEXT is shown


How to reproduce the bug:

* Focus the EditText
* Type something, wait for search state to be "Success"
* Start typing again

To see a working version:

* Change lib version to `1.2.0-alpha05` or `1.2.0-alpha06` in `build.gradle`.
* Build & run the app


| Version | Working as expected |
|---|---|
| 1.1.0 | no (loading icon sometimes disappears) |
| 1.2.0-alpha05 | yes |
| 1.2.0-alpha06 | yes |
| 1.2.0-beta01 | no |
| 1.2.0-rc01 | no |
| 1.2.0 | no |
| 1.2.1 | no |
| 1.3.0-alpha01 | no |
| 1.3.0-alpha02 | no |


Currently the app uses Material Components version `1.2.1`.

Versions tested on OnePlus 7 running Android 10.  
Bug is also reproducible on emulators running official Android images.


Bug stack trace:

```
E/AndroidRuntime: FATAL EXCEPTION: main
    Process: fi.vpuonti.textinputlayoutbug, PID: 26919
    java.lang.IndexOutOfBoundsException: Index: 2, Size: 2
        at java.util.ArrayList.get(ArrayList.java:437)
        at android.widget.TextView.sendOnTextChanged(TextView.java:10610)
        at android.widget.TextView.handleTextChanged(TextView.java:10700)
        at android.widget.TextView$ChangeWatcher.onTextChanged(TextView.java:13454)
        at android.text.SpannableStringBuilder.sendTextChanged(SpannableStringBuilder.java:1282)
        at android.text.SpannableStringBuilder.replace(SpannableStringBuilder.java:591)
        at android.text.SpannableStringBuilder.replace(SpannableStringBuilder.java:522)
        at android.text.SpannableStringBuilder.replace(SpannableStringBuilder.java:42)
        at android.view.inputmethod.BaseInputConnection.replaceText(BaseInputConnection.java:843)
        at android.view.inputmethod.BaseInputConnection.setComposingText(BaseInputConnection.java:616)
        at com.android.internal.view.IInputConnectionWrapper.executeMessage(IInputConnectionWrapper.java:393)
        at com.android.internal.view.IInputConnectionWrapper$MyHandler.handleMessage(IInputConnectionWrapper.java:89)
        at android.os.Handler.dispatchMessage(Handler.java:107)
        at android.os.Looper.loop(Looper.java:214)
        at android.app.ActivityThread.main(ActivityThread.java:7710)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:516)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:950)

```