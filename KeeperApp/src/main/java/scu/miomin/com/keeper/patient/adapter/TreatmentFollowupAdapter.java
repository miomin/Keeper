package scu.miomin.com.keeper.patient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.activity.DoctorInfoActivity;
import scu.miomin.com.keeper.bean.TreatmentBean;
import scu.miomin.com.keeper.bean.TreatmentFollowupBean;
import scu.miomin.com.keeper.resource.MyLoader;

/**
 * Created by miomin on 15/11/22.
 */
public class TreatmentFollowupAdapter extends BaseAdapter {

    private TextView tvDoctorName;
    private TextView tvAdministrative;
    private TextView tvProfessional;
    private TextView tvTreatmentDate;
    private TextView tvTreatmentHospital;
    private TextView tvPatientName;
    private TextView tvTreatmentReason;
    private ImageView ivDoctorHead;

    private ArrayList<TreatmentFollowupBean> treatmentFollowupList;
    private Context context;
    private TreatmentBean treatmentBean;

    public TreatmentFollowupAdapter(ArrayList<TreatmentFollowupBean> treatmentFollowupList,
                                    Context context, TreatmentBean treatmentBean) {
        this.context = context;
        this.treatmentFollowupList = treatmentFollowupList;
        this.treatmentBean = treatmentBean;
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
            convertView = View.inflate(context, R.layout.layout_treatment_info_top_patient, null);
            tvDoctorName = (TextView) convertView.findViewById(R.id.tvDoctorName);
            tvAdministrative = (TextView) convertView.findViewById(R.id.tvAdministrative);
            tvProfessional = (TextView) convertView.findViewById(R.id.tvProfessional);
            tvTreatmentDate = (TextView) convertView.findViewById(R.id.tvTreatmentDate);
            tvTreatmentHospital = (TextView) convertView.findViewById(R.id.tvTreatmentHospital);
            tvPatientName = (TextView) convertView.findViewById(R.id.tvPatientName);
            tvTreatmentReason = (TextView) convertView.findViewById(R.id.tvTreatmentReason);
            ivDoctorHead = (ImageView) convertView.findViewById(R.id.ivDoctorHead);

            tvDoctorName.setText(treatmentBean.getDoctorBean().getName());
            tvAdministrative.setText(treatmentBean.getDoctorBean().getAdministrative());
            tvProfessional.setText(treatmentBean.getDoctorBean().getProfessional());
            tvTreatmentDate.setText(treatmentBean.getDate());
            tvTreatmentHospital.setText(treatmentBean.getDoctorBean().getHospitalBean().getName());
            tvPatientName.setText(treatmentBean.getPatientBean().getName());
            tvTreatmentReason.setText(treatmentBean.getTreatmentReason());
            MyLoader.dispalyFromAssets(treatmentBean.getDoctorBean().getHeadUrl(), ivDoctorHead);

            View layout_doctorinfo = convertView.findViewById(R.id.layout_doctorinfo);
            layout_doctorinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DoctorInfoActivity.actionStart(context, treatmentBean.getDoctorBean());
                }
            });
        } else {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_treatment_info, null);
                viewHolder = new ViewHolder();
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            TreatmentFollowupBean treatmentFollowupBean = treatmentFollowupList.get(position - 1);

            viewHolder.tvDate.setText(treatmentFollowupBean.getDate());
            viewHolder.tvTitle.setText("诊后第" + position + "次随访");
        }
        return convertView;
    }

    class ViewHolder {
        public TextView tvDate;
        public TextView tvTitle;
    }
}
