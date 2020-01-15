package com.seatrend.xj.electricbicyclesalesystem.util;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

/**
 * 这个工具干嘛的？主要是为是否邮寄按钮选择来是否显示相应的内容
 */
public class CheckBoxUtils {

    public static void setListener(final CheckBox cb1, final CheckBox cb2) {
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb1.setChecked(true);
                    cb2.setChecked(false);
                } else {
                    cb1.setChecked(false);
                    cb2.setChecked(true);
                }
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb2.setChecked(true);
                    cb1.setChecked(false);
                } else {
                    cb2.setChecked(false);
                    cb1.setChecked(true);
                }
            }
        });
    }

    public static void setListenerAndView(final CheckBox cb1, final CheckBox cb2, final View view) {
        if (null == view) {
            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb1.setChecked(true);
                        cb2.setChecked(false);
                    } else {
                        cb1.setChecked(false);
                        cb2.setChecked(true);
                    }
                }
            });
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb2.setChecked(true);
                        cb1.setChecked(false);
                    } else {
                        cb2.setChecked(false);
                        cb1.setChecked(true);
                    }
                }
            });
        } else if (view instanceof Spinner) {
            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb1.setChecked(true);
                        cb2.setChecked(false);
                        view.setVisibility(View.INVISIBLE);
                    }
                }
            });
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb2.setChecked(true);
                        cb1.setChecked(false);
                        view.setVisibility(View.VISIBLE);
                    }else {
                        view.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else {
            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb1.setChecked(true);
                        cb2.setChecked(false);
                        view.setVisibility(View.GONE);
                    } else {
                        cb1.setChecked(false);
                        cb2.setChecked(true);
                        view.setVisibility(View.VISIBLE);
                    }
                }
            });
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb2.setChecked(true);
                        cb1.setChecked(false);
                        view.setVisibility(View.VISIBLE);
                    } else {
                        cb2.setChecked(false);
                        cb1.setChecked(true);
                        view.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
    public static void setListenerAndView(final CheckBox cb1, final CheckBox cb2, final View view1,final View view2) {
            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb1.setChecked(true);
                        cb2.setChecked(false);
                        view1.setVisibility(View.VISIBLE);
                    } else {
                        cb1.setChecked(false);
                        cb2.setChecked(true);
                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                    }
                }
            });
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb2.setChecked(true);
                        cb1.setChecked(false);
                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                    } else {
                        cb2.setChecked(false);
                        cb1.setChecked(true);
                        view1.setVisibility(View.VISIBLE);
                    }
                }
            });
    }
}
