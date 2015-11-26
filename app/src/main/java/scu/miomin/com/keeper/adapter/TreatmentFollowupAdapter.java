package scu.miomin.com.keeper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.bean.TreatmentFollowupBean;

/**
 * Created by miomin on 15/11/22.
 */
public class TreatmentFollowupAdapter extends BaseAdapter {

    private ArrayList<TreatmentFollowupBean> treatmentFollowupList;
    private Context context;

    public TreatmentFollowupAdapter(ArrayList<TreatmentFollowupBean> treatmentFollowupList, Context context) {
        this.context = context;
        this.treatmentFollowupList = treatmentFollowupList;
    }

    public void add(TreatmentFollowupBean treatmentFollowupBean) {
        treatmentFollowupList.add(treatmentFollowupBean);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return treatmentFollowupList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return treatmentFollowupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (position == 0) {
            convertView = View.inflate(context, R.layout.layout_treatment_followup_top, null);
        } else {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_treatment_followup, null);
                viewHolder = new ViewHolder();
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            TreatmentFollowupBean treatmentFollowupBean = treatmentFollowupList.get(position - 1);

            viewHolder.tvDate.setText(treatmentFollowupBean.getDate());
        }
        return convertView;
    }

    class ViewHolder {
        public TextView tvDate;
    }
}
