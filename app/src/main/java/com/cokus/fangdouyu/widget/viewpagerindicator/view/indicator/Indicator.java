/*
Copyright 2014 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.cokus.fangdouyu.widget.viewpagerindicator.view.indicator;

import android.view.View;
import android.view.ViewGroup;

import com.cokus.fangdouyu.widget.viewpagerindicator.view.indicator.slidebar.ColorBar;
import com.cokus.fangdouyu.widget.viewpagerindicator.view.indicator.slidebar.DrawableBar;
import com.cokus.fangdouyu.widget.viewpagerindicator.view.indicator.slidebar.LayoutBar;
import com.cokus.fangdouyu.widget.viewpagerindicator.view.indicator.slidebar.ScrollBar;
import com.cokus.fangdouyu.widget.viewpagerindicator.view.indicator.transition.OnTransitionTextListener;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 指示器
 */
public interface Indicator {
    /**
     * 设置适配器
     */
    public void setAdapter(IndicatorAdapter adapter);

    /**
     * 设置选中监听
     *
     * @param onItemSelectedListener
     */
    public void setOnItemSelectListener(OnItemSelectedListener onItemSelectedListener);

    /**
     * 获取选中监听
     *
     * @return OnItemSelectedListener
     */
    public OnItemSelectedListener getOnItemSelectListener();

    /**
     * 设置滑动变化的转换监听，tab在切换过程中会调用此监听。<br>
     * 设置它可以自定义实现在滑动过程中，tab项的字体变化，颜色变化等等效果<br>
     * 目前提供的子类
     * {@link OnTransitionTextListener}
     *
     * @param onTransitionListener
     */
    public void setOnTransitionListener(OnTransitionListener onTransitionListener);

    public OnTransitionListener getOnTransitionListener();

    /**
     * 设置滑动块<br>
     * 设置它可以自定义滑动块的样式。<br>
     * 目前提供的子类 {@link ColorBar}
     * {@link DrawableBar}
     * {@link LayoutBar}
     *
     * @param scrollBar
     */
    public void setScrollBar(ScrollBar scrollBar);

    public IndicatorAdapter getIndicatorAdapter();

    /**
     * 设置当前项.<br>
     * 如果使用IndicatorViewPager则使用IndicatorViewPager.setCurrentItem而不是在调用该方法
     *
     * @param item
     */
    public void setCurrentItem(int item);

    public void setCurrentItem(int item, boolean anim);

    /**
     * 获取每一项的tab
     *
     * @param item 索引
     * @return ItemView
     */
    public View getItemView(int item);

    /**
     * 获取当前选中项
     *
     * @return current
     */
    public int getCurrentItem();

    /**
     * 获取之前选中的项,可能返回-1，表示之前没有选中
     *
     * @return PreSelectItem
     */
    public int getPreSelectItem();

    /**
     * ViewPager切换变化的函数
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    /**
     * 适配器
     */
    public static abstract class IndicatorAdapter {
        private boolean isLoop;

        boolean isLoop() {
            return isLoop;
        }

        void setIsLoop(boolean isLoop) {
            this.isLoop = isLoop;
        }

        private Set<DataSetObserver> observers = new LinkedHashSet<DataSetObserver>(2);

        public abstract int getCount();

        public abstract View getView(int position, View convertView, ViewGroup parent);

        public void notifyDataSetChanged() {
            for (DataSetObserver dataSetObserver : observers) {
                dataSetObserver.onChange();
            }
        }

        public void registDataSetObserver(DataSetObserver observer) {
            observers.add(observer);
        }

        public void unRegistDataSetObserver(DataSetObserver observer) {
            observers.remove(observer);
        }

    }

    /**
     * 数据源观察者
     */
    static interface DataSetObserver {
        public void onChange();
    }

    /**
     * tab项选中监听
     */
    public static interface OnItemSelectedListener {
        /**
         * 注意 preItem 可能为 -1。表示之前没有选中过,每次adapter.notifyDataSetChanged也会将preItem
         * 设置为-1；
         *
         * @param selectItemView 当前选中的view
         * @param select         当前选中项的索引
         * @param preSelect      之前选中项的索引
         */
        public void onItemSelected(View selectItemView, int select, int preSelect);
    }

    /**
     * tab滑动变化的转换监听，tab在切换过程中会调用此监听。<br>
     * 通过它可以自定义实现在滑动过程中，tab项的字体变化，颜色变化等等效果<br>
     * 目前提供的子类
     * {@link OnTransitionTextListener}
     */
    public static interface OnTransitionListener {
        public void onTransition(View view, int position, float selectPercent);
    }

    public void onPageScrollStateChanged(int state);

    public void setItemClickable(boolean clickable);

    public boolean isItemClickable();
}