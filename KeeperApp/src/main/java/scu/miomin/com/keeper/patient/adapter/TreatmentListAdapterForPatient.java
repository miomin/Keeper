package scu.miomin.com.keeper.patient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.bean.TreatmentBean;
import scu.miomin.com.keeper.resource.MyLoader;

/**
 * 描述:病人端诊后随访列表的适配器 创建日期:2015/11/22
 *
 * @author 莫绪旻
 */
public class TreatmentListAdapterForPatient extends BaseAdapter {

    private ArrayList<TreatmentBean> listTreatmentFollowerForPatient;
    private Context context;

    // 构造器
    public TreatmentListAdapterForPatient(Context context) {
        super();
        this.listTreatmentFollowerForPatient = new ArrayList<TreatmentBean>();
        this.context = context;
        notifyDataSetChanged();
    }

    // 构造器
    public TreatmentListAdapterForPatient(Context context, ArrayList<TreatmentBean> listTreatmentFollowerForPatient) {
        super();
        this.listTreatmentFollowerForPatient = listTreatmentFollowerForPatient;
        this.context = context;
        notifyDataSetChanged();
    }

    public void add(TreatmentBean treatmentFollowupForPatientBean) {
        listTreatmentFollowerForPatient.add(treatmentFollowupForPatientBean);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listTreatmentFollowerForPatient.size();
    }

    @Override
    public Object getItem(int position) {
        return listTreatmentFollowerForPatient.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 保留一个item的控件
        viewHolder holder = null;

        if (convertView == null) {
            // 拿到ListViewItem的布局（一行，需要单独定义一个），转换为View类型的对象
            convertView = View.inflate(context, R.layout.item_treatment_list_patient, null);
            holder = new viewHolder();
            holder.ivHead = (ImageView) convertView.findViewById(R.id.ivHead);
            holder.tvHospital = (TextView) convertView.findViewById(R.id.tvHospital);
            holder.tvName = (TextView) convertView
                    .findViewById(R.id.tvName);
            holder.tvTreatmentReason = (TextView) convertView
                    .findViewById(R.id.tvTreatmentReason);
            holder.tvTreatmentDate = (TextView) convertView
                    .findViewById(R.id.tvTreatmentDate);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        // 更新保留的控件中的数据
        TreatmentBean treatmentFollowupForPatientBean =
                listTreatmentFollowerForPatient.get(position);

        if (treatmentFollowupForPatientBean == null)
            return null;

        holder.tvHospital.setText(treatmentFollowupForPatientBean.getDoctorBean().getHospitalBean().getName());
        holder.tvName.setText(treatmentFollowupForPatientBean.getDoctorBean().getName());
        holder.tvTreatmentReason.setText(treatmentFollowupForPatientBean.getTreatmentReason());
        holder.tvTreatmentDate.setText(treatmentFollowupForPatientBean.getDate());
        MyLoader.dispalyFromAssets(treatmentFollowupForPatientBean.getDoctorBean().getHeadUrl(),
                holder.ivHead);

        // 将更新后的控件返回给Android系统
        return convertView;
    }

    class viewHolder {
        ImageView ivHead;
        TextView tvHospital;
        TextView tvName;
        TextView tvTreatmentReason;
        TextView tvTreatmentDate;
    }
}