package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/6/14.
 */
public class QuestionResultAdapter extends BaseAdapter {
    private JSONArray array;
    private Context context;
    private int height;

    public QuestionResultAdapter(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
        height = (Utils.getScreenMetrics(context).x - Utils.dip2px(context, 100)) / 5;
    }

    public void upDateData(JSONArray array) {
        if (array != null) {
            this.array = array;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return array.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_question_result_grid_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams layoutParams = holder.selectNum.getLayoutParams();
        layoutParams.width = height;
        layoutParams.height = height;
        holder.selectNum.setLayoutParams(layoutParams);

        holder.selectNum.setText(position+1 + "");
        try {
            JSONObject object = (JSONObject) array.get(position);
            if (object.optString("isCorrect").equals("2")) {//没答题
                holder.selectNum.setBackgroundResource(R.drawable.circle_blue_stroke_bg);
                holder.selectNum.setTextColor(context.getResources().getColor(R.color.light_title_blue));
            }
            if (object.optString("isCorrect").equals("1")) {//答题正确
                holder.selectNum.setBackgroundResource(R.drawable.circle_blue_bg);
                holder.selectNum.setTextColor(context.getResources().getColor(R.color.white));
            }
            if (object.optString("isCorrect").equals("0")) {//答题错误
                holder.selectNum.setBackgroundResource(R.drawable.circle_red_bg);
                holder.selectNum.setTextColor(context.getResources().getColor(R.color.white));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.select_num)
        TextView selectNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
